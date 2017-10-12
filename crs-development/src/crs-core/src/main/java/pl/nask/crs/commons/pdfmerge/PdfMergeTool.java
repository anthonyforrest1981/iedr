package pl.nask.crs.commons.pdfmerge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;

public abstract class PdfMergeTool {

    abstract protected String getToolPath();

    abstract protected List<String> getToolArguments(List<File> pdfsToMerge, File output);

    public void mergePdfFiles(List<File> pdfsToMerge, File output) throws PdfMergeToolException {
        validateFilesExist(pdfsToMerge);
        String[] commandArray = constructCommandArray(pdfsToMerge, output);
        ProcessBuilder pb = new ProcessBuilder(commandArray);
        try {
            Process process = pb.start();
            if (process.waitFor() != 0) {
                String standardMessage = IOUtils.toString(process.getInputStream());
                String errorMessage = IOUtils.toString(process.getErrorStream());
                throw new PdfMergeToolException(String.format(
                        "Could not run pdf merge tool (%s) because: %s\nMerge tool standard output: %s", getToolPath(),
                        errorMessage, standardMessage));
            }
        } catch (InterruptedException e) {
            throw new PdfMergeToolException(e);
        } catch (IOException e) {
            throw new PdfMergeToolException(e);
        }
    }

    private void validateFilesExist(List<File> pdfsToMerge) throws PdfMergeToolException {
        List<String> missingFiles = new ArrayList<String>();
        for (File file : pdfsToMerge) {
            if (!file.exists()) {
                missingFiles.add(file.getAbsolutePath());
            }
        }
        if (!missingFiles.isEmpty()) {
            throw new PdfMergeToolException(String.format("Couldn't find files in the system: %s", Joiner.on(", ")
                    .join(missingFiles)));
        }
    }

    private String[] constructCommandArray(List<File> pdfsToMerge, File output) {
        List<String> command = new ArrayList<String>();
        command.add(getToolPath());
        command.addAll(getToolArguments(pdfsToMerge, output));
        Logger.getLogger(getClass()).debug("Calling merge tool: " + Joiner.on(" ").join(command));
        return command.toArray(new String[command.size()]);
    }

}

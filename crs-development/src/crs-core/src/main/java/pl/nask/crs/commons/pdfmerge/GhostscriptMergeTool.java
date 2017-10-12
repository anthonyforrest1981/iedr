package pl.nask.crs.commons.pdfmerge;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GhostscriptMergeTool extends PdfMergeTool {

    private String toolPath;

    public GhostscriptMergeTool(String toolPath) {
        this.toolPath = toolPath;
    }

    @Override
    protected String getToolPath() {
        return toolPath;
    }

    @Override
    protected List<String> getToolArguments(List<File> pdfsToMerge, File output) {
        String outputPath = output.getAbsolutePath();
        ArrayList<String> arguments = new ArrayList<String>(Arrays.asList("-dBATCH", "-dNOPAUSE", "-q",
                "-sDEVICE=pdfwrite", "-dPDFSETTINGS=/prepress"));
        arguments.add(String.format("-sOutputFile=%s", outputPath));
        for (File file : pdfsToMerge) {
            arguments.add(file.getAbsolutePath());
        }
        return arguments;
    }
}

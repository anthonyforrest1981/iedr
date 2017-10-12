package pl.nask.crs.domains.services.impl;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import pl.nask.crs.commons.exceptions.WhoisProcessingException;
import pl.nask.crs.domains.services.WhoisService;

public class WhoisServiceImpl implements WhoisService {

    private String whoisCommand;

    public WhoisServiceImpl(String whoisCommand) {
        this.whoisCommand = whoisCommand;
    }

    @Override
    public String generateWhoisResponse(String domainName)
            throws IOException, InterruptedException, WhoisProcessingException {
        ProcessBuilder processBulider = new ProcessBuilder(whoisCommand, domainName);
        Process process = processBulider.start();
        try {
            if (process.waitFor() == 0) {
                return IOUtils.toString(process.getInputStream());
            } else {
                String errorMessage = IOUtils.toString(process.getErrorStream());
                throw new WhoisProcessingException("Cannot run Whois for domain: " + domainName + "because: "
                        + errorMessage);
            }
        } finally {
            process.getInputStream().close();
            process.getOutputStream().close();
            process.getErrorStream().close();
        }
    }

}

package pl.nask.crs.domains.services;

import java.io.IOException;

import pl.nask.crs.commons.exceptions.WhoisProcessingException;

public interface WhoisService {

    String generateWhoisResponse(String domainName) throws IOException, InterruptedException, WhoisProcessingException;

}

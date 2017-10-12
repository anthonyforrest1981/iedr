package pl.nask.crs.domains.services;

import java.util.List;

public interface TopLevelDomainService {

    /**
     * Return list of known tlds. Each tld is in lowercase and if required
     * encoded as a punycode url.
     * @return list of lowercased tlds.
     */
    List<String> getAll();

}

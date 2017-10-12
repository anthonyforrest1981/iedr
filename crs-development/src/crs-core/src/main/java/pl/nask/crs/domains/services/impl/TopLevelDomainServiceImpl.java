package pl.nask.crs.domains.services.impl;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.domains.dao.TopLevelDomainDAO;
import pl.nask.crs.domains.services.TopLevelDomainService;

public class TopLevelDomainServiceImpl implements TopLevelDomainService {

    private TopLevelDomainDAO dao;

    public TopLevelDomainServiceImpl(TopLevelDomainDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<String> getAll() {
        List<String> tlds = dao.getAll();
        List<String> result = new ArrayList<>();
        for (String tld : tlds) {
            result.add(IDN.toASCII(tld.toLowerCase()));
        }
        return result;
    }

}

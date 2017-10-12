package pl.nask.crs.account.impl;

import pl.nask.crs.account.ModVersionService;
import pl.nask.crs.account.dao.SequentialNumberDAO;
import pl.nask.crs.commons.SequentialNumberGenerator;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SequentialNumberServiceImpl implements ModVersionService, SequentialNumberGenerator {

    SequentialNumberDAO dao;

    public SequentialNumberServiceImpl(SequentialNumberDAO dao) {
        this.dao = dao;
    }

    @Override
    public int getNextModVersion() {
        return (int) getNextId();
    }

    @Override
    public long getNextId() {
        return dao.getNext();
    }
}

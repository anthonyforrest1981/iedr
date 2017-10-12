package pl.nask.crs.defaults.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.defaults.ResellerDefaults;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface DefaultsDAO extends GenericDAO<ResellerDefaults, String> {

    public List<ResellerDefaults> getAll();

}

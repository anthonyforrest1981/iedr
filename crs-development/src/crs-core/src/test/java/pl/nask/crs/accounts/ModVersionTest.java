package pl.nask.crs.accounts;

import javax.annotation.Resource;

import pl.nask.crs.account.ModVersionService;
import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.SequentialNumberGenerator;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */

public class ModVersionTest extends AbstractTest {

    @Resource(name = "modVersionService")
    ModVersionService service;

    @Resource(name = "doaNumberGenerator")
    SequentialNumberGenerator doaNumberGenerator;

    //TODO: CRS-72
    //    @Test
    //    public void getNextTest() {
    //        int next = service.getNextModVersion();
    //        AssertJUnit.assertEquals(1, next);
    //        next = service.getNextModVersion();
    //        AssertJUnit.assertEquals(2, next);
    //        next = service.getNextModVersion();
    //        AssertJUnit.assertEquals(3, next);
    //        next = service.getNextModVersion();
    //        AssertJUnit.assertEquals(4, next);
    //    }

    //    @Test
    //    public void doaGetNextTest() {
    //        long next = doaNumberGenerator.getNextId();
    //        AssertJUnit.assertEquals(1, next);
    //        next = doaNumberGenerator.getNextId();
    //        AssertJUnit.assertEquals(2, next);
    //        next = doaNumberGenerator.getNextId();
    //        AssertJUnit.assertEquals(3, next);
    //        next = doaNumberGenerator.getNextId();
    //        AssertJUnit.assertEquals(4, next);
    //    }
}

package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.triplepass.TriplePassSupportService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TriplePassJob extends AbstractJob {

    private final TriplePassSupportService service;

    public TriplePassJob(TriplePassSupportService service) {
        this.service = service;
    }

    @Override
    public void runJob(AuthenticatedUser user) throws AccessDeniedException {
        service.triplePass(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}

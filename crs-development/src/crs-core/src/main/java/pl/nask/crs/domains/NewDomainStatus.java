package pl.nask.crs.domains;

public enum NewDomainStatus {
    Active, // activate an inactive domain / run triple pass on a domain
    Deleted, // enter voluntary NRP
    Reactivate // exit voluntary NRP
}

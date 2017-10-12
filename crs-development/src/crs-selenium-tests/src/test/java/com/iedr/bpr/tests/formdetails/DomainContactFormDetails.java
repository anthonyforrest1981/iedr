package com.iedr.bpr.tests.formdetails;

import com.iedr.bpr.tests.utils.User;

public class DomainContactFormDetails {

    private String adminContact1;
    private String adminContact2;
    private String techContact;

    public DomainContactFormDetails(String adminContact1, String adminContact2, String techContact) {
        setAdminContact1(adminContact1);
        setAdminContact2(adminContact2);
        setTechContact(techContact);
    }

    public DomainContactFormDetails(User user, String adminContact2) {
        this(user.login, adminContact2, user.login);
    }

    public DomainContactFormDetails(User user) {
        this(user, null);
    }

    public String getAdminContact1() {
        return adminContact1;
    }

    public void setAdminContact1(String adminContact1) {
        this.adminContact1 = adminContact1;
    }

    public String getAdminContact2() {
        return adminContact2;
    }

    public void setAdminContact2(String adminContact2) {
        this.adminContact2 = adminContact2;
    }

    public String getTechContact() {
        return techContact;
    }

    public void setTechContact(String techContact) {
        this.techContact = techContact;
    }

}

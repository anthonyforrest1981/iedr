package com.iedr.bpr.tests.forms;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.gui.Gui;

public abstract class DomainContactsForm implements Form {

    public TextField admin1Field;
    public TextField admin2Field;
    public TextField techField;

    public DomainContactsForm(Gui gui, String admin1FieldId, String admin2FieldId, String techFieldId) {
        admin1Field = new TextField(gui, By.id(admin1FieldId), ErrorMessageSelector.IN_TABLE, true);
        admin2Field = new TextField(gui, By.id(admin2FieldId), ErrorMessageSelector.IN_TABLE, true);
        techField = new TextField(gui, By.id(techFieldId), ErrorMessageSelector.IN_TABLE, true);
    }

    public void fillDomainContacts(DomainContactFormDetails details) {
        admin1Field.fill(details.getAdminContact1());
        admin2Field.fill(details.getAdminContact2());
        techField.fill(details.getTechContact());
    }

    public abstract ErrorMessages getErrorMessages();

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.<Form>asList(admin1Field, admin2Field, techField);
    }

    public static class ErrorMessages {

        public String duplicateMessage = null;
        public String invalidAdmin1Message = null;
        public String invalidAdmin2Message = null;
        public String invalidTechMessage = null;
        public String emptyAdmin1Message = null;
        public String emptyTechMessage = null;

        public ErrorMessages(String duplicateMessage, String invalidAdmin1Message, String invalidAdmin2Message,
                String invalidTechMessage, String emptyAdmin1Message, String emptyTechMessage) {
            this.duplicateMessage = duplicateMessage;
            this.invalidAdmin1Message = invalidAdmin1Message;
            this.invalidAdmin2Message = invalidAdmin2Message;
            this.invalidTechMessage = invalidTechMessage;
            this.emptyAdmin1Message = emptyAdmin1Message;
            this.emptyTechMessage = emptyTechMessage;
        }
    }

}

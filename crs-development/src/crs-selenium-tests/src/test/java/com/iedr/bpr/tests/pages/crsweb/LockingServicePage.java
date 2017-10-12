package com.iedr.bpr.tests.pages.crsweb;

import java.sql.SQLException;

import com.iedr.bpr.tests.gui.CrsWebGui;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;

public class LockingServicePage {
    public void view() throws SQLException {
        crsweb().view(CrsWebGui.SiteId.LockingService);
    }
}

package com.iedr.bpr.tests.utils;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.LocalDate;

import com.iedr.bpr.tests.utils.ssh.CrsSshConnection;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OutputFiles {
    private CrsSshConnection crs;

    public OutputFiles(CrsSshConnection crs) {
        this.crs = crs;
    }

    private String getDocumentsOutputDir(String outputKey, String useSubdirKey) throws SQLException {
        String outputDir = db().getAppConfigValue(outputKey);
        boolean useSubdir = db().getAppConfigValue(useSubdirKey).equals("1");
        if (useSubdir) {
            outputDir = FilenameUtils.concat(outputDir, new LocalDate().toString()).replace("\\", "/");
        }
        return outputDir;
    }

    public String getDoaTransactionXmlFilePath() throws SQLException {
        String doaTransactionXmlLocation = getDocumentsOutputDir("doa_xml_output_dir", "doa_xml_output_use_date_subdir");
        String doaTransactionXmlFileName = db().getDoaXmlFileName();
        String doaTransactionXmlFilePath = FilenameUtils.concat(doaTransactionXmlLocation, doaTransactionXmlFileName)
                .replace("\\", "/");
        return doaTransactionXmlFilePath;
    }

    public void checkDoaTransactionFile(int doaId, String userName, float amount)
            throws SQLException, JSchException, IOException {
        assertEquals(doaId + 1, db().getLastDoaId());
        String doaTransactionXmlFilePath = getDoaTransactionXmlFilePath();
        String fileContent = crs.getDoaTransactionXmlContent(doaTransactionXmlFilePath);
        assertTrue(fileContent.contains(userName));
        assertTrue(fileContent.contains(String.valueOf(amount)));
    }

    public void clearDoaTransactionXmls() throws JSchException, IOException, SQLException {
        crs.execute("rm -f " + getDoaTransactionXmlFilePath(), true);
    }

    public String getAccountXmlFilePath() throws SQLException {
        String accountTransactionXmlLocation = getDocumentsOutputDir("account_update_xml_output_dir",
                "account_update_xml_output_use_date_subdir");
        String accountTransactionXmlFileName = db().getAccountXmlFileName();
        String accountTransactionXmlFilePath = FilenameUtils.concat(accountTransactionXmlLocation,
                accountTransactionXmlFileName).replace("\\", "/");
        return accountTransactionXmlFilePath;
    }

    public void checkAccountXml(int accountId, String nicHandle) throws JSchException, IOException, SQLException {
        assertEquals(accountId + 1, db().getLastAccountId());
        String accountXmlFilePath = getAccountXmlFilePath();
        String fileContent = crs.getAccountXmlContent(accountXmlFilePath);
        assertTrue(fileContent.contains(nicHandle));
    }

    public void clearAccountXmls() throws JSchException, IOException, SQLException {
        crs.execute("rm -f " + getAccountXmlFilePath(), true);
    }

    public String getInvoicePdfPath(String invoiceNumber) throws SQLException {
        String invoicePdfLocation = getDocumentsOutputDir("pdf_invoice_output_dir",
                "pdf_invoice_output_use_date_subdir");
        String invoicePdfFileName = String.format("%s.pdf", invoiceNumber);
        String invoicePdfFilePath = FilenameUtils.concat(invoicePdfLocation, invoicePdfFileName).replace("\\", "/");
        return invoicePdfFilePath;
    }

    public void renameInvoice(String invoiceNumber) throws SQLException, IOException, JSchException {
        String invoicePdfPath = getInvoicePdfPath(invoiceNumber);
        String falseInvoicePdfPath = String.format("%s.false", invoicePdfPath);
        System.out.println(invoicePdfPath);
        System.out.println(falseInvoicePdfPath);
        crs.moveFile(invoicePdfPath, falseInvoicePdfPath, true);
    }
}

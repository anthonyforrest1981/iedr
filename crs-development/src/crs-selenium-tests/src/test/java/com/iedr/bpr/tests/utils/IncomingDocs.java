package com.iedr.bpr.tests.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.ssh;

public class IncomingDocs {

    private String tempDirName;
    private String basePath;

    public IncomingDocs(String basePath) {
        this.basePath = basePath.replaceAll("/$", "");
    }

    public String populateDocDirs(String domainName, String newFileName, String newFileContent)
            throws IOException, JSchException, SQLException {
        removeCreatedDocDirs();
        tempDirName = createSubDirs();
        populateAssignedSubDirs(domainName, newFileContent);
        populateNewSubDirs(newFileName, newFileContent);
        return tempDirName;
    }

    public void removeCreatedDocDirs() throws JSchException, IOException, SQLException {
        for (DocumentType docType : DocumentType.values()) {
            removeCreatedDocDir(docType);
        }
    }

    public Map<String, String> getCurrentAppConfig() throws SQLException {
        Map<String, String> appConfig = new HashMap<String, String>();
        List<String> keys = Arrays.asList("incoming_docs_path_attachment_assigned",
                "incoming_docs_path_attachment_new", "incoming_docs_path_fax_assigned", "incoming_docs_path_fax_new",
                "incoming_docs_path_paper_assigned", "incoming_docs_path_paper_new");
        for (String key : keys) {
            appConfig.put(key, db().getAppConfigValue(key));
        }
        return appConfig;
    }

    public void updateAppConfig(Map<String, String> appConfig, Map<String, String> initialAppConfig)
            throws SQLException {
        for (Map.Entry<String, String> entry : appConfig.entrySet()) {
            if (!initialAppConfig.containsKey(entry.getKey())) {
                throw new RuntimeException(String.format("%s value not in initialAppConfig", entry.getKey()));
            }
            db().setAppConfigStringValue(entry.getKey(), entry.getValue());
        }
    }

    public Map<String, String> getModifiedAppConfig(String tempDirName) throws SQLException {
        Map<String, String> appConfig = getCurrentAppConfig();
        for (Map.Entry<String, String> entry : appConfig.entrySet()) {
            String newValue = String.format("%s/%s", entry.getValue(), tempDirName);
            appConfig.put(entry.getKey(), newValue);
        }
        return appConfig;
    }

    public void setIncomingDocsDirectory(String tempDirName, Map<String, String> initialAppConfig) throws SQLException {
        updateAppConfig(getModifiedAppConfig(tempDirName), initialAppConfig);
    }

    private void removeCreatedDocDir(DocumentType docType) throws SQLException, JSchException, IOException {
        if (tempDirName == null) {
            // We haven't created any directories.
            return;
        }
        String docDirFullPath = getDocDirFullPath(docType, tempDirName);
        ssh().crsweb.execute("rm -rf " + docDirFullPath, true);
    }

    private String createSubDirs() throws SQLException, JSchException, IOException {
        String tempDirName = null;
        for (DocumentType docType : DocumentType.values()) {
            String docDirFullPath = getDocDirFullPath(docType, tempDirName);
            if (tempDirName == null) {
                String tempDirFullPath = ssh().crsweb.execute(String.format("mktemp -d -p %s", docDirFullPath), true);
                tempDirName = tempDirFullPath.replace(docDirFullPath, "").replace("/", "");
            } else {
                ssh().crsweb.execute(String.format("mkdir %s", docDirFullPath), true);
            }
        }
        return tempDirName;
    }

    private String getDocDirFullPath(DocumentType docType, String tempDirName) throws SQLException {
        // Returns full path of a directory containing documents e.g.
        // /opt/crs/tomcat/webapps/incoming_docs/attachment_assigned. If a temp
        // directory has been created the path will look like this
        // /opt/crs/tomcat/webapps/incoming_docs/attachment_assigned/tmp.jbVJv14145.
        String dbRelativePath = db().getAppConfigValue(docType.configKey);
        if (tempDirName != null && !dbRelativePath.contains(tempDirName)) {
            dbRelativePath = getNewRelativePath(dbRelativePath, tempDirName);
        }
        return basePath + dbRelativePath;
    }

    private String getNewRelativePath(String relativePath, String newDocsDir) {
        return String.format("%s/%s", relativePath, newDocsDir);
    }

    private void changeMode(DocumentType docType) throws JSchException, IOException, SQLException {
        if (tempDirName == null) {
            throw new RuntimeException("Attempted to change access rights of a real directory");
        }
        String docDirFullPath = getDocDirFullPath(docType, tempDirName);
        ssh().crsweb.execute(String.format("chmod -R a+r %s", docDirFullPath), true);
    }

    private void populateAssignedSubDirs(String domainName, String newFileContent)
            throws SQLException, IOException, JSchException {
        List<Integer> docIds = db().getDocumentIdsForDomain(domainName);
        for (int docId : docIds) {
            String docTypeStr = db().getDocumentType(docId);
            DocumentType docType = DocumentType.getType(docTypeStr, true);
            String docName = db().getDocumentName(docId);
            createAssignedFile(docType, docName, newFileContent);
            changeMode(docType);
        }
    }

    private void createAssignedFile(DocumentType docType, String docName, String newFileContent)
            throws IOException, JSchException, SQLException {
        createFile(docName, docType, newFileContent);
    }

    private void populateNewSubDirs(String newFileName, String newFileContent)
            throws IOException, JSchException, SQLException {
        List<DocumentType> newDocumentTypes = Arrays.asList(DocumentType.ATTACHMENT_NEW, DocumentType.FAX_NEW,
                DocumentType.PAPER_NEW);
        for (DocumentType docType : newDocumentTypes) {
            createFile(newFileName, docType, newFileContent);
            changeMode(docType);
        }
    }

    private void createFile(String docName, DocumentType docType, String newFileContent)
            throws IOException, JSchException, SQLException {
        String path = getDocumentLocation(docName, docType);
        ssh().crsweb.echoToFile(newFileContent, path, true);
    }

    public String getDocumentLocation(String docName, DocumentType docType) throws SQLException {
        if (tempDirName == null) {
            throw new RuntimeException("Attempted to read a document from a real directory");
        }
        return String.format("%s/%s", getDocDirFullPath(docType, tempDirName), docName);
    }

    public static enum DocumentType {
        ATTACHMENT_NEW("incoming_docs_path_attachment_new"), ATTACHMENT_ASSIGNED(
                "incoming_docs_path_attachment_assigned"), FAX_NEW("incoming_docs_path_fax_new"), FAX_ASSIGNED(
                "incoming_docs_path_fax_assigned"), PAPER_NEW("incoming_docs_path_paper_new"), PAPER_ASSIGNED(
                "incoming_docs_path_paper_assigned");

        public String configKey;

        private DocumentType(String configKey) {
            this.configKey = configKey;
        }

        public static DocumentType getType(String type, boolean assigned) {
            if ("attachment".equals(type)) {
                if (assigned) {
                    return ATTACHMENT_ASSIGNED;
                } else {
                    return ATTACHMENT_NEW;
                }
            } else if ("fax".equals(type)) {
                if (assigned) {
                    return FAX_ASSIGNED;
                } else {
                    return FAX_NEW;
                }
            } else if ("paper".equals(type)) {
                if (assigned) {
                    return PAPER_ASSIGNED;
                } else {
                    return PAPER_NEW;
                }
            }
            throw new RuntimeException("Invalid document type: " + type);
        }

        public static List<DocumentType> newDocuments() {
            return Arrays.asList(ATTACHMENT_NEW, FAX_NEW, PAPER_NEW);
        }

        public List<DocumentType> assignedDocuments() {
            return Arrays.asList(ATTACHMENT_ASSIGNED, FAX_ASSIGNED, PAPER_ASSIGNED);
        }
    }

}

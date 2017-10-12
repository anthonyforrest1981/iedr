package com.iedr.bpr.tests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static org.junit.Assert.assertEquals;

public class DataTest extends SeleniumTest {

    public DataTest(SeleniumTest.Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return null;
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    private List<String> getResourcePathsFromResourceDir(String dir) throws IOException {
        String list = IOUtils.toString(getClass().getResourceAsStream(dir));
        return Arrays.asList(list.split("\n"));
    }

    private List<Pair<String, String>> groupResourcePaths(List<String> resourcePaths) {
        List<Pair<String, String>> pairs = new ArrayList<>();
        for (String path : resourcePaths) {
            if (path.endsWith("_reset_data.sql")) {
                int firstIndex = resourcePaths.indexOf(path.replace("reset_", ""));
                if (firstIndex == -1) {
                    pairs.add(new ImmutablePair<String, String>(null, path));
                } else {
                    pairs.add(new ImmutablePair<>(resourcePaths.get(firstIndex), path));
                }
            }
        }
        return pairs;
    }

    private SQLException checkResourcePair(Pair<String, String> pair, String resourceDir) throws IOException {
        // reloadData() adds "/" at the beginning, so we have to remove it first
        String data = pair.getLeft() == null ? null : String.format("%s/%s", resourceDir, pair.getLeft()).substring(1);
        String resetData =
                pair.getRight() == null ? null : String.format("%s/%s", resourceDir, pair.getRight()).substring(1);
        try {
            db().reloadData(resetData, data);
        } catch (SQLException e) {
            return e;
        } finally {
            try {
                db().reloadData(resetData, null);
            } catch (Exception e2) {
                System.out.println("Could not clean data up!");
                e2.printStackTrace();
            }
        }
        return null;
    }


    @Test
    public void test() throws IOException, SQLException {
        List<String> errors = new ArrayList<>();
        List<String> resourceDirs = Arrays.asList("/sql_data/console", "/sql_data/crsweb", "/sql_data/crsscheduler");
        for (String resourceDir : resourceDirs) {
            List<String> resourcePaths = getResourcePathsFromResourceDir(resourceDir);
            List<Pair<String, String>> groupedPaths = groupResourcePaths(resourcePaths);
            for (Pair<String, String> groupedPath : groupedPaths) {
                SQLException e = checkResourcePair(groupedPath, resourceDir);
                if (e != null) {
                    String error = String.format("%s\n%s", e.getMessage(), ExceptionUtils.getStackTrace(e));
                    errors.add(error);
                }
            }
        }
        assertEquals("Some scripts failed to execute properly:\n\n" + StringUtils.join(errors, "\n\n"), 0,
                errors.size());
    }

}

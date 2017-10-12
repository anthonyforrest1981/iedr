package pl.nask.crs.commons;

public class TestOpInfo extends OpInfo {

    public static TestOpInfo DEFAULT = new TestOpInfo("test");

    public TestOpInfo(String userName) {
        super(userName);
    }

    public TestOpInfo(String userName, String remark) {
        super(userName, remark);
    }

    public TestOpInfo(String userName, String superUserName, String remark) {
        super(userName, superUserName, remark);
    }

}

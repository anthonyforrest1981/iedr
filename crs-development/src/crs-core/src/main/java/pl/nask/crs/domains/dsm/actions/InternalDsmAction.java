package pl.nask.crs.domains.dsm.actions;

public class InternalDsmAction {

    private String actionName;
    private String actionParam;
    private boolean executeAfterDsmChange;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }

    public boolean isExecuteAfterDsmChange() {
        return executeAfterDsmChange;
    }

    public void setExecuteAfterDsmChange(boolean executeAfterDsmChange) {
        this.executeAfterDsmChange = executeAfterDsmChange;
    }
}

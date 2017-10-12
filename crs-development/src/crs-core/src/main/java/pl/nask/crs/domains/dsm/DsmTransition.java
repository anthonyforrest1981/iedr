package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.domains.dsm.actions.InternalDsmAction;

public class DsmTransition {
    private static final Logger LOG = Logger.getLogger(DsmTransition.class);
    private int targetState;
    private List<InternalDsmAction> internalActions = new ArrayList<>();

    public void setTargetState(int targetState) {
        this.targetState = targetState;
    }

    public int getTargetState() {
        return targetState;
    }

    public void setInternalActions(List<InternalDsmAction> internalActions) {
        this.internalActions = internalActions;
    }

    public List<DsmAction> getPreActions(DsmActionFactory dsmActionFactory) {
        return getActions(dsmActionFactory, false);
    }

    public List<DsmAction> getPostActions(DsmActionFactory dsmActionFactory) {
        return getActions(dsmActionFactory, true);
    }

    private List<DsmAction> getActions(DsmActionFactory dsmActionFactory, boolean executeAfterDsmChange) {
        List<DsmAction> actions = new ArrayList<>();
        for (InternalDsmAction internalAction : internalActions) {
            if (internalAction.isExecuteAfterDsmChange() == executeAfterDsmChange) {
                DsmAction action = dsmActionFactory.actionFor(internalAction);
                if (action == null) {
                    LOG.error("Couldn't find a DSM action for name: " + internalAction.getActionName());
                    throw new IllegalStateException("No DSM action for name: " + internalAction.getActionName());
                } else {
                    actions.add(action);
                }
            }
        }

        return actions;
    }
}

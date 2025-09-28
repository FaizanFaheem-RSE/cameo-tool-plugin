package kommunikations_plugin;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsManager;
import com.nomagic.magicdraw.actions.MDActionsCategory;

public class DiagramConfiguration implements AMConfigurator {

    private final SPARQLQueryManager diagramAction;

    public DiagramConfiguration(SPARQLQueryManager diagramAction) {
        this.diagramAction = diagramAction;
    }

    @Override
    public void configure(ActionsManager actionsManager) {
    	MDActionsCategory category = new MDActionsCategory("cameo systems modeler plugin", "cameo systems modeler plugin");
        category.addAction(diagramAction);
        actionsManager.addCategory(category);
    }

    @Override
    public int getPriority() {
        return MEDIUM_PRIORITY;
    }
}
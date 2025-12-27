package kommunikations_plugin;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.uml2.ext.magicdraw.activities.mdfundamentalactivities.Activity;


public class SelectedFucntionElementId {

	public static String getSelectedFunctionElementID() {
		String funtionID = null;
		// Getting the active project
		Project project = Application.getInstance().getProject();
		// Retrieve  the first selected model element from the specified SysML model
		PresentationElement selectedElement = project.getActiveDiagram().getSelected().get(0);

		if (selectedElement != null && selectedElement.getElement() instanceof Activity) {
			
                        // Here returing the ID of the selected Activity/Function 

			funtionID = selectedElement.getElement().getID();
		}
		else {

                        // This message will be displayed if a system element other than Activity/Function
 is selected.
			Application.getInstance().getGUILog().showMessage("Please select the Activity/Function from the specified SysML model") ;
		}
		return funtionID;
	}
}
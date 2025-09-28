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
			/* 
			 * Checking if the model element in the specified SysML model is selected by the user. 
			 * It is also checked that the selected model element is Activity/Function 
			 * If both the described conditions are satisfied then the selected function ID will be returned to the SysMLModelDataSaver.java class. The 
			 * associated SysML elements, their properties and associated relationships of the selected function or activity will be stored in the SysMLModelDataSaver.java class.
			 */
			funtionID = selectedElement.getElement().getID();
		}
		else {
			Application.getInstance().getGUILog().showMessage("Please select the Activity/Function from the specified SysML model") ;
		}
		return funtionID;
	}
}
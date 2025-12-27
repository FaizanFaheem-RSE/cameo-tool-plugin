package kommunikations_plugin;
import javax.swing.*;

import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;

public class Kommunikations_Plugin extends Plugin {
	public static boolean initialized;

	@Override
	public void init() {
		createDiagramAction();
	}

	private void createDiagramAction() {
		SPARQLQueryManager action = new SPARQLQueryManager("Potential Failure Analysis", " Potential Failure Analysis");
		DiagramConfiguration configurator = new DiagramConfiguration(action);
		ActionsConfiguratorsManager.getInstance().addAnyDiagramCommandBarConfigurator(configurator);
	}


	@Override
	public boolean close() {
		JOptionPane.showMessageDialog( null, "My Plugin close");
		return true;
	}

	@Override
	public boolean isSupported() {
		return true;
	}
}

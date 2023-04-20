package com.vet.rcp.ficha;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.commands.ICommand;

public class OpenViewFotos extends Action {
	
	
	private final IWorkbenchWindow window;
	private int instanceNum = 0;
	private final String viewId;
	
	public OpenViewFotos(IWorkbenchWindow window, String label, String viewId) {
		this.window = window;
		this.viewId = viewId;
        setText(label);
        // The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_OPEN_FOTOS);
        // Associate the action with a pre-defined command, to allow key bindings.
//		setActionDefinitionId(ICommandIds.CMD_OPEN_FOTOS);
		setImageDescriptor(com.vet.rcp.ficha.Activator.getImageDescriptor("/icons/sample2.gif"));
	}
	
	public void run() {
		if(window != null) {	
			try {
				window.getActivePage().showView(viewId, "com.vet.rcp.ficha.FotosView" , IWorkbenchPage.VIEW_ACTIVATE);

			} catch (PartInitException e) {
				MessageDialog.openError(window.getShell(), "Error", "Error opening view:" + e.getMessage());
			}
		}
	}
}

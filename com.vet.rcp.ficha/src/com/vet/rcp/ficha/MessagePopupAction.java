package com.vet.rcp.ficha;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.vet.rcp.modelo.PacienteSeleccionado;


public class MessagePopupAction extends Action {

    private final IWorkbenchWindow window;

    MessagePopupAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        // The id is used to refer to the action in a menu or toolbar
//        setId(ICommandIds.CMD_OPEN);
        // Associate the action with a pre-defined command, to allow key bindings.
//        setActionDefinitionId(ICommandIds.CMD_OPEN);
        setImageDescriptor(com.vet.rcp.ficha.Activator.getImageDescriptor("/icons/sample3.gif"));
    }

    public void run() {
        MessageDialog.openInformation(window.getShell(), "Open", PacienteSeleccionado.paciente_seleccionado().getPaciente() + 
        		" ID: " + PacienteSeleccionado.paciente_seleccionado().getIdPaciente());
    }
}
package com.vet.rcp.ficha;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class openNuevoPaciente extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		
		DialogNuevoPaciente my = new DialogNuevoPaciente(shell);
		my.open();
		return null;
	}

}

package com.vet.rcp.ficha;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

public class BuscarPacienteView extends ViewPart implements ISelectionListener {
	public static final String ID = "com.vet.rcp.ficha.BuscarPacienteView";
	private FormToolkit toolkit;
//	private Form form;
	private ScrolledForm form;
	Text txtID, txtPropietario, txtPaciente;
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);
		form.setText("Buscar Pacientes");
//		toolkit.decorateFormHeading(form);	// 
		
		TableWrapLayout layout = new TableWrapLayout();
		//GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout);
		layout.numColumns =2;
		
		toolkit.createLabel(form.getBody(), "ID del Paciente:"); //$NON-NLS-1$
		
		txtID = toolkit.createText(form.getBody(), "",  SWT.BORDER);
		TableWrapData td = new TableWrapData(TableWrapData.LEFT);
		txtID.setLayoutData(td);
		
		toolkit.createLabel(form.getBody(), "Propietario:"); //$NON-NLS-1$
		
		txtPropietario = toolkit.createText(form.getBody(), "",  SWT.BORDER);
		td = new TableWrapData(TableWrapData.LEFT);
		txtPropietario.setLayoutData(td);
		
		toolkit.createLabel(form.getBody(), "Paciente:"); //$NON-NLS-1$
		
		txtPaciente = toolkit.createText(form.getBody(), "",  SWT.BORDER);
		td = new TableWrapData(TableWrapData.LEFT);
		txtPaciente.setLayoutData(td);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}

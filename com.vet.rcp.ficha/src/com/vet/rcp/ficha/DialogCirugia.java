package com.vet.rcp.ficha;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.vet.rcp.clases.AdminCirugia;
import com.vet.rcp.clases.AdminInternacion;
import com.vet.rcp.modelo.PacienteSeleccionado;

public class DialogCirugia extends TitleAreaDialog {
	DateTime dtFecha ;
	Text txtDescripcion;

	public DialogCirugia(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	public void create() {
		super.create();
		// Set the title
		setTitle("Cirugias");
		// Set the message
		setMessage("Ingresa la fecha y los comentarios de las intervenciones", 
				IMessageProvider.INFORMATION);

	}
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE );
		composite.setLayout(new GridLayout(2, false));
		
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.horizontalAlignment = GridData.FILL;
		
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblEspecie = new Label(composite, SWT.NONE);
	    lblEspecie.setText("Fecha:");
	    
	    dtFecha = new DateTime(composite, SWT.DROP_DOWN);
	    
	    Label lblTexto = new Label(composite, SWT.NONE);
	    lblTexto.setText("Descripción:");
	    
	    txtDescripcion = new Text(composite, SWT.BORDER);
	    gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = gridData.FILL;
	    txtDescripcion.setLayoutData(gridData);
	    
//	    initGrid();
	    
	    return parent;
	}
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.END;

		parent.setLayoutData(gridData);
		// Create Add button
		// Own method as we need to overview the SelectionAdapter
		createOkButton(parent, OK, "Add", true);
//		createDeleteButton(parent,10 , "Delete", false);
		// Add a SelectionListener
		
		// Create Cancel button
		Button cancelButton = createButton(parent, CANCEL, "Cancel", false);
		// Add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});
	}
	protected Button createOkButton(Composite parent, int id, String label,
			boolean defaultButton) {
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns=1;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (isValidInput()) {
					okPressed();
				}
			}
		});
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		setButtonLayoutData(button);
		return button;	  
	}
	protected Button createDeleteButton(Composite parent, int id, String label,
			boolean defaultButton) {
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns=1;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (isValidInput()) {
					okPressed();
				}
			}
		});
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		setButtonLayoutData(button);
		return button;	  
	}
	protected void okPressed() {
		AdminCirugia adm = new AdminCirugia();
			
		String fecha = (dtFecha.getDay() + "/" + (dtFecha.getMonth()+1) + 
				"/" + dtFecha.getYear());
		
		adm.agregarCirugia(PacienteSeleccionado.paciente_seleccionado(),  
				txtDescripcion.getText(), fecha);
		
		super.okPressed();
	}
	private boolean isValidInput() {
		boolean valid = true;
		if (txtDescripcion.getText().length() == 0) {
			setErrorMessage("Ingrese la descripción");
			valid = false;
		}
		
		return valid;
	}
}
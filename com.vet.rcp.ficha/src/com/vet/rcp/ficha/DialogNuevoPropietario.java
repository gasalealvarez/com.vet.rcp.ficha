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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.vet.rcp.clases.AdminPropietario;
import com.vet.rcp.modelo.Propietario;

public class DialogNuevoPropietario extends TitleAreaDialog {
	Text txtPropietario, txtDireccion, txtTelefono, txtemail;
	Shell shell;

	public DialogNuevoPropietario(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
		shell = parentShell;
	}
	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Propietario");
		// Set the message
		setMessage("Crea o edita los datos del propietario ", 
				IMessageProvider.INFORMATION);
		
	}
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE );
		composite.setLayout(new GridLayout(2, false));

		// The text fields will grow with the size of the dialog
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.horizontalAlignment = GridData.FILL;
		
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label label1 = new Label(composite, SWT.NONE);
		label1.setText("Propietario:");
		
		txtPropietario = new Text(composite, SWT.BORDER);
		txtPropietario.setLayoutData(gridData);
		
		Label lblDireccion = new Label(composite, SWT.NONE);
		lblDireccion.setText("Dirección:");
		
		txtDireccion = new Text(composite, SWT.BORDER);
		txtDireccion.setLayoutData(gridData);
		
		Label lblTe = new Label(composite, SWT.NONE);
		lblTe.setText("Teléfono:");
		
		txtTelefono = new Text(composite, SWT.BORDER);
		gridData = new GridData();
		gridData.widthHint= 150;
		txtTelefono.setLayoutData(gridData);
		
		Label lblEmail = new Label(composite, SWT.NONE);
		lblEmail.setText("Email:");
		
		txtemail = new Text(composite, SWT.BORDER);
		
		txtemail.setLayoutData(gridData);
		
		if (Propietario.getPropietario_seleccionado() != null){
			txtPropietario.setText(Propietario.getPropietario_seleccionado().getPropietario());
			txtDireccion.setText(Propietario.getPropietario_seleccionado().getDireccion());
			txtTelefono.setText(Propietario.getPropietario_seleccionado().getTelefono());
			txtemail.setText(Propietario.getPropietario_seleccionado().getEmail());
		}
			
		return parent;
	}
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.END;
		

		parent.setLayoutData(gridData);
		// Create Add button
		// Own method as we need to overview the SelectionAdapter
		createOkButton(parent, OK, "Add", true);
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
	protected void okPressed() {
		AdminPropietario adm = new AdminPropietario();
		String propietario=txtPropietario.getText();
		String direccion = txtDireccion.getText();
		String 	telefono = txtTelefono.getText();
		String 	mail = txtemail.getText();
	
		if (Propietario.getPropietario_seleccionado()!=null) {
			adm.editarPropietario(Propietario.getPropietario_seleccionado().getIdPropietario(),
					propietario, direccion, mail, telefono);
		} else {
			adm.altaPropietario(propietario, direccion, mail ,telefono);
		}
		
		super.okPressed();
	}
	private boolean isValidInput() {
		boolean valid = true;
		if (txtPropietario.getText().length() == 0) {
			setErrorMessage("Ingrese el nombre del propietario");
			valid = false;
		}
		if (txtDireccion.getText().length() == 0) {
			setErrorMessage("Ingrese la direccion ...");
			valid = false;
		}
		if (txtTelefono.getText().length() == 0) {
			setErrorMessage("Ingrese el numero de telefono");
			valid = false;
		}
		
		return valid;
	}
}

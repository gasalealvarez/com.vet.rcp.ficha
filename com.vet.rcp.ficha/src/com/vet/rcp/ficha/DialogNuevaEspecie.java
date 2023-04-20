package com.vet.rcp.ficha;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ListViewer;
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

import com.vet.rcp.clases.AdminEspecie;

public class DialogNuevaEspecie extends TitleAreaDialog {
	Shell shell;
	Text txtEspecie;
	ListViewer lstEspecie;

	public DialogNuevaEspecie(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
		shell = parentShell;
	}
	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Especie");
		// Set the message
		setMessage("Crea una nueva especie", 
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
		lblEspecie.setText("Especie:");
		
		txtEspecie = new Text(composite, SWT.BORDER);
		txtEspecie.setLayoutData(gridData);
		
		Label lblTodas = new Label(composite, SWT.NONE);
		lblTodas.setText("Todas:");
		
		lstEspecie = new ListViewer(composite, SWT.BORDER);
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		lstEspecie.getList().setLayoutData(gridData);
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
		Button deleteButton = createButton(parent, 10, "Delete", false);
		deleteButton.setEnabled(false);
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
//				if (isValidInput()) {
//					okPressed();
//				}
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
		AdminEspecie adm = new AdminEspecie();
  	  	adm.agregarEspecie(txtEspecie.getText());
		
		super.okPressed();
	}
	private boolean isValidInput() {
		boolean valid = true;
		if (txtEspecie.getText().length() == 0) {
			setErrorMessage("Ingrese el nombre de la especie");
			valid = false;
		}
		
		return valid;
	}
}

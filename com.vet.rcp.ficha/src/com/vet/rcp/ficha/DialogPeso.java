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

import com.vet.rcp.modelo.Plan;

public class DialogPeso extends TitleAreaDialog {
	Text txtPeso;

	public DialogPeso(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Peso");
		// Set the message
		setMessage("Ingrese peso del paciente", 
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
		
		Label lblPeso = new Label(composite, SWT.NONE);
		lblPeso.setText("Peso:");
		
		txtPeso = new Text(composite, SWT.BORDER);
		txtPeso.setLayoutData(gridData);
		
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
	@Override
	protected void okPressed() {
		Plan.setPesoSeleccionado(Double.parseDouble(txtPeso.getText()));
		super.okPressed();
	}
	private boolean isValidInput() {
		boolean valid = true;
		if (txtPeso.getText().length() == 0) {
			setErrorMessage("Por favor complete el peso");
			valid = false;
		}
		return valid;
	}

}
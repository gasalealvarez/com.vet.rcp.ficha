package com.vet.rcp.ficha;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.vet.rcp.modelo.ParametroSeleccionado;

public class MyDialog extends TitleAreaDialog {

	private ComboViewer cboEspecie;
	private Text txtParametro;
	private Text txtMinimo;
	private Text txtMaximo;
	private Text txtUnidad;
	private String firstName;
	private String lastName;
	
	public MyDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Parametro");
		// Set the message
		setMessage("Crea un nuevo parametro y sus correspondientes datos", 
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
		label1.setText("Especie");
		cboEspecie = new ComboViewer(composite, SWT.DROP_DOWN);
		cboEspecie.getCombo().setLayoutData(gridData);
		
		Label lblParametro = new Label(composite, SWT.NONE);
		lblParametro.setText("Parametro:");
		
		txtParametro = new Text(composite, SWT.BORDER);
		txtParametro.setLayoutData(gridData);
		txtParametro.setText(ParametroSeleccionado.ParametroSeleccionado().getParametro());
		
		new Label(composite, SWT.NONE);
		
		
		
			
		Group grpValores = new Group(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		grpValores.setLayout(layout);	
			
		Label lblNormal = new Label(grpValores, SWT.NONE);
		lblNormal.setText("Valor mínimo:");
			
		txtMinimo = new Text(grpValores, SWT.BORDER);
		txtMinimo.setLayoutData(gridData);
			
		Label lblMaximo = new Label(grpValores, SWT.NONE);
		lblMaximo.setText("Valor Máximo:");
			
		txtMaximo = new Text(grpValores, SWT.BORDER);
		txtMaximo.setLayoutData(gridData);
		
		Label lblUnidad = new Label(grpValores, SWT.NONE);
		lblUnidad.setText("Unidad:");
			
		txtUnidad = new Text(grpValores, SWT.BORDER);
		txtUnidad.setLayoutData(gridData);
		
		if (ParametroSeleccionado.ParametroSeleccionado().isTexto()) {
			grpValores.setEnabled(false);
		}
		
		return composite;
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

	private boolean isValidInput() {
		boolean valid = true;
		if (txtMinimo.getText().length() == 0) {
			setErrorMessage("Please maintain the first name");
			valid = false;
		}
		if (txtMaximo.getText().length() == 0) {
			setErrorMessage("Please maintain the last name");
			valid = false;
		}
		return valid;
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}

	// We need to have the textFields into Strings because the UI gets disposed
	// and the Text Fields are not accessible any more.
	private void saveInput() {
		firstName = txtMinimo.getText();
		lastName = txtMaximo.getText();

	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}


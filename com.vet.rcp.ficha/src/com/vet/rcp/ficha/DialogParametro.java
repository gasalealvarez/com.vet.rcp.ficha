package com.vet.rcp.ficha;

import java.util.Collection;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.vet.rcp.clases.AdminNombreParametro;
import com.vet.rcp.clases.AdminPanel;
import com.vet.rcp.clases.AdminPlan;
import com.vet.rcp.modelo.Especie;
import com.vet.rcp.modelo.Item;
import com.vet.rcp.modelo.Panel;
import com.vet.rcp.modelo.PanelSeleccionado;

public class DialogParametro extends TitleAreaDialog {
	Text txtParametro, txtUnidad;
	List lstParametro;
	Button chkTexto, chkValores;
	ComboViewer cboPanel;
	Shell shell;
	

	public DialogParametro(Shell parentShell) {
		super(parentShell);
		shell = parentShell;
			
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Parametro");
		// Set the message
		setMessage("Crea un nuevo parametro" , 
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
		
		Label lblPanel = new Label(composite, SWT.NONE);
		lblPanel.setText("Panel:");
		
		cboPanel = new ComboViewer(composite, SWT.DROP_DOWN);
		cboPanel.getCombo().setLayoutData(gridData);
		
		AdminPanel admPanel = new AdminPanel();
		Collection<Panel> col = admPanel.obtnerPanel();
		
		cboPanel.setContentProvider(new ArrayContentProvider());
		cboPanel.setInput(col);
		
		Label label1 = new Label(composite, SWT.NONE);
		label1.setText("Parametro:");
		
		txtParametro = new Text(composite, SWT.BORDER);
		txtParametro.setLayoutData(gridData);
							
		Label lblUnidad = new Label(composite, SWT.NONE);
		lblUnidad.setText("Unidad:");
		
		txtUnidad = new Text(composite, SWT.BORDER);
//		txtUnidad.setLayoutData(gridData);
		
		Group grp = new Group(composite, SWT.NONE);
		grp.setLayout(new GridLayout(1, true));
		grp.setText("Tipo de Campo");
		gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan=2;
		gridData.horizontalAlignment = GridData.FILL;
		
		grp.setLayoutData(gridData);
		
		chkTexto = new Button(grp, SWT.RADIO);
		chkTexto.setText("Texto");
		chkTexto.setSelection(true);
		
		
		chkValores = new Button(grp, SWT.RADIO);
		chkValores.setText("Numerico");
						
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
		if (txtParametro.getText().length() == 0) {
			setErrorMessage("Ingrese el nombre del parametro");
			valid = false;
		}
		if (txtUnidad.getText().length() == 0) {
			setErrorMessage("Ingrese la unidad");
			valid = false;
		}
		if (cboPanel.getCombo().getSelectionIndex() == -1){
			setErrorMessage("Ingrese el panel");
			valid = false;
		}
		return valid;
	}
	protected void okPressed() {
		
	  	IStructuredSelection panelStruc = (IStructuredSelection)
	  	cboPanel.getSelection();
	  
	  	Panel selPanel = (Panel)panelStruc.getFirstElement();
	  	
		AdminNombreParametro admNombre = new AdminNombreParametro();
  	  	admNombre.nuevoNombreParametro(selPanel, txtParametro.getText(), txtUnidad.getText(), 
  	  			chkTexto.getSelection());
		
		super.okPressed();
	}


}

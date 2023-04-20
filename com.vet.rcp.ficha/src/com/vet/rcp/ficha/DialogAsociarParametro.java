package com.vet.rcp.ficha;

import java.util.Collection;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
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
import com.vet.rcp.clases.AdminNombreParametro;
import com.vet.rcp.clases.AdminParametro;
import com.vet.rcp.modelo.Especie;
import com.vet.rcp.modelo.Item;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.PanelSeleccionado;
import com.vet.rcp.modelo.ParametroSeleccionado;

public class DialogAsociarParametro extends TitleAreaDialog {
	Shell shell;
	Double valueMin, valueMax;
	Text txtMinimo, txtMaximo;
	ComboViewer cboEspecie, cboParametro;
	Collection<Item> _colParametro;
	Collection<Especie> _colEspecie;

	public DialogAsociarParametro(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
		shell = parentShell;
	}
	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Nuevo Parametro asociado");
		// Set the message
		setMessage("Asocia un nuevo parametro a una especie con sus valores minimos y maximos " +
				"si es numerico o de texto", 
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
	    
	    cboEspecie = new ComboViewer(composite, SWT.DROP_DOWN);
	    GridData data = new GridData();
	    data.grabExcessHorizontalSpace= true;
	    data.horizontalAlignment= SWT.BEGINNING;
	    cboEspecie.getCombo().setLayoutData(data);
	    
	    Label lblParametro = new Label(composite, SWT.NONE);
	    lblParametro.setText("Parametro:");   
	    
	    cboParametro = new ComboViewer(composite, SWT.DROP_DOWN);
	    data = new GridData();
	    data.grabExcessHorizontalSpace= true;
	    data.horizontalAlignment= SWT.BEGINNING;
	    cboParametro.getCombo().setLayoutData(data);

	    Label label = new Label(composite, SWT.NULL);
	    label.setText("Valor Min.:");

	    txtMinimo = new Text(composite, SWT.SINGLE | SWT.BORDER);
	    
	    Label lblMaximo = new Label(composite, SWT.NULL);
	    lblMaximo.setText("Valor Max.:");

	    txtMaximo = new Text(composite, SWT.SINGLE | SWT.BORDER);
	    	    
	    initGrid();
	    
	  
	    return parent;
	}
	public void initGrid() {
		AdminEspecie admEspecie = new AdminEspecie();
		_colEspecie= admEspecie.obtenerEspecie();
		
		cboEspecie.setContentProvider(new ArrayContentProvider());
		cboEspecie.setInput(_colEspecie);	
		
		AdminNombreParametro adm = new AdminNombreParametro();
		_colParametro = adm.obtenerNombres();
			
		cboParametro.getCombo().removeAll();
		cboParametro.setContentProvider(new ArrayContentProvider());
		cboParametro.setInput(_colParametro);
		
		
		cboParametro.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection parametroStruc = (IStructuredSelection)
	   	  		cboParametro.getSelection();
	   	  		parametroStruc.getFirstElement();
			
	   	  		Item selParametro = (Item)parametroStruc.getFirstElement();
	   	  		if (selParametro.isTexto()) {
	   	  			txtMinimo.setEnabled(false);
	   	  			txtMaximo.setEnabled(false);
	   	  		}
			}
		});
		
	
				
//		for (Especie especie : _colEspecie) {
//			if (especie.getEspecie().equalsIgnoreCase(
//					PacienteSeleccionado.paciente_seleccionado().getEspecie())) {
//					cboEspecie.setSelection(new StructuredSelection(especie));
//			}
//		}
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
		Double minimo, maximo;
		
		IStructuredSelection parametroStruc = (IStructuredSelection)
   	  		cboParametro.getSelection();
   	  		parametroStruc.getFirstElement();
		
   	  		Item selParametro = (Item)parametroStruc.getFirstElement();
		
   	  	IStructuredSelection especieStruc = (IStructuredSelection)
   	  	cboEspecie.getSelection();
   	  
   	  	Especie selEspecie = (Especie)especieStruc.getFirstElement();
   	  	AdminParametro adm = new AdminParametro();
   	  	
   	  	System.out.println("****" + txtMinimo.getText());
   	  	if (txtMinimo.getText() != "" ){
   	  		minimo= Double.parseDouble(txtMinimo.getText());
   	  	}else{
   	  		minimo=0.0;
   	  	}
   	  	if (txtMaximo.getText() != "" ){
	  		maximo= Double.parseDouble(txtMaximo.getText());
	  	}else{
	  		maximo=0.0;
	  	}
   	 
   	  	if(adm.existeParametroAsociado(selParametro, selEspecie)){
   	  		adm.valorParametro(selParametro, selEspecie, Double.parseDouble(txtMinimo.getText()),
  	  			Double.parseDouble(txtMaximo.getText()));
   	  	} else {
   	  		adm.asociarParametro(selParametro, selEspecie,  minimo, maximo);
   	  	}
		super.okPressed();
	}
	private boolean isValidInput() {
		boolean valid = true;
		if (txtMinimo.getText().length() == 0  && txtMinimo.getEnabled()==true) {
			setErrorMessage("Por favor complete el valor minimo");
			valid = false;
		}
		if (txtMaximo.getText().length()==0 && txtMaximo.getEnabled()==true){
			setErrorMessage("Por favor complete el valor máximo");
			valid = false;
		}
				
		return valid;
	}

}

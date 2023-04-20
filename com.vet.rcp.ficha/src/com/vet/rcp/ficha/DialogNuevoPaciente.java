package com.vet.rcp.ficha;

import java.util.Collection;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
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

import com.vet.rcp.clases.AdminColor;
import com.vet.rcp.clases.AdminEspecie;
import com.vet.rcp.clases.AdminPaciente;
import com.vet.rcp.clases.AdminRaza;
import com.vet.rcp.modelo.Especie;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.Pelaje;
import com.vet.rcp.modelo.Propietario;
import com.vet.rcp.modelo.Raza;

public class DialogNuevoPaciente extends TitleAreaDialog  {
	Shell shell;
	Text txtPaciente;
	DateTime dtfecha;
	ComboViewer cboEspecie, cboRaza, cboPelaje;
	Button opcMacho, opcHembra;
	Propietario propietario;
	Collection<Pelaje> _colPelaje;
	Collection<Especie> _colEspecie;
	Collection<Raza> _colRaza;
	String fecha;
	

	public DialogNuevoPaciente(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
		shell = parentShell;
		
	}
	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Paciente");
		// Set the message
		setMessage("Crea un nuevo paciente de " + Propietario.getPropietario_seleccionado(), 
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
		
		Label lblPaciente = new Label(composite, SWT.NONE);
		lblPaciente.setText("Paciente:");
		
		txtPaciente = new Text(composite, SWT.BORDER);
		txtPaciente.setLayoutData(gridData);
		
		Label lblEspecie = new Label(composite, SWT.NONE);
		lblEspecie.setText("Especie:");
		
		cboEspecie = new ComboViewer(composite, SWT.DROP_DOWN);
		gridData = new GridData();
		gridData.widthHint= 75;
		cboEspecie.getCombo().setLayoutData(gridData);
				
//		Group grpSexo = new Group(composite, SWT.NONE);
//		grpSexo.setLayout(new GridLayout(2, false));
//		grpSexo.setText("Sexo");
//		gridData = new GridData();
//		gridData.horizontalSpan=2;
//		gridData.horizontalAlignment = SWT.BEGINNING;
//		grpSexo.setLayoutData(gridData);
		
		opcMacho = new Button(composite, SWT.RADIO);
		opcMacho.setText("Macho");
		opcMacho.setSelection(true);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		opcMacho.setLayoutData(gridData);
		
		opcHembra= new Button(composite, SWT.RADIO);
		opcHembra.setText("Hembra");
		
		Label lblRaza = new Label(composite, SWT.NONE);
		lblRaza.setText("Raza:");
		
		cboRaza = new ComboViewer(composite, SWT.DROP_DOWN);
		gridData = new GridData();
		gridData.widthHint= 75;
		cboRaza.getCombo().setLayoutData(gridData);
		
		Label lblPelaje = new Label(composite, SWT.NONE);
		lblPelaje.setText("Pelaje:");
		
		cboPelaje = new ComboViewer(composite, SWT.DROP_DOWN);
		cboPelaje.getCombo().setLayoutData(gridData);
		
		Label lblFecha = new Label(composite, SWT.NONE);
		lblFecha.setText("Fecha de Nacimiento:");
		
		dtfecha = new DateTime(composite, SWT.DROP_DOWN);
		
		if (PacienteSeleccionado.paciente_seleccionado() != null) {
			txtPaciente.setText(PacienteSeleccionado.paciente_seleccionado().getPaciente());
		}
				
		initGrid();
		return parent;
	}
	public void initGrid() {
		AdminEspecie admEspecie = new AdminEspecie();
		_colEspecie = admEspecie.obtenerEspecie();
		
		cboEspecie.getCombo().removeAll();
		cboEspecie.setContentProvider(new ArrayContentProvider());
		cboEspecie.setInput(_colEspecie);
		
		AdminRaza admRaza = new AdminRaza();
		_colRaza = admRaza.obtenerRaza();
		
		cboRaza.getCombo().removeAll();
		cboRaza.setContentProvider(new ArrayContentProvider());
		cboRaza.setInput(_colRaza);
		
		AdminColor admColor = new AdminColor();
		_colPelaje = admColor.obtenerPelaje();
		
		cboPelaje.getCombo().removeAll();
		cboPelaje.setContentProvider(new ArrayContentProvider());
		cboPelaje.setInput(_colPelaje);
		
		if (PacienteSeleccionado.paciente_seleccionado() != null){
			if (PacienteSeleccionado.paciente_seleccionado().getIdSexo()== 2) {
				opcHembra.setSelection(true);
				opcMacho.setSelection(false);
			} else {
				opcMacho.setSelection(true);
				opcHembra.setSelection(false);
			}
			for (Especie esp : _colEspecie){
				if (esp.getEspecie().equalsIgnoreCase(PacienteSeleccionado.paciente_seleccionado().getEspecie()) ){
					cboEspecie.setSelection(new StructuredSelection(esp));
				}
			}
			for (Raza raz : _colRaza) {
				if (raz.getRaza().equalsIgnoreCase(PacienteSeleccionado.paciente_seleccionado().getRaza())){
					cboRaza.setSelection(new StructuredSelection(raz));
				}
			}
			
			for  (Pelaje pel : _colPelaje) {
				if (pel.getPelaje().equalsIgnoreCase(PacienteSeleccionado.paciente_seleccionado().getColor())) {
					cboPelaje.setSelection(new StructuredSelection(pel));
				}
			}
			
			fecha=  PacienteSeleccionado.paciente_seleccionado().getFecha();
			
			int intyear = fecha.lastIndexOf("/");
			int intmonth = fecha.indexOf("/");
				
			
			dtfecha.setDate(Integer.parseInt(fecha.substring(intyear+1)), 
					Integer.parseInt(fecha.substring((intmonth+1), intyear))-1,
					Integer.parseInt(fecha.substring(0, intmonth)));
		}
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
		if (PacienteSeleccionado.paciente_seleccionado() == null){
			createOkButton(parent, OK, "Add", true);
		}
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
		AdminPaciente adm = new AdminPaciente();
		int idSexo=0;
		
		if (opcMacho.getSelection()){
			idSexo=1;
		}else{
			idSexo = 2;
		}
		
		String fecha = (dtfecha.getDay() + "/" + (dtfecha.getMonth()+1) + 
				"/" + dtfecha.getYear());
		
		IStructuredSelection especieStruc = (IStructuredSelection)
		cboEspecie.getSelection();
		especieStruc.getFirstElement();
		Especie selEspecie = (Especie)especieStruc.getFirstElement();
		
		IStructuredSelection razaStruc = (IStructuredSelection)
		cboRaza.getSelection();
		razaStruc.getFirstElement();
		Raza selRaza = (Raza)razaStruc.getFirstElement();
		
		IStructuredSelection colorStruc = (IStructuredSelection)
		cboPelaje.getSelection();
		colorStruc.getFirstElement();
		Pelaje selPelaje = (Pelaje)colorStruc.getFirstElement();
		
		adm.altaPaciente(txtPaciente.getText(),
				Propietario.getPropietario_seleccionado().getIdPropietario(),
				idSexo,  fecha,
				selEspecie.getIdEspecie(),
				selRaza.getIdRaza(), 
				selPelaje.getIdPelaje(), "", false);
		
		super.okPressed();
	}
	private boolean isValidInput() {
		boolean valid = true;
		if (txtPaciente.getText().length() == 0) {
			setErrorMessage("Ingrese el nombre del paciente");
			valid = false;
		}
		
		if (cboEspecie.getCombo().getSelectionIndex() == -1){
			setErrorMessage("Ingrese la especie");
			valid = false;
		}
		if (cboRaza.getCombo().getSelectionIndex() == -1){
			setErrorMessage("Ingrese la raza");
			valid = false;
		}
		if (cboPelaje.getCombo().getSelectionIndex() == -1){
			setErrorMessage("Ingrese el color de pelo");
			valid = false;
		}
		return valid;
	}
}

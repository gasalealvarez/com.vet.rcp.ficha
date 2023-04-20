package com.vet.rcp.ficha;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {

	public static final String ID = "com.vet.rcp.ficha.view";
	
	ComboViewer cboPropietario, cboPaciente, cboGrupo,  cboVacuna;
	Button btnResena, btnMedicamento, btnAceptar, btnCancelar, btnImprimir, chkAviso;
	Text txtSerie, txtPeso;
	DateTime dtFecha, dtFechaProxima;
	Table tblPlan;
	boolean bAviso;
	Menu subMenu ;
	MenuItem mnuAviso;

	
	public void createPartControl(Composite parent) {
		Composite top = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		top.setLayout(layout);
		// top banner
		Composite banner = new Composite(top, SWT.NONE);
		banner.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false));
		layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 10;
		layout.numColumns = 2;
		banner.setLayout(layout);
		
		// setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);    
		
		Label l = new Label(banner, SWT.WRAP);
		l.setText("Subject:");
		l.setFont(boldFont);
		l = new Label(banner, SWT.WRAP);
		l.setText("This is a message about the cool Eclipse RCP!");
		
		l = new Label(banner, SWT.WRAP);
		l.setText("From:");
		l.setFont(boldFont);
    
		final Link link = new Link(banner, SWT.NONE);
		link.setText("<a>nicole@mail.org</a>");
		link.addSelectionListener(new SelectionAdapter() {    
			public void widgetSelected(SelectionEvent e) {
//				MessageDialog.openInformation(getSite().getparent(), "Not Implemented", "Imagine the address book or a new message being created now.");
			}    
		});
    
		l = new Label(banner, SWT.WRAP);
		l.setText("Date:");
		l.setFont(boldFont);
		l = new Label(banner, SWT.WRAP);
		l.setText("10:34 am");
		// message contents
		Text text = new Text(top, SWT.MULTI | SWT.WRAP);
		text.setText("This RCP Application was generated from the PDE Plug-in Project wizard. This sample shows how to:\n"+
						"- add a top-level menu and toolbar with actions\n"+
						"- add keybindings to actions\n" +
						"- create views that can't be closed and\n"+
						"  multiple instances of the same view\n"+
						"- perspectives with placeholders for new views\n"+
						"- use the default about dialog\n"+
						"- create a product definition\n");
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		
//		GridLayout gridLayout = new GridLayout (3, false);
//		parent.setLayout (gridLayout);
//				
//		Label lblPropietario =  new Label (parent, SWT.NONE);;
//		lblPropietario.setText("Propietario:");
//		
//		cboPropietario = new ComboViewer(parent, SWT.READ_ONLY);
//		GridData data = new GridData (SWT.BEGINNING, SWT.BEGINNING, false, false, 2, 1);
//		data.widthHint = 150;
//		cboPropietario.getCombo().setLayoutData(data);
//						
//		Label lblPaciente = new Label (parent, SWT.NONE);
//		lblPaciente.setText ("Paciente:");
//		
//		cboPaciente = new ComboViewer (parent, SWT.READ_ONLY);
//		data = new GridData (SWT.BEGINNING, SWT.BEGINNING, false, false, 1, 1);
//		data.widthHint = 150;
//		cboPaciente.getCombo().setLayoutData (data);
//		
//		btnResena = new Button(parent, SWT.PUSH);
//		btnResena.setText("...");
//		btnResena.setToolTipText("Nuevo Paciente");
//		
//		Group grp = new Group(parent, SWT.NONE);
//		GridLayout gridGrp = new GridLayout (6, false);
//		data = new GridData (SWT.FILL, SWT.FILL, false, false, 3, 1);
//		grp.setLayout (gridGrp);
//		grp.setLayoutData(data);
//		
//		Label lblGrupo = new Label(grp, SWT.NONE);
//		lblGrupo.setText("Grupo:");
//		
//		cboGrupo = new ComboViewer(grp, SWT.READ_ONLY);
//		data = new GridData (SWT.BEGINNING, SWT.CENTER, false, false, 5, 1);
//		cboGrupo.getCombo().setLayoutData(data);
//		
//		Label lblVacuna	 = new Label (grp, SWT.NONE);
//		lblVacuna.setText ("Vacuna / Antiparasitarios:");
//		
//		cboVacuna = new ComboViewer (grp, SWT.READ_ONLY);
//		data = new GridData (SWT.BEGINNING, SWT.CENTER, false, false, 1, 1);
//		cboVacuna.getCombo().setLayoutData (data);
//		
//		btnMedicamento = new Button(grp, SWT.PUSH);
//		btnMedicamento.setText("...");
//		btnMedicamento.setToolTipText("Nuevo Medicamento");
//			
//		Label lblSerie = new Label(grp, SWT.NONE);
//		lblSerie.setText("Serie:");
//		
//		txtSerie = new Text(grp, SWT.BORDER);
//		data = new GridData (SWT.BEGINNING, SWT.CENTER, false, false, 2, 1);
//		txtSerie.setLayoutData(data);
//		
//		Label lblFecha = new Label (grp, SWT.NONE);
//		lblFecha.setText ("Fecha de Aplicación:");
//		
//		dtFecha = new DateTime (grp, SWT.BORDER);
//		data = new GridData (SWT.BEGINNING, SWT.CENTER, false, false, 2, 1);
//		dtFecha.setLayoutData (data);
//		
//		Label lblFechaProxima = new Label (grp, SWT.NONE);
//		lblFechaProxima.setText ("Proxima Aplicación:");
//		
//		dtFechaProxima = new DateTime (grp, SWT.BORDER);
//		data = new GridData (SWT.BEGINNING, SWT.CENTER, false, false, 2, 1);
//		dtFechaProxima.setLayoutData (data);
//		
//		Label lblPeso = new Label(grp, SWT.NONE);
//		lblPeso.setText("Peso:");
//		
//		txtPeso = new Text(grp, SWT.BORDER);
//		txtPeso.setLayoutData(data);
//		
//		Label lblAviso = new Label(grp, SWT.NONE);
//		lblAviso.setText("Aviso:");
//		
//		chkAviso = new Button(grp, SWT.CHECK);
//		data = new GridData (SWT.BEGINNING, SWT.CENTER, false, false, 2, 1);
//		chkAviso.setLayoutData(data);
//				
//		Composite cmpte = new Composite(parent, SWT.NONE);
//		cmpte.setLayout(gridLayout);
//		data = new GridData();
//		data.horizontalSpan=3;
//		data.horizontalAlignment= SWT.END;
//		cmpte.setLayoutData(data);
//		
//		btnImprimir = new Button(cmpte, SWT.PUSH);
//		btnImprimir.setText("Imprimir");
//		data= new GridData();
//		data.widthHint = 70;
//		btnImprimir.setLayoutData(data);
//		
//		btnAceptar = new Button (cmpte, SWT.PUSH);
//		btnAceptar.setText ("Aplicar");
//		btnAceptar.setLayoutData(data);
//		
//		btnCancelar = new Button (cmpte, SWT.PUSH);
//		btnCancelar.setText ("Cancelar");
//		btnCancelar.setLayoutData(data);
//		
//		tblPlan = new Table (parent, SWT.BORDER|SWT.FULL_SELECTION);
//		tblPlan.setLinesVisible (true);
//		
//		data = new GridData (SWT.FILL, SWT.FILL, true, true, 4, 1);
//		tblPlan.setLayoutData (data);
//		
//		final String[] titles = { "Vacuna", "Serie", "Fecha" };
//		for (int i = 0; i < titles.length; i++) {
//			TableColumn column = new TableColumn(tblPlan, SWT.NONE);
//			column.setText(titles[i]);
//		}
//		tblPlan.setHeaderVisible(true);
	}

	public void setFocus() {
	}
}

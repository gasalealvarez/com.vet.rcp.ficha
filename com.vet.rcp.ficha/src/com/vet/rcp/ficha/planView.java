package com.vet.rcp.ficha;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.LinePrint;
import net.sf.paperclips.PageDecoration;
import net.sf.paperclips.PageNumber;
import net.sf.paperclips.PageNumberPrint;
import net.sf.paperclips.PagePrint;
import net.sf.paperclips.PaperClips;
import net.sf.paperclips.Print;
import net.sf.paperclips.PrintJob;
import net.sf.paperclips.TextPrint;
import net.sf.paperclips.TextStyle;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.vet.rcp.Exceptions.ExceptionNoSeleccionPaciente;
import com.vet.rcp.clases.AdminGrupo;
import com.vet.rcp.clases.AdminMedicamento;
import com.vet.rcp.clases.AdminPlan;
import com.vet.rcp.modelo.GrupoMedicamento;
import com.vet.rcp.modelo.Medicamento;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.Plan;
import com.vet.rcp.modelo.Propietario;
import com.vet.rcp.provider.contentProviderPlan;
import com.vet.rcp.provider.labelProviderPlan;

public class planView extends ViewPart implements ISelectionListener, ISaveablePart  {
	public static final String ID ="com.vet.rcp.ficha.planView";
	private FormToolkit toolkit;
//	private Form form;
	private ScrolledForm form;
	private ComboViewer cboGrupo;
	private TableViewer tblVacuna;
	private Button chkAviso;
	private Text txtPeso;
	private DateTime dtFecha, dtFechaProxima;
	private TableViewer tblPlan;
	Composite composite;
	Action  addItemGrabar, addItemImprimir, addItemEliminar, addItemActualizar, addItemAgregar;
	Collection<Plan> _colPlan;

	boolean bDirty;
		
	public planView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		composite = parent;
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);
		form.setText("Plan Sanitario");
//		toolkit.decorateFormHeading(form);	// 
		
		TableWrapLayout layout = new TableWrapLayout();
		//GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout);
		layout.numColumns = 4;
				
		TableWrapData td = new TableWrapData();
		td.colspan = 4;
		td.align = TableWrapData.FILL_GRAB;
//		td.grabHorizontal= true;
		td.grabVertical=true;
		
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR|Section.DESCRIPTION);
		section.setText("Grupo de Medicamentos"); //$NON-NLS-1$
		section.setDescription("Detalla el grupo de medicamentos"); //$NON-NLS-1$.
		section.setLayoutData(td);
		section.setExpanded(true);
		Composite sectionGrupo = toolkit.createComposite(section);
		
		GridLayout layoutg = new GridLayout();
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		sectionGrupo.setLayout(layoutg);	
		
		GridData gd = new GridData();
		gd.widthHint=160;
		gd.horizontalSpan=2;
		cboGrupo = new ComboViewer(sectionGrupo, SWT.BORDER|SWT.FULL_SELECTION);
		cboGrupo.getCombo().setLayoutData(gd);
		
		toolkit.createLabel(sectionGrupo, "Si desea crear un nuevo grupo seleccione ");
		
		Hyperlink lnkGrupo = toolkit.createHyperlink(sectionGrupo,"Grupo", SWT.WRAP);
		
		section.setClient(sectionGrupo);
		section.setExpanded(true);
		
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		td.grabHorizontal = true;
		td.grabVertical= true;
		
		Section sectionVacuna = toolkit.createSection(form.getBody(), Section.TITLE_BAR|Section.DESCRIPTION);
		sectionVacuna.setText("Medicamentos"); //$NON-NLS-1$
		sectionVacuna.setDescription("Detalla el principio activo de medicamentos"); //$NON-NLS-1$.
		sectionVacuna.setLayoutData(td);
		sectionVacuna.setExpanded(true);
		
		Composite cpteVacuna = toolkit.createComposite(sectionVacuna);
		layoutg = new GridLayout();
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
//		layoutg.verticalSpacing=2;
		cpteVacuna.setLayout(layoutg);
						
			
		tblVacuna = new TableViewer(cpteVacuna, SWT.BORDER);
		gd = new GridData();
		gd.grabExcessHorizontalSpace= true;
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment= GridData.FILL;
		gd.horizontalSpan=2;
		gd.heightHint=160;
		tblVacuna.getTable().setLayoutData(gd);
		
		StringBuffer buf = new StringBuffer();
		buf.append("<form>");
		buf.append("<p>");
		buf.append("<a nowrap=\"true\">Agregar un nuevo Principio</a>");
		buf.append("</p>");
		buf.append("</form>");
		
		FormText formText = toolkit.createFormText(cpteVacuna, true);
		formText.setWhitespaceNormalized(true);
		gd = new GridData();
		gd.horizontalSpan=2;
		gd.grabExcessHorizontalSpace= true;
		formText.setLayoutData(gd);
		formText.setFont("header", JFaceResources.getHeaderFont());
		formText.setFont("code", JFaceResources.getTextFont());
		formText.setText(buf.toString(), true, false);
		formText.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				IHandlerService handlerService = (IHandlerService) getSite()
				.getService(IHandlerService.class);
				try {
					Object hs=
					handlerService.executeCommand(
							"com.vet.rcp.ficha.OpenNuevoPrincipio", null);
					
					
				} catch (Exception ex) {
					throw new RuntimeException(
					"com.vet.rcp.ficha.OpenNuevoPrincipio not found");
				}
			}
		});
		
		
		sectionVacuna.setClient(cpteVacuna);
		sectionVacuna.setExpanded(true);
		
		Section sectionOpciones = toolkit.createSection(form.getBody(), Section.TITLE_BAR|Section.DESCRIPTION);
		sectionOpciones.setText("Datos de la aplicación"); //$NON-NLS-1$
		sectionOpciones.setDescription("Detalla los datos de aplicación"); //$NON-NLS-1$.
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		td.grabHorizontal = true;
		sectionOpciones.setLayoutData(td);
		sectionOpciones.setExpanded(true);
		
		Composite cpteOpciones = toolkit.createComposite(sectionOpciones);
		layoutg = new GridLayout();
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		cpteOpciones.setLayout(layoutg);
		
		buf = new StringBuffer();
		buf.append("<form>");
		buf.append("<p>");
		buf.append("</p>");
		buf.append("<p>");
		buf.append("Seleccione el link para ingresar la   <a nowrap=\"true\">Serie</a> del medicamento seleccionado.");
		buf.append("</p>");
		buf.append("<p>");
		buf.append("</p>");
		buf.append("</form>");
		
		formText = toolkit.createFormText(cpteOpciones, true);
		formText.setWhitespaceNormalized(true);
		gd = new GridData();
		gd.horizontalSpan=2;
		formText.setLayoutData(gd);
		formText.setFont("header", JFaceResources.getHeaderFont());
		formText.setFont("code", JFaceResources.getTextFont());
		formText.setText(buf.toString(), true, false);
		formText.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
			  IHandlerService handlerService = (IHandlerService) getSite()
				.getService(IHandlerService.class);
				try {
					Object hs=
					handlerService.executeCommand(
							"com.vet.rcp.ficha.openSerie", null);
					
				} catch (Exception ex) {
					throw new RuntimeException(
					"com.vet.rcp.ficha.openSerie not found");
				}
		  }
		 });
			
		toolkit.createLabel(cpteOpciones, "Fecha de Aplicación:");	
		dtFecha = new DateTime (cpteOpciones, SWT.BORDER|SWT.DROP_DOWN);
		gd = new GridData();
		dtFecha.setLayoutData (gd);
		
		toolkit.createLabel(cpteOpciones, "Próxima Aplicación:");
		
		dtFechaProxima = new DateTime (cpteOpciones, SWT.BORDER|SWT.DROP_DOWN);
		dtFechaProxima.setLayoutData (gd);
		
		buf = new StringBuffer();
		buf.append("<form>");
		buf.append("<p>");
		buf.append("</p>");
		buf.append("<p>");
		buf.append("Seleccione el link para ingresar el   <a nowrap=\"true\">Peso</a> del paciente.");
		buf.append("</p>");
		buf.append("<p>");
		buf.append("</p>");
		buf.append("</form>");

		formText = toolkit.createFormText(cpteOpciones, true);
		formText.setWhitespaceNormalized(true);
		gd = new GridData();
		gd.horizontalSpan=2;
		formText.setLayoutData(gd);
		formText.setFont("header", JFaceResources.getHeaderFont());
		formText.setFont("code", JFaceResources.getTextFont());
		formText.setText(buf.toString(), true, false);
		formText.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
			  IHandlerService handlerService = (IHandlerService) getSite()
				.getService(IHandlerService.class);
				try {
					Object hs=
						handlerService.executeCommand(
								"com.vet.rcp.ficha.OpenPeso", null);
				} catch (Exception ex) {
					throw new RuntimeException(
					"com.vet.rcp.ficha.OpenPeso not found");
				}
		  }
		 });
		
		sectionOpciones.setClient(cpteOpciones);
		sectionOpciones.setExpanded(true);
		
		
		ExpandableComposite ec = toolkit.createExpandableComposite(form.getBody(), 
			     ExpandableComposite.TWISTIE|
			     ExpandableComposite.CLIENT_INDENT);
		ec.setText("Agenda");
		Composite sectionClient = toolkit.createComposite(ec);
		
		layoutg = new GridLayout();
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		sectionClient.setLayout(layoutg);	
			
		GridData gridData = new GridData();
		gridData.heightHint=100;
		gridData.grabExcessHorizontalSpace= true;
		gridData.horizontalSpan=2;
		gridData.horizontalAlignment= SWT.FILL;
		chkAviso= toolkit.createButton(sectionClient, "Incluye al paciente dentro de la Agenda", SWT.CHECK);
	
		
		ec.setClient(sectionClient);
		td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.colspan=4;
		ec.setLayoutData(td);
		ec.setExpanded(false);
		ec.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			}
		});
		ec.setExpanded(true);
		
		tblPlan =new TableViewer(form.getBody(), SWT.BORDER);
		td = new TableWrapData(); 
		td.colspan=4;
		td.heightHint = 300;
		td.align = TableWrapData.FILL;
		td.grabHorizontal = true;
		tblPlan.getTable().setLayoutData(td);
		createColumns(tblPlan);		
		tblPlan.setContentProvider(new contentProviderPlan());
		tblPlan.setLabelProvider(new labelProviderPlan());
//		
//		ToolBar bar = new ToolBar(section, SWT.FLAT| SWT.HORIZONTAL);
//		ToolItem item = new ToolItem(bar, SWT.PUSH);
//		item.setText("Eliminar");
//		section.setTextClient(bar);	
//
//		sectionDetalle.setClient(cpteDetalle);
//		section.setExpanded(false);
		
		_colPlan = new ArrayList<Plan>();
		bDirty= false;
		
		getViewSite().getPage().addSelectionListener(this);
		
		createActions();
        createMenu();
        createToolbar();
        cargarGrupo();
        cargarTabla();
    	
		
		cboGrupo.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection medicaStruc = (IStructuredSelection)
		    	  cboGrupo.getSelection();
				medicaStruc.getFirstElement();
				
				GrupoMedicamento selMedica = (GrupoMedicamento)medicaStruc.getFirstElement();
				
				GrupoMedicamento.setGrupo_seleccionado(selMedica);
							
				AdminMedicamento adm = new AdminMedicamento();
				Collection<Medicamento> col= adm.obtenerMedicamento(selMedica);
				
				tblVacuna.getTable().removeAll();
				tblVacuna.setContentProvider(new ArrayContentProvider());
				tblVacuna.setInput(col);
				
			}
		});
	}
	
	public void createActions() {
		addItemAgregar = new Action("Agregar...") {
			public void run () {
				addPlan();
			}
		};
		addItemGrabar = new Action("Grabar...") {
	    	public void run() {   		
	    		savePlan();
	    	}
	    };
	    addItemEliminar = new Action("Eliminar...") {
	    	public void run() {   		
	    		eliminarItem();
	    	}
	    };
	    addItemImprimir = new Action("Imprimir...") {
	    	public void run() {   		
	    		imprimirInforme();
	    	}
	    };
	    addItemActualizar = new Action("Actualizar...") {
	    	public void run() {   		
	    		cargarTabla();
	    	}
	    };
    }
	
	public void addPlan() {
		try {
			IStructuredSelection medicamentoStruc = (IStructuredSelection)
			tblVacuna.getSelection();
			medicamentoStruc.getFirstElement();
			Medicamento selMedicamento = (Medicamento)medicamentoStruc.getFirstElement();
			
			String fecha = (dtFecha.getDay() + "/" + (dtFecha.getMonth()+1) + 
					"/" + dtFecha.getYear());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date fechadf = df.parse(fecha);
			
			String fechaProxima = (dtFechaProxima.getDay() + "/" + (dtFechaProxima.getMonth()+1) + 
					"/" + dtFechaProxima.getYear());
			Date fechaProximadf = df.parse(fechaProxima);
			
			String serie = "" + Plan.getSerieSeleccionada();
			double peso = Plan.getPesoSeleccionado();
			boolean bAviso = chkAviso.getSelection();
			_colPlan.add(new Plan(0, Propietario.getPropietario_seleccionado().getIdPropietario(),
					Propietario.getPropietario_seleccionado().getPropietario(), selMedicamento.getIdMedicamento(),  
					PacienteSeleccionado.paciente_seleccionado().getIdPaciente(), 
					bAviso, false, selMedicamento.getMedicamento(),  serie, fecha, fechaProxima, peso ));
			
			tblPlan.getTable().removeAll();
			tblPlan.setContentProvider(new contentProviderPlan());
			tblPlan.setLabelProvider(new labelProviderPlan());
			tblPlan.setInput(_colPlan);
			bDirty=true;
			
			firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
	
	private void createMenu() {
         IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
         mgr.add(addItemGrabar);
	}
	 private void createToolbar() {
         IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
         mgr.add(addItemAgregar);
         mgr.add(addItemGrabar);
         mgr.add(addItemImprimir);
         mgr.add(addItemEliminar);
         mgr.add(addItemActualizar);
         addItemActualizar.setEnabled(false);
	 }
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	private void eliminarItem(){
		IStructuredSelection planlStruc = (IStructuredSelection)
  	  		tblPlan.getSelection();
		
		
		Plan selPlan = (Plan)planlStruc.getFirstElement();
		AdminPlan adm = new AdminPlan();
		_colPlan.remove(selPlan);
		tblPlan.refresh();
	}
		
	private static void createColumns(final TableViewer viewer) {
		Table table = viewer.getTable();
		table.removeAll();
		
		String[] titles = { "Vacuna", "Serie", "Fecha", "Fecha Proxima", "Peso"};
		int[] bounds = { 200, 80, 80, 80 , 80};

		for (int i = 0; i < titles.length; i++) {
			final int index = i;
			final TableViewerColumn viewerColumn = new TableViewerColumn(
					viewer, SWT.NONE);
			final TableColumn column = viewerColumn.getColumn();
			column.setText(titles[i]);
			column.setWidth(bounds[i]);
			column.setResizable(true);
			column.setMoveable(true);
			// Setting the right sorter
//			column.addSelectionListener(new SelectionAdapter() {
//				@Override
//				public void widgetSelected(SelectionEvent e) {
//					tableSorter.setColumn(index);
//					int dir = viewer.getTable().getSortDirection();
//					if (viewer.getTable().getSortColumn() == column) {
//						dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
//					} else {
//
//						dir = SWT.DOWN;
//					}
//					viewer.getTable().setSortDirection(dir);
//					viewer.getTable().setSortColumn(column);
//					viewer.refresh();
//				}
//			});
//			viewerColumn.setEditingSupport(new PersonEditingSupport(viewer, i));
		}
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}
	public void cargarGrupo() {
		AdminGrupo adm = new AdminGrupo();
		Collection<GrupoMedicamento> col = adm.obtnerGrupo();
		
		cboGrupo.getCombo().removeAll();
		cboGrupo.setContentProvider(new ArrayContentProvider());
		cboGrupo.setInput(col);
	}
	public void savePlan(){
		try {
//			IStructuredSelection medicamentoStruc = (IStructuredSelection)
//				tblVacuna.getSelection();
//			medicamentoStruc.getFirstElement();
//			Medicamento selMedicamento = (Medicamento)medicamentoStruc.getFirstElement();
//			
//			String fecha = (dtFecha.getDay() + "/" + (dtFecha.getMonth()+1) + 
//					"/" + dtFecha.getYear());
//			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//			Date fechadf = df.parse(fecha);
//			
//			String fechaProxima = (dtFechaProxima.getDay() + "/" + (dtFechaProxima.getMonth()+1) + 
//					"/" + dtFecha.getYear());
//			Date fechaProximadf = df.parse(fechaProxima);
//			
//			String serie = "" + Plan.getSerieSeleccionada();
//			double peso = Plan.getPesoSeleccionado();
//			boolean bAviso = chkAviso.getSelection();
//			
//			AdminPlan adm = new AdminPlan();
//			adm.borrarAviso(PacienteSeleccionado.paciente_seleccionado());
//					
//			String tsql = "INSERT INTO plan (idplan, idpaciente, fecha, fechaproxima, " +
//			"ultimadosisvacuna,  no_mostrar, serievacuna, idmedicamento," +
//			"peso) VALUES (DEFAULT, " +  PacienteSeleccionado.paciente_seleccionado().getIdPaciente()+", '" + 
//			fechadf +"', '"+ fechaProximadf
//			+ "', " + true + ", " + false + ", " +
//			serie +", "+ selMedicamento.getIdMedicamento() +", "+
//			peso + ");";
//		
//			Base.executeQuery(tsql);
//				
//			Base.executeQuery("UPDATE paciente SET aviso = " + bAviso + " WHERE " +
//					"idpaciente = " + PacienteSeleccionado.paciente_seleccionado().getIdPaciente());
			
			boolean bAviso = chkAviso.getSelection();
			AdminPlan adm = new AdminPlan();
			adm.borrarAviso(PacienteSeleccionado.paciente_seleccionado());
			
			adm.guardarPlan(_colPlan, PacienteSeleccionado.paciente_seleccionado(), bAviso);
			Plan.setSerieSeleccionada(0);
			Plan.setPesoSeleccionado(0.0);
			cargarTabla();
			bDirty= false;		
			firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
		} catch (Exception e) {
			// TODO: handle exception
		}	
	}
	
	public void cargarTabla() {
		try {
			if (PacienteSeleccionado.paciente_seleccionado() == null){
				MessageDialog.openWarning(getSite().getShell(),"Error","Paciente no seleccionado!!!!");
				throw new ExceptionNoSeleccionPaciente("Error");
			}
						
			AdminPlan adm = new AdminPlan();
			_colPlan = adm.obtenerPlan(PacienteSeleccionado.paciente_seleccionado().getIdPaciente());
			tblPlan.getTable().removeAll();
			tblPlan.setContentProvider(new contentProviderPlan());
			tblPlan.setLabelProvider(new labelProviderPlan());
			tblPlan.setInput(_colPlan);
			
		} catch ( ExceptionNoSeleccionPaciente ex){
			// TODO: handle exception
		} 
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		tblPlan.getTable().removeAll();
		if (PacienteSeleccionado.paciente_seleccionado() != null){
       	 	addItemActualizar.setEnabled(true);
        }
	}
	public void imprimirInforme(){
		try {		
			Display display = Display.getDefault();
		    
			final Shell shell = new Shell( display );
			
			Print print = createPrint();
					
		    PrintDialog dialog = new PrintDialog( shell, SWT.NONE );
	        PrinterData printerData = dialog.open();
	        if ( printerData != null ) {
	        	PaperClips.print(new PrintJob( "printPlan.java", print ).setMargins( 72 ), printerData );
	        }
			
		} catch (Exception e){
    		System.out.println(e);
		}   
	}
	 public static Print createPrint() {
		 	DefaultGridLook look = new DefaultGridLook();
//		 	StyledTextPrint doc = new StyledTextPrint();
		 	TextStyle normal = new TextStyle().font( "Arial", 10, SWT.NORMAL );
		 	TextStyle italic = normal.fontStyle( SWT.ITALIC );
		 	final TextStyle bold = normal.fontStyle( SWT.BOLD );
//		    look.setCellBorder( new LineBorder() );
		    

			AdminPlan adm = new AdminPlan();
			Collection<Plan> colPlan = 
				adm.obtenerPlan(PacienteSeleccionado.paciente_seleccionado().getIdPaciente());
			
			RGB evenRows = new RGB(0xA0, 0xA0, 0xFF); // light blue 
			
//			look.setBodyBackground(evenRows);
			GridPrint grid = new GridPrint("l:150, l:300", new DefaultGridLook(5, 2));  
			
					
			grid.add(new TextPrint("PROPIETARIO:", bold)); grid.add(new TextPrint(
						Propietario.getPropietario_seleccionado().getPropietario()));
			grid.add(new TextPrint("TELEFONO:", bold)); grid.add(new TextPrint(
						Propietario.getPropietario_seleccionado().getTelefono()));
			grid.add(new TextPrint("PACIENTE:", bold)); grid.add(new TextPrint(
						PacienteSeleccionado.paciente_seleccionado().getPaciente()));		
			
			grid.add(new TextPrint(""),2); 
			GridPrint child = new GridPrint( "l:150, l:75, l:75, l:75", look );
			
			child.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
			child.add(new TextPrint("VACUNA", italic)); 
			child.add(new TextPrint("FECHA", italic));
			child.add(new TextPrint("PROXIMA", italic)); 
			child.add(new TextPrint("ETIQUETA", italic), GridPrint.REMAINDER);
			
			child.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER); 
					
			for (Plan a : colPlan) {
				child.add(new TextPrint(a.getVacuna(), normal.fontHeight(9))); 
				child.add(new TextPrint(String.valueOf(a.getFecha()), normal.fontHeight(9))); 
				child.add(new TextPrint(String.valueOf(a.getFechaProxima()), normal.fontHeight(9)));
				child.add(new TextPrint(""));
			}
			
			
			grid.add(SWT.LEFT, child, GridPrint.REMAINDER); 

			PageDecoration footer = new PageDecoration() { 
				public Print createPrint(PageNumber pageNumber) { 
					GridPrint grid = new GridPrint("d:g, d" ); 
					grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
					grid.add(new TextPrint("CENTRO VETERINARIO PEQUEÑOS", bold)); 
					grid.add(new TextPrint("AMENABAR 1498 - TE: 427023", bold )); 
				return grid; 
				} 
			}; 
			 
			 PagePrint page = new PagePrint( grid );
			 page.setFooter( footer );
			 page.setHeaderGap( 5 );

			 return page;

		  }

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return bDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		// TODO Auto-generated method stub
		return true;
	}
}


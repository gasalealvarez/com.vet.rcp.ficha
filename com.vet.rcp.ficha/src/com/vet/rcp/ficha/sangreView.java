package com.vet.rcp.ficha;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import net.sf.paperclips.BreakPrint;
import net.sf.paperclips.CellBackgroundProvider;
import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.EmptyPrint;
import net.sf.paperclips.GridLook;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.LineBorder;
import net.sf.paperclips.LinePrint;
import net.sf.paperclips.PageDecoration;
import net.sf.paperclips.PageNumber;
import net.sf.paperclips.PageNumberPrint;
import net.sf.paperclips.PagePrint;
import net.sf.paperclips.PaperClips;
import net.sf.paperclips.Print;
import net.sf.paperclips.PrintJob;
import net.sf.paperclips.StyledTextPrint;
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
import org.eclipse.jface.viewers.ListViewer;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.vet.rcp.clases.AdminAnalisis;
import com.vet.rcp.clases.AdminPanel;
import com.vet.rcp.clases.AdminParametro;
import com.vet.rcp.modelo.Analisis;
import com.vet.rcp.modelo.Paciente;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.Panel;
import com.vet.rcp.modelo.PanelSeleccionado;
import com.vet.rcp.modelo.Parametro;
import com.vet.rcp.modelo.ParametroSeleccionado;
import com.vet.rcp.modelo.Propietario;
import com.vet.rcp.provider.contentProviderPanel;
import com.vet.rcp.provider.contentProviderSangre;
import com.vet.rcp.provider.labelProviderPanel;
import com.vet.rcp.provider.labelProviderSangre;

public class sangreView extends ViewPart implements  ISelectionListener, ISaveablePart  {
	
	public static final String ID ="com.vet.rcp.ficha.sangreView";
	private FormToolkit toolkit;
//	private Form form;
	private ScrolledForm form;
	ComboViewer cboPropietario, cboPaciente, cboParametro, cboPanel;
	DateTime dtFecha;
	Button btnRegistro, btnReseña, btnParametro, btnNormal, btnGuardar,
	btnEliminar, btnImprimir, btnNuevoPanel, btnNuevoParametro;
	Text txtValor, txtDescripcion, txtPanel;
	
	ListViewer  lstParametro;
	TableViewer tblPanel, tblParametro, tblValores, tblResena;
	Panel selPanel;
	Section sectionParametro;
	ToolItem itemEliminarPanel, itemEditarPanel, itemEliminarParametro, itemEditarParametro;
	Action addItemParametro, addItemPanel, addGrabar, addItemImprimir, addItemNuevo, addItemActualizar;
	Collection<Analisis> _colAnalisis;
	String _fecha;
	boolean bDirty;
	static Analisis selAnalisis;


	public sangreView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);
		form.setText("Analisis Complementarios");
//		toolkit.decorateFormHeading(form);	// 
		
		TableWrapLayout layout = new TableWrapLayout();
		//GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout);
		layout.numColumns =4;	
				
		Section section = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TWISTIE|Section.TITLE_BAR|Section.EXPANDED);
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 4;
		td.grabHorizontal= true;
		section.setLayoutData(td);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText("Historial de analisis"); //$NON-NLS-1$
		section.setDescription("Detalla la fecha y analisis que se practicaron con anterioridad"); //$NON-NLS-1$.
		Composite sectionClient = toolkit.createComposite(section);
		
		GridLayout layoutg = new GridLayout();
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		sectionClient.setLayout(layoutg);	
			
		tblResena = new TableViewer(sectionClient, SWT.BORDER);
		GridData gd = new GridData(GridData.BEGINNING);
		gd.heightHint = 100;
		gd.widthHint = 280;
		tblResena.getTable().setLayoutData(gd);


		ToolBar bar = new ToolBar(section, SWT.FLAT| SWT.HORIZONTAL);
		ToolItem item = new ToolItem(bar, SWT.PUSH);
//		item.setText("Eliminar");
		section.setTextClient(bar);	

		section.setClient(sectionClient);
		section.setExpanded(false);
		
		Section sectionPanel = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TWISTIE|Section.TITLE_BAR|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		td.grabHorizontal = true;
		sectionPanel.setLayoutData(td);
		
		sectionPanel.setText("Panel"); //$NON-NLS-1$
		sectionPanel.setDescription("Lista los paneles disponibles y permite administrarlos"); //$NON-NLS-1$.
		Composite sectionClientPanel = toolkit.createComposite(sectionPanel);
		
		layoutg = new GridLayout();		
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		sectionClientPanel.setLayout(layoutg);
		
		tblPanel = new TableViewer(sectionClientPanel, SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace= true;
//		gd.widthHint = 150;
		gd.heightHint= 100;
		tblPanel.getTable().setLayoutData(gd);
		
//		tblPanel.setContentProvider(new ArrayContentProvider());
//
//		tblPanel.setLabelProvider(new labelProviderPanel());
		
		btnNuevoPanel = toolkit.createButton(sectionClientPanel, "Nuevo", SWT.PUSH);	
		
		bar = new ToolBar(sectionPanel, SWT.FLAT| SWT.HORIZONTAL);
		itemEliminarPanel = new ToolItem(bar, SWT.PUSH);
		itemEliminarPanel.setText("Eliminar");
		itemEditarPanel = new ToolItem(bar, SWT.PUSH);
		itemEditarPanel.setText("Editar");
		sectionPanel.setTextClient(bar);	
		
		sectionPanel.setClient(sectionClientPanel);
		sectionPanel.setExpanded(true);
		
		sectionParametro = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TWISTIE|Section.TITLE_BAR|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		sectionParametro.setLayoutData(td);
		
		sectionParametro.setText("Parámetro"); //$NON-NLS-1$
		sectionParametro.setDescription("Lista los parámetros disponibles y permite administrarlos"); //$NON-NLS-1$.
		Composite sectionClientParametro = toolkit.createComposite(sectionParametro);
		
		layoutg = new GridLayout();		
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		sectionClientParametro.setLayout(layoutg);
		
		tblParametro = new TableViewer(sectionClientParametro);
		gd = new GridData();
		gd.horizontalSpan=2;
		gd.grabExcessHorizontalSpace= true;
		gd.horizontalAlignment = SWT.FILL;
		gd.heightHint = 100;
		tblParametro.getTable().setLayoutData(gd);
		
//		btnNuevoParametro = toolkit.createButton(sectionClientParametro, "Nuevo", SWT.PUSH);
		
		StringBuffer buf = new StringBuffer();
		buf.append("<form>");
		buf.append("<p>");
		buf.append("<a nowrap=\"true\">Agregar un nuevo Parametro</a>");
		buf.append("</p>");
		buf.append("</form>");
		
		FormText formText = toolkit.createFormText(sectionClientParametro, true);
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
							"com.vet.rcp.ficha.openCreateParameters", null);
					
//						txtParametro.setText(hs.toString());
					
				} catch (Exception ex) {
					throw new RuntimeException(
					"de.gaston.rcp.into.mail.OpenCreateParameters not found");
				}
			}
		});
		
		buf = new StringBuffer();
		buf.append("<form>");
		buf.append("<p>");
		buf.append("<a nowrap=\"true\">Ingrese los valores de referencia</a>");
		buf.append("</p>");
		buf.append("</form>");
		   

		formText = toolkit.createFormText(sectionClientParametro, true);
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
							"com.vet.rcp.ficha.OpenAsociarParametro", null);
					
				} catch (Exception ex) {
					throw new RuntimeException(
					"de.gaston.rcp.into.mail.OpenAsociarParametro not found");
				}
			}
		});
		
		bar = new ToolBar(sectionParametro, SWT.FLAT| SWT.HORIZONTAL);
		itemEliminarParametro = new ToolItem(bar, SWT.PUSH);
		itemEliminarParametro.setText("Eliminar");
		itemEditarPanel = new ToolItem(bar, SWT.PUSH);
		itemEditarPanel.setText("Editar");
		sectionParametro.setTextClient(bar);	
		sectionParametro.setClient(sectionClientParametro);
		sectionParametro.setExpanded(true);
		
		toolkit.createLabel(form.getBody(), "Seleccione la fecha de realización:"); //$NON-NLS-1$
		
		td = new TableWrapData(TableWrapData.LEFT);
		td.colspan = 3;
		dtFecha = new DateTime (form.getBody(), SWT.BORDER|SWT.DROP_DOWN);
		dtFecha.setLayoutData(td);

		buf = new StringBuffer();
		buf.append("<form>");
		buf.append("<p>");
		buf.append("</p>");
		buf.append("<p>");
		buf.append("Seleccione el link para ingresar el   <a nowrap=\"true\">resultado</a> del panel seleccionado.");
		buf.append("</p>");
		buf.append("<p>");
		buf.append("</p>");
		buf.append("</form>");

		formText = toolkit.createFormText(form.getBody(), true);
		formText.setWhitespaceNormalized(true);
		td = new TableWrapData(TableWrapData.LEFT);
		td.colspan=4;
		formText.setLayoutData(td);
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
							"com.vet.rcp.ficha.OpenSelectionDialog", null);
					
					cargarTabla();
					
				} catch (Exception ex) {
					throw new RuntimeException(
					"de.gaston.rcp.into.mail.OpenSelectionDialog not found");
				}
		  }
		 });
							
		td = new TableWrapData();
		td.align= TableWrapData.FILL;
		td.colspan=4;
		td.heightHint= 300;
		
		tblValores = new TableViewer(form.getBody(), SWT.BORDER);
		tblValores.getTable().setLayoutData(td);
		createColumns(tblValores);		
		tblValores.setContentProvider(new contentProviderSangre());
		tblValores.setLabelProvider(new labelProviderSangre());
				
		getViewSite().getPage().addSelectionListener(this);
		
		createActions();
        createMenu();
        createToolbar();
        CargarPanel();
        bDirty=false;
        
        _colAnalisis = new ArrayList<Analisis>();
        	
        btnNuevoPanel.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  crearPanel();
		      }
        });
		   	  	
		tblResena.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection analisislStruc = (IStructuredSelection)
		    	  tblResena.getSelection();
				analisislStruc.getFirstElement();
				
				selAnalisis = (Analisis)analisislStruc.getFirstElement();
				_fecha= selAnalisis.getFecha();
				addItemImprimir.setEnabled(true);
				cargarAnalisis(selAnalisis);
			}
		});
		
        tblPanel.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				
				IStructuredSelection panelStruc = (IStructuredSelection)
		    	  tblPanel.getSelection();
				panelStruc.getFirstElement();
				
				selPanel = (Panel)panelStruc.getFirstElement();
				
				PanelSeleccionado ps = new PanelSeleccionado(selPanel); 
			
				if (PacienteSeleccionado.paciente_seleccionado() != null){
					cargarParametro(selPanel);
				} else {
					MessageDialog.openWarning(getSite().getShell(),"Error","Paciente no seleccionado!!!!");
				}
			}
		});
        
        tblParametro.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				
				IStructuredSelection parametroStruc = (IStructuredSelection)
		    	  tblParametro.getSelection();
				parametroStruc.getFirstElement();		
				
				Parametro selParametro = (Parametro)parametroStruc.getFirstElement();
				int idEspecie = PacienteSeleccionado.paciente_seleccionado().getIdEspecie();
				
				ParametroSeleccionado ps = new ParametroSeleccionado(selParametro); 
			}
		});
	}
	

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	
	public void createActions() {
		addItemNuevo = new Action("Nuevo..") {
			public void run() {
				_colAnalisis= null;
				tblValores.getTable().removeAll();
			}
		};
        addItemPanel = new Action("Panel...") {
        	public void run() {
        		crearPanel();
            }
        };
	    addItemParametro = new Action("Parametro...") {
	    	public void run() {   		
//	    		crearParametro();
	    	}
	    };
	    addGrabar = new Action("Grabar....") {
	    	public void run() { 
	    		SaveAnalisis();
	    	}
		};
		addItemImprimir = new Action("Imprimir...") {
			 public void run() {   
		    	imprimirInforme();
			 }
		};  
		addItemActualizar = new Action("Actualizar...") {
			public void run() {
				cargarResena();
			}
		};
   }
	private void createMenu() {
         IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
         mgr.add(addGrabar);
	}
 
 /**
  * Create toolbar.
  */
	 private void createToolbar() {
         IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
         mgr.add(addItemNuevo);
         mgr.add(addItemActualizar);
         mgr.add(addItemImprimir);
         mgr.add(addGrabar);
         addItemImprimir.setEnabled(false);
	 }

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub+
		tblResena.getTable().removeAll();
		tblValores.getTable().removeAll();
		cargarResena();
	}
	public void CargarPanel(){
		AdminPanel adm = new AdminPanel();
		Collection<Panel> col = adm.obtnerPanel();
		
		tblPanel.getTable().removeAll();
		tblPanel.setContentProvider(new contentProviderPanel());
		tblPanel.setLabelProvider(new labelProviderPanel());
		tblPanel.setInput(col);
	}
	
	public void cargarParametro(Panel panel) {
		AdminParametro adm = new AdminParametro();
		
		Collection<Parametro>col = adm.obtenerParametros(panel, 
				PacienteSeleccionado.paciente_seleccionado().getIdEspecie());
		tblParametro.getTable().removeAll();
		tblParametro.setContentProvider(new ArrayContentProvider());
		tblParametro.setInput(col);
	}
	private void cargarTabla(){
		double minimo=ParametroSeleccionado.parametroSeleccionado.getMinimo();
		double maximo = ParametroSeleccionado.parametroSeleccionado.getMaximo();

		int id= ParametroSeleccionado.parametroSeleccionado.getIdParametro();
		String parametro = ParametroSeleccionado.parametroSeleccionado.getParametro();
		String texto="";
		double valor=0;
    	if (ParametroSeleccionado.parametroSeleccionado.isTexto()) {
    	  texto = ParametroSeleccionado.resultado();
    	} else {
    		valor = Double.parseDouble(ParametroSeleccionado.resultado());
    	}
    	_colAnalisis.add(new Analisis(id,parametro,valor , minimo, maximo, texto, 
    			ParametroSeleccionado.parametroSeleccionado.getUnidad(), ParametroSeleccionado.parametroSeleccionado.isTexto()));
    		    
    	tblValores.setInput(_colAnalisis);
    	
    	bDirty=true;
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}
	
	private static void createColumns(final TableViewer viewer) {
		Table table = viewer.getTable();
		table.removeAll();
		
		String[] titles = { "Parametro", "Valor", "Min. Normal", "Max Normal", "Descripción"};
		int[] bounds = { 200, 80, 80, 80, 300 };

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
	public void crearPanel() {
		 IHandlerService handlerService = (IHandlerService) getSite()
			.getService(IHandlerService.class);
			try {
				Object hs=
				handlerService.executeCommand(
						"com.vet.rcp.ficha.openNuevoPanel", null);
				
			} catch (Exception ex) {
				throw new RuntimeException(
				"de.gaston.rcp.into.mail.openNuevoPanel not found");
			}	
	}
	public void SaveAnalisis(){
		AdminAnalisis adm = new AdminAnalisis();
				
		Paciente selPaciente =PacienteSeleccionado.paciente_seleccionado();
		
		String fecha = (dtFecha.getDay() + "/" + (dtFecha.getMonth()+1) + 
				"/" + dtFecha.getYear());
		
		Analisis selAnalisis = adm.altaAnalisis(selPaciente, fecha);
				
		adm.GuardarAnalisis(selAnalisis.getIdAnalisis() , _colAnalisis);
		
		_colAnalisis = null;
		ParametroSeleccionado.resultado="";
		bDirty= false;		
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}
	public void cargarResena() {
		AdminAnalisis adm = new AdminAnalisis();
		Collection<Analisis> col = adm.obtenerAnalisis(PacienteSeleccionado.paciente_seleccionado());
		tblResena.getTable().removeAll();
		tblResena.setContentProvider(new ArrayContentProvider());
		tblResena.setInput(col);
	}
	public void cargarAnalisis(Analisis analisis){
		AdminAnalisis adm = new AdminAnalisis();
		int intyear, intmonth;
		_colAnalisis = adm.loadAnalisis(analisis);
		
		tblValores.getTable().removeAll();
		tblValores.setContentProvider(new ArrayContentProvider());
		tblValores.setInput(_colAnalisis);
				
		intyear = _fecha.lastIndexOf("/");
		intmonth = _fecha.indexOf("/");
			
		
		dtFecha.setDate(Integer.parseInt(_fecha.substring(intyear+1)), 
				Integer.parseInt(_fecha.substring((intmonth+1), intyear))-1,
				Integer.parseInt(_fecha.substring(0, intmonth)));
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
//		monitor = null;
		int total = 3; 
		
		try {	
			monitor.beginTask("Grabando", total);	
			SaveAnalisis();
			monitor.worked(1);			
		}catch (Exception e)	{	
			
		} finally {
			monitor.done()	;
		}
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
		 	StyledTextPrint doc = new StyledTextPrint();
		 	TextStyle normal = new TextStyle().font( "Arial", 10, SWT.NORMAL );
		 	TextStyle italic = normal.fontStyle( SWT.ITALIC );
		 	final TextStyle bold = normal.fontStyle( SWT.BOLD );
		    look.setCellBorder( new LineBorder() );
		    String tpanel="";
		    

			AdminAnalisis adm = new AdminAnalisis();
			Collection<Analisis> colAnalisis = adm.loadAnalisis(selAnalisis);
			
//			RGB evenRows = new RGB(0xA0, 0xA0, 0xFF); // light blue 
			
			GridPrint grid = new GridPrint("l:100, l:300", new DefaultGridLook(5, 2));  
			
			grid.add(new TextPrint("PROPIETARIO:", normal.underline())); grid.add(new TextPrint(
						Propietario.getPropietario_seleccionado().getPropietario(), normal));
			grid.add(new TextPrint("TELEFONO:", normal.underline())); grid.add(new TextPrint(
						Propietario.getPropietario_seleccionado().getTelefono(), normal));
			grid.add(new TextPrint("PACIENTE:", normal.underline())); grid.add(new TextPrint(
						PacienteSeleccionado.paciente_seleccionado().getPaciente(), normal));		
			grid.add(new TextPrint("FECHA:", normal.underline())); grid.add(new TextPrint(
						selAnalisis.getFecha(), normal),  GridPrint.REMAINDER);
			grid.add(new TextPrint(""),2); 
			GridPrint child = new GridPrint( "d,l:150, d, d, d, d", look );
			child.add(new TextPrint("PANEL", bold));
			child.add(new TextPrint("PARAMETRO", bold));
			child.add(new TextPrint("VALOR", bold)); 
			child.add(new TextPrint("MIN.", bold));
			child.add(new TextPrint("MAX.", bold));
			child.add(new TextPrint("UNIDAD.", bold), GridPrint.REMAINDER);
						
			for (Analisis a : colAnalisis) {
			if (a.isTexto() == false) {
					if (a.getpanel().equalsIgnoreCase(tpanel)) {
						child.add(new TextPrint(""));
						
					} else {
						child.add(new TextPrint(a.getpanel(), normal.fontHeight( 8 )));
					}					
					child.add(new TextPrint(a.getItem(), normal)); 
					child.add(new TextPrint(String.valueOf(a.getValor()), bold)); 
					child.add(new TextPrint(String.valueOf(a.getMinimo()), normal));
					child.add(new TextPrint(String.valueOf(a.getMaximo()), normal)); 				
					child.add(new TextPrint(a.getUnidad(),  normal.fontHeight( 8 )));
					
				}else{
					if (a.getpanel().equalsIgnoreCase(tpanel)) {
						child.add(new TextPrint(""));
						
					} else {
						child.add(new TextPrint(a.getpanel(), normal.fontHeight( 8 )));
					}
					child.add(new TextPrint(a.getItem(), normal)); 
					child.add(new TextPrint(String.valueOf(a.getDescripcion()), normal.fontHeight( 8 )),4);
				}
			tpanel= a.getpanel();	
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
}

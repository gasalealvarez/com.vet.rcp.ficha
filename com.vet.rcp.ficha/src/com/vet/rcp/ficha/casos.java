package com.vet.rcp.ficha;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.vet.rcp.clases.AdminCaso;
import com.vet.rcp.clases.AdminCirugia;
import com.vet.rcp.clases.AdminInternacion;
import com.vet.rcp.modelo.Caso;
import com.vet.rcp.modelo.Cirugia;
import com.vet.rcp.modelo.Internacion;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.provider.ContentProviderCirugia;
import com.vet.rcp.provider.LabelProviderCirugia;
import com.vet.rcp.provider.contentProviderCaso;
import com.vet.rcp.provider.contentProviderInternacion;
import com.vet.rcp.provider.contentProviderSangre;
import com.vet.rcp.provider.labelProviderCaso;
import com.vet.rcp.provider.labelProviderInternacion;
import com.vet.rcp.provider.labelProviderSangre;




public class casos extends ViewPart implements  ISelectionListener, ISaveablePart {
	
	public static final String ID = "com.vet.rcp.ficha.casos";
	
	private FormToolkit toolkit;
//	private Form form;
	private ScrolledForm form;
	DateTime dtFecha;
	TableViewer tblResena, tblInternacion, tblCirugia;
	Text txtAntecedentes, txtSintomas, txtDiagnostico, txtTratamiento;
	boolean _editable;
	String fecha;	
	Action addGrabarHistora, addNuevo, addEditar;
	ToolItem itemAgregar, itemCirugia, itemEliminarInternacion, itemEliminarCirugia;
	boolean bDirty;


	public casos() {
		// TODO Auto-generated constructor stub
	
	}
//	private ISelectionListener listener = new ISelectionListener() {
//		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
//			// we ignore our own selections
//			if (sourcepart != casos.this) {
//			    showSelection(sourcepart, selection);
//			}
//		}
//	};
//	public void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
//		setContentDescription(sourcepart.getTitle() + " (" + selection.getClass().getName() + ")");
//		if (selection != null && selection instanceof IStructuredSelection) {
//			Object obj = ((IStructuredSelection) selection).getFirstElement();
//			
//			// If we had a selection lets open the editor
//			if (obj != null) {
//				Paciente person = (Paciente) obj;
//			}
//		}
//	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);
		form.setText("Historia Clinica");
//		toolkit.decorateFormHeading(form);	// 
		TableWrapLayout layout = new TableWrapLayout();
		//GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout);
		layout.numColumns = 2;
				
		TableWrapData td = new TableWrapData();
		td.colspan = 2;
		td.align = TableWrapData.FILL_GRAB;
			
		Section section = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TWISTIE|Section.TITLE_BAR|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		td.grabHorizontal = true;
		section.setLayoutData(td);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText("Historial de Casos"); //$NON-NLS-1$
		section.setDescription("Detalla el historial medico del paciente"); //$NON-NLS-1$.
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
		gd.widthHint = 400;
		tblResena.getTable().setLayoutData(gd);


		ToolBar bar = new ToolBar(section, SWT.FLAT| SWT.HORIZONTAL);
		ToolItem item = new ToolItem(bar, SWT.PUSH);
		item.setText("Eliminar");
		section.setTextClient(bar);	

		section.setClient(sectionClient);
		section.setExpanded(false);
			
		toolkit.createLabel(form.getBody(), "Fecha de realización:"); //$NON-NLS-1$
		
		td = new TableWrapData();
		dtFecha = new DateTime (form.getBody(), SWT.BORDER|SWT.DROP_DOWN);
		dtFecha.setLayoutData(td);
		
				
		ExpandableComposite ec = toolkit.createExpandableComposite(form.getBody(), 
			     ExpandableComposite.TWISTIE|
			     ExpandableComposite.CLIENT_INDENT);
		ec.setText("Antecedentes");
		sectionClient = toolkit.createComposite(ec);
		
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
		txtAntecedentes = new Text(sectionClient, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtAntecedentes.setEditable(false);
		txtAntecedentes.setLayoutData(gridData);
		
		ec.setClient(sectionClient);
		td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.colspan=2;
		ec.setLayoutData(td);
		ec.setExpanded(false);
		ec.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			}
		});

		ec = toolkit.createExpandableComposite(form.getBody(), 
			     ExpandableComposite.TWISTIE|
			     ExpandableComposite.CLIENT_INDENT);
		ec.setText("Sintomas del Caso");
		sectionClient = toolkit.createComposite(ec);
		
		layoutg = new GridLayout();
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		sectionClient.setLayout(layoutg);	
			
		gridData = new GridData();
		gridData.heightHint=100;
		gridData.grabExcessHorizontalSpace= true;
		gridData.horizontalSpan=2;
		gridData.horizontalAlignment= SWT.FILL;
		txtSintomas = new Text(sectionClient,SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtSintomas.setEditable(false);
		txtSintomas.setLayoutData(gridData);
		
		ec.setClient(sectionClient);
		td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.colspan=2;
		ec.setLayoutData(td);
		ec.setExpanded(false);
		ec.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			} 
		});
		
		ec = toolkit.createExpandableComposite(form.getBody(), 
			     ExpandableComposite.TWISTIE|
			     ExpandableComposite.CLIENT_INDENT);
		ec.setText("Diagnosticos");
		sectionClient = toolkit.createComposite(ec);
		
		layoutg = new GridLayout();
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		sectionClient.setLayout(layoutg);	
			
		gridData = new GridData();
		gridData.heightHint=100;
		gridData.grabExcessHorizontalSpace= true;
		gridData.horizontalSpan=2;
		gridData.horizontalAlignment= SWT.FILL;
		txtDiagnostico = new Text(sectionClient, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtDiagnostico.setEditable(false);
		txtDiagnostico.setLayoutData(gridData);
		
		ec.setClient(sectionClient);
		td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.colspan=2;
		ec.setLayoutData(td);
		ec.setExpanded(false);
		ec.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			}
		});
			
		ec = toolkit.createExpandableComposite(form.getBody(), 
			     ExpandableComposite.TWISTIE|
			     ExpandableComposite.CLIENT_INDENT);
		ec.setText("Tratamiento del Caso");
		sectionClient = toolkit.createComposite(ec);
		
		layoutg = new GridLayout();
		layoutg.numColumns = 2;
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		sectionClient.setLayout(layoutg);	
			
		gridData = new GridData();
		gridData.heightHint=100;
		gridData.grabExcessHorizontalSpace= true;
		gridData.horizontalSpan=2;
		gridData.horizontalAlignment= SWT.FILL;
		txtTratamiento = new Text(sectionClient, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtTratamiento.setEditable(false);
		txtTratamiento.setLayoutData(gridData);
		
		ec.setClient(sectionClient);
		td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.colspan=2;
		ec.setLayoutData(td);
		ec.setExpanded(false);
		ec.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
			   form.reflow(true);
			}
		});
			
		Section section1 = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TWISTIE|Section.TITLE_BAR|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		td.grabHorizontal = true;
		section1.setLayoutData(td);
		section1.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section1.setText("Historial de Internaciones u Observaciones"); //$NON-NLS-1$
		section1.setDescription("Detalla el historial de internación"); //$NON-NLS-1$.
		Composite sectionInternacion = toolkit.createComposite(section1);
		
		layoutg = new GridLayout();
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		sectionInternacion.setLayout(layoutg);	
			
		tblInternacion = new TableViewer(sectionInternacion, SWT.BORDER);
		gd = new GridData(GridData.FILL);
		gd.grabExcessHorizontalSpace= true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment= SWT.FILL;
		gd.verticalAlignment= SWT.FILL;
		gd.heightHint=100;
		tblInternacion.getTable().setLayoutData(gd);
		
		createColumns(tblInternacion);	

		ToolBar barInternacion = new ToolBar(section1, SWT.FLAT| SWT.HORIZONTAL);
		itemAgregar = new ToolItem(barInternacion, SWT.PUSH);
		itemAgregar.setText("Agregar");
		itemEliminarInternacion = new ToolItem(barInternacion, SWT.PUSH);
		itemEliminarInternacion.setText("Eliminar");
		section1.setTextClient(barInternacion);	
	
		section1.setClient(sectionInternacion);
		section1.setExpanded(true);
		
		Section section2 = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TWISTIE|Section.TITLE_BAR|Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		td.grabHorizontal = true;
		section2.setLayoutData(td);
		section2.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section2.setText("Historial de Cirugias"); //$NON-NLS-1$
		section2.setDescription("Detalla el historial de Cirugias"); //$NON-NLS-1$.
		Composite sectionCirugia = toolkit.createComposite(section2);
		
		layoutg = new GridLayout();
		layoutg.marginWidth = 2;
		layoutg.marginHeight = 2;
		layoutg.verticalSpacing=2;
		sectionCirugia.setLayout(layoutg);	
		
		tblCirugia = new TableViewer(sectionCirugia, SWT.BORDER);
		gd = new GridData(GridData.FILL);
		gd.grabExcessHorizontalSpace= true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment= SWT.FILL;
		gd.verticalAlignment= SWT.FILL;
		gd.heightHint=100;
		tblCirugia.getTable().setLayoutData(gd);
		
		createColumns(tblCirugia);	
		
		ToolBar barCirugia = new ToolBar(section2, SWT.FLAT| SWT.HORIZONTAL);
		itemCirugia = new ToolItem(barCirugia, SWT.PUSH);
		itemCirugia.setText("Agregar");
		itemEliminarCirugia = new ToolItem(barCirugia, SWT.PUSH);
		itemEliminarCirugia.setText("Eliminar");
		section2.setTextClient(barCirugia);	
		
		section2.setClient(sectionCirugia);
		section2.setExpanded(true);
		
		createActions();
		createToolbar();
		createMenu();
		bDirty=false;
		
		getViewSite().getPage().addSelectionListener(this);
		
		tblResena.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection casoStruc = (IStructuredSelection)
		    	  tblResena.getSelection();
				casoStruc.getFirstElement();
				int intyear, intmonth;
				
				Caso selCaso = (Caso)casoStruc.getFirstElement();
				fecha= selCaso.getFecha();
				txtAntecedentes.setText(selCaso.getAntecedentes());
				txtSintomas.setText(selCaso.getSintomas());
				txtDiagnostico.setText(selCaso.getDiagnostico());
				txtTratamiento.setText(selCaso.getTratamiento());
				
				intyear = fecha.lastIndexOf("/");
				intmonth = fecha.indexOf("/");
					
				
				dtFecha.setDate(Integer.parseInt(fecha.substring(intyear+1)), 
						Integer.parseInt(fecha.substring((intmonth+1), intyear))-1,
						Integer.parseInt(fecha.substring(0, intmonth)));
			}
		});
		txtAntecedentes.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				bDirty=true;
				firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
			}
		});
		txtSintomas.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				bDirty=true;
				firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
			}
		});
		txtDiagnostico.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				bDirty=true;
				firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
			}
		});
		txtTratamiento.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				bDirty=true;
				firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
			}
		});
		itemAgregar.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  IHandlerService handlerService = (IHandlerService) getSite()
					.getService(IHandlerService.class);
					try {
						Object hs=
						handlerService.executeCommand(
								"com.vet.rcp.ficha.OpenInternacion", null);
						cargarInternacion();
						
					} catch (Exception ex) {
						throw new RuntimeException(
						"de.gaston.rcp.into.mail.OpenInternacion not found");
					}
		      }
        });
		itemCirugia.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  IHandlerService handlerService = (IHandlerService) getSite()
					.getService(IHandlerService.class);
					try {
						Object hs=
						handlerService.executeCommand(
								"com.vet.rcp.ficha.OpenCirugia", null);
						cargarCirugias();
						
					} catch (Exception ex) {
						throw new RuntimeException(
						"de.gaston.rcp.into.mail.OpenCirugia not found");
					}
		      }
		});
	}
		
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		cargarHistoria();
		cargarInternacion();
		cargarCirugias();
	}
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	public void createActions() {
	    addGrabarHistora = new Action("Grabar...") {
	    	public void run() { 
	    		SaveHistoria();
	        }	
	    };
	    addEditar = new Action("Editar...") {
	    	public void run() { 
	    		_editable= true;
	    		addEditar.setChecked(_editable);
	    		txtAntecedentes.setEditable(_editable);
	    		txtSintomas.setEditable(_editable);
	    		txtDiagnostico.setEditable(_editable);
	    		txtTratamiento.setEditable(_editable);
	    	}
	    };
	    addNuevo = new Action("Nuevo...") {
			public void run() { 
				_editable = false;
				addEditar.setChecked(_editable);
	    		txtAntecedentes.setEditable(true);
	    		txtAntecedentes.setText("");
	    		txtSintomas.setEditable(true);
	    		txtSintomas.setText("");
	    		txtDiagnostico.setEditable(true);
	    		txtDiagnostico.setText("");
	    		txtTratamiento.setEditable(true);
	    		txtTratamiento.setText("");
	    		txtAntecedentes.setFocus();
			}
		};  
	}
	private void createToolbar() {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        mgr.add(addGrabarHistora);
        mgr.add(addEditar);
        mgr.add(addNuevo);
	}
	private void createMenu() {
	    IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
	    mgr.add(addGrabarHistora);
	    mgr.add(addEditar);
	    mgr.add(addNuevo);
	}
	public void SaveHistoria(){
		AdminCaso adm = new AdminCaso();
		IStructuredSelection resenalStruc = (IStructuredSelection)
  	  		tblResena.getSelection();
		resenalStruc.getFirstElement();
		
		Caso selCaso = (Caso)resenalStruc.getFirstElement();
		
		String fecha = (dtFecha.getDay() + "/" + (dtFecha.getMonth()+1) + 
				"/" + dtFecha.getYear());
		
		if (_editable== false) {
			adm.altaCaso(PacienteSeleccionado.paciente_seleccionado().getIdPaciente(),
					txtAntecedentes.getText(),txtSintomas.getText(),
					txtDiagnostico.getText(), txtTratamiento.getText(), fecha);
		} else {				
			adm.editarCaso(selCaso,  txtAntecedentes.getText(),
					txtSintomas.getText(),
					txtDiagnostico.getText(), txtTratamiento.getText());
		}
		bDirty= false;		
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
		
		cargarHistoria();
	}
	public void cargarInternacion(){
		AdminInternacion adm = new AdminInternacion();
		Collection<Internacion> col = adm.obtenerInternacion(PacienteSeleccionado.paciente_seleccionado());
		
		tblInternacion.getTable().removeAll();
		tblInternacion.setContentProvider(new contentProviderInternacion());
		tblInternacion.setLabelProvider(new labelProviderInternacion());
		tblInternacion.setInput(col);
	}
	public void cargarHistoria(){
		AdminCaso adm = new AdminCaso();
		Collection<Caso> col = adm.obtenerHistorial(PacienteSeleccionado.paciente_seleccionado());
		
		tblResena.getTable().removeAll();
		tblResena.setContentProvider(new contentProviderCaso());
		tblResena.setLabelProvider(new labelProviderCaso());
		tblResena.setInput(col);
	}
	public void cargarCirugias(){
		AdminCirugia adm = new AdminCirugia();
		Collection<Cirugia>col = adm.obtenerCirugias(PacienteSeleccionado.paciente_seleccionado());
		
		tblCirugia.getTable().removeAll();
		tblCirugia.setContentProvider(new ContentProviderCirugia());
		tblCirugia.setLabelProvider(new LabelProviderCirugia());
		tblCirugia.setInput(col);
	}
	private static void createColumns(final TableViewer viewer) {
		Table table = viewer.getTable();
		table.removeAll();
		
		String[] titles = { "Fecha", "Descripción"};
		int[] bounds = {  80, 500 };

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
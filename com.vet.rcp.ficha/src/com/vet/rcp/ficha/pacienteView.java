package com.vet.rcp.ficha;

import java.util.Collection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.vet.rcp.clases.AdminPaciente;
import com.vet.rcp.modelo.Paciente;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.Propietario;
import com.vet.rcp.provider.contentProviderPaciente;
import com.vet.rcp.provider.labelProviderPaciente;

public class pacienteView extends ViewPart implements  ISelectionListener   {
	public static final String ID  = "com.vet.rcp.ficha.paciente";
	private static final Display DISPLAY= Display.getDefault();
	private TableViewer cbo;
	Action addPaciente, deletePaciente, addEspecie, addRaza, addPelaje, addEditar, refresh, bBaja;
	Text text;
		
	// the listener we register with the selection service 
	private ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			// we ignore our own selections
			if (sourcepart != pacienteView.this) {
			    showSelection(sourcepart, selection);
			}
		}
	};


	public void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
		setContentDescription(sourcepart.getTitle() + " (" + selection.getClass().getName() + ")");
		if (selection != null && selection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			
			// If we had a selection lets open the editor
			if (obj != null) {
				Propietario person = (Propietario) obj;
			}
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		GridLayout layout = new GridLayout(1, false);
		parent.setLayout(layout);
			
		cbo = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		getSite().setSelectionProvider(cbo);
		
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		cbo.getControl().setLayoutData(gridData);
		createColumns(cbo);
		cargarPacientes();
		
		getViewSite().getPage().addSelectionListener(this);
		
		createActions();
        createMenu();
        createToolbar();
        createContextMenu();

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		if (selection instanceof IStructuredSelection) {
            Object first = ((IStructuredSelection)selection).getFirstElement();
            if (first instanceof Propietario) {
            		Propietario.setPropietario_seleccionado((Propietario)first);
                    cargarPacientes(first);
            }
            if (first instanceof Paciente) {
            	PacienteSeleccionado ps = new PacienteSeleccionado(((Paciente) first));
            }
           
		}
	}

	private void cargarPacientes(Object first) {
		// TODO Auto-generated method stub
		AdminPaciente adm = new AdminPaciente();
		Collection<Paciente> col = adm.obtenerPaciente(first);
//		cbo.setLabelProvider(new ExampleLabelProvider(boldFont))
		cbo.setInput(col);
				
	}
	public void createActions() {
        deletePaciente = new Action("Borrar") {
        	public void run() { 
        		System.out.println("Seleccionado " + PacienteSeleccionado.paciente_seleccionado().getIdEspecie());
            }
    };
    bBaja = new Action("Baja") {
    	public void run() { 
    		AdminPaciente adm = new AdminPaciente();
    		adm.bajaPaciente(PacienteSeleccionado.paciente_seleccionado());
    	}
    };
    addPaciente = new Action("Paciente") {
    	public void run() { 
    		IHandlerService handlerService = (IHandlerService) getSite()
			.getService(IHandlerService.class);
			try {
				new PacienteSeleccionado(null);
				Object hs=
				handlerService.executeCommand(
						"com.vet.rcp.ficha.openNuevoPaciente", null);
				
				
			} catch (Exception ex) {
				throw new RuntimeException(
				"com.vet.rpc.ficha.openNuevoPaciente not found");
			}
    	}
    };
    addEspecie =  new Action("Especie...") {
    	public void run() {
    		IHandlerService handlerService = (IHandlerService) getSite()
    			.getService(IHandlerService.class);
    			try {
    				Object hs=
    				handlerService.executeCommand(
    						"com.vet.rcp.ficha.openNuevaEspecie", null);
    				
    			} catch (Exception ex) {
    				throw new RuntimeException(
    				"com.vet.rpc.ficha.openNuevaEspecie not found");
    			}
    		
    	}
    };
    addRaza =  new Action("Raza...") {
    	public void run() {
    		IHandlerService handlerService = (IHandlerService) getSite()
    			.getService(IHandlerService.class);
    			try {
    				Object hs=
    				handlerService.executeCommand(
    						"com.vet.rcp.ficha.openNuevaRaza", null);
    				
    				
    			} catch (Exception ex) {
    				throw new RuntimeException(
    				"com.vet.rpc.ficha.openNuevaRaza not found");
    			}                         
    	}
    };
    addPelaje =  new Action("Pelaje...") {
    	public void run() {
    		IHandlerService handlerService = (IHandlerService) getSite()
    			.getService(IHandlerService.class);
    			try {
    				Object hs=
    				handlerService.executeCommand(
    						"com.vet.rcp.ficha.openNuevoPelaje", null);
    				
    				
    			} catch (Exception ex) {
    				throw new RuntimeException(
    				"com.vet.rpc.ficha.openNuevoPelaje not found");
    			}                         
    	}
    };
    addEditar = new Action("Editar") {
    	public void run(){
    		IStructuredSelection pacienteStruc = (IStructuredSelection)
	    	  cbo.getSelection();
    		pacienteStruc.getFirstElement();
			
			Paciente selPaciente = (Paciente)pacienteStruc.getFirstElement();
			
			new PacienteSeleccionado(selPaciente);
			IHandlerService handlerService = (IHandlerService) getSite()
			.getService(IHandlerService.class);
			try {
				Object hs=
				handlerService.executeCommand(
						"com.vet.rcp.ficha.openNuevoPaciente", null);
				
				
			} catch (Exception ex) {
				throw new RuntimeException(
				"com.vet.rpc.ficha.openNuevoPaciente not found");
			}
    	}
	};
    refresh = new Action("Refrescar...") {
    	public void run() {
    		cargarPacientes();
    	}
	};    
       
   }
	private void createMenu() {
         IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
         mgr.add(addPaciente);
         mgr.add(deletePaciente);
         MenuManager menu1 = new MenuManager("Nuevo... ", "1");
         menu1.add(addEspecie);
         menu1.add(addRaza);
         menu1.add(addPelaje);
		 mgr.add(menu1);
	}
 
 /**                   
  * Create toolbar.
  */
	 private void createToolbar() {
         IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
         mgr.add(addPaciente);
         mgr.add(refresh);
         mgr.add(deletePaciente);
         mgr.add(bBaja);
	 }
	 private void createContextMenu() {
         // Create menu manager.
		 MenuManager menuMgr = new MenuManager();
         menuMgr.setRemoveAllWhenShown(true);
         menuMgr.addMenuListener(new IMenuListener() {
                 public void menuAboutToShow(IMenuManager mgr) {
                         fillContextMenu(mgr);
                 }
         });
         
         // Create menu.
         Menu menu = menuMgr.createContextMenu(cbo.getControl());
  
         cbo.getControl().setMenu(menu);
         
         // Register menu for extension.
      getSite().registerContextMenu(menuMgr, cbo);
 }
	 private void fillContextMenu(IMenuManager mgr) {	
		   MenuManager menu1 = new MenuManager("Nuevo Paciente", "1");
		   menu1.add(addPaciente);
		   mgr.add(menu1);
		   mgr.add(addEditar);
    }
	private void cargarPacientes(){
		cbo.getTable().removeAll();
		cbo.setContentProvider(new contentProviderPaciente());
		
		cbo.setLabelProvider(new labelProviderPaciente());
	}
	private static void createColumns(final TableViewer viewer) {
		Table table = viewer.getTable();
		table.removeAll();
		
		String[] titles = { "ID", "Paciente"};
		int[] bounds = { 80,  300 };

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

}

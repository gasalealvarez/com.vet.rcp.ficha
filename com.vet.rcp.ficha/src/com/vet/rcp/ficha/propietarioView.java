package com.vet.rcp.ficha;

import java.util.Collection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.vet.rcp.clases.AdminPropietario;
import com.vet.rcp.modelo.GrupoMedicamento;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.Propietario;
import com.vet.rcp.provider.PersonFilter;
import com.vet.rcp.provider.contentProviderPropietario;
import com.vet.rcp.provider.labelProviderPropietario;

public class propietarioView extends ViewPart {
	public static final String ID = "com.vet.rcp.ficha.propietarioView";
	private TableViewer cbo;
	private PersonFilter filter;
	Action addPropietario, deletePropietario, viewAvisos, refresh;
	MenuItem item;


	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Buscar: ");
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				cbo.refresh();
			}
		});

		cbo = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
//		createColumns(cbo);
		
		Menu menu = new Menu (parent.getShell(), SWT.POP_UP);
		item = new MenuItem (menu, SWT.RADIO);
		item.setText("Editar");
					
		cbo.getTable().setMenu(menu);

		cargarPropietarios();
		
		createActions();
        createMenu();
        createToolbar();
		
		getSite().setSelectionProvider(cbo);
			
		filter = new PersonFilter();

		cbo.addFilter(filter);
			
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		cbo.getControl().setLayoutData(gridData);
		
//		getSite().getPage().addSelectionListener("pacientes.ID", (ISelectionListener)this);
	}

	
	public void createActions() {
        addPropietario = new Action("Crear...") {
        	public void run() {       		
        		Propietario.setPropietario_seleccionado(null);
        		
        		IHandlerService handlerService = (IHandlerService) getSite()
    			.getService(IHandlerService.class);
    			try {
    				Object hs=
    				handlerService.executeCommand(
    						"com.vet.rcp.ficha.OpenNuevoPropietario", null);

    				
    			} catch (Exception ex) {
    				System.out.println(ex);
    				throw new RuntimeException(
    				"com.vet.rcp.ficha.OpenNuevoPropietario not found");
    			}
            }
	    };
	    item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				IStructuredSelection propietarioStruc = (IStructuredSelection)
		    	  cbo.getSelection();
				propietarioStruc.getFirstElement();
							
				Propietario selPropietario = (Propietario)propietarioStruc.getFirstElement();
				
				Propietario.setPropietario_seleccionado(selPropietario);
				
				
				IHandlerService handlerService = (IHandlerService) getSite()
    			.getService(IHandlerService.class);
    			try {
    				Object hs=
    				handlerService.executeCommand(
    						"com.vet.rcp.ficha.OpenNuevoPropietario", null);

    				
    			} catch (Exception ex) {
    				System.out.println(ex);
    				throw new RuntimeException(
    				"com.vet.rcp.ficha.OpenNuevoPropietario not found");
    			}
			}
		});
	    deletePropietario = new Action("Eliminar...") {
	    	public void run() { 
	    		AdminPropietario adm = new AdminPropietario();
	    		adm.eliminarPropietario(Propietario.getPropietario_seleccionado());
	    	}
	    };
	    refresh = new Action("Refrescar...") {
	    	public void run() {
	    		cargarPropietarios();
	    	}
		};
	    viewAvisos = new Action("Avisos...") {
	    	public void run() { 
	    		IHandlerService handlerService = (IHandlerService) getSite()
	    					.getService(IHandlerService.class);
	    					try {
	    						Object hs=
	    						handlerService.executeCommand(
	    								"com.vet.rcp.ficha.OpenAvisos", null);
    						
	    					} catch (Exception ex) {
	    						throw new RuntimeException(
	    						"com.vet.rcp.ficha.OpenAvisos not found");
	    					}
	    		    	}
	    }; 
   }
	private void createMenu() {
         IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
         mgr.add(addPropietario);
         mgr.add(deletePropietario);
         mgr.add(viewAvisos);
	}
	 private void createToolbar() {
         IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
         mgr.add(viewAvisos);
         mgr.add(refresh);
	 }

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {

	}
	
	public propietarioView() {
		// TODO Auto-generated constructor stub
	}
	public void cargarPropietarios(){
		AdminPropietario adm = new AdminPropietario();
		Collection<Propietario> col = adm.obtenerPropietario();
		
		cbo.getTable().removeAll();
		cbo.setContentProvider(new contentProviderPropietario() );
		cbo.setLabelProvider(new labelProviderPropietario());
		cbo.setInput(col);
	}
	private static void createColumns(final TableViewer viewer) {
		Table table = viewer.getTable();
		table.removeAll();
		
		String[] titles = { "Propietario", "Domicilio", "TE"};
		int[] bounds = { 200, 80, 80};

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
	}
}

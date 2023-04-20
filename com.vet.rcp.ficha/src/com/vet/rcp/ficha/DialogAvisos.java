package com.vet.rcp.ficha;

import java.util.Collection;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.vet.rcp.clases.AdminPlan;
import com.vet.rcp.modelo.Plan;
import com.vet.rcp.modelo.Propietario;
import com.vet.rcp.provider.AvisosContetProvider;
import com.vet.rcp.provider.AvisosLabelProvider;

public class DialogAvisos extends TitleAreaDialog {
	static TableViewer table;
	static Plan selPlan;
	public DialogAvisos(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Avisos");
		// Set the message
		setMessage("Muestra las acciones vencidas", 
				IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE );
		composite.setLayout(new GridLayout(2, false));
		
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.heightHint = 180;
				
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		table = new TableViewer(composite, SWT.FULL_SELECTION|SWT.BORDER);
		table.getTable().setLayoutData(gridData);
		createColumns(table);
		
		AdminPlan adm = new AdminPlan();
		Collection<Plan> col = adm.avisos();
		
		table.setContentProvider(new AvisosContetProvider());
		table.setLabelProvider(new AvisosLabelProvider());
		table.setInput(col);
	
		return parent;
	}
	private static void createColumns(final TableViewer viewer) {
		Table table = viewer.getTable();
		String[] titles = { "Vacuna", "Aplicacion", "Recomendada", "Propietario"  };
		int[] bounds = { 100, 100, 100, 180 };

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
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
//					tableSorter.setColumn(index);
					int dir = viewer.getTable().getSortDirection();
					if (viewer.getTable().getSortColumn() == column) {
						dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
					} else {

						dir = SWT.DOWN;
					}
					viewer.getTable().setSortDirection(dir);
					viewer.getTable().setSortColumn(column);
					viewer.refresh();
				}
			});
//			viewerColumn.setEditingSupport(new PersonEditingSupport(viewer, i));
		}
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection planStruc = (IStructuredSelection)
		    	  DialogAvisos.table.getSelection();
				planStruc.getFirstElement();
				
				selPlan = (Plan)planStruc.getFirstElement();
			}
		});

	}

	protected void createButtonsForButtonBar(Composite parent) {
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.END;

		parent.setLayoutData(gridData);
		// Create Add button
		// Own method as we need to overview the SelectionAdapter
		
		Button deleteButton = createButton(parent, 1, "Borrar", false);
		// Add a SelectionListener
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				AdminPlan adm = new AdminPlan();
				adm.ActualizarAviso(selPlan);
				table.refresh();
			}
		});
		Button createOkButton = createButton(parent, OK, "Ok", false);
		createOkButton.addSelectionListener(new SelectionAdapter() {
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
//				if (isValidInput()) {
//					okPressed();
//				}
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
}

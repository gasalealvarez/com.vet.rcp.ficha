package com.vet.rcp.ficha;

import java.util.Collection;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.vet.rcp.clases.AdminEspecie;
import com.vet.rcp.clases.AdminNombreParametro;
import com.vet.rcp.clases.AdminParametro;
import com.vet.rcp.modelo.Especie;
import com.vet.rcp.modelo.Item;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.Parametro;
import com.vet.rcp.modelo.ParametroSeleccionado;

public class valores extends Dialog {
	Double valueMin, valueMax;
	ComboViewer cboEspecie, cboParametro;
	Collection<Item> _colParametro;
	Collection<Especie> _colEspecie;


	public valores(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	public valores(Shell parent, int style) {
	    super(parent, style);
	}

	/**
	   * Makes the dialog visible.
	   * 
	   * @return
	   */
	public void open() {
		Display display = Display.getDefault();
		Shell parent = getParent();
	    final Shell shell =
	      new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL); 
	    
	    shell.setLayout(new GridLayout(2, true));
	    shell.setSize(250, 200);
	    
	    Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);
	    
	    Label lblEspecie = new Label(shell, SWT.NONE);
	    lblEspecie.setText("Especie:");
	    
	    cboEspecie = new ComboViewer(shell, SWT.DROP_DOWN);
	    GridData data = new GridData();
	    data.grabExcessHorizontalSpace= true;
	    data.horizontalAlignment= SWT.FILL;
	    cboEspecie.getCombo().setLayoutData(data);
	    
	    Label lblParametro = new Label(shell, SWT.NONE);
	    lblParametro.setText("Parametro:");   
	    
	    cboParametro = new ComboViewer(shell, SWT.DROP_DOWN);
	    data = new GridData();
	    data.grabExcessHorizontalSpace= true;
	    data.horizontalAlignment= SWT.FILL;
	    cboParametro.getCombo().setLayoutData(data);

	    Label label = new Label(shell, SWT.NULL);
	    label.setText("Valor Min.:");

	    final Text txtMinimo = new Text(shell, SWT.SINGLE | SWT.BORDER);
	    
	    Label lblMaximo = new Label(shell, SWT.NULL);
	    lblMaximo.setText("Valor Max.:");

	    final Text txtMaximo = new Text(shell, SWT.SINGLE | SWT.BORDER);
	    
	    Composite base = new Composite(shell, SWT.NONE);
	    base.setLayout(new GridLayout(2, true));
	    data = new GridData();
	    data.horizontalSpan=2;
	    data.horizontalAlignment = SWT.END;
	    data.grabExcessHorizontalSpace=true;
	    data.grabExcessVerticalSpace= true;
	    base.setLayoutData(data);
	    
	    final Button buttonOK = new Button(base, SWT.PUSH);
	    buttonOK.setText("  Ok  ");
	    data = new GridData();
	    data.horizontalAlignment= GridData.HORIZONTAL_ALIGN_END;
	    data.widthHint= 60;
	    buttonOK.setLayoutData(data);
	    Button buttonCancel = new Button(base, SWT.PUSH);
	    buttonCancel.setText("Cancel");
	    buttonCancel.setLayoutData(data);

	    txtMinimo.addListener(SWT.Modify, new Listener() {
	      public void handleEvent(Event event) {
	        try {
	        	
	          valueMin = new Double(txtMinimo.getText());
//	          buttonOK.setEnabled(true);
	        } catch (Exception e) {
	          buttonOK.setEnabled(false);
	        }
	      }
	    });
	    txtMaximo.addListener(SWT.Modify, new Listener() {
		      public void handleEvent(Event event) {
			        try {
			          valueMax = new Double(txtMaximo.getText());
			          buttonOK.setEnabled(true);
			        } catch (Exception e) {
			          buttonOK.setEnabled(false);
			        }
			      }
			    });
	    buttonOK.addListener(SWT.Selection, new Listener() {
	      public void handleEvent(Event event) {
	    	  IStructuredSelection parametroStruc = (IStructuredSelection)
	    	  cboParametro.getSelection();
	    	  		parametroStruc.getFirstElement();
			
	    	  Item selParametro = (Item)parametroStruc.getFirstElement();
			
	    	  IStructuredSelection especieStruc = (IStructuredSelection)
	    	  	cboEspecie.getSelection();
	    	  
	    	  Especie selEspecie = (Especie)especieStruc.getFirstElement();
	    	  AdminParametro adm = new AdminParametro();
	    	  adm.valorParametro(selParametro, selEspecie, valueMin, valueMax);
	    	  shell.dispose();
	      }
	    });

	    buttonCancel.addListener(SWT.Selection, new Listener() {
	      public void handleEvent(Event event) {
	        valueMin = null;
	        valueMax = null;
	        shell.dispose();
	      }
	    });
	    
	    shell.addListener(SWT.Traverse, new Listener() {
	      public void handleEvent(Event event) {
	        if(event.detail == SWT.TRAVERSE_ESCAPE)
	          event.doit = false;
	      }
	    });

	    txtMinimo.setText("");
	   
	    initGrid();
	    shell.open();

	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }

//	    return value;
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
		
				
		for (Especie especie : _colEspecie) {
			if (especie.getEspecie().equalsIgnoreCase(
					PacienteSeleccionado.paciente_seleccionado().getEspecie())) {
				
				cboEspecie.setSelection(new StructuredSelection(especie));
			}
		}
	}

}

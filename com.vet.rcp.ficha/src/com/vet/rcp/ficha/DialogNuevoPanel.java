package com.vet.rcp.ficha;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DialogNuevoPanel extends TitleAreaDialog {
	Text txtPanel;
	public DialogNuevoPanel(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Panel");
		// Set the message
		setMessage("Crea un nuevo Panel", 
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
		
		Label lblPanel = new Label(composite, SWT.NONE);
		lblPanel.setText("Panel:");
		
		txtPanel = new Text(composite, SWT.BORDER);
		txtPanel.setLayoutData(gridData);
		
		return parent;
	}
	
}

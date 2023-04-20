package com.vet.rcp.provider;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.vet.rcp.clases.AdminPropietario;

public class contentprovider implements IStructuredContentProvider  {
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		AdminPropietario adm = new AdminPropietario();
		return (adm).obtenerPropietario().toArray();  
	}


	
	
}

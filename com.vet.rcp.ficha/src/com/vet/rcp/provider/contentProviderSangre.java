package com.vet.rcp.provider;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.vet.rcp.modelo.Analisis;

public class contentProviderSangre  implements IStructuredContentProvider  {

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
		List<Analisis> persons = extracted(inputElement);
		return persons.toArray();
	}
	private List<Analisis> extracted(Object inputElement) {
		return (List<Analisis>) inputElement;
	}

}

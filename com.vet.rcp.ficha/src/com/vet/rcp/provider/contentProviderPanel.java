package com.vet.rcp.provider;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.vet.rcp.modelo.Panel;
import com.vet.rcp.modelo.Propietario;

public class contentProviderPanel implements IStructuredContentProvider {

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
		List<Panel> persons = extracted(inputElement);
		return persons.toArray();
	}
	private List<Panel> extracted(Object inputElement) {
		return (List<Panel>) inputElement;
	}
}

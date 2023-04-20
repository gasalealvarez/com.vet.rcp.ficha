package com.vet.rcp.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.vet.rcp.modelo.Caso;
import com.vet.rcp.modelo.Internacion;

public class labelProviderInternacion extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		Internacion caso = (Internacion) element;
		switch (columnIndex) {
		case 0:
			return caso.getFecha();
		case 1:
			return caso.getTexto();
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}

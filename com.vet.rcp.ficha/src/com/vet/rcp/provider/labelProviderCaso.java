package com.vet.rcp.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.vet.rcp.modelo.Caso;

public class labelProviderCaso extends LabelProvider  implements
	ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		Caso caso = (Caso) element;
		switch (columnIndex) {
		case 0:
			return caso.getFecha();
		case 1:
			return caso.getDiagnostico();
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}

package com.vet.rcp.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.vet.rcp.modelo.Cirugia;
import com.vet.rcp.modelo.Internacion;

public class LabelProviderCirugia extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		Cirugia cirugia = (Cirugia) element;
		switch (columnIndex) {
		case 0:
			return cirugia.getFecha();
		case 1:
			return cirugia.getCirugia();
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}

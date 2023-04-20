package com.vet.rcp.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.vet.rcp.modelo.Analisis;

public class labelProviderResenaSangre extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
// TODO Auto-generated method stub
		
		Analisis analisis = (Analisis) element;
		switch (columnIndex) {
		case 0:
			return  analisis.getFecha();
		
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}

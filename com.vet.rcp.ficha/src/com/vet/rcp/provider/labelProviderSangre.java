package com.vet.rcp.provider;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;

import com.vet.rcp.modelo.Analisis;

public class labelProviderSangre extends LabelProvider implements ITableLabelProvider, ITableColorProvider  {


	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Color getForeground(Object myObj, int column) {
		Analisis analisis = (Analisis) myObj;
		if (analisis.getValor() > analisis.getMaximo() ) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}


	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		Analisis analisis = (Analisis) element;
		switch (columnIndex) {
		case 0:
			return analisis.getItem();
		case 1:
			return String.valueOf(analisis.getValor());
		case 2:
			return  String.valueOf(analisis.getMinimo());
		case 3:
			return String.valueOf(analisis.getMaximo());
		case 4:
			return analisis.getDescripcion();
		default:
			throw new RuntimeException("Should not happen");
		}
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}

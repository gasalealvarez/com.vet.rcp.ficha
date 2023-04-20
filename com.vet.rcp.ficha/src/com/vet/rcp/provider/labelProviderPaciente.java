package com.vet.rcp.provider;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.vet.rcp.ficha.CodeVerificator;
import com.vet.rcp.modelo.Paciente;
import com.vet.rcp.modelo.PacienteSeleccionado;


public class labelProviderPaciente extends  LabelProvider implements ITableLabelProvider , ITableColorProvider  {
	

	@Override
	public Color getForeground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		Paciente paciente = (Paciente)element;
		if (paciente.isBaja()){
			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	


	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		Paciente paciente = (Paciente)element;
		switch (columnIndex) {
		case 0:
			return String.valueOf(paciente.getIdPaciente() + "-" + new CodeVerificator(paciente.getIdPaciente()));
		case 1:
			return paciente.getPaciente();
				default:
			throw new RuntimeException("Should not happen");
		}
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
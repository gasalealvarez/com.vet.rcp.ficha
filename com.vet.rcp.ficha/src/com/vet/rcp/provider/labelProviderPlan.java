package com.vet.rcp.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.vet.rcp.modelo.Analisis;
import com.vet.rcp.modelo.Plan;

public class labelProviderPlan extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		Plan plan = (Plan) element;
		switch (columnIndex) {
		case 0:
			return plan.getVacuna();
		case 1:
			return String.valueOf(plan.getSerie());
		case 2:
			return  String.valueOf(plan.getFecha());
		case 3:
			return String.valueOf(plan.getFechaProxima());
		case 4: 
			return String.valueOf(plan.getPeso());
		
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}

package com.vet.rcp.provider;

import org.eclipse.jface.viewers.LabelProvider;

import com.vet.rcp.modelo.Propietario;

public class labelprovider extends LabelProvider {
	@Override
	public String getText(Object element) {
//		if (element instanceof Propietario) {
//			Category category = (Category) element;
//			return category.getName();
//		}
		return ((Propietario) element).getPropietario();
	}

}

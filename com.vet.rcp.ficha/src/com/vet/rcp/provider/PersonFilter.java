package com.vet.rcp.provider;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.vet.rcp.modelo.Propietario;

public class PersonFilter extends ViewerFilter {

	private String searchString;

	public void setSearchText(String s) {
		// Search must be a substring of the existing value
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Propietario p = (Propietario) element;
		if (p.getPropietario().matches(searchString)) {
			return true;
		}
		
		return false;
	}
}


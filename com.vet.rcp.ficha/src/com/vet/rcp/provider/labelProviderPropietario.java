package com.vet.rcp.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.vet.rcp.modelo.Propietario;

public class labelProviderPropietario  extends LabelProvider implements ITableLabelProvider {
	private String searchText;
	private Color systemColor;
	

	public labelProviderPropietario() {
		systemColor = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;

	}


	public Image getColumnImage(Object element, int columnIndex) {
		// In case you don't like image just return null here
		
		return null;
	}
	public void update(ViewerCell cell) {
		Propietario element = (Propietario) cell.getElement();
		int index = cell.getColumnIndex();
		String columnText = getColumnText(element, index);
		cell.setText(columnText);
		cell.setImage(getColumnImage(element, index));
		if (searchText != null && searchText.length() > 0) {
			int intRangesCorrectSize[] = SearchUtil.getSearchTermOccurrences(
					searchText, columnText);
			List<StyleRange> styleRange = new ArrayList<StyleRange>();
			for (int i = 0; i < intRangesCorrectSize.length / 2; i++) {
				StyleRange myStyleRange = new StyleRange(0, 0, null,
						systemColor);
				myStyleRange.start = intRangesCorrectSize[i];
				myStyleRange.length = intRangesCorrectSize[++i];
				styleRange.add(myStyleRange);
			}
			cell.setStyleRanges(styleRange.toArray(new StyleRange[styleRange
					.size()]));
		} else {
			cell.setStyleRanges(null);
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		
		Propietario propietario = (Propietario) element;
		switch (columnIndex) {
		case 0:
			return  propietario.getPropietario();
		case 1:
			return  propietario.getDireccion();
		case 2:
			return  propietario.getTelefono();
		
		default:
			throw new RuntimeException("Should not happen");
		}
	}

}
package com.vet.rcp.provider;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import com.vet.rcp.modelo.Analisis;
import com.vet.rcp.modelo.Paciente;

public class labelProviderSangre2 extends StyledCellLabelProvider {
	private static final Display DISPLAY= Display.getDefault();
	private static int IMAGE_SIZE= 16;
	private static final Image IMAGE1= new Image(DISPLAY, DISPLAY.getSystemImage(SWT.ICON_INFORMATION).getImageData().scaledTo(IMAGE_SIZE, IMAGE_SIZE));
	private static final Image IMAGE2= new Image(DISPLAY, DISPLAY.getSystemImage(SWT.ICON_ERROR).getImageData().scaledTo(IMAGE_SIZE, IMAGE_SIZE));

	private final Styler fBoldStyler; 
	
	public labelProviderSangre2() {
		fBoldStyler= new Styler() {
			public void applyStyles(TextStyle textStyle) {
			
			}
		};
	}
	public void update(ViewerCell cell) {
		Object element= cell.getElement();
		
		if (element instanceof Analisis) {
			Analisis analisis = (Analisis)element;
			
			// Multi-font support only works in JFace 3.5 and above (specifically, 3.5 M4 and above).
			// With JFace 3.4, the font information (bold in this example) will be ignored.
			Styler style=  fBoldStyler;
//			cell.setText(analisis.getItem());
			
			
			if (analisis.getValor() > analisis.getMaximo()) {
				cell.setImage(IMAGE2);
			} else {
				cell.setImage(IMAGE1);
			}
		} else {
			cell.setText("Unknown element"); //$NON-NLS-1$
		}

		super.update(cell);
	}
	
	protected void measure(Event event, Object element) {
		super.measure(event, element);
	}
}

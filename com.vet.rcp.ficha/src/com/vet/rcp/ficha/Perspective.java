package com.vet.rcp.ficha;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "com.vet.rcp.ficha.perspective";

	

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);


		IFolderLayout top = layout.createFolder("top", IPageLayout.LEFT, 0.25f, editorArea);
		top.addView(propietarioView.ID);
		
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.65f, "top");
		bottom.addView(pacienteView.ID);
		bottom.addView(BuscarPacienteView.ID);
		
		IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, 1.0f, editorArea);
		right.addPlaceholder(sangreView.ID+":*");
		right.addPlaceholder(FotosView.ID+":*");
		right.addPlaceholder(planView.ID+":*");
		
		layout.setFixed(true);
		
	}
}

package com.vet.rcp.ficha;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.vet.rcp.clases.AdminFoto;
import com.vet.rcp.modelo.PacienteSeleccionado;
import com.vet.rcp.modelo.foto;


public class FotosView extends ViewPart implements  ISelectionListener {
	
	public static final String ID ="com.vet.rcp.ficha.FotosView";
	FormToolkit toolkit;
//	private Form form;
	ScrolledForm form;
	Image sourceImage; /* original image */
	Image screenImage; /* screen image */
	String currentDir=""; /* remembering file open directory */
	Gallery gallery;
//	Map<Integer , String > map ;
	static String filename;
	SWTImageCanvas imageCanvas;
	Text txtComentarios;
	Action addItemFoto,deleteItemFoto, addItemTexto, addFijar, addZoomMas, addZoomMenos, addGrabar; 
	Map <Integer, foto> mapBD; 
//	DateTime dtFecha; 
	
	public FotosView() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void createPartControl(final Composite parent) {
		// TODO Auto-generated method stub
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
		GridData data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.verticalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace= true;
		data.grabExcessVerticalSpace = true;

		imageCanvas = new SWTImageCanvas(parent);
		imageCanvas.setLayoutData(data);
			
//		imageCanvas.onFileOpen("c:/fotos/DSC07962.JPG");
	    gallery = new Gallery(parent, SWT.VIRTUAL|SWT.V_SCROLL);
	    data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.verticalAlignment = SWT.FILL;
		data.verticalSpan=2;
		data.grabExcessVerticalSpace = true;
		data.widthHint = 100;
		gallery.setLayoutData(data);
		
		DefaultGalleryGroupRenderer gr = new DefaultGalleryGroupRenderer();
		gr.setItemSize(90, 90);
		gr.setMinMargin(3);
		DefaultGalleryItemRenderer ir = new DefaultGalleryItemRenderer();

		gallery.setGroupRenderer(gr);
		gallery.setItemRenderer(ir);
		gallery.setAntialias(SWT.ON);
		gallery.setInterpolation(SWT.HIGH);
		
		gallery.setVirtualGroups(true);
		gr.setExpanded(true);
		gallery.setAntialias(SWT.OFF);
		gallery.setInterpolation(SWT.LOW);
		
			
		txtComentarios = new Text(parent, SWT.BORDER);
		data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.heightHint=100;
		txtComentarios.setLayoutData(data);
		txtComentarios.setEditable(false);
		
//		dtFecha = new DateTime(parent, SWT.BORDER);
				
		mapBD =  new HashMap<Integer, foto>();
			
		createActions();
        createMenu();
        createToolbar();
		       
        getViewSite().getPage().addSelectionListener(this);
        
        
		if (PacienteSeleccionado.paciente_seleccionado() != null){	
			loadGallery();
		}
				
		gallery.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				GalleryItem item = (GalleryItem) event.item;
				int index = gallery.indexOf(item);
				if (mapBD.get(index) != null) {
					filename = mapBD.get(index).toString();
					txtComentarios.setText( mapBD.get(index).getDescripcion());
				}
				imageCanvas.onFileOpen(filename);

				

			}
		});
		
		gallery.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event event) {
				GalleryItem item = (GalleryItem) event.item;
				int index;
				
				if (item.getParentItem() != null) {

					index = item.getParentItem().indexOf(item);
					item.setItemCount(0);
				} else {
					index = gallery.indexOf(item);
					if (mapBD.get(index) != null) {
						item.setItemCount(mapBD.size());
					}
				}				

//				if (map.get(index) != null) {
//					org.eclipse.swt.graphics.Image img = new org.eclipse.swt.graphics.Image(parent.getDisplay(), map.get(index).toString());
//					item.setImage(resize(img, img.getBounds().width / 4 ,img.getBounds().height / 4));
//					item.setText(map.get(index)); //$NON-NLS-1$
//					String string =map.get(index);
//					img.dispose();
//				}		
				if (mapBD.get(index) != null) {
					org.eclipse.swt.graphics.Image img = new org.eclipse.swt.graphics.Image(parent.getDisplay(), mapBD.get(index).getPath().toString());
					item.setImage(resize(img, img.getBounds().width / 4 ,img.getBounds().height / 4));
					item.setText(mapBD.get(index).getPath()); //$NON-NLS-1$
					String string = mapBD.get(index).getPath();
					img.dispose();
				}
				item.setExpanded(true);
			}
		});
	}
	public static Image resize(Image image, int width, int height) {
		if (image == null)
	        return null;

	    final Image scaled = new Image(Display.getDefault(), width, height);
	    GC gcRes = new GC(scaled);
	    gcRes.setAntialias(SWT.ON);
	    gcRes.setInterpolation(SWT.HIGH);
	     
	    gcRes.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
	    gcRes.dispose();

	    return scaled;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
//		imageCanvas.setFocus();
		
	}
	
	public void createActions() {
        addItemFoto = new Action("Agregar...") {
        	public void run() {
        		onFileOpen();
            }
        };
	    deleteItemFoto = new Action("Eliminar...") {
	    	public void run() {   		

	    	}
	    };
	    addFijar = new Action("Fijar...") {
	    	public void run() {   		
	    		imageCanvas.fitCanvas();
	    	}
	    };
	    addZoomMas = new Action("Zoom +") {
	    	public void run() {   		
	    		imageCanvas.zoomIn();
	    	}
	    };
	    addZoomMenos = new Action("Zoom -") {
	    	public void run() {   		
	    		imageCanvas.zoomOut();
	    	}
	    };
	    addGrabar = new Action("Grabar") {
	    	public void run() {   		
	    		saveFoto();
	    	}
	    };
	    addItemTexto = new Action("Texto...") {
	    	public void run() {   		
	    		 IHandlerService handlerService = (IHandlerService) getSite()
	 			.getService(IHandlerService.class);
	 			try {
	 				Object hs=
	 				handlerService.executeCommand(
	 						"com.vet.rcp.ficha.OpenDescripcionFoto", null);
	 				txtComentarios.setText(foto.descripcion_Texto);			
	 			} catch (Exception ex) {
	 				throw new RuntimeException(
	 				"de.gaston.rcp.into.mail.OpenDescripcionFoto not found");
	 			}	
	    	}
	    };
	}  
	public void saveFoto() {
		AdminFoto adm = new AdminFoto();
		try {
			String computername=InetAddress.getLocalHost().getHostName();
			String  tstring =  "\\\\" + computername.toUpperCase() + "\\"
					 +  filename.substring(3, filename.length()) ;
			adm.agregarFoto(PacienteSeleccionado.paciente_seleccionado(),tstring , "" + txtComentarios.getText());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadGallery() {
		gallery.setItemCount(0);
		AdminFoto admfoto = new AdminFoto();
		Collection<foto>colFoto = admfoto.obtnerFotos(PacienteSeleccionado.paciente_seleccionado());
		mapBD.clear();
		int c=0;
		gallery.setItemCount(0);
		for (foto foto : colFoto){
			mapBD.put(c++, foto);	
		}
		gallery.setItemCount(1);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		loadGallery();
	}
	private void createMenu() {
        IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
        mgr.add(addItemFoto);
	}

/**
 * Create toolbar.
 */
	 private void createToolbar() {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        mgr.add(addFijar);
        mgr.add(addZoomMas);
        mgr.add(addZoomMenos);
        mgr.add(addItemFoto);
        mgr.add(addItemTexto);
        mgr.add(deleteItemFoto);
        mgr.add(addGrabar);
	 }
	

	public void onFileOpen() {
		Display display = Display.getDefault();
		    
		Shell shell = new Shell( display );
		FileDialog fileChooser = new FileDialog(shell, SWT.OPEN);
		fileChooser.setText("Open image file");
		fileChooser.setFilterPath(currentDir);
		fileChooser.setFilterExtensions(
			new String[] { "*.gif; *.jpg; *.png; *.ico; *.bmp" });
		fileChooser.setFilterNames(
			new String[] { "SWT image" + " (gif, jpeg, png, ico, bmp)" });
		filename = fileChooser.open();
		if (filename != null){
			imageCanvas.onFileOpen(filename);
			currentDir = fileChooser.getFilterPath();
		}
	}

}

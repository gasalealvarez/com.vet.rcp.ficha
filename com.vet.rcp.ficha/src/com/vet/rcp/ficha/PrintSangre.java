package com.vet.rcp.ficha;

import java.awt.print.PrinterJob;

public class PrintSangre {
	public PrintSangre() {
		String path = "C:/Products/Reportes/Analisis.html";
		System.out.println("Ingresando a print...");
	
		boolean showPrintDialog=true;
		PrinterJob printJob = PrinterJob.getPrinterJob ();
		printJob.setJobName (path);
		try {
			if (showPrintDialog) {
				if (printJob.printDialog()) {
					printJob.print();
					System.out.println("ingresando en show y en trabajo");
				}
				System.out.println("ingresando en show");
			}
			else
				printJob.print ();
				System.out.println(" NOOOO ingresando en show");
			} catch (Exception PrintException) {
				PrintException.printStackTrace();
				System.out.println(PrintException.getMessage());
			}
		}
}

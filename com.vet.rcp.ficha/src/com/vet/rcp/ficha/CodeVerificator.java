package com.vet.rcp.ficha;


public class CodeVerificator {

	/**
	 * @param args
	 */
	int suma;
	public CodeVerificator(final int id){
		String valor = String.valueOf(id);
		suma =0;
		int valor1= 0;
	
		for (int cuenta =0; cuenta < valor.length(); ++cuenta ){	
			suma = suma + Integer.parseInt(valor.substring( cuenta ,cuenta+1  ));
		}
		valor1=suma;
		do {
			valor1= suma;
			suma=0;
			for (int cuenta =0; cuenta < String.valueOf(valor1).length()   ; ++cuenta ){	
				suma= suma +  Integer.parseInt( String.valueOf(valor1).substring( cuenta ,cuenta+1  ));
			}
		} while (String.valueOf(suma).length() > 1);	
	}
	public int id (){
		return suma;
	}
	@Override
	public String toString(){
		return  String.valueOf(this.suma) ;
	}
}

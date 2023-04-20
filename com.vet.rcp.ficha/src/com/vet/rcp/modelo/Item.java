package com.vet.rcp.modelo;

public class Item {
	int idItem;
	String item;
	boolean isTexto;
	
	
	public Item(int idItem, String item, boolean isTexto) {
		super();
		this.idItem = idItem;
		this.item = item;
		this.isTexto = isTexto;
	}
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	@Override
	public String toString() {
		return this.item;
	}
	public boolean isTexto() {
		return isTexto;
	}
	public void setTexto(boolean isTexto) {
		this.isTexto = isTexto;
	}
	
}

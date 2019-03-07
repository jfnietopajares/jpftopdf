package com.jnieto.pojo;

public class ParametroCfg {
	public String nombre;
	public String valor;
	
	public ParametroCfg () {}
	
	public  ParametroCfg(String nombre,String valor) {
		this.setNombre(nombre);
		this.setValor(valor);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}

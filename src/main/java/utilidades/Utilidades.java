package com.jnieto.jpegtopef;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utilidades {
	public static final Logger logger = LogManager.getLogger(Constantes.class);
	
	public static String getTraceException(Exception ex) {
		String mensaje = Constantes.NEWLINE ;
		for (StackTraceElement elemnt : ex.getStackTrace()) {
			mensaje += elemnt.toString() + Constantes.NEWLINE;
		}
		return mensaje;
	}

	public static void doMuestraTextoAyuda(Properties miPr) {
		System.out.println(Constantes.AYUDAUSO);

		Enumeration<Object> keys = miPr.keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			System.out.println(key + " = " + miPr.get(key));
		}
		System.out.println(Constantes.NEWLINE + Constantes.NEWLINE);
	}
}

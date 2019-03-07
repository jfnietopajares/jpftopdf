package com.jnieto.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 
 * @author JuanNieto
 * @version 27.02.2019
 * Clase Utilidades con métodos de propósito general.
 * Normalmente los métodos son estáticos.
 *
 */
public class Utilidades {
	public static final Logger logger = LogManager.getLogger(Constantes.class);
/**
 * 
 * @param ex La excepcion que se quiere trazar
 * @return la traza de la excepcion
 */
	public static String getTraceException(Exception ex) {
		String mensaje = Constantes.NEWLINE;
		for (StackTraceElement elemnt : ex.getStackTrace()) {
			mensaje += elemnt.toString() + Constantes.NEWLINE;
		}
		 mensaje += Constantes.NEWLINE;
		return mensaje;
	}
   /**
    * Muestra ayuda de las opciones de llamada y una descripcion del funcionamiento
    * y de los valores de configuracion
    * @param miPr Es el objeto properties con lo valores actules de cada parámetro del programa
    */
	public static void doMuestraTextoAyuda(Properties miPr, String directorioTrabjo) {
		System.out.println(Constantes.AYUDAUSO);
		System.out.println(
				"----------------------------------------------------------------------------" );
		System.out.println("           Directorio de trabajo actual : " + directorioTrabjo);
		System.out.println(
				"----------------------------------------------------------------------------" );
		System.out.println(" Valores actuales de configuración " + Constantes.NEWLINE);
		System.out.println(
				"----------------------------------------------------------------------------" );
		Enumeration<Object> keys = miPr.keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			System.out.println(key + " = " + miPr.get(key));
		}
		System.out.println(
				"----------------------------------------------------------------------------");
		System.out.println(Constantes.NEWLINE + Constantes.NEWLINE);
	}
	
   public static String getFechaHora () {
	  return " "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " ";
   }
}

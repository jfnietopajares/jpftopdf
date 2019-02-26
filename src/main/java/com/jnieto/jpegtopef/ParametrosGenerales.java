package com.jnieto.jpegtopef;



/**
 *
 * Si el fichero no exite, lo crea con los valores por defecto
 * 
 *  @author: Juan Nieto
 *  @param :pr Objeto tipo properties
 *  @return void. Carga en el objeto properties los valores del fichero 
 *  @version: 26/02/2019 Version 1.0

*/

import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pojo.ParametroCfg;

/**
 * 
 * @author JuanNieto
 * @version 26/02/2019 Gestión de parámetros generales
 * @return Properties getParametros() Clase ParametrosGenerales Si existe el
 *         fichero Constantes.NOMBREFICHEROPROPERTEIS en el directorio de
 *         trabajo recupera los valores si no existe crea el fichero con los
 *         valores por defecto puestos a puñetazos en el método
 */

public class ParametrosGenerales {
	/**
	 * ficheroPropierties miPr nombre del fichero de properties
	 */
	public Properties miPr = new Properties();

	public static final Logger logger = LogManager.getLogger(ParametrosGenerales.class);
	
	/**
	 * Constructor: 
	 */
	public ParametrosGenerales() {
		FileInputStream is = null;
		File fconfig = new File(Constantes.CONFIGURACIONNOMBREFICHERO);
		try {
			if (fconfig.exists() && fconfig.isFile()) {
				is = new FileInputStream(fconfig);
				miPr.load(is);
				logger.info(Constantes.MSGCONFIGURACIONLEEFICHERO + " " + Constantes.CONFIGURACIONNOMBREFICHERO);
			} else {
				creaFichero();
			}

		} catch (IOException e) {
			logger.error(Constantes.MSGCONFIGURACIONERRORLEEFICHERO + " " + Constantes.CONFIGURACIONNOMBREFICHERO);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	/**
	 * 
	 * @return el objeto properties con los valores
	 */
	public Properties getParametros() {
		return miPr;
	}

	/**
	 * 
	 * @throws IOException
	 *             Carga los valores por defecto en el objeto properties y lo
	 *             almacena en el fichero Constantes.CONFIGURACIONNOMBREFICHERO)
	 */
	public void creaFichero() throws IOException {
		FileOutputStream salida = null;
		ArrayList<ParametroCfg> listaParametros = new ArrayList<>();
			listaParametros.add(new ParametroCfg("mailhost", "outlook.office365.com"));
			listaParametros.add(new ParametroCfg("mailstarttls", "true"));
			listaParametros.add(new ParametroCfg("mailport", "25"));
			listaParametros.add(new ParametroCfg("mailemisor", "jnieto@saludcastillayleon.es"));
			listaParametros.add(new ParametroCfg("mailuser", "06551256M@saludcastillayleon.es"));
			listaParametros.add(new ParametroCfg("mailpasswd", "00000jjjjj"));
			listaParametros.add(new ParametroCfg("mailauth", "true"));
			listaParametros.add(new ParametroCfg("maildestinatarios", "jfnietopajares@gmail.com"));
        Iterator<ParametroCfg> it = listaParametros.iterator();
        while (it.hasNext()) {
        	ParametroCfg parametro= new ParametroCfg();
        	parametro=it.next();
        	miPr.setProperty(parametro.getNombre(),parametro.getValor());
        }
		try {
			salida = new FileOutputStream(Constantes.CONFIGURACIONNOMBREFICHERO);
			miPr.store(salida, Constantes.CONFIGURACIONDESCRIPCIONFICHERO);
			logger.info(Constantes.CONFIGURACIONMSGFICHEROCREADO + " " + Constantes.CONFIGURACIONNOMBREFICHERO );
			
		} catch (IOException e) {
			logger.error(Constantes.CONFIGURACIONMSGERRORFICHERO + " " + Constantes.CONFIGURACIONNOMBREFICHERO  + " " + Utilidades.getTraceException(e));
		} finally {
			if (salida != null)
				salida.close();
		}
	}

	/**
	 * Este método muestra los valores de los parámetros. Sirve de control del
	 * programa para ver el funcionamiento correcto.
	 */
	public void verParametros() {

		Enumeration<Object> keys = this.miPr.keys();
		
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			System.out.println(key + " = " + this.miPr.get(key));
		}
		
	}

}


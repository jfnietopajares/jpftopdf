package com.jnieto.jpegtopef;

import java.net.InetAddress;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controlador.JpgTpPdfControlador;
import dao.OperaFicheros;
import utilidades.Constantes;
import utilidades.MandaMail;
import utilidades.ParametrosGenerales;
import utilidades.Utilidades;

/**
 * 
 * @author JuanNieto. jfnietopajares@gmail.com
 * @Version 27.02.2019
 * Clase main del pruyecto.
 */
public class JpgToPdf {

	public static final Logger logger = LogManager.getLogger(JpgToPdf.class);
	public static Properties objParametros = null; 
	public String contenidoMail="";
	public String orientacion;
	
	public static void main(String arg[]) throws Exception {
		JpgToPdf obj = new JpgToPdf();
		objParametros = new ParametrosGenerales().getParametros();
		if (arg.length == 0 || arg.length > 1 || arg[0].equals("?") || arg[0].toLowerCase().equals("help")
				|| arg[0].toLowerCase().equals("ayuda")) {
			Utilidades.doMuestraTextoAyuda(objParametros);
			System.exit(0);
		} else {
			obj.setOrientacion(obj.getOrientacionParam(arg[0]));
		}
	
		obj.inicio();
		OperaFicheros.doCreaDirectorio();

		JpgTpPdfControlador objJpgControler=  new JpgTpPdfControlador(obj.orientacion);
		objJpgControler.recorreDirectorio();
		obj.contenidoMail += objJpgControler.getContenidoMail();
		obj.fin();
		obj.mandaCorreo(obj.contenidoMail);
	}

	final Runnable tarea = new Runnable() {
		public void run() {
			new JpgTpPdfControlador(orientacion);
		}
	};
	
	public void inicio() {
		logger.info(Constantes.MSGINICIO + " " +  OperaFicheros.getDirectorioActual() );
		contenidoMail += Constantes.MSGINICIO +Constantes.NEWLINE;
		contenidoMail += OperaFicheros.getDirectorioActual() ;
	}

	public void fin() {
		logger.info(Constantes.MSGFIN);
		contenidoMail += Constantes.MSGFIN +Constantes.NEWLINE;
	}

	public String getOrientacionParam(String tipo) {
		String orientacion = "V";
		switch (tipo.toLowerCase()) {
		case "v":
			orientacion = "V";
			break;
		case "vertical":
			orientacion = "V";
			break;
		case "h":
			orientacion = "H";
			break;
		case "horizontal":
			orientacion = "H";
			break;
		}
		return orientacion;
	}
	
	public void mandaCorreo(String contenido) {
		try {
			new MandaMail().sendEmail(Constantes.MAILEMISOR,
					"Proceso jpegTopdf de la ip " + InetAddress.getLocalHost(), contenido);
			logger.info(Constantes.MSGMAILOK);
		} catch (Exception e) {
			logger.error(Constantes.MSGERRORMAIL + ' ' + Utilidades.getTraceException(e));
		}
	}

	public String getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(String orientacion) {
		this.orientacion = orientacion;
	}

}
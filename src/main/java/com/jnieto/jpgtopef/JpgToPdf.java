package com.jnieto.jpgtopef;

import java.net.InetAddress;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jnieto.controlador.JpgToPdfControlador;
import com.jnieto.dao.OperaFicheros;
import com.jnieto.utilidades.Constantes;
import com.jnieto.utilidades.MandaMail;
import com.jnieto.utilidades.ParametrosGenerales;
import com.jnieto.utilidades.Utilidades;

/**
 * 
 * @author JuanNieto. jfnietopajares@gmail.com
 * @Version 27.02.2019 Clase main del proyecto.
 */
public class JpgToPdf {

	public static final Logger logger = LogManager.getLogger(JpgToPdf.class);
	public static Properties objParametros = null;
	public String contenidoMail = "";
	public String orientacion;
	public String directorioTrabajo="";

	/**
	 * 
	 * @param arg
	 *            En la llamada se pasan  dos  parámetros que puede ser.
	 *            
	 *            
	 *            [?] [help]  [ayuda] Cualquiera de estos parámetros muestra la ayuda de uso del  programa
	 *            [v] [V] [vertical] Indica que los pdf que se generen tengan  horienztación vertical 
	 *            [h] [H] [horizontal] Indica que los pdf que se generen tengan horienzación horizontal
	 * 
	 * 			  El segundo argumento es el directorio de trabajo.  Se debe pasar en la llamada para 
	 *            facilitar la programación automática de las tareas	
	 */
	public static void main(String arg[]) {
		try {
			JpgToPdf obj = new JpgToPdf();
			objParametros = new ParametrosGenerales().getParametros();
			obj.setDirectorioTrabajo(OperaFicheros.getDirectorioActual());
			switch (arg.length) {
			case 0:
				Utilidades.doMuestraTextoAyuda(objParametros,obj.getDirectorioTrabajo());
				System.exit(0);
				break;
			case 1:
				// un parámetro toma como directorio de trabajo el actual
				if (arg[0].equals("?") || arg[0].toLowerCase().equals("help") || arg[0].toLowerCase().equals("ayuda")) {
					Utilidades.doMuestraTextoAyuda(objParametros,obj.getDirectorioTrabajo());
					System.exit(0);
				} else {
					// un parámetro toma como directorio de trabajo el actual
					obj.setOrientacion(obj.getOrientacionParam(arg[0]));
					obj.setDirectorioTrabajo(OperaFicheros.getDirectorioActual());
				}
				break;
			case 2:
				obj.setOrientacion(obj.getOrientacionParam(arg[0]));
				if (!OperaFicheros.existeDirectorio(arg[1])) {
					logger.info(Constantes.MSGERRORDIRECTORIONOEXISTE + arg[1]);
					Utilidades.doMuestraTextoAyuda(objParametros,obj.getDirectorioTrabajo());
					System.exit(0);
				} else {
					obj.setDirectorioTrabajo(arg[1]);
				}
			}
//
			obj.inicio();
			// crea directorio para  procesos
			OperaFicheros.doCreaDirectorio(obj.getDirectorioTrabajo() + Constantes.CARPETAPROCESADOS);
			JpgToPdfControlador objJpgControler = new JpgToPdfControlador(obj.orientacion,obj.getDirectorioTrabajo());
			objJpgControler.recorreDirectorio();
			obj.contenidoMail += objJpgControler.getContenidoMail();
			obj.fin();
			System.exit(0);
			// obj.mandaCorreo(obj.contenidoMail);
		} catch (Exception e) {
			logger.error(Constantes.MSGERRORGENERAL + Utilidades.getTraceException(e));
		}

	}
   /**
    * Registra el evento de inicio del programa.
    */
	public void inicio() {
		logger.info(Constantes.MSGINICIO +  Utilidades.getFechaHora() +" Directorio:" + OperaFicheros.getDirectorioActual());
		contenidoMail += Constantes.MSGINICIO +Utilidades.getFechaHora()  +  Constantes.NEWLINE;
		contenidoMail += OperaFicheros.getDirectorioActual();
	}

	public void fin() {
		logger.info(Constantes.MSGFIN + Utilidades.getFechaHora());
		contenidoMail += Constantes.MSGFIN + Utilidades.getFechaHora() + Constantes.NEWLINE;
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
			new MandaMail().sendEmail(Constantes.MAILEMISOR, "Proceso jpegTopdf de la ip " + InetAddress.getLocalHost(),
					contenido);
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
	public String getDirectorioTrabajo() {
		return directorioTrabajo;
	}
	public void setDirectorioTrabajo(String directorioTrabajo) {
		this.directorioTrabajo = directorioTrabajo;
	}

}
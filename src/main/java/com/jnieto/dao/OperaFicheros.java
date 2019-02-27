package dao;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Excepciones.RenombraException;
import utilidades.Constantes;
import utilidades.Utilidades;

public class OperaFicheros {

	public static final Logger logger = LogManager.getLogger(OperaFicheros.class);
	public OperaFicheros() {
		
	}
	/*
	 * Comprueba que el nombre del fichero tenga un .
	 */
	public static boolean doValidaNombreFichero(String nombreFichero) {
		String partesNombre[] = nombreFichero.split("\\.");
		if (partesNombre.length != 2) {
			logger.info(Constantes.MSGERRORNOMBREFICHERO + nombreFichero);
		//	this.contenidoMail += Constantes.MSGERRORNOMBREFICHERO + nombreFichero + Constantes.NEWLINE;
			return false ;
		} else 
			return true;
	}
	/*
	 * Renombre el fichero poniendo como prefijo el directorio de procesados y el prefijo de  procesado según
	 * la constante
	 * 
	 */
	public static String getNuevoNombreFichero(String nombreFichero) {
		String nombreDirectorio = "." + System.getProperty("file.separator") + Constantes.CARPETAPROCESADOS;
		String  nombre = nombreDirectorio + System.getProperty("file.separator") + Constantes.PREFIJOPROCESADO + nombreFichero;
		return nombre;
	}
	/*
	 * Renombra o mueve el un fichero.
	 *  Si eldestino no tiene path como prejifo lo renombra 
	 *  si el destino tiene path como prefijo mueve el fichero al nuevo directorio
	 */
	public static void doRenombraMueve(String origen, String destino ) throws RenombraException  {
		try {
			File file = new File(origen);
			file.renameTo(new File(destino));
			logger.info(Constantes.MSGMOVIDO + " " + origen + " a " + destino);
			
		//	this.contenidoMail += Constantes.MSGMOVIDO + nuevoNombe;
		} catch (Exception e) {
			logger.error(Constantes.MSGERRORFICHERO + destino + " " + destino + " " + Utilidades.getTraceException(e) );
			
			throw new RenombraException( Constantes.MSGMOVIDO + " " + origen + " a " + destino, e);
			
		//	this.contenidoMail += Constantes.MSGERRORFICHERO + nombreFichero + " " + nombreFichero + " "
		//			+ Utilidades.getTraceException(e) +Constantes.NEWLINE;
		}
	}
	

	
	public static void doCreaDirectorio() {
		String nombreDirectorio = "." + System.getProperty("file.separator") + Constantes.CARPETAPROCESADOS;
		try {
			File directorio = new File(nombreDirectorio);
			directorio.mkdir();
			logger.info(Constantes.MSGDIRECTORIOCREADO + nombreDirectorio);
		//	this.contenidoMail += Constantes.MSGDIRECTORIOCREADO + nombreDirectorio +Constantes.NEWLINE;
		} catch (Exception e) {
			logger.error(Constantes.MSGERRORDIRECTORIO + nombreDirectorio + " "+ Utilidades.getTraceException(e) );
		//	this.contenidoMail += Constantes.MSGERRORDIRECTORIO + nombreDirectorio + " " + Utilidades.getTraceException(e) +Constantes.NEWLINE;
		}
	}
	
	/**
	 * Recupera el path absoluto de trabajo en formato normalizado.
	 * @return path del directorio de trabajo
	 */
	public static String getDirectorioActual() {
		File  file = new File(".");
		String pathAbsoluto = file.getAbsolutePath();
		// quitamos el punto final
		pathAbsoluto = pathAbsoluto.substring(0,pathAbsoluto.length()-1) ;
		return (pathAbsoluto);
	}
	
}

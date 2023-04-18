package com.jnieto.dao;

import com.jnieto.excepciones.RenombraException;
import com.jnieto.utilidades.Constantes;
import com.jnieto.utilidades.Utilidades;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            return false;
        } else {
            return true;
        }
    }

    /*
	 * Renombre el fichero poniendo como prefijo el directorio de procesados y el prefijo de  procesado según
	 * la constante
	 *
     */
    public static String getNuevoNombreFichero(String directorioTrabajo, String nombreFichero) {
        String nombreDirectorio = Constantes.CARPETAPROCESADOS;
        String nombre = directorioTrabajo + nombreDirectorio + System.getProperty("file.separator") + Constantes.PREFIJOPROCESADO + nombreFichero;
        return nombre;
    }

    /*
	 * Renombra o mueve el un fichero.
	 *  Si eldestino no tiene path como prejifo lo renombra
	 *  si el destino tiene path como prefijo mueve el fichero al nuevo directorio
     */
    public static void doRenombraMueve(String origen, String destino) throws RenombraException {
        try {
            File file = new File(origen);
            file.renameTo(new File(destino));
            logger.info(Constantes.MSGMOVIDO + " " + origen + " a " + destino);
        } catch (Exception e) {
            logger.error(Constantes.MSGERRORFICHERO + destino + " " + destino + " " + Utilidades.getTraceException(e));

            throw new RenombraException(Constantes.MSGMOVIDO + " " + origen + " a " + destino, e);
        }
    }

    public static void doCreaDirectorio(String nombreDirectorio) {
        try {
            File directorio = new File(nombreDirectorio);
            directorio.mkdir();
            logger.info(Constantes.MSGDIRECTORIOCREADO + nombreDirectorio);
        } catch (Exception e) {
            logger.error(Constantes.MSGERRORDIRECTORIO + nombreDirectorio + " " + Utilidades.getTraceException(e));
        }
    }

    /**
     * Recupera el path absoluto de trabajo en formato normalizado.
     *
     * @return path del directorio de trabajo
     */
    public static String getDirectorioActual() {
        File file = new File(".");
        String pathAbsoluto = file.getAbsolutePath();
        // quitamos el punto final
        pathAbsoluto = pathAbsoluto.substring(0, pathAbsoluto.length() - 1);
        return (pathAbsoluto);
    }

    public static boolean existeDirectorio(String directorio) {
        File file = new File(directorio);
        return file.isDirectory();
    }

}

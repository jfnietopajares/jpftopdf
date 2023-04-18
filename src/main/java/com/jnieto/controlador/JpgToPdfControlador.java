package com.jnieto.controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.jnieto.dao.OperaFicheros;
import com.jnieto.utilidades.Constantes;
import com.jnieto.utilidades.Utilidades;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JpgTpPdfControlador.
 *
 * @author Juan Nieto - jfnietopajares@gmail.com
 * @version 27.02.2019
 */
public class JpgToPdfControlador {

    public static final Logger logger = LogManager.getLogger(JpgToPdfControlador.class);
    public String contenidoMail = " ";
    public String orientacion;
    public String directorioTrabajo;

    /**
     * Constructor.
     *
     * @param orientacion: Define la orientaciÃ±on de pdf que se va a generar H
     * horizontal V veritcal
     */
    public JpgToPdfControlador(String orientacion, String directorioTrabajo) {
        this.setOrientacion(orientacion);
        this.setDirectorioTrabajo(directorioTrabajo);
    }

    /**
     * recorreDirectorio. Para el directorio actual saca la lista de ficheros
     * valida que el nombre tenga sÃ³lo un . si tiene extensiÃ³n jpg lo procesa
     */
    public void recorreDirectorio() {
        /*
		 *
         */
        File directorioActual = new File(this.getDirectorioTrabajo());
        //System.out.println("------>" + this.getDirectorioTrabajo());
        File[] listaFicheros = directorioActual.listFiles();
        String nombreFichero;
        for (int i = 0; i < listaFicheros.length; i++) {
            if (!listaFicheros[i].isDirectory()) {
                nombreFichero = listaFicheros[i].getName();
                //	System.out.println("------>" + nombreFichero);
                if (OperaFicheros.doValidaNombreFichero(nombreFichero)) {
                    String partesNombre[] = nombreFichero.split("\\.");
                    //		System.out.println("------> validado nombre" );
                    if (partesNombre[1].toLowerCase().equals(Constantes.EXTENSIONJPG)) {
                        this.procesaFicheroJpg(nombreFichero);
                    } else if (partesNombre[1].toLowerCase().equals(Constantes.EXTENSIONPDDF)) {
                        //			System.out.println("------> pdf " );
                        this.procesaFicheroPdf(nombreFichero);
                    } else {
                        logger.info(Constantes.MSGEXTENSIONNOJPG + nombreFichero);
                    }
                } else {
                    logger.info(Constantes.MSGERRORNOMBREFICHERO + nombreFichero);
                    System.out.println(" el nombre no es vÃ¡lido ver que hacemos" + nombreFichero + Constantes.NEWLINE);
                }
            }
        }
    }

    /**
     * procesaFicheroJpg. El fichero jpg si no ha sido procesado (el nombre no
     * tiene el prefijo procesado Constantes.PREFIJOPROCESADO En el atributo
     * contenidoMail va aÃ±adiendo informaciÃ³n de cada fichero procesado
     *
     * @param nombreFichero: Un nombre de fichero con extensiÃ³n jpg
     *
     */
    public void procesaFicheroJpg(String nombreFichero) {
        String partesNombre[] = nombreFichero.split("\\.");
        String nombreFicheroPathCompleto = this.directorioTrabajo + nombreFichero;
        try {
            if (partesNombre[0].length() > Constantes.PREFIJOPROCESADO.length()) {
                if (!partesNombre[0].substring(0, Constantes.PREFIJOPROCESADO.length())
                        .equals(Constantes.PREFIJOPROCESADO)) {
                    /*
					 * El fichero no tiene el prefijo procesado
                     */
                    this.doPdf(this.directorioTrabajo, nombreFichero, this.getOrientacion());
                    OperaFicheros.doRenombraMueve(nombreFicheroPathCompleto,
                            OperaFicheros.getNuevoNombreFichero(this.getDirectorioTrabajo(), nombreFichero));
                } else {
                    /*
					 * El fichero tiene como prefijo procesado se mueve a la carpeta de procesados
                     */
                    OperaFicheros.doRenombraMueve(this.directorioTrabajo + nombreFichero,
                            OperaFicheros.getNuevoNombreFichero(getDirectorioTrabajo(), nombreFichero));
                }
            } else {
                /*
				 * El jpg tiene un nombre corto no ha sido procesado
                 */
                this.doPdf(this.directorioTrabajo, nombreFichero, this.getOrientacion());
                OperaFicheros.doRenombraMueve(nombreFicheroPathCompleto,
                        OperaFicheros.getNuevoNombreFichero(this.getDirectorioTrabajo(), nombreFichero));
            }
            contenidoMail += Constantes.MSGFICHEORJPGOK + " " + nombreFichero + Constantes.NEWLINE;
        } catch (Exception e) {
            contenidoMail += Utilidades.getTraceException(e);
        }
    }

    /**
     * Genera un fichero con formato PDF a partir del fichero jpg y el tipo de
     * orientacion
     *
     * @param nombreFichero: El nombre de fichero jpg
     * @param orientacion: V vertical H horizontal para construir el pdf
     */
    public void doPdf(String directorio, String nombreFichero, String orientacion) {
        String fileName = nombreFichero.split("\\.")[0] + "." + Constantes.EXTENSIONPDDF;
        File root = new File(directorio);
        Document document;
        try {
            if (orientacion.equals("H")) {
                document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);
            } else {
                document = new Document(PageSize.A4, 0, 0, 0, 0);
            }

            PdfWriter.getInstance(document, new FileOutputStream(new File(root, fileName)));
            document.open();
            document.newPage();
            Image image = Image.getInstance(new File(root, nombreFichero).getAbsolutePath());
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);
            image.scaleAbsolute(PageSize.A4.rotate());
            document.add(image);
            document.close();
            logger.info(Constantes.MSGFICHEORPDFOK + nombreFichero);
            this.contenidoMail += Constantes.MSGFICHEORPDFOK + nombreFichero + Constantes.NEWLINE;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(Constantes.MSGERRORFICHERO + nombreFichero + Utilidades.getTraceException(e));
            this.contenidoMail += Constantes.MSGERRORFICHERO + nombreFichero + Utilidades.getTraceException(e)
                    + Constantes.NEWLINE;
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.error(Constantes.MSGERRORFICHERO + nombreFichero + Utilidades.getTraceException(e));
            this.contenidoMail += Constantes.MSGERRORFICHERO + nombreFichero + Utilidades.getTraceException(e)
                    + Constantes.NEWLINE;
        } finally {

        }
    }

    public void procesaFicheroPdf(String nombreFichero) {
        String partesNombre[] = nombreFichero.split("\\.");
        String nombreFicheroPathCompleto = this.directorioTrabajo + nombreFichero;
        try {
            if (partesNombre[0].length() > Constantes.PREFIJOPROCESADO.length()) {
                if (partesNombre[0].substring(0, Constantes.PREFIJOPROCESADO.length())
                        .equals(Constantes.PREFIJOPROCESADO)) {
                    /*
					 * El fichero tiene como prefijo procesado se mueve a la carpeta de procesados
                     */
                    OperaFicheros.doRenombraMueve(nombreFicheroPathCompleto,
                            this.directorioTrabajo + Constantes.CARPETAPROCESADOS + System.getProperty("file.separator") + nombreFichero);
                }
            } else {
                /*
				 * El pdf tiene un nombre corto no ha sido procesado
				 * no se hace nada
                 */
            }
            contenidoMail += Constantes.MSGFICHEORPDFMOVIDO + " " + nombreFichero + Constantes.NEWLINE;
        } catch (Exception e) {
            contenidoMail += Utilidades.getTraceException(e);
        }
    }

    /**
     * Getter.
     *
     * @return orientacion: valor del atributo orientacion
     */
    public String getOrientacion() {
        return orientacion;
    }

    /**
     * Setter.
     *
     * @param orientacion: valor para asignar al atributo orientacion
     */
    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    /**
     * Getter.
     *
     * @return contenidoMail: valor de atributo contenidoMail
     */
    public String getContenidoMail() {
        return contenidoMail;
    }

    /**
     * Setter.
     *
     * @param orientacion: valor para asignar al atributo contenidoMail
     */
    public void setContenidoMail(String contenidoMail) {
        this.contenidoMail = contenidoMail;
    }

    public String getDirectorioTrabajo() {
        return directorioTrabajo;
    }

    public void setDirectorioTrabajo(String directorioTrabajo) {
        this.directorioTrabajo = directorioTrabajo;
    }

}

package controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import dao.OperaFicheros;
import utilidades.Constantes;
import utilidades.Utilidades;

/**
 * 2 * Clase : JpgTpPdfControlador. 3 * 4 * @author Juan Nieto -
 * jfnietopajares@gmail.com 5 * @version 27.02.2019 6
 */
public class JpgTpPdfControlador {
	public static final Logger logger = LogManager.getLogger(JpgTpPdfControlador.class);
	public String contenidoMail = " ";
	public String orientacion;

	/**
	 * Constructor.
	 * 
	 * @param orientacion:
	 *            Define la orientaciñon de pdf que se va a generar H horizontal V
	 *            veritcal
	 */
	public JpgTpPdfControlador(String orientacion) {
		this.setOrientacion(orientacion);
	}

	/**
	 * recorreDirectorio. Para el directorio actual saca la lista de ficheros valida
	 * que el nombre tenga sólo un . si tiene extensión jpg lo procesa
	 */
	public void recorreDirectorio() {
		File directorioActual = new File("." + System.getProperty("file.separator"));
		File[] listaFicheros = directorioActual.listFiles();
		String nombreFichero;
		for (int i = 0; i < listaFicheros.length; i++) {
			if (!listaFicheros[i].isDirectory()) {
				nombreFichero = listaFicheros[i].getName();
				if (OperaFicheros.doValidaNombreFichero(nombreFichero)) {
					String partesNombre[] = nombreFichero.split("\\.");
					if (partesNombre[1].toLowerCase().equals(Constantes.EXTENSIONJPG)) {
						this.procesaFicheroJpg(nombreFichero);
					} else {
						System.out.println(" el fichero no es jpg " + nombreFichero + Constantes.NEWLINE);
					}
				} else {
					System.out.println(" el nombre no es válido ver que hacemos" + nombreFichero + Constantes.NEWLINE);
				}
			}
		}
	}

	/**
	 * procesaFicheroJpg. El fichero jpg si no ha sido procesado (el nombre no tiene
	 * el prefijo procesado Constantes.PREFIJOPROCESADO En el atributo contenidoMail
	 * va añadiendo información de cada fichero procesado
	 * 
	 * @param nombreFichero:
	 *            Un nombre de fichero con extensión jpg
	 * 
	 */
	public void procesaFicheroJpg(String nombreFichero) {
		String partesNombre[] = nombreFichero.split("\\.");
		try {
			if (partesNombre[0].length() > Constantes.PREFIJOPROCESADO.length()) {
				if (!partesNombre[0].substring(0, Constantes.PREFIJOPROCESADO.length())
						.equals(Constantes.PREFIJOPROCESADO)) {
					/*
					 * El fichero no tiene el prefijo procesado
					 */
					this.doPdf(nombreFichero,this.getOrientacion());
					OperaFicheros.doRenombraMueve(nombreFichero, OperaFicheros.getNuevoNombreFichero(nombreFichero));
				} else {
					/*
					 * El fichero tiene como prefijo procesado se mueve a la carpeta de procesados
					 */
					String nuevoNombre = "." + System.getProperty("file.separator") + Constantes.CARPETAPROCESADOS
							+ System.getProperty("file.separator") + nombreFichero;
					OperaFicheros.doRenombraMueve(nombreFichero, nuevoNombre);
				}
			} else {
				/*
				 * El jpg tiene un nombre corto no ha sido procesado
				 */
				this.doPdf(nombreFichero, this.getOrientacion());
				OperaFicheros.doRenombraMueve(nombreFichero, OperaFicheros.getNuevoNombreFichero(nombreFichero));
			}
			contenidoMail += Constantes.MSGFICHEORJPGOK + " " + nombreFichero + Constantes.NEWLINE;
		} catch (Exception e) {
			contenidoMail += Utilidades.getTraceException(e);
		}
	}

	/** 
	 *  Genera un fichero con formato  PDF a partir del fichero jpg  y el tipo de orientacion
	 * 
	 * @param nombreFichero: El nombre de fichero jpg
	 * @param orientacion: V vertical H horizontal para construir el pdf
	 */
	public void doPdf(String nombreFichero,String orientacion) {
		String fileName = nombreFichero.split("\\.")[0] + "." + Constantes.EXTENSIONPDDF;
		File root = new File("." + System.getProperty("file.separator"));
		Document document;
		try {
			if (orientacion.equals("H"))
				document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);
			else
				document = new Document(PageSize.A4, 0, 0, 0, 0);

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
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
			logger.error(Constantes.MSGERRORFICHERO + nombreFichero + Utilidades.getTraceException(e));
			this.contenidoMail += Constantes.MSGERRORFICHERO + nombreFichero + Utilidades.getTraceException(e)
					+ Constantes.NEWLINE;
		} finally {

		}
	}

	  /**
        * Getter.
          * @return orientacion: valor del atributo orientacion 
          */
	public String getOrientacion() {
		return orientacion;
	}
	  /**
     * Setter.
       * @param  orientacion: valor  para asignar al  atributo orientacion 
       */
	public void setOrientacion(String orientacion) {
		this.orientacion = orientacion;
	}

	  /**
     * Getter.
       * @return contenidoMail: valor de atributo contenidoMail 
       */
	public String getContenidoMail() {
		return contenidoMail;
	}
	  /**
     * Setter.
       * @param  orientacion: valor  para asignar al  atributo contenidoMail 
       */
	public void setContenidoMail(String contenidoMail) {
		this.contenidoMail = contenidoMail;
	}

}

package com.jnieto.jpegtopef;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class JpgToPdf {

	public static final Logger logger = LogManager.getLogger(JpgToPdf.class);
	public static Properties objParametros = null; 
	public String contenidoMail;
	public InetAddress address;
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
		new ParametrosGenerales();
		obj.address = InetAddress.getLocalHost();
		obj.inicio();
		obj.doCreaDirectorio();
		obj.recorreDirectorio();
		obj.mandaCorreo();
		obj.fin();
	}

	public void inicio() {
		logger.info(Constantes.MSGINICIO);
		contenidoMail = Constantes.MSGINICIO +Constantes.NEWLINE;
	}

	public void fin() {
		logger.info(Constantes.MSGFIN);
		contenidoMail = Constantes.MSGFIN +Constantes.NEWLINE;
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

	public void recorreDirectorio() {
		File micarpeta = new File("." + System.getProperty("file.separator"));
		File[] listaFicheros = micarpeta.listFiles();
		for (int i = 0; i < listaFicheros.length; i++) {
			if (!listaFicheros[i].isDirectory()) {
				this.procesaFicheroJpg(listaFicheros[i].getName());
			}
		}
	}

	public void procesaFicheroJpg(String nombreFichero) {
		String partesNombre[] = nombreFichero.split("\\.");
		if (partesNombre.length != 2) {
			logger.info(Constantes.MSGERRORNOMBREFICHERO + nombreFichero);
			this.contenidoMail += Constantes.MSGERRORNOMBREFICHERO + nombreFichero +Constantes.NEWLINE;
		} else if (partesNombre[1].toLowerCase().equals(Constantes.EXTENSIONJPG)) {
			/*
			 * El fichero tiene extension jpg y no ha sido procesado. No tiene de prefijo
			 * 'procesado'
			 */
			if (partesNombre[0].length() > Constantes.PREFIJOPROCESADO.length()) {
				if (!partesNombre[0].substring(0, Constantes.PREFIJOPROCESADO.length())
						.equals(Constantes.PREFIJOPROCESADO)) {
					this.doPdf(nombreFichero);
				}
			} else {
				this.doPdf(nombreFichero);
			}
		} else if (partesNombre[0].length() > Constantes.PREFIJOPROCESADO.length()) {
			if (partesNombre[0].substring(0, Constantes.PREFIJOPROCESADO.length())
					.equals(Constantes.PREFIJOPROCESADO)) {
				/*
				 * El fichero ha sido procesado se mueve a la carpeta de procesados
				 */
				this.doMueveProcesado(nombreFichero);
			}
		}
	}

	/*
	 * Genera el PDF
	 * 
	 * @param nombreFichero: El nombre de fichero jpg
	 */
	public void doPdf(String nombreFichero) {
		String fileName = nombreFichero.split("\\.")[0] + "." + Constantes.EXTENSIONPDDF;
		File root = new File("." + System.getProperty("file.separator"));
		Document document;
		try {
			if (this.getOrientacion().equals("H"))
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
			doRenombre(nombreFichero, Constantes.PREFIJOPROCESADO + nombreFichero);
			
			logger.info(Constantes.MSGPROCESADOPDF + nombreFichero);
			this.contenidoMail += Constantes.MSGPROCESADOPDF + nombreFichero +Constantes.NEWLINE;
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
			logger.error("Error " + nombreFichero + Utilidades.getTraceException(e) );
			this.contenidoMail += Constantes.MSGERRORFICHERO + nombreFichero +  Utilidades.getTraceException(e) +Constantes.NEWLINE;
		} finally {

		}
	}

	public void doRenombre(String nombreFichero, String nuevoNombe) {
		try {
			String nombreDirectorio = "." + System.getProperty("file.separator") + Constantes.CARPETAPROCESADOS;
			nuevoNombe = nombreDirectorio + System.getProperty("file.separator") + nuevoNombe;
			File file = new File(nombreFichero);
			file.renameTo(new File(nuevoNombe));
			logger.info(Constantes.MSGMOVIDO + nuevoNombe);
			this.contenidoMail += Constantes.MSGMOVIDO + nuevoNombe;
		} catch (Exception e) {
			logger.error(Constantes.MSGERRORFICHERO + nombreFichero + " " + nuevoNombe + " " + Utilidades.getTraceException(e) );
			this.contenidoMail += Constantes.MSGERRORFICHERO + nombreFichero + " " + nuevoNombe + " " + Utilidades.getTraceException(e) 
					+Constantes.NEWLINE;
		}
	}

	public void doMueveProcesado(String nombreFichero) {
		try {
			String nombreDirectorio = "." + System.getProperty("file.separator") + Constantes.CARPETAPROCESADOS;
			String nuevoNombe = nombreDirectorio + System.getProperty("file.separator") + nombreFichero;
			File file = new File(nombreFichero);
			file.renameTo(new File(nuevoNombe));
			logger.info(Constantes.MSGMOVIDO + nuevoNombe);
			this.contenidoMail += Constantes.MSGMOVIDO + nuevoNombe;
		} catch (Exception e) {
			logger.error(Constantes.MSGERRORFICHERO + nombreFichero + " " + nombreFichero + " " + Utilidades.getTraceException(e) );
			this.contenidoMail += Constantes.MSGERRORFICHERO + nombreFichero + " " + nombreFichero + " "
					+ Utilidades.getTraceException(e) +Constantes.NEWLINE;
		}
	}

	public void doCreaDirectorio() {
		String nombreDirectorio = "." + System.getProperty("file.separator") + Constantes.CARPETAPROCESADOS;
		try {
			File directorio = new File(nombreDirectorio);
			directorio.mkdir();
			logger.info(Constantes.MSGDIRECTORIOCREADO + nombreDirectorio);
			this.contenidoMail += Constantes.MSGDIRECTORIOCREADO + nombreDirectorio +Constantes.NEWLINE;
		} catch (Exception e) {
			logger.error(Constantes.MSGERRORDIRECTORIO + nombreDirectorio + " "+ Utilidades.getTraceException(e) );
			this.contenidoMail += Constantes.MSGERRORDIRECTORIO + nombreDirectorio + " " + Utilidades.getTraceException(e) +Constantes.NEWLINE;
		}
	}

	public void mandaCorreo() {
		try {
			new MandaMail().sendEmail(Constantes.MAILEMISOR,
					"Proceso jpegTopdf de la ip " + this.address.getHostAddress(), this.contenidoMail);
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
package utilidades;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.OperaFicheros;

public class Utilidades {
	public static final Logger logger = LogManager.getLogger(Constantes.class);

	public static String getTraceException(Exception ex) {
		String mensaje = Constantes.NEWLINE;
		for (StackTraceElement elemnt : ex.getStackTrace()) {
			mensaje += elemnt.toString() + Constantes.NEWLINE;
		}
		return mensaje;
	}

	public static void doMuestraTextoAyuda(Properties miPr) {
		System.out.println(Constantes.AYUDAUSO);
		System.out.println(
				"----------------------------------------------------------------------------" );
		System.out.println("           Directorio de trabajo actual : " + OperaFicheros.getDirectorioActual());
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
}

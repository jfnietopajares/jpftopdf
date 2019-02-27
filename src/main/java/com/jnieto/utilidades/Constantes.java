package com.jnieto.utilidades;

public abstract class Constantes {
	public static final String NEWLINE = "\n";

	public static final String EXTENSIONPDDF = "pdf";
	public static final String EXTENSIONJPG = "jpg";
	public static final String PREFIJOPROCESADO = "procesado";
	public static final String CARPETAPROCESADOS = "procesados";

	public static final String CONFIGURACIONNOMBREFICHERO = "jpgtopdf.properties";
	public static final String CONFIGURACIONDESCRIPCIONFICHERO = " Parámetro de configuración del programa";
	public static final String CONFIGURACIONMSGFICHEROCREADO = " Fichero de configuracion creado";
	public static final String CONFIGURACIONMSGERRORFICHERO = " Error creando ichero de configuracion ";

	public final static String MIALHOST = "outlook.office365.com";
	public final static String MIALSTARTTLS = "true";
	public final static int MIALPORT = 25;
	public final static String MAILEMISOR = "jnieto@saludcastillayleon.es";
	public final static String MAILUSER = "06551256M@saludcastillayleon.es";
	public final static String MAILPASSW = "00000jjjjj";
	public final static String MAILAUTH = "true";
	public final static String MAILDESTINATARIOS = "jfnietopajares@gmail.com";
	public final static String MSGERRORMAIL = "Error enviando mail : ";

	public final static String MSGFICHEORJPGOK = "Fichero jpg  procesado : ";
	public final static String MSGFICHEORPDFOK = "Fichero PDF generado : ";
	
	public final static String MSGMOVIDO = "Fichero movido al directorio : ";
	public final static String MSGDIRECTORIOCREADO = " Directorio creado  : ";
	public final static String MSGERRORDIRECTORIO = " Error al crear diractorio  : ";
	public final static String MSGERRORFICHERO = "Error en fichero original : ";
	public final static String MSGERRORGENERAL = "Error general  : ";
	public final static String MSGERRORNOMBREFICHERO = "Error en el nombre del fichero original : ";

	public final static String MSGMAILOK = "Correo enviado : ";
	public final static String MSGINICIO = "Inicio de ejecución : ";
	public final static String MSGFIN = "Fin de ejecución : ";

	public final static String MSGCONFIGURACIONLEEFICHERO = "Fichero de configuración leido ";
	public final static String MSGCONFIGURACIONERRORLEEFICHERO = "Error leyendo fichero de configuración leido ";

	public final static String AYUDAUSO = NEWLINE  
			+ "----------------------------------------------------------------------------" + NEWLINE
			+ " Programa: jpgtopdf.jar \n" + " Autor: Juan Nieto.\n" + " Fecha: Febrero 2019 " + NEWLINE
			+ " Para ejecutar el programa de forma correcta debes indicar un parámetro: " + NEWLINE
			+ "    [?] o [ayuda] o [help]   -->  Muestra esta ayuda  "+ NEWLINE
			+ "    [H] u [Horizontal]       --> Compone el PDF en horizontal  "+ NEWLINE
			+ "    [V] o [Vertical]         --> Componen el PDF en vertical "+ NEWLINE+ NEWLINE
			+ " Ejemplo:  java -jar jpgtopdf.jar V "+ NEWLINE
			+ "----------------------------------------------------------------------------"+ NEWLINE
			+ "El programa recorre el directorio actual y por cada fichero jpg genera un  "+ NEWLINE
			+ "fichero PDF con el mismo nombre. "+ NEWLINE+ NEWLINE
			+ "Los ficheros procesados los renombra poniendo como prefijo 'procesado' "+ NEWLINE
			+ "y los mueve al subdirectorio './procesados'. "+ NEWLINE;
			

}

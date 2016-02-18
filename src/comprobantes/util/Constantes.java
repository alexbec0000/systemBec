package comprobantes.util;
 
import java.text.SimpleDateFormat;

public class Constantes
{
    public static final boolean enviarSRI = true;
    public static final String pathFac = "C:\\TEMP\\Facturas\\ERP\\";
    public static final String pathCORE = "http://172.20.120.15/addocument/";
    public static final String pathCORE_Test = "http://172.20.116.101/html/addocument/";
    public static final String tipoEmision = "1";
    public static final String secret_key = "IISE9C5un6alfpzLqL7lPuKyWgKYBRZeoQwgdOYq";
    public static final String codigo_numerico = "00000001";
    public static final String contribuyenteEspecial = "162";
    public static final String llevaContabilidad = "SI";

    public static final String VERSION = "1.0.0";
    public static final String XML = ".xml";
    public static final String COD_UTF8 = "UTF-8";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
    public static final Integer LONGITUD_CLAVE_CONTINGENCIA = Integer.valueOf(37);

    public Constantes() {}
  
}

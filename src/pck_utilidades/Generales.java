package pck_utilidades;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import pck_controller.ClienteController;

/**
 *
 * @author alex
 */
public class Generales {
    private static SimpleDateFormat formato;
    public static final int ANIO_MES_DIA = 1;
    public static final int DIA_MES_ANIO = 2;
    public static final String PREFIJO_ARTICULO = "AR";
    public static final String PREFIJO_CLIENTE = "CL";
    public static final String PREFIJO_VENDEDOR = "VE";
    
    private static Date fechaString;
   
    private static String fechaActual()
    {
        String strFecha="1990-01-01";
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        //strFecha = dia + "/" + (mes+1) + "/" + año;
        
        strFecha=año+"-";
        
        if((mes+1)<10)
            strFecha+="0"+(mes+1)+"-";
        else
            strFecha+=(mes+1)+"-";
        
        if(dia<10)
            strFecha+="0"+dia;
        else
            strFecha+=dia;
        
        return strFecha;
    }
   
    public static String getFechaFormateada(Date fecha,int tipoFormato)
    {
        formato = new SimpleDateFormat("yyyy-MM-dd");
        if(tipoFormato == DIA_MES_ANIO)
            formato = new SimpleDateFormat("dd-MM-yyyy");
        return formato.format((Date)fecha);
    }
    
    public static Date stringToDate(String text,int tipoFormato)
    {  
        text=text==null?fechaActual():text;
        formato = new SimpleDateFormat("yyyy-MM-dd");
        if(tipoFormato == DIA_MES_ANIO)
            formato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            fechaString=(Date)formato.parseObject(text);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return fechaString;
    }
    
  
    
    public static synchronized String generarCodigo(String cadena,String prefijo)
    {
        String pre = "";
        int longitudPrefijo = 0;
        
        if(cadena==null || cadena.isEmpty())
       {
           throw new NullPointerException("cadena nula o vacia. solo el prefijo puede ser nulo o vacio");
       }
        
        if(prefijo != null)
        {            
           pre = prefijo;
           String pre2 = cadena.substring(0, pre.length());           
           if(!pre.equals(pre2))
           {
               System.out.println("El prefijo: "+pre2+" de la cadena "
                       +cadena+" no coincide con el prefijo "+pre);
           }
           if(!prefijo.equals(PREFIJO_ARTICULO) || !prefijo.equals(PREFIJO_CLIENTE)
               || !prefijo.equals(PREFIJO_VENDEDOR))
           {
              longitudPrefijo = contadorLetras(cadena); 
           }else
           {
              longitudPrefijo = prefijo.length(); 
           }
        }else{
            longitudPrefijo = contadorLetras(cadena);            
            prefijo = "";
        }
        
        
        
        StringBuilder cadOriginal = new StringBuilder(cadena);
        String tmpCadena = cadOriginal.toString();
        
        if(longitudPrefijo>0)
        {          
          tmpCadena = cadOriginal.substring(longitudPrefijo);  
        }
        
        int valorEntero = 0;
        try{
            valorEntero = Integer.parseInt(tmpCadena);
        }catch(NumberFormatException ex){
        
            System.err.println(tmpCadena+" tiene un formato no valido.\n"+
                    "ejem: formato valido: PREJ0000015,pref0000025,0000158");
            return null;
        }
        valorEntero += 1;
        if(valorEntero<=9)
        {
            tmpCadena = tmpCadena.substring(0, tmpCadena.length()-1);
            tmpCadena = prefijo+tmpCadena+valorEntero;
        }else{
            String ctmp = ""+valorEntero;
            if(ctmp.length() > tmpCadena.length())
            {
                System.out.println("Valor maximo alcansado: no se puede generar mas codigos");
                return null;
            }
            if(ctmp.length() == tmpCadena.length())
            {
              tmpCadena=pre+ctmp;
            }else{
               
                int posicion = tmpCadena.length()-ctmp.length();
                String ceros = tmpCadena.substring(0, posicion);
                tmpCadena=pre+ceros+ctmp;
            }
                
        }
        
        return tmpCadena;
    }
    
    private static int contadorLetras(String cadena)
    {
        char[] letras = cadena.toCharArray();
        int contador = 0;
        for(int i=0;i<letras.length;i++)
        {
            if(Character.isLetter(letras[i]))
            {
                contador++;
            }
        }
        return contador;
    }
    
    public static String padLeft(String valor, String pad, int relleno) {
        try {
            String str = valor;
            StringBuilder sb = new StringBuilder();
            
            for (int toPrepend = relleno - str.length(); toPrepend > 0; toPrepend--) {
                sb.append(pad);
            }
            
            sb.append(str);
            return sb.toString();
        } catch (NumberFormatException ex) {
        }
        return "-1";
    }
    
    public static String padRight(String valor, String pad, int relleno) {
        try {
            String str = valor;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            for (int toPrepend = relleno - str.length(); toPrepend > 0; toPrepend--) {
                sb.append(pad);
            }
            return sb.toString();
        } catch (NumberFormatException ex) {
        }
        return "-1";
    }
    
    /**
     * 
     * @param dc Docuement del compoenente
     * @param tipoValidacion tipo de filtro:<br/>
     * <ul>
     * <li>SOLO_NUMEROS: el campo solo permitira numeros.</li>
     * <li>SOLO_LETRAS: el campo permitira solo letras</li>
     * <li>NUM_LETRAS: una mesclas de SOLO_NUMEROS y SOLO_LETRAS</li>
     * <li>DEFAULT: permitira casi cualquier caracter</li>
     * </ul>
     * @param lgCadena longitud maxima de la cadena. pasar cero(0) si la cedena es iliminada
     * @param espcBlco <code>true</code> para que se permita espacios en blanco.
     */
    public static void setFiltraEntrada(Document dc,char tipoValidacion,int lgCadena,boolean espcBlco)
    {
        FiltraEntrada fe = new FiltraEntrada(tipoValidacion,lgCadena,espcBlco);
        ((AbstractDocument)dc).setDocumentFilter(fe);
    }
    public static void removerFiltraEntrada(Document dc)
    {        
        ((AbstractDocument)dc).setDocumentFilter(new DocumentFilter());
    }
    
    /*public static CustomImageIcon getFoto(String id)
    {
        CustomImageIcon ii = null;
        try
        {
            ClienteController objClienteController=new ClienteController();
            InputStream is=objClienteController.getFoto(id);
            if(is != null)
            {
                BufferedImage bi = ImageIO.read(is);
                ii = new CustomImageIcon(bi);
            }

       }
       catch(IOException ex)
       {
           ex.printStackTrace();
       }
       return ii;
    }*/
    
    public static CustomImageIcon getFoto(InputStream is)
    {
        CustomImageIcon ii = null;
        try
        {
            if(is != null)
            {
                BufferedImage bi = ImageIO.read(is);
                ii = new CustomImageIcon(bi);
            }

       }
       catch(IOException ex)
       {
           ex.printStackTrace();
       }
       return ii;
    }
        
}

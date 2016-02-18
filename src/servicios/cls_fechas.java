/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Alex
 */
 public class cls_fechas {
    
    private static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    // private static SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
    
    public String DeDateAString(Date fecha)
    {        
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");               
        return formato.format(fecha);    
    }
    
    public String dateDeStringAString(String fecha)
    {        
        String[] fechaE = fecha.split("-");      
        return fechaE[2] + "/" + fechaE[1] + "/" + fechaE[0];     
    }
    
    public static Date DeStringADate(String fecha)
    {               
        String strFecha = fecha;        
        Date fechaDate = null;        
        try {    
            fechaDate = formato.parse(strFecha);                                  
            return fechaDate;        
        } catch (ParseException ex) 
        {            ex.printStackTrace();            
            return new Date();        
        }    
    }
    
    public static Date DeStringADate2(String fecha)
    {               
        String[] fechaE = fecha.split("-");      
        String strFecha = fechaE[2] + "/" + fechaE[1] + "/" + fechaE[0];        
        Date fechaDate = null;        
        try {    
            fechaDate = formato.parse(strFecha);                                  
            return fechaDate;        
        } catch (ParseException ex) 
        {            ex.printStackTrace();            
            return new Date();        
        }    
    }
    
     public String sumarDias(String strFecha,int dias)
    {
        String hoy = formato.format(DeStringADate(strFecha));
        String [] dataTemp = hoy.split("/");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(dataTemp[2]), Integer.parseInt(dataTemp[1])- 1, Integer.parseInt(dataTemp[0]));
        c.add(Calendar.DATE, dias);
        return formato.format(c.getTime());
    }

    public SimpleDateFormat getFormato() {
        return formato;
    }
     
     
    
}

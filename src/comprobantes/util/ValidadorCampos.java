/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comprobantes.util;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author abecerra
 */

 public class ValidadorCampos
 {
   public ValidadorCampos() {}
   
   public static String validarCampoObligatorio(HashMap<String, String> mapa)
   {
     Set<String> keys = mapa.keySet();
     for (String key : keys) {
       if (validarObligatorio(key).booleanValue()) {
         return "El campo " + (String)mapa.get(key) + " es obligatorio";
       }
     }
     return null;
   }
   
   private static Boolean validarObligatorio(String valor) {
     if ((valor == null) || (valor.isEmpty())) {
       return Boolean.TRUE;
     }
     return Boolean.FALSE;
   }
   
   public static String validarCampoLongitudDiferente(List<CampoModelo> campos)
   {
     for (CampoModelo campoModelo : campos) {
       if ((campoModelo.getValor() != null) && (!campoModelo.getValor().isEmpty()) && (campoModelo.getValor().length() != campoModelo.getLongitud().intValue())) {
         return "La longitud del campo " + campoModelo.getEtiqueta() + " debe ser de " + campoModelo.getLongitud() + " caracteres";
       }
     }
     return null;
   }
   
   public static String validarCampoLongitudMayor(List<CampoModelo> campos) {
     for (CampoModelo campoModelo : campos) {
       if ((campoModelo.getValor() != null) && (!campoModelo.getValor().isEmpty()) && (campoModelo.getValor().length() > campoModelo.getLongitud().intValue())) {
         return "La longitud máxima del campo " + campoModelo.getEtiqueta() + " es de " + campoModelo.getLongitud() + " caracteres";
       }
     }
     return null;
   }
   
   public static Boolean validarDecimales(String valor, int numeroDecimales)
   {
     String[] partesNumero = null;
     if (valor.indexOf(".") < 0) {
       return Boolean.TRUE;
     }
     partesNumero = valor.split("\\.");
     return Boolean.valueOf(partesNumero[1].length() <= numeroDecimales);
   }
 }







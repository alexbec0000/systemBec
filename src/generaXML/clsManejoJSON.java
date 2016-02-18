/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generaXML;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author abecerra
 */
public  class clsManejoJSON {
    
         /**
     * Función que pasa de un objeto JSON a un HashMap.
     *
     * @param objetoJSON
     * @return
     */
    public static HashMap<String, Object> parseMap(String objetoJSON) {
        // HashMap a retornar con los datos de la consulta
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();

        try {
            // Convirtiendo el objeto JSON a un Map
            jsonMap = new Gson().fromJson(objetoJSON,
                    new TypeToken<HashMap<String, Object>>() {
                    }.getType());
        } catch (Exception ex) {
            System.out.println("Error al pasar a JSON\n" + ex.getMessage());
        }

        // Retornando el resultado
        return jsonMap;
    }

    /**
     * Función que pasa de un objeto a un Map.
     *
     * @param objeto
     * @return
     */
    public static Map<String, Object> parseMap(Object objeto) {
        // Map a retornar con los datos de la consulta
        Map<String, Object> mapa = new HashMap<String, Object>();

        try {
            // Convirtiendo el objeto JSON a un Map
            mapa = (Map<String, Object>) objeto;
        } catch (Exception ex) {
            System.out.println("Error al pasar a Map\n" + ex.getMessage());
        }

        // Retornando el resultado
        return mapa;
    }
    
    /**
     * Retorna el mensaje del core de facturación como un arreglo de Strings
     *
     * @param objetoJSON
     * @return
     */
    public static String[] obtenerMensaje(String objetoJSON, String mail, String targetURL, boolean reproceso) {
        // Mensaje a retornar
        String[] mensajeCore = null;

        try {
            // Pasando a map
            Map<String, Object> respuesta = parseMap(objetoJSON);

            // Si la cabecera retorna "message"
            if (respuesta.keySet().toArray()[0].toString().contains("message")) {
                // Valor del mensaje
                Map<String, Object> message = parseMap(respuesta.get("message"));

                // Estado del mensaje
                String estado = message.get("estado").toString().toUpperCase();

                // Si el estado es igual a "DEVUELTA"
                if (estado.equals("DEVUELTA")) {
                    // Mapa con los elementos de los comprobantes
                    Map<String, Object> comprobantes = parseMap(message.get("comprobantes"));

                    // Mapa con los elementos del comprobante
                    Map<String, Object> comprobante = parseMap(comprobantes.get("comprobante"));

                    // Clave de Acceso
                    String claveAcceso = comprobante.get("claveAcceso").toString();

                    // Contenido de los mensajes
                    Map<String, Object> mensajes = parseMap(comprobante.get("mensajes"));

                    // Contenido del mensaje
                    Map<String, Object> mensaje = parseMap(mensajes.get("mensaje"));

                    // Identificador (Código del error)
                    String identificador = mensaje.get("identificador").toString();
                    
                    // infoAdicional (Informacion adicional del error)
                    String infoAdicional = "N/A";
                    
                    try { infoAdicional = mensaje.get("informacionAdicional").toString(); }
                    catch(Exception ex) {}
                    // Error secuencial
                    String errorMensaje = mensaje.get("mensaje").toString();

                    // Tipo error
                    String tipoError = mensaje.get("tipo").toString();

                    // Cantidad de items que retorna el mensaje -------------------
                    mensajeCore = new String[5];
                    mensajeCore[0] = estado;
                    mensajeCore[1] = tipoError +": "+ identificador;
                    mensajeCore[2] = errorMensaje;
                    mensajeCore[3] = "Clave Acceso: " + claveAcceso;
                    mensajeCore[4] = "infoAdicional: " +infoAdicional;
                    
                    if(identificador.equals("43") || identificador.equals("45"))
                    {
                        if(reproceso)
                        {
                            consumoServiciosCore objconsumoServiciosCore=new consumoServiciosCore();
                            //String[] mensaje2=objconsumoServiciosCore.consumoAuthorizationsJson(targetURL, claveAcceso, mail);
                            //mensajeCore[0] = "AUTORIZADO";
                            //mensajeCore[4] = "infoAdicional: " + mensaje2[0] + " - "+mensaje2[1];
                            
                            mensajeCore=objconsumoServiciosCore.consumoAuthorizationsJson(targetURL, claveAcceso, mail);
                            mensajeCore[0] = "AUTORIZADO";
                        }
                        else
                            mensajeCore[0] = "AUTORIZADO";
                    }
                }

                // Si el estado es igual a "AUTORIZADO"
                if (estado.equals("AUTORIZADO")) {
                    // Número de autorización
                    String numeroAutorizacion = message.get("numeroAutorizacion").toString();

                    // Fecha de autorización
                    String fechaAutorizacion = message.get("fechaAutorizacion").toString();

                    // Clave de acceso
                    String claveAcceso = message.get("clave_acceso").toString();

                    // Cantidad de items que retorna el mensaje -------------------
                    mensajeCore = new String[4];
                    mensajeCore[0] = estado;
                    mensajeCore[1] = "Fecha Autorizacion: " + fechaAutorizacion;
                    mensajeCore[2] = "Número Autorizacion: " + numeroAutorizacion;
                    mensajeCore[3] = "Clave Acceso: " + claveAcceso;
                }
                
                // Si el estado es igual a "NO AUTORIZADO"
                if (estado.equals("NO AUTORIZADO")) {
                    
                    // Fecha de autorización
                    String fechaAutorizacion = message.get("fechaAutorizacion").toString();

                    // Clave de acceso
                    String claveAcceso = message.get("clave_acceso").toString();
                    
                     // Contenido de los mensajes
                    Map<String, Object> mensajes = parseMap(message.get("mensajes"));

                    // Contenido del mensaje
                    Map<String, Object> mensaje = parseMap(mensajes.get("mensaje"));

                    // Identificador (Código del error)
                    String identificador = mensaje.get("identificador").toString();

                    // Error secuencial
                    String errorMensaje = mensaje.get("mensaje").toString();

                    // Tipo error
                    String tipoError = mensaje.get("tipo").toString();

                    // Cantidad de items que retorna el mensaje -------------------
                    mensajeCore = new String[5];
                    mensajeCore[0] = estado;
                    mensajeCore[1] = tipoError +": "+ identificador;
                    mensajeCore[2] = errorMensaje;
                    mensajeCore[3] = "Fecha de Autorizacion: " + fechaAutorizacion;
                    mensajeCore[4] = "Clave Acceso: " + claveAcceso;
                   
                }
            }

            // Si la cabecera retorna "error"
            if (respuesta.keySet().toArray()[0].toString().contains("error")) {
                // Map con los items del error
                Map<String, Object> error = parseMap(respuesta.get("error"));

                // Código del mensaje
                String codigo = error.get("code").toString();

                // Descripcion
                String descripcion = error.get("descripcion").toString();

                // Clave_acceso
                String clave_acceso = null;
                try
                {clave_acceso = error.get("clave_acceso").toString();}
                catch(Exception ex)
                {}

                // Cantidad de items que retorna el mensaje -------------------
                mensajeCore = new String[3];
                mensajeCore[0] = "Error: " + codigo;
                mensajeCore[1] = descripcion;
                mensajeCore[2] = "Clave de acceso: " + clave_acceso;
                
                if(codigo.equals("14") || codigo.equals("14.0")) //el documentos ya ha sido autorizado previamente, No se debe autorizar un documento nuevamente
                {
                    mensajeCore[0] = "AUTORIZADO";
                }
            }
        } catch (Exception ex) {
            mensajeCore = new String[2];
            mensajeCore[0] = "Error";
            mensajeCore[1] = "No se pudo procesar la respuesta JSON "+ex.getMessage();
        }

        return mensajeCore;
    }

}

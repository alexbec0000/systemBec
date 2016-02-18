package comprobantes.util;

import comprobantes.entidades.ClaveContingencia;
import comprobantes.doc.factura.Factura;
import comprobantes.doc.guiaRemision.GuiaRemision;
import comprobantes.doc.notacredito.NotaCredito;
import comprobantes.doc.notadebito.NotaDebito;
import comprobantes.doc.retencion.ComprobanteRetencion;

import comprobantes.util.xml.Java2XML;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ArchivoUtils
{
   public ArchivoUtils() {}
   

    private static String realizaMarshal(Object comprobante, String pathArchivo)
      {
        String respuesta = null;
        if ((comprobante instanceof Factura)) {
          respuesta = Java2XML.marshalFactura((Factura)comprobante, pathArchivo);
        } else if ((comprobante instanceof NotaDebito)) {
          respuesta = Java2XML.marshalNotaDeDebito((NotaDebito)comprobante, pathArchivo);
        } else if ((comprobante instanceof NotaCredito)) {
          respuesta = Java2XML.marshalNotaDeCredito((NotaCredito)comprobante, pathArchivo);
        } else if ((comprobante instanceof ComprobanteRetencion)) {
          respuesta = Java2XML.marshalComprobanteRetencion((ComprobanteRetencion)comprobante, pathArchivo);
        } else if ((comprobante instanceof GuiaRemision)) {
          respuesta = Java2XML.marshalGuiaRemision((GuiaRemision)comprobante, pathArchivo);
        }
        return respuesta;
      }
   
    public static String crearArchivoXml2(String pathArchivo, Object objetoModelo, ClaveContingencia claveContingencia, Long secuencial, String tipoComprobante)
      {
        String respuestaCreacion = null;
        if (objetoModelo != null) {
          try
          {
            respuestaCreacion = realizaMarshal(objetoModelo, pathArchivo);
            if (respuestaCreacion == null)
            {
              if ((claveContingencia != null) && (claveContingencia.getCodigoComprobante() != null))
              {
                claveContingencia.setUsada("S");
                //new ClavesSQL().actualizaClave(claveContingencia);
              }
              //COmentado por ALex
              //new ComprobantesSQL().actualizaSecuencial(tipoComprobante, Long.valueOf(secuencial.longValue() + 1L));
            }
          }
          catch (Exception ex)
          {
            respuestaCreacion = "Error: crearArchivoXml2 - "+ex.getMessage();
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          respuestaCreacion = "Ingrese los campos obligatorios del comprobante";
        }
        return respuestaCreacion;
      }

 }


package comprobantes.util.xml;
 
import comprobantes.doc.factura.Factura;
import comprobantes.doc.guiaRemision.GuiaRemision;
import comprobantes.doc.notacredito.NotaCredito;
import comprobantes.doc.notadebito.NotaDebito;
import comprobantes.doc.retencion.ComprobanteRetencion;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Java2XML
{
  public Java2XML() {}
  
  public static String marshalFactura(Factura comprobante, String pathArchivoSalida)
  {
    String respuesta = null;
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { Factura.class });
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty("jaxb.encoding", "UTF-8");
      marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
      marshaller.marshal(comprobante, out);
    }
    catch (Exception ex)
    {
      Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, null, ex);
      return ex.getMessage();
    }
    return respuesta;
  }
  
  public static String marshalNotaDeDebito(NotaDebito comprobante, String pathArchivoSalida)
  {
    String respuesta = null;
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { NotaDebito.class });
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty("jaxb.encoding", "UTF-8");
      marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
      marshaller.marshal(comprobante, out);
    }
    catch (Exception ex)
    {
      Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, null, ex);
      return ex.getMessage();
    }
    return respuesta;
  }
  
  public static String marshalNotaDeCredito(NotaCredito comprobante, String pathArchivoSalida)
  {
    String respuesta = null;
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { NotaCredito.class });
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty("jaxb.encoding", "UTF-8");
      marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
      marshaller.marshal(comprobante, out);
    }
    catch (Exception ex)
    {
      Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, null, ex);
      return ex.getMessage();
    }
    return respuesta;
  }
  
  public static String marshalComprobanteRetencion(ComprobanteRetencion comprobante, String pathArchivoSalida)
  {
    String respuesta = null;
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { ComprobanteRetencion.class });
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty("jaxb.encoding", "UTF-8");
      marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
      marshaller.marshal(comprobante, out);
    }
    catch (Exception ex)
    {
      Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, null, ex);
      return ex.getMessage();
    }
    return respuesta;
  }
  
  public static String marshalGuiaRemision(GuiaRemision comprobante, String pathArchivoSalida)
  {
    String respuesta = null;
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { GuiaRemision.class });
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty("jaxb.encoding", "UTF-8");
      marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
      marshaller.marshal(comprobante, out);
    }
    catch (Exception ex)
    {
      Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, null, ex);
      return ex.getMessage();
    }
    return respuesta;
  }
  
  /*public static String marshalRespuestaSolicitud(RespuestaSolicitud respuesta, String pathArchivoSalida)
  {
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { RespuestaSolicitud.class });
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty("jaxb.encoding", "UTF-8");
      marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
      marshaller.marshal(respuesta, out);
    }
    catch (Exception ex)
    {
      Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, null, ex);
      return ex.getMessage();
    }
    return null;
  }*/
}

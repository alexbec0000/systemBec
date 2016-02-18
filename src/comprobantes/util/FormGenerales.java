 package comprobantes.util;
 
 import comprobantes.entidades.ClaveContingencia;
 import comprobantes.entidades.Emisor;

 import java.util.Calendar;
 import java.util.Date;

 public class FormGenerales
 {
   private static char SEPARADOR_DECIMAL = '.';

   public FormGenerales() {}

   public static String obtieneTipoDeComprobante(String claveDeAcceso)
   {
     String abreviatura = null;
     
     if ((claveDeAcceso != null) && (claveDeAcceso.length() == 49)) {
       String tipo = claveDeAcceso.substring(8, 10);
       
       if (tipo.equals(TipoComprobanteEnum.FACTURA.getCode())) {
         abreviatura = TipoComprobanteEnum.FACTURA.getDescripcion();
       } else if (tipo.equals(TipoComprobanteEnum.NOTA_DE_DEBITO.getCode())) {
         abreviatura = TipoComprobanteEnum.NOTA_DE_DEBITO.getDescripcion();
       } else if (tipo.equals(TipoComprobanteEnum.NOTA_DE_CREDITO.getCode())) {
         abreviatura = TipoComprobanteEnum.NOTA_DE_CREDITO.getDescripcion();
       } else if (tipo.equals(TipoComprobanteEnum.GUIA_DE_REMISION.getCode())) {
         abreviatura = TipoComprobanteEnum.GUIA_DE_REMISION.getDescripcion();
       } else if (tipo.equals(TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode())) {
         abreviatura = TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getDescripcion();
       } else if (tipo.equals(TipoComprobanteEnum.LOTE.getCode())) {
         abreviatura = TipoComprobanteEnum.LOTE.getDescripcion();
       }
     }
     return abreviatura;
   }

   public static Date eliminaHora(Date date)
   {
     Calendar cal = Calendar.getInstance();
     cal.setTime(date);
     cal.set(11, 0);
     cal.set(12, 0);
     cal.set(13, 0);
     cal.set(14, 0);
     return cal.getTime();
   }
   
   public static String insertarCaracteres(String cadenaLarga, String aInsertar, int longitud)
   {
     StringBuilder sb = new StringBuilder(cadenaLarga);
     
     int i = 0;
     while ((i = sb.indexOf(" ", i + longitud)) != -1) {
       sb.replace(i, i + 1, aInsertar);
     }
     
     return sb.toString();
   }
   
   public ClaveContingencia obtieneClaveDeAcceso(String secuencialComprobante, Emisor emisor, String serie, String claveDeAcceso, Date fechaEmision, String tipoComprobante)
   {
     ClaveContingencia clave = new ClaveContingencia();
     if (emisor != null) {
       serie = emisor.getCodigoEstablecimiento().concat(emisor.getCodPuntoEmision());
     }
     
     claveDeAcceso = new ClaveDeAcceso().generaClave(fechaEmision, tipoComprobante, emisor.getRuc(), emisor.getTipoAmbiente(), serie, secuencialComprobante, emisor.getClaveInterna(), emisor.getTipoEmision());
     clave.setCodigoComprobante(claveDeAcceso);
     
     /*
     if ((emisor != null) && (emisor.getTipoEmision().equals("1"))) {
       claveDeAcceso = new ClaveDeAcceso().generaClave(fechaEmision, tipoComprobante, emisor.getRuc(), emisor.getTipoAmbiente(), serie, secuencialComprobante, emisor.getClaveInterna(), "1");
       
       clave.setCodigoComprobante(claveDeAcceso);
     } else if ((emisor != null) && ((emisor.getTipoEmision().equals("2")) || (emisor.getTipoEmision().equals("3"))))
     {
       try {
         //clave = new ClavesSQL().obtenerUltimaNoUsada();
         
         if (clave.getClave() != null) {
           claveDeAcceso = new ClaveDeAcceso().generaClaveContingencia(fechaEmision, tipoComprobante, clave.getClave(), emisor.getTipoEmision());
           
           clave.setCodigoComprobante(claveDeAcceso);
         } else {
           System.out.println( "No existen claves de contingencia, por favor cargue claves en el Sistema o cambie su estado de Emisión a: NORMAL - INFORMACION IMPORTANTE");
         }
       } catch (Exception ex) {
         //Logger.getLogger(FacturaView.class.getName()).log(Level.SEVERE, null, ex);
           System.out.println("Error: "+ex.getMessage());
       }
     }
     */
     return clave;
   }

   
 }

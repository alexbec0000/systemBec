/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generaXML;

import comprobantes.entidades.ClaveContingencia;
import comprobantes.entidades.Clientes;
import comprobantes.entidades.Emisor;
import comprobantes.entidades.Producto;
import comprobantes.modelo.InfoTributaria;
import comprobantes.modelo.SubtotalImpuesto;
import comprobantes.doc.factura.Factura;
import comprobantes.doc.factura.Impuesto;
import comprobantes.doc.factura.ObjectFactory;
import comprobantes.table.DatosAdicionalesTableModel;
import comprobantes.table.FacturaModel;
import comprobantes.util.ArchivoUtils;
import comprobantes.util.Constantes;
import comprobantes.util.FormGenerales;
import comprobantes.util.TipoCompradorEnum;
import comprobantes.util.TipoComprobanteEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author abecerra
 */
public class GeneraFacturaXMLauto {
    
   private long VALOR_MAXIMO_CONSUMIDOR_FINAL = 200L;
   private Long secuencial = null;
   private String secuencialComprobante = null;
   private String razonSocialComprador = null;
   private String identificacionComprador = null;
   private InfoTributaria infoTributaria = null;
   private Factura.InfoFactura infoFactura = null;
   private Emisor emisor = null;
   private Clientes cliente = null;
   private String claveDeAcceso = null;
   private Date fechaEmision = null;
   private BigDecimal subtotalSinImpuestos = BigDecimal.ZERO;
   private final BigDecimal valorDescuento = BigDecimal.ZERO;
   private BigDecimal totalValorFactura = BigDecimal.ZERO;
   private ClaveContingencia claveContingencia = new ClaveContingencia();
   private DatosAdicionalesTableModel modeloDatosAdicionales = null;
   private FacturaModel modeloDetalle = null;
   private Producto producto = null;
   private ObjectFactory facturaFactory = null;

   public GeneraFacturaXMLauto(Date fechaEmision, String secuenciaFactura, String subtotal, String valorTotal, Emisor emisor, Clientes cliente)
   {
       this.emisor=emisor;
       this.cliente=cliente;
       this.secuencial = Long.parseLong(secuenciaFactura);
       this.secuencialComprobante = String.format("%09d", new Object[] { Long.valueOf(this.secuencial.longValue()) });
       this.fechaEmision=fechaEmision;//new Date();      
       actualizaClaveDeAcceso();
       this.subtotalSinImpuestos = new BigDecimal(subtotal);
       this.totalValorFactura=new BigDecimal(valorTotal);
       
       this.modeloDetalle = new FacturaModel();

       this.modeloDatosAdicionales=new DatosAdicionalesTableModel();
       try{
        if(this.cliente.getDireccion().length()>0)
             this.modeloDatosAdicionales.addRow("Dirección", this.cliente.getDireccion());
       }
       catch(Exception ex){}
       try{
        if(this.cliente.getTelefonoConvencional().length()>0)
             this.modeloDatosAdicionales.addRow("Teléfono", this.cliente.getTelefonoConvencional());
       }
       catch(Exception ex){}
       try{
        if(this.cliente.getCorreo().length()>0)
             this.modeloDatosAdicionales.addRow("Email", this.cliente.getCorreo());
       }
       catch(Exception ex){}
       try{
        if(this.cliente.getObservacion().length()>0)
             this.modeloDatosAdicionales.addRow("Observación", this.cliente.getObservacion());
       }
       catch(Exception ex){}
       
       this.facturaFactory = new ObjectFactory();
   }
   
   public void agregarDetalle(Producto prod)
   {
       this.producto = prod;
       this.modeloDetalle.addRow(producto);
   }
   
   public String generarFactura(String directorio)
   {
     String respuesta = null;
     String archivoACrear = null;
     Factura facturaLLena = null;
     try
     {
       if ((this.modeloDetalle.getRowCount() > 0))
       {
         facturaLLena = generarComprobante();
         archivoACrear=directorio+this.claveDeAcceso + ".xml";
         respuesta = ArchivoUtils.crearArchivoXml2(archivoACrear, facturaLLena, this.claveContingencia, this.secuencial, TipoComprobanteEnum.FACTURA.getCode());
         if (respuesta != null ) 
             System.out.println("Error al tratar de crear el archivo correspondiente al comprobante:\n" + respuesta+ " Se ha producido un error ");
         
       } else {
           System.out.println("\nAl menos debe añadir un item al comprobante y todas las columnas de ICE deben estar llenas"+ ">> COMPROBANTE VACIO");

       }
     }
     catch (Exception ex) {
        System.out.println("Error generarFactura: "+ex.getMessage());
     }
     return archivoACrear;
   }

   private Factura generarComprobante()
   {
     Factura factura = null;
     if (!llenarObjetoComprobante())
     {
       Factura.Detalles detalles = generarDetalle();
       Factura.InfoAdicional informacion = generarInformacionAdicional();
       
       factura = new Factura();
       factura.setInfoTributaria(this.infoTributaria);
       factura.setInfoFactura(this.infoFactura);
       
       if (detalles != null) {
         factura.setDetalles(detalles);
       }
       if (informacion.getCampoAdicional().size() > 0) {
         factura.setInfoAdicional(informacion);
       }
       factura.setVersion("1.0.0");
       factura.setId("comprobante");
     }
     return factura;
   }
    
   private boolean llenarObjetoComprobante()
   {
     boolean error = false;
     this.infoTributaria = new InfoTributaria();
     this.infoTributaria.setSecuencial(this.secuencialComprobante);
     this.infoTributaria.setAmbiente(this.emisor.getTipoAmbiente());
     this.infoTributaria.setTipoEmision(this.emisor.getTipoEmision());
     this.infoTributaria.setRazonSocial(this.emisor.getRazonSocial());
     this.infoTributaria.setRuc(this.emisor.getRuc());
     this.infoTributaria.setCodDoc(TipoComprobanteEnum.FACTURA.getCode());
     this.infoTributaria.setEstab(this.emisor.getCodigoEstablecimiento());
     this.infoTributaria.setPtoEmi(this.emisor.getCodPuntoEmision());
     this.infoTributaria.setDirMatriz(this.emisor.getDireccionMatriz());
     
     if (this.claveDeAcceso != null) {
       this.infoTributaria.setClaveAcceso(this.claveDeAcceso);
     } else {
         System.out.println("\nLa clave de Acceso no puede ser nula"+ " >>Se ha producido un error ");
       error = true;
     }
     
     if ((this.emisor.getNombreComercial() != null) && (!this.emisor.getNombreComercial().isEmpty())) {
       this.infoTributaria.setNombreComercial(this.emisor.getNombreComercial());
     }
     
     this.infoFactura = this.facturaFactory.createFacturaInfoFactura();
     this.infoFactura.setFechaEmision(Constantes.dateFormat.format(FormGenerales.eliminaHora(this.fechaEmision)));
     
     if ((this.emisor.getDirEstablecimiento() != null) && (!this.emisor.getDirEstablecimiento().isEmpty())) {
       this.infoFactura.setDirEstablecimiento(this.emisor.getDirEstablecimiento());
     }
     //String guiaRemision = "001-001-000000122";
     String guiaRemision = "00";
     if (guiaRemision.length() == 17) {
       this.infoFactura.setGuiaRemision(guiaRemision);
     } else if (guiaRemision.length() != 2) {
         System.out.println("Guía de Remisión debe ser de 15 números: ej: 123-123-123456789"+ " >>Se ha producido un error ");
      
       error = true;
     }
    
       this.razonSocialComprador = this.cliente.getApellido();
       this.identificacionComprador = this.cliente.getNumeroIdentificacio();
       //this.infoFactura.setTipoIdentificacionComprador(TipoCompradorEnum.CONSUMIDOR_FINAL.getCode());
       this.infoFactura.setTipoIdentificacionComprador(TipoCompradorEnum.retornaCodigo(this.identificacionComprador));

       if (this.totalValorFactura.compareTo(BigDecimal.valueOf(this.VALOR_MAXIMO_CONSUMIDOR_FINAL)) == 1
               && this.infoFactura.getTipoIdentificacionComprador().equals(TipoCompradorEnum.CONSUMIDOR_FINAL.getCode())) {
         System.out.println("No se pueden emitir facturas mayores a USD 200.00 donde el comprador conste como: CONSUMIDOR FINAL"+ " >>Se ha producido un error ");
         error = true;
       }

     this.infoFactura.setIdentificacionComprador(this.identificacionComprador);
     this.infoFactura.setRazonSocialComprador(this.razonSocialComprador);
     this.infoFactura.setTotalSinImpuestos(this.subtotalSinImpuestos);
     this.infoFactura.setTotalDescuento(this.valorDescuento);
     this.infoFactura.setPropina(BigDecimal.ZERO.setScale(2));

     this.infoFactura.setImporteTotal(this.totalValorFactura);
     this.infoFactura.setMoneda("DOLAR");
     this.infoFactura.setTotalConImpuestos(generaTotalesImpuesto());
     
     if ((this.emisor.getContribuyenteEspecial() != null) && (!this.emisor.getContribuyenteEspecial().isEmpty())) {
       this.infoFactura.setContribuyenteEspecial(this.emisor.getContribuyenteEspecial());
     }
     if (this.emisor.getLlevaContabilidad() != null) {
       if (this.emisor.getLlevaContabilidad().equals("SI")) {
         this.infoFactura.setObligadoContabilidad("SI");
       } else {
         this.infoFactura.setObligadoContabilidad("NO");
       }
     }
     return error;
   }
   
    private Factura.InfoFactura.TotalConImpuestos generaTotalesImpuesto()
   {
     Factura.InfoFactura.TotalConImpuestos respuesta = this.facturaFactory.createFacturaInfoFacturaTotalConImpuestos();
     Factura.InfoFactura.TotalConImpuestos.TotalImpuesto item = this.facturaFactory.createFacturaInfoFacturaTotalConImpuestosTotalImpuesto();
     
     if (this.modeloDetalle.getListaIva0().size() > 0) {
       item = obtieneTotal(this.modeloDetalle.getListaIva0());
       respuesta.getTotalImpuesto().add(item);
     }
     if (this.modeloDetalle.getListaIva12().size() > 0) {
       item = obtieneTotal(this.modeloDetalle.getListaIva12());
       respuesta.getTotalImpuesto().add(item);
     }
     if (this.modeloDetalle.getListaNoIva().size() > 0) {
       item = obtieneTotal(this.modeloDetalle.getListaNoIva());
       respuesta.getTotalImpuesto().add(item);
     }
     
     if (this.modeloDetalle.getListaICE().size() > 0) {
       respuesta.getTotalImpuesto().addAll(obtieneTotalesICE(this.modeloDetalle.getListaICE()));
     }
     
     return respuesta;
   }
    
   private List<Factura.InfoFactura.TotalConImpuestos.TotalImpuesto> obtieneTotalesICE(List<SubtotalImpuesto> lista)
   {
     List<Factura.InfoFactura.TotalConImpuestos.TotalImpuesto> respuesta = new ArrayList();
     
     Factura.InfoFactura.TotalConImpuestos.TotalImpuesto totalImpuesto = null;
     
     BigDecimal baseImponibleGrupo = BigDecimal.ZERO;
     BigDecimal valorTotalGrupo = BigDecimal.ZERO;
     String codigoGrupo = null;
     ArrayList encontrados = new ArrayList();
     
     for (SubtotalImpuesto subtotal : lista) {
       totalImpuesto = this.facturaFactory.createFacturaInfoFacturaTotalConImpuestosTotalImpuesto();
       codigoGrupo = subtotal.getCodigo();
       java.util.Collections.sort(encontrados);
       baseImponibleGrupo = BigDecimal.ZERO;
       valorTotalGrupo = BigDecimal.ZERO;
       if (java.util.Collections.binarySearch(encontrados, codigoGrupo) < 0) {
         for (SubtotalImpuesto i : lista) {
           if (i.getCodigo().equals(codigoGrupo)) {
             baseImponibleGrupo = baseImponibleGrupo.add(i.getBaseImponible());
             valorTotalGrupo = valorTotalGrupo.add(i.getSubtotal());
           }
         }
         totalImpuesto.setCodigo(String.valueOf(subtotal.getCodigoImpuesto()));
         totalImpuesto.setCodigoPorcentaje(codigoGrupo);
         totalImpuesto.setBaseImponible(baseImponibleGrupo);
         totalImpuesto.setValor(valorTotalGrupo);
         respuesta.add(totalImpuesto);
         encontrados.add(codigoGrupo);
       }
     }
     return respuesta;
  }
   
  private Factura.InfoFactura.TotalConImpuestos.TotalImpuesto obtieneTotal(List<SubtotalImpuesto> lista)
   {
     Factura.InfoFactura.TotalConImpuestos.TotalImpuesto impuesto = new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
     BigDecimal baseImponible = BigDecimal.ZERO;
     BigDecimal total = BigDecimal.ZERO;
     
     SubtotalImpuesto primer = (SubtotalImpuesto)lista.get(0);
     
     impuesto.setCodigo(String.valueOf(primer.getCodigoImpuesto()));
     impuesto.setCodigoPorcentaje(primer.getCodigo());
     
     for (SubtotalImpuesto item : lista) {
       total = total.add(item.getSubtotal());
       baseImponible = baseImponible.add(item.getBaseImponible());
     }
     
     impuesto.setBaseImponible(baseImponible);
     impuesto.setValor(total);
     
     return impuesto;
   }   
   
  private Factura.Detalles generarDetalle()
   {
     Factura.Detalles resultado = this.facturaFactory.createFacturaDetalles();
     for (int i = 0; i < this.modeloDetalle.getRowCount(); i++) {
       try {
         BigDecimal descuento = (BigDecimal)this.modeloDetalle.getValueAt(i, 5);
         Factura.Detalles.Detalle detalle = this.facturaFactory.createFacturaDetallesDetalle();
         detalle.setCodigoPrincipal(this.modeloDetalle.getValueAt(i, 1).toString());
         detalle.setCodigoAuxiliar(this.modeloDetalle.getValueAt(i, 2).toString());
         detalle.setDescripcion(this.modeloDetalle.getValueAt(i, 3).toString());
         detalle.setCantidad(new BigDecimal(((Double)this.modeloDetalle.getValueAt(i, 0)).doubleValue()).setScale(2, RoundingMode.HALF_UP));
         detalle.setPrecioUnitario((BigDecimal)this.modeloDetalle.getValueAt(i, 4));
         detalle.setDescuento(descuento.setScale(2, RoundingMode.HALF_UP));
         detalle.setPrecioTotalSinImpuesto((BigDecimal)this.modeloDetalle.getValueAt(i, 6));
         String obs=this.modeloDetalle.getValueAt(i, 10).toString();
         Producto prod = new Producto();
         prod.setCodigoPrincipal(detalle.getCodigoPrincipal());
         prod.setCodigoAuxiliar(detalle.getCodigoAuxiliar());
         prod.setNombre(detalle.getDescripcion());
         detalle.setImpuestos(obtenerImpuestosProducto(prod));
           
         if (obs.length()>0) {
            Factura.Detalles.Detalle.DetallesAdicionales obj = this.facturaFactory.createFacturaDetallesDetalleDetallesAdicionales();
            Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional det = this.facturaFactory.createFacturaDetallesDetalleDetallesAdicionalesDetAdicional();
            det.setNombre("Info");
            det.setValor(obs);
            obj.getDetAdicional().add(det);
            detalle.setDetallesAdicionales(obj);
         }
         resultado.getDetalle().add(detalle);
       } catch (Exception ex) {
         System.out.println("Error: generarDetalle "+ex.getMessage());
       }
     }
     return resultado;
   }
   
  private Factura.Detalles.Detalle.Impuestos obtenerImpuestosProducto(Producto producto)
  {
    Factura.Detalles.Detalle.Impuestos result = this.facturaFactory.createFacturaDetallesDetalleImpuestos();
    List<SubtotalImpuesto> lista = new ArrayList();
    if (this.modeloDetalle.getListaIva0().size() > 0) {
      lista.addAll(obtieneItems(this.modeloDetalle.getListaIva0(), producto));
    }
    if (this.modeloDetalle.getListaIva12().size() > 0) {
      lista.addAll(obtieneItems(this.modeloDetalle.getListaIva12(), producto));
    }
    if (this.modeloDetalle.getListaNoIva().size() > 0) {
      lista.addAll(obtieneItems(this.modeloDetalle.getListaNoIva(), producto));
    }
    if (this.modeloDetalle.getListaICE().size() > 0) {
      lista.addAll(obtieneItems(this.modeloDetalle.getListaICE(), producto));
    }
    if (this.modeloDetalle.getListaExentoIVA().size() > 0) {
      lista.addAll(obtieneItems(this.modeloDetalle.getListaExentoIVA(), producto));
    }
    if (this.modeloDetalle.getListaIRBPNR().size() > 0) {
      lista.addAll(obtieneItems(this.modeloDetalle.getListaIRBPNR(), producto));
    }
    for (SubtotalImpuesto s : lista)
    {
      Impuesto i = new Impuesto();
      i.setCodigo(String.valueOf(s.getCodigoImpuesto()));
      i.setCodigoPorcentaje(s.getCodigo());
      i.setTarifa(s.getPorcentaje().multiply(BigDecimal.valueOf(100L).setScale(0, RoundingMode.HALF_UP)));
      i.setBaseImponible(s.getBaseImponible());
      i.setValor(s.getSubtotal());
      
      result.getImpuesto().add(i);
    }
    return result;
  }
   
  private List<SubtotalImpuesto> obtieneItems(List<SubtotalImpuesto> lista, Producto producto)
  {
    List<SubtotalImpuesto> resultado = new ArrayList();
    for (SubtotalImpuesto s : lista) {
      if (s.verificarProducto(producto)) {
        resultado.add(s);
      }
    }
    return resultado;
  }
   
  private Factura.InfoAdicional generarInformacionAdicional()
   {
     Factura.InfoAdicional info = this.facturaFactory.createFacturaInfoAdicional();
     
     for (int i = 0; i < this.modeloDatosAdicionales.getRowCount(); i++) {
       Factura.InfoAdicional.CampoAdicional detalle = new Factura.InfoAdicional.CampoAdicional();
       detalle.setNombre((String)this.modeloDatosAdicionales.getValueAt(i, 0));
       detalle.setValue((String)this.modeloDatosAdicionales.getValueAt(i, 1));
       info.getCampoAdicional().add(detalle);
     }
     return info;
   }  
   
   private void actualizaClaveDeAcceso()
   {
        String serie="";
        this.claveDeAcceso = null;
        this.claveContingencia = new FormGenerales().obtieneClaveDeAcceso(this.secuencialComprobante, this.emisor, serie, this.claveDeAcceso, this.fechaEmision, TipoComprobanteEnum.FACTURA.getCode());
        if ((this.claveContingencia.getCodigoComprobante() != null) && (!this.claveContingencia.getCodigoComprobante().isEmpty())) {
          this.claveDeAcceso = this.claveContingencia.getCodigoComprobante();
        }
    }
     
}

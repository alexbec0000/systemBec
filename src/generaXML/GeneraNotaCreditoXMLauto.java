/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generaXML;

import comprobantes.doc.notacredito.Impuesto;
import comprobantes.doc.notacredito.NotaCredito;
import comprobantes.doc.notacredito.ObjectFactory;
import comprobantes.doc.notacredito.TotalConImpuestos;
import comprobantes.entidades.ClaveContingencia;
import comprobantes.entidades.Clientes;
import comprobantes.entidades.Emisor;
import comprobantes.entidades.Producto;
import comprobantes.modelo.InfoTributaria;
import comprobantes.modelo.SubtotalImpuesto;
import comprobantes.table.DatosAdicionalesTableModel;
import comprobantes.table.NotaCreditoModel;
import comprobantes.util.ArchivoUtils;
import comprobantes.util.Constantes;
import comprobantes.util.FormGenerales;
import comprobantes.util.TipoCompradorEnum;
import comprobantes.util.TipoComprobanteEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author abecerra
 */
public class GeneraNotaCreditoXMLauto {

    private long VALOR_MAXIMO_CONSUMIDOR_FINAL = 200L;
    private String documento = null;
    private String secuencialDocumentoFULL=null;
    private String fechaDocumento=null;
    private String motivoAnulacion=null;
    private String razonSocialComprador = null;
    private String identificacionComprador = null;
    private Date fechaEmision;
    private Long secuencial = null;
    private String secuencialComprobante = null;
    private String claveDeAcceso = null;
    private ClaveContingencia claveContingencia = new ClaveContingencia();
    
    private Clientes clienteSeleccionado;
    private InfoTributaria infoTributaria = null;
    private NotaCredito.InfoNotaCredito infoNotaCredito = null;
    private Emisor emisor = null;
    private DatosAdicionalesTableModel modeloDatosAdicionales;
    private NotaCreditoModel modeloDetalle;
    private ObjectFactory factory;
    private Producto producto = null;
    
    private BigDecimal subtotalSinImpuestos = BigDecimal.ZERO;
    private BigDecimal totalValorComprobante = BigDecimal.ZERO;

    public GeneraNotaCreditoXMLauto(Date fechaEmision, String secuenciaNC, String subtotal, String valorTotal,
            Emisor emisor, Clientes cliente,
            String documento, String secuencialDocumentoFULL, String fechaDocumento, String motivoAnulacion) 
    {
       this.documento=documento;
       this.secuencialDocumentoFULL=secuencialDocumentoFULL;
       this.fechaDocumento=fechaDocumento;
       this.motivoAnulacion=motivoAnulacion;
       this.fechaEmision=fechaEmision;//new Date();
       this.secuencial = Long.parseLong(secuenciaNC);
       this.secuencialComprobante = String.format("%09d", new Object[] { Long.valueOf(this.secuencial.longValue()) });
       this.emisor=emisor;
       this.clienteSeleccionado=cliente;
       this.totalValorComprobante=new BigDecimal(valorTotal);
       this.subtotalSinImpuestos=new BigDecimal(subtotal);
       actualizaClaveDeAcceso();
       this.modeloDetalle = new NotaCreditoModel();
       
       this.modeloDatosAdicionales=new DatosAdicionalesTableModel();
       try{
        if(this.clienteSeleccionado.getDireccion().length()>0)
             this.modeloDatosAdicionales.addRow("Dirección", this.clienteSeleccionado.getDireccion());
       }
       catch(Exception ex){}
       try{
        if(this.clienteSeleccionado.getTelefonoConvencional().length()>0)
             this.modeloDatosAdicionales.addRow("Teléfono", this.clienteSeleccionado.getTelefonoConvencional());
       }
       catch(Exception ex){}
       try{
        if(this.clienteSeleccionado.getCorreo().length()>0)
             this.modeloDatosAdicionales.addRow("Email", this.clienteSeleccionado.getCorreo());
       }
       catch(Exception ex){}
       try{
        if(this.clienteSeleccionado.getObservacion().length()>0)
             this.modeloDatosAdicionales.addRow("Observación", this.clienteSeleccionado.getObservacion());
       }
       catch(Exception ex){}
       
       this.factory = new ObjectFactory();
    }
    
    public String generarNotaCredito(String directorio)
    {
      String respuesta = null;
      String archivoACrear = null;
      NotaCredito notaLlena = null;
      try
      {
        if ((this.modeloDetalle.getRowCount() > 0))
        {
          notaLlena = generarComprobante();
          archivoACrear=directorio+this.claveDeAcceso + ".xml";
          respuesta = ArchivoUtils.crearArchivoXml2(archivoACrear, notaLlena, this.claveContingencia, this.secuencial, TipoComprobanteEnum.NOTA_DE_CREDITO.getCode());
          if (respuesta != null) 		
           System.out.println("Error al tratar de crear el archivo correspondiente al comprobante:\n" + respuesta+ " Se ha producido un error ");
          
        } else {
            System.out.println("\nAl menos debe añadir un item al comprobante y todas las columnas de ICE deben estar llenas"+ ">> COMPROBANTE VACIO");

        }
      }
      catch (Exception ex) {
         System.out.println("Error generarNotaCredito: "+ex.getMessage());
      }
      return archivoACrear;
    }

    public void agregarDetalle(Producto prod)
    {
        this.producto = prod;
        this.modeloDetalle.addRow(producto);
    }
 
    private boolean llenarObjetoComprobante()
    {
      StringBuilder mensajes = new StringBuilder();
      boolean error = false;
      this.infoTributaria = new InfoTributaria();
      this.infoTributaria.setSecuencial(this.secuencialComprobante);
      this.infoTributaria.setAmbiente(this.emisor.getTipoAmbiente());
      this.infoTributaria.setTipoEmision(this.emisor.getTipoEmision());
      this.infoTributaria.setRazonSocial(this.emisor.getRazonSocial());
      this.infoTributaria.setRuc(this.emisor.getRuc());
      this.infoTributaria.setCodDoc(TipoComprobanteEnum.NOTA_DE_CREDITO.getCode());
      this.infoTributaria.setEstab(this.emisor.getCodigoEstablecimiento());
      this.infoTributaria.setPtoEmi(this.emisor.getCodPuntoEmision());
      this.infoTributaria.setDirMatriz(this.emisor.getDireccionMatriz());
      if (this.claveDeAcceso != null)
      {
        this.infoTributaria.setClaveAcceso(this.claveDeAcceso);
      }
      else
      {
        System.out.println("La clave de Acceso no puede ser nula"+ " >>Se ha producido un error ");
        error = true;
      }
      if ((this.emisor.getNombreComercial() != null) && (!this.emisor.getNombreComercial().isEmpty())) {
        this.infoTributaria.setNombreComercial(this.emisor.getNombreComercial());
      }
      this.infoNotaCredito = this.factory.createNotaCreditoInfoNotaCredito();
      this.infoNotaCredito.setFechaEmision(Constantes.dateFormat.format(FormGenerales.eliminaHora(this.fechaEmision)));
      if ((this.emisor.getDirEstablecimiento() != null) && (!this.emisor.getDirEstablecimiento().isEmpty())) {
        this.infoNotaCredito.setDirEstablecimiento(this.emisor.getDirEstablecimiento());
      }
      
      this.razonSocialComprador = this.clienteSeleccionado.getApellido();
      this.identificacionComprador = this.clienteSeleccionado.getNumeroIdentificacio();
      this.infoNotaCredito.setTipoIdentificacionComprador(TipoCompradorEnum.retornaCodigo(this.identificacionComprador));

      if (this.totalValorComprobante.compareTo(BigDecimal.valueOf(this.VALOR_MAXIMO_CONSUMIDOR_FINAL)) == 1
               && this.infoNotaCredito.getTipoIdentificacionComprador().equals(TipoCompradorEnum.CONSUMIDOR_FINAL.getCode())) {
          mensajes.append("No se pueden emitir facturas mayores a USD 200.00 donde el comprador conste como: CONSUMIDOR FINAL");
          error = true;
       }
      this.infoNotaCredito.setIdentificacionComprador(this.identificacionComprador);
      this.infoNotaCredito.setRazonSocialComprador(this.razonSocialComprador);
      this.infoNotaCredito.setTotalSinImpuestos(this.subtotalSinImpuestos);
      this.infoNotaCredito.setValorModificacion(this.totalValorComprobante);
      this.infoNotaCredito.setMoneda("DOLAR");
      this.infoNotaCredito.setTotalConImpuestos(generaTotalesImpuesto());
      if (this.documento.equals("FACTURA")) {
        this.infoNotaCredito.setCodDocModificado(TipoComprobanteEnum.FACTURA.getCode());
      }
      this.infoNotaCredito.setNumDocModificado(this.secuencialDocumentoFULL);
      this.infoNotaCredito.setFechaEmisionDocSustento(this.fechaDocumento);
      this.infoNotaCredito.setMotivo(this.motivoAnulacion);
      if ((this.emisor.getContribuyenteEspecial() != null) && (!this.emisor.getContribuyenteEspecial().isEmpty())) {
        this.infoNotaCredito.setContribuyenteEspecial(this.emisor.getContribuyenteEspecial());
      }
      if (this.emisor.getLlevaContabilidad() != null) {
        if (this.emisor.getLlevaContabilidad().equals("SI")) {
          this.infoNotaCredito.setObligadoContabilidad("SI");
        } else {
          this.infoNotaCredito.setObligadoContabilidad("NO");
        }
      }
      if (error == true) {
        System.out.println(mensajes.toString()+ " >>Se ha producido un error ");
      }
      return error;
    }
  
    private NotaCredito generarComprobante()
    {
        NotaCredito notaCredito = null;
        if (!llenarObjetoComprobante())
        {
          notaCredito = this.factory.createNotaCredito();
          NotaCredito.InfoAdicional informacion = generarInformacionAdicional();
          notaCredito.setInfoTributaria(this.infoTributaria);
          notaCredito.setInfoNotaCredito(this.infoNotaCredito);
          notaCredito.setDetalles(generarDetalle());
          if (informacion.getCampoAdicional().size() > 0) {
            notaCredito.setInfoAdicional(informacion);
          }
          notaCredito.setVersion("1.0.0");
          notaCredito.setId("comprobante");
        }
        return notaCredito;
    }
    
    private NotaCredito.Detalles generarDetalle()
    {
        NotaCredito.Detalles resultado = this.factory.createNotaCreditoDetalles();
        for (int i = 0; i < this.modeloDetalle.getRowCount(); i++) {
          try
          {
            NotaCredito.Detalles.Detalle detalle = this.factory.createNotaCreditoDetallesDetalle();

            detalle.setCodigoInterno(this.modeloDetalle.getValueAt(i, 1).toString());
            detalle.setCodigoAdicional(this.modeloDetalle.getValueAt(i, 2).toString());
            detalle.setDescripcion(this.modeloDetalle.getValueAt(i, 3).toString());
            BigDecimal descuento = (BigDecimal)this.modeloDetalle.getValueAt(i, 5);
            detalle.setCantidad(new BigDecimal(Double.parseDouble(this.modeloDetalle.getValueAt(i, 0).toString())).setScale(2, RoundingMode.HALF_UP));
            detalle.setPrecioUnitario((BigDecimal)this.modeloDetalle.getValueAt(i, 4));
            detalle.setDescuento(descuento);
            detalle.setPrecioTotalSinImpuesto((BigDecimal)this.modeloDetalle.getValueAt(i, 6));
            Producto prod = new Producto();
            prod.setCodigoPrincipal(detalle.getCodigoInterno());
            prod.setCodigoAuxiliar(detalle.getCodigoAdicional());
            prod.setNombre(detalle.getDescripcion());
            detalle.setImpuestos(obtenerImpuestosProducto(prod));
            String obs=this.modeloDetalle.getValueAt(i, 10).toString();
            if (obs.length()>0)
            {
                NotaCredito.Detalles.Detalle.DetallesAdicionales obj = this.factory.createNotaCreditoDetallesDetalleDetallesAdicionales();
                NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional det = this.factory.createNotaCreditoDetallesDetalleDetallesAdicionalesDetAdicional();
                det.setNombre("Info");
                det.setValor(obs);
                obj.getDetAdicional().add(det);
                detalle.setDetallesAdicionales(obj);
            }
            resultado.getDetalle().add(detalle);
          }
          catch (Exception ex)
          {
             System.out.println("Se ha producido un error "+ex.getMessage());
          }
        }
        return resultado;
    }
    
    private NotaCredito.Detalles.Detalle.Impuestos obtenerImpuestosProducto(Producto producto)
    {
      NotaCredito.Detalles.Detalle.Impuestos result = this.factory.createNotaCreditoDetallesDetalleImpuestos();
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
      if (this.modeloDetalle.getListaExentoIVA().size() > 0) {
        lista.addAll(obtieneItems(this.modeloDetalle.getListaExentoIVA(), producto));
      }
      if (this.modeloDetalle.getListaICE().size() > 0) {
        lista.addAll(obtieneItems(this.modeloDetalle.getListaICE(), producto));
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
  
  private NotaCredito.InfoAdicional generarInformacionAdicional()
  {
    NotaCredito.InfoAdicional info = this.factory.createNotaCreditoInfoAdicional();
    for (int i = 0; i < this.modeloDatosAdicionales.getRowCount(); i++)
    {
      NotaCredito.InfoAdicional.CampoAdicional detalle = this.factory.createNotaCreditoInfoAdicionalCampoAdicional();
      detalle.setNombre((String)this.modeloDatosAdicionales.getValueAt(i, 0));
      detalle.setValue((String)this.modeloDatosAdicionales.getValueAt(i, 1));
      info.getCampoAdicional().add(detalle);
    }
    return info;
  }
  
  private TotalConImpuestos.TotalImpuesto obtieneTotal(List<SubtotalImpuesto> lista)
  {
    TotalConImpuestos.TotalImpuesto impuesto = this.factory.createTotalConImpuestosTotalImpuesto();
    BigDecimal baseImponible = BigDecimal.ZERO;
    BigDecimal total = BigDecimal.ZERO;
    SubtotalImpuesto primer = (SubtotalImpuesto)lista.get(0);
    impuesto.setCodigo(String.valueOf(primer.getCodigoImpuesto()));
    impuesto.setCodigoPorcentaje(primer.getCodigo());
    if (String.valueOf("2").equals(primer.getCodigo()))
    {
      for (SubtotalImpuesto item : lista) {
        baseImponible = baseImponible.add(item.getBaseImponible());
      }
      total = baseImponible.multiply(primer.getPorcentaje()).setScale(2, RoundingMode.HALF_UP);
    }
    else
    {
      for (SubtotalImpuesto item : lista)
      {
        total = total.add(item.getSubtotal());
        baseImponible = baseImponible.add(item.getBaseImponible());
      }
    }
    impuesto.setBaseImponible(baseImponible);
    impuesto.setValor(total);
    return impuesto;
  }
  
  private TotalConImpuestos generaTotalesImpuesto()
  {
    TotalConImpuestos respuesta = this.factory.createTotalConImpuestos();
    TotalConImpuestos.TotalImpuesto item = this.factory.createTotalConImpuestosTotalImpuesto();
    if (this.modeloDetalle.getListaIva0().size() > 0)
    {
      item = obtieneTotal(this.modeloDetalle.getListaIva0());
      respuesta.getTotalImpuesto().add(item);
    }
    if (this.modeloDetalle.getListaIva12().size() > 0)
    {
      item = obtieneTotal(this.modeloDetalle.getListaIva12());
      respuesta.getTotalImpuesto().add(item);
    }
    if (this.modeloDetalle.getListaNoIva().size() > 0)
    {
      item = obtieneTotal(this.modeloDetalle.getListaNoIva());
      respuesta.getTotalImpuesto().add(item);
    }
    if (this.modeloDetalle.getListaExentoIVA().size() > 0)
    {
      item = obtieneTotal(this.modeloDetalle.getListaExentoIVA());
      respuesta.getTotalImpuesto().add(item);
    }
    if (this.modeloDetalle.getListaICE().size() > 0) {
      respuesta.getTotalImpuesto().addAll(obtieneTotales(this.modeloDetalle.getListaICE()));
    }
    if (this.modeloDetalle.getListaIRBPNR().size() > 0) {
      respuesta.getTotalImpuesto().addAll(obtieneTotales(this.modeloDetalle.getListaIRBPNR()));
    }
    return respuesta;
  }
  
  private List<TotalConImpuestos.TotalImpuesto> obtieneTotales(List<SubtotalImpuesto> lista)
  {
    List<TotalConImpuestos.TotalImpuesto> respuesta = new ArrayList();
    TotalConImpuestos.TotalImpuesto item = null;
    BigDecimal baseImponible = BigDecimal.ZERO;
    BigDecimal valorTotalGrupo = BigDecimal.ZERO;
    String codigoGrupo = null;
    ArrayList encontrados = new ArrayList();
    for (SubtotalImpuesto busc : lista)
    {
      baseImponible = BigDecimal.ZERO;
      valorTotalGrupo = BigDecimal.ZERO;
      item = this.factory.createTotalConImpuestosTotalImpuesto();
      codigoGrupo = busc.getCodigo();
      Collections.sort(encontrados);
      if (Collections.binarySearch(encontrados, codigoGrupo) < 0)
      {
        for (SubtotalImpuesto i : lista) {
          if (i.getCodigo().equals(codigoGrupo))
          {
            baseImponible = baseImponible.add(i.getBaseImponible());
            valorTotalGrupo = valorTotalGrupo.add(i.getSubtotal());
          }
        }
        item.setCodigo(String.valueOf(busc.getCodigoImpuesto()));
        item.setCodigoPorcentaje(codigoGrupo);
        item.setBaseImponible(baseImponible);
        item.setValor(valorTotalGrupo);
        respuesta.add(item);
        encontrados.add(codigoGrupo);
      }
    }
    return respuesta;
  }
    
   private void actualizaClaveDeAcceso()
   {
        String serie="";
        this.claveDeAcceso = null;
        this.claveContingencia = new FormGenerales().obtieneClaveDeAcceso(this.secuencialComprobante, this.emisor, serie, this.claveDeAcceso, this.fechaEmision, TipoComprobanteEnum.NOTA_DE_CREDITO.getCode());
        if ((this.claveContingencia.getCodigoComprobante() != null) && (!this.claveContingencia.getCodigoComprobante().isEmpty())) {
          this.claveDeAcceso = this.claveContingencia.getCodigoComprobante();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generaXML;

import comprobantes.doc.notadebito.Impuesto;
import comprobantes.doc.notadebito.NotaDebito;
import comprobantes.doc.notadebito.ObjectFactory;
import comprobantes.entidades.ClaveContingencia;
import comprobantes.entidades.Clientes;
import comprobantes.entidades.Emisor;
import comprobantes.entidades.ImpuestoValor;
import comprobantes.modelo.InfoTributaria;
import comprobantes.sql.ImpuestoValorSQL;
import comprobantes.table.DatosAdicionalesTableModel;
import comprobantes.table.NotaDebitoTableModel;
import comprobantes.util.ArchivoUtils;
import comprobantes.util.Constantes;
import comprobantes.util.FormGenerales;
import comprobantes.util.TipoCompradorEnum;
import comprobantes.util.TipoComprobanteEnum;
import comprobantes.util.TipoImpuestoIvaEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author abecerra
 */
public class GeneraNotaDebitoXMLauto {

    private String documento = null;
    private String secuencialDocumentoFULL=null;
    private String fechaDocumento=null;
    private int numero=0;
    private Long secuencial = null;		
    private String secuencialComprobante = null;
    private String claveDeAcceso = null;
    private Date fechaEmision;
    private Emisor emisor = null;
    private Clientes clienteSeleccionado;
    private ClaveContingencia claveContingencia = new ClaveContingencia();
    private InfoTributaria infoTributaria = null;		
    private NotaDebito.InfoNotaDebito infoNotaDebito = null;
    private NotaDebitoTableModel modeloDetalle;
    private DatosAdicionalesTableModel modeloDatosAdicionales;	
    private ObjectFactory factory = null;
   
    private BigDecimal subtotal12 = BigDecimal.ZERO;		
    private BigDecimal subtotal0 = BigDecimal.ZERO;		
    private BigDecimal subtotalNoObjetoIVA = BigDecimal.ZERO;		
    private BigDecimal subtotalExentoIVA = BigDecimal.ZERO;		
    private BigDecimal subtotalValorICE = BigDecimal.ZERO;		
    private BigDecimal subtotalSinImpuestos = BigDecimal.ZERO;		
    private BigDecimal valorIVA = BigDecimal.ZERO;		
    private BigDecimal valorTotalComprobante = BigDecimal.ZERO;	
    private String codigoICE=null;

    public GeneraNotaDebitoXMLauto(Date fechaEmision, String secuenciaND, Emisor emisor, Clientes cliente,
            String documento, String secuencialDocumentoFULL, String fechaDocumento) {

       this.documento=documento;
       this.secuencialDocumentoFULL=secuencialDocumentoFULL;
       this.fechaDocumento=fechaDocumento;
       this.secuencial = Long.parseLong(secuenciaND);
       this.secuencialComprobante = String.format("%09d", new Object[] { Long.valueOf(this.secuencial.longValue()) });
       this.emisor=emisor;
       this.clienteSeleccionado=cliente;
       this.fechaEmision=fechaEmision;//new Date();
       actualizaClaveDeAcceso();
       this.numero=0;
  
       this.modeloDetalle = new NotaDebitoTableModel();
       
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
    
   public void agregarDetalle(String razon, String valor)
   {
       this.numero++;
        try {
            BigDecimal Valor=new BigDecimal(valor);
            this.modeloDetalle.addRow(this.numero, razon, Valor);
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error: "+ex.getMessage());
        }
   }
    	
   private NotaDebito generarComprobante()		
   {		
     NotaDebito notaDeDebito = null;		
     		
     if (!llenarObjetoComprobante()) {		
       notaDeDebito = this.factory.createNotaDebito();		
       		
       NotaDebito.InfoNotaDebito.Impuestos impuestos = obtenerImpuestos();		
       NotaDebito.InfoAdicional informacion = generarInformacionAdicional();		
       		
       if (impuestos != null) {		
         notaDeDebito.setInfoTributaria(this.infoTributaria);		
         this.infoNotaDebito.setImpuestos(impuestos);		
         notaDeDebito.setInfoNotaDebito(this.infoNotaDebito);		
         		
         if (informacion.getCampoAdicional().size() > 0) {		
           notaDeDebito.setInfoAdicional(informacion);		
         }		
         		
         notaDeDebito.setMotivos(obtenerMotivos());		
         notaDeDebito.setVersion("1.0.0");		
         notaDeDebito.setId("comprobante");		
       } else {		
         notaDeDebito = null;		
       }		
     }		
     return notaDeDebito;		
   }		
   		
   private NotaDebito.InfoNotaDebito.Impuestos obtenerImpuestos()		
   {		
     NotaDebito.InfoNotaDebito.Impuestos resultado = this.factory.createNotaDebitoInfoNotaDebitoImpuestos();		
     		
     boolean error = false;		
     try {	
         
       if (this.subtotalValorICE.floatValue() > 0.0F) {		
         Impuesto i = this.factory.createImpuesto();		
         if (this.codigoICE.equals("")) {				
           System.out.println("Se ha producido un error - Ingrese el código ICE correspondiente al impuesto");		
           error = true;		
         }		
         else {		
           ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo(this.codigoICE);		
           if (iv != null) {		
             i.setCodigo(String.valueOf(iv.getCodigoImpuesto()));		
             i.setCodigoPorcentaje(iv.getCodigo());		
             i.setTarifa(BigDecimal.ZERO.setScale(2));		
             i.setBaseImponible(subtotalActivo());		
             i.setValor(this.subtotalValorICE);		
             		
             resultado.getImpuesto().add(i);		
           } else {			
             System.out.println("Se ha producido un error - El código de ICE ingresado no se encuentra registrado");
             this.codigoICE="";		
             error = true;		
           }		
         }		
       }		
       		
       if (this.subtotal12.floatValue() > 0.0F) {		
         Impuesto i = this.factory.createImpuesto();		
         ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo(TipoImpuestoIvaEnum.IVA_VENTA_12.getCode());		
         if (iv != null) {		
           BigDecimal porcentaje = BigDecimal.valueOf(iv.getPorcentaje().doubleValue()/100.0D).setScale(2, RoundingMode.HALF_UP);			
           i.setCodigo(String.valueOf(iv.getCodigoImpuesto()));		
           i.setCodigoPorcentaje(iv.getCodigo());		
           i.setTarifa(porcentaje.multiply(BigDecimal.valueOf(100L).setScale(0, RoundingMode.HALF_UP)));		
           i.setBaseImponible(this.subtotal12.add(this.subtotalValorICE));		
           i.setValor(this.subtotal12.add(this.subtotalValorICE).multiply(porcentaje).setScale(2, RoundingMode.HALF_UP));		
           		
           resultado.getImpuesto().add(i);		
         }		
       }		
       if (this.subtotal0.floatValue() > 0.0F) {		
         Impuesto i = this.factory.createImpuesto();		
         ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo(TipoImpuestoIvaEnum.IVA_VENTA_0.getCode());		
         if (iv != null)		
         {		
           i.setCodigo(String.valueOf(iv.getCodigoImpuesto()));		
           i.setCodigoPorcentaje(iv.getCodigo());		
           i.setTarifa(BigDecimal.valueOf(0L).setScale(2, RoundingMode.HALF_UP));		
           i.setBaseImponible(this.subtotal0.add(this.subtotalValorICE));		
           i.setValor(BigDecimal.ZERO);		
           resultado.getImpuesto().add(i);		
         }		
       }		
       if (this.subtotalNoObjetoIVA.floatValue() > 0.0F) {		
         Impuesto i = this.factory.createImpuesto();		
         ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo(TipoImpuestoIvaEnum.IVA_NO_OBJETO.getCode());		
         if (iv != null)		
         {		
           i.setCodigo(String.valueOf(iv.getCodigoImpuesto()));		
           i.setCodigoPorcentaje(iv.getCodigo());		
           i.setTarifa(BigDecimal.valueOf(0L).setScale(2, RoundingMode.HALF_UP));		
           i.setBaseImponible(this.subtotalNoObjetoIVA.add(this.subtotalValorICE));		
           i.setValor(BigDecimal.ZERO);		
           		
           resultado.getImpuesto().add(i);		
         }		
       }		
       if (this.subtotalExentoIVA.floatValue() > 0.0F) {		
         Impuesto i = this.factory.createImpuesto();		
         ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo(TipoImpuestoIvaEnum.IVA_EXCENTO.getCode());		
         if (iv != null)		
         {		
           i.setCodigo(String.valueOf(iv.getCodigoImpuesto()));		
           i.setCodigoPorcentaje(iv.getCodigo());		
           i.setTarifa(BigDecimal.valueOf(0L).setScale(2, RoundingMode.HALF_UP));		
           i.setBaseImponible(this.subtotalExentoIVA.add(this.subtotalValorICE));		
           i.setValor(BigDecimal.ZERO);		
           		
           resultado.getImpuesto().add(i);		
         }		
       }		
     } catch (Exception ex) {		
       System.out.println("Error: "+ex.getMessage());	
       return null;		
     }		
     		
     if (error == true) {		
       resultado = null;		
     }		
     return resultado;		
   }		
   		
    private NotaDebito.InfoAdicional generarInformacionAdicional() {		
    NotaDebito.InfoAdicional info = this.factory.createNotaDebitoInfoAdicional();		

    for (int i = 0; i < this.modeloDatosAdicionales.getRowCount(); i++) {		
      NotaDebito.InfoAdicional.CampoAdicional detalle = new NotaDebito.InfoAdicional.CampoAdicional();		
      detalle.setNombre((String)this.modeloDatosAdicionales.getValueAt(i, 0));		
      detalle.setValue((String)this.modeloDatosAdicionales.getValueAt(i, 1));		

      info.getCampoAdicional().add(detalle);		
    }		
    return info;		
    }	

    private NotaDebito.Motivos obtenerMotivos() {		
    NotaDebito.Motivos resultado = this.factory.createNotaDebitoMotivos();		

    for (int i = 0; i < this.modeloDetalle.getRowCount(); i++) {		
      NotaDebito.Motivos.Motivo mot = this.factory.createNotaDebitoMotivosMotivo();		
      mot.setRazon((String)this.modeloDetalle.getValueAt(i, 1));		
      mot.setValor(((BigDecimal)this.modeloDetalle.getValueAt(i, 2)).setScale(2, RoundingMode.HALF_UP));		
      resultado.getMotivo().add(mot);		
    }		
    return resultado;		
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
     this.infoTributaria.setCodDoc(TipoComprobanteEnum.NOTA_DE_DEBITO.getCode());		
     this.infoTributaria.setEstab(this.emisor.getCodigoEstablecimiento());		
     this.infoTributaria.setPtoEmi(this.emisor.getCodPuntoEmision());		
     this.infoTributaria.setDirMatriz(this.emisor.getDireccionMatriz());		
     		
     if (this.claveDeAcceso != null) {		
       this.infoTributaria.setClaveAcceso(this.claveDeAcceso);		
     } else {		
       mensajes.append("\nLa clave de Acceso no puede ser nula");		
       error = true;		
     }		
     		
     if ((this.emisor.getNombreComercial() != null) && (!this.emisor.getNombreComercial().isEmpty())) {		
       this.infoTributaria.setNombreComercial(this.emisor.getNombreComercial());		
     }		
     		
     this.infoNotaDebito = this.factory.createNotaDebitoInfoNotaDebito();		
     this.infoNotaDebito.setFechaEmision(Constantes.dateFormat.format(FormGenerales.eliminaHora(this.fechaEmision)));		
     		
     if ((this.emisor.getDirEstablecimiento() != null) && (!this.emisor.getDirEstablecimiento().isEmpty())) {		
       this.infoNotaDebito.setDirEstablecimiento(this.emisor.getDirEstablecimiento());		
     }		
     			
     if(this.clienteSeleccionado != null)
     {
        this.infoNotaDebito.setIdentificacionComprador(this.clienteSeleccionado.getNumeroIdentificacio());		
        this.infoNotaDebito.setRazonSocialComprador(this.clienteSeleccionado.getApellido());
        this.infoNotaDebito.setTipoIdentificacionComprador(TipoCompradorEnum.retornaCodigo(this.clienteSeleccionado.getNumeroIdentificacio()));

     }
 		
     if (this.documento.equals("FACTURA")) {		
       this.infoNotaDebito.setCodDocModificado(TipoComprobanteEnum.FACTURA.getCode());		
     }	
     
     this.infoNotaDebito.setNumDocModificado(this.secuencialDocumentoFULL);		
     this.infoNotaDebito.setFechaEmisionDocSustento(this.fechaDocumento);		
     if (!this.valorTotalComprobante.equals(BigDecimal.ZERO)) {		
       this.infoNotaDebito.setTotalSinImpuestos(this.subtotalSinImpuestos);		
       this.infoNotaDebito.setValorTotal(this.valorTotalComprobante);		
     } else {		
       mensajes.append("\nNo se ha calculado el Valor Total del comprobante");		
       error = true;		
     }		
     if ((this.emisor.getContribuyenteEspecial() != null) && (!this.emisor.getContribuyenteEspecial().isEmpty())) {		
       this.infoNotaDebito.setContribuyenteEspecial(this.emisor.getContribuyenteEspecial());		
     }		
     if (this.emisor.getLlevaContabilidad() != null) {		
       if (this.emisor.getLlevaContabilidad().equals("SI")) {		
         this.infoNotaDebito.setObligadoContabilidad("SI");		
       } else {		
         this.infoNotaDebito.setObligadoContabilidad("NO");		
       }		
     }		
     		
     if (error == true) {		
       System.out.println("Se ha producido un error: "+mensajes.toString());
     }		
     		
     return error;		
   }		
   	
    public String GenerarNotaDebito(String directorio, int tipoImpuesto) {		
     String respuestaCrear = null;		
     String archivoACrear = null;		
     NotaDebito notaDebitoLlena = null;		
     try {	
       actualizaDetalle(tipoImpuesto);
       if ((this.modeloDetalle.getRowCount() > 0)) 
       {		
         notaDebitoLlena = generarComprobante();				
         archivoACrear = directorio + this.claveDeAcceso + ".xml";		
         respuestaCrear = ArchivoUtils.crearArchivoXml2(archivoACrear, notaDebitoLlena, this.claveContingencia, this.secuencial, TipoComprobanteEnum.NOTA_DE_DEBITO.getCode());		
	
         if (respuestaCrear != null) 		
           System.out.println("Error al tratar de crear el archivo correspondiente al comprobante:\n" + respuestaCrear+ " Se ha producido un error ");
         		
       } else {		
         System.out.println("\nAl menos debe añadir un item al comprobante y todas las columnas de ICE deben estar llenas"+ ">> COMPROBANTE VACIO");
       }		
     } catch (Exception ex) {		
        System.out.println("Error: "+ex.getMessage());
     }	
     return archivoACrear;
   }	
   
   private BigDecimal subtotalActivo()		
   {		
     BigDecimal valor = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);		
     if (this.subtotal12.intValue() > 0) {		
       valor = this.subtotal12;		
     } else if (this.subtotal0.intValue() > 0) {		
       valor = this.subtotal0;		
     } else if (this.subtotalNoObjetoIVA.intValue() > 0) {		
       valor = this.subtotalNoObjetoIVA;		
     }		
     return valor;		
   }
    
    public void actualizaDetalle(int impuestos) {		
     BigDecimal porcentaje = BigDecimal.ZERO;	

    
     if (impuestos==0) {//12%		
       this.subtotal12 = BigDecimal.valueOf((this.modeloDetalle.getTotalIva()).doubleValue());	
       porcentaje = new BigDecimal("0.12");
     } else {		
       this.subtotal12 = BigDecimal.ZERO;		
     }		
     		
     if (impuestos==1) {//0%		
       this.subtotal0 = BigDecimal.valueOf((this.modeloDetalle.getTotalIva()).doubleValue());			
     } else {		
       this.subtotal0 = BigDecimal.ZERO;		
     }		
     		
     if (impuestos==2) {		
       this.subtotalNoObjetoIVA = BigDecimal.valueOf((this.modeloDetalle.getTotalIva()).doubleValue());	
     } else {		
       this.subtotalNoObjetoIVA = BigDecimal.ZERO;		
     }		
     if (impuestos==3) {		
       this.subtotalExentoIVA = BigDecimal.valueOf((this.modeloDetalle.getTotalIva()).doubleValue());			
     } else {		
       this.subtotalExentoIVA = BigDecimal.ZERO;		
     }		
    
     this.subtotalSinImpuestos = this.subtotal12.add(this.subtotal0.add(this.subtotalNoObjetoIVA).add(this.subtotalExentoIVA));		
     this.valorIVA = this.subtotal12.add(this.subtotalValorICE).multiply(porcentaje).setScale(2, RoundingMode.HALF_UP);		
     this.valorTotalComprobante = this.subtotalSinImpuestos.add(this.subtotalValorICE.add(this.valorIVA));				
   }		
   	
    
   private void actualizaClaveDeAcceso()		
   {		
        String serie="";
        this.claveDeAcceso = null;
        this.claveContingencia = new FormGenerales().obtieneClaveDeAcceso(this.secuencialComprobante, this.emisor, serie, this.claveDeAcceso, this.fechaEmision, TipoComprobanteEnum.NOTA_DE_DEBITO.getCode());
        if ((this.claveContingencia.getCodigoComprobante() != null) && (!this.claveContingencia.getCodigoComprobante().isEmpty())) {
        this.claveDeAcceso = this.claveContingencia.getCodigoComprobante();
        }
   }	
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generaXML;

import comprobantes.entidades.Clientes;
import comprobantes.entidades.Emisor;
import comprobantes.entidades.Producto;
import comprobantes.util.Constantes;
import comprobantes.util.TipoComprobanteEnum;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author abecerra
 * Formato de fechas dd/MM/aaaa
 */
public class GeneraXMLsri {

    private static String strDirectorio;
    private static String targetURL; 
    private static String filePath; 
    private Emisor emisor;
    private Clientes cliente;
    private GeneraFacturaXMLauto objGeneraFacturaXMLauto;
    private GeneraRetencionXMLauto objGeneraRetencionXMLauto;
    private GeneraNotaDebitoXMLauto objGeneraNotaDebitoXMLauto;
    private GeneraNotaCreditoXMLauto objGeneraNotaCreditoXMLauto;

    public GeneraXMLsri(String strDirectorio, String targetURL) 
    {
        GeneraXMLsri.strDirectorio = strDirectorio;
        GeneraXMLsri.targetURL = targetURL;
    }
    
    public void llenarFactura(Emisor emisor, Clientes cliente, Date fechaEmision, String secFactura, String subTotal, String valorTotal) 
    {
        this.emisor=emisor;
        this.emisor.setClaveInterna(Constantes.codigo_numerico);
        this.emisor.setContribuyenteEspecial(Constantes.contribuyenteEspecial);
        this.emisor.setLlevaContabilidad(Constantes.llevaContabilidad);
        this.cliente=cliente;
        //Factura
        this.objGeneraFacturaXMLauto=new GeneraFacturaXMLauto(fechaEmision,secFactura,subTotal,valorTotal,emisor,cliente);
    }
    
    public void llenarDetFact(String codPrincipal,String codAux, String cantidad, String descipcion, String valorUnit, String obs) 
    {
        int codImpuesto=2;
        Producto producto = new Producto();
        producto.setCodigoPrincipal(codPrincipal);
        producto.setCodigoAuxiliar(codAux);
        producto.setCantidad(Double.parseDouble(cantidad));
        producto.setCodigoImpuesto(codImpuesto);//2->IVA12
        producto.setNombre(descipcion);
        producto.setValorUnitario(new BigDecimal(valorUnit));
        producto.setObs(obs);
        this.objGeneraFacturaXMLauto.agregarDetalle(producto);
    }

    public String[] generarXMLfact(boolean enviarCore) 
    {
        String[] mensaje=new String[2];
        GeneraXMLsri.filePath=this.objGeneraFacturaXMLauto.generarFactura(GeneraXMLsri.strDirectorio);
        if(enviarCore)
        {
            consumoServiciosCore objConsumoServiciosCore = new consumoServiciosCore();
            mensaje=objConsumoServiciosCore.consumoFull_AuthorizationsJson(targetURL,filePath,cliente.getCorreo());
        }
        else
        {
            mensaje[0]="OK";
            mensaje[1]="Se genero exitosamente el XML - "+filePath;
        }
        
        System.out.println("Retorno a la aplicacion: "+mensaje[0] + " - "+ mensaje[1]);
        
        return mensaje;
    }
        
    
    public void llenarRetencion(Emisor emisor, Clientes cliente, String secuencialRetencion, String mes, String anio) 
    {
        this.emisor=emisor;
        this.emisor.setClaveInterna(Constantes.codigo_numerico);
        this.emisor.setContribuyenteEspecial(Constantes.contribuyenteEspecial);
        this.emisor.setLlevaContabilidad(Constantes.llevaContabilidad);
        this.cliente=cliente;
        //Retencion
        this.objGeneraRetencionXMLauto=new GeneraRetencionXMLauto(secuencialRetencion,mes,anio,emisor,cliente);
    }
    
    public void llenarDetRetencion(String fechaFactura, String numFacturaRetener, double valorFactura) 
    {
        String retencion="IVA";
        String codImpuesto="2";
        numFacturaRetener=numFacturaRetener.replaceAll("-", "");
        //numFacturaRetener=numFacturaRetener.replace('-', ' ');
        this.objGeneraRetencionXMLauto.adicionarDetalle(retencion,codImpuesto,TipoComprobanteEnum.FACTURA.getDescripcion(),numFacturaRetener,fechaFactura,valorFactura);
    }
    
    public String[] generarXMLret(boolean enviarCore) 
    {
        //Comprobante de Retencion
        String[] mensaje=new String[2];
        filePath=this.objGeneraRetencionXMLauto.generarComprobateRetencion(strDirectorio);
        if(enviarCore)
        {
            consumoServiciosCore objConsumoServiciosCore = new consumoServiciosCore();
            mensaje=objConsumoServiciosCore.consumoFull_AuthorizationsJson(targetURL,filePath,cliente.getCorreo());
        }
        else
        {
            mensaje[0]="OK";
            mensaje[1]="Se genero exitosamente el XML - "+filePath;
        }
        return mensaje;
    }
    
    
    public void llenarNotaDebito(Emisor emisor, Clientes cliente, Date fechaEmision, String secuencialND, 
            String secuencialDocumentoFULL, String fechaDocumento) 
    {
        this.emisor=emisor;
        this.emisor.setClaveInterna(Constantes.codigo_numerico);
        this.emisor.setContribuyenteEspecial(Constantes.contribuyenteEspecial);
        this.emisor.setLlevaContabilidad(Constantes.llevaContabilidad);
        this.cliente=cliente;
        String [] secuencialDocumentoFULL_=secuencialDocumentoFULL.split("-");
        secuencialDocumentoFULL=secuencialDocumentoFULL_[0]+"-"+secuencialDocumentoFULL_[1]+"-"+String.format("%09d", new Object[] { Long.valueOf(Long.parseLong(secuencialDocumentoFULL_[2])) });
        //NotaDebito
        this.objGeneraNotaDebitoXMLauto=new GeneraNotaDebitoXMLauto(fechaEmision,secuencialND,
        emisor,cliente,TipoComprobanteEnum.FACTURA.getDescripcion(),secuencialDocumentoFULL,fechaDocumento);
    }
    
    public void llenarDetNotaDebito(String detalle, String valor) 
    {
        this.objGeneraNotaDebitoXMLauto.agregarDetalle(detalle,valor);
    }
    
    public String[] generarXMLnotaDebito(boolean enviarCore) 
    {
        //Comprobante de Retencion
        String[] mensaje=new String[2];
        //filePath=this.objGeneraNotaDebitoXMLauto.GenerarNotaDebito(strDirectorio,0); //0->12%; 1->0%; 2->NoObjetoIVA; 3->ExentoIVA
        filePath=this.objGeneraNotaDebitoXMLauto.GenerarNotaDebito(strDirectorio,1); //0->12%; 1->0%; 2->NoObjetoIVA; 3->ExentoIVA
        if(enviarCore)
        {
            consumoServiciosCore objConsumoServiciosCore = new consumoServiciosCore();
            mensaje=objConsumoServiciosCore.consumoFull_AuthorizationsJson(targetURL,filePath,cliente.getCorreo());
        }
        else
        {
            mensaje[0]="OK";
            mensaje[1]="Se genero exitosamente el XML - "+filePath;
        }
        return mensaje;
    }
    
    
    public void llenarNotaCredito(Emisor emisor, Clientes cliente, Date fechaEmision, String secuencialNC, String subTotal, String valorTotal
    ,String secuencialDocumentoFULL, String fechaDocumento, String motivoAnulacion) 
    {
        this.emisor=emisor;
        this.emisor.setClaveInterna(Constantes.codigo_numerico);
        this.emisor.setContribuyenteEspecial(Constantes.contribuyenteEspecial);
        this.emisor.setLlevaContabilidad(Constantes.llevaContabilidad);
        this.cliente=cliente;
        String [] secuencialDocumentoFULL_=secuencialDocumentoFULL.split("-");
        secuencialDocumentoFULL=secuencialDocumentoFULL_[0]+"-"+secuencialDocumentoFULL_[1]+"-"+String.format("%09d", new Object[] { Long.valueOf(Long.parseLong(secuencialDocumentoFULL_[2])) });
        //NotaCredito
        this.objGeneraNotaCreditoXMLauto=new GeneraNotaCreditoXMLauto(fechaEmision,secuencialNC,subTotal,
                valorTotal,emisor,cliente,TipoComprobanteEnum.FACTURA.getDescripcion(),secuencialDocumentoFULL,fechaDocumento,motivoAnulacion);
    }
    
    public void llenarDetNotaCredito(String codPrincipal,String codAux, String cantidad, String descipcion, String valorUnit, String obs) 
    {
        int codImpuesto=2;
        Producto producto = new Producto();
        producto.setCodigoPrincipal(codPrincipal);
        producto.setCodigoAuxiliar(codAux);
        producto.setCantidad(Double.parseDouble(cantidad));
        producto.setCodigoImpuesto(codImpuesto);//2->IVA12
        producto.setNombre(descipcion);
        producto.setValorUnitario(new BigDecimal(valorUnit));
        producto.setObs(obs);
        this.objGeneraNotaCreditoXMLauto.agregarDetalle(producto);
    }

    public String[] generarXMLnotaCredito(boolean enviarCore) 
    {
        String[] mensaje=new String[2];
        GeneraXMLsri.filePath=this.objGeneraNotaCreditoXMLauto.generarNotaCredito(GeneraXMLsri.strDirectorio);
        if(enviarCore)
        {
            consumoServiciosCore objConsumoServiciosCore = new consumoServiciosCore();
            mensaje=objConsumoServiciosCore.consumoFull_AuthorizationsJson(targetURL,filePath,cliente.getCorreo());
        }
        else
        {
            mensaje[0]="OK";
            mensaje[1]="Se genero exitosamente el XML - "+filePath;
        }
        return mensaje;
    }
        
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generaXML;

import comprobantes.entidades.ClaveContingencia;
import comprobantes.entidades.Clientes;
import comprobantes.entidades.Emisor;
import comprobantes.entidades.ImpuestoValor;
import comprobantes.modelo.InfoTributaria;
import comprobantes.doc.retencion.ComprobanteRetencion;
import comprobantes.doc.retencion.Impuesto;
import comprobantes.doc.retencion.ObjectFactory;
import comprobantes.sql.ImpuestoValorSQL;
import comprobantes.table.DatosAdicionalesTableModel;
import comprobantes.table.RetencionTableModel;
import comprobantes.util.ArchivoUtils;
import comprobantes.util.Constantes;
import comprobantes.util.FormGenerales;
import comprobantes.util.TipoComprobanteEnum;
import comprobantes.util.TipoImpuestoEnum;
import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abecerra
 */
public class GeneraRetencionXMLauto {

    private String razonSociaSujetoReten = null;
    private String identSujetoRetenido = null;
    private String claveDeAcceso = null;
    private String secuencialComprobante = null;
    private Date fechaEmision = null;
    private Long secuencial = null;
    private ClaveContingencia claveContingencia = new ClaveContingencia();
    private Emisor emisor = null;
    private Clientes cliente = null;
    private ComprobanteRetencion.InfoCompRetencion infoCompRetencion = null;
    private InfoTributaria infoTributaria = null;
    private DatosAdicionalesTableModel modeloDatosAdicionales;
    private RetencionTableModel modeloDetalle;
    private ObjectFactory factory = null;
    
    private String txtMes=null;
    private String txtAnio=null;
    
    
    public GeneraRetencionXMLauto(String secuenciaRetencion,String mes,String anio, Emisor emisor, Clientes cliente) 
    {
       this.txtMes= mes;
       this.txtAnio=anio;
       this.emisor=emisor;
       this.cliente=cliente;
       this.secuencial = Long.parseLong(secuenciaRetencion);
       this.secuencialComprobante = String.format("%09d", new Object[] { Long.valueOf(this.secuencial.longValue()) });
       this.fechaEmision=new Date();      
       actualizaClaveDeAcceso();
       
       
       this.modeloDetalle = new RetencionTableModel();
       
//       String[] opciones = { TipoComprobanteEnum.FACTURA.getDescripcion(), TipoComprobanteEnum.NOTA_DE_DEBITO.getDescripcion(), TipoComprobanteEnum.LIQUIDACION_DE_COMPRAS.getDescripcion(), "OTROS" };
//       String[] impuestos = { "Seleccione...", TipoImpuestoEnum.IVA.getDescripcion(), TipoImpuestoEnum.RENTA.getDescripcion() };
       
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
       
       this.factory = new ObjectFactory();
    }
    
    
    public void adicionarDetalle(String retencion, String codigo,String docRetener,String strNumCompModif, String strFechaCompModif, double txtBaseImponible)
    {
	BigDecimal impuesto = BigDecimal.ZERO;
	BigDecimal baseImponible = BigDecimal.ZERO;

        List<ImpuestoValor> listadoIva = new java.util.ArrayList();
//        List<ImpuestoValor> listadoRet = new java.util.ArrayList();
        try {
            listadoIva = new ImpuestoValorSQL().obtenerIVARetencion();
//            listadoRet = new ImpuestoValorSQL().obtenerValorImpuestoRenta();
        } catch (Exception ex) {
            Logger.getLogger(GeneraRetencionXMLauto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ImpuestoValor iv=null; 

        if(retencion.equals("IVA"))
        {
            for(int index=0;index<listadoIva.size();index++)
                if(listadoIva.get(index).getCodigo().equals(codigo))
                {
                    iv=listadoIva.get(index);
                    impuesto = BigDecimal.valueOf(listadoIva.get(index).getPorcentajeRentencion());
                    break;
                }
        }

	baseImponible = BigDecimal.valueOf(txtBaseImponible);
	String tipoDoc = TipoComprobanteEnum.retornaCodigo(docRetener);
	
	if (tipoDoc != null) {
//	if (textNumCompModif.isEmpty()) {
//	JOptionPane.showMessageDialog(this, "\nIngrese el número de comprobante a modificar", "Se ha producido un error ", 0);
//	this.textNumCompModif.requestFocus();
//	} else if (this.textNumCompModif.getText().length() < 15) {
//	JOptionPane.showMessageDialog(this, "\n El Nro. del Comprobante debe de ser de 15 dígitos", "Se ha producido un error ", 0);
//	this.textNumCompModif.requestFocus();
//	} else if (this.textFechaCompModif.getText().isEmpty()) {
//	JOptionPane.showMessageDialog(this, "\nIngrese la fecha de emisión del comprobante a modificar", "Se ha producido un error ", 0);
//	} else if (FormGenerales.eliminaHora(this.jCalendarCompModifica.getTargetDate()).after(this.fechaEmision)) {
//	JOptionPane.showMessageDialog(this, "La fecha del Comprobante de Retención no puede ser menor a la fecha de emisión del Documento de Sustento", "Se ha producido un error ", 0);
//	this.textFechaCompModif.requestFocus();
//	} else {
            this.modeloDetalle.addRow(iv, baseImponible, impuesto, baseImponible.multiply(impuesto).divide(BigDecimal.valueOf(100L), 2, java.math.RoundingMode.HALF_UP), strNumCompModif, strFechaCompModif, tipoDoc);
	}
    }

    public String generarComprobateRetencion(String directorio)
    {
        String respuestaCrear = null;
	ComprobanteRetencion comprobanteLleno = null;
	String archivoACrear = null;
	try
	{
            if (this.modeloDetalle.getRowCount() > 0)
            {
                comprobanteLleno = generarComprobante();
                //archivoACrear = new ConfiguracionDirectorioSQL().obtenerDirectorio(DirectorioEnum.GENERADOS.getCode()).getPath() + File.separator + nombreArchivo;
                archivoACrear=directorio+this.claveDeAcceso + ".xml";
                respuestaCrear = ArchivoUtils.crearArchivoXml2(archivoACrear, comprobanteLleno, this.claveContingencia, this.secuencial, TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode());

               if (respuestaCrear != null ) 
                    System.out.println("Error al tratar de crear el archivo correspondiente al comprobante:\n" + respuestaCrear+ " Se ha producido un error ");
         
    //            ComprobanteRetencionReporte crr = new ComprobanteRetencionReporte(comprobanteLleno);
    //            generarReporte(crr, null, null);
    //            this.btnFirmarProcesar.setEnabled(false);
                
            } else {
                System.out.println("\nAl menos debe añadir un item al comprobante y todas las columnas de ICE deben estar llenas"+ ">> COMPROBANTE DE VENTA VACIO");
            }
	}
	catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
	}
        return archivoACrear;
    }
    
    private void actualizaClaveDeAcceso()
    {
        String serie="";
        this.claveDeAcceso = null;
        this.claveContingencia = new FormGenerales().obtieneClaveDeAcceso(this.secuencialComprobante, this.emisor, serie, this.claveDeAcceso, this.fechaEmision, TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode());
        if ((this.claveContingencia.getCodigoComprobante() != null) && (!this.claveContingencia.getCodigoComprobante().isEmpty())) {
                this.claveDeAcceso = this.claveContingencia.getCodigoComprobante();
        }
    }
    
    private ComprobanteRetencion generarComprobante() 
    { 
        ComprobanteRetencion compRetencion = null;

        if (!llenarObjetoComprobante())
        {
            ComprobanteRetencion.Impuestos impuestos = obtenerImpuestos();
            ComprobanteRetencion.InfoAdicional informacion = generarInformacionAdicional();

            if (impuestos != null) {
                compRetencion = this.factory.createComprobanteRetencion();
                compRetencion.setVersion("1.0.0");
                compRetencion.setId("comprobante");
                compRetencion.setInfoTributaria(this.infoTributaria);
                compRetencion.setInfoCompRetencion(this.infoCompRetencion);

                compRetencion.setImpuestos(impuestos);
                if (informacion.getCampoAdicional().size() > 0) {
                        compRetencion.setInfoAdicional(informacion);
                }
            }
        }
        return compRetencion;
    }
    
    private boolean llenarObjetoComprobante()
    {
	StringBuilder mensajes = new StringBuilder();
	boolean error = false;

	this.infoTributaria = this.factory.createInfoTributaria();

	this.infoTributaria.setAmbiente(this.emisor.getTipoAmbiente());
	this.infoTributaria.setTipoEmision(this.emisor.getTipoEmision());
	this.infoTributaria.setRazonSocial(this.emisor.getRazonSocial());
	this.infoTributaria.setRuc(this.emisor.getRuc());
	this.infoTributaria.setCodDoc(TipoComprobanteEnum.COMPROBANTE_DE_RETENCION.getCode());
	this.infoTributaria.setEstab(this.emisor.getCodigoEstablecimiento());
	this.infoTributaria.setPtoEmi(this.emisor.getCodPuntoEmision());
	this.infoTributaria.setSecuencial(this.secuencialComprobante);
	this.infoTributaria.setDirMatriz(this.emisor.getDireccionMatriz());

	if (this.claveDeAcceso != null) {
	this.infoTributaria.setClaveAcceso(this.claveDeAcceso);
	} else {
	  System.out.println("\nLa clave de Acceso no puede ser nula >> Se ha producido un error ");

	error = true;
	}

	if ((this.emisor.getNombreComercial() != null) && (!this.emisor.getNombreComercial().isEmpty())) {
	this.infoTributaria.setNombreComercial(this.emisor.getNombreComercial());
	}

	this.infoCompRetencion = this.factory.createComprobanteRetencionInfoCompRetencion();

	this.infoCompRetencion.setFechaEmision(Constantes.dateFormat.format(FormGenerales.eliminaHora(this.fechaEmision)));

	if ((this.emisor.getDirEstablecimiento() != null) && (!this.emisor.getDirEstablecimiento().isEmpty())) {
	this.infoCompRetencion.setDirEstablecimiento(this.emisor.getDirEstablecimiento());
	}

	if (this.cliente != null) {
	this.razonSociaSujetoReten = this.cliente.getApellido();
	this.identSujetoRetenido = this.cliente.getNumeroIdentificacio();
	this.infoCompRetencion.setIdentificacionSujetoRetenido(this.identSujetoRetenido);
	this.infoCompRetencion.setRazonSocialSujetoRetenido(this.razonSociaSujetoReten);
	this.infoCompRetencion.setTipoIdentificacionSujetoRetenido(comprobantes.util.TipoCompradorEnum.retornaCodigo(this.cliente.getNumeroIdentificacio()));
	} else {
	mensajes.append("\nLos datos de Sujeto Retenido son obligatorios");
	error = true;
	}

	Date fechaIngresada = null;
	if ((this.txtMes.isEmpty()) || (this.txtAnio.isEmpty())) {
	mensajes.append("\nEl mes y el año del período fiscal son obligatorios");
	error = true;
	} else {
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.set(1, Integer.parseInt(this.txtAnio));
	cal.set(2, Integer.parseInt(this.txtMes) - 1);
	cal.set(11, 0);
	cal.set(12, 0);
	cal.set(13, 0);
	cal.set(14, 0);
	fechaIngresada = cal.getTime();

	if (fechaIngresada.after(new Date())) {
	mensajes.append("\nEl período fiscal no puede ser mayor a la fecha actual");
	error = true;
	} else {
	this.infoCompRetencion.setPeriodoFiscal(this.txtMes + "/" + this.txtAnio);
	}
	}

	if ((this.emisor.getContribuyenteEspecial() != null) && (!this.emisor.getContribuyenteEspecial().isEmpty())) {
	this.infoCompRetencion.setContribuyenteEspecial(this.emisor.getContribuyenteEspecial());
	}
	if (this.emisor.getLlevaContabilidad() != null) {
	if (this.emisor.getLlevaContabilidad().equals("SI")) {
	this.infoCompRetencion.setObligadoContabilidad("SI");
	} else {
	this.infoCompRetencion.setObligadoContabilidad("NO");
	}
	}

	if (error == true) {
            System.out.println("Se ha producido un error ");
	}

	return error;
    }

    private ComprobanteRetencion.Impuestos obtenerImpuestos() 
    {
	ComprobanteRetencion.Impuestos resultado = this.factory.createComprobanteRetencionImpuestos();
	boolean error = false;
	try
	{
            for (int i = 0; i < this.modeloDetalle.getRowCount(); i++) {
                Object[] item = this.modeloDetalle.getRow(i);

                ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo((String)item[0]);
                if (iv != null)
                {
                    Impuesto ir = this.factory.createImpuesto();
                    ir.setCodigo(String.valueOf(iv.getCodigoImpuesto()));
                    ir.setCodigoRetencion(iv.getCodigo());
                    ir.setPorcentajeRetener((BigDecimal)item[4]);
                    ir.setBaseImponible((BigDecimal)item[3]);
                    ir.setValorRetenido((BigDecimal)item[5]);
                    ir.setCodDocSustento((String)item[8]);
                    ir.setNumDocSustento((String)item[6]);
                    ir.setFechaEmisionDocSustento((String)item[7]);
                    resultado.getImpuesto().add(ir);
                }
            }
	}
	catch (Exception ex) {
            System.out.println("Error: "+ex.getMessage());
	}

	if (error == true) {
	resultado = null;
	}
	return resultado;
    }

    private ComprobanteRetencion.InfoAdicional generarInformacionAdicional()
    {
        ComprobanteRetencion.InfoAdicional info = this.factory.createComprobanteRetencionInfoAdicional();

        for (int i = 0; i < this.modeloDatosAdicionales.getRowCount(); i++) {
            ComprobanteRetencion.InfoAdicional.CampoAdicional detalle = this.factory.createComprobanteRetencionInfoAdicionalCampoAdicional();
            detalle.setNombre((String)this.modeloDatosAdicionales.getValueAt(i, 0));
            detalle.setValue((String)this.modeloDatosAdicionales.getValueAt(i, 1));

            info.getCampoAdicional().add(detalle);
        }
        return info;
    }
    
}

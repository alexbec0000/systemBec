/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_utilidades;

import comprobantes.entidades.Clientes;
import comprobantes.entidades.Emisor;
import generaXML.GeneraXMLsri;
import java.util.ArrayList;
import java.util.List;
import pck_entidades.Factura;
import pck_entidades.FacturaDetalle;
import pck_entidades.dElectronicos.DetalleFacturaElectronica;
import pck_entidades.dElectronicos.FacturaElectronica;
import servicios.cls_fechas;

/**
 *
 * @author abecerra
 */
public class DocumentosElectronicos {

    private static String urlCore;
    private static String pathXML;
    private static final boolean autorizar = false;    // true produccion
//    private static String ambiente = "1";     // 2 produccion

    public DocumentosElectronicos(String urlCore, String pathXML) {
        DocumentosElectronicos.urlCore = urlCore;
        DocumentosElectronicos.pathXML=pathXML;
    }

    
    public String generarFacturaElectronica(Factura factura, Emisor sucursalEmisora) {
        //Emisor sucursalEmisora = new Emisor();
        String[] numFac = factura.getNumFac().split("-");
        String observacion = "";
        FacturaElectronica fac = new FacturaElectronica();
        fac.setAutorizar(autorizar);

        sucursalEmisora.setCodigoEstablecimiento(numFac[0]);
        sucursalEmisora.setCodPuntoEmision(numFac[1]);

        Clientes cliente = new Clientes();
        cliente.setNumeroIdentificacio(factura.getRuc());
        cliente.setApellido(factura.getCliente());
        cliente.setDireccion(factura.getDireccion());
        cliente.setCorreo(factura.getEmail());
        cliente.setTelefonoConvencional(factura.getTelefono());

        cliente.setObservacion(observacion);

        fac.setEmisor(sucursalEmisora);
        fac.setCliente(cliente);
        fac.setFechaEmision(factura.getFecha()); //"dd/MM/yyyy"
        fac.setSecuencialFactura(numFac[2]);
        fac.setSubTotal(factura.getSubTotalFac());
        fac.setValorTotal(factura.getValorFac());
        fac.setPathCore(urlCore);
        fac.setPathFactura(pathXML);

        // Arreglo de detalles
        List<DetalleFacturaElectronica> Lisdetalle = new ArrayList();

        // Detalle a instanciarse
        DetalleFacturaElectronica detalle;
        int cod=1;
        for (FacturaDetalle det : factura.getLs_FacturaDetalle()) {
            detalle = new DetalleFacturaElectronica();

            detalle.setCodPrincipal(cod+"");
            detalle.setCodAux(det.getCodigoArticulo());
            detalle.setCantidad(det.getCantidad());
            detalle.setDescipcion(det.getDescripcion());
            detalle.setValorUnit(det.getValorUnit());
            detalle.setObs("");
            cod++;
            Lisdetalle.add(detalle);

        }

        // Agregando la lista de facturas
        fac.setDetalleFactura(Lisdetalle);

        java.util.Date fechaEmision = new cls_fechas().DeStringADate(fac.getFechaEmision()); //"dd/MM/yyyy"
        GeneraXMLsri objGeneraXMLsri = new GeneraXMLsri(fac.getPathFactura(), fac.getPathCore());

        objGeneraXMLsri.llenarFactura(fac.getEmisor(), fac.getCliente(), fechaEmision, fac.getSecuencialFactura(), fac.getSubTotal(), fac.getValorTotal());

        for (DetalleFacturaElectronica objDetFacElect : fac.getDetalleFactura()) {
            objGeneraXMLsri.llenarDetFact(objDetFacElect.getCodPrincipal(),
                    objDetFacElect.getCodAux(), objDetFacElect.getCantidad(),
                    objDetFacElect.getDescipcion(), objDetFacElect.getValorUnit(),
                    objDetFacElect.getObs());
        }

        String[] mensaje = objGeneraXMLsri.generarXMLfact(fac.isAutorizar());
        
        return mensaje[0];

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pck_utilidades;

import pck_entidades.Factura;

/**
 *
 * @author abecerra
 */
public class DocumentosElectronicos {
    
     public String generarFacturaElectronica(Factura factura)
        {
            String[] numFac = factura.getNumFac().split("-");
            String observacion = "";
            facturaElectronica fac = new facturaElectronica();
            fac.autorizar = autorizar;

            sucursalEmisora.ruc = rucEmpresa;
            sucursalEmisora.razonSocial = razonSocial;
            sucursalEmisora.nombreComercial = nombreComercial;
            sucursalEmisora.claveInterna = claveInterna;
            sucursalEmisora.contribuyenteEspecial = contribuyenteEspecial;
            sucursalEmisora.llevaContabilidad = llevaContabilidad;
            sucursalEmisora.tipoAmbiente = ambiente;
            sucursalEmisora.tipoEmision = tipoEmision;

            clientes cliente = new clientes();
            cliente.numeroIdentificacio = factura.Ruc;
            cliente.apellido = factura.Cliente;
            cliente.direccion = factura.Direccion;
            cliente.correo = factura.Email;
            cliente.telefonoConvencional = factura.Telefono;
            if (factura.Placa.Length > 0 || factura.NumTicketBascula.Length > 0)
            {
                if (factura.Placa.Length > 0 && factura.NumTicketBascula.Length > 0)
                {
                    observacion = "Placa: " + factura.Placa;
                    observacion += " - Ticket Báscula: " + factura.NumTicketBascula;
                }
                else
                {
                    if (factura.Placa.Length > 0)
                        observacion = "Placa: " + factura.Placa;

                    if (factura.NumTicketBascula.Length > 0)
                        observacion = "Ticket Báscula: " + factura.NumTicketBascula;
                }
                cliente.observacion = observacion;
            }
 
            fac.emisor = sucursalEmisora;
            fac.cliente = cliente;
            fac.fechaEmision = factura.Fecha; //"dd/MM/yyyy"
            fac.secuencialFactura = numFac[2];
            fac.subTotal = factura.SubTotalFac;
            fac.valorTotal = factura.ValorFac;
            fac.pathCore = SesionActiva.Config.PathCore;
            fac.pathFactura = SesionActiva.Config.PathFactura;
    
            // Arreglo de detalles
            detalleFacturaElectronica[] Lisdetalle = new detalleFacturaElectronica[detalles.Count];

            // Detalle a instanciarse
            detalleFacturaElectronica detalle;

            // Índice del arreglo
            int i = 0;

            foreach (DetalleTicket det in detalles)
            {
                detalle = new detalleFacturaElectronica();

                detalle.codPrincipal = det.codDetalle.ToString();
                detalle.codAux = det.codServicio.ToString();
                detalle.cantidad = det.cantidad.ToString();
                detalle.descipcion = det.descripcionServicio;
                detalle.valorUnit = det.valorUnitario.ToString();
                detalle.obs = det.observacion;

                Lisdetalle[i] = detalle;

                i++;
            }

            // Agregando la lista de facturas
            fac.detalleFactura = Lisdetalle;
            
            java.util.Date fechaEmision = new cls_fechas().DeStringADate(facturaElectronica.getFechaEmision()); //"dd/MM/yyyy"
            GeneraXMLsri objGeneraXMLsri = new GeneraXMLsri(facturaElectronica.getPathFactura(), facturaElectronica.getPathCore());

            objGeneraXMLsri.llenarFactura(facturaElectronica.getEmisor(), facturaElectronica.getCliente(), fechaEmision, facturaElectronica.getSecuencialFactura(), facturaElectronica.getSubTotal(), facturaElectronica.getValorTotal());

            for (DetalleFacturaElectronica objDetFacElect : facturaElectronica.getDetalleFactura()) {
                objGeneraXMLsri.llenarDetFact(objDetFacElect.getCodPrincipal(),
                        objDetFacElect.getCodAux(), objDetFacElect.getCantidad(),
                        objDetFacElect.getDescipcion(), objDetFacElect.getValorUnit(),
                        objDetFacElect.getObs());
            }

            String[] mensaje = objGeneraXMLsri.generarXMLfact(facturaElectronica.isAutorizar());

            return mensaje[0];

        }
    
}

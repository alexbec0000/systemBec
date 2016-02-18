/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicios;

import comprobantes.entidades.Clientes;
import comprobantes.entidades.Emisor;
import comprobantes.util.Constantes;
import generaXML.GeneraXMLsri;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author abecerra
 */
public class RetencionElectronica {
    
    private String pathCORE = null; 
    
        public String[] generarRetencion(String numRetencion, String ambiente) {
        boolean existeFactura = false;
        String[] mensaje = new String[2];
        try {
            Emisor emisor;
            ResultSet rs = CargarDatosERP.ConsultaFacturas(numRetencion);
            while (rs.next()) {
                String[] numFac = (rs.getString(5) == null ? "" : rs.getString(5).trim()).split("-");
                int idFactura = Integer.parseInt(rs.getString(1) == null ? "" : rs.getString(1).trim());
                int idCliente = Integer.parseInt(rs.getString(3) == null ? "" : rs.getString(3).trim());
                String observacion = rs.getString(6) == null ? "" : rs.getString(6).trim();
                String ruc=null;
                String razonSocial=null;
                String direccion=null;
                
                ResultSet rsEmp = CargarDatosERP.ConsultaEmpresa();
                
                while (rsEmp.next()) {
                    ruc=rsEmp.getString(1) == null ? "" : rsEmp.getString(1).trim();
                    razonSocial=rsEmp.getString(2) == null ? "" : rsEmp.getString(2).trim();
                    direccion=rsEmp.getString(3) == null ? "" : rsEmp.getString(3).trim();
                }
                
                emisor = new Emisor();
                emisor.setRuc(ruc);
                emisor.setRazonSocial(razonSocial);
                emisor.setNombreComercial(razonSocial);
                emisor.setDireccionMatriz(direccion);
                emisor.setDirEstablecimiento(direccion);
                emisor.setCodPuntoEmision(numFac[1]);
                emisor.setCodigoEstablecimiento(numFac[0]);
                emisor.setTipoAmbiente(ambiente);//2 produccion
                emisor.setTipoEmision(Constantes.tipoEmision);

                Clientes cliente = new Clientes();
                try {
                    ResultSet rs1 = CargarDatosERP.ConsultaClientes(idCliente);
                    while (rs1.next()) {
                        cliente = new Clientes();
                        cliente.setNumeroIdentificacio(rs1.getString(1) == null ? "" : rs1.getString(1).trim());//"9999999999999"
                        cliente.setApellido(rs1.getString(2) == null ? "" : rs1.getString(2).trim()); //"CONSUMIDOR FINAL"
                        cliente.setDireccion(rs1.getString(3) == null ? "" : rs1.getString(3).trim());
                        if (ambiente.equals("2")) {
                            cliente.setCorreo(rs1.getString(4) == null ? "" : rs1.getString(4).trim());
                            this.pathCORE=Constantes.pathCORE;
                        } else {
                            this.pathCORE=Constantes.pathCORE_Test;
                            cliente.setCorreo("alex.becerra@emgirs.gob.ec");
                        }
                        cliente.setTelefonoConvencional(rs1.getString(5) == null ? "" : rs1.getString(5).trim());
                        cliente.setObservacion(observacion);
                    }
                } catch (SQLException e) {
                    mensaje[0] = "Error de ConsultaClientes" + e.getMessage();

                } catch (Exception ex) {
                    mensaje[0] = "Error de ConsultaClientes" + ex.getMessage();
                }
                
                /*
                //Comprobante de Retencion
                String mes="11";
                String anio="2015";
                String secuencialRetencion="1";
                String numFacturaRetener="001001000000001";
                String fechaFactura="29/11/2015";
                double valorFactura=122.22;

                GeneraRetencionXMLauto objGeneraRetencionXMLauto=new GeneraRetencionXMLauto(secuencialRetencion,mes,anio,emisor,cliente);
                objGeneraRetencionXMLauto.adicionarDetalle("IVA","2",TipoComprobanteEnum.FACTURA.getDescripcion(),numFacturaRetener,fechaFactura,valorFactura);
                filePath=objGeneraRetencionXMLauto.generarComprobateRetencion(strDirectorio);
                if(enviar)
                {
                    consumoServiciosCore obj1 = new consumoServiciosCore();
                    obj1.consumoFull_AuthorizationsJson(targetURL,filePath,cliente.getCorreo());
                }
                */
                
                //fecha desde el erp 2015-10-06
                String[] fechaFac = (rs.getString(4) == null ? "" : rs.getString(4).trim()).split("-");
                Date fechaEmision = new cls_fechas().DeStringADate(fechaFac[2] + "/" + fechaFac[1] + "/" + fechaFac[0]);
                String secuencialFactura = numFac[2];

                String subTotal = Redondear(rs.getDouble(7)) + "";
                String valorTotal = Redondear(rs.getDouble(9)) + "";

                GeneraXMLsri objGeneraXMLsri = new GeneraXMLsri(Constantes.pathFac, pathCORE);
                objGeneraXMLsri.llenarFactura(emisor, cliente, fechaEmision, secuencialFactura, subTotal, valorTotal);

                try {
                    ResultSet rs2 = CargarDatosERP.ConsultaDetalleFact(idFactura);
                    while (rs2.next()) {
                        String codPrincipal = rs2.getString(1) == null ? "" : rs2.getString(1).trim();
                        String codAux = rs2.getString(2) == null ? "" : rs2.getString(2).trim();
                        String cantidad = rs2.getString(4) == null ? "" : rs2.getString(4).trim();
                        String descipcion = rs2.getString(3) == null ? "" : rs2.getString(3).trim();
                        String valorUnit = rs2.getString(5) == null ? "" : rs2.getString(5).trim();
                        String obs = rs2.getString(7) == null ? "" : rs2.getString(7).trim();
                        objGeneraXMLsri.llenarDetFact(codPrincipal, codAux, cantidad, descipcion, valorUnit, obs);
                    }
                } catch (SQLException e) {
                    mensaje[0] = "Error de ConsultaDetalleFact" + e.getMessage();

                } catch (Exception ex) {
                    mensaje[0] = "Error de ConsultaDetalleFact" + ex.getMessage();
                }
                mensaje = objGeneraXMLsri.generarXMLfact(Constantes.enviarSRI);
                existeFactura = true;
            }
        } catch (SQLException e) {
            mensaje[0] = "Error de ConsultaFacturas" + e.getMessage();
        } catch (Exception ex) {
            mensaje[0] = "Error de ConsultaFacturas" + ex.getMessage();
        }

        if (!existeFactura) {
            mensaje[0] = "Error:";
            mensaje[1] = "La factura no existe";
        }

        return mensaje;
    }

    
    public double Redondear(double numero) //2 decimales
    {
        return Math.rint(numero * 100) / 100;
    }
    
}

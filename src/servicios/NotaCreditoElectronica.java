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
public class NotaCreditoElectronica {
    
    private String pathCORE = null; 
    
    public String[] generarNotaCredito(String numNC, String ambiente) throws Exception {
        boolean existeNC = false;
        String[] mensaje = new String[2];
        try {
            Emisor emisor;
            ResultSet rs = CargarDatosERP.ConsultaNotaCreditos(numNC);
            while (rs.next()) {
                existeNC = true;
                String[] numNotCredito=(rs.getString(7) == null ? "" : rs.getString(7).trim()).split("-");
                if(numNotCredito.length<3)
                    throw new Exception("- Formato de secuencial 000-000-0000000");
                int idFactura = Integer.parseInt(rs.getString(2) == null ? "" : rs.getString(2).trim());
                int idCliente = Integer.parseInt(rs.getString(9) == null ? "" : rs.getString(9).trim());
                String motivoAnulacion = rs.getString(3) == null ? "" : rs.getString(3).trim();
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
                emisor.setCodPuntoEmision(numNotCredito[1]);
                emisor.setCodigoEstablecimiento(numNotCredito[0]);
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
                        //cliente.setObservacion(motivoAnulacion);
                    }
                } catch (SQLException e) {
                    throw new Exception("Error de Consulta Clientes SQL " + e.getMessage());

                } catch (Exception ex) {
                    throw new Exception("Error de Consulta Clientes " + ex.getMessage());
                }

                //fecha desde el erp 2015-10-06
                Date fechaEmision = new cls_fechas().DeStringADate2(rs.getString(8) == null ? "" : rs.getString(8).trim());
                String secuencialDocumentoFULL=rs.getString(11).trim(); 
                String fechaDocumento=new cls_fechas().dateDeStringAString(rs.getString(10) == null ? "" : rs.getString(10).trim());
                String secuencialNC = numNotCredito[2];

                String subTotal = Redondear(rs.getDouble(4)) + "";
                String valorTotal = Redondear(rs.getDouble(6)) + "";

                GeneraXMLsri objGeneraXMLsri = new GeneraXMLsri(Constantes.pathFac, pathCORE);
                objGeneraXMLsri.llenarNotaCredito(emisor, cliente, fechaEmision, secuencialNC, subTotal, valorTotal,secuencialDocumentoFULL,fechaDocumento,motivoAnulacion);

                try {
                    ResultSet rs2 = CargarDatosERP.ConsultaDetalleFact(idFactura);
                    while (rs2.next()) {
                        String codPrincipal = rs2.getString(1) == null ? "" : rs2.getString(1).trim();
                        String codAux = rs2.getString(2) == null ? "" : rs2.getString(2).trim();
                        String cantidad = rs2.getString(4) == null ? "" : rs2.getString(4).trim();
                        String descipcion = rs2.getString(3) == null ? "" : rs2.getString(3).trim();
                        String valorUnit = rs2.getString(5) == null ? "" : rs2.getString(5).trim();
                        String obs = rs2.getString(7) == null ? "" : rs2.getString(7).trim();
                        objGeneraXMLsri.llenarDetNotaCredito(codPrincipal, codAux, cantidad, descipcion, valorUnit, obs);
                    }
                } catch (SQLException e) {
                    throw new Exception("Error de Consulta Detalle Factura SQL " + e.getMessage());

                } catch (Exception ex) {
                    throw new Exception("Error de Consulta Detalle Factura " + ex.getMessage());
                }
                mensaje = objGeneraXMLsri.generarXMLnotaCredito(Constantes.enviarSRI);
            }
        } catch (SQLException e) {
            throw new Exception("Error de Consulta nota de credito SQL " + e.getMessage());
        } catch (Exception ex) {
            throw new Exception("Error de Consulta nota de credito " + ex.getMessage());
        }

        if (!existeNC) {
            mensaje[0] = "Error:";
            mensaje[1] = "La nota de credito no existe";
        }

        return mensaje;
    }

    public double Redondear(double numero) //2 decimales
    {
        return Math.rint(numero * 100) / 100;
    }

    
}

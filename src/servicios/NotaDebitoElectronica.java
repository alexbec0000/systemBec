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
public class NotaDebitoElectronica {
     
    private String pathCORE = null; 
    
    public String[] generarNotaDebito(String numND, String ambiente) throws Exception {
        boolean existeND = false;
        String[] mensaje = new String[2];
        try {
            Emisor emisor;
            ResultSet rs = CargarDatosERP.ConsultaNotaDebitos(numND);
            while (rs.next()) {
                String[] numNotaD=(rs.getString(4) == null ? "" : rs.getString(4).trim()).split("-");
                if(numNotaD.length<3)
                    throw new Exception("- Formato de secuencial 000-000-0000000");
                
                int idNotaD = Integer.parseInt(rs.getString(1) == null ? "" : rs.getString(1).trim());
                int idCliente = Integer.parseInt(rs.getString(2) == null ? "" : rs.getString(2).trim());
                String observacion=rs.getString(7) == null ? "" : rs.getString(7).trim();
                String observacion2="VALOR FACTURA: $"+(rs.getString(8) == null ? "" : rs.getString(8).trim());
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
                emisor.setCodPuntoEmision(numNotaD[1]);
                emisor.setCodigoEstablecimiento(numNotaD[0]);
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
                        //cliente.setObservacion(observacion);
                        cliente.setObservacion(observacion2);
                    }
                } catch (SQLException e) {
                    throw new Exception("Error de Consulta Clientes SQl " + e.getMessage());

                } catch (Exception ex) {
                    throw new Exception("Error de Consulta Clientes " + ex.getMessage());
                }
                
                /*
                //NOTA DE DEBITO
                objGeneraNotaDebitoXMLauto.agregarDetalle("EJEMPLO", "10");
                filePath=objGeneraNotaDebitoXMLauto.GenerarNotaDebito(strDirectorio,0); //0->12%; 1->0%; 2->NoObjetoIVA; 3->ExentoIVA
                */
                
                //fecha desde el erp 2015-10-06
                Date fechaEmision = new cls_fechas().DeStringADate2(rs.getString(3) == null ? "" : rs.getString(3).trim());
                String secuencialDocumentoFULL=rs.getString(5) == null ? "" : rs.getString(5).trim();
                String fechaDocumento = new cls_fechas().dateDeStringAString(rs.getString(6) == null ? "" : rs.getString(6).trim());
             
                GeneraXMLsri objGeneraXMLsri = new GeneraXMLsri(Constantes.pathFac, pathCORE);
                objGeneraXMLsri.llenarNotaDebito(emisor, cliente, fechaEmision, numNotaD[2], secuencialDocumentoFULL, fechaDocumento);

                try {
                    ResultSet rs2 = CargarDatosERP.ConsultaDetalleND(idNotaD);
                    StringBuilder detalle;
                    while (rs2.next()) {
                        detalle = new StringBuilder();
                        //detalle.append("Fecha Vencimiento: ").append(rs2.getString(6) == null ? "" : rs2.getString(6).trim());
                        //detalle.append(" Dias Retraso: ").append(rs2.getString(7) == null ? "" : rs2.getString(7).trim());
                        detalle.append(rs2.getString(8) == null ? "INTERÉS POR MORA" : rs2.getString(8).trim());
                        //String valor = rs2.getString(1) == null ? "" : rs2.getString(1).trim(); //Base imponible
                        String valor = rs2.getString(2) == null ? "0" : rs2.getString(2).trim(); //Interes generado
                        objGeneraXMLsri.llenarDetNotaDebito(detalle.toString(), valor);
                    }
                } catch (SQLException e) {
                    throw new Exception("Error de Consulta Detalle ND SQL " + e.getMessage());

                } catch (Exception ex) {
                    throw new Exception("Error de Consulta Detalle ND " + ex.getMessage());
                }
                mensaje = objGeneraXMLsri.generarXMLnotaDebito(Constantes.enviarSRI);
                existeND = true;
            }
        } catch (SQLException e) {
            throw new Exception("Error de Consulta nota de debito SQL " + e.getMessage());
        } catch (Exception ex) {
            throw new Exception("Error de Consulta nota de debito " + ex.getMessage());
        }

        if (!existeND) {
            mensaje[0] = "Error:";
            mensaje[1] = "La nota de debito no existe";
        }

        return mensaje;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import comprobantes.util.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author abecerra
 */
public class CargarDatosERP {

    public static ResultSet ConsultaEmpresa() throws Exception {
        //Conexion.conectaERP();
        String sqlFact = "SELECT identificacion_empr, nom_empr, direccion_empr, telefono_empr FROM sis_empresa limit 1;";
        PreparedStatement ps = Conexion.getCon().prepareStatement(sqlFact);
        ResultSet rs = ps.executeQuery();
        Conexion.cerrar();
        return rs;
    }
    
    public static ResultSet ConsultaClientes(int idCliente) throws Exception {
        //Conexion.conectaERP();

        String sqlCli = "SELECT  cli.ruc_comercial_recli, cli.razon_social_recli, cliD.direccion_recld, cliE.email_recle, cli.telefono_factura_recli "
                + " FROM rec_clientes cli "
                + " join rec_cliente_direccion cliD on cliD.ide_recli=cli.ide_recli and cliD.activo_recld=true "
                + " left join rec_cliente_email cliE on cliE.ide_recli=cli.ide_recli and cliE.notificacion_recle=true and cliE.activo_recle=true "
                + " where cli.activo_recli=true and cli.ide_recli=" + idCliente + ";";

        PreparedStatement ps = Conexion.getCon().prepareStatement(sqlCli);
        ResultSet rs = ps.executeQuery();
        Conexion.cerrar();
        return rs;
    }
    
    public static ResultSet ConsultaFacturas(String numFac) throws Exception {
        //Conexion.conectaERP();
        String sqlFact = "SELECT ide_fafac, ide_fadaf,ide_recli,fecha_transaccion_fafac, secuencial_fafac, "
                + "  observacion_fafac, base_aprobada_fafac, valor_iva_fafac, total_fafac "
                + "  FROM fac_factura "
                + "  where ide_coest in (2,24) and activo_fafac=true and secuencial_fafac like '" + numFac + "';";
        PreparedStatement ps = Conexion.getCon().prepareStatement(sqlFact);
        ResultSet rs = ps.executeQuery();
        Conexion.cerrar();
        return rs;
    }

    public static ResultSet ConsultaDetalleFact(int idFact) throws Exception {
        //Conexion.conectaERP();
        String sqlDetFac = "SELECT ide_fadef,mat.ide_bomat, mat.detalle_bomat, cantidad_fadef, valor_fadef, total_fadef, observacion_fadef "
                + "  FROM fac_detalle_factura df "
                + "  join bodt_material mat on mat.ide_bomat=df.ide_bomat "
                + "  where ide_fafac=" + idFact + ";";
        PreparedStatement ps = Conexion.getCon().prepareStatement(sqlDetFac);
        ResultSet rs = ps.executeQuery();
        Conexion.cerrar();
        return rs;
    }
    
    public static ResultSet ConsultaNotaDebitos(String numND) throws Exception {
        //Conexion.conectaERP();
        String sqlND = "SELECT nd.ide_fanod, nd.ide_recli, nd.fecha_emision_fanod, nd.nro_nota_debito_fanod, fac.secuencial_fafac, fac.fecha_transaccion_fafac " 
            + " ,nd.detalle_fenod,fac.total_fafac FROM fac_nota_debito nd" 
            + "  join fac_detalle_debito ndd on ndd.ide_fanod=nd.ide_fanod" 
            + "  join fac_factura fac on fac.ide_fafac=ndd.ide_fafac" 
            + " where nd.activo_fanod=true and nd.ide_coest=16 and nd.nro_nota_debito_fanod='"+numND+"';";
        PreparedStatement ps = Conexion.getCon().prepareStatement(sqlND);
        ResultSet rs = ps.executeQuery();
        Conexion.cerrar();
        return rs;
    }
    
    public static ResultSet ConsultaDetalleND(int idND) throws Exception {
        //Conexion.conectaERP();
        String sqlDetND = "SELECT base_imponible_faded, interes_generado_faded, " +
                "  valor_iva_faded, total_faded, interes_aplicado_faded, " +
                "  fecha_emision_factura_faded, dias_retraso_faded, detalle_faded " +
                "  FROM fac_detalle_debito where activo_faded=true and ide_fanod="+idND+";";
        PreparedStatement ps = Conexion.getCon().prepareStatement(sqlDetND);
        ResultSet rs = ps.executeQuery();
        Conexion.cerrar();
        return rs;
    }
    
    public static ResultSet ConsultaNotaCreditos(String numNC) throws Exception {
        //Conexion.conectaERP();
        String sqlNC = "SELECT ide_fanoc, nc.ide_fafac, detalle_fanoc, valor_referencial_fanoc, " +
                        "  iva_fanoc, total_fanoc,nro_nota_credito_fanoc,fecha_fanoc,ide_recli,"
                + " fecha_transaccion_fafac,secuencial_fafac " +
                        "  FROM fac_nota_credito nc " +
                        "  join fac_factura fac on fac.ide_fafac=nc.ide_fafac "
                + " where nc.ide_coest=2 and activo_fanoc=true and nro_nota_credito_fanoc like '" + numNC + "';";
        PreparedStatement ps = Conexion.getCon().prepareStatement(sqlNC);
        ResultSet rs = ps.executeQuery();
        Conexion.cerrar();
        return rs;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  Consultas informativas para detectar el problema en la autorización  //
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Comprueba si existe un cliente registrado en el ERP, buscando por el
     * secuencia de la factura.
     *
     * @param secuencial
     * @return
     */
    public static Boolean existeCliente(String secuencial) {
        // Variable de retorno
        Boolean existe = false;

        try {
            // Abriendo la conexión
            //Conexion.conectaERP();

            try {
                String sqlQuery = "select cli.ide_recli, cli.ruc_comercial_recli, cli.razon_social_recli "
                        + "from rec_clientes cli join fac_factura fac on cli.ide_recli = fac.ide_recli"
                        + "where fac.secuencial_fafac = '" + secuencial + "';";
                PreparedStatement ps = Conexion.getCon().prepareStatement(sqlQuery);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    System.out.println(rs.getString("ide_recli") + " - "
                            + rs.getString("ruc_comercial_recli") + " - "
                            + rs.getString("razon_social_recli"));

                    existe = true;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            // Cerrando la conexión
            Conexion.cerrar();

        } catch (Exception ex) {
            Logger.getLogger(CargarDatosERP.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existe;
    }
}

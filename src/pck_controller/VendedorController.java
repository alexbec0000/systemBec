/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pck_accesoDatos.cls_conexion;
import pck_entidades.cls_vendedor;
import pck_utilidades.CustomImageIcon;
import pck_utilidades.Generales;

/**
 *
 * @author Alex
 */
public class VendedorController extends AbstractController {

    ArrayList<cls_vendedor> listVend;
    cls_vendedor vend;

    public ArrayList<cls_vendedor> cargarVendedores() {
        try {
            listVend = new ArrayList();
            String sql = "SELECT ID_VEN, CED_RUC_VEN, NOMBRE_APELLIDO_VEN, DIRECCION_VEN, "
                    + "TELEFONO_VEN, CELULAR_VEN, VENTAS,"
                    + "  EMAIL_VEN, OBSERVACION_VEN, FOTO_VEN, LABORA,"
                    + " FECHA_INGRESO_VEN,FEC_NACIMIENTO_VEN,USUARIO_VEN,"
                    + " CLAVE_VEN  FROM vendedores;";
            rs = cls_conexion.getStatement().executeQuery(sql);
            while (rs.next()) {
                vend = new cls_vendedor();
                vend.setCodigo(rs.getString(1));
                vend.setCI(rs.getString(2));
                vend.setNombres(rs.getString(3));
                vend.setDireccion(rs.getString(4));
                vend.setTelefono(rs.getString(5));
                vend.setCelular(rs.getString(6));
                vend.setVentas(rs.getFloat(7));
                vend.setMail(rs.getString(8));
                vend.setObs(rs.getString(9));
                try {
                    vend.setImagen(Generales.getFoto(rs.getBinaryStream(10)));
                } catch (Exception ex) {
                    //ex.printStackTrace();
                    vend.setImagen(new CustomImageIcon(getClass().getResource("/recursos/icono_cliente.jpg")));
                }
                vend.setEstado(rs.getBoolean(11));
                vend.setFechaIngreso(rs.getString(12));
                vend.setFechaNacimiento(rs.getString(13));
                vend.setUsuario(rs.getString(14));
                vend.setClave(rs.getString(15));

                listVend.add(vend);
            }

            rs.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listVend;
    }

    public int grabarRegistro(cls_vendedor vend) {

        int resultado = 0;
        String sql = "INSERT INTO vendedores(ID_VEN, CED_RUC_VEN, NOMBRE_APELLIDO_VEN, DIRECCION_VEN, TELEFONO_VEN, CELULAR_VEN, VENTAS, "
                + "EMAIL_VEN, OBSERVACION_VEN, FOTO_VEN) "
                + "VALUES('" + vend.getCodigo() + "','"
                + vend.getCI() + "','"
                + vend.getNombres() + "','"
                + vend.getDireccion() + "','"
                + vend.getTelefono() + "','"
                + vend.getCelular() + "','"
                + vend.getVentas() + "','"
                + vend.getMail() + "','"
                + vend.getObs() + "',?);";

        String sql2 = "UPDATE vendedores "
                + "   SET CED_RUC_VEN='" + vend.getCI() + "', NOMBRE_APELLIDO_VEN='" + vend.getNombres() + "', DIRECCION_VEN='" + vend.getDireccion() + "', "
                + "     TELEFONO_VEN='" + vend.getTelefono() + "', CELULAR_VEN='" + vend.getCelular() + "', "
                + "     EMAIL_VEN='" + vend.getMail() + "', "
                + "     OBSERVACION_VEN='" + vend.getObs() + "', "
                + "     FOTO_VEN=? WHERE ID_VEN='" + vend.getCodigo() + "';";

        try {
            if (existeRegistro("vendedores", "ID_VEN", vend.getCodigo())) {
                System.out.println(sql2);
                if (vend.getFoto() == null) {
                    vend.setFoto(recuperaImagen("vendedores", "FOTO_VEN", "ID_VEN", vend.getCodigo()));
                }
                PreparedStatement ps = cls_conexion.getPreparedStatement(sql2);
                ps.setBinaryStream(1, vend.getFoto());
                ps.execute();
            } else {
                System.out.println(sql);
                //resultado = cls_conexion.getStatement().executeUpdate(sql);
                PreparedStatement ps = cls_conexion.getPreparedStatement(sql);
                ps.setBinaryStream(1, vend.getFoto());
                ps.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;

    }

    public static String login(String usuario, String clave) {
        String retorno="";
        try {

            String sql = "SELECT ID_VEN FROM vendedores where LABORA=true and USUARIO_VEN='"+usuario+"'"
                    + " and CLAVE_VEN='"+clave+"';";
            ResultSet rs = cls_conexion.getStatement().executeQuery(sql);
            while (rs.next()) {
                retorno=rs.getString("ID_VEN");
            }

            rs.close();

        } catch (SQLException ex) {
            System.out.println(ex);
            retorno="-1";
        }

        return retorno;
    }

    public static ResultSet obtenerVendedores() {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("select id_ven, NOMBRE_APELLIDO_VEN as elvendedor from vendedores order by elvendedor");
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ResultSet obtenerTotalVendedores() {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("select count(id_ven) as cuantos from vendedores");
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ResultSet listarVendedores() {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("select concat(USUARIO_VEN,' ',NOMBRE_APELLIDO_VEN) as elvendedor from vendedores order by elvendedor");
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ResultSet listarVendedores(String nombre) {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("select id_ven,concat(USUARIO_VEN,' ',NOMBRE_APELLIDO_VEN) as elvendedor from vendedores where concat(USUARIO_VEN,' ',NOMBRE_APELLIDO_VEN) like '" + nombre + "%' order by elvendedor");
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ResultSet obtenerVendedores(String condicion) {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("select facv_cab.num_facv,DATE_FORMAT(facv_cab.fecha,'%d/%m/%Y') as fecha,concat(clientes.RAZON_SOCIAL_CLI,' ',clientes.NOMBRE_APELLIDO_CLI) as elcliente,concat(vendedores.USUARIO_VEN,' ',vendedores.NOMBRE_APELLIDO_VEN) as elvendedor,"
                    + "(facv_cab.sub_gen-facv_cab.tot_des+facv_cab.tot_iva) as total,anulada from facv_cab inner join clientes on(facv_cab.id_cli=clientes.id_cli) inner join vendedores on(facv_cab.id_ven=vendedores.id_ven) "
                    + condicion);
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void actualizarVentasVendedores(String total, String ID_VEN) {
        try {
            String sql = "UPDATE VENDEDORES SET VENTAS=VENTAS+" + Double.valueOf(total) + " WHERE ID_VEN='" + ID_VEN + "'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

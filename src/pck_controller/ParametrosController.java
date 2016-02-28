/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pck_accesoDatos.cls_conexion;

/**
 *
 * @author abecerra
 */
public class ParametrosController extends AbstractController {

    public static ResultSet obtenerParametros() {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT * FROM parametros order by id_par");
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ResultSet obtenerTotalParametros() {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("select count(id_par) as cuantos from parametros");
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String obtenerDescripcionParametroXid(String idParametro) {
        String resultado = null;
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT descripcion_par FROM parametros where id_par='" + idParametro + "';");
            ResultSet rs = sentenciacli.getResultSet();
            while (rs.next()) {
                resultado = rs.getString(1);
            }
            return resultado;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public static void ingresarParametro() {
    }

    public static void actualizarParametro(String idParametro, String nombre, String descripcion, String valor1, boolean estado) {
        try {
            double valor = 0;
            try {
                valor = Double.parseDouble(valor1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            String sql = "UPDATE parametros SET nombre_par='" + nombre + "', descripcion_par='" + descripcion + "', valor_par=" + valor + " , activo_par=" + estado + " WHERE id_par='" + idParametro + "'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void actualizarEstadoParametro(String idParametro, boolean estado) {
        try {
            String sql = "UPDATE parametros SET activo_par=" + estado + " WHERE id_par='" + idParametro + "'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ResultSet obtenerEmpresa() {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT * FROM empresa order by id_emp");
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ResultSet obtenerTotalEmpresa() {
        try {
            Statement sentenciacli = cls_conexion.getStatement();
            sentenciacli.executeQuery("select count(id_emp) as cuantos from empresa");
            return sentenciacli.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

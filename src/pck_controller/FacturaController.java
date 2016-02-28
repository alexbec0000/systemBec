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
public class FacturaController extends AbstractController{
    
    public static ResultSet obtenerFACV_CAB()
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT *,(sub_gen-tot_des+tot_iva) as total,round((sub_gen-tot_des+tot_iva-pago_ini),2) as saldo FROM facv_cab ORDER BY NUM_FACV");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static ResultSet obtenerFacv_det(String num_facv)
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select facv_det.id_art,articulos.DESCRIPCION_ART,facv_det.pvp,facv_det.cant "
                    + " from facv_det inner join articulos on(facv_det.id_art=articulos.id_art) "
                    + "where num_facv='" + num_facv + "'");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
     public static ResultSet obtenerTotalFACV_CABxId_cli(String ID_CLI)
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT COUNT(NUM_FACV) AS CUANTOS FROM facv_cab WHERE ID_CLI='" + ID_CLI + "'");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static void anularFactura(String observacion, String NUM_FACV)
    {
        try {
            String sql ="UPDATE facv_cab SET ANULADA=1,OBSERVAC='"+observacion+"' WHERE NUM_FACV='"+NUM_FACV+"'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     
    public static void insertarFACV_CAB(String Numero, String id_cli, String ID_VEN, String fechasql
                    ,Object formaPago, String Descuento, String Subtotal, String Totdes, String Totiva
                    ,String Pago, String Observa)
    {
        try {
            String sql ="INSERT INTO facv_cab (NUM_FACV,ID_CLI,ID_VEN,FECHA,FOR_PAG,DESCU,SUB_GEN,TOT_DES,TOT_IVA,pago_ini,OBSERVAC,ANULADA) VALUES('"+Numero+
                        "','"+id_cli+"','"+ID_VEN+"','"+fechasql+"','"+formaPago+
                        "',"+Descuento+","+Subtotal+","+Totdes+","+Totiva+","+
                        Pago+",'"+Observa+"',0)";
            
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void insertarFACV_DET(String NUM_FACV, Object ID_ART, Object CANT, Object PVP)
    {
        try {
            String sql ="INSERT INTO FACV_DET (NUM_FACV,ID_ART,CANT,PVP) VALUES('"+NUM_FACV+"','"+ID_ART+"',"+CANT+","+PVP+")";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void actualizarFactura(String NUM_FACV, String observacion, String estado)
    {
        try {
            String sql ="update facv_cab set OBSERVAC='"+observacion+"', PROCESADA="+estado+" where NUM_FACV='"+NUM_FACV+"'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}

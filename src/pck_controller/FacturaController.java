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
    
    public static ResultSet obtenerARTICULOS()
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT ID_ART,DESCRIPCION_ART,STOCK,PVP_ART FROM ARTICULOS ORDER BY ID_ART");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static ResultSet obtenerTotalARTICULOS()
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select count(id_art) as cuantos from articulos");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static ResultSet obtenerStockARTICULOS(String codlinea)
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT STOCK FROM ARTICULOS WHERE ID_ART='"+codlinea+"'");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static ResultSet obtenerFACV_CAB()
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT *,(sub_gen-tot_des+tot_iva) as total,round((sub_gen-tot_des+tot_iva-pago_ini),2) as saldo FROM FACV_CAB ORDER BY NUM_FACV");
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
            sentenciacli.executeQuery("SELECT COUNT(NUM_FACV) AS CUANTOS FROM FACV_CAB WHERE ID_CLI='" + ID_CLI + "'");
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
            String sql ="UPDATE FACV_CAB SET ANULADA=1,OBSERVAC='"+observacion+"' WHERE NUM_FACV='"+NUM_FACV+"'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void actualizarStock(Object stock, Object ID_ART)
    {
        try {
            String sql ="UPDATE ARTICULOS SET STOCK=STOCK+"+stock+" WHERE ID_ART='"+ID_ART+"'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void actualizarVentasVendedores(String total, String ID_VEN)
    {
        try {
            String sql ="UPDATE VENDEDORES SET VENTAS=VENTAS+"+Double.valueOf(total)+" WHERE ID_VEN='"+ID_VEN+"'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void actualizarSaldoCliente(String saldo, String id_cli)
    {
        try {
            String sql ="UPDATE CLIENTES SET SALDO_CLI=SALDO_CLI+"+Double.valueOf(saldo)+" WHERE ID_CLI='"+id_cli+"'";
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
            String sql ="INSERT INTO FACV_CAB (NUM_FACV,ID_CLI,ID_VEN,FECHA,FOR_PAG,DESCU,SUB_GEN,TOT_DES,TOT_IVA,pago_ini,OBSERVAC,ANULADA) VALUES('"+Numero+
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
    
}

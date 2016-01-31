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
import pck_entidades.cls_producto;
import pck_utilidades.CustomImageIcon;
import pck_utilidades.Generales;

/**
 *
 * @author Alex
 */
public class ProductoController extends AbstractController{
    
    ArrayList <cls_producto> listProd;
    cls_producto prod;
    
    public ArrayList <cls_producto> cargarProductos()
    {
         try{
             listProd=new ArrayList();
             String sql="SELECT ID_ART, CODIGO_BARRAS_ART, FECHA_ART, DESCRIPCION_ART, "
                     + "MARCA_ART, MODELO_ART, LINEA_ART, STOCK, STK_MIN, STK_MAX, "
                     + "PVP_ART, PCO_ART, OBSERVACION_ART, PROVEEDOR_ART, GARANTIA, "
                     + "FECHA_VENCIMIENTO_ART, FOTO_ART FROM articulos;";
                 rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
                prod = new cls_producto();
                prod.setCodigo_art(rs.getString(1));
                prod.setCodigo_barras(rs.getString(2));
                prod.setFecha(rs.getString(3));
                prod.setNombre(rs.getString(4));
                prod.setMarca(rs.getString(5));
                prod.setModelo(rs.getString(6));
                prod.setLinea(rs.getString(7));
                prod.setStock(rs.getInt(8));
                prod.setStk_minimo(rs.getInt(9));
                prod.setStk_maximo(rs.getInt(10));
                prod.setP_venta(rs.getFloat(11));
                prod.setP_compra(rs.getFloat(12));
                prod.setObs(rs.getString(13));
                prod.setProveedor(rs.getString(14));
                prod.setGarantia(rs.getString(15));
                prod.setFechaVencimiento(rs.getString(16));

                try{
                    prod.setImagen(Generales.getFoto(rs.getBinaryStream(17)));
                }
                catch(Exception ex){
                    //ex.printStackTrace();
                    prod.setImagen(new CustomImageIcon(getClass().getResource("/recursos/icono_cliente.jpg")));
                }

                listProd.add(prod);
            }
           
            rs.close();
           
          }catch(SQLException ex){
               ex.printStackTrace();
        }
        return listProd;
    }
    
    public int grabarRegistro(cls_producto prod) {
        
        int resultado = 0;
        String sql = "INSERT INTO articulos(ID_ART, CODIGO_BARRAS_ART, FECHA_ART, "
                + "DESCRIPCION_ART, MARCA_ART, MODELO_ART, LINEA_ART, STOCK, "
                + "STK_MIN, STK_MAX, PVP_ART, PCO_ART, OBSERVACION_ART, PROVEEDOR_ART, "
                + "GARANTIA, FECHA_VENCIMIENTO_ART, FOTO_ART)" +
	"VALUES('"+prod.getCodigo_art()+"','"+ 
                prod.getCodigo_barras()+"','"+
                prod.getFecha()+"','"+
                prod.getNombre()+"','"+
                prod.getMarca()+"','"+
                prod.getModelo()+"','"+
                prod.getLinea()+"',"+
                prod.getStock()+"',"+
                prod.getStk_minimo()+","+
                prod.getStk_maximo()+","+
                prod.getP_venta()+","+
                prod.getP_compra()+",'"+
                prod.getObs()+"','"+
                prod.getProveedor()+"','"+
                prod.getGarantia()+"','"+
                prod.getFechaVencimiento()+"',?);";
        
        String sql2 ="UPDATE articulos" +
            " SET CODIGO_BARRAS_ART='"+prod.getCodigo_barras()+"', DESCRIPCION_ART='"+prod.getNombre()+"', FECHA_VENCIMIENTO_ART='" +prod.getFechaVencimiento()+"', "+
            "   LINEA_ART='"+prod.getLinea()+"', MARCA_ART='"+prod.getMarca()+"', MODELO_ART='"+prod.getModelo()+"', "+
            "   OBSERVACION_ART='"+prod.getObs()+"', PROVEEDOR_ART='"+prod.getProveedor()+"', GARANTIA='"+prod.getGarantia()+"', " +
            "   STK_MIN="+prod.getStk_minimo()+", STK_MAX="+prod.getStk_maximo()+", "+
            "   PCO_ART="+prod.getP_compra()+", STOCK="+prod.getStock()+", PVP_ART="+prod.getP_venta()+", " +
            "   FOTO_ART=? WHERE id_art='"+prod.getCodigo_art()+"';"; 
        
        try {
            if(existeRegistro("articulos","id_art",prod.getCodigo_art()))
            {
                System.out.println(sql2);
                if(prod.getFoto() == null)
                    prod.setFoto(recuperaImagen("articulos","FOTO_ART","id_art",prod.getCodigo_art()));
                PreparedStatement ps=cls_conexion.getPreparedStatement(sql2);
                ps.setBinaryStream(1,prod.getFoto());
                ps.execute();
            }
            else{
                System.out.println(sql);
                PreparedStatement ps=cls_conexion.getPreparedStatement(sql);
                ps.setBinaryStream(1,prod.getFoto());
                ps.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return resultado;
        
    }
    
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
    
    public static ResultSet listarMarcas()
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select detalle_lm, id_lm from linea_marca where linea=false order by detalle_lm");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
     public static ResultSet listarLineas()
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select detalle_lm, id_lm from linea_marca where linea=true order by detalle_lm");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
     public static ResultSet listarMarcas(String detalle)
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select detalle_lm as detalle, id_lm as id from linea_marca where linea=false and detalle_lm like '%"+detalle+"%' order by detalle_lm");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
     public static ResultSet listarLineas(String detalle)
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select detalle_lm as detalle, id_lm as id from linea_marca where linea=true and detalle_lm like '%"+detalle+"%' order by detalle_lm");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
     
    public static void insertarLineaMarca(String id, String detalle, boolean linea)
    {
        try {
            String sql ="insert into linea_marca (id_lm,detalle_lm,linea) values ('"+id+"','"+detalle+"',"+linea+");";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void actualizarLineaMarca(String id, String detalle, boolean linea)
    {
        try {
            String sql ="update linea_marca set detalle_lm ='"+detalle+"' values where linea="+linea+" and id_lm='"+id+"';";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static ResultSet listarARTICULOS(String descripcion)
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("SELECT ID_ART,DESCRIPCION_ART,STOCK,PVP_ART FROM ARTICULOS WHERE DESCRIPCION_ART LIKE '" + descripcion + "%' ORDER BY DESCRIPCION_ART");
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
    
    public static void actualizarStock(Object stock, Object ID_ART)
    {
        try {
            String sql ="UPDATE ARTICULOS SET STOCK=STOCK+"+stock+" WHERE ID_ART='"+ID_ART+"'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}

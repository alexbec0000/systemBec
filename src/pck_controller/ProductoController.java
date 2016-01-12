/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_controller;

import java.sql.SQLException;
import java.util.ArrayList;
import pck_accesoDatos.cls_conexion;
import pck_entidades.cls_producto;

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
             String sql="SELECT codigo_barras, codigo_producto, codigo_fabricante, nombre, linea, " +
                        "       marca, modelo, obs, proveedor, vendedor, garantia, fecha, stk_minimo, " +
                        "       stk_maximo, p_compra, moneda, p_venta, utilidad1, valor1, utilidad2, " +
                        "       valor2, p_pmayor, imagen" +
                        "  FROM producto;";
                 rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
                prod = new cls_producto();
                prod.setCodigo_barras(rs.getString(1));
                prod.setCodigo_producto(rs.getString(2));
                prod.setCodigo_fabricante(rs.getString(3));
                prod.setNombre(rs.getString(4));
                prod.setLinea(rs.getString(5));
                prod.setMarca(rs.getString(6));
                prod.setModelo(rs.getString(7));
                prod.setObs(rs.getString(8));
                prod.setProveedor(rs.getString(9));
                prod.setVendedor(rs.getString(10));
                prod.setGarantia(rs.getString(11));
                prod.setFecha(rs.getString(12));
                prod.setStk_minimo(rs.getInt(13));
                prod.setStk_maximo(rs.getInt(14));
                prod.setP_compra(rs.getFloat(15));
                prod.setMoneda(rs.getString(16));
                prod.setP_venta(rs.getFloat(17));
                prod.setUtilidad1(rs.getString(18));
                prod.setValor1(rs.getFloat(19));
                prod.setUtilidad2(rs.getString(20));
                prod.setValor2(rs.getFloat(21));
                prod.setP_pmayor(rs.getFloat(22));
                //prod.setImagen(rs.getString(1));

                listProd.add(prod);
            }
           
            rs.close();
           
          }catch(SQLException ex){
                     
        }
        return listProd;
    }
    
    public int grabarRegistro(cls_producto prod) {
        
        int resultado = 0;
        String sql = "INSERT INTO producto(codigo_barras, codigo_producto, codigo_fabricante, nombre, linea, " +
                    "  marca, modelo, obs, proveedor, vendedor, garantia, fecha, stk_minimo, " +
                    "  stk_maximo, p_compra, moneda, p_venta, utilidad1, valor1, utilidad2, " +
                    "  valor2, p_pmayor, imagen)" +
	"VALUES('"+prod.getCodigo_barras()+"','"+ 
            prod.getCodigo_producto()+"','"+
                prod.getCodigo_fabricante()+"','"+
                prod.getNombre()+"','"+
                prod.getLinea()+"','"+
                prod.getMarca()+"','"+
                prod.getModelo()+"','"+
                prod.getObs()+"','"+
                prod.getProveedor()+"','"+
                prod.getVendedor()+"','"+
                prod.getGarantia()+"','"+
                prod.getFecha()+"',"+
                prod.getStk_minimo()+","+
                prod.getStk_maximo()+",'"+
                prod.getP_compra()+"','"+
                prod.getMoneda()+"','"+
                prod.getP_venta()+"','"+
                prod.getUtilidad1()+"','"+
                prod.getValor1()+"','"+
                prod.getUtilidad2()+"','"+
                prod.getValor2()+"','"+
                prod.getP_pmayor()+"','"+
            prod.getImagen()+"');";
        
        String sql2 ="UPDATE producto" +
            " SET codigo_barras='"+prod.getCodigo_barras()+"', codigo_fabricante='"+prod.getCodigo_fabricante()+"', nombre='"+prod.getNombre()+"', " +
            "   linea='"+prod.getLinea()+"', marca='"+prod.getMarca()+"', modelo='"+prod.getModelo()+"', "+
            "   obs='"+prod.getObs()+"', proveedor='"+prod.getProveedor()+"', vendedor='"+prod.getVendedor()+"', garantia='"+prod.getGarantia()+"', " +
            "   fecha='"+prod.getFecha()+"', stk_minimo="+prod.getStk_minimo()+", stk_maximo="+prod.getStk_maximo()+", "+
            "   p_compra="+prod.getP_compra()+", moneda='"+prod.getMoneda()+"', p_venta="+prod.getP_venta()+", " +
            "   utilidad1='"+prod.getUtilidad1()+"', valor1="+prod.getValor1()+", utilidad2='"+prod.getUtilidad2()+"', "+
            "   valor2="+prod.getValor2()+", p_pmayor="+prod.getP_pmayor()+", imagen='"+prod.getImagen()+"'" +
            " WHERE codigo_producto='"+prod.getCodigo_producto()+"';"; 
        
        try {
            if(existeRegistro("producto","codigo_producto",prod.getCodigo_producto()))
            {
                System.out.println(sql2);
                resultado = cls_conexion.getStatement().executeUpdate(sql2);
            }
            else{
                System.out.println(sql);
                resultado = cls_conexion.getStatement().executeUpdate(sql);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return resultado;
        
    }
    
    
}

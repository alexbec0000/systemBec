/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_controller;

import java.sql.SQLException;
import java.util.ArrayList;
import pck_accesoDatos.cls_conexion;
import pck_entidades.cls_proveedor;

/**
 *
 * @author Alex
 */
public class ProveedorController extends AbstractController{
    
    ArrayList <cls_proveedor> listProv;
    cls_proveedor prov;
    
    public ArrayList <cls_proveedor> cargarProveedores()
    {
         try{
             listProv=new ArrayList();
             String sql="SELECT codigo, ruc, nombre, direccion, telefono, fax, web, mail, "
                     + " nombre_r, telefono_r, celular_r, obs" +
                     "  FROM proveedor;";
                 rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
                prov = new cls_proveedor();
                prov.setCodigo(rs.getString(1));
                prov.setRuc(rs.getString(2));
                prov.setNombre(rs.getString(3));
                prov.setDireccion(rs.getString(4)); 
                prov.setTelefono(rs.getString(5)); 
                prov.setFax(rs.getString(6)); 
                prov.setWeb(rs.getString(7)); 
                prov.setMail(rs.getString(8)); 
                prov.setNombre_r(rs.getString(9));
                prov.setTelefono_r(rs.getString(10));
                prov.setCelular_r(rs.getString(11));
                prov.setObs(rs.getString(12));
               
//                cl.setSexo(rs.getString(8));
//                cl.setFechaIngreso(rs.getDate(9));
//                cl.setFechaNacimiento(rs.getDate(10));
//                cl.setEmail(rs.getString(11));
//                cl.setVentas(rs.getDouble(12));
//                cl.setLabora(rs.getShort(13));
//                cl.setSueldo(rs.getDouble(14));
                
                listProv.add(prov);
            }
           
            rs.close();
           
        }catch(SQLException ex){
                     
        }
        return listProv;
    }
    
    public int grabarRegistro(cls_proveedor prov) {
        
        int resultado = 0;
        String sql = "INSERT INTO proveedor(codigo, ruc, nombre, direccion, telefono, fax, web, mail, "
                + " nombre_r, telefono_r, celular_r, obs) " +
	"VALUES('"+prov.getCodigo()+"','"+ 
            prov.getRuc()+"','"+
                prov.getNombre()+"','"+
                prov.getDireccion()+"','"+
                prov.getTelefono()+"','"+
                prov.getFax()+"','"+
                prov.getWeb()+"','"+
                prov.getMail()+"','"+
                prov.getNombre_r()+"','"+
                prov.getTelefono_r()+"','"+
                prov.getCelular_r()+"','"+
            prov.getObs()+"');";
        
        String sql2 ="UPDATE proveedor " +
                    "   SET ruc='"+prov.getRuc()+"', nombre='"+prov.getNombre()+"', direccion='"+prov.getDireccion()+"', "+
                    "     telefono='"+prov.getTelefono()+"', fax='"+prov.getFax()+"', web='"+prov.getWeb()+"', " +
                    "     mail='"+prov.getMail()+"', nombre_r='"+prov.getNombre_r()+"', " +
                    "     telefono_r='"+prov.getTelefono_r()+"', celular_r='"+prov.getCelular_r()+"', obs='"+prov.getObs()+"'" +
                    " WHERE codigo='"+prov.getCodigo()+"';"; 
        
        try {
            if(existeRegistro("proveedor","codigo",prov.getCodigo()))
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

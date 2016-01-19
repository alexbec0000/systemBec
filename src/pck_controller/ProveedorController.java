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
             String sql="SELECT CODIGO_PRV, RUC_PRV, NOMBRE_APELLIDO_PRV, DIRECCION_PRV, TELEFONO_PRV, WEB_PRV, EMAIL_PRV, "
                     + " NOMBRE_R_PRV, TELEFONO_R_PRV, CELULAR_R_PRV, OBSERVACION_PRV" +
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
                prov.setWeb(rs.getString(6)); 
                prov.setMail(rs.getString(7)); 
                prov.setNombre_r(rs.getString(8));
                prov.setTelefono_r(rs.getString(9));
                prov.setCelular_r(rs.getString(10));
                prov.setObs(rs.getString(11));
               
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
        String sql = "INSERT INTO proveedor(CODIGO_PRV, RUC_PRV, NOMBRE_APELLIDO_PRV, DIRECCION_PRV, TELEFONO_PRV, WEB_PRV, EMAIL_PRV, "
                + " NOMBRE_R_PRV, TELEFONO_R_PRV, CELULAR_R_PRV, OBSERVACION_PRV) " +
	"VALUES('"+prov.getCodigo()+"','"+ 
            prov.getRuc()+"','"+
                prov.getNombre()+"','"+
                prov.getDireccion()+"','"+
                prov.getTelefono()+"','"+
                prov.getWeb()+"','"+
                prov.getMail()+"','"+
                prov.getNombre_r()+"','"+
                prov.getTelefono_r()+"','"+
                prov.getCelular_r()+"','"+
            prov.getObs()+"');";
        
        String sql2 ="UPDATE proveedor " +
                    " SET RUC_PRV='"+prov.getRuc()+"', NOMBRE_APELLIDO_PRV='"+prov.getNombre()+"', DIRECCION_PRV='"+prov.getDireccion()+"', "+
                    " TELEFONO_PRV='"+prov.getTelefono()+"', WEB_PRV='"+prov.getWeb()+"', " +
                    " EMAIL_PRV='"+prov.getMail()+"', NOMBRE_R_PRV='"+prov.getNombre_r()+"', " +
                    " TELEFONO_R_PRV='"+prov.getTelefono_r()+"', CELULAR_R_PRV='"+prov.getCelular_r()+"', OBSERVACION_PRV='"+prov.getObs()+"'" +
                    " WHERE CODIGO_PRV='"+prov.getCodigo()+"';"; 
        
        try {
            if(existeRegistro("proveedor","CODIGO_PRV",prov.getCodigo()))
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

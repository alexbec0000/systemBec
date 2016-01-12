/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_controller;

import java.sql.SQLException;
import java.util.ArrayList;
import pck_accesoDatos.cls_conexion;
import pck_entidades.cls_vendedor;

/**
 *
 * @author Alex
 */
public class VendedorController extends AbstractController{
    
    ArrayList <cls_vendedor> listVend;
    cls_vendedor vend;
    
    public ArrayList <cls_vendedor> cargarVendedores()
    {
         try{
             listVend=new ArrayList();
             String sql="SELECT codigo, ci, nombres, direccion, telefono, celular, comision," +
                        "  mail, departamento, provincia, distrito, obs, foto" +
                        "  FROM vendedor;";
                 rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
                vend = new cls_vendedor();
                vend.setCodigo(rs.getString(1));
                vend.setCI(rs.getString(2));
                vend.setNombres(rs.getString(3));
                vend.setDireccion(rs.getString(4)); 
                vend.setTelefono(rs.getString(5)); 
                vend.setCelular(rs.getString(6)); 
                vend.setComision(rs.getFloat(7)); 
                vend.setMail(rs.getString(8)); 
                vend.setObs(rs.getString(9));
                //vend.setFoto(rs.getString(13));
               
                listVend.add(vend);
            }
           
            rs.close();
           
        }catch(SQLException ex){
                     
        }
        return listVend;
    }
    
    
    public int grabarRegistro(cls_vendedor vend) {
        
        int resultado = 0;
        String sql = "INSERT INTO vendedor(codigo, ci, nombres, direccion, telefono, celular, comision, " +
                "mail, obs, foto) " +
                "VALUES('"+vend.getCodigo()+"','"+ 
                vend.getCI()+"','"+
                vend.getNombres()+"','"+
                vend.getDireccion()+"','"+
                vend.getTelefono()+"','"+
                vend.getCelular()+"','"+
                vend.getComision()+"','"+
                vend.getMail()+"','"+
                vend.getObs()+"','"+
                vend.getFoto()+"');";
        
        String sql2 ="UPDATE vendedor " +
                    "   SET ci='"+vend.getCI()+"', nombres='"+vend.getNombres()+"', direccion='"+vend.getDireccion()+"', "+
                    "     telefono='"+vend.getTelefono()+"', celular='"+vend.getCelular()+"', " +
                    "     mail='"+vend.getMail()+"', "+
                    "     obs='"+vend.getObs()+"', " +
                    "     foto='"+vend.getFoto()+"'"+
                    " WHERE codigo='"+vend.getCodigo()+"';"; 
        
        try {
            if(existeRegistro("vendedor","codigo",vend.getCodigo()))
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

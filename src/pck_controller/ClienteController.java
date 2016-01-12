/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_controller;

import java.sql.SQLException;
import java.util.ArrayList;
import pck_accesoDatos.cls_conexion;
import pck_entidades.cls_cliente;

/**
 *
 * @author Alex
 */
public class ClienteController extends AbstractController{
    
    ArrayList <cls_cliente> listCli;
    cls_cliente cli;
    
    public ArrayList <cls_cliente> cargarClientes()
    {
         try{
             listCli=new ArrayList();
             String sql="SELECT codigo, ruc, dni, nombre, direccion, razon, telefono, celular, " +
                        "       fax, forma_p, credito, mail," +
                        "       vendedor, obs, foto" +
                        "  FROM clientes;";
                 rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
                cli = new cls_cliente();
                cli.setCodigo(rs.getString(1));
                cli.setRUC(rs.getString(2));
                cli.setDNI(rs.getString(3));
                cli.setNombre(rs.getString(4));
                cli.setDireccion(rs.getString(5));
                cli.setRazon(rs.getString(6));
                cli.setTelefono(rs.getString(7));
                cli.setCelular(rs.getString(8));
                cli.setFax(rs.getString(9));
                cli.setForma_p(rs.getString(10));
                cli.setCredito(rs.getString(11));
                cli.setMail(rs.getString(12));
                cli.setVendedor(rs.getString(13));
                cli.setObs(rs.getString(14));
                //cli.setFoto(rs.getByte(0));
//                cl.setSueldo(rs.getDouble(14));
                
                listCli.add(cli);
            }
           
            rs.close();
           
        }catch(SQLException ex){
                     
        }
        return listCli;
    }
    
    public int grabarRegistro(cls_cliente cli) {
        
        int resultado = 0;
        String sql = "INSERT INTO clientes(codigo, ruc, dni, nombre, direccion, razon, telefono, celular, " +
                                        " fax, forma_p, credito, mail, " +
                                        " vendedor, obs, foto) " +
	"VALUES('"+cli.getCodigo()+"','"+ 
            cli.getRUC()+"','"+
                cli.getDNI()+"','"+
                cli.getNombre()+"','"+
                cli.getDireccion()+"','"+
                cli.getRazon()+"','"+
                cli.getTelefono()+"','"+
                cli.getCelular()+"','"+
                cli.getFax()+"','"+
                cli.getForma_p()+"','"+
                cli.getCredito()+"','"+
                cli.getMail()+"','"+
                cli.getVendedor()+"','"+
                cli.getObs()+"','"+
            cli.getFoto()+"');";
        
        String sql2 ="UPDATE clientes " +
                    "   SET ruc='"+cli.getRUC()+"', nombre='"+cli.getNombre()+"', direccion='"+cli.getDireccion()+"', "+
                    "     telefono='"+cli.getTelefono()+"', fax='"+cli.getFax()+"', credito='"+cli.getCredito()+"', " +
                    "     mail='"+cli.getMail()+"', "+
                    "     forma_p='"+cli.getForma_p()+"', razon='"+cli.getRazon()+"', " +
                    "     dni='"+cli.getDNI()+"', foto='"+cli.getFoto()+"', vendedor='" +cli.getVendedor()+"',"+
                    "     celular='"+cli.getCelular()+"', obs='"+cli.getObs()+"'" +
                    " WHERE codigo='"+cli.getCodigo()+"';"; 
        
        try {
            if(existeRegistro("clientes","codigo",cli.getCodigo()))
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

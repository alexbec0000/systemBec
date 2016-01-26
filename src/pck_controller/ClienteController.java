/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_controller;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import pck_accesoDatos.cls_conexion;
import pck_entidades.cls_cliente;
import pck_utilidades.CustomImageIcon;
import pck_utilidades.Generales;

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
             String sql="SELECT ID_CLI, CED_RUC_CLI, NOMBRE_APELLIDO_CLI, "
                      + " DIRECCION_CLI, RAZON_SOCIAL_CLI, "
                      + " TELEFONO_CLI, CELULAR_CLI, " +
                        " FORMA_PAGO_CLI, SALDO_CLI, EMAIL_CLI," +
                        " OBSERVACION_CLI, FECHA_INGRESO_CLI" +
                        " FROM clientes;";
                 rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
                cli = new cls_cliente();
                cli.setCodigo(rs.getString(1));
                cli.setRUC(rs.getString(2));
                cli.setNombre(rs.getString(3));
                cli.setDireccion(rs.getString(4));
                cli.setRazon(rs.getString(5));
                cli.setTelefono(rs.getString(6));
                cli.setCelular(rs.getString(7));
                cli.setForma_p(rs.getString(8));
                cli.setCredito(rs.getString(9));
                cli.setMail(rs.getString(10));
                cli.setObs(rs.getString(11));
                //cli.setFoto(rs.getBinaryStream(12));
                cli.setFechaIngreso(rs.getString(12));
                //cli.setFoto(rs.getByte(0));
//                cl.setSueldo(rs.getDouble(14));
                
                listCli.add(cli);
            }
           
            rs.close();
           
        }catch(SQLException ex){
                     
        }
        return listCli;
    }
    
    public InputStream getFoto(String id)
    {
        InputStream is = null;
        String sql ="SELECT FOTO_CLI from clientes where ID_CLI ='"+id+"';";
        try{
            rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
                is=rs.getBinaryStream(1);
            }
  
            rs.close();
           
        }catch(SQLException ex){
                     
        }
        
        return is;
    }
    
    public int grabarRegistro(cls_cliente cli) {
        
        int resultado = 0;
        String sql = "INSERT INTO clientes(ID_CLI, CED_RUC_CLI, NOMBRE_APELLIDO_CLI, DIRECCION_CLI, RAZON_SOCIAL_CLI, TELEFONO_CLI, CELULAR_CLI, " +
                                        " FECHA_INGRESO_CLI, FORMA_PAGO_CLI, SALDO_CLI, EMAIL_CLI, " +
                                        " OBSERVACION_CLI, FOTO_CLI) " +
	"VALUES('"+cli.getCodigo()+"','"+ 
            cli.getRUC()+"','"+
                cli.getNombre()+"','"+
                cli.getDireccion()+"','"+
                cli.getRazon()+"','"+
                cli.getTelefono()+"','"+
                cli.getCelular()+"','"+
                cli.getFechaIngreso()+"','"+
                cli.getForma_p()+"','"+
                cli.getCredito()+"','"+
                cli.getMail()+"','"+
                cli.getObs()+"',?);";
        
        String sql2 ="UPDATE clientes " +
                    "   SET CED_RUC_CLI='"+cli.getRUC()+"', NOMBRE_APELLIDO_CLI='"+cli.getNombre()+"', DIRECCION_CLI='"+cli.getDireccion()+"', "+
                    "     TELEFONO_CLI='"+cli.getTelefono()+"', FECHA_INGRESO_CLI='"+cli.getFechaIngreso()+"', SALDO_CLI='"+cli.getCredito()+"', " +
                    "     EMAIL_CLI='"+cli.getMail()+"', "+
                    "     FORMA_PAGO_CLI='"+cli.getForma_p()+"', RAZON_SOCIAL_CLI='"+cli.getRazon()+"', " +
                    "     FOTO_CLI=?,"+
                    "     CELULAR_CLI='"+cli.getCelular()+"', OBSERVACION_CLI='"+cli.getObs()+"'" +
                    " WHERE ID_CLI='"+cli.getCodigo()+"';"; 
        
        try {
            if(existeRegistro("clientes","ID_CLI",cli.getCodigo()))
            {
                System.out.println(sql2);
                PreparedStatement ps=cls_conexion.getPreparedStatement(sql2);
                ps.setBinaryStream(1,cli.getFoto());
                ps.execute();
            }
            else{
                System.out.println(sql);
                //resultado = cls_conexion.getStatement().executeUpdate(sql);
                PreparedStatement ps=cls_conexion.getPreparedStatement(sql);
                ps.setBinaryStream(1,cli.getFoto());
                ps.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return resultado;
        
    }
    
}

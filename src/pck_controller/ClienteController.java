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
                        " OBSERVACION_CLI, FECHA_INGRESO_CLI, FOTO_CLI" +
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
                cli.setFechaIngreso(rs.getString(12));
                try{
                    cli.setImagen(Generales.getFoto(rs.getBinaryStream(13)));
                }
                catch(Exception ex){
                    //ex.printStackTrace();
                    cli.setImagen(new CustomImageIcon(getClass().getResource("/recursos/icono_cliente.jpg")));
                }

                listCli.add(cli);
            }
           
            rs.close();
           
        }catch(SQLException ex){
                     
        }
        return listCli;
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
                if(cli.getFoto() == null)
                    cli.setFoto(recuperaImagen("clientes","FOTO_CLI","ID_CLI",cli.getCodigo()));
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
    
    public static ResultSet listarClientes(String cliente)
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select id_cli,concat(RAZON_SOCIAL_CLI,' ',NOMBRE_APELLIDO_CLI) as elcliente from clientes where concat(RAZON_SOCIAL_CLI,' ',NOMBRE_APELLIDO_CLI) like '" + cliente + "%' order by elcliente");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static ResultSet obtenerClientes()
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select * from clientes order by id_cli");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static ResultSet obtenerTotalClientes()
    {
        try 
        {          
            Statement sentenciacli=cls_conexion.getStatement();
            sentenciacli.executeQuery("select count(id_cli) as cuantos from clientes");
            return sentenciacli.getResultSet();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static void eliminarCliente(String ID_CLI)
    {
        try {
            String sql ="DELETE FROM CLIENTES WHERE ID_CLI='" + ID_CLI + "'";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void ingresarCliente(String ID_CLI, String nombreComercial, String razonSocial, String direccion
                ,String cedula, String telefono, String celular, Object sexo, String fecha, String email, String saldo)
    {
        try {
            String sql ="INSERT INTO CLIENTES (ID_CLI,NOMBRE_APELLIDO_CLI,RAZON_SOCIAL_CLI,DIRECCION_CLI,CED_RUC_CLI,TELEFONO_CLI,CELULAR_CLI,SEXO_CLI,FECHA_INGRESO_CLI,EMAIL_CLI,SALDO_CLI) VALUES('" + ID_CLI
                        + "','" + nombreComercial + "','" + razonSocial + "','" + direccion + "','" + cedula + "','" + telefono
                        + "','" + celular + "','" + sexo + "','" + fecha + "','" + email + "'," + saldo + ")";
            cls_conexion.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void actualizarCliente(String ID_CLI, String nombreComercial, String razonSocial, String direccion
                ,String cedula, String telefono, String celular, Object sexo, String fecha, String email)
    {
        try {
            String sql ="UPDATE CLIENTES SET NOMBRE_APELLIDO_CLI='" + nombreComercial + "',RAZON_SOCIAL_CLI='" + razonSocial + "',DIRECCION_CLI='" + direccion
                        + "',CED_RUC_CLI='" + cedula + "',TELEFONO_CLI='" + telefono + "',CELULAR_CLI='" + celular
                        + "',SEXO_CLI='" + sexo + "',FECHA_INGRESO_CLI='" + fecha + "',EMAIL_CLI='" + email + "' WHERE ID_CLI='" + ID_CLI + "'";
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
    
}

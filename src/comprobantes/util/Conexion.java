/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comprobantes.util;

import java.sql.*;


/**
 *
 * @author abecerra
 */
public class Conexion {
    
    private static Connection con;
    private static String mensaje;
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static String host = AppConfig.getConfig().getHost();
    private static String dataBase = AppConfig.getConfig().getDataBase();
    private static String user = AppConfig.getConfig().getUser();
    private static String pass = AppConfig.getConfig().getPass();

    public static Connection getCon() {
        return con;
    }
   
    public static void setCon(Connection aCon) {
        con = aCon;
    }

    public static void conectaSRI() throws Exception {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            mensaje = "Error al cargar el Driver" + e.getMessage();
            throw new Exception(mensaje);
        }
        try {
            setCon(DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+dataBase, user, pass));
            mensaje = "Coneccion O.K.";
        } catch (SQLException e) {
            e.printStackTrace();
            mensaje = "Error no se logro la coneccion" + e.getMessage();
            throw new Exception(mensaje);
        }
    }
    
    
    /**
     * Cierra la conexion a la base de datos
     * @throws java.lang.Exception Si existi� alg�n error al cerrar la conexi�n
     */

    public static void cerrar() throws Exception {
        try {
            getCon().close();
        } catch (SQLException e) {
            mensaje = "Error al cerrar la coneccion11" + e.getMessage();
            throw new Exception(mensaje);
        }
    }
}

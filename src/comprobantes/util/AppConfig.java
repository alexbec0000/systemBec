/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprobantes.util;

import java.io.Serializable;

/**
 *
 * @author Alex
 */

public class AppConfig implements Serializable {
    
    private static AppConfig config = new AppConfig();
    
    private String host = "www.db4free.net";
    private String dataBase = "ab_facturacion";
    private String user = "ab_sys";
    private String pass = "ab_admin";
    private String idUsuario = "";
    private String usuario = "";

    public AppConfig() {
    }

    public static AppConfig getConfig() {
        return config;
    }

    public static void setConfig(AppConfig config) {
        AppConfig.config = config;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    
    
}
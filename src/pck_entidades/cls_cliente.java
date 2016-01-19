package pck_entidades;
//Importando las librerias
import java.io.Serializable;
import javax.swing.*;

public class cls_cliente implements Serializable
{
    //Declaramos la lista de atributos
    private String codigo;
    private String RUC;
    private String nombre;
    private String direccion;
    private String razon;
    private String telefono;
    private String celular;
    private String fechaIngreso;
    private String forma_p;
    private String credito;
    private String mail;
    private String obs;
    private Icon foto;
    private boolean editar;

    //Declaramos el contructor vacio
    public cls_cliente () {}

    //Declaramos el contructor con parametros
    public cls_cliente (String codigo, String RUC, String nombre, String direccion, String razon, String telefono, 
                    String celular, String fechaIngreso, String forma_p, String credito, String mail, 
                    String obs, Icon foto,boolean editar)
    {
        this.codigo = codigo;
        this.RUC = RUC;
        this.nombre = nombre;
        this.direccion = direccion;
        this.razon = razon;
        this.telefono = telefono;
        this.celular = celular;
        this.fechaIngreso = fechaIngreso;
        this.forma_p = forma_p;
        this.credito = credito;
        this.mail = mail;
        this.obs = obs;
        this.foto = foto;
        this.editar = editar;
    }

    //iniziamos el seteo

    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the RUC
     */
    public String getRUC() {
        return RUC;
    }

    /**
     * @param RUC the RUC to set
     */
    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the razon
     */
    public String getRazon() {
        return razon;
    }

    /**
     * @param razon the razon to set
     */
    public void setRazon(String razon) {
        this.razon = razon;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }


    /**
     * @return the forma_p
     */
    public String getForma_p() {
        return forma_p;
    }

    /**
     * @param forma_p the forma_p to set
     */
    public void setForma_p(String forma_p) {
        this.forma_p = forma_p;
    }

    /**
     * @return the credito
     */
    public String getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(String credito) {
        this.credito = credito;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @param obs the obs to set
     */
    public void setObs(String obs) {
        this.obs = obs;
    }

    /**
     * @return the foto
     */
    public Icon getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(Icon foto) {
        this.foto = foto;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

}// Fin de la clace


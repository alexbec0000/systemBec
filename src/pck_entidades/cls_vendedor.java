package pck_entidades;
//Importando las librerias
import java.io.Serializable;
import javax.swing.*;

public class cls_vendedor implements Serializable
{
//Declaramos la lista de atributos
private String codigo;
private String CI;
private String nombres;
private String direccion;
private String telefono;
private String celular;
private float comision;
private String mail;
private String obs;
private Icon foto;

//Declarando el constructor vacío
public cls_vendedor () {}

//Declarando el constructor con parámetros
public cls_vendedor (String codigo, String CI, String nombres, String direccion, String telefono, String celular, float comision, String mail, 
                  String obs, Icon foto)
{
this.codigo = codigo;
this.CI = CI;
this.nombres = nombres;
this.direccion = direccion;
this.telefono = telefono;
this.celular = celular;
this.comision = comision;
this.mail = mail;
this.obs = obs;
this.foto = foto;
}

//Iniziando el seteo
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
     * @return the CI
     */
    public String getCI() {
        return CI;
    }

    /**
     * @param CI the CI to set
     */
    public void setCI(String CI) {
        this.CI = CI;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
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

    /**
     * @return the comision
     */
    public float getComision() {
        return comision;
    }

    /**
     * @param comision the comision to set
     */
    public void setComision(float comision) {
        this.comision = comision;
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

}//Fin de la clace Vendedor

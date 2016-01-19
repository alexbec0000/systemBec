package pck_entidades;
//Importando las librerias
import java.io.Serializable;

public class cls_proveedor implements Serializable
{
//Declaramos la lista de atributos
private String codigo;
private String ruc;
private String nombre;
private String direccion;
private String telefono;
private String web;
private String mail;
private String nombre_r;
private String telefono_r;
private String celular_r;
private String obs;
private boolean editar;

//Declaramos el constructor vacio
public cls_proveedor(){}

//Declaramos el segundo constructor con los parametros
public cls_proveedor (String codigo, String ruc, String nombre, String direccion, String telefono, 
                  String web, String mail, String nombre_r,
                  String telefono_r, String celular_r, String obs, boolean editar )
{
    this.codigo = codigo;
    this.ruc = ruc;
    this.nombre = nombre;
    this.direccion = direccion;
    this.telefono = telefono;
    this.web = web;
    this.mail = mail;
    this.nombre_r = nombre_r;
    this.telefono_r = telefono_r;
    this.celular_r = celular_r;
    this.obs = obs;
    this.editar = editar;
}


//Inisializando el "seteo"
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
     * @return the ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * @param ruc the ruc to set
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
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
     * @return the web
     */
    public String getWeb() {
        return web;
    }

    /**
     * @param web the web to set
     */
    public void setWeb(String web) {
        this.web = web;
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
     * @return the nombre_r
     */
    public String getNombre_r() {
        return nombre_r;
    }

    /**
     * @param nombre_r the nombre_r to set
     */
    public void setNombre_r(String nombre_r) {
        this.nombre_r = nombre_r;
    }

    /**
     * @return the telefono_r
     */
    public String getTelefono_r() {
        return telefono_r;
    }

    /**
     * @param telefono_r the telefono_r to set
     */
    public void setTelefono_r(String telefono_r) {
        this.telefono_r = telefono_r;
    }

    /**
     * @return the celular_r
     */
    public String getCelular_r() {
        return celular_r;
    }

    /**
     * @param celular_r the celular_r to set
     */
    public void setCelular_r(String celular_r) {
        this.celular_r = celular_r;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
    
    public String getObs() {return obs;}
    public void setObs(String obs) {this.obs = obs;}

}//Fin de la clace



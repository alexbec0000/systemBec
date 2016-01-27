package pck_entidades;
//Importando las librerias
import java.io.FileInputStream;
import java.io.Serializable;
import javax.swing.*;

public class cls_vendedor implements Serializable
{
    //Declaramos la lista de atributos
    private String codigo;
    private String CI;
    private String nombres;
    private String usuario;
    private String clave;
    private String fechaIngreso;
    private String fechaNacimiento;
    private String direccion;
    private String telefono;
    private String celular;
    private float ventas;
    private String mail;
    private String obs;
    private FileInputStream foto;
    private Icon imagen;
    private boolean estado;
    private boolean editar;

    //Declarando el constructor vacío
    public cls_vendedor () {}

    //Declarando el constructor con parámetros
    
    public cls_vendedor (boolean editar) 
    {
        this.editar = editar;
    }
    
    public cls_vendedor (String codigo, String CI, String nombres, String direccion, String telefono, String celular, float comision, String mail, 
                      String obs, FileInputStream foto, boolean editar)
    {
        this.codigo = codigo;
        this.CI = CI;
        this.nombres = nombres;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.ventas = comision;
        this.mail = mail;
        this.obs = obs;
        this.foto = foto;
        this.editar = editar;
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
    public FileInputStream getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(FileInputStream foto) {
        this.foto = foto;
    }

    public Icon getImagen() {
        return imagen;
    }

    public void setImagen(Icon imagen) {
        this.imagen = imagen;
    }
    
    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public float getVentas() {
        return ventas;
    }

    public void setVentas(float ventas) {
        this.ventas = ventas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
    
}//Fin de la clace Vendedor

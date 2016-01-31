package pck_entidades;
//Importando las librerias
import java.io.FileInputStream;
import java.io.Serializable;
import javax.swing.*;

public class cls_producto implements Serializable
{
    //Declaramos la lista de atributos
    private String id_art;
    private String codigo_barras;
    private String fecha;
    private String nombre;
    private String marca;
    private String modelo;
    private String linea;
    private int stock;
    private int stk_minimo;
    private int stk_maximo;
    private float p_venta;
    private float p_compra;
    private String obs;
    private String proveedor;
    private String garantia;
    private String fechaVencimiento;
    private FileInputStream foto;
    private Icon imagen;
    private boolean editar;
    
    //private String codigo_fabricante;

    //Declarando el constructor vacío
    public cls_producto (){}
    
    public cls_producto (boolean editar) 
    {
        this.editar = editar;
    }
    //Declarando el constructor con parámetros
    public cls_producto (String id_art, String codigo_barras, String nombre,String linea, String marca, String modelo, String obs,
                    String proveedor, String garantia, String fechaVencimiento, int stock, int stk_minimo, int stk_maximo,
                   float pvp, float pcompra, FileInputStream foto, boolean editar)
    {
        this.codigo_barras = codigo_barras;
        this.id_art = id_art;
        this.nombre = nombre;
        this.linea = linea;
        this.marca = marca;
        this.modelo = modelo;
        this.obs = obs;
        this.proveedor = proveedor;
        this.garantia = garantia;
        this.fechaVencimiento=fechaVencimiento;
        this.stock=stock;
        this.stk_minimo = stk_minimo;
        this.stk_maximo = stk_maximo;
        this.p_venta=pvp;
        this.p_compra=pcompra;
        this.foto = foto;
        this.editar = editar;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    /**
     * @param codigo_barras the codigo_barras to set
     */
    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    /**
     * @return the id_art
     */
    public String getCodigo_art() {
        return id_art;
    }

    /**
     * @param id_art the id_art to set
     */
    public void setCodigo_art(String id_art) {
        this.id_art = id_art;
    }

    /**
     * @return the abreviatura
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param abreviatura the abreviatura to set
     */
    public void setNombre(String abreviatura) {
        this.nombre = abreviatura;
    }

    public String getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(String linea) {
        this.linea = linea;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
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
     * @return the proveedor
     */
    public String getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the garantia
     */
    public String getGarantia() {
        return garantia;
    }

    /**
     * @param garantia the garantia to set
     */
    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the stk_minimo
     */
    public int getStk_minimo() {
        return stk_minimo;
    }

    /**
     * @param stk_minimo the stk_minimo to set
     */
    public void setStk_minimo(int stk_minimo) {
        this.stk_minimo = stk_minimo;
    }

    /**
     * @return the stk_maximo
     */
    public int getStk_maximo() {
        return stk_maximo;
    }

    /**
     * @param stk_maximo the stk_maximo to set
     */
    public void setStk_maximo(int stk_maximo) {
        this.stk_maximo = stk_maximo;
    }

 
    public float getP_compra() {
        return p_compra;
    }

    /**
     * @param p_compra the p_compra to set
     */
    public void setP_compra(float p_compra) {
        this.p_compra = p_compra;
    }

    /**
     * @return the p_venta
     */
    public float getP_venta() {
        return p_venta;
    }

    /**
     * @param p_venta the p_venta to set
     */
    public void setP_venta(float p_venta) {
        this.p_venta = p_venta;
    }

    public Icon getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Icon imagen) {
        this.imagen = imagen;
    }

    public String getId_art() {
        return id_art;
    }

    public void setId_art(String id_art) {
        this.id_art = id_art;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public FileInputStream getFoto() {
        return foto;
    }

    public void setFoto(FileInputStream foto) {
        this.foto = foto;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    

}//Fin de la clace Producto

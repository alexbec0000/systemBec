package pck_entidades;
//Importando las librerias
import java.io.Serializable;
import javax.swing.*;

public class cls_producto implements Serializable
{
    //Declaramos la lista de atributos
    private String codigo_barras;
    private String codigo_producto;
    private String codigo_fabricante;
    private String nombre;
    //private String grupo;
    private String linea;
    private String marca;
    private String modelo;
    private String obs;
    private String proveedor;
    private String vendedor;
    private String garantia;
    private String fecha;
    private int stk_minimo;
    private int stk_maximo;

    private float p_compra;
    private String moneda;
    private float p_venta;
    private String utilidad1;
    private float valor1;
    private String utilidad2;
    private float valor2;
    private float p_pmayor;
    private Icon imagen;

    //Declarando el constructor vacío
    public cls_producto (){}

    //Declarando el constructor con parámetros
    public cls_producto (String codigo_barras,String codigo_producto, String codigo_fabricante, String nombre,String linea, String marca, String modelo, String obs,
                    String proveedor, String vendedor, String garantia, String fecha, int stk_minimo, int stk_maximo,
                   float p_compra, String moneda, float p_venta, String utilidad1,  float valor1, String utilidad2, float valor2,float p_pmayor, Icon imagen)
    {
        this.codigo_barras = codigo_barras;
        this.codigo_producto = codigo_producto;
        this.codigo_fabricante = codigo_fabricante;
        this.nombre = nombre;
        //this.grupo = grupo;
        this.linea = linea;
        this.marca = marca;
        this.modelo = modelo;
        this.obs = obs;
        this.proveedor = proveedor;
        this.vendedor = vendedor;
        this.garantia = garantia;
        this.fecha = fecha;
        this.stk_minimo = stk_minimo;
        this.stk_maximo = stk_maximo;
        this.p_compra = p_compra;
        this.moneda = moneda;
        this.p_venta = p_venta;
        this.utilidad1 = utilidad1;

        this.valor1 = valor1;
        this.utilidad2 = utilidad2;

        this.valor2 = valor2;
        this.p_pmayor = p_pmayor;
        this.imagen = imagen;
    }

    //Inisiando el seteo

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
     * @return the codigo_producto
     */
    public String getCodigo_producto() {
        return codigo_producto;
    }

    /**
     * @param codigo_producto the codigo_producto to set
     */
    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    /**
     * @return the codigo_fabricante
     */
    public String getCodigo_fabricante() {
        return codigo_fabricante;
    }

    /**
     * @param codigo_fabricante the codigo_fabricante to set
     */
    public void setCodigo_fabricante(String codigo_fabricante) {
        this.codigo_fabricante = codigo_fabricante;
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
     * @return the vendedor
     */
    public String getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
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
     * @return the moneda
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * @param moneda the moneda to set
     */
    public void setMoneda(String moneda) {
        this.moneda = moneda;
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

    /**
     * @return the utilidad1
     */
    public String getUtilidad1() {
        return utilidad1;
    }

    /**
     * @param utilidad1 the utilidad1 to set
     */
    public void setUtilidad1(String utilidad1) {
        this.utilidad1 = utilidad1;
    }


    public float getValor1() {
        return valor1;
    }

    /**
     * @param valor1 the valor1 to set
     */
    public void setValor1(float valor1) {
        this.valor1 = valor1;
    }

    /**
     * @return the utilidad2
     */
    public String getUtilidad2() {
        return utilidad2;
    }

    /**
     * @param utilidad2 the utilidad2 to set
     */
    public void setUtilidad2(String utilidad2) {
        this.utilidad2 = utilidad2;
    }


    public float getValor2() {
        return valor2;
    }

    /**
     * @param valor2 the valor2 to set
     */
    public void setValor2(float valor2) {
        this.valor2 = valor2;
    }

public float getP_pmayor(){return p_pmayor;}
public void setP_pmayor (float p_pmayor){this.p_pmayor = p_pmayor;}





    public Icon getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Icon imagen) {
        this.imagen = imagen;
    }


}//Fin de la clace Producto

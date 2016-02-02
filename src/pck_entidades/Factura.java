/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pck_entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abecerra
 */
public class Factura {
    
    String sucursal;
    String direccionSucursal;

    String numFac;
    String fecha;
    String cuidad;
    String cliente;
    String direccion;
    String ruc;
    String rucEmpresa;
    String telefono;
    String email;
    List<FacturaDetalle> ls_FacturaDetalle=new ArrayList();
    String subTotalFac;
    String iva;
    String valorFac;
    String responsable;
    String cargo;
    
    public Factura() {
        
        rucEmpresa="";
        numFac="";
        fecha="";
        cuidad="";
        cliente="";
        direccion="";
        ruc="";
        telefono="";
        email="";
        subTotalFac="";
        iva="";
        valorFac="";
        responsable="";
        cargo="";
        sucursal="";
        direccionSucursal="";
    }

    public String getRucEmpresa() {
        return rucEmpresa;
    }

    public void setRucEmpresa(String rucEmpresa) {
        this.rucEmpresa = rucEmpresa;
    }

    public String getNumFac() {
        return numFac;
    }

    public void setNumFac(String numFac) {
        this.numFac = numFac;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuidad() {
        return cuidad;
    }

    public void setCuidad(String cuidad) {
        this.cuidad = cuidad;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubTotalFac() {
        return subTotalFac;
    }

    public void setSubTotalFac(String subTotalFac) {
        this.subTotalFac = subTotalFac;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getValorFac() {
        return valorFac;
    }

    public void setValorFac(String valorFac) {
        this.valorFac = valorFac;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    public List<FacturaDetalle> getLs_FacturaDetalle() {
        return ls_FacturaDetalle;
    }

    public void setLs_FacturaDetalle(List<FacturaDetalle> ls_FacturaDetalle) {
        this.ls_FacturaDetalle = ls_FacturaDetalle;
    }
    
    
    
}

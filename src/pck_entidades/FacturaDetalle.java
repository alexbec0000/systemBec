/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pck_entidades;

/**
 *
 * @author abecerra
 */
public class FacturaDetalle {
    
    String cantidad;
    String unidadMedida;
    String descripcion;
    String valorUnit;
    String valorTotalDet;

    public FacturaDetalle() {
    }
    
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(String valorUnit) {
        this.valorUnit = valorUnit;
    }

    public String getValorTotalDet() {
        return valorTotalDet;
    }

    public void setValorTotalDet(String valorTotalDet) {
        this.valorTotalDet = valorTotalDet;
    }
    
    
}

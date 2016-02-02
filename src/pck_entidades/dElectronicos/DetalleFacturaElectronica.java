/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pck_entidades.dElectronicos;

/**
 *
 * @author abecerra
 */
public class DetalleFacturaElectronica {
    
    private String codPrincipal;
    private String codAux;
    private String cantidad;
    private String descipcion;
    private String valorUnit;
    private String obs;

    public DetalleFacturaElectronica() {
    }

    public String getCodPrincipal() {
        return codPrincipal;
    }

    public void setCodPrincipal(String codPrincipal) {
        this.codPrincipal = codPrincipal;
    }

    public String getCodAux() {
        return codAux;
    }

    public void setCodAux(String codAux) {
        this.codAux = codAux;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public String getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(String valorUnit) {
        this.valorUnit = valorUnit;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    
    
    
}

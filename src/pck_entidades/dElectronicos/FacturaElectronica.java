/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pck_entidades.dElectronicos;

import comprobantes.entidades.Clientes;
import comprobantes.entidades.Emisor;
import java.util.List;

/**
 *
 * @author abecerra
 */
public class FacturaElectronica {
    
    private String pathFactura;
    private String pathCore;
    private String secuencialFactura;
    private String subTotal;
    private String valorTotal;
    private String fechaEmision;
    private boolean autorizar;
    private Emisor emisor;
    private Clientes cliente;
    
    private List<DetalleFacturaElectronica> detalleFactura;

    public FacturaElectronica() {
    }

    public String getPathFactura() {
        return pathFactura;
    }

    public void setPathFactura(String pathFactura) {
        this.pathFactura = pathFactura;
    }

    public String getPathCore() {
        return pathCore;
    }

    public void setPathCore(String pathCore) {
        this.pathCore = pathCore;
    }

    public String getSecuencialFactura() {
        return secuencialFactura;
    }

    public void setSecuencialFactura(String secuencialFactura) {
        this.secuencialFactura = secuencialFactura;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public boolean isAutorizar() {
        return autorizar;
    }

    public void setAutorizar(boolean autorizar) {
        this.autorizar = autorizar;
    }

    public List<DetalleFacturaElectronica> getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(List<DetalleFacturaElectronica> detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }
    
    
    
}

package comprobantes.modelo;

public class EstadoAutorizacionComprobante
{
  private EstadoAutorizacion estadoAutorizacion;
  private String mensajes;
  
  public EstadoAutorizacionComprobante(EstadoAutorizacion estadoAutorizacion, String mensajes)
  {
    this.estadoAutorizacion = estadoAutorizacion;
    this.mensajes = mensajes;
  }
  
  public EstadoAutorizacionComprobante(EstadoAutorizacion estadoAutorizacion)
  {
    this.estadoAutorizacion = estadoAutorizacion;
  }
  
  public EstadoAutorizacion getEstadoAutorizacion()
  {
    return this.estadoAutorizacion;
  }
  
  public void setEstadoAutorizacion(EstadoAutorizacion estadoAutorizacion)
  {
    this.estadoAutorizacion = estadoAutorizacion;
  }
  
  public String getMensajes()
  {
    return this.mensajes;
  }
  
  public void setMensajes(String mensajes)
  {
    this.mensajes = mensajes;
  }
}

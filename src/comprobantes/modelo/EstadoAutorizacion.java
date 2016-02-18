package comprobantes.modelo;

public enum EstadoAutorizacion
{
  AUT("AUTORIZADO"),  PPR("EN PROCESAMIENTO"),  NAT("NO AUTORIZADO");
  
  private String descripcion;
  
  private EstadoAutorizacion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public String getDescripcion()
  {
    return this.descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
}

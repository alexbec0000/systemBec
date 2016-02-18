package comprobantes.util;
 
public class CampoModelo
{
  private String valor;
  private String etiqueta;
  private Integer longitud;
  
  public CampoModelo(String valor, String etiqueta, Integer longitud)
  {
    this.valor = valor;
    this.etiqueta = etiqueta;
    this.longitud = longitud;
  }
  
  public String getValor()
  {
    return this.valor;
  }
  
  public void setValor(String valor)
  {
    this.valor = valor;
  }
  
  public String getEtiqueta()
  {
    return this.etiqueta;
  }
  
  public void setEtiqueta(String etiqueta)
  {
    this.etiqueta = etiqueta;
  }
  
  public Integer getLongitud()
  {
    return this.longitud;
  }
  
  public void setLongitud(Integer longitud)
  {
    this.longitud = longitud;
  }
}

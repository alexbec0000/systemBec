package comprobantes.util;
 
public enum TipoProductoEnum
{
  TODOS(null),  BIEN("B"),  SERVICIO("S");
  
  private String code;
  
  private TipoProductoEnum(String code)
  {
    this.code = code;
  }
  
  public String getCode()
  {
    return this.code;
  }
}

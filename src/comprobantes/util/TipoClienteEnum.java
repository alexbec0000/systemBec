package comprobantes.util;
 
public enum TipoClienteEnum
{
  C("CLIENTE", "C"),  R("SUJETO RETENIDO", "R"),  D("DESTINATARIO", "D");
  
  private String code;
  private String descripcion;
  
  private TipoClienteEnum(String descripcion, String code)
  {
    this.code = code;
    this.descripcion = descripcion;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public String getDescripcion()
  {
    return this.descripcion;
  }
  
  public String toString()
  {
    return this.descripcion;
  }
}

package comprobantes.util;
 
public enum DirectorioEnum
{
  GENERADOS(1),  FIRMADOS(2),  AUTORIZADOS(3),  NO_AUTORIZADOS(4);
  
  private int code;
  
  private DirectorioEnum(int code)
  {
    this.code = code;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
}

package comprobantes.util;

public enum TipoCompradorEnum
{
  CONSUMIDOR_FINAL("07"),  RUC("04"),  CEDULA("05"),  PASAPORTE("06"),  IDENTIFICACION_EXTERIOR("08"),  PLACA("09");
  
  private String code;
  
  private TipoCompradorEnum(String code)
  {
    this.code = code;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public static String retornaCodigo(String valor)
  {
    String codigo = null;
    if (valor.equals("C") || valor.length()==10) {
      codigo = CEDULA.getCode();
    }
    if (valor.equals("R") || valor.length()==13) {
      codigo = RUC.getCode();
    }
    if (valor.equals("P") || (valor.length()!=10 && valor.length()!=13)) {
      codigo = PASAPORTE.getCode();
    }
    if (valor.equals("I")) {
      codigo = IDENTIFICACION_EXTERIOR.getCode();
    }
    if (valor.equals("L")) {
      codigo = PLACA.getCode();
    }
    if (valor.equals("F") || valor.equals("9999999999999")) {
      codigo = CONSUMIDOR_FINAL.getCode();
    }
    return codigo;
  }
}

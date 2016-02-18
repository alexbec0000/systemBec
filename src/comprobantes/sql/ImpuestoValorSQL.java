package comprobantes.sql;
 
import comprobantes.entidades.ImpuestoValor;
import comprobantes.util.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpuestoValorSQL
{

  private ResultSet rs;

  public ImpuestoValorSQL()
  {
  }
  
  
  public List<ImpuestoValor> obtenerValorImpuestoIVA()
    throws SQLException, ClassNotFoundException
  {
        try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=2and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A')");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            return obtenerImpuestoValor();
        }
        catch(Exception ex){
            return new ArrayList();}
  }
  
  public List<ImpuestoValor> obtenerValorImpuestoICE()
    throws SQLException, ClassNotFoundException
  {
        try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=3and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A' )");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            return obtenerImpuestoValor();
        }
        catch(Exception ex){
            return new ArrayList();}
  }
  
  public List<ImpuestoValor> obtenerValorImpuestoIRBPNR()
    throws SQLException, ClassNotFoundException
  {
        try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=5 and TIPO_IMPUESTO='B'");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            return obtenerImpuestoValor();
        }
        catch(Exception ex){
            return new ArrayList();}
  }
  
  private List<ImpuestoValor> obtenerImpuestoValor()
    throws SQLException
  {
    List<ImpuestoValor> impuestoValorList = new ArrayList();
    while (this.rs.next())
    {
      ImpuestoValor imp = new ImpuestoValor();
      imp.setCodigo(this.rs.getString("CODIGO"));
      imp.setCodigoImpuesto(Integer.valueOf(this.rs.getInt("CODIGO_IMPUESTO")));
      imp.setPorcentaje(Double.valueOf(this.rs.getDouble("PORCENTAJE")));
      imp.setPorcentajeRentencion(Double.valueOf(this.rs.getDouble("PORCENTAJE_RETENCION")));
      imp.setTipoImpuesto(this.rs.getString("TIPO_IMPUESTO"));
      imp.setFechaInicio(this.rs.getDate("FECHA_INICIO"));
      imp.setFechaFin(this.rs.getDate("FECHA_FIN"));
      imp.setDescripcion(this.rs.getString("DESCRIPCION"));
      imp.setCodigo_Adm(Integer.valueOf(this.rs.getInt("CODIGO_ADM")));
      imp.setMarcaPorcentajeLibre(this.rs.getString("MARCA_PORCENTAJE_LIBRE"));
      impuestoValorList.add(imp);
    }
    return impuestoValorList;
  }
  
  private List<ImpuestoValor> obtenerImpuestoValorIVA()
    throws SQLException
  {
    List<ImpuestoValor> impuestoValorList = new ArrayList();
    while (this.rs.next())
    {
      ImpuestoValor imp = new ImpuestoValor();
      imp.setCodigo(this.rs.getString("CODIGO_ADM"));
      imp.setCodigoImpuesto(Integer.valueOf(this.rs.getInt("CODIGO_IMPUESTO")));
      imp.setPorcentaje(Double.valueOf(this.rs.getDouble("PORCENTAJE")));
      imp.setPorcentajeRentencion(Double.valueOf(this.rs.getDouble("PORCENTAJE_RETENCION")));
      imp.setTipoImpuesto(this.rs.getString("TIPO_IMPUESTO"));
      imp.setFechaInicio(this.rs.getDate("FECHA_INICIO"));
      imp.setFechaFin(this.rs.getDate("FECHA_FIN"));
      imp.setDescripcion(this.rs.getString("DESCRIPCION"));
      imp.setMarcaPorcentajeLibre(this.rs.getString("MARCA_PORCENTAJE_LIBRE"));
      impuestoValorList.add(imp);
    }
    return impuestoValorList;
  }
  
  public List<ImpuestoValor> obtenerValorImpuestoRenta()
    throws SQLException, ClassNotFoundException
  {
        try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where  codigo_impuesto= 1 and TIPO_IMPUESTO='R' ");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            return obtenerImpuestoValor();
        }
        catch(Exception ex){
            return new ArrayList();}
  }
  
  public List<ImpuestoValor> obtenerIVARetencion()
    throws SQLException, ClassNotFoundException
  {
        try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where (codigo_impuesto=2 and TIPO_IMPUESTO='R') ");
            sql.append("or (codigo_impuesto= 2 and TIPO_IMPUESTO='A') order by CODIGO_ADM");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            return obtenerImpuestoValorIVA();
        }
        catch(Exception ex){
            return new ArrayList();}
  }
  
  public ImpuestoValor obtenerValorPorCodigo(String codigo)
    throws SQLException, ClassNotFoundException
  {
        try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo = '");
            sql.append(codigo);
            sql.append("'");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            List<ImpuestoValor> impuestos = obtenerImpuestoValor();
            Conexion.cerrar();
            if (!impuestos.isEmpty()) {
              return (ImpuestoValor)impuestos.get(0);
            }
        }
        catch(Exception ex){
            return null;}

      return null;
  }
  
  public ImpuestoValor obtenerValorPorCodigoIVA(String codigo)
    throws SQLException, ClassNotFoundException
  {
        try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_adm = '");
            sql.append(codigo);
            sql.append("'");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            List<ImpuestoValor> impuestos = obtenerImpuestoValor();
            if (!impuestos.isEmpty()) {
                return (ImpuestoValor)impuestos.get(0);
            }
       }
        catch(Exception ex){
            return null;}

      return null;
  }
  
   public ImpuestoValor obtenerIVA() throws SQLException {

       ImpuestoValor imp = new ImpuestoValor();
       imp.setCodigo("2");
       imp.setCodigoImpuesto(2);
       imp.setPorcentaje(Double.valueOf(Double.parseDouble("12")));
       imp.setPorcentajeRentencion(Double.valueOf(Double.parseDouble("70")));
       imp.setTipoImpuesto("A");
       imp.setFechaInicio(new Date());
       imp.setFechaFin(new Date());
       imp.setDescripcion("12%");

     return imp;
   }
}

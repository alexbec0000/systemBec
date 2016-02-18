package comprobantes.sql;
 
import comprobantes.entidades.Impuesto;
import comprobantes.util.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpuestoSQL
{

  private ResultSet rs;

  public ImpuestoSQL()
  {

  }
  
  public List<Impuesto> obtenerDatosEmisor()
    throws SQLException, ClassNotFoundException
  {
        try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            return obetnerImpuestos();
        }
        catch(Exception ex){}
    return new ArrayList();
  }
  
  private List<Impuesto> obetnerImpuestos()
    throws SQLException
  {
    List<Impuesto> impuestos = new ArrayList();
    int i = 0;
    while (this.rs.next())
    {
      if (i == 0)
      {
        Impuesto seleccione = new Impuesto();
        seleccione.setDescripcion("Seleccione");
        impuestos.add(seleccione);
      }
      Impuesto imp = new Impuesto();
      imp.setCodigo(Integer.valueOf(this.rs.getInt("CODIGO")));
      imp.setDescripcion(this.rs.getString("TAIM_DES_IMP"));
      imp.setEstado(this.rs.getString("TAIM_ESTADO"));
      impuestos.add(imp);
      i++;
    }
    return impuestos;
  }
  
  private Impuesto obtenerImpuesto()
    throws SQLException
  {
    Impuesto imp = new Impuesto();
    while (this.rs.next())
    {
      imp.setCodigo(Integer.valueOf(this.rs.getInt("CODIGO")));
      imp.setDescripcion(this.rs.getString("TAIM_DES_IMP"));
      imp.setEstado(this.rs.getString("TAIM_ESTADO"));
    }
    return imp;
  }
  
  public Impuesto obtenerImpuestoPorCodigo(Integer codigo)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select * from impuesto where CODIGO=" + codigo);
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
        }
        catch(Exception ex){}
    return obtenerImpuesto();
  }
}

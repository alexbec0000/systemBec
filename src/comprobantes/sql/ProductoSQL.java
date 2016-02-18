package comprobantes.sql;

import comprobantes.entidades.ImpuestoProducto;
import comprobantes.entidades.ImpuestoValor;
import comprobantes.entidades.Producto;
import comprobantes.modelo.InformacionAdicionalProducto;
import comprobantes.util.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoSQL
{

  private ResultSet rs;

  public ProductoSQL() {}
  
  public void crearProducto(Producto producto)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            Integer va = Integer.valueOf(obtenerMaxCodigoProducto().intValue() + 1);
            StringBuilder sql = new StringBuilder("INSERT INTO PRODUCTO VALUES(" + va + ",");
            sql.append(getInsertSQL(producto));
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            ps.executeUpdate(); 
            Conexion.cerrar();
            producto.setCodigo(va);
            insertInfoAdicional(producto);
            insertProductoImpuesto(producto);
        }
        catch(Exception ex){}
  }
  
  public void crearProducto(List<Producto> productos)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            for (Producto producto : productos)
            {
              Integer va = Integer.valueOf(obtenerMaxCodigoProducto().intValue() + 1);
              StringBuilder sql = new StringBuilder("INSERT INTO PRODUCTO VALUES(" + va + ",");
              sql.append(getInsertSQL(producto));
              PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
              ps.executeUpdate();
              producto.setCodigo(va);
            }
           Conexion.cerrar();
       }
        catch(Exception ex){}
  }
  
  private String getInsertSQL(Producto producto)
  {
    return "'" + producto.getCodigoPrincipal() + "','" + producto.getCodigoAuxiliar() + "','" + producto.getNombre() + "'," + producto.getValorUnitario() + ",'" + producto.getTipoProducto() + "','" + producto.getIva() + "','" + producto.getIce() + "','" + producto.getIrbpnr() + "')";
  }
  
  public Integer obtenerMaxCodigoProducto()
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("SELECT MAX(CODIGO) as SECUENCIA FROM PRODUCTO");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            if (this.rs.next()) {
              return Integer.valueOf(this.rs.getInt("SECUENCIA"));
            }
        }
        catch(Exception ex){}
    return Integer.valueOf(0);
  }
  
  public Integer ObtenerMaxCodigoInfoAdicional()
    throws SQLException, ClassNotFoundException
  {
     try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("SELECT MAX(CODIGO) as SECUENCIA FROM INFO_ADICIONAL");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            if (this.rs.next()) {
              return Integer.valueOf(this.rs.getInt("SECUENCIA"));
            }
    }
        catch(Exception ex){}
    return Integer.valueOf(0);
  }
  
  private void insertInfoAdicional(Producto producto)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            for (InformacionAdicionalProducto inf : producto.getInfoAdicionalList())
            {
              Integer va = Integer.valueOf(ObtenerMaxCodigoInfoAdicional().intValue() + 1);
              StringBuilder sql = new StringBuilder("INSERT INTO INFO_ADICIONAL VALUES(" + va + ",");
              String sentencia = producto.getCodigo() + ",'" + inf.getAtributo() + "','" + inf.getValor() + "')";
              sql.append(sentencia);
              PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
                      ps.executeUpdate(); 
            }
            Conexion.cerrar();
        }
        catch(Exception ex){}
  }
  
  private void insertProductoImpuesto(Producto producto)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            for (ImpuestoProducto inf : producto.getImpuestoProducto())
            {
              StringBuilder sql = new StringBuilder("INSERT INTO PRODUCTO_IMPUESTO VALUES(" + producto.getCodigo() + ",'" + inf.getCodigoImpuesto() + "')");
              PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
                      ps.executeUpdate(); 
            }
            Conexion.cerrar();
        }
        catch(Exception ex){}
  }
  
  public List<Producto> buscarPorParametros(String codPrincipal, String codAux, String nomProd, String tipoProd, Integer tipoImpuesto)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCTO WHERE 1=1 ");
            sql.append(getSqlBusquedaProducto(codPrincipal, codAux, nomProd, tipoProd, tipoImpuesto));
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            List<Producto> prd = new ArrayList();
            prd = obtenerProducto();
            return prd;
        }
        catch(Exception ex){}
    return new ArrayList();
  }
  
  private List<ImpuestoProducto> obtenerImpuestoProducto(Integer p)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCTO_IMPUESTO WHERE CODIGO_PRODUCTO=" + p);
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
        }
        catch(Exception ex){}
    return getImpuestoProducto();
  }
  
  private List<ImpuestoProducto> getImpuestoProducto()
    throws SQLException
  {
    List<ImpuestoProducto> impuestoProductoList = new ArrayList();
    while (this.rs.next())
    {
      ImpuestoProducto imProd = new ImpuestoProducto();
      imProd.setCodigoProducto(Integer.valueOf(this.rs.getInt("CODIGO_PRODUCTO")));
      imProd.setCodigoImpuesto(this.rs.getString("CODIGO_IMPUESTO_VALOR"));
      impuestoProductoList.add(imProd);
    }
    return impuestoProductoList;
  }
  
  private List<InformacionAdicionalProducto> obtenerInfoAdicional(Integer p)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("SELECT * FROM INFO_ADICIONAL WHERE CODIGO_PRODUCTO=" + p);
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
        }
        catch(Exception ex){}
    return getInfoAdicional(this.rs);
  }
  
  private List<ImpuestoValor> obtenerImpuestoValor(Integer p)
    throws ClassNotFoundException, SQLException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("select v.* from impuesto_valor v, producto_impuesto p");
            sql.append(" where v.codigo=p.codigo_impuesto_valor");
            sql.append(" and p.codigo_producto=");
            sql.append(p);
            sql.append(" and v.tipo_impuesto in('A','I','B')");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
        }
        catch(Exception ex){}
    return getImpuestoValor(this.rs);
  }
  
  private List<ImpuestoValor> getImpuestoValor(ResultSet result)
    throws SQLException
  {
    List<ImpuestoValor> impuestoValorList = new ArrayList();
    while (result.next())
    {
      ImpuestoValor impValor = new ImpuestoValor();
      impValor.setCodigo(result.getString("CODIGO"));
      impValor.setCodigoImpuesto(Integer.valueOf(result.getInt("CODIGO_IMPUESTO")));
      impValor.setPorcentaje(Double.valueOf(result.getDouble("PORCENTAJE")));
      impValor.setPorcentajeRentencion(Double.valueOf(result.getDouble("PORCENTAJE_RETENCION")));
      impValor.setTipoImpuesto(result.getString("TIPO_IMPUESTO"));
      impValor.setDescripcion(result.getString("DESCRIPCION"));
      impuestoValorList.add(impValor);
    }
    return impuestoValorList;
  }
  
  private List<InformacionAdicionalProducto> getInfoAdicional(ResultSet rs)
    throws SQLException
  {
    List<InformacionAdicionalProducto> listInfoAdicional = new ArrayList();
    while (rs.next())
    {
      InformacionAdicionalProducto infoPro = new InformacionAdicionalProducto();
      infoPro.setCodigo(Integer.valueOf(rs.getInt("CODIGO")));
      infoPro.setCodigoProducto(Integer.valueOf(rs.getInt("CODIGO_PRODUCTO")));
      infoPro.setAtributo(rs.getString("ATRIBUTO"));
      infoPro.setValor(rs.getString("VALOR"));
      listInfoAdicional.add(infoPro);
    }
    return listInfoAdicional;
  }
  
  private String getSqlBusquedaProducto(String codPrincipal, String codAux, String nomProd, String tipoProd, Integer tipoImpuesto)
  {
    String sql = "";
    if ((codPrincipal != null) && (!codPrincipal.isEmpty())) {
      sql = sql + " and UCASE(CODIGO_PRINCIPAL)=" + "'" + codPrincipal.toUpperCase() + "'";
    }
    if ((codAux != null) && (!codAux.isEmpty())) {
      sql = sql + " and UCASE(CODIGO_AUXILIAR)=" + "'" + codAux.toUpperCase() + "'";
    }
    if ((nomProd != null) && (!nomProd.isEmpty())) {
      sql = sql + " and UCASE(nombre) = " + "'" + nomProd.toUpperCase() + "'";
    }
    if ((tipoProd != null) && (!tipoProd.isEmpty())) {
      sql = sql + " and TIPO_PRODUCTO ='" + tipoProd + "'";
    }
    if ((tipoImpuesto != null) && (tipoImpuesto.intValue() == 2)) {
      sql = sql + " and IVA='S'";
    }
    if ((tipoImpuesto != null) && (tipoImpuesto.intValue() == 3)) {
      sql = sql + " and ICE='S'";
    }
    if ((tipoImpuesto != null) && (tipoImpuesto.intValue() == 5)) {
      sql = sql + " and IRBPNR='S'";
    }
    return sql;
  }
  
  private List<Producto> obtenerProducto() throws SQLException, ClassNotFoundException
  {
    List<Producto> productoList = new ArrayList();
    while (this.rs.next())
    {
      Producto p = new Producto();
      p.setCodigo(Integer.valueOf(this.rs.getInt("CODIGO")));
      p.setCodigoPrincipal(this.rs.getString("CODIGO_PRINCIPAL"));
      p.setCodigoAuxiliar(this.rs.getString("CODIGO_AUXILIAR"));
      p.setNombre(this.rs.getString("NOMBRE"));
      p.setValorUnitario(this.rs.getBigDecimal("VALOR_UNITARIO"));
      p.setTipoProducto(this.rs.getString("TIPO_PRODUCTO"));
      p.setIva(this.rs.getString("IVA"));
      p.setIce(this.rs.getString("ICE"));
      p.setIrbpnr(this.rs.getString("IRBPNR"));
      p.setInfoAdicionalList(obtenerInfoAdicional(p.getCodigo()));
      p.setImpuestoValor(obtenerImpuestoValor(p.getCodigo()));
      productoList.add(p);
    }
    return productoList;
  }
  
  private void deleteInfoAdicionalProducto(List<InformacionAdicionalProducto> listInfoAdicional)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            for (InformacionAdicionalProducto infoAd : listInfoAdicional)
            {
              StringBuilder sql = new StringBuilder("DELETE FROM INFO_ADICIONAL  WHERE CODIGO=" + infoAd.getCodigo());
              PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
              ps.executeUpdate(); 
            }
             Conexion.cerrar();
        }
        catch(Exception ex){}
  }
  
  private void deleteImpuestoProducto(List<ImpuestoProducto> impuestoProdList)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            for (ImpuestoProducto impProd : impuestoProdList)
            {
              StringBuilder sql = new StringBuilder("DELETE FROM PRODUCTO_IMPUESTO  WHERE CODIGO_PRODUCTO=" + impProd.getCodigoProducto());
              PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
                      ps.executeUpdate(); 
            }
             Conexion.cerrar();
        }
        catch(Exception ex){}
  }
  
  private void executeUpdateProducto(Producto p)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("UPDATE PRODUCTO SET ");
            sql.append(getSteStatament(p));
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            ps.executeUpdate(); 
            Conexion.cerrar();
        }
        catch(Exception ex){}
  }
  
  private String getSteStatament(Producto p)
  {
    String sql = "CODIGO_PRINCIPAL='" + p.getCodigoPrincipal() + "'," + "CODIGO_AUXILIAR='" + p.getCodigoAuxiliar() + "'," + "NOMBRE='" + p.getNombre() + "'," + "VALOR_UNITARIO=" + p.getValorUnitario() + "," + "TIPO_PRODUCTO='" + p.getTipoProducto() + "'," + "IVA='" + p.getIva() + "'," + "ICE='" + p.getIce() + "' where codigo=" + p.getCodigo();
    
    return sql;
  }
  
  public void updateProducto(Producto producto)
    throws SQLException, ClassNotFoundException
  {
    deleteImpuestoProducto(obtenerImpuestoProducto(producto.getCodigo()));
    deleteInfoAdicionalProducto(obtenerInfoAdicional(producto.getCodigo()));
    executeUpdateProducto(producto);
    insertInfoAdicional(producto);
    insertProductoImpuesto(producto);
  }
  
  public List<Producto> obtenerProducto(String codigoPrincipal, String codigoAuxiliar, String descripcion)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCTO WHERE 1=1");
            if ((codigoPrincipal != null) && (!codigoPrincipal.isEmpty())) {
              sql.append(" and CODIGO_PRINCIPAL like '" + codigoPrincipal + "'");
            }
            if ((codigoAuxiliar != null) && (!codigoAuxiliar.isEmpty())) {
              sql.append(" and CODIGO_AUXILIAR like '" + codigoAuxiliar + "'");
            }
            if ((descripcion != null) && (!descripcion.isEmpty())) {
              sql.append(" and NOMBRE like '" + descripcion.toUpperCase() + "%'");
            }
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
        }
        catch(Exception ex){}
    return listaProductos();
  }
  
  public Producto obtenerProductoPorTipo(String codigoPrincipal, String codigoAuxiliar, String nombre, String tipoProducto)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            String sql = "SELECT * FROM PRODUCTO WHERE 1=1";
            if (codigoPrincipal != null) {
              sql = sql + " and UCASE(CODIGO_PRINCIPAL)=" + "'" + codigoPrincipal.toUpperCase() + "'";
            }
            if (codigoAuxiliar != null) {
              sql = sql + " and UCASE(CODIGO_AUXILIAR)=" + "'" + codigoAuxiliar.toUpperCase() + "'";
            }
            if (nombre != null) {
              sql = sql + " and UCASE(nombre) = " + "'" + nombre.toUpperCase() + "'";
            }
            if (tipoProducto != null) {
              sql = sql + " and TIPO_PRODUCTO ='" + tipoProducto + "'";
            }
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
            List<Producto> listaProductos = obtenerProducto();
            if (listaProductos.isEmpty()) {
              return null;
            }
            return (Producto)listaProductos.get(0);
        }
        catch(Exception ex){}
    return null;
  }
  
  private List<Producto> listaProductos()
    throws SQLException, ClassNotFoundException
  {
    List<Producto> list = new ArrayList();
    while (this.rs.next())
    {
      Producto p = new Producto();
      
      p.setCodigo(Integer.valueOf(this.rs.getInt("CODIGO")));
      p.setCodigoPrincipal(this.rs.getString("CODIGO_PRINCIPAL"));
      p.setCodigoAuxiliar(this.rs.getString("CODIGO_AUXILIAR"));
      p.setNombre(this.rs.getString("NOMBRE"));
      p.setValorUnitario(this.rs.getBigDecimal("VALOR_UNITARIO"));
      p.setTipoProducto(this.rs.getString("TIPO_PRODUCTO"));
      
      //p.setImpuestoProducto(new ProductoImpuestoSQL().obtenerImpuestoProducto(p.getCodigo()));
      //p.setInfoAdicionalList(new InformacionAdicionalSQL().obtenerInformacionAdicional(p.getCodigo()));
      
      list.add(p);
    }
    return list;
  }
  
  public void deleteProducto(String codigoPrincipal)
    throws SQLException, ClassNotFoundException
  {
    Producto p = obtenerProductoCodigoPrincipal(codigoPrincipal);
    if ((p.getImpuestoProducto() != null) && (!p.getImpuestoProducto().isEmpty())) {
      deleteImpuestoProducto(p.getImpuestoProducto());
    }
    if ((p.getInfoAdicionalList() != null) && (!p.getInfoAdicionalList().isEmpty())) {
      deleteInfoAdicionalProducto(p.getInfoAdicionalList());
    }
    deleteProductoSQL(p);
  }
  
  private void deleteProductoSQL(Producto p)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("DELETE FROM PRODUCTO  WHERE CODIGO=" + p.getCodigo());
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            ps.executeUpdate(); 
            Conexion.cerrar();
        }
        catch(Exception ex){}
  }
  
  private Producto obtenerProductoCodigoPrincipal(String codigoPrincipal)
    throws SQLException, ClassNotFoundException
  {
    try
        {
            Conexion.conectaSRI();
            StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCTO WHERE 1=1");
            sql.append(" and CODIGO_PRINCIPAL = '" + codigoPrincipal + "'");
            PreparedStatement ps = Conexion.getCon().prepareStatement(sql.toString());
            this.rs = ps.executeQuery();
            Conexion.cerrar();
    
        }
        catch(Exception ex){}
    return getProducto(this.rs);
  }
  
  private Producto getProducto(ResultSet result)
    throws SQLException, ClassNotFoundException
  {
    Producto p = new Producto();
    while (result.next())
    {
      p.setCodigo(Integer.valueOf(result.getInt("CODIGO")));
      p.setCodigoPrincipal(result.getString("CODIGO_PRINCIPAL"));
      p.setCodigoAuxiliar(result.getString("CODIGO_AUXILIAR"));
      p.setNombre(result.getString("NOMBRE"));
      p.setValorUnitario(result.getBigDecimal("VALOR_UNITARIO"));
      p.setTipoProducto(result.getString("TIPO_PRODUCTO"));
      System.out.println("CODIGO ....." + p.getCodigo());
      //p.setImpuestoProducto(new ProductoImpuestoSQL().obtenerImpuestoProducto(p.getCodigo()));
      //p.setInfoAdicionalList(new InformacionAdicionalSQL().obtenerInformacionAdicional(p.getCodigo()));
    }
    return p;
  }
  
}

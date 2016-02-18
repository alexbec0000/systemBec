 package comprobantes.table;
 
import comprobantes.entidades.ImpuestoProducto;
import comprobantes.entidades.ImpuestoValor;
import comprobantes.entidades.Producto;
import comprobantes.modelo.SubtotalImpuesto;
import comprobantes.sql.ImpuestoValorSQL;
import comprobantes.sql.ProductoSQL;
import comprobantes.util.TipoImpuestoEnum;
import comprobantes.util.TipoImpuestoIvaEnum;
import comprobantes.util.ValidadorCampos;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

public class NotaCreditoModel extends AbstractTableModel
{
  public static final int CANTIDAD = 0;
  public static final int COD_PROD = 1;
  public static final int CODIGO_AUXILIAR = 2;
  public static final int PROD_DESC = 3;
  public static final int PREC_UNIT = 4;
  public static final int DESCUENTO = 5;
  public static final int VAL_TOTAL = 6;
  public static final int VAL_ICE = 7;
  public static final int BASE_IMPONIBLE_IRBPNR = 8;
  public static final int VAL_IRBPNR = 9;
  public static final int ACCION = 10;
  public static final int DECIMALES_IVA_PRESUNTIVO = 2;
  private BigDecimal subtotalIva12 = BigDecimal.ZERO;
  private BigDecimal subtotalIva0 = BigDecimal.ZERO;
  private BigDecimal subtotalNoIva = BigDecimal.ZERO;
  private BigDecimal subtotalICE = BigDecimal.ZERO;
  private BigDecimal subtotalExentoIVA = BigDecimal.ZERO;
  private BigDecimal subtotalIRBPNR = BigDecimal.ZERO;
  private List<SubtotalImpuesto> listaIva12 = new ArrayList();
  private List<SubtotalImpuesto> listaIva0 = new ArrayList();
  private List<SubtotalImpuesto> listaNoIva = new ArrayList();
  private List<SubtotalImpuesto> listaIRBPNR = new ArrayList();
  private List<SubtotalImpuesto> listaExentoIVA = new ArrayList();
  private List<SubtotalImpuesto> listaICE = new ArrayList();
  private ArrayList<Object[]> data;
  private String[] columnNames = { "Cantidad", "Código principal", "Código auxiliar", "Descripción", "Precio Unitario", "Descuento", "Valor Total", "Valor ICE", "B.I. IRBPNR", "Valor IRBPNR", "Acción" };
  
  public NotaCreditoModel()
  {
    this.data = new ArrayList();
  }
  
  public NotaCreditoModel(Producto prod, Double cantidad)
  {
    Object[] p = { cantidad, prod.getCodigoPrincipal(), prod.getCodigoAuxiliar(), prod.getNombre(), prod.getValorUnitario(), BigDecimal.ZERO, new BigDecimal(prod.getValorUnitario().doubleValue() * cantidad.doubleValue()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "" };
    
    this.data.add(p);
  }
  
  public int getColumnCount()
  {
    return this.columnNames.length;
  }
  
  public int getRowCount()
  {
    return this.data.size();
  }
  
  public String getColumnName(int col)
  {
    return this.columnNames[col];
  }
  
  public Object getValueAt(int row, int col)
  {
    if ((col == 4) || (col == 5) || (col == 8) || (col == 9)) {
      return convertirABigDecimal(((Object[])this.data.get(row))[col]);
    }
    if (col == 0) {
      return convertirADouble(((Object[])this.data.get(row))[col]);
    }
    return ((Object[])this.data.get(row))[col];
  }
  
  public Class getColumnClass(int c)
  {
    return getValueAt(0, c).getClass();
  }
  
  public boolean isCellEditable(int row, int col)
  {
    boolean resp = false;
    if ((col == 0) || (col == 4) || (col == 5) || (col == 7) || (col == 8) || (col == 9) || (col == 10)) {
      resp = true;
    }
    return resp;
  }
  
  private BigDecimal getTotal(int row)
  {
    BigDecimal total = null;
    BigDecimal precio = (BigDecimal)getValueAt(row, 4);
    Double cantidad = (Double)getValueAt(row, 0);
    BigDecimal descuento = (BigDecimal)getValueAt(row, 5);
    if ((precio == null) || (cantidad == null)) {
      total = BigDecimal.ZERO;
    } else {
      total = precio.multiply(new BigDecimal(cantidad.doubleValue())).subtract(descuento).setScale(2, RoundingMode.HALF_UP);
    }
    return total;
  }
  
  public BigDecimal getTotalDescuento()
  {
    BigDecimal total = BigDecimal.ZERO;
    for (int i = 0; i < this.data.size(); i++)
    {
      BigDecimal parcial = getValueAt(i, 5) == null ? BigDecimal.ZERO : (BigDecimal)getValueAt(i, 5);
      total = total.add(parcial);
    }
    return total;
  }
  
  public void setValueAt(Object value, int row, int col)
  {
    if (this.data.isEmpty()) {
      return;
    }
    if (this.data.size() == row) {
      row--;
    }
    String codigoPrincipal = (String)getValueAt(row, 1);
    String codigoAuxiliar = (String)getValueAt(row, 2);
    String descripcion = (String)getValueAt(row, 3);
    Producto prod = null;
    try
    {
      prod = (Producto)new ProductoSQL().obtenerProducto(codigoPrincipal, codigoAuxiliar, descripcion).get(0);
    }
    catch (Exception ex)
    {
      Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (col == 0)
    {
      Double newCantidad = null;
      try
      {
        newCantidad = Double.valueOf(Double.parseDouble(value.toString()));
      }
      catch (NumberFormatException ex)
      {
        System.out.println("Valor no permitido - Se ha producido un error ");
        
        return;
      }
      if (!ValidadorCampos.validarDecimales(value.toString(), 2).booleanValue())
      {
        System.out.println("El número máximo de decimales permitido es: "+String.valueOf(2)+ " Se ha producido un error ");
        return;
      }
      if (newCantidad.doubleValue() <= 0.0D)
      {
        System.out.println("Valor no permitido - Se ha producido un error ");
      }
      else
      {
        ((Object[])this.data.get(row))[col] = String.valueOf(value);
        BigDecimal totalFila = getTotal(row);
        setValueAt(totalFila, row, 6);
        try
        {
          setValueAt(calculaICEProducto(prod), row, 7);
          calculaImpuestosProducto(prod, newCantidad.doubleValue(), true, row);
        }
        catch (Exception ex)
        {
          Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    else if (col == 4)
    {
      Double newCantidad = (Double)getValueAt(row, 0);
      ((Object[])this.data.get(row))[col] = value;
      try
      {
        BigDecimal nuevoPrecio = (BigDecimal)getValueAt(row, 4);
        prod.setValorUnitario(nuevoPrecio);
        setValueAt(getTotal(row), row, 6);
        calculaImpuestosProducto(prod, newCantidad.doubleValue(), true, row);
      }
      catch (Exception ex)
      {
        Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    else if (col == 5)
    {
      Double newCantidad = (Double)getValueAt(row, 0);
      BigDecimal newDescuento = null;
      try
      {
        newDescuento = new BigDecimal(Double.parseDouble(value.toString())).setScale(2, RoundingMode.HALF_UP);
      }
      catch (Exception ex)
      {
        System.out.println("Valor no permitido - Se ha producido un error ");
      }
      if (newDescuento.doubleValue() < 0.0D)
      {
        System.out.println("Valor no permitido - Se ha producido un error ");
      }
      else
      {
        if (!ValidadorCampos.validarDecimales(value.toString(), 2).booleanValue())
        {
          System.out.println("El número máximo de decimales permitido es: "+String.valueOf(2)+ " Se ha producido un error ");
          return;
        }
        ((Object[])this.data.get(row))[col] = value;
        BigDecimal totalFila = getTotal(row);
        setValueAt(totalFila, row, 6);
        try
        {
          BigDecimal nuevoPrecio = (BigDecimal)getValueAt(row, 4);
          prod.setValorUnitario(nuevoPrecio);
          setValueAt(calculaICEProducto(prod), row, 7);
          calculaImpuestosProducto(prod, newCantidad.doubleValue(), true, row);
        }
        catch (Exception ex)
        {
          Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    else if (col == 7)
    {
      BigDecimal valorIce = null;
      try
      {
        if (!((String)value).isEmpty()) {
          valorIce = new BigDecimal(Double.parseDouble(value.toString())).setScale(2, RoundingMode.HALF_UP);
        }
      }
      catch (Exception ex)
      {
        System.out.println("Valor no permitido - Se ha producido un error ");
      }
      if ((valorIce != null) && (valorIce.doubleValue() < 0.0D))
      {
        System.out.println("Valor no permitido - Se ha producido un error ");
      }
      else
      {
        if (valorIce != null) {
          ((Object[])this.data.get(row))[col] = valorIce.toPlainString();
        } else {
          ((Object[])this.data.get(row))[col] = "";
        }
        Double newCantidad = (Double)getValueAt(row, 0);
        try
        {
          calculaImpuestosProducto(prod, newCantidad.doubleValue(), true, row);
        }
        catch (Exception ex)
        {
          Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    else if ((col == 9) || (col == 8))
    {
      ((Object[])this.data.get(row))[col] = value;
      BigDecimal valorIRBPNR = ((BigDecimal)getValueAt(row, 9)).setScale(2, RoundingMode.HALF_UP);
      if ((valorIRBPNR != null) && (valorIRBPNR.doubleValue() < 0.0D))
      {
        System.out.println("Valor no permitido - Se ha producido un error ");
      }
      else
      {
        Double newCantidad = (Double)getValueAt(row, 0);
        try
        {
          calculaImpuestosProducto(prod, newCantidad.doubleValue(), true, row);
        }
        catch (Exception ex)
        {
          Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    else
    {
      ((Object[])this.data.get(row))[col] = value;
    }
    fireTableCellUpdated(row, col);
  }
  
  public void deleteRow(int fila)
  {
    if (fila == -1) {
      fila = 0;
    }
    Object[] pr = (Object[])this.data.get(fila);
    try
    {
      String codigoPrincipal = (String)getValueAt(fila, 1);
      String codigoAuxiliar = (String)getValueAt(fila, 2);
      String descripcion = (String)getValueAt(fila, 3);
      Producto prod = (Producto)new ProductoSQL().obtenerProducto(codigoPrincipal, codigoAuxiliar, descripcion).get(0);
      eliminaImpuestosProducto(prod);
    }
    catch (SQLException ex)
    {
      Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (ClassNotFoundException ex)
    {
      Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.data.remove(pr);
    this.data.size();
    fireTableRowsDeleted(fila, fila);
    super.fireTableDataChanged();
  }
  
  public void addRow(Producto producto)
  {
    ImpuestoProducto ip=new ImpuestoProducto(producto.getCodigoImpuesto()+"");
    producto.getImpuestoProducto().add(ip);
    int numRows = this.data.size();
    BigDecimal subtotalProducto = new BigDecimal(producto.getValorUnitario().doubleValue() * producto.getCantidad()).setScale(2, RoundingMode.HALF_UP);
    Object[] fila = { producto.getCantidad(), producto.getCodigoPrincipal(), producto.getCodigoAuxiliar(), producto.getNombre(), producto.getValorUnitario(), BigDecimal.ZERO, subtotalProducto, calculaICEProducto(producto), BigDecimal.ZERO, BigDecimal.ZERO, producto.getObs() };
    
    this.data.add(fila);
    try
    {
      calculaImpuestosProducto(producto, producto.getCantidad(), false, this.data.size() - 1);
    }
    catch (Exception ex)
    {
      Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    fireTableRowsInserted(numRows, this.data.size() - 1);
  }
  
  private void calculaImpuestosProducto(Producto producto, double newCantidad, boolean old, int row)
    throws SQLException, ClassNotFoundException
  {
    List<ImpuestoProducto> impuestosProducto = producto.getImpuestoProducto();
    BigDecimal iceItem;
    if (!impuestosProducto.isEmpty())
    {
      iceItem = BigDecimal.ZERO;
      for (ImpuestoProducto ip : impuestosProducto)
      {
        ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo(ip.getCodigoImpuesto());
        BigDecimal descuento = ((BigDecimal)getValueAt(row, 5)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal baseimponible = new BigDecimal(producto.getValorUnitario().doubleValue() * newCantidad).subtract(descuento).setScale(2, RoundingMode.HALF_UP);
        if (iv.getCodigoImpuesto().intValue() == TipoImpuestoEnum.ICE.getCode())
        {
          BigDecimal iceManual = null;
          if (!((String)getValueAt(row, 7)).isEmpty()) {
            iceManual = new BigDecimal((String)getValueAt(row, 7)).setScale(2, RoundingMode.HALF_UP);
          }
          BigDecimal newIceIndividual = null;
          if (iceManual != null) {
            newIceIndividual = iceManual;
          } else {
            newIceIndividual = BigDecimal.ZERO;
          }
          if (old == true) {
            remueveSubtotal(producto, this.listaICE);
          }
          this.listaICE.add(new SubtotalImpuesto(producto, iv.getCodigoImpuesto(), iv.getCodigo(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), baseimponible, newIceIndividual));
          
          this.subtotalICE = calcularTotalICE(this.listaICE);
          iceItem = newIceIndividual;
        }
        else if (iv.getCodigoImpuesto().intValue() == TipoImpuestoEnum.IRBPNR.getCode())
        {
          BigDecimal valorImpuestoBotellas = null;
          BigDecimal baseImponibleIRBPNR = ((BigDecimal)getValueAt(row, 8)).setScale(2, RoundingMode.HALF_UP);
          valorImpuestoBotellas = ((BigDecimal)getValueAt(row, 9)).setScale(2, RoundingMode.HALF_UP);
          if (old == true) {
            remueveSubtotal(producto, this.listaIRBPNR);
          }
          if (producto.getBaseImponileIRBPNR() != null) {
            baseImponibleIRBPNR = producto.getBaseImponileIRBPNR();
          }
          this.listaIRBPNR.add(new SubtotalImpuesto(producto, iv.getCodigoImpuesto(), iv.getCodigo(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), baseImponibleIRBPNR, valorImpuestoBotellas));
          
          this.subtotalIRBPNR = sumarListaImpuestos(this.listaIRBPNR);
        }
        else if (iv.getCodigoImpuesto().intValue() == TipoImpuestoEnum.IVA.getCode())
        {
          BigDecimal temp = BigDecimal.ZERO;
          BigDecimal total = BigDecimal.ZERO;
          SubtotalImpuesto subTotal = null;
          if (iceItem.doubleValue() > 0.0D)
          {
            temp = baseimponible;
            baseimponible = baseimponible.add(iceItem);
            total = baseimponible.multiply(BigDecimal.valueOf(iv.getPorcentaje().doubleValue() / 100.0D)).setScale(2, RoundingMode.HALF_UP);
            subTotal = new SubtotalImpuesto(producto, iv.getCodigoImpuesto(), iv.getCodigo(), BigDecimal.valueOf(iv.getPorcentaje().doubleValue() / 100.0D).setScale(2, RoundingMode.HALF_UP), baseimponible, total);
            
            baseimponible = temp;
          }
          else
          {
            total = baseimponible.multiply(BigDecimal.valueOf(iv.getPorcentaje().doubleValue() / 100.0D)).setScale(2, RoundingMode.HALF_UP);
            subTotal = new SubtotalImpuesto(producto, iv.getCodigoImpuesto(), iv.getCodigo(), BigDecimal.valueOf(iv.getPorcentaje().doubleValue() / 100.0D).setScale(2, RoundingMode.HALF_UP), baseimponible, total);
          }
          subTotal.setValorIce(iceItem);
          if (iv.getCodigo().equals(TipoImpuestoIvaEnum.IVA_VENTA_0.getCode()))
          {
            if (old == true) {
              remueveSubtotal(producto, this.listaIva0);
            }
            this.listaIva0.add(subTotal);
            this.subtotalIva0 = calcularTotal(this.listaIva0);
          }
          else if (iv.getCodigo().equals(TipoImpuestoIvaEnum.IVA_NO_OBJETO.getCode()))
          {
            if (old == true) {
              remueveSubtotal(producto, this.listaNoIva);
            }
            this.listaNoIva.add(subTotal);
            this.subtotalNoIva = calcularTotal(this.listaNoIva);
          }
          else if (iv.getCodigo().equals(TipoImpuestoIvaEnum.IVA_EXCENTO.getCode()))
          {
            if (old == true) {
              remueveSubtotal(producto, this.listaExentoIVA);
            }
            this.listaExentoIVA.add(subTotal);
            this.subtotalExentoIVA = calcularTotal(this.listaExentoIVA);
          }
          else if (iv.getCodigo().equals(TipoImpuestoIvaEnum.IVA_VENTA_12.getCode()))
          {
            if (old == true) {
              remueveSubtotal(producto, this.listaIva12);
            }
            this.listaIva12.add(subTotal);
            this.subtotalIva12 = calcularTotal(this.listaIva12);
          }
        }
      }
    }
  }
  
  private void eliminaImpuestosProducto(Producto prod)
    throws SQLException, ClassNotFoundException
  {
    List<ImpuestoProducto> impuestosProducto = prod.getImpuestoProducto();
    for (ImpuestoProducto ip : impuestosProducto)
    {
      ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo(ip.getCodigoImpuesto());
      if (iv.getCodigoImpuesto().intValue() == TipoImpuestoEnum.IVA.getCode())
      {
        if (iv.getCodigo().equals(TipoImpuestoIvaEnum.IVA_VENTA_0.getCode()))
        {
          remueveSubtotal(prod, this.listaIva0);
          this.subtotalIva0 = calcularTotal(this.listaIva0);
        }
        else if (iv.getCodigo().equals(TipoImpuestoIvaEnum.IVA_NO_OBJETO.getCode()))
        {
          remueveSubtotal(prod, this.listaNoIva);
          this.subtotalNoIva = calcularTotal(this.listaNoIva);
        }
        else if (iv.getCodigo().equals(TipoImpuestoIvaEnum.IVA_EXCENTO.getCode()))
        {
          remueveSubtotal(prod, this.listaExentoIVA);
          this.subtotalExentoIVA = calcularTotal(this.listaExentoIVA);
        }
        else if (iv.getCodigo().equals(TipoImpuestoIvaEnum.IVA_VENTA_12.getCode()))
        {
          remueveSubtotal(prod, this.listaIva12);
          this.subtotalIva12 = calcularTotal(this.listaIva12);
        }
      }
      else if (iv.getCodigoImpuesto().intValue() == TipoImpuestoEnum.ICE.getCode())
      {
        remueveSubtotal(prod, this.listaICE);
        this.subtotalICE = sumarListaImpuestos(this.listaICE);
      }
      else if (iv.getCodigoImpuesto().intValue() == TipoImpuestoEnum.IRBPNR.getCode())
      {
        remueveSubtotal(prod, this.listaIRBPNR);
        this.subtotalIRBPNR = sumarListaImpuestos(this.listaIRBPNR);
      }
    }
  }
  
  private BigDecimal sumarListaImpuestos(List<SubtotalImpuesto> lista)
  {
    BigDecimal total = BigDecimal.ZERO;
    for (SubtotalImpuesto s : lista) {
      total = total.add(s.getSubtotal());
    }
    return total;
  }
  
  private BigDecimal calcularTotal(List<SubtotalImpuesto> lista)
  {
    BigDecimal total = BigDecimal.ZERO;
    for (SubtotalImpuesto s : lista) {
      total = total.add(s.getBaseImponible().subtract(s.getValorIce() == null ? BigDecimal.ZERO : s.getValorIce()));
    }
    return total;
  }
  
  private BigDecimal calcularTotalICE(List<SubtotalImpuesto> lista)
  {
    BigDecimal total = BigDecimal.ZERO;
    for (SubtotalImpuesto s : lista) {
      total = total.add(s.getSubtotal());
    }
    return total;
  }
  
  public BigDecimal calcularTotalIva12()
  {
    BigDecimal base = BigDecimal.ZERO;
    BigDecimal total = BigDecimal.ZERO;
    BigDecimal tarifa = BigDecimal.ZERO;
    for (SubtotalImpuesto s : this.listaIva12)
    {
      base = base.add(s.getBaseImponible());
      tarifa = s.getPorcentaje();
    }
    total = base.multiply(tarifa);
    return total;
  }
  
  private void remueveSubtotal(Producto producto, List<SubtotalImpuesto> lista)
    throws SQLException, ClassNotFoundException
  {
    boolean itemEncontrado = false;
    for (int i = 0; i < lista.size(); i++) {
      if ((((SubtotalImpuesto)lista.get(i)).verificarProducto(producto)) && (!itemEncontrado))
      {
        lista.remove(i);
        itemEncontrado = true;
      }
    }
  }
  
  private Double convertirADouble(Object objeto)
  {
    Double cantidad = null;
    if ((objeto instanceof String)) {
      cantidad = Double.valueOf(Double.parseDouble((String)objeto));
    } else if ((objeto instanceof Double)) {
      cantidad = (Double)objeto;
    } else if ((objeto instanceof Integer)) {
      cantidad = Double.valueOf(Double.parseDouble(objeto.toString()));
    }
    return cantidad;
  }
  
  private BigDecimal convertirABigDecimal(Object objeto)
  {
    BigDecimal cantidad = null;
    if ((objeto instanceof String)) {
      cantidad = new BigDecimal((String)objeto);
    } else if ((objeto instanceof Double)) {
      cantidad = new BigDecimal(((Double)objeto).doubleValue());
    } else if ((objeto instanceof Integer)) {
      cantidad = new BigDecimal(((Integer)objeto).intValue());
    } else if ((objeto instanceof BigDecimal)) {
      cantidad = (BigDecimal)objeto;
    }
    return cantidad;
  }
  
  private String calculaICEProducto(Producto producto)
  {
    String newIceIndividual = "0";
    List<ImpuestoProducto> impuestosProducto = producto.getImpuestoProducto();
    if (!impuestosProducto.isEmpty()) {
      for (ImpuestoProducto ip : impuestosProducto)
      {
        ImpuestoValor iv = null;
        try
        {
          iv = new ImpuestoValorSQL().obtenerValorPorCodigo(ip.getCodigoImpuesto());
        }
        catch (Exception ex)
        {
          Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (iv.getCodigoImpuesto().intValue() == TipoImpuestoEnum.ICE.getCode())
        {
          newIceIndividual = "";
          break;
        }
      }
    }
    return newIceIndividual;
  }
  
  public static boolean esImpuestoEspecial(String codigo)
  {
    boolean respuesta = false;
    if ((codigo.equals("3011")) || (codigo.equals("3021")) || (codigo.equals("3031")) || (codigo.equals("3041"))) {
      respuesta = true;
    }
    return respuesta;
  }
  
  public List<SubtotalImpuesto> getListaICE()
  {
    return this.listaICE;
  }
  
  public void setListaICE(List<SubtotalImpuesto> listaICE)
  {
    this.listaICE = listaICE;
  }
  
  public List<SubtotalImpuesto> getListaIva0()
  {
    return this.listaIva0;
  }
  
  public void setListaIva0(List<SubtotalImpuesto> listaIva0)
  {
    this.listaIva0 = listaIva0;
  }
  
  public List<SubtotalImpuesto> getListaIva12()
  {
    return this.listaIva12;
  }
  
  public void setListaIva12(List<SubtotalImpuesto> listaIva12)
  {
    this.listaIva12 = listaIva12;
  }
  
  public List<SubtotalImpuesto> getListaNoIva()
  {
    return this.listaNoIva;
  }
  
  public void setListaNoIva(List<SubtotalImpuesto> listaNoIva)
  {
    this.listaNoIva = listaNoIva;
  }
  
  public BigDecimal getSubtotalICE()
  {
    return this.subtotalICE;
  }
  
  public void setSubtotalICE(BigDecimal subtotalICE)
  {
    this.subtotalICE = subtotalICE;
  }
  
  public BigDecimal getSubtotalIva0()
  {
    return this.subtotalIva0;
  }
  
  public void setSubtotalIva0(BigDecimal subtotalIva0)
  {
    this.subtotalIva0 = subtotalIva0;
  }
  
  public BigDecimal getSubtotalIva12()
  {
    return this.subtotalIva12;
  }
  
  public void setSubtotalIva12(BigDecimal subtotalIva12)
  {
    this.subtotalIva12 = subtotalIva12;
  }
  
  public BigDecimal getSubtotalNoIva()
  {
    return this.subtotalNoIva;
  }
  
  public void setSubtotalNoIva(BigDecimal subtotalNoIva)
  {
    this.subtotalNoIva = subtotalNoIva;
  }
  
  public BigDecimal getSubtotalExentoIVA()
  {
    return this.subtotalExentoIVA;
  }
  
  public void setSubtotalExentoIVA(BigDecimal subtotalExentoIVA)
  {
    this.subtotalExentoIVA = subtotalExentoIVA;
  }
  
  public List<SubtotalImpuesto> getListaExentoIVA()
  {
    return this.listaExentoIVA;
  }
  
  public void setListaExentoIVA(List<SubtotalImpuesto> listaExentoIVA)
  {
    this.listaExentoIVA = listaExentoIVA;
  }
  
  public List<SubtotalImpuesto> getListaIRBPNR()
  {
    return this.listaIRBPNR;
  }
  
  public void setListaIRBPNR(List<SubtotalImpuesto> listaIRBPNR)
  {
    this.listaIRBPNR = listaIRBPNR;
  }
  
  public BigDecimal getSubtotalIRBPNR()
  {
    return this.subtotalIRBPNR;
  }
  
  public void setSubtotalIRBPNR(BigDecimal subtotalIRBPNR)
  {
    this.subtotalIRBPNR = subtotalIRBPNR;
  }
}

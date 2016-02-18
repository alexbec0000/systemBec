package comprobantes.modelo;
 
import comprobantes.entidades.Producto;
import comprobantes.doc.guiaRemision.Destinatario;
import java.util.List;

public class DestinatarioProducto
{
  private Destinatario destinatario;
  private List<Producto> productos;
  
  public DestinatarioProducto(Destinatario destinatario, List<Producto> productos)
  {
    this.destinatario = destinatario;
    this.productos = productos;
  }
  
  public Destinatario getDestinatario()
  {
    return this.destinatario;
  }
  
  public void setDestinatario(Destinatario destinatario)
  {
    this.destinatario = destinatario;
  }
  
  public List<Producto> getProductos()
  {
    return this.productos;
  }
  
  public void setProductos(List<Producto> productos)
  {
    this.productos = productos;
  }
}

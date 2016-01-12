package pck_controller;


import java.sql.ResultSet;
import java.sql.SQLException;
import pck_accesoDatos.cls_conexion;

/**
 *
 * @author alex
 */
public abstract class AbstractController{
    
    cls_conexion objcls_conexion=new cls_conexion();
    public ResultSet rs;   
    public final static int TODO = 1;
    public final static int POR_CONDICION = 2;
    public final static int COLUMNA_STRING = 1;
    public final static int COLUMNA_ENTERO = 2;
    
    public String getCodigo(String nombreTabla,String nombreColumna)
    {
        String sql = "SELECT "+nombreColumna+" FROM "+nombreTabla+" ORDER BY "+nombreColumna+" DESC LIMIT 1";
        String codigo = null;
        try{
            rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
               codigo = rs.getObject(1).toString();
            }
            rs.close();
        }catch(SQLException ex){
            return null;           
        }
        return codigo;
    }
    
    public boolean existeRegistro(String nombreTabla,String nombreColumna,String dato)
    {
        String sql = "SELECT "+nombreColumna+" FROM "+nombreTabla+" WHERE "+nombreColumna+" ='"+dato+"';";
        boolean codigo = false;
        try{
            rs = cls_conexion.getStatement().executeQuery(sql);
            while(rs.next())
            {
               codigo=true;
            }
            rs.close();
        }catch(SQLException ex){
            return false;           
        }
        return codigo;
    }
    
    /**
     * 
     * @param nombreTabla nombre de la Tabla
     * @param nomColumnaCambiar nombre de la columan de la cual se cambiara el valor
     * @param nomColumnaId nombre de la columnas con las claves primarias
     * @param id clave primaria
     * @param nuevoValor nuevo valor.
     * @return 
     */
//    public int eliminacionTemporal(String nombreTabla,String nomColumnaCambiar,String nomColumnaId,String id,int nuevoValor)
//    {
//        int resultado = 1;        
//        if(nuevoValor < 0 || nuevoValor>1)
//        {
//            throw new ExceptionValoresNoIgual("el valor no puede ser menor de 0 ni mayor de 1");
//        }
//        String sql = "UPDATE "+nombreTabla+" SET "+nomColumnaCambiar+" = "+nuevoValor+" where "+nomColumnaId+"='"+id+"'";
//        try{
//            resultado = BaseConexion.getStatement().executeUpdate(sql);            
//           
//        }catch(SQLException ex){
//            
//             ex.printStackTrace();        
//        }
//        System.out.println(sql);
//        return resultado;
//    }
    
    /**
     * 
     * @param nombreTabla nombre de la tabla en la base de datos
     * @param columnaId nombre de la columna. que serivara como base para ordenar los regstros<br/>
     * @param columnCondicion nombre de la columna condicional ejem:<br>
     * <p>
     *  select * from cliente where estado = 1<br/>
     *  En este caso 'estado' seria el nombre de la columna condicional.<br/>
     *  si no se desa especificar este campo entonces se debe pasar <code>null</code>
     * </p>
     * @param valorCondicion el valor que servira como condicion para la columna condicional.<br/>
     * <p>Este valor solo debe especificarse, si nombre de la columna condicional es diferente de <code>null</code></p>
     * @param inicio entero que indica desde donde se comensara la obtencion de registros.
     * @param fin numero de registros;
     * @param asc true para que los registros se ordenen de forma ascendente.<br/>
     * <code>false</code> para ordenar los registros se ordenen de forma descendente<br/>
     * ejemplo completo consideranto que controller es clase hija de <code>AbstractController</code>:<br/>
     * si se llama a este metodo de la siguiente forma:<br/>
     * <code>Integer valorCondicional = new Integer(1)</code>; valor condicional es igual a 1<br/>
     * <code>controller.getPaginador("cliente","idCliente","activo",valorCondicional,4,5,true);</code><br/>
     * se generara la siguiente consulta SQL:<br/>
     * select * from cliente where activo = valorCondicional order by idCliente asc;<br/>
     * si se llama a este metodode siguiente forma:<br/>
     * <code>controller.getPaginador("cliente","idCliente",null,null,4,5,true);</code><br/>
     * se generara la siguiente consulta SQL:<br/>
     * select * from cliente order by idCliente asc;<br/>
     */
//    public void getPaginador(String nombreTabla,String columnaId,String columnCondicion,Object valorCondicion,int inicio,int fin,boolean asc)
//    {
//        
//        String sql = "SELECT * FROM "+nombreTabla+" "+(columnCondicion!=null?"where "+columnCondicion+"="+valorCondicion:"")+" "+(asc?"ORDER BY "+columnaId+" asc":"ORDER BY "+columnaId+" desc")+" LIMIT "+inicio+","+fin;
//        System.out.println(sql);
//        try{
//            rs = cls_conexion.getStatement().executeQuery(sql);
//        }catch(SQLException ex){
//            ex.printStackTrace();          
//        }
//       
//    }
    
//    public int geNumRegistros(String tblName,String nomCampoCondicional,Object valorCondicion,boolean todos)
//    {
//        String sql = "SELECT COUNT("+nomCampoCondicional+") AS num FROM "+tblName+" "+(!todos?"WHERE "+nomCampoCondicional+" = "+valorCondicion.toString():"");
//        
//        int numFilas = 0;
//        try{
//            rs = cls_conexion.getStatement().executeQuery(sql);            
//            while(rs.next())
//            {
//                numFilas = rs.getInt(1);
//            }
//            rs.close();            
//        }catch(SQLException ex){
//            //return null;           
//        }
//        System.out.println("Consulta sql: "+sql);
//        System.out.println("num registros "+numFilas);
//        return numFilas;
//    }
    
   
    
    
    
    /**
     * 
     * @param tblName
     * @param campoSumar
     * @param campoComparar
     * @param valorCampoComparar
     * @return 
     */
//    public int sumaValoresCampo(String tblName,String campoSumar,String campoComparar,String valorCampoComparar)
//    {
//        //SELECT SUM(CANT) AS cantidad FROM facv_det WHERE NUM_FACV = "0000000002";
//        String sql = "SELECT SUM("+campoSumar+") as cantidad from "+tblName+" where "+campoComparar+" = '"+valorCampoComparar+"'";
//        int numFilas = 0;
//        try{
//            rs = cls_conexion.getStatement().executeQuery(sql);            
//            while(rs.next())
//            {
//                numFilas = rs.getInt(1);
//            }
//            rs.close();            
//        }catch(SQLException ex){
//            //return null;           
//        }        
//        return numFilas;
//        
//    }

}

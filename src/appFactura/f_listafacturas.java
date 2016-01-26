package appFactura;

import javax.swing.JPanel;
import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Point;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.*;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;

public class f_listafacturas extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton BSeleccionar = null;

	private JButton BCerrar = null;

	private JTextField TxtNumero = null;

	private JLabel LblNumero = null;

	private JScrollPane jScrollPane = null;

	private JTable JTable = null;
	Connection conexion;  //  @jve:decl-index=0:
	Statement sentencia,sentenciaven,sentenciaxreg;  //  @jve:decl-index=0:
	ResultSet resultado,resultadoven,resultadoxreg;  //  @jve:decl-index=0:
	configura modelo=new configura();
	static String codigofac=new String("");  //  @jve:decl-index=0:
	static boolean seleccionafac=false;
	Calendar fecha = Calendar.getInstance();  //  @jve:decl-index=0:
	private JPanel Panel1 = null;

	private JLabel LblFecha = null;

	private JTextField TxtFecha = null;

	private JLabel LblCliente = null;

	private JTextField TxtCliente = null;

	private JLabel LblVendedor = null;

	private JComboBox CboVendedor = null;

	private JPanel jPanel = null;

	/**
	 * @param owner
	 */
	public f_listafacturas() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
	this.setSize(827, 528);
	this.setResizable(false);
	this.setTitle("Lista de Facturas de Venta");
	this.setContentPane(getJContentPane());
	this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setModal(true);
	modelo.addColumn("NUMERO");
	modelo.addColumn("FECHA");
	modelo.addColumn("C L I E N T E");
	modelo.addColumn("V E N D E D O R");
	modelo.addColumn("TOTAL");
	modelo.addColumn("ANULADA");
	TableColumn columna1 = JTable.getColumn("NUMERO");
	columna1.setPreferredWidth(80);
	columna1.setMinWidth(80);
	columna1.setMaxWidth(80);
	TableColumn columna2 = JTable.getColumn("FECHA");
	columna2.setPreferredWidth(80);
	columna2.setMinWidth(80);
	columna2.setMaxWidth(80);
	TableColumn columna3 = JTable.getColumn("C L I E N T E");
	columna3.setPreferredWidth(240);
	columna3.setMinWidth(240);
	columna3.setMaxWidth(240);
	TableColumn columna4 = JTable.getColumn("V E N D E D O R");
	columna4.setPreferredWidth(240);
	columna4.setMinWidth(240);
	columna4.setMaxWidth(240);
	TableColumn columna5 = JTable.getColumn("TOTAL");
	columna5.setPreferredWidth(80);
	columna5.setMinWidth(80);
	columna5.setMaxWidth(80);
	JTable.isCellEditable(JTable.getSelectedRow(), JTable.getSelectedColumn());
	conectar();
}

/**
 * This method initializes jContentPane
 *
 * @return javax.swing.JPanel
 */
private JPanel getJContentPane() {
	if (jContentPane == null) {
		LblVendedor = new JLabel();
		LblVendedor.setText("Vendedor:");
		LblVendedor.setBounds(new Rectangle(396, 32, 67, 23));
		LblCliente = new JLabel();
		LblCliente.setText("Cliente:");
		LblCliente.setBounds(new Rectangle(6, 33, 64, 23));
		LblFecha = new JLabel();
		LblFecha.setText("Fecha:");
		LblFecha.setBounds(new Rectangle(396, 6, 67, 23));
		LblNumero = new JLabel();
		LblNumero.setText("Número:");
		LblNumero.setBounds(new Rectangle(6, 6, 64, 23));
		LblNumero.setPreferredSize(new Dimension(48, 19));
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		jContentPane.add(getJScrollPane(), null);
		jContentPane.add(getPanel1(), null);
		jContentPane.add(getJPanel(), null);
	}
	return jContentPane;
}

/**
 * This method initializes BSeleccionar
 *
 * @return javax.swing.JButton
 */
private JButton getBSeleccionar() {
	if (BSeleccionar == null) {
		BSeleccionar = new JButton();
		BSeleccionar.setFont(new Font("Dialog", Font.PLAIN, 11));
		BSeleccionar.setIcon(new ImageIcon(getClass().getResource("/recursos/ok2.JPG")));
		BSeleccionar.setBounds(new Rectangle(7, 7, 130, 39));
		BSeleccionar.setText("Seleccionar");
		BSeleccionar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				seleccionar();
			}
		});
	}
	return BSeleccionar;
}

/**
 * This method initializes BCerrar
 *
 * @return javax.swing.JButton
 */
private JButton getBCerrar() {
	if (BCerrar == null) {
		BCerrar = new JButton();
		BCerrar.setFont(new Font("Dialog", Font.PLAIN, 11));
		BCerrar.setIcon(new ImageIcon(getClass().getResource("/recursos/CANCELAR2.JPG")));
		BCerrar.setBounds(new Rectangle(143, 7, 130, 39));
		BCerrar.setText("Cancelar");
		BCerrar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				seleccionafac=false;
				dispose();
			}
		});
	}
	return BCerrar;
}

/**
 * This method initializes TxtNumero
 *
 * @return javax.swing.JTextField
 */
private JTextField getTxtNumero() {
	if (TxtNumero == null) {
		TxtNumero = new JTextField();
		TxtNumero.setBounds(new Rectangle(81, 6, 112, 23));
		TxtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char caracter;
				   caracter=(e.getKeyChar()+"").charAt(0);
				   if ((caracter+"").matches("[0-9]"))
				      {e.setKeyChar(caracter);}
				   else
				      {e.consume();}
			}
		});
		TxtNumero.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent e) {
				ventaspornum();
			}
		});
		TxtNumero.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				limpiar();
			}
		});
	}
	return TxtNumero;
}

/**
 * This method initializes jScrollPane
 *
 * @return javax.swing.JScrollPane
 */
private JScrollPane getJScrollPane() {
	if (jScrollPane == null) {
		jScrollPane = new JScrollPane();
		jScrollPane.setBounds(new Rectangle(13, 84, 798, 345));
		jScrollPane.setFont(new Font("Arial Narrow", Font.PLAIN, 14));
		jScrollPane.setViewportView(getJTable());
	}
	return jScrollPane;
}

/**
 * This method initializes JTable
 *
 * @return javax.swing.JTable
 */
private JTable getJTable() {
	if (JTable == null) {
		JTable = new JTable(modelo);
		JTable.setFont(new Font("Dialog", Font.PLAIN, 11));
		JTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getClickCount() == 2){
				   seleccionar();
				}

			}
		});
	}
	return JTable;
}
public void conectar(){
	try
    {Class.forName("com.mysql.jdbc.Driver").newInstance();
     conexion=DriverManager.getConnection("jdbc:mysql://localhost/facturacion","root","");
     sentenciaven=conexion.createStatement();
	 sentenciaven.executeQuery("select concat(USUARIO_VEN,' ',NOMBRE_APELLIDO_VEN) as elvendedor from vendedores order by elvendedor");
	 resultadoven=sentenciaven.getResultSet();
	 cargarvendedores();
     sentencia=conexion.createStatement();
	 ventaspornum();
	}
    catch( Exception e )
    {JOptionPane.showMessageDialog(null,"¡Ha ocurrido un error al intentar acceder los datos de los Artículos!", "Error de acceso", 2);
     return;}
}

public class configura extends DefaultTableModel
{private static final long serialVersionUID = 1L;
public boolean isCellEditable (int row, int column)
{if (column == 1)
 return false;
 return false;
}
public Class getColumnClass(int columna)
{
if (columna == 4) return Integer.class;
if (columna == 5) return Boolean.class;
return Object.class;
}

}

private void actualizalista(){
	try {resultado=sentencia.getResultSet();
		 int xreg=modelo.getRowCount();
		 if (xreg>0){
			for (int x=1;x<=xreg;x++){
				modelo.removeRow(0);}
		 }
		 if (resultado.next()==true){
			 BSeleccionar.setEnabled(true);
			 resultado.first();
			 while (resultado.isAfterLast()==false)
			 {  Object[] fila = new Object[6]; // Se crea un array con 4 columnas para cada filas en la tabla
			    fila[0]=resultado.getString(1);
			    fila[1]=resultado.getString(2);
			    fila[2]=resultado.getString(3);
			    fila[3]=resultado.getString(4);
			    fila[4]=resultado.getString(5);
			    fila[5]=resultado.getBoolean(6);
			    modelo.addRow(fila);// Se crea la fila y se pasa los datos del object.
	 	        resultado.next();
	 		  }
		 }
		 else
		 {BSeleccionar.setEnabled(false);}
	    }
	catch(Exception m)
	    {JOptionPane.showMessageDialog(null,"error");}
}
private void seleccionar(){
	if (JTable.isCellSelected(JTable.getSelectedRow(), 0)==false){
		JOptionPane.showMessageDialog(null,"�Seleccione una Factura de la lista!","Error de selecci�n",2);
	}
	else {
		codigofac=JTable.getValueAt(JTable.getSelectedRow(), 0).toString();
		seleccionafac=true;
		dispose();
	}
}

/**
 * This method initializes Panel1
 *
 * @return javax.swing.JPanel
 */
private JPanel getPanel1() {
	if (Panel1 == null) {
		Panel1 = new JPanel();
		Panel1.setLayout(null);
		Panel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		Panel1.setLocation(new Point(530, 437));
		Panel1.setSize(new Dimension(280, 52));
		Panel1.add(getBSeleccionar(), null);
		Panel1.add(getBCerrar(), null);
	}
	return Panel1;
}

/**
 * This method initializes TxtFecha
 *
 * @return javax.swing.JTextField
 */
private JTextField getTxtFecha() {
	if (TxtFecha == null) {
		TxtFecha = new JTextField();
		TxtFecha.setBounds(new Rectangle(469, 6, 112, 23));
		TxtFecha.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				limpiar();
				cargarfecha();
				ventasporfec();
			}
		});
		TxtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char caracter;
				   caracter=(e.getKeyChar()+"").charAt(0);
				   if ((caracter+"").matches("[0-9/]"))
				      {e.setKeyChar(caracter);}
				   else
				      {e.consume();}
			}
		});
		TxtFecha.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (FechaValida(TxtFecha.getText())==false || TxtFecha.getText().length()>10 ){
					JOptionPane.showMessageDialog(null,"�Formato de fecha incorrecto! Use el formato DIA/MES/anio (25/05/2011)","Fecha incorrecta",2);
					cargarfecha();
				}
				ventasporfec();
			}
		});
	}
	return TxtFecha;
}

/**
 * This method initializes TxtCliente
 *
 * @return javax.swing.JTextField
 */
private JTextField getTxtCliente() {
	if (TxtCliente == null) {
		TxtCliente = new JTextField();
		TxtCliente.setBounds(new Rectangle(81, 33, 300, 23));
		TxtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char caracter;
				   caracter=(e.getKeyChar()+"").toUpperCase().charAt(0);
				   if ((caracter+"").matches("[A-Z ]"))
				      {e.setKeyChar(caracter);}
				   else
				      {e.consume();}
			}
		});
		TxtCliente.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent e) {
				ventasporcli();
			}
		});
		TxtCliente.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				limpiar();
			}
		});
	}
	return TxtCliente;
}

/**
 * This method initializes CboVendedor
 *
 * @return javax.swing.JComboBox
 */
private JComboBox getCboVendedor() {
	if (CboVendedor == null) {
		CboVendedor = new JComboBox();
		CboVendedor.setBounds(new Rectangle(469, 33, 322, 23));
		CboVendedor.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				ventasporven();
			}
		});
		CboVendedor.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				limpiar();
			}
		});
	}
	return CboVendedor;
}
public void ventaspornum(){
  try{
	sentencia.executeQuery("select facv_cab.num_facv,DATE_FORMAT(facv_cab.fecha,'%d/%m/%Y') as fecha,concat(clientes.RAZON_SOCIAL_CLI,' ',clientes.NOMBRE_APELLIDO_CLI) as elcliente,concat(vendedores.USUARIO_VEN,' ',vendedores.NOMBRE_APELLIDO_VEN) as elvendedor,"+
            "(facv_cab.sub_gen-facv_cab.tot_des+facv_cab.tot_iva) as total,anulada from facv_cab inner join clientes on(facv_cab.id_cli=clientes.id_cli) inner join vendedores on(facv_cab.id_ven=vendedores.id_ven) "+
            "where num_facv like '%"+TxtNumero.getText()+"%' order by num_facv");
    actualizalista();
  }
  catch(Exception e){}
}
public void ventasporcli(){
	  try{
		sentencia.executeQuery("select facv_cab.num_facv,DATE_FORMAT(facv_cab.fecha,'%d/%m/%Y') as fecha,concat(clientes.RAZON_SOCIAL_CLI,' ',clientes.NOMBRE_APELLIDO_CLI) as elcliente,concat(vendedores.USUARIO_VEN,' ',vendedores.NOMBRE_APELLIDO_VEN) as elvendedor,"+
	            "(facv_cab.sub_gen-facv_cab.tot_des+facv_cab.tot_iva) as total,anulada from facv_cab inner join clientes on(facv_cab.id_cli=clientes.id_cli) inner join vendedores on(facv_cab.id_ven=vendedores.id_ven) "+
	            "where concat(clientes.RAZON_SOCIAL_CLI,' ',clientes.NOMBRE_APELLIDO_CLI) like '"+TxtCliente.getText()+"%' order by num_facv");
	    actualizalista();
	  }
	  catch(Exception e){}
	}
public void ventasporven(){
	  try{
		sentencia.executeQuery("select facv_cab.num_facv,DATE_FORMAT(facv_cab.fecha,'%d/%m/%Y') as fecha,concat(clientes.RAZON_SOCIAL_CLI,' ',clientes.NOMBRE_APELLIDO_CLI) as elcliente,concat(vendedores.USUARIO_VEN,' ',vendedores.NOMBRE_APELLIDO_VEN) as elvendedor,"+
	            "(facv_cab.sub_gen-facv_cab.tot_des+facv_cab.tot_iva) as total,anulada from facv_cab inner join clientes on(facv_cab.id_cli=clientes.id_cli) inner join vendedores on(facv_cab.id_ven=vendedores.id_ven) "+
	            "where concat(vendedores.USUARIO_VEN,' ',vendedores.NOMBRE_APELLIDO_VEN) like '"+ CboVendedor.getSelectedItem() +"%' order by num_facv");
	    actualizalista();
	  }
	  catch(Exception e){}
	}
public void ventasporfec(){
	  try{
		sentencia.executeQuery("select facv_cab.num_facv,DATE_FORMAT(facv_cab.fecha,'%d/%m/%Y') as fecha,concat(clientes.RAZON_SOCIAL_CLI,' ',clientes.NOMBRE_APELLIDO_CLI) as elcliente,concat(vendedores.USUARIO_VEN,' ',vendedores.NOMBRE_APELLIDO_VEN) as elvendedor,"+
	            "(facv_cab.sub_gen-facv_cab.tot_des+facv_cab.tot_iva) as total,anulada from facv_cab inner join clientes on(facv_cab.id_cli=clientes.id_cli) inner join vendedores on(facv_cab.id_ven=vendedores.id_ven) "+
	            "where DATE_FORMAT(facv_cab.fecha,'%d/%m/%Y')= '"+ TxtFecha.getText() +"' order by num_facv");
	    actualizalista();
	  }
	  catch(Exception e){}
	}
public void cargarvendedores(){
	try{
		sentenciaxreg=conexion.createStatement();
		sentenciaxreg.executeQuery("select count(id_ven) as cuantos from vendedores");
		resultadoxreg=sentenciaxreg.getResultSet();
		resultadoxreg.last();
		resultadoven.first();
		for (int xreg=1;xreg<=resultadoxreg.getInt("cuantos");xreg++){
			CboVendedor.addItem(resultadoven.getObject("elvendedor"));
			resultadoven.next();
		}
		CboVendedor.setSelectedIndex(-1);
	}
	catch(Exception s){}
}
public void limpiar(){
	TxtNumero.setText("");
	TxtCliente.setText("");
	TxtFecha.setText("");
	CboVendedor.setSelectedIndex(-1);
}
private void cargarfecha(){
	String fechactual=new String("");
	String dia=new String(String.valueOf(fecha.get(fecha.DAY_OF_MONTH)));
	if (dia.length()==1)
	   {dia="0"+dia;}
	String mes=new String(String.valueOf(fecha.get(fecha.MONTH)+1));
	if (mes.length()==1)
	   {mes="0"+mes;}
	String anio=new String(String.valueOf(fecha.get(fecha.YEAR)));
	fechactual=dia+"/"+mes+"/"+anio;
  TxtFecha.setText(fechactual);
}
private static boolean FechaValida(String fechax) {
	  try {SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	       formatoFecha.setLenient(false);
	       formatoFecha.parse(fechax);}
	  catch(ParseException e)
	      {return false;}
	  return true;
	}

/**
 * This method initializes jPanel
 *
 * @return javax.swing.JPanel
 */
private JPanel getJPanel() {
	if (jPanel == null) {
		jPanel = new JPanel();
		jPanel.setLayout(null);
		jPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		jPanel.setSize(new Dimension(797, 61));
		jPanel.setLocation(new Point(13, 11));
		jPanel.add(LblNumero, null);
		jPanel.add(getTxtNumero(), null);
		jPanel.add(LblCliente, null);
		jPanel.add(getTxtCliente(), null);
		jPanel.add(LblFecha, null);
		jPanel.add(getTxtFecha(), null);
		jPanel.add(LblVendedor, null);
		jPanel.add(getCboVendedor(), null);
	}
	return jPanel;
}
}  //  @jve:decl-index=0:visual-constraint="10,10"

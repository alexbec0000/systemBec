package appFactura;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Point;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.awt.event.KeyEvent;

public class f_clientes extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel31 = null;

	private JLabel jLabel32 = null;

	private JLabel jLabel33 = null;

	private JLabel jLabel34 = null;

	private JTextField txtCodigo = null;

	private JTextField txtNombre = null;

	private JTextField txtRazonSocial = null;

	private JTextField txtDireccion = null;

	private JTextField txtCedula = null;

	private JTextField txtTelefono = null;

	private JTextField txtFecha = null;

	private JTextField txtEmail = null;

	private JLabel jLabel311 = null;

	private JComboBox cboSexo = null;

	private JLabel jLabel321 = null;

	private JTextField txtCelular = null;

	private JLabel jLabel3211 = null;

	private JTextField txtSaldo = null;

	private JPanel jPanel = null;

	private JButton bNuevo = null;

	private JButton bModificar = null;

	private JButton bGuardar = null;

	private JButton bCancelar = null;

	private JButton bEliminar = null;

	private JButton bAnterior = null;

	private JButton bBuscar = null;

	private JButton bSiguiente = null;

	private JButton bUltimo = null;

	private JButton bSalir = null;

	private JLabel jLabel4 = null;
	Connection conexion;  //  @jve:decl-index=0:
	Statement sentencia,sentenciaventas,sentenciaxreg;  //  @jve:decl-index=0:
	ResultSet resultado,resultadoventas,resultadoxreg;
    int xregistros;
    boolean nuevoreg;
    Calendar lafecha = Calendar.getInstance();  //  @jve:decl-index=0:
    String elcodigo= new String("");  //  @jve:decl-index=0:
    private JPanel jPanel1 = null;

	private JButton bPrimero = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel5 = null;



	/**
	 * This method initializes txtNombre
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setEnabled(false);
			txtNombre.setSize(new Dimension(452, 24));
			txtNombre.setDisabledTextColor(Color.darkGray);
			txtNombre.setPreferredSize(new Dimension(4, 24));
			txtNombre.setLocation(new Point(131, 66));
			txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (txtNombre.getText().length()<30)
					   {char letra;
					    letra=(e.getKeyChar()+"").toUpperCase().charAt(0);
					    e.setKeyChar(letra);}
					else
					   {e.consume();}
				}
			});
			txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					txtNombre.setText(txtNombre.getText().trim());
				}
			});
		}
		return txtNombre;
	}

	/**
	 * This method initializes txtRazonSocial
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtApellido() {
		if (txtRazonSocial == null) {
			txtRazonSocial = new JTextField();
			txtRazonSocial.setEnabled(false);
			txtRazonSocial.setSize(new Dimension(452, 24));
			txtRazonSocial.setDisabledTextColor(Color.darkGray);
			txtRazonSocial.setLocation(new Point(131, 95));
			txtRazonSocial.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (txtRazonSocial.getText().length()<30)
					   {char letra;
					    letra=(e.getKeyChar()+"").toUpperCase().charAt(0);
					    e.setKeyChar(letra);}
					else
					   {e.consume();}
				}
			});
		}
		return txtRazonSocial;
	}

	/**
	 * This method initializes txtDireccion
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtDireccion() {
		if (txtDireccion == null) {
			txtDireccion = new JTextField();
			txtDireccion.setEnabled(false);
			txtDireccion.setSize(new Dimension(572, 24));
			txtDireccion.setDisabledTextColor(Color.darkGray);
			txtDireccion.setLocation(new Point(131, 124));
			txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (txtDireccion.getText().length()<100)
					   {char letra;
					    letra=(e.getKeyChar()+"").toUpperCase().charAt(0);
					    e.setKeyChar(letra);}
					else
					   {e.consume();}
				}
			});
		}
		return txtDireccion;
	}

	/**
	 * This method initializes txtCedula
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtCedula() {
		if (txtCedula == null) {
			txtCedula = new JTextField();
			txtCedula.setEnabled(false);
			txtCedula.setSize(new Dimension(123, 24));
			txtCedula.setDisabledTextColor(Color.darkGray);
			txtCedula.setLocation(new Point(131, 154));
			txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (txtCedula.getText().length()<13)
				       {char caracter;
					    caracter=e.getKeyChar();
						if ((caracter+"").matches("[0-9]"))
				            {e.setKeyChar(caracter);}
				        else
				             {e.consume();}
				    	}
				    else
				        {e.consume();}
				}
			});
			txtCedula.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (txtCedula.getText().length()!=10 & txtCedula.getText().length()!=13){
						JOptionPane.showMessageDialog(null,"Ingrese 10 n�meros para la C�dula o 13 para el RUC","Error de ingreso",2);
						txtCedula.setText("");
					}
				}
			});
		}
		return txtCedula;
	}

	/**
	 * This method initializes txtTelefono
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setEnabled(false);
			txtTelefono.setSize(new Dimension(123, 24));
			txtTelefono.setDisabledTextColor(Color.darkGray);
			txtTelefono.setLocation(new Point(131, 184));
			txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (txtTelefono.getText().length()<9)
				       {char caracter;
					    caracter=e.getKeyChar();
						if ((caracter+"").matches("[0-9]"))
				            {e.setKeyChar(caracter);}
				        else
				             {e.consume();}
				    	}
				    else
				        {e.consume();}
				}
			});
		}
		return txtTelefono;
	}

	/**
	 * This method initializes txtFecha
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setEnabled(false);
			txtFecha.setSize(new Dimension(123, 24));
			txtFecha.setDisabledTextColor(Color.darkGray);
			txtFecha.setLocation(new Point(131, 213));
			txtFecha.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (FechaValida(txtFecha.getText())==false)
						{JOptionPane.showMessageDialog(null,"�Error en formato de fecha! Use correctamente el formato DIA/MES/anio (25/05/2012)","Fecha incorrecta",3);
						cargarfecha();
						txtFecha.requestFocus();
					    }
					else
						{if (!txtFecha.getText().substring(2,3).equals("/"))
						    {txtFecha.setText("0"+txtFecha.getText());
						    }
						 String anterior=new String(txtFecha.getText());
	  				     if (!txtFecha.getText().substring(5,6).equals("/"))
					        {txtFecha.setText(anterior.substring(0,3)+"0"+anterior.substring(3));
					        }
	  				     if (txtFecha.getText().length()<10)
	  				        {JOptionPane.showMessageDialog(null,"�Error en formato de fecha! Use correctamente el formato DIA/MES/anio (25/05/2012)","Fecha incorrecta",3);
							 cargarfecha();
							 txtFecha.requestFocus();
	  				    	}
					    }
				}
			});
			txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (txtFecha.getText().length()<10)
				       {char caracter;
					    caracter=e.getKeyChar();
						if ((caracter+"").matches("[0-9/]"))
				            {e.setKeyChar(caracter);}
				        else
				             {e.consume();}
				    	}
				    else
				        {e.consume();}

				}
			});
		}
		return txtFecha;
	}

	/**
	 * This method initializes txtEmail
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtEmail() {
		if (txtEmail == null) {
			txtEmail = new JTextField();
			txtEmail.setEnabled(false);
			txtEmail.setSize(new Dimension(572, 24));
			txtEmail.setDisabledTextColor(Color.darkGray);
			txtEmail.setLocation(new Point(131, 243));
			txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (txtEmail.getText().length()>=100)
					   {e.consume();}
				}
			});
		}
		return txtEmail;
	}

	/**
	 * This method initializes cboSexo
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCboSexo() {
		if (cboSexo == null) {
			cboSexo = new JComboBox();
			cboSexo.setEnabled(false);
			cboSexo.setSize(new Dimension(122, 24));
			cboSexo.setLocation(new Point(580, 154));
			cboSexo.addItem("MASCULINO");
			cboSexo.addItem("FEMENINO");
		}
		return cboSexo;
	}

	/**
	 * This method initializes txtCelular
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtCelular() {
		if (txtCelular == null) {
			txtCelular = new JTextField();
			txtCelular.setEnabled(false);
			txtCelular.setSize(new Dimension(123, 24));
			txtCelular.setDisabledTextColor(Color.darkGray);
			txtCelular.setLocation(new Point(580, 184));
			txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (txtCelular.getText().length()<9)
				       {char caracter;
					    caracter=e.getKeyChar();
						if ((caracter+"").matches("[0-9]"))
				            {e.setKeyChar(caracter);}
				        else
				             {e.consume();}
				    	}
				    else
				        {e.consume();}
				}
			});
		}
		return txtCelular;
	}

	/**
	 * This method initializes txtSaldo
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtSaldo() {
		if (txtSaldo == null) {
			txtSaldo = new JTextField();
			txtSaldo.setEnabled(false);
			txtSaldo.setSize(new Dimension(123, 24));
			txtSaldo.setDisabledTextColor(Color.darkGray);
			txtSaldo.setHorizontalAlignment(JTextField.RIGHT);
			txtSaldo.setLocation(new Point(580, 213));
		}
		return txtSaldo;
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
			jPanel.setBounds(new Rectangle(8, 291, 705, 56));
			jPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jPanel.add(getBNuevo(), null);
			jPanel.add(getBModificar(), null);
			jPanel.add(getBGuardar(), null);
			jPanel.add(getBCancelar(), null);
			jPanel.add(getBEliminar(), null);
			jPanel.add(getBSalir(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes bNuevo
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBNuevo() {
		if (bNuevo == null) {
			bNuevo = new JButton();
			bNuevo.setToolTipText("Nuevo registro de Cliente");
			bNuevo.setLocation(new Point(7, 8));
			bNuevo.setSize(new Dimension(112, 41));
			bNuevo.setText("   Nuevo");
			bNuevo.setFont(new Font("Dialog", Font.PLAIN, 11));
			bNuevo.setMnemonic(KeyEvent.VK_N);
			bNuevo.setIcon(new ImageIcon(getClass().getResource("/recursos/NUEVO.JPG")));
			bNuevo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					nuevoreg=true;
					limpiar();
					generacodigo();
					habilitanuemod();
					txtNombre.requestFocus();

				}
			});
		}
		return bNuevo;
	}

	/**
	 * This method initializes bModificar
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBModificar() {
		if (bModificar == null) {
			bModificar = new JButton();
			bModificar.setToolTipText("Modificar datos de Cliente");
			bModificar.setSize(new Dimension(112, 41));
			bModificar.setLocation(new Point(123, 8));
			bModificar.setText("Modificar");
			bModificar.setFont(new Font("Dialog", Font.PLAIN, 11));
			bModificar.setMnemonic(KeyEvent.VK_M);
			bModificar.setIcon(new ImageIcon(getClass().getResource("/recursos/MODIFICAR.JPG")));
			bModificar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					nuevoreg=false;
					habilitanuemod();
					txtNombre.requestFocus();
				}
			});
		}
		return bModificar;
	}

	/**
	 * This method initializes bGuardar
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBGuardar() {
		if (bGuardar == null) {
			bGuardar = new JButton();
			bGuardar.setToolTipText("Guardar datos de Cliente");
			bGuardar.setSize(new Dimension(112, 41));
			bGuardar.setLocation(new Point(239, 8));
			bGuardar.setEnabled(false);
			bGuardar.setFont(new Font("Dialog", Font.PLAIN, 11));
			bGuardar.setText(" Guardar");
			bGuardar.setMnemonic(KeyEvent.VK_G);
			bGuardar.setIcon(new ImageIcon(getClass().getResource("/recursos/GUARDAR2.JPG")));
			bGuardar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (txtNombre.getText().trim().length()==0){
						JOptionPane.showMessageDialog(null,"�Ingrese los Nombres del Cliente!", "Error de almacenamiento", 2);
						txtNombre.requestFocus();
					}
					else if(txtRazonSocial.getText().trim().length()==0){
						JOptionPane.showMessageDialog(null,"�Ingrese los Apellidos del Cliente!", "Error de almacenamiento", 2);
						txtRazonSocial.requestFocus();
					}
					else if(txtDireccion.getText().trim().length()==0){
						JOptionPane.showMessageDialog(null,"�Ingrese la Direcci�n del Cliente!", "Error de almacenamiento", 2);
						txtDireccion.requestFocus();
					}
					else if(txtCedula.getText().trim().length()==0){
						JOptionPane.showMessageDialog(null,"�Ingrese la C�dula o RUC del Cliente!", "Error de almacenamiento", 2);
						txtCedula.requestFocus();
					}
					else if(cboSexo.getSelectedIndex()==-1){
						JOptionPane.showMessageDialog(null,"�Ingrese el Sexo del Cliente!", "Error de almacenamiento", 2);
						cboSexo.requestFocus();
					}
					else if(txtFecha.getText().trim().length()==0){
						JOptionPane.showMessageDialog(null,"�Ingrese la Fecha de ingreso del Cliente!", "Error de almacenamiento", 2);
						txtFecha.requestFocus();
					}
					else{
						guardar();
					}
				}
			});
		}
		return bGuardar;
	}

	/**
	 * This method initializes bCancelar
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBCancelar() {
		if (bCancelar == null) {
			bCancelar = new JButton();
			bCancelar.setToolTipText("Cancelar informaci�n");
			bCancelar.setSize(new Dimension(112, 41));
			bCancelar.setLocation(new Point(355, 8));
			bCancelar.setEnabled(false);
			bCancelar.setText("Cancelar");
			bCancelar.setFont(new Font("Dialog", Font.PLAIN, 11));
			bCancelar.setMnemonic(KeyEvent.VK_C);
			bCancelar.setIcon(new ImageIcon(getClass().getResource("/recursos/CANCELAR.JPG")));
			bCancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				  try{
					 int respuesta=JOptionPane.showConfirmDialog(null, "�Desea descartar los datos ingresados?", "Cancelar ingresos",0, 3);
				  	 if (respuesta==0){
						sentencia.executeQuery("select * from clientes order by id_cli");
						resultado=sentencia.getResultSet();
						habilitaguacan();
						if (resultado.next()==false)
						   {if (nuevoreg==false)
						       {JOptionPane.showMessageDialog(null,"�Los datos de este Cliente acaban de ser eliminados por otro usuario del sistema!","Cliente inexistente",0);
							   }
						    limpiar();
						    vacio();
						    }
						else
						   {if(nuevoreg==true)
						      {resultado.last();
						       actualizar();
						      }
						   else
						   {elcodigo=txtCodigo.getText();
							encuentra();
						   }
						 }
						}
				     }
				  catch(Exception s){}
				}
			});
		}
		return bCancelar;
	}

	/**
	 * This method initializes bEliminar
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBEliminar() {
		if (bEliminar == null) {
			bEliminar = new JButton();
			bEliminar.setToolTipText("Eliminar registro de Cliente");
			bEliminar.setSize(new Dimension(112, 41));
			bEliminar.setLocation(new Point(471, 8));
			bEliminar.setFont(new Font("Dialog", Font.PLAIN, 11));
			bEliminar.setText("  Eliminar");
			bEliminar.setMnemonic(KeyEvent.VK_E);
			bEliminar.setIcon(new ImageIcon(getClass().getResource("/recursos/ELIMINAR.JPG")));
			bEliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int respuesta=JOptionPane.showConfirmDialog(null, "�Desea eliminar el registro del Cliente "+txtNombre.getText().trim()+" "+txtRazonSocial.getText()+"?", "Eliminar cliente",0, 3);
					if (respuesta==0){
						eliminar();
					}
				}
			});
		}
		return bEliminar;
	}

	/**
	 * This method initializes bAnterior
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBAnterior() {
		if (bAnterior == null) {
			bAnterior = new JButton();
			bAnterior.setToolTipText("Anterior Cliente");
			bAnterior.setLocation(new Point(45, 6));
			bAnterior.setSize(new Dimension(34, 34));
			bAnterior.setIcon(new ImageIcon(getClass().getResource("/recursos/ANTERIOR.JPG")));
			bAnterior.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try{
						if (resultado.isBeforeFirst()==false){
							resultado.previous();
							if (resultado.isBeforeFirst()==true)
						       {resultado.first();}
							actualizar();
						}
					}
					catch(Exception p){}
				}
			});
		}
		return bAnterior;
	}

	/**
	 * This method initializes bBuscar
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBBuscar() {
		if (bBuscar == null) {
			bBuscar = new JButton();
			bBuscar.setToolTipText("Buscar Cliente");
			bBuscar.setLocation(new Point(85, 6));
			bBuscar.setSize(new Dimension(34, 34));
			bBuscar.setIcon(new ImageIcon(getClass().getResource("/recursos/BUSCAR.JPG")));
			bBuscar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new f_listaclientes().setVisible(true);
					if (f_listaclientes.seleccionacli==true){
					    elcodigo=f_listaclientes.codigocli;
						encuentra();}
				}
			});
		}
		return bBuscar;
	}

	/**
	 * This method initializes bSiguiente
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBSiguiente() {
		if (bSiguiente == null) {
			bSiguiente = new JButton();
			bSiguiente.setToolTipText("Siguiente Cliente");
			bSiguiente.setLocation(new Point(124, 6));
			bSiguiente.setSize(new Dimension(34, 34));
			bSiguiente.setIcon(new ImageIcon(getClass().getResource("/recursos/SIGUIENTE.JPG")));
			bSiguiente.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try{
						if (resultado.isAfterLast()==false){
							resultado.next();
							if (resultado.isAfterLast()==true)
						       {resultado.last();}
							actualizar();
						}
					}
					catch(Exception p){}
				}
			});
		}
		return bSiguiente;
	}

	/**
	 * This method initializes bUltimo
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBUltimo() {
		if (bUltimo == null) {
			bUltimo = new JButton();
			bUltimo.setToolTipText("Ultimo Cliente");
			bUltimo.setLocation(new Point(163, 6));
			bUltimo.setSize(new Dimension(34, 34));
			bUltimo.setIcon(new ImageIcon(getClass().getResource("/recursos/ULTIMO.JPG")));
			bUltimo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try{
						resultado.last();
						actualizar();
						}
					catch(Exception a){}
				}
			});
		}
		return bUltimo;
	}

	/**
	 * This method initializes bSalir
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBSalir() {
		if (bSalir == null) {
			bSalir = new JButton();
			bSalir.setToolTipText("Salir");
			bSalir.setSize(new Dimension(112, 41));
			bSalir.setLocation(new Point(587, 8));
			bSalir.setText("   Salir");
			bSalir.setFont(new Font("Dialog", Font.PLAIN, 11));
			bSalir.setMnemonic(KeyEvent.VK_S);
			bSalir.setIcon(new ImageIcon(getClass().getResource("/recursos/SALIR2.JPG")));
			bSalir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int resp=JOptionPane.showConfirmDialog(null,"�Desea salir del formulario?","Salir del formulario",0);
					if(resp==0)
						{dispose();}

				}
			});
		}
		return bSalir;
	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jPanel1.setSize(new Dimension(203, 46));
			jPanel1.setLocation(new Point(380, 8));
			jPanel1.add(getBPrimero(), null);
			jPanel1.add(getBAnterior(), null);
			jPanel1.add(getBBuscar(), null);
			jPanel1.add(getBSiguiente(), null);
			jPanel1.add(getBUltimo(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes bPrimero
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getBPrimero() {
		if (bPrimero == null) {
			bPrimero = new JButton();
			bPrimero.setIcon(new ImageIcon(getClass().getResource("/recursos/PRIMERO.JPG")));
			bPrimero.setLocation(new Point(6, 6));
			bPrimero.setSize(new Dimension(34, 34));
			bPrimero.setToolTipText("Primer Cliente");
			bPrimero.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try{
						resultado.first();
					    actualizar();
					   }
					catch(Exception f){}
				}
			});
		}
		return bPrimero;
	}

	/**
	 * This method initializes jPanel2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(13, 11, 64, 23));
			jLabel5.setText("CODIGO:");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jPanel2.setSize(new Dimension(372, 46));
			jPanel2.setLocation(new Point(10, 8));
			jPanel2.add(jLabel5, null);
			jPanel2.add(getTxtCodigo1(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes txtCodigo1
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtCodigo1() {
		if (txtCodigo == null) {
			txtCodigo = new JTextField();
			txtCodigo.setPreferredSize(new Dimension(4, 20));
			txtCodigo.setLocation(new Point(122, 10));
			txtCodigo.setSize(new Dimension(123, 24));
			txtCodigo.setDisabledTextColor(Color.darkGray);
			txtCodigo.setEnabled(false);
		}
		return txtCodigo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				f_clientes thisClass = new f_clientes();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public f_clientes() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(731, 395);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursos/GENTE.JPG")));
		this.setContentPane(getJContentPane());
		this.setTitle("Datos de los Clientes");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				System.out.println("windowOpened()"); // TODO Auto-generated Event stub windowOpened()
			}
		});
		this.setLocationRelativeTo(null);
		conectar();
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setIcon(new ImageIcon(getClass().getResource("/recursos/0242iconos-clubzonanokia.com.png")));
			jLabel4.setLocation(new Point(604, 11));
			jLabel4.setSize(new Dimension(89, 99));
			jLabel4.setText("");
			jLabel3211 = new JLabel();
			jLabel3211.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel3211.setLocation(new Point(501, 212));
			jLabel3211.setSize(new Dimension(67, 23));
			jLabel3211.setText("SALDO:");
			jLabel321 = new JLabel();
			jLabel321.setText("CELULAR:");
			jLabel321.setLocation(new Point(503, 184));
			jLabel321.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel321.setSize(new Dimension(65, 21));
			jLabel311 = new JLabel();
			jLabel311.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel311.setSize(new Dimension(44, 21));
			jLabel311.setLocation(new Point(523, 156));
			jLabel311.setText("SEXO:");
			jLabel34 = new JLabel();
			jLabel34.setText("E-MAIL:");
			jLabel34.setLocation(new Point(20, 244));
			jLabel34.setSize(new Dimension(91, 21));
			jLabel33 = new JLabel();
			jLabel33.setText("FECHA/INGRESO:");
			jLabel33.setLocation(new Point(20, 212));
			jLabel33.setSize(new Dimension(109, 23));
			jLabel32 = new JLabel();
			jLabel32.setText("TELEFONO:");
			jLabel32.setLocation(new Point(20, 184));
			jLabel32.setSize(new Dimension(83, 21));
			jLabel31 = new JLabel();
			jLabel31.setText("CEDULA/RUC:");
			jLabel31.setLocation(new Point(20, 156));
			jLabel31.setSize(new Dimension(85, 21));
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(20, 126, 90, 19));
			jLabel3.setText("DIRECCION:");
			jLabel2 = new JLabel();
			jLabel2.setText("RAZON SOCIAL:");
			jLabel2.setLocation(new Point(20, 95));
			jLabel2.setSize(new Dimension(77, 22));
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(20, 66, 78, 21));
			jLabel1.setText("NOMBRES:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel31, null);
			jContentPane.add(jLabel32, null);
			jContentPane.add(jLabel33, null);
			jContentPane.add(jLabel34, null);
			jContentPane.add(getTxtNombre(), null);
			jContentPane.add(getTxtApellido(), null);
			jContentPane.add(getTxtDireccion(), null);
			jContentPane.add(getTxtCedula(), null);
			jContentPane.add(getTxtTelefono(), null);
			jContentPane.add(getTxtFecha(), null);
			jContentPane.add(getTxtEmail(), null);
			jContentPane.add(jLabel311, null);
			jContentPane.add(getCboSexo(), null);
			jContentPane.add(jLabel321, null);
			jContentPane.add(getTxtCelular(), null);
			jContentPane.add(jLabel3211, null);
			jContentPane.add(getTxtSaldo(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJPanel1(), null);

			jContentPane.add(getJPanel2(), null);
		}
		return jContentPane;
	}
	public void conectar(){
		try
	    {Class.forName("com.mysql.jdbc.Driver").newInstance();
	     conexion=DriverManager.getConnection("jdbc:mysql://localhost/facturacion","root","");
		 sentencia=conexion.createStatement();
		 sentencia.executeQuery("SELECT * FROM CLIENTES ORDER BY ID_CLI");
		 resultado=sentencia.getResultSet();
		 if (resultado.next()==false)
		    {vacio();
		     xregistros=0;}
		 else
		    {xregistros=1;
			 resultado.last();
		     actualizar();
			 }
	    }
	    catch( Exception e )
	    {JOptionPane.showMessageDialog(null,"�Ha ocurrido un error al intentar acceder los datos del Cliente!", "Error de acceso", 2);
	     return;}
	}
	private void vacio(){
		bModificar.setEnabled(false);
		bEliminar.setEnabled(false);
		bAnterior.setEnabled(false);
		bSiguiente.setEnabled(false);
		bBuscar.setEnabled(false);
		bPrimero.setEnabled(false);
		bUltimo.setEnabled(false);
		bAnterior.setEnabled(false);
		bSiguiente.setEnabled(false);
		bBuscar.setEnabled(false);
		bPrimero.setEnabled(false);
		bUltimo.setEnabled(false);
	}
	private void actualizar(){
		try{
	 	 txtCodigo.setText(resultado.getString("id_cli"));
		 txtNombre.setText(resultado.getString("NOMBRE_APELLIDO_CLI"));
		 txtRazonSocial.setText(resultado.getString("RAZON_SOCIAL_CLI"));
		 txtDireccion.setText(resultado.getString("DIRECCION_CLI"));
		 txtCedula.setText(resultado.getString("CED_RUC_CLI"));
		 txtTelefono.setText(resultado.getString("TELEFONO_CLI"));
		 txtCelular.setText(resultado.getString("CELULAR_CLI"));
		 txtSaldo.setText(resultado.getString("SALDO_CLI"));
		 cboSexo.setSelectedItem(resultado.getString("SEXO_CLI"));
		 txtEmail.setText(resultado.getString("EMAIL_CLI"));
		 txtFecha.setText(resultado.getString("FECHA_INGRESO_CLI").substring(8)+"/"+resultado.getString("FECHA_INGRESO_CLI").substring(5,7)+"/"+resultado.getString("FECHA_INGRESO_CLI").substring(0,4));
		}
		catch (Exception m){
		}
	}
	public void generacodigo(){
		try{
			if (xregistros==0)
			   {txtCodigo.setText("CL0001"); }
			else{
			   resultado.last();
			   String numerocad=new String(resultado.getString("id_cli").substring(2));
			   String cadnuevo=String.valueOf(Integer.valueOf(numerocad)+1);
			   int x;
			   String ceros=new String("");
			   for (x=1;x<=4-cadnuevo.length();x++)
			       {ceros=ceros+"0";}
			   txtCodigo.setText("CL"+ceros+cadnuevo);
			   if (cadnuevo.length()==5)
			      {txtCodigo.setText("");
			      JOptionPane.showMessageDialog(null, "�No se puede generar el c�digo. Se ha superado la capacidad del sistema!","Desbordamiento de valores",0);
			      System.exit(0);}
			}
		}
		catch(Exception g){
		}
	}
	private void limpiar(){
		   txtCodigo.setText("");
		   txtNombre.setText("");
		   txtRazonSocial.setText("");
		   txtDireccion.setText("");
		   txtCedula.setText("");
		   txtTelefono.setText("");
		   txtCelular.setText("");
		   txtEmail.setText("");
		   txtSaldo.setText("0.00");
		   cboSexo.setSelectedIndex(-1);
		   txtFecha.setText("");
	   }
	public void habilitanuemod(){
		bNuevo.setEnabled(false);
		bModificar.setEnabled(false);
		bGuardar.setEnabled(true);
		bCancelar.setEnabled(true);
		bEliminar.setEnabled(false);
		bPrimero.setEnabled(false);
		bAnterior.setEnabled(false);
		bBuscar.setEnabled(false);
		bSiguiente.setEnabled(false);
		bUltimo.setEnabled(false);
		txtNombre.setEnabled(true);
		txtRazonSocial.setEnabled(true);
		txtDireccion.setEnabled(true);
		txtCedula.setEnabled(true);
		txtCelular.setEnabled(true);
		txtTelefono.setEnabled(true);
		cboSexo.setEnabled(true);
		txtFecha.setEnabled(true);
		cboSexo.setEnabled(true);
		txtEmail.setEnabled(true);
	}
	public static boolean FechaValida(String fechax) {
		  try {SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		       formatoFecha.setLenient(false);
		       formatoFecha.parse(fechax);}
		  catch(ParseException e)
		      {return false;}
		  return true;
		}

	public void cargarfecha(){
		String fechactual=new String("");
		String dia=new String(String.valueOf(lafecha.get(lafecha.DAY_OF_MONTH)));
		if (dia.length()==1)
		   {dia="0"+dia;}
		String mes=new String(String.valueOf(lafecha.get(lafecha.MONTH)+1));
		if (mes.length()==1)
		   {mes="0"+mes;}
		String anio=new String(String.valueOf(lafecha.get(lafecha.YEAR)));
		fechactual=dia+"/"+mes+"/"+anio;
	  txtFecha.setText(fechactual);
	}
	public void habilitaguacan(){
		bNuevo.setEnabled(true);
		bModificar.setEnabled(true);
		bGuardar.setEnabled(false);
		bCancelar.setEnabled(false);
		bEliminar.setEnabled(true);
		bPrimero.setEnabled(true);
		bAnterior.setEnabled(true);
		bBuscar.setEnabled(true);
		bSiguiente.setEnabled(true);
		bUltimo.setEnabled(true);
		txtNombre.setEnabled(false);
		txtRazonSocial.setEnabled(false);
		txtDireccion.setEnabled(false);
		txtCedula.setEnabled(false);
		txtCelular.setEnabled(false);
		txtTelefono.setEnabled(false);
		cboSexo.setEnabled(false);
		txtFecha.setEnabled(false);
		cboSexo.setEnabled(false);
		txtEmail.setEnabled(false);
	}
	public void eliminar(){
		  try{
			sentenciaventas=conexion.createStatement();
			sentenciaventas.executeQuery("SELECT COUNT(NUM_FACV) AS CUANTOS FROM FACV_CAB WHERE ID_CLI='"+txtCodigo.getText()+"'");
			resultadoventas=sentenciaventas.getResultSet();
			resultadoventas.last();
			if (resultadoventas.getInt("CUANTOS")>0)
			   {JOptionPane.showMessageDialog(null,"�Este Cliente no puede ser eliminado. Tiene registros vinculados en la Base de Datos!", "Error de eliminaci�n", 2);
			   }
			else
			   {sentencia=conexion.createStatement();
				sentencia.executeUpdate("DELETE FROM CLIENTES WHERE ID_CLI='"+txtCodigo.getText()+"'");
				JOptionPane.showMessageDialog(null,"�Los datos del Cliente han sido eliminados!", "Eliminaci�n de datos", 1);
				sentencia.executeQuery("SELECT * FROM CLIENTES ORDER BY ID_CLI");
				resultado=sentencia.getResultSet();
				if (resultado.next()==false)
				    {xregistros=0;
					limpiar();
					vacio();}
				else
				    {xregistros=1;
				     resultado.last();
				     actualizar();
					 }
			   }
			}
		  catch( Exception e ){
			JOptionPane.showMessageDialog(null,"�Ha ocurrido un error al intentar eliminar los datos del Cliente!", "Error de eliminaci�n", 2);
			return;
			}
		}
	private void guardar(){
		try
	    {String fechasql=new String(txtFecha.getText().substring(6)+"-"+txtFecha.getText().substring(3,5)+"-"+txtFecha.getText().substring(0,2));
		 sentencia=conexion.createStatement();
		 if (nuevoreg==true)
		 	{sentencia.executeUpdate("INSERT INTO CLIENTES (ID_CLI,NOMBRE_APELLIDO_CLI,RAZON_SOCIAL_CLI,DIRECCION_CLI,CED_RUC_CLI,TELEFONO_CLI,CELULAR_CLI,SEXO_CLI,FECHA_INGRESO_CLI,EMAIL_CLI,SALDO_CLI) VALUES('"+txtCodigo.getText()+
					                 "','"+txtNombre.getText()+"','"+txtRazonSocial.getText()+"','"+txtDireccion.getText()+"','"+txtCedula.getText()+"','"+txtTelefono.getText()+
					                 "','"+txtCelular.getText()+"','"+cboSexo.getSelectedItem()+"','"+fechasql+"','"+txtEmail.getText()+"',"+txtSaldo.getText()+")");
			 xregistros=1;}
		 else
		    {sentencia.executeUpdate("UPDATE CLIENTES SET NOMBRE_APELLIDO_CLI='"+txtNombre.getText()+"',RAZON_SOCIAL_CLI='"+txtRazonSocial.getText()+"',DIRECCION_CLI='"+txtDireccion.getText()+
		    		                 "',CED_RUC_CLI='"+txtCedula.getText()+"',TELEFONO_CLI='"+txtTelefono.getText()+"',CELULAR_CLI='"+txtCelular.getText()+
		    		                 "',SEXO_CLI='"+cboSexo.getSelectedItem()+"',FECHA_INGRESO_CLI='"+fechasql+"',EMAIL_CLI='"+txtEmail.getText()+"' WHERE ID_CLI='"+txtCodigo.getText()+"'");
		    }

		 sentencia.executeQuery("SELECT * FROM CLIENTES ORDER BY ID_CLI");
		 resultado=sentencia.getResultSet();
		 if (nuevoreg==true)
			{resultado.last();
			 actualizar();}
		 else
		    {elcodigo=txtCodigo.getText();
			 encuentra();}
		 JOptionPane.showMessageDialog(null,"�Los datos del Cliente han sido guardados!", "Almacenamiento de datos", 1);
		 habilitaguacan();
		 }
	    catch( Exception e )
	    { JOptionPane.showMessageDialog(null,"�Ha ocurrido un error al intentar almacenar los datos del Cliente!", "Error de almacenamiento", 2);
	     return;}
	}
	private void encuentra(){
		try{
			sentenciaxreg=conexion.createStatement();
			sentenciaxreg.executeQuery("SELECT COUNT(ID_CLI)AS CUANTOSCLI FROM CLIENTES");
			resultadoxreg=sentenciaxreg.getResultSet();
			resultadoxreg.last();
			resultado.first();
			for (int x=1;x<=resultadoxreg.getInt("CUANTOSCLI");x++){
				if (resultado.getString("id_cli").equals(elcodigo)==true){
					break;
				}
				resultado.next();
			}
			if (resultado.isAfterLast()==true)
			{  JOptionPane.showMessageDialog(null,"�El Cliente no pudo ser localizado!", "Error de b�squeda", 2);
			   resultado.last();
			}
			actualizar();
			}
		catch(Exception m){JOptionPane.showMessageDialog(null,"�El Cliente no pudo ser localizado!", "Error de b�squeda", 2);}
	}
}  //  FIN DEL FORMULARIO

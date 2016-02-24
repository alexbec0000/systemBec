package appFactura;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import pck_controller.ParametrosController;

import appFactura.f_clientes;
import appFactura.f_listaclientes;
import javax.swing.JCheckBox;

public class f_empresa extends JFrame {

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
    private JTextField txtNombreComercial = null;
    private JTextField txtRazonSocial = null;
    private JTextField txtDireccion = null;
    private JTextField txtCedula = null;
    private JTextField txtEstablecimiento = null;
    private JTextField txtPuntoEmision = null;
    private JTextField txtNContribuyente = null;
    private JLabel jLabel311 = null;
    private JLabel jLabel321 = null;
    private JLabel jLabel3211 = null;
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
    private JPanel jPanel1 = null;
    private JButton bPrimero = null;
    private JPanel jPanel2 = null;
    private JLabel jLabel5 = null;

    ResultSet resultado, resultadoventas, resultadoxreg;
    int xregistros;
    boolean nuevoreg;
    String elcodigo = new String("");  //  @jve:decl-index=0:
    private JCheckBox checkLlevaContabi;
    private JCheckBox checkProduccion;
    private JLabel label;
    private JTextField textClaveInterna;


    /**
     * This method initializes txtNombre
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTxtNombreComercial() {
        if (txtNombreComercial == null) {
            txtNombreComercial = new JTextField();
            txtNombreComercial.setEnabled(false);
            txtNombreComercial.setSize(new Dimension(452, 24));
            txtNombreComercial.setDisabledTextColor(Color.darkGray);
            txtNombreComercial.setPreferredSize(new Dimension(4, 24));
            txtNombreComercial.setLocation(new Point(131, 66));
            txtNombreComercial.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    if (txtNombreComercial.getText().length() < 30) {
                        char letra;
                        letra = (e.getKeyChar() + "").toUpperCase().charAt(0);
                        e.setKeyChar(letra);
                    } else {
                        e.consume();
                    }
                }
            });
            txtNombreComercial.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    txtNombreComercial.setText(txtNombreComercial.getText().trim());
                }
            });
        }
        return txtNombreComercial;
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
                    if (txtRazonSocial.getText().length() < 30) {
                        char letra;
                        letra = (e.getKeyChar() + "").toUpperCase().charAt(0);
                        e.setKeyChar(letra);
                    } else {
                        e.consume();
                    }
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
                    if (txtDireccion.getText().length() < 100) {
                        char letra;
                        letra = (e.getKeyChar() + "").toUpperCase().charAt(0);
                        e.setKeyChar(letra);
                    } else {
                        e.consume();
                    }
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
                    if (txtCedula.getText().length() < 13) {
                        char caracter;
                        caracter = e.getKeyChar();
                        if ((caracter + "").matches("[0-9]")) {
                            e.setKeyChar(caracter);
                        } else {
                            e.consume();
                        }
                    } else {
                        e.consume();
                    }
                }
            });
            txtCedula.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (txtCedula.getText().length() != 10 & txtCedula.getText().length() != 13) {
                        JOptionPane.showMessageDialog(null, "Ingrese 10 numeros para la Cedula o 13 para el RUC", "Error de ingreso", 2);
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
    private JTextField getTxtEstablecimiento() {
        if (txtEstablecimiento == null) {
            txtEstablecimiento = new JTextField();
            txtEstablecimiento.setEnabled(false);
            txtEstablecimiento.setSize(new Dimension(123, 24));
            txtEstablecimiento.setDisabledTextColor(Color.darkGray);
            txtEstablecimiento.setLocation(new Point(131, 184));
            txtEstablecimiento.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    if (txtEstablecimiento.getText().length() < 9) {
                        char caracter;
                        caracter = e.getKeyChar();
                        if ((caracter + "").matches("[0-9]")) {
                            e.setKeyChar(caracter);
                        } else {
                            e.consume();
                        }
                    } else {
                        e.consume();
                    }
                }
            });
        }
        return txtEstablecimiento;
    }

    /**
     * This method initializes txtFecha
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTxtPuntoEmision() {
        if (txtPuntoEmision == null) {
            txtPuntoEmision = new JTextField();
            txtPuntoEmision.setEnabled(false);
            txtPuntoEmision.setSize(new Dimension(123, 24));
            txtPuntoEmision.setDisabledTextColor(Color.darkGray);
            txtPuntoEmision.setLocation(new Point(131, 213));
            txtPuntoEmision.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    if (txtPuntoEmision.getText().length() < 9) {
                        char caracter;
                        caracter = e.getKeyChar();
                        if ((caracter + "").matches("[0-9]")) {
                            e.setKeyChar(caracter);
                        } else {
                            e.consume();
                        }
                    } else {
                        e.consume();
                    }
                }
            });
        }
        return txtPuntoEmision;
    }

    /**
     * This method initializes txtEmail
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTxtNContribuyente() {
        if (txtNContribuyente == null) {
            txtNContribuyente = new JTextField();
            txtNContribuyente.setEnabled(false);
            txtNContribuyente.setSize(new Dimension(123, 24));
            txtNContribuyente.setDisabledTextColor(Color.darkGray);
            txtNContribuyente.setLocation(new Point(131, 243));
            txtNContribuyente.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    if (txtNContribuyente.getText().length() < 9) {
                        char caracter;
                        caracter = e.getKeyChar();
                        if ((caracter + "").matches("[0-9]")) {
                            e.setKeyChar(caracter);
                        } else {
                            e.consume();
                        }
                    } else {
                        e.consume();
                    }
                }
            });
        }
        return txtNContribuyente;
    }
    
    private JTextField getTxtClaveInterna() {
        if (textClaveInterna == null) {
        	textClaveInterna = new JTextField();
            textClaveInterna.setSize(new Dimension(123, 24));
            textClaveInterna.setLocation(new Point(131, 154));
            textClaveInterna.setEnabled(false);
            textClaveInterna.setDisabledTextColor(Color.DARK_GRAY);
            textClaveInterna.setBounds(424, 242, 123, 24);
            textClaveInterna.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    if (textClaveInterna.getText().length() < 9) {
                        char caracter;
                        caracter = e.getKeyChar();
                        if ((caracter + "").matches("[0-9]")) {
                            e.setKeyChar(caracter);
                        } else {
                            e.consume();
                        }
                    } else {
                        e.consume();
                    }
                }
            });
            
        }
        return textClaveInterna;
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
            bNuevo.setIcon(new ImageIcon(getClass().getResource("/recursos/NUEVO.png")));
            bNuevo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    nuevoreg = true;
                    limpiar();
                    generacodigo();
                    habilitanuemod();
                    txtNombreComercial.requestFocus();

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
                    nuevoreg = false;
                    habilitanuemod();
                    txtNombreComercial.requestFocus();
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
            bGuardar.setIcon(new ImageIcon(getClass().getResource("/recursos/save.png")));
            bGuardar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (txtNombreComercial.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "¡Ingrese el nombre comercial de la empresa!", "Error de almacenamiento", 2);
                        txtNombreComercial.requestFocus();
                    } else if (txtRazonSocial.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "¡Ingrese la razon social de la empresa!", "Error de almacenamiento", 2);
                        txtRazonSocial.requestFocus();
                    } else if (txtDireccion.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "¡Ingrese la Direccion de la empresa!", "Error de almacenamiento", 2);
                        txtDireccion.requestFocus();
                    } else if (txtCedula.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "¡Ingrese el RUC de la empresa!", "Error de almacenamiento", 2);
                        txtCedula.requestFocus(); 
                    } else if (textClaveInterna.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "¡Ingrese la clave interna de la empresa!", "Error de almacenamiento", 2);
                        textClaveInterna.requestFocus();
                    } else if (txtEstablecimiento.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "¡Ingrese el numero de establecimiento de la empresa!", "Error de almacenamiento", 2);
                        txtEstablecimiento.requestFocus();
                    } else if (txtPuntoEmision.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "¡Ingrese el punto de emision de la empresa!", "Error de almacenamiento", 2);
                        txtPuntoEmision.requestFocus();
                    } else {
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
            bCancelar.setToolTipText("Cancelar informacion");
            bCancelar.setSize(new Dimension(112, 41));
            bCancelar.setLocation(new Point(355, 8));
            bCancelar.setEnabled(false);
            bCancelar.setText("Cancelar");
            bCancelar.setFont(new Font("Dialog", Font.PLAIN, 11));
            bCancelar.setMnemonic(KeyEvent.VK_C);
            bCancelar.setIcon(new ImageIcon(getClass().getResource("/recursos/CANCELAR.JPG")));
            bCancelar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea descartar los datos ingresados?", "Cancelar ingresos", 0, 3);
                        if (respuesta == 0) {
                            resultado = ParametrosController.obtenerEmpresa();
                            habilitaguacan();
                            if (resultado.next() == false) {
                                if (nuevoreg == false) {
                                    JOptionPane.showMessageDialog(null, "¡Los datos de este Cliente acaban de ser eliminados por otro usuario del sistema!", "Cliente inexistente", 0);
                                }
                                limpiar();
                                vacio();
                            } else {
                                if (nuevoreg == true) {
                                    resultado.last();
                                    actualizar();
                                } else {
                                    elcodigo = txtCodigo.getText();
                                    encuentra();
                                }
                            }
                        }
                    } catch (Exception s) {
                    }
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
            bEliminar.setIcon(new ImageIcon(getClass().getResource("/recursos/ELIMINAR.PNG")));
            bEliminar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el registro de la empresa " + txtNombreComercial.getText().trim() + " " + txtRazonSocial.getText() + "?", "Eliminar cliente", 0, 3);
                    if (respuesta == 0) {
                    	JOptionPane.showMessageDialog(null, "�No se puede eliminar los datos de la empresa!", "Error", 2);
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
                    try {
                        if (resultado.isBeforeFirst() == false) {
                            resultado.previous();
                            if (resultado.isBeforeFirst() == true) {
                                resultado.first();
                            }
                            actualizar();
                        }
                    } catch (Exception p) {
                    }
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
                    if (f_listaclientes.seleccionacli == true) {
                        elcodigo = f_listaclientes.codigocli;
                        encuentra();
                    }
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
                    try {
                        if (resultado.isAfterLast() == false) {
                            resultado.next();
                            if (resultado.isAfterLast() == true) {
                                resultado.last();
                            }
                            actualizar();
                        }
                    } catch (Exception p) {
                    }
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
                    try {
                        resultado.last();
                        actualizar();
                    } catch (Exception a) {
                    }
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
                    int resp = JOptionPane.showConfirmDialog(null, "¿Desea salir del formulario?", "Salir del formulario", 0);
                    if (resp == 0) {
                        dispose();
                    }

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
                    try {
                        resultado.first();
                        actualizar();
                    } catch (Exception f) {
                    }
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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					f_empresa frame = new f_empresa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public f_empresa() {
		this.setSize(731, 395);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursos/User.png")));
        this.setContentPane(getJContentPane());
        this.setTitle("DATOS DE LA EMPRESA");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                //System.out.println("windowOpened()"); // TODO Auto-generated Event stub windowOpened()
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
            jLabel3211.setLocation(new Point(290, 243));
            jLabel3211.setSize(new Dimension(124, 23));
            jLabel3211.setText("CLAVE INTERNA:");
            jLabel321 = new JLabel();
            jLabel321.setText("PRODUCCION:");
            jLabel321.setLocation(new Point(329, 184));
            jLabel321.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel321.setSize(new Dimension(85, 21));
            jLabel34 = new JLabel();
            jLabel34.setText("N CONTRIBUYENTE:");
            jLabel34.setLocation(new Point(20, 244));
            jLabel34.setSize(new Dimension(109, 21));
            jLabel33 = new JLabel();
            jLabel33.setText("PUNTO EMISION:");
            jLabel33.setLocation(new Point(20, 212));
            jLabel33.setSize(new Dimension(109, 23));
            jLabel32 = new JLabel();
            jLabel32.setText("ESTABLECIMIENTO:");
            jLabel32.setLocation(new Point(20, 184));
            jLabel32.setSize(new Dimension(109, 21));
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
            jLabel2.setSize(new Dimension(90, 22));
            jLabel1 = new JLabel();
            jLabel1.setBounds(new Rectangle(20, 66, 109, 21));
            jLabel1.setText("NOMB COMERCIAL:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(jLabel1, null);
            jContentPane.add(jLabel2, null);
            jContentPane.add(jLabel3, null);
            jContentPane.add(jLabel31, null);
            jContentPane.add(jLabel32, null);
            jContentPane.add(jLabel33, null);
            jContentPane.add(jLabel34, null);
            jContentPane.add(getTxtNombreComercial(), null);
            jContentPane.add(getTxtApellido(), null);
            jContentPane.add(getTxtDireccion(), null);
            jContentPane.add(getTxtCedula(), null);
            jContentPane.add(getTxtEstablecimiento(), null);
            jContentPane.add(getTxtPuntoEmision(), null);
            jContentPane.add(getTxtNContribuyente(), null);
            jContentPane.add(jLabel321, null);
            jContentPane.add(jLabel3211, null);
            jContentPane.add(getJPanel(), null);
            jContentPane.add(jLabel4, null);
            jContentPane.add(getJPanel1(), null);

            jContentPane.add(getJPanel2(), null);
            
            checkProduccion = new JCheckBox("");
            checkProduccion.setBounds(424, 182, 97, 23);
            jContentPane.add(checkProduccion);
            jContentPane.add(getCheckLlevaContabi());
            jContentPane.add(getLabel());
            
            
            jContentPane.add(getTxtClaveInterna());
        }
        return jContentPane;
    }

    public void conectar() {
        try {
            resultado = ParametrosController.obtenerEmpresa();
            if (resultado.next() == false) {
                vacio();
                xregistros = 0;
            } else {
                xregistros = 1;
                resultado.last();
                actualizar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error al intentar acceder los datos de la empresa!", "Error de acceso", 2);
            return;
        }
    }

    private void vacio() {
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

    private void actualizar() {
        try {
            txtCodigo.setText(resultado.getString("id_emp"));
            txtNombreComercial.setText(resultado.getString("nombre_comercial_emp"));
            txtRazonSocial.setText(resultado.getString("razon_social_emp"));
            txtDireccion.setText(resultado.getString("direccion_emp"));
            txtCedula.setText(resultado.getString("ruc_emp"));
            txtEstablecimiento.setText(resultado.getString("establecimiento_emp"));
            textClaveInterna.setText(resultado.getString("claveInterna_emp"));
            if(resultado.getString("ambiente_emp").equals("2"))
            	checkProduccion.setSelected(true);
            else
            	checkProduccion.setSelected(false);
            
            if(resultado.getString("llevaContabilidad_emp").equals("SI"))
            	checkLlevaContabi.setSelected(true);
            else
            	checkLlevaContabi.setSelected(false);
            txtNContribuyente.setText(resultado.getString("contribuyenteEspecial_emp"));
            txtPuntoEmision.setText(resultado.getString("punto_emp"));
        } catch (Exception m) {
        }
    }

    public void generacodigo() {
        try {
            if (xregistros == 0) {
                txtCodigo.setText("CL0001");
            } else {
                resultado.last();
                String numerocad = new String(resultado.getString("id_emp").substring(2));
                String cadnuevo = String.valueOf(Integer.valueOf(numerocad) + 1);
                int x;
                String ceros = new String("");
                for (x = 1; x <= 4 - cadnuevo.length(); x++) {
                    ceros = ceros + "0";
                }
                txtCodigo.setText("CL" + ceros + cadnuevo);
                if (cadnuevo.length() == 5) {
                    txtCodigo.setText("");
                    JOptionPane.showMessageDialog(null, "¡No se puede generar el codigo. Se ha superado la capacidad del sistema!", "Desbordamiento de valores", 0);
                    System.exit(0);
                }
            }
        } catch (Exception g) {
        }
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombreComercial.setText("");
        txtRazonSocial.setText("");
        txtDireccion.setText("");
        txtCedula.setText("");
        txtEstablecimiento.setText("");
        textClaveInterna.setText("");
        txtNContribuyente.setText("");
        checkLlevaContabi.setSelected(false);
        checkProduccion.setSelected(false);
        
        txtPuntoEmision.setText("");
    }

    public void habilitanuemod() {
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
        txtNombreComercial.setEnabled(true);
        txtRazonSocial.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtCedula.setEnabled(true);
        textClaveInterna.setEnabled(true);
        txtEstablecimiento.setEnabled(true);
        checkProduccion.setEnabled(true);
        checkLlevaContabi.setEnabled(true);
        txtPuntoEmision.setEnabled(true);
        txtNContribuyente.setEnabled(true);
    }

    public void habilitaguacan() {
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
        txtNombreComercial.setEnabled(false);
        txtRazonSocial.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtCedula.setEnabled(false);
        textClaveInterna.setEnabled(false);
        txtEstablecimiento.setEnabled(false);
        checkProduccion.setEnabled(false);
        checkLlevaContabi.setEnabled(false);
        txtPuntoEmision.setEnabled(false);
        txtNContribuyente.setEnabled(false);
    }

    private void guardar() {
        try {
            
            /*if (nuevoreg == true) {
                ClienteController.ingresarCliente(txtCodigo.getText(), txtNombre.getText(), txtRazonSocial.getText(),
                        txtDireccion.getText(), txtCedula.getText(), txtTelefono.getText(), txtCelular.getText(), cboSexo.getSelectedItem(), fechasql, txtEmail.getText(), txtSaldo.getText());
                xregistros = 1;
            } else {

                ClienteController.actualizarCliente(txtCodigo.getText(), txtNombre.getText(), txtRazonSocial.getText(),
                        txtDireccion.getText(), txtCedula.getText(), txtTelefono.getText(), txtCelular.getText(), cboSexo.getSelectedItem(), fechasql, txtEmail.getText());
            }*/

            resultado = ParametrosController.obtenerEmpresa();
            if (nuevoreg == true) {
                resultado.last();
                actualizar();
            } else {
                elcodigo = txtCodigo.getText();
                encuentra();
            }
            JOptionPane.showMessageDialog(null, "¡Los datos de la empresa han sido guardados!", "Almacenamiento de datos", 1);
            habilitaguacan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error al intentar almacenar los datos de la empresa!", "Error de almacenamiento", 2);
            return;
        }
    }

    private void encuentra() {
        try {
            resultadoxreg = ParametrosController.obtenerTotalEmpresa();
            resultadoxreg.last();
            resultado.first();
            for (int x = 1; x <= resultadoxreg.getInt("cuantos"); x++) {
                if (resultado.getString("id_emp").equals(elcodigo) == true) {
                    break;
                }
                resultado.next();
            }
            if (resultado.isAfterLast() == true) {
                JOptionPane.showMessageDialog(null, "¡El Cliente no pudo ser localizado!", "Error de busqueda", 2);
                resultado.last();
            }
            actualizar();
        } catch (Exception m) {
            JOptionPane.showMessageDialog(null, "¡El Cliente no pudo ser localizado!", "Error de busqueda", 2);
        }
    }
	private JCheckBox getCheckLlevaContabi() {
		if (checkLlevaContabi == null) {
			checkLlevaContabi = new JCheckBox("");
			checkLlevaContabi.setBounds(424, 212, 97, 23);
		}
		return checkLlevaContabi;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("LLEVA CONTABILIDAD:");
			label.setSize(new Dimension(124, 23));
			label.setLocation(new Point(290, 212));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setBounds(290, 216, 124, 23);
		}
		return label;
	}
}

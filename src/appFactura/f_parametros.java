package appFactura;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Point;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import pck_controller.ParametrosController;
import javax.swing.JTextArea;

public class f_parametros extends JFrame {

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
    private JTextArea txtDescripcion=null;
    private JTextField txtValor = null;
    private JCheckBox ckbxEstado=null;
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
    Calendar lafecha = Calendar.getInstance();  //  @jve:decl-index=0:
    String elcodigo = new String("");  //  @jve:decl-index=0:

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
                    if (txtNombre.getText().length() < 30) {
                        char letra;
                        letra = (e.getKeyChar() + "").toUpperCase().charAt(0);
                        e.setKeyChar(letra);
                    } else {
                        e.consume();
                    }
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
     * This method initializes txtCedula
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTxtValor() {
        if (txtValor == null) {
            txtValor = new JTextField();
            txtValor.setEnabled(false);
            txtValor.setSize(new Dimension(123, 24));
            txtValor.setDisabledTextColor(Color.darkGray);
            txtValor.setLocation(new Point(131, 242));
            txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    if (txtValor.getText().length() < 13) {
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
            txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (txtValor.getText().length() != 10 & txtValor.getText().length() != 13) {
                        JOptionPane.showMessageDialog(null, "Ingrese 10 numeros para la Cedula o 13 para el RUC", "Error de ingreso", 2);
                        txtValor.setText("");
                    }
                }
            });
        }
        return txtValor;
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
            bNuevo.setToolTipText("Nuevo registro de Parametros");
            bNuevo.setLocation(new Point(7, 8));
            bNuevo.setSize(new Dimension(112, 41));
            bNuevo.setText("   Nuevo");
            bNuevo.setFont(new Font("Dialog", Font.PLAIN, 11));
            bNuevo.setMnemonic(KeyEvent.VK_N);
            bNuevo.setIcon(new ImageIcon(getClass().getResource("/recursos/NUEVO.PNG")));
            bNuevo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    nuevoreg = true;
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
            bModificar.setToolTipText("Modificar datos de Parametros");
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
            bGuardar.setToolTipText("Guardar datos de Parametros");
            bGuardar.setSize(new Dimension(112, 41));
            bGuardar.setLocation(new Point(239, 8));
            bGuardar.setEnabled(false);
            bGuardar.setFont(new Font("Dialog", Font.PLAIN, 11));
            bGuardar.setText(" Guardar");
            bGuardar.setMnemonic(KeyEvent.VK_G);
            bGuardar.setIcon(new ImageIcon(getClass().getResource("/recursos/save.png")));
            bGuardar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (txtNombre.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "�Ingrese el Nombre del Parametros!", "Error de almacenamiento", 2);
                        txtNombre.requestFocus();
                    } else if (txtDescripcion.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "�Ingrese la descripcion del Parametros!", "Error de almacenamiento", 2);
                        txtDescripcion.requestFocus();
                    } else if (txtValor.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "�Ingrese el valor del Parametros!", "Error de almacenamiento", 2);
                        txtValor.requestFocus();
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
                        int respuesta = JOptionPane.showConfirmDialog(null, "�Desea descartar los datos ingresados?", "Cancelar ingresos", 0, 3);
                        if (respuesta == 0) {
                            resultado = ParametrosController.obtenerParametros();
                            habilitaguacan();
                            if (resultado.next() == false) {
                                if (nuevoreg == false) {
                                    JOptionPane.showMessageDialog(null, "�Los datos de este Parametros acaban de ser eliminados por otro usuario del sistema!", "Parametros inexistente", 0);
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
            bEliminar.setToolTipText("Eliminar registro de Parametros");
            bEliminar.setSize(new Dimension(112, 41));
            bEliminar.setLocation(new Point(471, 8));
            bEliminar.setFont(new Font("Dialog", Font.PLAIN, 11));
            bEliminar.setText("  Eliminar");
            bEliminar.setMnemonic(KeyEvent.VK_E);
            bEliminar.setIcon(new ImageIcon(getClass().getResource("/recursos/ELIMINAR.PNG")));
            bEliminar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "�Desea eliminar el registro del Parametros " + txtNombre.getText().trim() + "?", "Eliminar Parametro", 0, 3);
                    if (respuesta == 0) {
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
            bAnterior.setToolTipText("Anterior Parametros");
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
            bBuscar.setToolTipText("Buscar Parametros");
            bBuscar.setLocation(new Point(85, 6));
            bBuscar.setSize(new Dimension(34, 34));
            bBuscar.setIcon(new ImageIcon(getClass().getResource("/recursos/BUSCAR.JPG")));
            bBuscar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    new f_listaclientes().setVisible(true);
//                    if (f_listaclientes.seleccionacli == true) {
//                        elcodigo = f_listaclientes.codigocli;
//                        encuentra();
//                    }
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
            bSiguiente.setToolTipText("Siguiente Parametros");
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
            bUltimo.setToolTipText("Ultimo Parametros");
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
                    int resp = JOptionPane.showConfirmDialog(null, "�Desea salir del formulario?", "Salir del formulario", 0);
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
            bPrimero.setToolTipText("Primer Parametros");
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
    public f_parametros() {
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
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursos/cog.png")));
        this.setContentPane(getJContentPane());
        this.setTitle("PARAMETROS DEL SISTEMA");
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
            jLabel31 = new JLabel();
            jLabel31.setText("VALOR:");
            jLabel31.setLocation(new Point(20, 244));
            jLabel31.setSize(new Dimension(85, 21));
            jLabel2 = new JLabel();
            jLabel2.setText("DESCRIPCION:");
            jLabel2.setLocation(new Point(20, 95));
            jLabel2.setSize(new Dimension(77, 22));
            jLabel1 = new JLabel();
            jLabel1.setBounds(new Rectangle(20, 66, 78, 21));
            jLabel1.setText("NOMBRE:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(jLabel1, null);
            jContentPane.add(jLabel2, null);
            jContentPane.add(jLabel31, null);
            jContentPane.add(getTxtNombre(), null);
            jContentPane.add(getTxtValor(), null);
            jContentPane.add(getJPanel(), null);
            jContentPane.add(jLabel4, null);
            jContentPane.add(getJPanel1(), null);

            jContentPane.add(getJPanel2(), null);
            
            txtDescripcion = new JTextArea();
            txtDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 11));
            txtDescripcion.setEnabled(false);
            txtDescripcion.setBounds(131, 94, 452, 137);
            txtDescripcion.setRows(5);
            jContentPane.add(txtDescripcion);
            
            ckbxEstado = new JCheckBox("ACTIVO");
            ckbxEstado.setBounds(315, 247, 97, 15);
            ckbxEstado.setEnabled(false);
            jContentPane.add(ckbxEstado);
        }
        return jContentPane;
    }

    public void conectar() {
        try {
            resultado = ParametrosController.obtenerParametros();
            if (resultado.next() == false) {
                vacio();
                xregistros = 0;
            } else {
                xregistros = 1;
                resultado.last();
                actualizar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "�Ha ocurrido un error al intentar acceder los datos del Parametros!", "Error de acceso", 2);
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
            txtCodigo.setText(resultado.getString("id_par"));
            txtNombre.setText(resultado.getString("nombre_par"));
            txtDescripcion.setText(resultado.getString("descripcion_par"));
            txtValor.setText(resultado.getString("valor_par"));
            ckbxEstado.setSelected(resultado.getBoolean("activo_par"));
        } catch (Exception m) {
        }
    }

    public void generacodigo() {
        try {
            if (xregistros == 0) {
                txtCodigo.setText("PR0001");
            } else {
                resultado.last();
                String numerocad = new String(resultado.getString("id_par").substring(2));
                String cadnuevo = String.valueOf(Integer.valueOf(numerocad) + 1);
                int x;
                String ceros = new String("");
                for (x = 1; x <= 4 - cadnuevo.length(); x++) {
                    ceros = ceros + "0";
                }
                txtCodigo.setText("PR" + ceros + cadnuevo);
                if (cadnuevo.length() == 5) {
                    txtCodigo.setText("");
                    JOptionPane.showMessageDialog(null, "�No se puede generar el codigo. Se ha superado la capacidad del sistema!", "Desbordamiento de valores", 0);
                    System.exit(0);
                }
            }
        } catch (Exception g) {
        }
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtValor.setText("0.00");
        ckbxEstado.setEnabled(false);
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
        txtNombre.setEnabled(true);
        txtDescripcion.setEnabled(true);
        txtValor.setEnabled(true);
        ckbxEstado.setEnabled(true);
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
        txtNombre.setEnabled(false);
        txtDescripcion.setEnabled(false);
        ckbxEstado.setEnabled(false);
        txtValor.setEnabled(false);
    }

    public void eliminar() {
        try {

                ParametrosController.actualizarEstadoParametro(txtCodigo.getText(),false);
                JOptionPane.showMessageDialog(null, "�El Parametro ha sido inactivado!", "Eliminacion de datos", 1);
                resultado = ParametrosController.obtenerParametros();
                if (resultado.next() == false) {
                    xregistros = 0;
                    limpiar();
                    vacio();
                } else {
                    xregistros = 1;
                    resultado.last();
                    actualizar();
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "�Ha ocurrido un error al intentar eliminar los datos del Parametros!", "Error de eliminacion", 2);
            return;
        }
    }

    private void guardar() {
        try {
            if (nuevoreg == true) {
                ParametrosController.ingresarParametro();
                xregistros = 1;
            } else {

                ParametrosController.actualizarParametro(txtCodigo.getText(), txtNombre.getText(), txtDescripcion.getText(),
                         txtValor.getText(), ckbxEstado.isSelected());
            }

            resultado = ParametrosController.obtenerParametros();
            if (nuevoreg == true) {
                resultado.last();
                actualizar();
            } else {
                elcodigo = txtCodigo.getText();
                encuentra();
            }
            JOptionPane.showMessageDialog(null, "�Los datos del Parametros han sido guardados!", "Almacenamiento de datos", 1);
            habilitaguacan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "�Ha ocurrido un error al intentar almacenar los datos del Parametros!", "Error de almacenamiento", 2);
            return;
        }
    }

    private void encuentra() {
        try {
            resultadoxreg = ParametrosController.obtenerTotalParametros();
            resultadoxreg.last();
            resultado.first();
            for (int x = 1; x <= resultadoxreg.getInt("cuantos"); x++) {
                if (resultado.getString("id_par").equals(elcodigo) == true) {
                    break;
                }
                resultado.next();
            }
            if (resultado.isAfterLast() == true) {
                JOptionPane.showMessageDialog(null, "�El Parametros no pudo ser localizado!", "Error de busqueda", 2);
                resultado.last();
            }
            actualizar();
        } catch (Exception m) {
            JOptionPane.showMessageDialog(null, "�El Parametros no pudo ser localizado!", "Error de busqueda", 2);
        }
    }
}  //  FIN DEL FORMULARIO

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
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import pck_controller.ProductoController;

public class f_lineas extends JFrame {

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
    private JTextField txtLinea = null;
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
    int xregistros, totalReg;
    boolean nuevoreg, linea;
    Calendar lafecha = Calendar.getInstance();  //  @jve:decl-index=0:
    String elcodigo = new String("");  //  @jve:decl-index=0:

    /**
     * This method initializes txtNombre
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTxtLinea() {
        if (txtLinea == null) {
            txtLinea = new JTextField();
            txtLinea.setEnabled(false);
            txtLinea.setSize(new Dimension(452, 24));
            txtLinea.setDisabledTextColor(Color.darkGray);
            txtLinea.setPreferredSize(new Dimension(4, 24));
            txtLinea.setLocation(new Point(131, 66));
            txtLinea.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    if (txtLinea.getText().length() < 30) {
                        char letra;
                        letra = (e.getKeyChar() + "").toUpperCase().charAt(0);
                        e.setKeyChar(letra);
                    } else {
                        e.consume();
                    }
                }
            });
            txtLinea.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    txtLinea.setText(txtLinea.getText().trim());
                }
            });
        }
        return txtLinea;
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
            jPanel.setBounds(new Rectangle(10, 155, 705, 56));
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
            bNuevo.setToolTipText("Nuevo registro de Linea");
            bNuevo.setLocation(new Point(7, 8));
            bNuevo.setSize(new Dimension(112, 41));
            bNuevo.setText("   Nuevo");
            bNuevo.setFont(new Font("Dialog", Font.PLAIN, 11));
            bNuevo.setMnemonic(KeyEvent.VK_N);
            bNuevo.setIcon(new ImageIcon(getClass().getResource("/recursos/nuevo.png")));
            bNuevo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    nuevoreg = true;
                    limpiar();
                    generacodigo();
                    habilitanuemod();
                    txtLinea.requestFocus();

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
            bModificar.setToolTipText("Modificar datos de Linea");
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
                    txtLinea.requestFocus();
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
            bGuardar.setToolTipText("Guardar datos de Linea");
            bGuardar.setSize(new Dimension(112, 41));
            bGuardar.setLocation(new Point(239, 8));
            bGuardar.setEnabled(false);
            bGuardar.setFont(new Font("Dialog", Font.PLAIN, 11));
            bGuardar.setText(" Guardar");
            bGuardar.setMnemonic(KeyEvent.VK_G);
            bGuardar.setIcon(new ImageIcon(getClass().getResource("/recursos/save.png")));
            bGuardar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (txtLinea.getText().trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "¡Ingrese los Nombres del Linea!", "Error de almacenamiento", 2);
                        txtLinea.requestFocus();
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
                            resultado = cargar();
                            habilitaguacan();
                            if (resultado.next() == false) {
                                if (nuevoreg == false) {
                                    JOptionPane.showMessageDialog(null, "¡Los datos de este Linea acaban de ser eliminados por otro usuario del sistema!", "Linea inexistente", 0);
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
            bEliminar.setToolTipText("Eliminar registro de Linea");
            bEliminar.setSize(new Dimension(112, 41));
            bEliminar.setLocation(new Point(471, 8));
            bEliminar.setFont(new Font("Dialog", Font.PLAIN, 11));
            bEliminar.setText("  Eliminar");
            bEliminar.setMnemonic(KeyEvent.VK_E);
            bEliminar.setIcon(new ImageIcon(getClass().getResource("/recursos/ELIMINAR.PNG")));
            bEliminar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el registro del Linea " + txtLinea.getText().trim() + "?", "Eliminar cliente", 0, 3);
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
            bAnterior.setToolTipText("Anterior Linea");
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
            bBuscar.setToolTipText("Buscar Linea");
            bBuscar.setLocation(new Point(85, 6));
            bBuscar.setSize(new Dimension(34, 34));
            bBuscar.setIcon(new ImageIcon(getClass().getResource("/recursos/BUSCAR.JPG")));
            bBuscar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    new f_listaGeneral(linea).setVisible(true);
                    if (f_listaGeneral.seleccionacli == true) {
                        elcodigo = f_listaGeneral.codigocli;
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
            bSiguiente.setToolTipText("Siguiente Linea");
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
            bUltimo.setToolTipText("Ultimo Linea");
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
            bPrimero.setToolTipText("Primer Linea");
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
                f_lineas thisClass = new f_lineas(true);
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    /**
     * This is the default constructor
     * @param linea
     */
    public f_lineas(boolean linea) {
        super();
        initialize();
        this.linea=linea;
        conectar();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(731, 256);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursos/icon_edit-page_40.gif")));
        this.setContentPane(getJContentPane());
        this.setTitle("Datos de las Lineas de Productos");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                System.out.println("windowOpened()"); // TODO Auto-generated Event stub windowOpened()
            }
        });
        this.setLocationRelativeTo(null);
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
            jLabel1 = new JLabel();
            jLabel1.setBounds(new Rectangle(20, 66, 78, 21));
            jLabel1.setText("LINEA:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(jLabel1, null);
            jContentPane.add(getTxtLinea(), null);
            jContentPane.add(getJPanel(), null);
            jContentPane.add(jLabel4, null);
            jContentPane.add(getJPanel1(), null);

            jContentPane.add(getJPanel2(), null);
        }
        return jContentPane;
    }
    
    private ResultSet cargar()
    {
        ResultSet rs;
        if(this.linea)
            rs= ProductoController.listarLineas();
        else
            rs= ProductoController.listarMarcas();
        
        this.totalReg=0;
        
        try 
        {
            while(rs.next())
            {
                this.totalReg+=1;
            }
        } catch (SQLException ex) {
             ex.printStackTrace();
        }
        return rs;
    }

    private void conectar() {
        try {
            resultado = cargar();
            if (this.totalReg<1) {
                vacio();
                xregistros = 0;
            } else {
                xregistros = 1;
                resultado.last();
                actualizar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error al intentar acceder los datos del Linea!", "Error de acceso", 2);
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
            txtCodigo.setText(resultado.getString("id_lm"));
            txtLinea.setText(resultado.getString("detalle_lm"));
           
        } catch (Exception m) {
        }
    }

    public void generacodigo() {
        try {
            if (xregistros == 0) {
                txtCodigo.setText("LM0001");
            } else {
                resultado.last();
                String numerocad = new String(resultado.getString("id_lm").substring(2));
                String cadnuevo = String.valueOf(Integer.valueOf(numerocad) + 1);
                int x;
                String ceros = new String("");
                for (x = 1; x <= 4 - cadnuevo.length(); x++) {
                    ceros = ceros + "0";
                }
                txtCodigo.setText("LM" + ceros + cadnuevo);
                if (cadnuevo.length() == 5) {
                    txtCodigo.setText("");
                    JOptionPane.showMessageDialog(null, "¡No se puede generar el codigo. Se ha superado la capacidad del sistema!", "Desbordamiento de valores", 0);
                    System.exit(0);
                }
            }
        } catch (Exception g) {
            g.printStackTrace();
        }
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtLinea.setText("");
       
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
        txtLinea.setEnabled(true);
    }

    public static boolean FechaValida(String fechax) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fechax);
        } catch (ParseException e) {
            return false;
        }
        return true;
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
        txtLinea.setEnabled(false);
    }

    public void eliminar() {
        try {
           
            //LineaController.eliminarLinea(txtCodigo.getText());
            JOptionPane.showMessageDialog(null, "¡Los datos del Linea han sido eliminados!", "Eliminacion de datos", 1);
            //resultado = LineaController.obtenerLineas();
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
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error al intentar eliminar los datos del Linea!", "Error de eliminacion", 2);
            return;
        }
    }

    private void guardar() {
        try {
            
            if (nuevoreg == true) {
                ProductoController.insertarLineaMarca(txtCodigo.getText(), txtLinea.getText(), this.linea);
                xregistros = 1;
            } else {

                ProductoController.actualizarLineaMarca(txtCodigo.getText(), txtLinea.getText(), this.linea);
            }

            resultado = cargar();
            if (nuevoreg == true) {
                resultado.last();
                actualizar();
            } else {
                elcodigo = txtCodigo.getText();
                encuentra();
            }
            JOptionPane.showMessageDialog(null, "¡Los datos del Linea han sido guardados!", "Almacenamiento de datos", 1);
            habilitaguacan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error al intentar almacenar los datos del Linea!", "Error de almacenamiento", 2);
            return;
        }
    }

    private void encuentra() {
        try {
            //resultadoxreg = cargar();
            //resultadoxreg.last();
            resultado.first();
            for (int x = 1; x <= this.totalReg; x++) {
                if (resultado.getString("id_lm").equals(elcodigo) == true) {
                    break;
                }
                resultado.next();
            }
            if (resultado.isAfterLast() == true) {
                JOptionPane.showMessageDialog(null, "¡El Linea no pudo ser localizado!", "Error de busqueda", 2);
                resultado.last();
            }
            actualizar();
        } catch (Exception m) {
            JOptionPane.showMessageDialog(null, "¡El Linea no pudo ser localizado!", "Error de busqueda", 2);
        }
    }
}  //  FIN DEL FORMULARIO

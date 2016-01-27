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
import javax.swing.JOptionPane;
import javax.swing.table.*;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import pck_controller.ProductoController;

public class f_listarticulos extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;
    private JButton BSeleccionar = null;
    private JButton BCerrar = null;
    private JTextField TxtArticulo = null;
    private JLabel LblVendedor = null;
    private JScrollPane jScrollPane = null;
    private JTable JTable = null;
    private JPanel Panel1 = null;
    private JLabel jLabel = null;
    
    ResultSet resultado;  //  @jve:decl-index=0:
    configura modelo = new configura();
    static String codigoart = new String("");  //  @jve:decl-index=0:
    static boolean seleccionaart = false;

    /**
     * @param owner
     */
    public f_listarticulos() {
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
        this.setResizable(false);
        this.setTitle("Lista de Artículos");
        this.setContentPane(getJContentPane());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        modelo.addColumn("ID");
        modelo.addColumn("ARTICULOS");
        modelo.addColumn("STOCK");
        modelo.addColumn("P.V.P.");
        TableColumn columna0 = JTable.getColumn("ID");
        columna0.setPreferredWidth(0);
        columna0.setMinWidth(0);
        columna0.setMaxWidth(0);
        TableColumn columna1 = JTable.getColumn("ARTICULOS");
        columna1.setPreferredWidth(500);
        columna1.setMinWidth(500);
        columna1.setMaxWidth(500);
        TableColumn columna2 = JTable.getColumn("STOCK");
        columna2.setPreferredWidth(90);
        columna2.setMinWidth(90);
        columna2.setMaxWidth(90);
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
            jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(16, 308, 57, 50));
            jLabel.setText("");
            jLabel.setIcon(new ImageIcon(getClass().getResource("/recursos/LUPA.PNG")));
            LblVendedor = new JLabel();
            LblVendedor.setBounds(new Rectangle(12, 15, 64, 21));
            LblVendedor.setText("Artículo:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getTxtArticulo(), null);
            jContentPane.add(LblVendedor, null);
            jContentPane.add(getJScrollPane(), null);
            jContentPane.add(getPanel1(), null);
            jContentPane.add(jLabel, null);
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
                    seleccionaart = false;
                    dispose();
                }
            });
        }
        return BCerrar;
    }

    /**
     * This method initializes TxtArticulo
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTxtArticulo() {
        if (TxtArticulo == null) {
            TxtArticulo = new JTextField();
            TxtArticulo.setBounds(new Rectangle(87, 13, 624, 25));
            TxtArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    char caracter;
                    caracter = (e.getKeyChar() + "").toUpperCase().charAt(0);
                    e.setKeyChar(caracter);
                }
            });
            TxtArticulo.addCaretListener(new javax.swing.event.CaretListener() {
                public void caretUpdate(javax.swing.event.CaretEvent e) {
                    actualizalista();
                }
            });
        }
        return TxtArticulo;
    }

    /**
     * This method initializes jScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setBounds(new Rectangle(15, 48, 696, 251));
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
            JTable.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        seleccionar();
                    }

                }
            });
        }
        return JTable;
    }

    public void conectar() {
        try {
            actualizalista();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error al intentar acceder los datos de los Artículos!", "Error de acceso", 2);
            return;
        }
    }

    public class configura extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public Class getColumnClass(int columna) {
            if (columna == 2) {
                return Integer.class;
            }
            if (columna == 3) {
                return Integer.class;
            }
            return Object.class;
        }
    }

    private void actualizalista() {
        try {
            resultado = ProductoController.listarARTICULOS(TxtArticulo.getText());
            int xreg = modelo.getRowCount();
            if (xreg > 0) {
                for (int x = 1; x <= xreg; x++) {
                    modelo.removeRow(0);
                }
            }
            if (resultado.next() == true) {
                BSeleccionar.setEnabled(true);
                resultado.first();
                while (resultado.isAfterLast() == false) {
                    Object[] fila = new Object[4]; // Se crea un array con 4 columnas para cada filas en la tabla
                    fila[0] = resultado.getObject("id_art");
                    fila[1] = resultado.getObject("DESCRIPCION_ART");
                    fila[2] = resultado.getObject("stock");
                    fila[3] = resultado.getString("pvp_art");
                    modelo.addRow(fila);// Se crea la fila y se pasa los datos del object.
                    resultado.next();
                }
            } else {
                BSeleccionar.setEnabled(false);
            }
        } catch (Exception m) {
            JOptionPane.showMessageDialog(null, "error");
        }
    }

    private void seleccionar() {
        if (JTable.isCellSelected(JTable.getSelectedRow(), 0) == false) {
            JOptionPane.showMessageDialog(null, "¡Seleccione un Artículo de la lista!", "Error de selección", 2);
        } else {
            codigoart = JTable.getValueAt(JTable.getSelectedRow(), 0).toString();
            seleccionaart = true;
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
            Panel1.setLocation(new Point(430, 306));
            Panel1.setSize(new Dimension(280, 52));
            Panel1.add(getBSeleccionar(), null);
            Panel1.add(getBCerrar(), null);
        }
        return Panel1;
    }

}

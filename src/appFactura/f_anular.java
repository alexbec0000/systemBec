package appFactura;

import javax.swing.JPanel;
import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.WindowConstants;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.JOptionPane;

public class f_anular extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;
    private JPanel jPanel = null;
    private JButton BAnular = null;
    private JButton BCancelar = null;
    private JLabel LblClave = null;
    private JPasswordField PswClave = null;
    private JLabel LblIcono = null;
    private JLabel Lblatencion = null;
    private JLabel LblInforma = null;
    private JLabel Lbl2 = null;
    static boolean anulafac = false;

    /**
     * @param owner
     */
    public f_anular() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(561, 203);
        this.setResizable(false);
        this.setModal(true);
        this.setTitle("¡Anular Factura!");
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setContentPane(getJContentPane());
        this.setLocationRelativeTo(null);
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            LblClave = new JLabel();
            LblClave.setBounds(new Rectangle(13, 128, 115, 29));
            LblClave.setText("Clave de anulación:");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJPanel(), null);
            jContentPane.add(getBAnular(), null);
            jContentPane.add(getBCancelar(), null);
            jContentPane.add(LblClave, null);
            jContentPane.add(getPswClave(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            Lbl2 = new JLabel();
            Lbl2.setText("¿DESEA ANULAR ESTA FACTURA DE VENTA?");
            Lbl2.setLocation(new Point(89, 68));
            Lbl2.setSize(new Dimension(399, 22));
            LblInforma = new JLabel();
            LblInforma.setBounds(new Rectangle(89, 47, 435, 25));
            LblInforma.setHorizontalAlignment(SwingConstants.LEFT);
            LblInforma.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
            LblInforma.setAutoscrolls(false);
            LblInforma.setHorizontalTextPosition(SwingConstants.LEFT);
            LblInforma.setText("AL ANULAR LA FACTURA, SUS DATOS SERAN IGNORADOS POR EL SISTEMA.");
            Lblatencion = new JLabel();
            Lblatencion.setBounds(new Rectangle(195, 15, 181, 28));
            Lblatencion.setFont(new Font("Arial", Font.BOLD, 20));
            Lblatencion.setHorizontalTextPosition(SwingConstants.CENTER);
            Lblatencion.setHorizontalAlignment(SwingConstants.CENTER);
            Lblatencion.setForeground(new Color(204, 0, 51));
            Lblatencion.setText("¡ATENCION!");
            LblIcono = new JLabel();
            LblIcono.setBounds(new Rectangle(21, 29, 48, 45));
            LblIcono.setIcon(new ImageIcon(getClass().getResource("/recursos/ADMIRACION.JPG")));
            LblIcono.setIconTextGap(4);
            LblIcono.setText("");
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel.setBounds(new Rectangle(12, 10, 533, 100));
            jPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            jPanel.add(LblIcono, null);
            jPanel.add(Lblatencion, null);
            jPanel.add(LblInforma, null);
            jPanel.add(Lbl2, null);
        }
        return jPanel;
    }

    /**
     * This method initializes BAnular
     *
     * @return javax.swing.JButton
     */
    private JButton getBAnular() {
        if (BAnular == null) {
            BAnular = new JButton();
            BAnular.setText("    Anular");
            BAnular.setToolTipText("Anular Factura");
            BAnular.setHorizontalTextPosition(SwingConstants.RIGHT);
            BAnular.setHorizontalAlignment(SwingConstants.LEFT);
            BAnular.setLocation(new Point(301, 121));
            BAnular.setSize(new Dimension(116, 41));
            BAnular.setFont(new Font("Dialog", Font.PLAIN, 12));
            BAnular.setIcon(new ImageIcon(getClass().getResource("/recursos/ELIMINAR.JPG")));
            BAnular.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    String clave = new String(PswClave.getPassword());
                    if (clave.equals("ANULAR") == true) {
                        anulafac = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "¡La clave de anulación de Facturas es incorrecta!", "Clave incorrecta", 0);
                        PswClave.setText("");
                        PswClave.requestFocus();
                    }
                }
            });
        }
        return BAnular;
    }

    /**
     * This method initializes BCancelar
     *
     * @return javax.swing.JButton
     */
    private JButton getBCancelar() {
        if (BCancelar == null) {
            BCancelar = new JButton();
            BCancelar.setToolTipText("Cancelar anulación y salir");
            BCancelar.setIcon(new ImageIcon(getClass().getResource("/recursos/CANCELAR2.JPG")));
            BCancelar.setHorizontalTextPosition(SwingConstants.RIGHT);
            BCancelar.setHorizontalAlignment(SwingConstants.LEFT);
            BCancelar.setFont(new Font("Dialog", Font.PLAIN, 12));
            BCancelar.setLocation(new Point(428, 121));
            BCancelar.setSize(new Dimension(116, 41));
            BCancelar.setText("Cancelar");
            BCancelar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    anulafac = false;
                    dispose();
                }
            });
        }
        return BCancelar;
    }

    /**
     * This method initializes PswClave
     *
     * @return javax.swing.JPasswordField
     */
    private JPasswordField getPswClave() {
        if (PswClave == null) {
            PswClave = new JPasswordField();
            PswClave.setBounds(new Rectangle(128, 130, 157, 26));
            PswClave.setFont(new Font("Dialog", Font.BOLD, 12));
            PswClave.setEchoChar('@');
        }
        return PswClave;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"

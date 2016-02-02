package appsistema;
//Declaracion de librerias

import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import pck_entidades.AppConfig;

public class Presentacion extends javax.swing.JFrame {
//Inicializando los parametros

    private final Timer timer;
    private int cont;
    //Dimension pantalla;
    //private String newline;
    public final static int ONE_SECOND = 10;

    public Presentacion() {
        initComponents();
        cont = -1;
        jProgressBar1.setValue(0);
        jProgressBar1.setStringPainted(true);
        timer = new Timer(ONE_SECOND, new TimerListener());
        timer.start();
        
//        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
//        setLocation((pantalla.width - getWidth())/2,(pantalla.height - getHeight())/2);
    }

    class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            cont++;
            //jLabel1.setText("nume es :"+cont);
            jProgressBar1.setValue(cont);
            if (cont == 120) {
                timer.stop();
                esconder();
                if(Cargar_Data())
                {
                    Acceso objAcceso = new Acceso();
                    //objAcceso.setLocation((pantalla.width-objAcceso.getWidth())/2, (pantalla.height-objAcceso.getHeight())/2);
                    objAcceso.setVisible(true);
                }
            }
        }
    }

   
    public boolean Cargar_Data() {
        //Lee la data del objeto serializable
        try {
            FileInputStream fis = new FileInputStream("AppConfig.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            if (ois != null) {
                AppConfig obj =  (AppConfig) ois.readObject();
                 System.out.println(obj.getDataBase());
                ois.close();
            }
           
        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo binaro Configuracion: " + e);
            Configurar configurar = new Configurar(true);
            configurar.setVisible(true);
            return false;
        }
        return true;
    }

    public void esconder() {
        this.hide();
    }

    public void Activar() {
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Extreme performance.jpg"))); // NOI18N

        jProgressBar1.setForeground(new java.awt.Color(137, 172, 217));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(0, 150, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Presentacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

}

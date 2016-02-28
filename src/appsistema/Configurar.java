/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appsistema;

import appFactura.f_empresa;
import appFactura.f_parametros;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import pck_accesoDatos.cls_conexion;
import comprobantes.util.AppConfig;
import pck_utilidades.FiltroArchivo;
import pck_utilidades.ScriptRunner;

/**
 *
 * @author Alex
 */
public class Configurar extends javax.swing.JFrame {

    /**
     * Creates new form Configurar
     */
    AppConfig obj;
    boolean cargar;

    public Configurar() {
        initComponents();
        Cargar_Data();
    }

    public Configurar(boolean cargar) {
        initComponents();
        this.cargar = cargar;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTxTHost = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTxtDB = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTxtUser = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jBtnOk = new javax.swing.JButton();
        jBtnExit = new javax.swing.JButton();
        jBtnRespaldar = new javax.swing.JButton();
        jBtnExit2 = new javax.swing.JButton();
        jBtnParametrs = new javax.swing.JButton();
        jBtnEmpresa = new javax.swing.JButton();
        jTxtClave = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("CONFIGURACION");

        jLabel3.setText("HOST:");

        jLabel5.setText("DB:");

        jTxtDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtDBActionPerformed(evt);
            }
        });

        jLabel7.setText("USER:");

        jLabel8.setText("CLAVE:");

        jBtnOk.setBackground(new java.awt.Color(255, 255, 255));
        jBtnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icon_confirm.jpg"))); // NOI18N
        jBtnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOkActionPerformed(evt);
            }
        });

        jBtnExit.setBackground(new java.awt.Color(255, 255, 255));
        jBtnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icon_cancel.png"))); // NOI18N
        jBtnExit.setText("Exit");
        jBtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExitActionPerformed(evt);
            }
        });

        jBtnRespaldar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnRespaldar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/database_save.png"))); // NOI18N
        jBtnRespaldar.setText("RESPALDAR");
        jBtnRespaldar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRespaldarActionPerformed(evt);
            }
        });

        jBtnExit2.setBackground(new java.awt.Color(255, 255, 255));
        jBtnExit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/database_refresh.png"))); // NOI18N
        jBtnExit2.setText("RESTAURAR");
        jBtnExit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExit2ActionPerformed(evt);
            }
        });

        jBtnParametrs.setBackground(new java.awt.Color(255, 255, 255));
        jBtnParametrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/SELECCIONAR.JPG"))); // NOI18N
        jBtnParametrs.setToolTipText("Parametros");
        jBtnParametrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnParametrsActionPerformed(evt);
            }
        });

        jBtnEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        jBtnEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icon_edit-page_40.gif"))); // NOI18N
        jBtnEmpresa.setToolTipText("Empresa");
        jBtnEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEmpresaActionPerformed(evt);
            }
        });

        jTxtClave.setText("jPasswordField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxTHost)
                                    .addComponent(jTxtDB)
                                    .addComponent(jTxtUser)
                                    .addComponent(jTxtClave, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBtnRespaldar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnExit2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jBtnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnParametrs, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTxTHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTxtDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBtnRespaldar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTxtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTxtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBtnExit2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jBtnParametrs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBtnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtDBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDBActionPerformed

    private void jBtnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOkActionPerformed
        obj = new AppConfig();
        obj.setHost(jTxTHost.getText());
        obj.setDataBase(jTxtDB.getText());
        obj.setUser(jTxtUser.getText());
        obj.setPass(jTxtClave.getText());
        Grabar();
        if (this.cargar) {
            Acceso objAcceso = new Acceso();
            objAcceso.setVisible(true);
        }

        setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jBtnOkActionPerformed

    private void jBtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExitActionPerformed
        if (this.cargar) {
            System.exit(0);
        } else {
            setVisible(false);
        }
    }//GEN-LAST:event_jBtnExitActionPerformed

    private void jBtnRespaldarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRespaldarActionPerformed
        // TODO add your handling code here:
        JFileChooser selectorDeArchivos = new JFileChooser();
        int showSaveDialog = selectorDeArchivos.showSaveDialog(this);
        if (showSaveDialog == JFileChooser.APPROVE_OPTION) {
            File archivo = selectorDeArchivos.getSelectedFile();

            /*NOTE: Used to create a cmd command*/
            String pasword = (obj.getPass() == null || obj.getPass().isEmpty()) ? "" : " -p " + obj.getPass();
            String datosComando = obj.getUser() + pasword + " --database " + obj.getDataBase() + " -r " + archivo.getAbsoluteFile() + ".sql";
            String executeCmd = "mysqldump -u " + datosComando;
            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/

            int processComplete = respaldarBd(executeCmd);
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                JFileChooser seleccionarExe = new JFileChooser();
                seleccionarExe.setFileFilter(new FiltroArchivo());
                int showOpenDialog = seleccionarExe.showOpenDialog(this);
                if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
                    executeCmd = seleccionarExe.getSelectedFile().getAbsolutePath() + " -u " + datosComando;
                    System.out.println(executeCmd);
                    int respaldarBd = respaldarBd(executeCmd);
                    if (processComplete == 0) {
                        JOptionPane.showMessageDialog(this, "Respaldo de base de datos se realizo correctamente.\n"
                                + "en " + seleccionarExe.getSelectedFile().getAbsolutePath(), "El Proceso se realizo correctammente", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }

        }
    }//GEN-LAST:event_jBtnRespaldarActionPerformed

    private void jBtnExit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExit2ActionPerformed
        // TODO add your handling code here:

        JFileChooser selectorDeArchivos = new JFileChooser();
        int showSaveDialog = selectorDeArchivos.showOpenDialog(this);
        if (showSaveDialog == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader archivo = new FileReader(selectorDeArchivos.getSelectedFile());
                cls_conexion.conectar();
                ScriptRunner sr = new ScriptRunner(cls_conexion.getCns(), false, true);
                sr.runScript(archivo);
                cls_conexion.cerrarTodo(1);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Archivo no soportado");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Archivo no soportado");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Archivo no soportado");
            }
            JOptionPane.showMessageDialog(this, "Base de datos Importado correctamente");
        }
    }//GEN-LAST:event_jBtnExit2ActionPerformed

    private void jBtnParametrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnParametrsActionPerformed
        // TODO add your handling code here:

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                f_parametros thisClass = new f_parametros();
                thisClass.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }//GEN-LAST:event_jBtnParametrsActionPerformed

    private void jBtnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEmpresaActionPerformed
        // TODO add your handling code here:
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                f_empresa thisClass = new f_empresa();
                thisClass.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }//GEN-LAST:event_jBtnEmpresaActionPerformed

    public void Cargar_Data() {
        //Lee la data del objeto serializable
        try {
            FileInputStream fis = new FileInputStream("AppConfig.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            if (ois != null) {
                AppConfig.setConfig((AppConfig) ois.readObject());
                obj = AppConfig.getConfig();
                ois.close();
            }

            jTxTHost.setText(obj.getHost());
            jTxtDB.setText(obj.getDataBase());
            jTxtUser.setText(obj.getUser());
            jTxtClave.setText(obj.getPass());

        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo binaro Configuracion: " + e);
        }
    }

    public void Grabar() {
        //Guarda la data en el archivo serializado
        try {
            FileOutputStream fos = new FileOutputStream("AppConfig.bin");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            //Comprobando si esta vacio
            if (out != null) {
                out.writeObject(obj);
                out.close();
            }//Fin del if

        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la grabacion sobre el arreglo: " + e);
        }
    }//Fin de Grabar

    public int respaldarBd(String executeCmd) {
        int processComplete = -1;
        /*NOTE: Executing the command here*/
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            processComplete = runtimeProcess.waitFor();
            return processComplete;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return processComplete;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Configurar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Configurar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Configurar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Configurar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Configurar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnEmpresa;
    private javax.swing.JButton jBtnExit;
    private javax.swing.JButton jBtnExit2;
    private javax.swing.JButton jBtnOk;
    private javax.swing.JButton jBtnParametrs;
    private javax.swing.JButton jBtnRespaldar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTxTHost;
    private javax.swing.JPasswordField jTxtClave;
    private javax.swing.JTextField jTxtDB;
    private javax.swing.JTextField jTxtUser;
    // End of variables declaration//GEN-END:variables
}
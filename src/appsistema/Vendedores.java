package appsistema;
//Declaracion de librerias

import appFactura.f_vendedores;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pck_controller.VendedorController;
import pck_entidades.Arreglo_Vendedores;
import pck_entidades.cls_vendedor;
import pck_utilidades.FiltraEntrada;
import pck_utilidades.Generales;

public class Vendedores extends javax.swing.JFrame {

    //Declarando las claces
    DefaultComboBoxModel cbxProv;
    cls_vendedor objVendedor;
    Arreglo_Vendedores objArreglo;
    DefaultTableModel miModelo;
    String[] cabecera = {"Nº", "Código", "CI", "Nombres", "Usuario", "Direccion", "Tlf.", "Celular", "Ventas", "E-Mail"};
    String[][] data = {};
    VendedorController objVendedorController;
    FileInputStream foto;
    //Variables globales
    int num = 0;

    public String Generar_Codigo() {
        int cod = (int) (Math.random() * (99999 - 10000)) + 10000;
        String codig = String.valueOf(cod);
        return codig;
    }

    public Vendedores() {
        initComponents();
        cbxProv = new DefaultComboBoxModel();
        miModelo = new DefaultTableModel(data, cabecera);
        jTblVendedores.setModel(miModelo);
        objArreglo = new Arreglo_Vendedores();
        objVendedorController = new VendedorController();
        Cargar_Data();
        Actualizar_Tabla();
        jTxtCI.requestFocus();

    }

    public void Cargar_Data() {
        //Lee la data del objeto serializable
        try {
            ArrayList<cls_vendedor> listVend = objVendedorController.cargarVendedores();
            for (cls_vendedor listVend1 : listVend) {
                objArreglo.Agregar(listVend1);
            }
        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los vendedores: " + e);
        }
    }

    //METODO PARA GRABAR LA INFORMACION DEL ARREGLO DE OBJETOS AL ARCHIVO BINARIO
    public void Grabar() {
        //Guarda la data en el archivo serializado
        try {
            for (int i = 0; i < objArreglo.Numero_Vendedores(); i++) {
                if (objArreglo.getVendedor(i).isEditar()) {
                    objVendedorController.grabarRegistro(objArreglo.getVendedor(i));
                    objArreglo.getVendedor(i).setFoto(null);
                }
            }
        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la grabacion sobre el arreglo: " + e);
        }
    }//Fin de Grabar

    //METODOS PARA MANIPULAR LA TABLA Y LA LISTA
    public void Actualizar_Tabla() {
        //Vaciando la informacion de la tabla
        Vaciar_Tabla();
        //Capturando el tamaño del arreglo
        int n = objArreglo.Numero_Vendedores();

        for (int i = 0; i < n; i++) {
            //Extrallendo informacion de cada objeto del arreglo
            String codigo = objArreglo.getVendedor(i).getCodigo();
            String CI = objArreglo.getVendedor(i).getCI();
            String nombres = objArreglo.getVendedor(i).getNombres();
            String usuario = objArreglo.getVendedor(i).getUsuario();
            String direccion = objArreglo.getVendedor(i).getTelefono();
            String telefono = objArreglo.getVendedor(i).getTelefono();
            String celular = objArreglo.getVendedor(i).getCelular();
            float comision = objArreglo.getVendedor(i).getVentas();
            String mail = objArreglo.getVendedor(i).getMail();

            //Insertando la indormacion en la tabla
            Insertar(i + 1, codigo, CI, nombres, usuario, direccion, telefono, celular, comision, mail);
        }//Fin del for
    }

    public void Vaciar_Tabla() {
        int filas = jTblVendedores.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0); //Removiendo la fila de la tabla
        }//Fin del for
    }

    public void Insertar(int num, String codigo, String CI, String nombres, String usuario, String direccion, String telefono, String celular, float comision, String mail) {

        //Llenando la fila de la tabla
        Object fila[] = {num, codigo, CI, nombres, usuario, direccion, telefono, celular, comision, mail};
        //Agregando la fila a nuestro modelo de tabla
        miModelo.addRow(fila);
    }

    //METODOS UTILIZADOS PARA LAS CONSULTAS, ELIMINACION Y ACTUALIZACION DE DATOS
    public void Modificar() {
        String codigo = jLblCodigo.getText();
        String dato = Verifica_Datos();
        if (dato.equalsIgnoreCase("")) {
            if (codigo.equalsIgnoreCase("")) {
                mensaje("ERROR: Códico incorrecto");
                Habilitar();
                jTxtCI.requestFocus();
            } else {
                //Se vuelve a buscar el codigo para no repetir el mismo
                int p = objArreglo.Buscar(codigo);
                //Se leen los datos de entrada de los text file

                //Generando la clace para manejar un Registro academico
                objVendedor = obtenerVendedor();
                //Verificando si el codigo existe dentro del arreglo

                if (p == -1)//El codigo es nuevo
                {
                    objArreglo.Agregar(objVendedor);
                } else//Codigo ya existente
                {
                    objArreglo.Reemplaza(p, objVendedor);
                }

                //Limpiando las entradas
                Limpiar_Entradas();
                //Grabamos la informacion en el archivo binario
                Grabar();
                //Actualizando la tabla
                Actualizar_Tabla();
                //Colocando el cursor el en primer area de texto
                jTxtCI.requestFocus();
            }//Fin de else
        } else {
            mensaje("Debe llenar los campos correspondientes del vendedor que desea modificar");
        }

    }//Fin de modificar

    public void Eliminar() {
        //Se llama al metodo consultar para ver los datos a borrar
        Consultar();
        //Se llama al metodo busca en el arreglo que devuelve la posicion del codigo buscado
        int p = objArreglo.Buscar(jLblCodigo.getText().toUpperCase());
        //Comprobando que el codigo existe
        if (p != -1) {
            int R = JOptionPane.showConfirmDialog(this, "Está seguro de eliminar éste registro?", "Responder", 0);
            //Si la respuesta es AFIRMATIVA:
            if (R == 0) {
                //Eliminamos el objeto en la posicion capturada (Posicion "p")
                objArreglo.Elimina(p);
                Limpiar_Entradas();//Limpiando las entradas
                Grabar();//Grabamos la informacion en el archivo binario
                Actualizar_Tabla();//Actualizamos la tabla
                mensaje("Los datos se eliminaron correctamente");
                jTxtCI.requestFocus();//Colocando el cursor en el primer area de texto
            }//Fin del if
            //En caso el codigo no exista
            else {
                JOptionPane.showMessageDialog(null, "El código del alumno no existe");
            }

        }//Fin del if(Verificando que existe el codigo)

    }//Fin del metodo Eliminar

    public void Consultar() {
        //Pedimos el código
        String codigo = JOptionPane.showInputDialog("Ingresar: Código del vendedor").toUpperCase().trim();
        //Se llama al metodo busca que devuelve la posicion del codigo buscado
        int p = objArreglo.Buscar(codigo);
        //Comprobando si el codigo existe
        if (p == -1)//Cuando el codigo no exite
        {
            mensaje("Por favor verificar el código");
            Limpiar_Entradas();
        } else//Cuando existe el codigo
        {
            //Mostramos los datos en el formulario
            Imprimir_Datos(p);

        }//Fin del else

    }//Fin de Consultar

    public void Imprimir_Datos(int pos) {

        Limpiar_Entradas();
        Habilitar();
        //Instanciamos el objeto
        objVendedor = objArreglo.getVendedor(pos);

        //Colocando la informacion en los objetos
        jLblCodigo.setText(objVendedor.getCodigo());
        jTxtCI.setText(objVendedor.getCI());
        jTxtNombres.setText(objVendedor.getNombres());
        jTxtDireccion.setText(objVendedor.getDireccion());
        jTxtTelefono.setText(objVendedor.getTelefono());
        jTxtCelular.setText(objVendedor.getCelular());
        jTxtComision.setText(String.valueOf(objVendedor.getVentas()));
        jTxtMail.setText(objVendedor.getMail());
        jTxaObs.setText(objVendedor.getObs());
        jLblFoto.setIcon(objVendedor.getImagen());
        jTxtUsuario.setText(objVendedor.getUsuario());
        jTxtClave.setText(objVendedor.getClave());
        jTxtFechaNacimiento.setDate(Generales.stringToDate(objVendedor.getFechaNacimiento(), Generales.ANIO_MES_DIA));
        jCheckBoxEstado.setSelected(objVendedor.isEstado());

    }

    public String Verifica_Datos() {
        String dato = "";

        if (jTxtCI.getText().equals("")) {
            dato = "Verifique los datos en el apartado: CI ";
            jTxtCI.requestFocus();
            return dato;
        }

        if (jTxtNombres.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Nombres ";
            jTxtNombres.requestFocus();
            return dato;
        }

        if (jTxtDireccion.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Dirección ";
            jTxtDireccion.requestFocus();
            return dato;
        }

        if (jTxtTelefono.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Teléfono ";
            jTxtTelefono.requestFocus();
            return dato;
        }

        if (jTxtCelular.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Nº Celular ";
            jTxtCelular.requestFocus();
            return dato;
        }

        if (jTxtComision.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Comisión ";
            jTxtComision.requestFocus();
            return dato;
        }

        if (jTxtMail.getText().equals("")) {
            dato = "Verifique los datos en el apartado: E-Mail ";
            jTxtMail.requestFocus();
            return dato;
        }

        return "";
    }

    public void Deshabilitar() {
        jLblCodigo.setEnabled(false);
        jTxtCI.setEnabled(false);
        jTxtNombres.setEnabled(false);
        jTxtDireccion.setEnabled(false);
        jTxtTelefono.setEnabled(false);
        jTxtCelular.setEnabled(false);
        jTxtComision.setEnabled(false);
        jTxtMail.setEnabled(false);
        jTxaObs.setEnabled(false);
        jTxtUsuario.setEnabled(false);
        jTxtClave.setEnabled(false);
        jTxtFechaNacimiento.setEnabled(false);
        jCheckBoxEstado.setEnabled(false);
        removerFiltroCampos();
    }

    public void Habilitar() {
        jLblCodigo.setEnabled(true);
        jTxtCI.setEnabled(true);
        jTxtNombres.setEnabled(true);
        jTxtDireccion.setEnabled(true);
        jTxtTelefono.setEnabled(true);
        jTxtCelular.setEnabled(true);
        jTxtComision.setEnabled(true);
        jTxtMail.setEnabled(true);
        jTxaObs.setEnabled(true);
        jTxtUsuario.setEnabled(true);
        jTxtClave.setEnabled(true);
        jTxtFechaNacimiento.setEnabled(true);
        jCheckBoxEstado.setEnabled(true);
        setFiltroCampos();
    }

    public void Limpiar_Entradas() {
        removerFiltroCampos();
        jLblCodigo.setText("");
        jTxtCI.setText("");
        jTxtNombres.setText("");
        jTxtDireccion.setText("");
        jTxtTelefono.setText("");
        jTxtCelular.setText("");
        jTxtComision.setText("");
        jTxtMail.setText("");
        jTxaObs.setText("");
        jLblFoto.setIcon(null);
        jTxtUsuario.setText("");
        jTxtClave.setText("");
        jTxtFechaNacimiento.setDate(null);
        jCheckBoxEstado.setSelected(false);
    }

    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }//Fin del mensaje

    private void setFiltroCampos() {
        Generales.setFiltraEntrada(jTxtNombres.getDocument(), FiltraEntrada.SOLO_LETRAS, 30, true);
        Generales.setFiltraEntrada(jTxtDireccion.getDocument(), FiltraEntrada.DEFAULT, 80, true);
        Generales.setFiltraEntrada(jTxtCI.getDocument(), FiltraEntrada.SOLO_NUMEROS, 13, false);
        Generales.setFiltraEntrada(jTxtTelefono.getDocument(), FiltraEntrada.SOLO_NUMEROS, 9, true);
        Generales.setFiltraEntrada(jTxtCelular.getDocument(), FiltraEntrada.SOLO_NUMEROS, 9, true);
    }

    private void removerFiltroCampos() {
        Generales.removerFiltraEntrada(jTxtNombres.getDocument());
        Generales.removerFiltraEntrada(jTxtDireccion.getDocument());
        Generales.removerFiltraEntrada(jTxtCI.getDocument());
        Generales.removerFiltraEntrada(jTxtTelefono.getDocument());
        Generales.removerFiltraEntrada(jTxtCelular.getDocument());

    }

    private cls_vendedor obtenerVendedor() {
        cls_vendedor objCls_vendedor = new cls_vendedor(true);

        objCls_vendedor.setCodigo(jLblCodigo.getText());
        objCls_vendedor.setCI(jTxtCI.getText());
        objCls_vendedor.setNombres(jTxtNombres.getText());
        objCls_vendedor.setDireccion(jTxtDireccion.getText());
        objCls_vendedor.setTelefono(jTxtTelefono.getText());
        objCls_vendedor.setCelular(jTxtCelular.getText());
        objCls_vendedor.setVentas(Float.parseFloat(jTxtComision.getText()));
        objCls_vendedor.setMail(jTxtMail.getText());
        objCls_vendedor.setObs(jTxaObs.getText());
        objCls_vendedor.setFoto(foto);
        objCls_vendedor.setImagen(jLblFoto.getIcon());

        return objCls_vendedor;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTxtCI = new javax.swing.JTextField();
        jTxtNombres = new javax.swing.JTextField();
        jTxtDireccion = new javax.swing.JTextField();
        jTxtTelefono = new javax.swing.JTextField();
        jTxtCelular = new javax.swing.JTextField();
        jTxtComision = new javax.swing.JTextField();
        jTxtMail = new javax.swing.JTextField();
        jLblCodigo = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTxtUsuario = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTxtClave = new javax.swing.JTextField();
        jCheckBoxEstado = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        jTxtFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxaObs = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTblVendedores = new javax.swing.JTable();
        jLblFoto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jBtnGrabar = new javax.swing.JButton();
        jBtnBuscar = new javax.swing.JButton();
        jBtnModificar = new javax.swing.JButton();
        jBtnImprimir = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INGRESAR DATOS DEL VENDEDOR: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel9.setText("Codigo:");

        jLabel10.setText("CI:");

        jLabel11.setText("Celular:");

        jLabel12.setText("Ventas:");

        jLabel13.setText("E-Mail");

        jLabel14.setText("Teléfono:");

        jLabel15.setText("Nombres:");

        jLabel16.setText("Dirección:");

        jTxtCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCIKeyPressed(evt);
            }
        });

        jTxtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtNombresKeyPressed(evt);
            }
        });

        jTxtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtDireccionKeyPressed(evt);
            }
        });

        jTxtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtTelefonoKeyPressed(evt);
            }
        });

        jTxtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCelularKeyPressed(evt);
            }
        });

        jTxtComision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtComisionKeyPressed(evt);
            }
        });

        jTxtMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMailKeyPressed(evt);
            }
        });

        jLblCodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblCodigo.setForeground(new java.awt.Color(255, 0, 0));

        jLabel17.setText("Usuario:");

        jTxtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtUsuarioKeyPressed(evt);
            }
        });

        jLabel18.setText("Clave:");

        jTxtClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtClaveKeyPressed(evt);
            }
        });

        jCheckBoxEstado.setText("Activo");

        jLabel19.setText("Fecha Nacimiento:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtTelefono)
                                    .addComponent(jTxtCelular)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(10, 10, 10)
                                .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTxtCI)
                                .addGap(45, 45, 45)
                                .addComponent(jCheckBoxEstado))
                            .addComponent(jTxtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtUsuario, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTxtMail, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTxtComision, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTxtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTxtCI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTxtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTxtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTxtComision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTxtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTxtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jTxtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Otros datos:");

        jTxaObs.setColumns(20);
        jTxaObs.setRows(5);
        jScrollPane1.setViewportView(jTxaObs);

        jTblVendedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblVendedoresMouseClicked(evt);
            }
        });
        jTblVendedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTblVendedoresKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTblVendedores);

        jLblFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblFotoMouseClicked(evt);
            }
        });

        jBtnGrabar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save.png"))); // NOI18N
        jBtnGrabar.setText("Grabar");
        jBtnGrabar.setMaximumSize(new java.awt.Dimension(121, 53));
        jBtnGrabar.setMinimumSize(new java.awt.Dimension(121, 53));
        jBtnGrabar.setPreferredSize(new java.awt.Dimension(121, 53));
        jBtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGrabarActionPerformed(evt);
            }
        });

        jBtnBuscar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ico_search.jpg"))); // NOI18N
        jBtnBuscar.setText("Buscar");
        jBtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarActionPerformed(evt);
            }
        });

        jBtnModificar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icon_edit_large.gif"))); // NOI18N
        jBtnModificar.setText("Modificar");
        jBtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnModificarActionPerformed(evt);
            }
        });

        jBtnImprimir.setBackground(new java.awt.Color(255, 255, 255));
        jBtnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconPrint.gif"))); // NOI18N
        jBtnImprimir.setText("Imprimir");
        jBtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnImprimirActionPerformed(evt);
            }
        });

        jBtnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/RTEmagicC_EliminarPag_10_png.png"))); // NOI18N
        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jBtnSalir.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eqsl_exit.png"))); // NOI18N
        jBtnSalir.setText("Salir    ");
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnGrabar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jBtnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton1.setText("LIMPIAR ENTRADAS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnModificarActionPerformed
        Modificar();
    }//GEN-LAST:event_jBtnModificarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Limpiar_Entradas();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGrabarActionPerformed
        String dato = Verifica_Datos();
        if (dato.equalsIgnoreCase("")) {

            //Generando la clace para manejar unj registro acaemico
            objVendedor = obtenerVendedor();
            //Verificando si el codigo existe dentro del arreglo
            if (objArreglo.Buscar(objVendedor.getCodigo()) != -1) {
                mensaje("Codigo Repetido");//Se muestrea el mensaje
            } else {

                objVendedor.setCodigo(Generar_Codigo());
                //Se agrega el objeto al arreglo
                objArreglo.Agregar(objVendedor);
                //Insertando la ifnromacion en la tabla
                Insertar(0, objVendedor.getCodigo(), objVendedor.getCI(), 
                        objVendedor.getNombres(),objVendedor.getUsuario(),
                        objVendedor.getDireccion(), objVendedor.getTelefono(),
                        objVendedor.getCelular(), objVendedor.getVentas(), 
                        objVendedor.getMail());
                //Limpiando las entradas
                Limpiar_Entradas();
                //Grabando la ifnromacion en el archivo binario
                Grabar();
                //Actualizando la tabla de registros
                Actualizar_Tabla();

            }//Fin del else
        } else {
            mensaje(dato);
            Habilitar();
            jTxtCI.requestFocus();
        }
    }//GEN-LAST:event_jBtnGrabarActionPerformed

    private void jBtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarActionPerformed
        Consultar();
        
    }//GEN-LAST:event_jBtnBuscarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        Eliminar();
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jTxtCIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCIKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) 
            jTxtNombres.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtCIKeyPressed

    private void jTxtNombresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombresKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) 
            jTxtDireccion.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_jTxtNombresKeyPressed

    private void jTxtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDireccionKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) 
            jTxtTelefono.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_jTxtDireccionKeyPressed

    private void jTxtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTelefonoKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) 
            jTxtCelular.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_jTxtTelefonoKeyPressed

    private void jTxtCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCelularKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) 
            jTxtComision.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_jTxtCelularKeyPressed

    private void jTxtComisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtComisionKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) 
            jTxtMail.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_jTxtComisionKeyPressed

    private void jTxtMailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMailKeyPressed
        //if(evt.getKeyCode()==evt.VK_ENTER)
        //  jCbxDepartamento.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_jTxtMailKeyPressed

    private void jTblVendedoresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTblVendedoresKeyPressed

    }//GEN-LAST:event_jTblVendedoresKeyPressed

    private void jTblVendedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblVendedoresMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() >= 1) {
            if (jTblVendedores.getSelectedRow() != -1) {
                Imprimir_Datos(jTblVendedores.getSelectedRow());
            }
        }
    }//GEN-LAST:event_jTblVendedoresMouseClicked

    private void jTxtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtUsuarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtUsuarioKeyPressed

    private void jTxtClaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtClaveKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtClaveKeyPressed

    private void jLblFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblFotoMouseClicked
        // TODO add your handling code here:
        JFileChooser dlg = new JFileChooser();
        //Abre la ventana de dialogo
        int option = dlg.showOpenDialog(this);
        //Si hace click en el boton abrir del dialogo
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                foto = new FileInputStream(dlg.getSelectedFile());
                Image icono = ImageIO.read(dlg.getSelectedFile()).getScaledInstance(jLblFoto.getWidth(), jLblFoto.getHeight(), Image.SCALE_DEFAULT);
                jLblFoto.setIcon(new ImageIcon(icono));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLblFotoMouseClicked

    private void jBtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImprimirActionPerformed
        // TODO add your handling code here:
        new f_vendedores().setVisible(true);
    }//GEN-LAST:event_jBtnImprimirActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vendedores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnBuscar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGrabar;
    private javax.swing.JButton jBtnImprimir;
    private javax.swing.JButton jBtnModificar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBoxEstado;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblCodigo;
    private javax.swing.JLabel jLblFoto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTblVendedores;
    private javax.swing.JTextArea jTxaObs;
    private javax.swing.JTextField jTxtCI;
    private javax.swing.JTextField jTxtCelular;
    private javax.swing.JTextField jTxtClave;
    private javax.swing.JTextField jTxtComision;
    private javax.swing.JTextField jTxtDireccion;
    private com.toedter.calendar.JDateChooser jTxtFechaNacimiento;
    private javax.swing.JTextField jTxtMail;
    private javax.swing.JTextField jTxtNombres;
    private javax.swing.JTextField jTxtTelefono;
    private javax.swing.JTextField jTxtUsuario;
    // End of variables declaration//GEN-END:variables

}

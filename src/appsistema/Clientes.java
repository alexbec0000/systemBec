package appsistema;
//Declaracion de librerias
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pck_controller.ClienteController;
import pck_entidades.Arreglo_Cliente;
import pck_entidades.cls_cliente;
import pck_utilidades.CustomImageIcon;
import pck_utilidades.FiltraEntrada;
import pck_utilidades.Generales;

public final class Clientes extends javax.swing.JFrame 
{
    //Declarando las claces
    cls_cliente objCliente;
    Arreglo_Cliente objArreglo;
    DefaultTableModel miModelo2;
    DefaultComboBoxModel cbxProv;
    String[] cabecera = {"Nº","Código", "RUC", "Nombre", "Dirección", "Razón S.", "Teléfono", "Celular", "Fecha Ingreso", "Forma P.", "Linea C.", "E - Mail"};
    String[][] data = {};
    ClienteController objClienteController;
    FileInputStream foto;
    //Variables globales
    int num = 0;

    public Clientes()
    {
        initComponents();
        Deshabilitar();
        objClienteController =new ClienteController();
        miModelo2 = new DefaultTableModel(data, cabecera);
        jTblClientes.setModel(miModelo2);
        objArreglo = new Arreglo_Cliente();
        cbxProv = new DefaultComboBoxModel();
        Cargar_Data();
        Actualizar_Tabla();
        //jTxtRuc.requestFocus();
        //jLabel2.setVisible(false);
    }

    public String Generar_Codigo ()
    {
        int cod = (int)(Math.random()*(99999-10000))+10000;
        String codig = String.valueOf(cod);
        return codig;
    }

    public void Cargar_Data()
    {
        //Lee la data del objeto serializable
        try
        {
            objArreglo.Elimina();
            ArrayList <cls_cliente> listCli= objClienteController.cargarClientes();
            for (cls_cliente listCli1 : listCli) {
                objArreglo.Agregar(listCli1);
            }
        }//Fin del try
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar los Clientes: "+e);
        }
    }

//METODO PARA GRABAR LA INFORMACION DEL ARREGLO DE OBJETOS AL ARCHIVO BINARIO
    public void Grabar()
    {
        //Guarda la data en el archivo serializado
        try
        {
            for(int i=0;i<objArreglo.Numero_Clientes();i++)
            {
                if(objArreglo.getCliente(i).isEditar())
                    objClienteController.grabarRegistro(objArreglo.getCliente(i));
            }
            
            Cargar_Data();
            Actualizar_Tabla();

        }//Fin del try

        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error en la grabacion sobre el arreglo: "+ e);
        }
    }//Fin de Grabar

//METODOS PARA MANIPULAR LA TABLA Y EL jLIST
    public void Actualizar_Tabla()
    {
        //Vaciando la informacion de la tabla
        Vaciar_Tabla();
        //Capturando el tamaño del arreglo
        int n = objArreglo.Numero_Clientes();

        for(int i=0; i < n; i++)
        {
            //Extrallendo informacion de cada objeto del arreglo
            String codigo = objArreglo.getCliente(i).getCodigo();
            String RUC = objArreglo.getCliente(i).getRUC();
            String nombre = objArreglo.getCliente(i).getNombre();
            String direccion = objArreglo.getCliente(i).getDireccion();
            String razon = objArreglo.getCliente(i).getRazon();
            String telefono = objArreglo.getCliente(i).getTelefono();
            String celular = objArreglo.getCliente(i).getCelular();
            String fechaIngreso = objArreglo.getCliente(i).getFechaIngreso();
            String forma_p = objArreglo.getCliente(i).getForma_p();
            String credito = objArreglo.getCliente(i).getCredito();
            String mail = objArreglo.getCliente(i).getMail();

            Insertar (i+1, codigo, RUC, nombre, direccion, razon, telefono, celular, fechaIngreso, forma_p, credito, mail);

        }//Fin del for
    }

    public void Vaciar_Tabla()
    {
        int filas = jTblClientes.getRowCount();
        for(int i =0; i<filas; i++)
        {
            miModelo2.removeRow(0); //Removiendo la fila de la tabla
        }//Fin del for
    }

    public  void Insertar(int  num, String codigo, String RUC, String nombre, String direccion, String razon,
            String telefono, String celular, String fechaIngreso, String forma_p, String credito, String mail)
    {

        //Llenando la fila de la tabla
        Object fila [] = {num, codigo, RUC, nombre, direccion, razon, telefono, celular, fechaIngreso, forma_p, credito, mail};
         //Agregando la fila a nuestro modelo de tabla
        miModelo2.addRow(fila);
    }


    //METODOS UTILIZADOS PARA LAS CONSULTAS, ELIMINACION Y ACTUALIZACION DE DATOS
    public void Modificar()
    {
        String codigo = jLblCodigo.getText();//JOptionPane.showInputDialog("Ingresar: Código del proveedor").toUpperCase().trim();
        String dato = Verifica_Datos();
        if(dato.equalsIgnoreCase("")){
            if(codigo.equalsIgnoreCase(""))
            {
                mensaje("ERROR: Códico incorrecto");
                Habilitar();
                jTxtNombre.requestFocus();
            }
            else {
                //Se vuelve a buscar el codigo para no repetir el mismo
                int p = objArreglo.Buscar(codigo);
                //Se leen los datos de entrada de los text file
                codigo = jLblCodigo.getText();
                String RUC   = jTxtRuc.getText();
                String nombre = jTxtNombre.getText();
                String direccion  = jTxtDireccion.getText();
                String razon  = jTxtRazon.getText();
                String telefono  = jTxtTelefono.getText();
                String celular  = jTxtCelular.getText();
                String fechaIngreso  = jTxtFechaIngreso.getText();
                String forma_p  = jTxtForma_p.getText();
                String credito =   jTxtCredito.getText();
                String mail  = jTxtMail.getText();
                String obs = jTxaObs.getText();

                //Generando la clace para manejar un Registro academico
                objCliente = new cls_cliente (codigo, RUC, nombre, direccion, 
                  razon, telefono, celular, fechaIngreso, forma_p, credito, mail, obs, foto, true);
                //Verificando si el codigo existe dentro del arreglo

                if(p==-1)//El codigo es nuevo
                    objArreglo.Agregar(objCliente);

                else//Codigo ya existente
                    objArreglo.Reemplaza(p, objCliente);

                //Limpiando las entradas
                Limpiar_Entradas();
                //Grabamos la informacion en el archivo binario
                Grabar();
                //Actualizando la tabla
                //Actualizar_Tabla();
                //Colocando el cursor el en primer area de texto
                jTxtRuc.requestFocus();
            }//Fin de else
        }
         else mensaje ("Debe llenar los campos correspondientes del cliente que desea modificar, "+dato);
    }//Fin de modificar

    public void Eliminar()
    {
        //Se llama al metodo consultar para ver los datos a borrar
        Consultar();
        //Se llama al metodo busca en el arreglo que devuelve la posicion del codigo buscado
        int p = objArreglo.Buscar(jLblCodigo.getText().toUpperCase());
        //Comprobando que el codigo existe
        if(p != -1)
        {
            int R = JOptionPane.showConfirmDialog(this, "Está seguro de eliminar éste registro?", "Responder", 0);
            //Si la respuesta es AFIRMATIVA:
            if(R==0)
            {
                //Eliminamos el objeto en la posicion capturada (Posicion "p")
                objArreglo.Elimina(p);
                Limpiar_Entradas();//Limpiando las entradas
                Grabar();//Grabamos la informacion en el archivo binario
                //Actualizar_Tabla();//Actualizamos la tabla
                mensaje("Los datos se eliminaron correctamente");
                jTxtRuc.requestFocus();//Colocando el cursor en el primer area de texto
            }//Fin del if

        //En caso el codigo no exista
        else
            JOptionPane.showMessageDialog(null, "El código del alumno no existe");
        }//Fin del if(Verificando que existe el codigo)
    }//Fin del metodo Eliminar

    public void Consultar()
    {
        //Pedimos el código
        String codigo = JOptionPane.showInputDialog("Ingresar: Código").toUpperCase().trim();
        //Se llama al metodo busca que devuelve la posicion del codigo buscado
        int p = objArreglo.Buscar(codigo);
        //Comprobando si el codigo existe
        if(p==-1)//Cuando el codigo no exite
        {
            mensaje("Por favor verificar el código");
            Limpiar_Entradas();
        }
        else//Cuando existe el codigo
        {
            Imprimir_Datos(p);
        }//Fin del else
    }//Fin de Consultar

    //METODOS SECUNDARIOS PARA EL DISEÑO DEL FORMULARIO
    public void Deshabilitar()
    {
        jLblCodigo.setEnabled(false);
        jTxtRuc.setEnabled(false);
        jTxtNombre.setEnabled(false);
        jTxtDireccion.setEnabled(false);
        jTxtRazon.setEnabled(false);
        jTxtTelefono.setEnabled(false);
        jTxtCelular.setEnabled(false);
        jTxtFechaIngreso.setEnabled(false);
        jTxtForma_p.setEnabled(false);
        jTxtCredito.setEnabled(false);
        jTxtMail.setEnabled(false);
        jLblFoto.setEnabled(false);
        jTxaObs.setEnabled(false);
        removerFiltroCampos();
    }

    public void Habilitar()
    {
        jLblCodigo.setEnabled(true);
        jTxtRuc.setEnabled(true);
        jTxtNombre.setEnabled(true);
        jTxtDireccion.setEnabled(true);
        jTxtRazon.setEnabled(true);
        jTxtTelefono.setEnabled(true);
        jTxtCelular.setEnabled(true);
        jTxtFechaIngreso.setEnabled(true);
        jTxtForma_p.setEnabled(true);
        jTxtCredito.setEnabled(true);
        jTxtMail.setEnabled(true);
        jLblFoto.setEnabled(true);
        jTxaObs.setEnabled(true);
        jTxtRuc.requestFocus();
        setFiltroCampos();
    }

    //METODO PARA MOSTRAR LOS MENSAJES DEL APLICATIVO
    public void mensaje(String texto)
    {
        JOptionPane.showMessageDialog(this, texto);
    }//Fin del mensaje

    public void Limpiar_Entradas()
    {
        removerFiltroCampos();
        jLblCodigo.setText("");
        jTxtRuc.setText("");
        jTxtNombre.setText("");
        jTxtDireccion.setText("");
        jTxtRazon.setText("");
        jTxtTelefono.setText("");
        jTxtCelular.setText("");
        jTxtFechaIngreso.setText("");
        jTxtForma_p.setText("");
        jTxtCredito.setText("");
        jTxtMail.setText("");
        jLblFoto.setIcon(null);
        jTxaObs.setText("");
        jTxtRuc.requestFocus();
    }

    public void Imprimir_Datos(int pos)
    {
         Limpiar_Entradas();
         Habilitar();
        //Se extrae todo el objeto con toda la informacion
        objCliente = objArreglo.getCliente(pos);
        //Se extrae la ifnromacion de los campos del objeto
        String codigo = objCliente.getCodigo();
        String RUC = objCliente.getRUC();
        String nombre  = objCliente.getNombre();
        String direccion   = objCliente.getDireccion();
        String razon   = objCliente.getRazon();
        String telefono  = objCliente.getTelefono();
        String celular    = objCliente.getCelular();
        String fechaIngreso    = objCliente.getFechaIngreso();
        String forma_p    = objCliente.getForma_p();
        String credito   = objCliente.getCredito();
        String mail    = objCliente.getMail();
        String obs   = objCliente.getObs();

        //Colocando la informacion en los objetos
        jLblCodigo.setText(codigo);
        jTxtRuc.setText(RUC);
        jTxtNombre.setText(nombre);
        jTxtDireccion.setText(direccion);
        jTxtRazon.setText(razon);
        jTxtTelefono.setText(telefono);
        jTxtCelular.setText(celular);
        jTxtFechaIngreso.setText(fechaIngreso);
        jTxtForma_p.setText(forma_p);
        jTxtCredito.setText(credito);
        jTxtMail.setText(mail);
        jTxaObs.setText(obs);
        
        try{
            jLblFoto.setIcon(Generales.getFoto(codigo));
        }
        catch(Exception ex){
            //ex.printStackTrace();
            jLblFoto.setIcon(new CustomImageIcon(getClass().getResource("/recursos/icono_cliente.jpg")));
        }
        
        jLblFoto.updateUI();
                    
        Habilitar();
    }

    public String Verifica_Datos()
    {
        String dato="";

        if(jTxtRuc.getText().equals(""))
        {dato="Verifique los datos en el apartado: Nº RUC ";
            jTxtRuc.requestFocus();
            return dato;
        }

        if(jTxtNombre.getText().equals(""))
        {dato="Verifique los datos en el apartado: Nombre ";
        jTxtNombre.requestFocus();
        return dato;
        }

        if(jTxtDireccion.getText().equals(""))
        {dato="Verifique los datos en el apartado: Direccion ";
        jTxtDireccion.requestFocus();
        return dato;
        }

        if(jTxtRazon.getText().equals(""))
        {dato="Verifique los datos en el apartado: Razon Social ";
        jTxtRazon.requestFocus();
        return dato;
        }

        if(jTxtTelefono.getText().equals(""))
        {dato="Verifique los datos en el apartado: Nº Telefono";
        jTxtTelefono.requestFocus();
        return dato;
        }

        if(jTxtCelular.getText().equals(""))
        {dato="Verifique los datos en el apartado: Nº Celular ";
        jTxtCelular.requestFocus();
        return dato;
        }

        if(jTxtFechaIngreso.getText().equals(""))
        {dato="Verifique los datos en el apartado: Fecha Ingreso ";
        jTxtFechaIngreso.requestFocus();
        return dato;
        }

        if(jTxtForma_p.getText().equals(""))
        {dato="Verifique los datos en el apartado: Forma de pago ";
        jTxtForma_p.requestFocus();
        return dato;
        }

        if(jTxtCredito.getText().equals(""))
        {dato="Verifique los datos en el apartado: Linea de credito ";
        jTxtCredito.requestFocus();
        return dato;
        }

        if(jTxtMail.getText().equals(""))
        {dato="Verifique los datos en el apartado: E - Mail ";
        jTxtMail.requestFocus();
        return dato;
        }

        return "";
    }

    private void setFiltroCampos()
    {        
        Generales.setFiltraEntrada(jTxtRazon.getDocument(), FiltraEntrada.SOLO_LETRAS,30,true);
        Generales.setFiltraEntrada(jTxtDireccion.getDocument(), FiltraEntrada.DEFAULT,80,true);
        Generales.setFiltraEntrada(jTxtRuc.getDocument(), FiltraEntrada.SOLO_NUMEROS,13,false);
        Generales.setFiltraEntrada(jTxtTelefono.getDocument(), FiltraEntrada.SOLO_NUMEROS,9,true);
        Generales.setFiltraEntrada(jTxtCelular.getDocument(), FiltraEntrada.SOLO_NUMEROS,9,true);
    } 
     
    private void removerFiltroCampos()
    {
        Generales.removerFiltraEntrada(jTxtRazon.getDocument());
        Generales.removerFiltraEntrada(jTxtDireccion.getDocument());
        Generales.removerFiltraEntrada(jTxtRuc.getDocument());
        Generales.removerFiltraEntrada(jTxtTelefono.getDocument());
        Generales.removerFiltraEntrada(jTxtCelular.getDocument());  
    }   
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTxtRuc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jTxtRazon = new javax.swing.JTextField();
        jTxtTelefono = new javax.swing.JTextField();
        jTxtCelular = new javax.swing.JTextField();
        jTxtFechaIngreso = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTxtForma_p = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTxtCredito = new javax.swing.JTextField();
        jTxtMail = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLblFoto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxaObs = new javax.swing.JTextArea();
        jBtnIngresaDatos = new javax.swing.JButton();
        jLblCodigo = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTxtDireccion = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jBtnGrabar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("CLIENTES");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INGRESAR LOS DATOS DEL CLIENTE: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Codigo:");

        jLabel3.setText("RUC:");

        jLabel5.setText("Nombre:");

        jLabel6.setText("Dirección:");

        jLabel7.setText("Razón social:");

        jLabel8.setText("Nº Telefono:");

        jLabel9.setText("Nº Celular:");

        jLabel10.setText("Fecha Ingreso:");

        jTxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtNombreActionPerformed(evt);
            }
        });

        jLabel11.setText("Forma de pago:");

        jLabel12.setText("Línea Crédito:");

        jLabel13.setText("E - Mail:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Otros Datos:");

        jLblFoto.setBackground(new java.awt.Color(255, 255, 204));
        jLblFoto.setPreferredSize(new java.awt.Dimension(100, 180));
        jLblFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblFotoMouseClicked(evt);
            }
        });

        jTxaObs.setColumns(20);
        jTxaObs.setRows(5);
        jScrollPane1.setViewportView(jTxaObs);

        jBtnIngresaDatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/plus_icon_compare.gif"))); // NOI18N
        jBtnIngresaDatos.setText("INGRESAR DATOS DEL CLIENTE");
        jBtnIngresaDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIngresaDatosActionPerformed(evt);
            }
        });

        jLblCodigo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLblCodigo.setForeground(new java.awt.Color(255, 0, 0));

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setText("LIMPIAR ENTRADAS");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTxtDireccion.setColumns(20);
        jTxtDireccion.setRows(5);
        jScrollPane3.setViewportView(jTxtDireccion);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnIngresaDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTxtFechaIngreso)
                                            .addComponent(jTxtRazon, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTxtForma_p)
                                            .addComponent(jTxtCredito)
                                            .addComponent(jTxtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(jLabel8))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel9)))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTxtTelefono)
                                            .addComponent(jTxtCelular)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addGap(55, 55, 55)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(jTxtRuc))
                                            .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10))
                                .addGap(0, 263, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane3)))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTxtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTxtRazon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTxtFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(jTxtForma_p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(jTxtCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jTxtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jTxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTxtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addComponent(jBtnIngresaDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel10.getAccessibleContext().setAccessibleDescription("");

        jBtnGrabar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save.png"))); // NOI18N
        jBtnGrabar.setText("Grabar");
        jBtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGrabarActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/RTEmagicC_EliminarPag_10_png.png"))); // NOI18N
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ico_search.jpg"))); // NOI18N
        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconPrint.gif"))); // NOI18N
        jButton4.setText("Imprimir");

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icon_edit_large.gif"))); // NOI18N
        jButton5.setText("Modificar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eqsl_exit.png"))); // NOI18N
        jButton6.setText("Salir");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnGrabar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnGrabar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTblClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Consultar();     // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jBtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGrabarActionPerformed
       String dato = Verifica_Datos();
       if(dato.equalsIgnoreCase(""))
       {
        //Se leen los datos de entrada de los text file
         String codigo = jLblCodigo.getText();
         String RUC   = jTxtRuc.getText();
         String nombre = jTxtNombre.getText();
         String direccion  = jTxtDireccion.getText();
         String razon  = jTxtRazon.getText();
         String telefono  = jTxtTelefono.getText();
         String celular  = jTxtCelular.getText();
         String fechaIngreso  = jTxtFechaIngreso.getText();
         String forma_p  = jTxtForma_p.getText();
         String credito =   jTxtCredito.getText();
         String mail  = jTxtMail.getText();
         String obs = jTxaObs.getText();
         //Icon foto = jLblFoto.getIcon();


         //Generando la clace para manejar un Registro academico
         objCliente = new cls_cliente (codigo, RUC,nombre, direccion, razon, telefono, 
                 celular, fechaIngreso, forma_p, credito, mail, obs, foto, false);
         //Verificando si el codigo existe dentro del arreglo
         if(objArreglo.Buscar(objCliente.getCodigo())!= -1)
             mensaje("Codigo Repetido");//Se muestrea el mensaje

         else {
             //Instanciamos una clace con diferente codigo para el caso sea un nuevo registro
             String cod =  Generar_Codigo();
             objCliente = new cls_cliente (cod, RUC, nombre, direccion, razon, 
                     telefono, celular, fechaIngreso, forma_p, credito, mail, obs, foto, true);
             //Se agrega el objeto al arreglo
             objArreglo.Agregar(objCliente);
             //Insertando la ifnromacion en la tabla
             Insertar (0, codigo, RUC, nombre, direccion, razon, telefono, celular, fechaIngreso, forma_p, credito, mail);
             //Limpiando las entradas
             Limpiar_Entradas();
             //Grabando la ifnromacion en el archivo binario
             Grabar();
             //Actualizando la tabla de registros
             //Actualizar_Tabla();
         }//Fin del else
       }
       else
       {
       mensaje(dato);
       Habilitar();
       jTxtNombre.requestFocus();
       }
    }//GEN-LAST:event_jBtnGrabarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Modificar();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Eliminar();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//jCbxVendedor.setSelectedItem("nombres");
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        setVisible(false);
}//GEN-LAST:event_jButton6ActionPerformed

    private void jTblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblClientesMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() >= 1)
        {
            if(jTblClientes.getSelectedRow() != -1)
            {
                Imprimir_Datos(jTblClientes.getSelectedRow());
            }
        }
    }//GEN-LAST:event_jTblClientesMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Limpiar_Entradas();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jBtnIngresaDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIngresaDatosActionPerformed
        Habilitar();
        Limpiar_Entradas();
    }//GEN-LAST:event_jBtnIngresaDatosActionPerformed

    private void jTxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtNombreActionPerformed

    private void jLblFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblFotoMouseClicked
        // TODO add your handling code here:
        
        JFileChooser dlg = new JFileChooser();
        //Abre la ventana de dialogo
        int option = dlg.showOpenDialog(this);
        //Si hace click en el boton abrir del dialogo
        if(option==JFileChooser.APPROVE_OPTION) {
            try {
                foto= new FileInputStream(dlg.getSelectedFile());
                Image icono = ImageIO.read(dlg.getSelectedFile()).getScaledInstance(jLblFoto.getWidth(), jLblFoto.getHeight(), Image.SCALE_DEFAULT);
                jLblFoto.setIcon(new ImageIcon(icono));
                jLblFoto.updateUI();
            } catch (FileNotFoundException ex) {ex.printStackTrace();}
            catch (IOException ex){ex.printStackTrace();}
        }
    }//GEN-LAST:event_jLblFotoMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnGrabar;
    private javax.swing.JButton jBtnIngresaDatos;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblCodigo;
    private javax.swing.JLabel jLblFoto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTblClientes;
    private javax.swing.JTextArea jTxaObs;
    private javax.swing.JTextField jTxtCelular;
    private javax.swing.JTextField jTxtCredito;
    private javax.swing.JTextArea jTxtDireccion;
    private javax.swing.JTextField jTxtFechaIngreso;
    private javax.swing.JTextField jTxtForma_p;
    private javax.swing.JTextField jTxtMail;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtRazon;
    private javax.swing.JTextField jTxtRuc;
    private javax.swing.JTextField jTxtTelefono;
    // End of variables declaration//GEN-END:variables

}

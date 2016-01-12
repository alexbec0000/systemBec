package appsistema;
//Declaracion de librerias
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.print.PrinterException;
import pck_controller.ProveedorController;
import pck_entidades.Arreglo_Proveedor;
import pck_entidades.cls_proveedor;
import pck_utilidades.FiltraEntrada;
import pck_utilidades.Generales;

public final class Proveedores extends javax.swing.JFrame {
//Declarando las claces
cls_proveedor objProveedores;
Arreglo_Proveedor objArreglo;
DefaultTableModel miModelo;
DefaultComboBoxModel cbxProv;
String[] cabecera = {"Nº","Código", "RUC","Nombre", "Direccion", "Telefono", "Representante", "Telefono R.", "Celular R."};
String[][] data = {};
ProveedorController objProveedorController;
//Variables globales
int num = 0;

    public Proveedores() {
       initComponents();
       Deshabilitar();
       objProveedorController =new ProveedorController();
       miModelo = new DefaultTableModel(data, cabecera);
       jTblProveedores.setModel(miModelo);
       cbxProv = new DefaultComboBoxModel();
       objArreglo = new Arreglo_Proveedor();
       Cargar_Data();
       Actualizar_Tabla();
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
            ArrayList <cls_proveedor> listProv= objProveedorController.cargarProveedores();
            
            for (cls_proveedor listProv1 : listProv) {
                objArreglo.Agregar(listProv1);
            }
        }//Fin del try

        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar los proveedores: "+e);
        }
    }

    //METODO PARA GRABAR LA INFORMACION DEL ARREGLO DE OBJETOS AL ARCHIVO BINARIO
    public void Grabar()
    {
        //Guarda la data en el archivo serializado
        try
        {
            for(int i=0;i<objArreglo.Numero_Proveedor();i++)
                objProveedorController.grabarRegistro(objArreglo.getProveedor(i));
        
        }//Fin del try

        catch(Exception e)
        {
        JOptionPane.showMessageDialog(null,"Error en la grabacion sobre el arreglo: "+ e);
        }
    }//Fin de Grabar


    //METODOS PARA MANIPULAR LA TABLA Y LA LISTA
    public void Actualizar_Tabla()
    {
        //Vaciando la informacion de la tabla
        Vaciar_Tabla();
        //Capturando el tamaño del arreglo
        int n = objArreglo.Numero_Proveedor();

        for(int i=0; i < n; i++)
        {
            //Extrallendo informacion de cada objeto del arreglo
            String codigo = objArreglo.getProveedor(i).getCodigo();
            String ruc = objArreglo.getProveedor(i).getRuc();
            String nombre = objArreglo.getProveedor(i).getNombre();
            String direccion = objArreglo.getProveedor(i).getDireccion();
            String telefono = objArreglo.getProveedor(i).getTelefono();
            String fax = objArreglo.getProveedor(i).getFax();
            String web = objArreglo.getProveedor(i).getWeb();
            String mail = objArreglo.getProveedor(i).getMail();
            String nombre_r = objArreglo.getProveedor(i).getNombre_r();
            String telefono_r = objArreglo.getProveedor(i).getTelefono_r();
            String celular_r = objArreglo.getProveedor(i).getCelular_r();

            //Insertando la indormacion en la tabla

            Insertar (i+1, codigo, ruc,nombre, direccion, telefono, fax, web, mail, nombre_r, telefono_r, celular_r);

        }//Fin del for
    }

    public void Vaciar_Tabla()
    {
        int filas = jTblProveedores.getRowCount();
        for(int i =0; i<filas; i++)
        {
            miModelo.removeRow(0); //Removiendo la fila de la tabla
        }//Fin del for
    }

    public  void Insertar(int  num, String codigo, String ruc, String nombre, String direccion, String telefono, String fax, String web, String mail, String nombre_r, String telefono_r, String celular_r)
    {

        //Llenando la fila de la tabla
        Object fila [] = {num, codigo, ruc, nombre.toUpperCase(), direccion, telefono, nombre_r, telefono_r, celular_r};
         //Agregando la fila a nuestro modelo de tabla
        miModelo.addRow(fila);
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
                jTxtRuc.requestFocus();
            }
            else{
                //Se vuelve a buscar el codigo para no repetir el mismo
                int p = objArreglo.Buscar(codigo);
                //Se leen los datos de entrada de los text file
                    codigo = jLblCodigo.getText();
                    String ruc = jTxtRuc.getText();
                    String nombre = jTxtNombrePr.getText();
                    String direccion = jTxtDireccion.getText();
                    String telefono = jTxtTelefonoPr.getText();
                    String fax = jTxtFax.getText();
                    String web = jTxtWeb.getText();
                    String mail = jTxtMail.getText();
                    String nombre_r = jTxtNombreRep.getText();
                    String telefono_r = jTxtTelefonoRep.getText();
                    String celular_r = jTxtCelularRep.getText();
                    String obs = jTxaObs.getText();

                //Generando la clace para manejar un Registro 
                objProveedores = new cls_proveedor (codigo, ruc, nombre, direccion, telefono, fax, web, mail, nombre_r, telefono_r, celular_r, obs);
                //Verificando si el codigo existe dentro del arreglo

                if(p==-1)//El codigo es nuevo
                objArreglo.Agregar(objProveedores);

                else//Codigo ya existente
                objArreglo.Reemplaza(p, objProveedores);

                //Limpiando las entradas
                Limpiar_Entradas();
                //Grabamos la informacion en el archivo binario
                Grabar();
                //Actualizando la tabla
                Actualizar_Tabla();
                //Colocando el cursor el en primer area de texto
                jTxtRuc.requestFocus();
              }//Fin de else
           }
           else
               mensaje("Debe llenar los campos correspondientes del proveedor que desea modificar");
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
                Actualizar_Tabla();//Actualizamos la tabla
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
        String codigo = JOptionPane.showInputDialog("Ingresar: Código del proveedor").toUpperCase().trim();
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
            //Mostramos los datos en el formulario
            Imprimir_Datos(p);
        }//Fin del else
    }//Fin de Consultar

    ////////////////////////METODOS SECUNDARIOS QUE PERMITEN UN MEJOR MANEJO CON EL DISEÑO////////////////////////

    //IMPRIMIR DATOS BUSCADOS
    public void Imprimir_Datos(int pos)
    {
        Habilitar ();
        //Instanciamos el objeto
        objProveedores = objArreglo.getProveedor(pos);
        //Se extrae la ifnromacion de los campos del objeto
        String codigo = objProveedores.getCodigo();
        String ruc = objProveedores.getRuc();
        String nombre = objProveedores.getNombre();
        String direccion = objProveedores.getDireccion();
        String telefono = objProveedores.getTelefono();
        String fax = objProveedores.getFax();
        String web = objProveedores.getWeb();
        String mail = objProveedores.getMail();
        String nombre_r = objProveedores.getNombre_r();
        String telefono_r = objProveedores.getTelefono_r();
        String celular_r = objProveedores.getCelular_r();
        String obs = objProveedores.getObs();

        //Colocando la informacion en los objetos
        jLblCodigo.setText(codigo);
        jTxtRuc.setText(ruc);
        jTxtNombrePr.setText(nombre);
        jTxtDireccion.setText(direccion);
        jTxtTelefonoPr.setText(telefono);
        jTxtFax.setText(fax);
        jTxtWeb.setText(web);
        jTxtMail.setText(mail);
        jTxtNombreRep.setText(nombre_r);
        jTxtTelefonoRep.setText(telefono_r);
        jTxtCelularRep.setText(celular_r);
        jTxaObs.setText(obs);
    }

    //Verificamos los datos de entrada si están correctamente ingresados
    public String Verifica_Datos ()
    {
        String dato="";

        if(jTxtRuc.getText().equals(""))
        {dato="Verifique los datos en el apartado: Nº RUC ";
            jTxtRuc.requestFocus();
            return dato;
        }

        if(jTxtNombrePr.getText().equals(""))
        {dato="Verifique los datos en el apartado: Nombre ";
            jTxtNombrePr.requestFocus();
            return dato;
        }

        if(jTxtDireccion.getText().equals(""))
        {dato="Verifique los datos en el apartado: Direccion ";
            jTxtDireccion.requestFocus();
            return dato;
        }

        if(jTxtTelefonoPr.getText().equals(""))
        {dato="Verifique los datos en el apartado: Telefono del proveedor ";
            jTxtTelefonoPr.requestFocus();
            return dato;
        }

        if(jTxtFax.getText().equals(""))
        {dato="Verifique los datos en el apartado: Nº Fax ";
            jTxtFax.requestFocus();
            return dato;
        }

        if(jTxtWeb.getText().equals(""))
        {dato="Verifique los datos en el apartado: Página W ";
            jTxtWeb.requestFocus();
            return dato;
        }

        if(jTxtMail.getText().equals(""))
        {dato="Verifique los datos en el apartado: E-Mail ";
        jTxtMail.requestFocus();
        return dato;
        }

        if(jTxtNombreRep.getText().equals(""))
        {
            dato="Verifique los datos en el apartado: Nombre del representante ";
            jTxtNombreRep.requestFocus();
            return dato;
        }

        if(jTxtTelefonoRep.getText().equals(""))
        {
            dato="Verifique los datos en el apartado: Telefono del Representante ";
            jTxtTelefonoRep.requestFocus();
            return dato;
        }

        if(jTxtCelularRep.getText().equals(""))
        {
            dato="Verifique los datos en el apartado: Celular del representante ";
            jTxtCelularRep.requestFocus();
            return dato;
        }

        if(jTxaObs.getText().equals(""))
        {
            dato="Verifique los datos en el apartado: Observaciones ";
            jTxaObs.requestFocus();
            return dato;
        }
        return "";
    }

    public void Limpiar_Entradas()
    {
        removerFiltroCampos();
        jLblCodigo.setText("");
        jTxtRuc.setText("");
        jTxtNombrePr.setText("");
        jTxtDireccion.setText("");
        jTxtTelefonoPr.setText("");
        jTxtFax.setText("");
        jTxtWeb.setText("");
        jTxtMail.setText("");
        jTxtNombreRep.setText("");
        jTxtTelefonoRep.setText("");
        jTxtCelularRep.setText("");
        jTxaObs.setText("");
        jTxtRuc.requestFocus();
        setFiltroCampos();
    }

    //Deshabilitando las entradas al inicio
    void Deshabilitar ()
    {
        jLblCodigo.setEnabled (false);
        jTxtRuc.setEnabled(false);
        jTxtNombrePr.setEnabled(false);
        jTxtDireccion.setEnabled (false);
        jTxtTelefonoPr.setEnabled (false);
        jTxtFax.setEnabled (false);
        jTxtWeb.setEnabled (false);
        jTxtMail.setEnabled (false);
        jTxtNombreRep.setEnabled (false);
        jTxtTelefonoRep.setEnabled (false);
        jTxtCelularRep.setEnabled (false);
        jTxaObs.setEnabled (false);
        jBtnLimpiar.setEnabled(false);
        removerFiltroCampos(); 
    }//Fin del metodo

    //Habilitando entradas
    void Habilitar ()
    {
        jLblCodigo.setEnabled (true);
        jTxtRuc.setEnabled(true);
        jTxtNombrePr.setEnabled(true);
        jTxtDireccion.setEnabled (true);
        jTxtTelefonoPr.setEnabled (true);
        jTxtFax.setEnabled (true);
        jTxtWeb.setEnabled (true);
        jTxtMail.setEnabled (true);
        jTxtNombreRep.setEnabled (true);
        jTxtTelefonoRep.setEnabled (true);
        jTxtCelularRep.setEnabled (true);
        jTxaObs.setEnabled (true);
        jBtnLimpiar.setEnabled(true);
        jTxtRuc.requestFocus();
        setFiltroCampos();
    }

    //METODO PARA MOSTRAR LOS MENSAJES DEL APLICATIVO
    public void mensaje(String texto)
    {
        JOptionPane.showMessageDialog(this, texto);
    }//Fin del mensaje

     private void setFiltroCampos()
    {        
        Generales.setFiltraEntrada(jTxtNombrePr.getDocument(), FiltraEntrada.SOLO_LETRAS,30,true);
        Generales.setFiltraEntrada(jTxtDireccion.getDocument(), FiltraEntrada.DEFAULT,80,true);
        Generales.setFiltraEntrada(jTxtRuc.getDocument(), FiltraEntrada.SOLO_NUMEROS,13,false);
        Generales.setFiltraEntrada(jTxtTelefonoPr.getDocument(), FiltraEntrada.SOLO_NUMEROS,9,true);
        Generales.setFiltraEntrada(jTxtCelularRep.getDocument(), FiltraEntrada.SOLO_NUMEROS,9,true);
    } 
     
     private void removerFiltroCampos()
    {
        Generales.removerFiltraEntrada(jTxtNombrePr.getDocument());
        Generales.removerFiltraEntrada(jTxtDireccion.getDocument());
        Generales.removerFiltraEntrada(jTxtRuc.getDocument());
        Generales.removerFiltraEntrada(jTxtTelefonoPr.getDocument());
        Generales.removerFiltraEntrada(jTxtCelularRep.getDocument());
            
    }   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtnGrabar = new javax.swing.JButton();
        jBtnBuscar = new javax.swing.JButton();
        jBtnModificar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTxtRuc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTxtNombrePr = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTxtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTxtTelefonoPr = new javax.swing.JTextField();
        jTxtFax = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTxtWeb = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTxtNombreRep = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTxtTelefonoRep = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTxtCelularRep = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxaObs = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jTxtMail = new javax.swing.JTextField();
        jBtnLimpiar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLblCodigo = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTblProveedores = new javax.swing.JTable();
        jBtnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Proov"); // NOI18N

        jBtnGrabar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save.png"))); // NOI18N
        jBtnGrabar.setText("GRABAR");
        jBtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGrabarActionPerformed(evt);
            }
        });

        jBtnBuscar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ico_search.jpg"))); // NOI18N
        jBtnBuscar.setText("BUSCAR");
        jBtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarActionPerformed(evt);
            }
        });

        jBtnModificar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icon_edit_large.gif"))); // NOI18N
        jBtnModificar.setText("MODIFICAR");
        jBtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnModificarActionPerformed(evt);
            }
        });

        jBtnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/RTEmagicC_EliminarPag_10_png.png"))); // NOI18N
        jBtnEliminar.setText("ELIMINAR");
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jBtnSalir.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eqsl_exit.png"))); // NOI18N
        jBtnSalir.setText("SALIR");
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATOS DEL PROVEEDOR ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel2.setText("Codigo:");

        jLabel3.setText("Nº RUC:");

        jTxtRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtRucKeyPressed(evt);
            }
        });

        jLabel4.setText("Nombre:");

        jTxtNombrePr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtNombrePrKeyPressed(evt);
            }
        });

        jLabel5.setText("Dirección:");

        jTxtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtDireccionKeyPressed(evt);
            }
        });

        jLabel6.setText("Teléfono:");

        jLabel7.setText("Nº FAX:");

        jTxtTelefonoPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtTelefonoPrKeyPressed(evt);
            }
        });

        jTxtFax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtFaxKeyPressed(evt);
            }
        });

        jLabel8.setText("Página WEB:");

        jTxtWeb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtWebKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATOS DEL REPRESENTANTE:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel9.setText("Nombres:");

        jTxtNombreRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtNombreRepKeyPressed(evt);
            }
        });

        jLabel10.setText("Teléfono:");

        jTxtTelefonoRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtTelefonoRepKeyPressed(evt);
            }
        });

        jLabel11.setText("Celular:");

        jTxtCelularRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCelularRepKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtTelefonoRep, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jTxtCelularRep)
                    .addComponent(jTxtNombreRep))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTxtNombreRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTxtTelefonoRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTxtCelularRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OBSERVACIONES: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jTxaObs.setColumns(20);
        jTxaObs.setRows(5);
        jScrollPane2.setViewportView(jTxaObs);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );

        jLabel12.setText("E - Mail:");

        jTxtMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMailKeyPressed(evt);
            }
        });

        jBtnLimpiar.setText("LIMPIAR ENTRADAS");
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/plus-icon.png"))); // NOI18N
        jButton2.setText("AGREGAR UN NUEVO PROVEEDOR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLblCodigo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLblCodigo.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel12))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTxtMail)
                                            .addComponent(jTxtWeb)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTxtTelefonoPr, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTxtDireccion)
                                            .addComponent(jTxtRuc, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTxtNombrePr)
                                            .addComponent(jTxtFax, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTxtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTxtNombrePr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTxtTelefonoPr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTxtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTxtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTblProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblProveedoresMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTblProveedores);

        jBtnImprimir.setBackground(new java.awt.Color(255, 255, 255));
        jBtnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconPrint.gif"))); // NOI18N
        jBtnImprimir.setText("IMPRIMIR");
        jBtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jBtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGrabarActionPerformed
        String dato = Verifica_Datos();
       if(dato.equalsIgnoreCase(""))
       {

        //Se leen los datos de entrada de los text file
         String codigo  = jLblCodigo.getText();
         String ruc = jTxtRuc.getText();
         String nombre = jTxtNombrePr.getText();
         String direccion = jTxtDireccion.getText();
         String telefono = jTxtTelefonoPr.getText();
         String fax = jTxtFax.getText();
         String web = jTxtWeb.getText();
         String mail = jTxtMail.getText();
         String nombre_r = jTxtNombreRep.getText();
         String telefono_r = jTxtTelefonoRep.getText();
         String celular_r = jTxtCelularRep.getText();
         String obs = jTxaObs.getText();

         //Generando la clace para manejar un registro 
         objProveedores = new cls_proveedor (codigo, ruc, nombre, direccion, telefono, fax, web, mail, nombre_r, telefono_r, celular_r, obs);
         //Verificando si el codigo existe dentro del arreglo
         if(objArreglo.Buscar(objProveedores.getCodigo())!= -1)
             mensaje("Codigo Repetido");//Se muestrea el mensaje

         else {
             //Instanciamos una clace con diferente codigo para el caso sea un nuevo registro
             String cod =  Generar_Codigo();
             objProveedores = new cls_proveedor (cod, ruc, nombre, direccion, telefono, fax, web, mail, nombre_r, telefono_r, celular_r, obs);
             //Se agrega el objeto al arreglo
             objArreglo.Agregar(objProveedores);
             //Insertando la ifnromacion en la tabla
             Insertar(0,cod, ruc,nombre, direccion, telefono, fax, web, mail, nombre_r, telefono_r, celular_r);
             //Limpiando las entradas
             Limpiar_Entradas();
             //Grabando la ifnromacion en el archivo binario
             Grabar();
             //Actualizando la tabla de registros
             Actualizar_Tabla();

            }//Fin del else
       }
       else 
       {
        mensaje(dato);
        Habilitar();
        jTxtRuc.requestFocus();
       }
    }//GEN-LAST:event_jBtnGrabarActionPerformed

    private void jBtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarActionPerformed
        Consultar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnBuscarActionPerformed

    private void jBtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnModificarActionPerformed
        Modificar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnModificarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        Eliminar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        Limpiar_Entradas();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Habilitar();
        Limpiar_Entradas();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTxtRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtRucKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxtNombrePr.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtRucKeyPressed

    private void jTxtNombrePrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombrePrKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxtDireccion.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtNombrePrKeyPressed

    private void jTxtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtDireccionKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxtTelefonoPr.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtDireccionKeyPressed

    private void jTxtTelefonoPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTelefonoPrKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxtFax.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtTelefonoPrKeyPressed

    private void jTxtFaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtFaxKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxtWeb.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtFaxKeyPressed

    private void jTxtWebKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtWebKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxtMail.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtWebKeyPressed

    private void jTxtMailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMailKeyPressed
        //if(evt.getKeyCode()==evt.VK_ENTER)
            //jCbxDepart.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtMailKeyPressed

    private void jTxtNombreRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtNombreRepKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxtTelefonoRep.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtNombreRepKeyPressed

    private void jTxtTelefonoRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTelefonoRepKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxtCelularRep.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtTelefonoRepKeyPressed

    private void jTxtCelularRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCelularRepKeyPressed
        if(evt.getKeyCode()==evt.VK_ENTER)
            jTxaObs.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_jTxtCelularRepKeyPressed

    private void jBtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnImprimirActionPerformed
        try {
            jTblProveedores.print(JTable.PrintMode.NORMAL, null, null);

            } catch (PrinterException ex) {
            Logger.getLogger(Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnImprimirActionPerformed

    private void jTblProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblProveedoresMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() >= 1)
        {
            if(jTblProveedores.getSelectedRow() != -1)
            {
                Imprimir_Datos(jTblProveedores.getSelectedRow());
            }
        }
    }//GEN-LAST:event_jTblProveedoresMouseClicked


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Proveedores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnBuscar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGrabar;
    private javax.swing.JButton jBtnImprimir;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JButton jBtnModificar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblCodigo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTblProveedores;
    private javax.swing.JTextArea jTxaObs;
    private javax.swing.JTextField jTxtCelularRep;
    private javax.swing.JTextField jTxtDireccion;
    private javax.swing.JTextField jTxtFax;
    private javax.swing.JTextField jTxtMail;
    private javax.swing.JTextField jTxtNombrePr;
    private javax.swing.JTextField jTxtNombreRep;
    private javax.swing.JTextField jTxtRuc;
    private javax.swing.JTextField jTxtTelefonoPr;
    private javax.swing.JTextField jTxtTelefonoRep;
    private javax.swing.JTextField jTxtWeb;
    // End of variables declaration//GEN-END:variables

}

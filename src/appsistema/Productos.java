package appsistema;
//Declaracion de librerias

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import pck_controller.ProductoController;
import pck_controller.ProveedorController;
import pck_entidades.Arreglo_Producto;
import pck_entidades.cls_producto;
import pck_utilidades.FiltraEntrada;
import pck_utilidades.Generales;

public final class Productos extends javax.swing.JFrame {

    //Declarando las claces
    cls_producto objProducto;
    Arreglo_Producto objArreglo;
    DefaultTableModel miModelo;
    String[] cabecera = {"Nº", "Cód. barras", "Codigo", "Nombre", "Linea", "Marca", "Modelo", "Proveedor", "Fecha V.", "Stock", "Precio Venta"};
    String[][] data = {};
    ProductoController objProductoController;
    FileInputStream foto;
    //Variables globales
    int num = 0;

    public String Generar_Codigo() {
        int cod = (int) (Math.random() * (99999 - 10000)) + 10000;
        String codig = String.valueOf(cod);
        return codig;
    }

    public Productos() {
        initComponents();
        objProductoController = new ProductoController();
        jLblUtilidad.setText("");
        miModelo = new DefaultTableModel(data, cabecera);
        jTblProductos.setModel(miModelo);
        objArreglo = new Arreglo_Producto();
        Cargar_Data();
        Actualizar_Tabla();
        jTxtCodigoBarras.requestFocus();
        //setFiltroCampos();
        Limpiar_Entradas();
    }

    public void Cargar_Data() {
        //Lee la data del objeto serializable
        try {
            ArrayList<cls_producto> listProd = objProductoController.cargarProductos();
            for (cls_producto listProd1 : listProd) {
                objArreglo.Agregar(listProd1);
            }
            
            ResultSet rs=ProveedorController.listarProveedores();
            while(rs.next())
            {
                jCbxProveedor.addItem(rs.getString(1));
            }
            
            rs=ProductoController.listarMarcas();
            while(rs.next())
            {
                jCbxMarca.addItem(rs.getString(1));
            }
            
            rs=ProductoController.listarLineas();
            while(rs.next())
            {
                jCbxLinea.addItem(rs.getString(1));
            }
            
        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los Productos: " + e);
        }
    }

    //METODO PARA GRABAR LA INFORMACION DEL ARREGLO DE OBJETOS AL ARCHIVO BINARIO
    public void Grabar() {
        //Guarda la data en el archivo serializado
        try {
            for (int i = 0; i < objArreglo.Numero_Producto(); i++) {
                if (objArreglo.getProducto(i).isEditar()) {
                    objProductoController.grabarRegistro(objArreglo.getProducto(i));
                    objArreglo.getProducto(i).setFoto(null);
                }
            }

        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la grabacion sobre el arreglo: " + e);
        }
    }//Fin de Grabar

    //METODOS QUE PERMITEN MANIPULAR A TABLA Y LA LISTA
    public void Actualizar_Tabla() {
        //Vaciando la informacion de la tabla
        Vaciar_Tabla();
        //Capturando el tamaño del arreglo
        int n = objArreglo.Numero_Producto();

        for (int i = 0; i < n; i++) {
            String cod_barras = objArreglo.getProducto(i).getCodigo_barras();
            String codigo = objArreglo.getProducto(i).getCodigo_art();
            String nombre = objArreglo.getProducto(i).getNombre();
            String linea = objArreglo.getProducto(i).getLinea();
            String marca = objArreglo.getProducto(i).getMarca();
            String modelo = objArreglo.getProducto(i).getModelo();
            String proveedor = objArreglo.getProducto(i).getProveedor();
            String fechaV = objArreglo.getProducto(i).getFechaVencimiento();
            int stk = objArreglo.getProducto(i).getStock();
            float p_venta = objArreglo.getProducto(i).getP_venta();
            //Insertando la indormacion en la tabla
            Insertar(i + 1, cod_barras, codigo, nombre, linea, marca, modelo, proveedor, fechaV, stk, p_venta);
        }//Fin del for
    }

    public void Insertar(int num, String cod_barras, String codigo, String nombre, String linea, String marca, String modelo, String proveedor, String fechaV, int stk_max, float p_venta) {
        //Llenando la fila de la tabla
        Object fila[] = {num, cod_barras, codigo, nombre, linea, marca, modelo, proveedor, fechaV, stk_max, p_venta};
        //Agregando la fila a nuestro modelo de tabla
        miModelo.addRow(fila);
    }

    public void Vaciar_Tabla() {
        int filas = jTblProductos.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0); //Removiendo la fila de la tabla
        }//Fin del for
    }

    public void Resumen_Utilidad() {
        //Declarando las variables locales
        float p_compra = Float.parseFloat(jTxtPCompra.getText());
        float p_venta = Float.parseFloat(jTxtPVenta.getText());
        String util = "";

        //Declarando un tipo de formato
        DecimalFormat df = new DecimalFormat("####.00");

        float utilidad = Calcular(p_compra, p_venta);
        util = df.format(utilidad);

        //Imprimiendo la utilidad segun sea el caso de seleccione en S/.
        jLblUtilidad.setText("%" + util + " Valor: " + String.valueOf(p_venta - p_compra));
    }

    public float Calcular(float p_compra, float p_venta) {
        float ut = 100 * (p_venta - p_compra) / p_compra;
        return ut;

    }

    public void Resumen_Por_Mayor() {
        //Declarando un tipo de formato
        DecimalFormat df = new DecimalFormat("####.00");
        //Extraemos variables para el posterior calculo
        float p_compra = Float.parseFloat(jTxtPCompra.getText());
        float p_pmayor = Float.parseFloat(jTxtPPMayor.getText());

        //Para el precio al por mayor
        float utilidad2 = Calcular(p_compra, p_pmayor);
        //Dandole formato al porcentaje calculado de la utilidad2
        String util2 = df.format(utilidad2);
        //Colocando la informacion de salida
        jLblUtilidad.setText("%" + util2 + " Valor: " + String.valueOf(p_pmayor - p_compra));
    }

    //METODOS UTILIZADOS PARA LAS CONSULTAS, ELIMINACION Y ACTUALIZACION DE DATOS
    public void Modificar() {
        String codigo = jLblCodigo.getText();// JOptionPane.showInputDialog("Ingresar: Código del proveedor").toUpperCase().trim();
        String dato = Verifica_Datos();
        if (dato.equalsIgnoreCase("")) {
            if (codigo.equalsIgnoreCase("")) {
                mensaje("ERROR: Códico incorrecto");
                jTxtCodigoBarras.requestFocus();
            } else {
                //Se vuelve a buscar el codigo para no repetir el mismo
                int p = objArreglo.Buscar(codigo);

                //Generando la clace para manejar un Registro 
                objProducto = obtenerArticulo();
                //Verificando si el codigo existe dentro del arreglo

                if (p == -1)//El codigo es nuevo
                {
                    objArreglo.Agregar(objProducto);
                } else//Codigo ya existente
                {
                    objArreglo.Reemplaza(p, objProducto);
                }

                //Limpiando las entradas
                Limpiar_Entradas();
                //Grabamos la informacion en el archivo binario
                Grabar();
                //Actualizando la tabla
                Actualizar_Tabla();
                //Colocando el cursor el en primer area de texto
                jTxtCodigoBarras.requestFocus();
            }//Fin de elser
        } else {
            mensaje("Debe llenar los campos correspondientes del producto que desea modificar");
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
                jTxtCodigoBarras.requestFocus();//Colocando el cursor en el primer area de texto
            }//Fin del if
            //En caso el codigo no exista
            else {
                mensaje("El código del alumno no existe");
            }
        }//Fin del if(Verificando que existe el codigo)
    }//Fin del metodo Eliminar

    public void Consultar() {
        String codigo = JOptionPane.showInputDialog("Ingresar: Código").toUpperCase().trim();

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
    }

    private cls_producto obtenerArticulo() {
        cls_producto objArticulo;

        //Atributos principales del producto
        String cod_barras = jTxtCodigoBarras.getText();
        String codigo = jLblCodigo.getText();
        //String fecha = jTxtFechaR.getText();
        String nombre = jTxtNombre.getText();
        String linea = jCbxLinea.getSelectedItem().toString();
        String marca = jCbxMarca.getSelectedItem().toString();
        String modelo = jTxtModelo.getText();
        String obs = jTxaObs.getText();

        String proveedor = jCbxProveedor.getSelectedItem().toString();
        String garantia = jCbxGarantia.getSelectedItem().toString();
        String fechaV = Generales.getFechaFormateada(jTxtFechaVencimiento.getCalendar().getTime(), Generales.ANIO_MES_DIA);
        int stock = Integer.parseInt(jTxtStock.getText());
        int stk_min = Integer.parseInt(jTxtSMinimo.getText());
        int stk_max = Integer.parseInt(jTxtSMaximo.getText());
        //Detalles de precio y comisiones del producto
        float p_venta = Float.parseFloat(jTxtPVenta.getText());
        float p_compra = Float.parseFloat(jTxtPCompra.getText());
        //float p_pmayor = Float.parseFloat(jTxtPPMayor.getText());

        objArticulo = new cls_producto(codigo, cod_barras, nombre, linea, marca, modelo, obs, proveedor, garantia, fechaV, stock, stk_min,
                stk_max, p_venta, p_compra, foto, true);
        objArticulo.setImagen(jLblImagen.getIcon());
        
        return objArticulo;
    }

    //METODOS SECUNDARIOS
    public void Imprimir_Datos(int pos) {
        
        Limpiar_Entradas();
        //Instanciamos el objeto
        objProducto = objArreglo.getProducto(pos);
        //Se extrae la ifnromacion de los campos del objeto

        //Atributos principales del producto
        jTxtCodigoBarras.setText(objProducto.getCodigo_barras());
        jLblCodigo.setText(objProducto.getCodigo_art());
        jTxtFechaR.setDate(Generales.stringToDate(objProducto.getFecha(), Generales.ANIO_MES_DIA));
        jTxtNombre.setText(objProducto.getNombre());
        jCbxLinea.setSelectedItem(objProducto.getLinea());
        jCbxMarca.setSelectedItem(objProducto.getMarca());
        jTxtModelo.setText(objProducto.getModelo());
        jTxaObs.setText(objProducto.getObs());
        jLblImagen.setIcon(objProducto.getImagen());

        //Detalles de venta del producto
        jCbxProveedor.setSelectedItem(objProducto.getProveedor());

        jCbxGarantia.setSelectedItem(objProducto.getGarantia());
        jTxtFechaVencimiento.setDate(Generales.stringToDate(objProducto.getFechaVencimiento(), Generales.ANIO_MES_DIA));

        jTxtSMinimo.setText(String.valueOf(objProducto.getStk_minimo()));
        jTxtSMaximo.setText(String.valueOf(objProducto.getStk_maximo()));
        jTxtStock.setText(String.valueOf(objProducto.getStock()));

        //Detalles de precio y comisiones del producto
        jTxtPVenta.setText(String.valueOf(objProducto.getP_venta()));
        jTxtPCompra.setText(String.valueOf(objProducto.getP_compra()));
        jTxtPPMayor.setText(String.valueOf("0"));

    }

    public void Limpiar_Entradas() {
        //Atributos principales del producto
        removerFiltroCampos();
        jTxtCodigoBarras.setText("");
        jLblCodigo.setText("");
        jTxtFechaR.setDate(Generales.stringToDate(null, Generales.ANIO_MES_DIA));
        jTxtNombre.setText("");
        jCbxLinea.setSelectedIndex(0);
        jCbxMarca.setSelectedIndex(0);
        jTxtModelo.setText("");
        jTxaObs.setText("");
        jLblImagen.setIcon(null);

        //Detalles de venta del producto
        jCbxProveedor.setSelectedIndex(0);
        jCbxGarantia.setSelectedIndex(0);

        jTxtFechaVencimiento.setDate(null);
        jTxtSMinimo.setText("");
        jTxtSMaximo.setText("");
        jTxtStock.setText("");

        //Detalles de precio y comisiones del producto
        jTxtPVenta.setText("");
        jTxtPCompra.setText("");
        jTxtPPMayor.setText("");
        jLblUtilidad.setText("");
        setFiltroCampos();
    }

    public String Verifica_Datos() {
        String dato = "";

        if (jTxtCodigoBarras.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Codigo de barras ";
            jTxtCodigoBarras.requestFocus();
            return dato;
        }

        if (jTxtNombre.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Nombre ";
            jTxtNombre.requestFocus();
            return dato;
        }

        if (jCbxLinea.getSelectedIndex() == 0) {
            dato = "Verifique los datos en el apartado: Linea";
            jCbxLinea.setSelectedIndex(0);
            return dato;
        }

        if (jCbxMarca.getSelectedIndex() == 0) {
            dato = "Verifique los datos en el apartado: Marca";
            jCbxMarca.setSelectedIndex(0);
            return dato;
        }

        if (jTxtModelo.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Modelo ";
            jTxtModelo.requestFocus();
            return dato;
        }

        if (jTxtFechaVencimiento.getCalendar() == null) {
            dato = "Verifique los datos en el apartado: Fecha Vencimiento ";
            jTxtFechaVencimiento.requestFocus();
            return dato;
        }

        if (jTxaObs.getText().equals("")) {
            dato = "Verifique los datos en el apartado: especificaciones ";
            jTxaObs.requestFocus();
            return dato;
        }

        if (jCbxProveedor.getSelectedIndex() == 0) {
            dato = "Verifique los datos en el apartado: Proveedor";
            jCbxProveedor.setSelectedIndex(0);
            return dato;
        }

        if (jCbxGarantia.getSelectedIndex() == 0) {
            dato = "Verifique los datos en el apartado: Garantia";
            jCbxGarantia.setSelectedIndex(0);
            return dato;
        }

        if (jTxtSMinimo.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Stk. Mínimo ";
            jTxtSMinimo.requestFocus();
            return dato;
        }

        if (jTxtStock.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Stock ";
            jTxtStock.requestFocus();
            return dato;
        }

        if (jTxtPVenta.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Precio de Venta ";
            jTxtPVenta.requestFocus();
            return dato;
        }

        if (jTxtPCompra.getText().equals("")) {
            dato = "Verifique los datos en el apartado: Precio de Compra ";
            jTxtPCompra.requestFocus();
            return dato;
        }

        return "";
    }

    private void setFiltroCampos() {
        Generales.setFiltraEntrada(jTxtSMinimo.getDocument(), FiltraEntrada.SOLO_NUMEROS, 8, false);
        Generales.setFiltraEntrada(jTxtSMaximo.getDocument(), FiltraEntrada.SOLO_NUMEROS, 8, false);
        Generales.setFiltraEntrada(jTxtStock.getDocument(), FiltraEntrada.SOLO_NUMEROS, 8, false);
        Generales.setFiltraEntrada(jTxtPCompra.getDocument(), FiltraEntrada.SOLO_NUMEROS, 8, false);
        Generales.setFiltraEntrada(jTxtPVenta.getDocument(), FiltraEntrada.SOLO_NUMEROS, 8, false);
        Generales.setFiltraEntrada(jTxtPPMayor.getDocument(), FiltraEntrada.SOLO_NUMEROS, 8, false);
    }

    private void removerFiltroCampos() {
        Generales.removerFiltraEntrada(jTxtSMinimo.getDocument());
        Generales.removerFiltraEntrada(jTxtSMaximo.getDocument());
        Generales.removerFiltraEntrada(jTxtStock.getDocument());
        Generales.removerFiltraEntrada(jTxtPCompra.getDocument());
        Generales.removerFiltraEntrada(jTxtPVenta.getDocument());
        Generales.removerFiltraEntrada(jTxtPPMayor.getDocument());
    }

    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }//Fin del mensaje

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrupoSerie = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtCodigoBarras = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCbxLinea = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jCbxMarca = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jTxtModelo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxaObs = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLblImagen = new javax.swing.JLabel();
        jLblCodigo = new javax.swing.JLabel();
        jTxtFechaR = new com.toedter.calendar.JDateChooser();
        jPanel_Seleccionar = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jCbxProveedor = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jCbxGarantia = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTxtSMinimo = new javax.swing.JTextField();
        jTxtStock = new javax.swing.JTextField();
        jTxtFechaVencimiento = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jTxtSMaximo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTxtPCompra = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTxtPVenta = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTxtPPMayor = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jBtnImprimir = new javax.swing.JButton();
        jBtnBuscar = new javax.swing.JButton();
        jBtnGrabar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnModificar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTblProductos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLblUtilidad = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CARACTERÍSTICAS DEL PRODUCTO: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setText("Codigo de barras: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Codigo:");

        jLabel4.setText("Fecha:");
        jLabel4.setToolTipText("");

        jLabel5.setText("Nombre: ");

        jLabel6.setText("Seleccionar Línea:");

        jCbxLinea.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--------Seleccione--------" }));

        jLabel8.setText("Marca: ");

        jCbxMarca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--------Seleccione--------" }));

        jLabel9.setText("Modelo:");

        jTxaObs.setColumns(20);
        jTxaObs.setRows(5);
        jScrollPane2.setViewportView(jTxaObs);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Especificaciones:");

        jLblImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLblImagenMouseClicked(evt);
            }
        });

        jLblCodigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblCodigo.setForeground(new java.awt.Color(255, 0, 0));

        jTxtFechaR.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCbxMarca, javax.swing.GroupLayout.Alignment.TRAILING, 0, 144, Short.MAX_VALUE)
                            .addComponent(jTxtNombre)
                            .addComponent(jCbxLinea, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTxtModelo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTxtFechaR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTxtCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jTxtFechaR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jTxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jCbxLinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jCbxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jTxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_Seleccionar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATOS ADICIONALES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel25.setText("Proveedor:");

        jCbxProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--------Seleccione--------" }));

        jLabel27.setText("Garantía: ");

        jCbxGarantia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--------Seleccione--------", "Sin Garantía", "01 Mes", "02 meses", "03 Meses", "04 Meses", "04 Meses", "05 Meses", "06 Meses", "07 Meses", "08 Meses", "09 Meses", "10 Meses", "11 Meses", "1 Año", "2 Años" }));
        jCbxGarantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCbxGarantiaActionPerformed(evt);
            }
        });

        jLabel28.setText("Fecha V.:");

        jLabel29.setText("Stk. Mínimo: ");

        jLabel30.setText("Stock:");

        jLabel31.setText("Stk. Maximo: ");

        jLabel11.setText("Precio de Compra:");

        jLabel12.setText("Precio de Venta: ");

        jLabel14.setText("Precio / Mayor:");

        javax.swing.GroupLayout jPanel_SeleccionarLayout = new javax.swing.GroupLayout(jPanel_Seleccionar);
        jPanel_Seleccionar.setLayout(jPanel_SeleccionarLayout);
        jPanel_SeleccionarLayout.setHorizontalGroup(
            jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_SeleccionarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_SeleccionarLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtPCompra))
                    .addGroup(jPanel_SeleccionarLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCbxProveedor, 0, 150, Short.MAX_VALUE))
                    .addGroup(jPanel_SeleccionarLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCbxGarantia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_SeleccionarLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_SeleccionarLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(6, 6, 6)
                                .addComponent(jTxtFechaVencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel_SeleccionarLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtSMinimo)))
                        .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_SeleccionarLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel30)
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_SeleccionarLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtSMaximo)
                            .addComponent(jTxtStock)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_SeleccionarLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtPVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtPPMayor, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel_SeleccionarLayout.setVerticalGroup(
            jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_SeleccionarLayout.createSequentialGroup()
                .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jCbxProveedor)
                    .addComponent(jTxtSMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jTxtSMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(jTxtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30)
                        .addComponent(jLabel27)
                        .addComponent(jCbxGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTxtFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jTxtPCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jTxtPVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_SeleccionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel14)
                        .addComponent(jTxtPPMayor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBtnImprimir.setBackground(new java.awt.Color(255, 255, 255));
        jBtnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/iconPrint.gif"))); // NOI18N
        jBtnImprimir.setText("Imprimir");

        jBtnBuscar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ico_search.jpg"))); // NOI18N
        jBtnBuscar.setText("Buscar");
        jBtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarActionPerformed(evt);
            }
        });

        jBtnGrabar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save.png"))); // NOI18N
        jBtnGrabar.setText("Grabar");
        jBtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGrabarActionPerformed(evt);
            }
        });

        jBtnSalir.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eqsl_exit.png"))); // NOI18N
        jBtnSalir.setText("Salir");
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
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

        jBtnModificar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/icon_edit_large.gif"))); // NOI18N
        jBtnModificar.setText("Modificar");
        jBtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnImprimir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jBtnBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnGrabar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jBtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTblProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTblProductosKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTblProductos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
        );

        jButton1.setText("LIMPIAR ENTRADAS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("PRODUCTOS");

        jLblUtilidad.setText("Utilidad");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLblUtilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addComponent(jPanel_Seleccionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_Seleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jLblUtilidad))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jBtnSalirActionPerformed

    private void jBtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGrabarActionPerformed
        String dato = Verifica_Datos();
        if (dato.equalsIgnoreCase("")) {
            //Se leen los datos de entrada de los text file
            //Atributos principales del producto

            String fecha = Generales.getFechaFormateada(jTxtFechaR.getCalendar().getTime(), Generales.ANIO_MES_DIA);

            //Generando la clace para manejar un registro
            objProducto = obtenerArticulo();
            objProducto.setCodigo_art(Generar_Codigo());
            objProducto.setFecha(fecha);
            //Verificando si el codigo existe dentro del arreglo
            if (objArreglo.Buscar(objProducto.getCodigo_art()) != -1) {
                mensaje("Codigo Repetido");//Se muestrea el mensaje
            } else {
                //Se agrega el objeto al arreglo
                objArreglo.Agregar(objProducto);
                //Insertando la ifnromacion en la tabla
                Insertar(0, objProducto.getCodigo_barras(), objProducto.getCodigo_art(),
                        objProducto.getNombre(), objProducto.getLinea(), objProducto.getMarca(),
                        objProducto.getModelo(), objProducto.getProveedor(), objProducto.getFechaVencimiento(), objProducto.getStock(), objProducto.getP_venta());
                //Limpiando las entradas
                Limpiar_Entradas();
                //Grabando la ifnromacion en el archivo binario
                Grabar();
                //Actualizando la tabla de registros
                Actualizar_Tabla();
            }//Fin del else
        } else {
            mensaje(dato);
            jTxtCodigoBarras.requestFocus();
        }
    }//GEN-LAST:event_jBtnGrabarActionPerformed

    private void jBtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarActionPerformed
        Consultar();
    }//GEN-LAST:event_jBtnBuscarActionPerformed

    private void jBtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnModificarActionPerformed
        Modificar();
    }//GEN-LAST:event_jBtnModificarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        Eliminar();
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Limpiar_Entradas();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCbxGarantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCbxGarantiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCbxGarantiaActionPerformed

    private void jTblProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTblProductosKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTblProductos.getSelectedRow() != -1) {
                Imprimir_Datos(jTblProductos.getSelectedRow());
            }
        }
    }//GEN-LAST:event_jTblProductosKeyPressed

    private void jLblImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLblImagenMouseClicked
        // TODO add your handling code here:
        JFileChooser dlg = new JFileChooser();
        //Abre la ventana de dialogo
        int option = dlg.showOpenDialog(this);
        //Si hace click en el boton abrir del dialogo
        if (option == JFileChooser.APPROVE_OPTION) {
            //Obtiene nombre del archivo seleccionado
            try {
                foto = new FileInputStream(dlg.getSelectedFile());
                Image icono = ImageIO.read(dlg.getSelectedFile()).getScaledInstance(jLblImagen.getWidth(), jLblImagen.getHeight(), Image.SCALE_DEFAULT);
                jLblImagen.setIcon(new ImageIcon(icono));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_jLblImagenMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrupoSerie;
    private javax.swing.JButton jBtnBuscar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGrabar;
    private javax.swing.JButton jBtnImprimir;
    private javax.swing.JButton jBtnModificar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jCbxGarantia;
    private javax.swing.JComboBox jCbxLinea;
    private javax.swing.JComboBox jCbxMarca;
    private javax.swing.JComboBox jCbxProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblCodigo;
    private javax.swing.JLabel jLblImagen;
    private javax.swing.JLabel jLblUtilidad;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel_Seleccionar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTblProductos;
    private javax.swing.JTextArea jTxaObs;
    private javax.swing.JTextField jTxtCodigoBarras;
    private com.toedter.calendar.JDateChooser jTxtFechaR;
    private com.toedter.calendar.JDateChooser jTxtFechaVencimiento;
    private javax.swing.JTextField jTxtModelo;
    private javax.swing.JTextField jTxtNombre;
    private javax.swing.JTextField jTxtPCompra;
    private javax.swing.JTextField jTxtPPMayor;
    private javax.swing.JTextField jTxtPVenta;
    private javax.swing.JTextField jTxtSMaximo;
    private javax.swing.JTextField jTxtSMinimo;
    private javax.swing.JTextField jTxtStock;
    // End of variables declaration//GEN-END:variables

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_utilidades;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import pck_controller.ParametrosController;
import pck_entidades.Factura;

/**
 *
 * @author Alex
 */
public class ImpresionTicket implements Printable {

     //Se obtienen las lineas de texto del JTextArea, la linea de texto finaliza cuando se encuentra el caracter de nueva linea \n
    StringTokenizer lineasdetexto;// = new StringTokenizer(jTAsalida.getText(), "\n", true);
    //Se obtiene el total de lineas de texto
    int totallineas;// = lineasdetexto.countTokens();
    
    int[] paginas;  // Arreglo de número de paginas que se necesitaran para imprimir todo el texto 

    String[] textoLineas; //Lineas de texto que se imprimiran en cada hoja

    public ImpresionTicket() {
    }

    public void impresion(Factura objFactura) {
        
        lineasdetexto = new StringTokenizer(Cargar_Plantilla(objFactura), "\n", true);
        totallineas = lineasdetexto.countTokens();
        imprimirTicket();
    }

    public String Cargar_Plantilla(Factura objFactura) {
        //Lee la data del objeto serializable
        String textoImprimir = null;
        try {
            String texto=ParametrosController.obtenerDescripcionParametroXid("PAR001");
            String [] plantilla=texto.split("\n");
            
            for (int index=0;index<plantilla.length;index++)
            {
                plantilla[index] = mergePlantilla(plantilla[index], objFactura);
            }
            
            textoImprimir="";
            
            for (String plantilla1 : plantilla) {
                textoImprimir += plantilla1+"\n";
            }
            
        }//Fin del try
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la plantilla del ticket: " + e);
        }
        return textoImprimir;
    }
   
    /// <summary>
        /// Función que lee la plantilla y coloca los datos en esas posiciones
        /// </summary>
        /// <param name="fila"></param>
        /// <param name="objFactura"></param>
        /// <returns></returns>
        private String mergePlantilla(String fila, Factura objFactura)
        {
            String linea = fila;

            if (fila.indexOf("@NUMFAC") > 0)
                linea = fila.replace("@NUMFAC", objFactura.getNumFac());

            if (fila.indexOf("@FECHAEMISION") > 0)
                linea = fila.replace("@FECHAEMISION", objFactura.getFecha());

            if (fila.indexOf("@CLIENTE") > 0)
                linea = fila.replace("@CLIENTE", objFactura.getCliente());

            if (fila.indexOf("@RUC") > 0)
                linea = fila.replace("@RUC", objFactura.getRuc());

            if (fila.indexOf("@TELEFONO") > 0)
                linea = fila.replace("@TELEFONO", objFactura.getTelefono());

            if (fila.indexOf("@DIRECCION") > 0)
                linea = fila.replace("@DIRECCION", objFactura.getDireccion());

            if (fila.indexOf("@DETALLE") > 0)
            {
                String cantidad = objFactura.getCantidad();
                String detalle = objFactura.getDescripcion();
                String pUnit = objFactura.getValorUnit();
                String costo = objFactura.getValorTotalDet();

                cantidad = Generales.padLeft(cantidad, " ", 5);
                cantidad = Generales.padRight(cantidad," ",10);
                detalle = Generales.padLeft(detalle," ",15);
                detalle = Generales.padRight(detalle," ",26);
                pUnit = Generales.padLeft(pUnit," ",6);
                pUnit = Generales.padRight(pUnit," ",10);
                costo = Generales.padLeft(costo," ",8);
                costo = Generales.padRight(costo," ",10);

                linea = fila.replace("@DETALLE", cantidad + detalle + pUnit + costo);
            }

            if (fila.indexOf("@SUBTOTAL") > 0)
            {
                String SubTotalFac = objFactura.getSubTotalFac();
                SubTotalFac = Generales.padLeft(SubTotalFac," ",12);
                linea = fila.replace("@SUBTOTAL", "SUBTOTAL: " + SubTotalFac);
            }

            if (fila.indexOf("@IVA") > 0)
            {
                String Iva = objFactura.getIva();
                Iva = Generales.padLeft(Iva," ",14);
                linea = fila.replace("@IVA", "IVA12%:     " + Iva);
            }

            if (fila.indexOf("@TOTAL") > 0)
            {
                String ValorFac = objFactura.getValorFac();
                ValorFac = Generales.padLeft(ValorFac," ",15);
                linea = fila.replace("@TOTAL", "TOTAL:     " + ValorFac);
            }

            if (fila.indexOf("@RESP") > 0)
            {
                linea = fila.replace("@RESP", objFactura.getResponsable());
            }

            if (fila.indexOf("@CARGO") > 0)
            {
                linea = fila.replace("@CARGO", objFactura.getCargo());
            }

            if (fila.indexOf("@SUCURSAL") > 0)
            {
                linea = fila.replace("@SUCURSAL", objFactura.getSucursal());
            }

            if (fila.indexOf("@DIRSUCURSAL") > 0)
            {
                linea = fila.replace("@DIRSUCURSAL", objFactura.getDireccionSucursal());
            }

            return linea.replace('\r', ' ');
        }
    
    
    //Metodo que se crea por default cuando una clase implementa a Printable
    public int print(Graphics g, PageFormat pf, int pageIndex)
            throws PrinterException {
        //Se establece la fuente, el tipo, el tamaño, la metrica según la fuente asignada, 
        //obtiene la altura de cada linea de texto para que todas queden iguales
        Font font = new Font("Arial", Font.PLAIN, 8);
        FontMetrics metrics = g.getFontMetrics(font);
        int altodelinea = metrics.getHeight();
        //Calcula el número de lineas por pagina y el total de paginas
        if (paginas == null) {
            initTextoLineas();
            //Calcula las lineas que le caben a cada página dividiendo la altura imprimible entre la altura de la linea de texto
            int lineasPorPagina = (int) (pf.getImageableHeight() / altodelinea);
            //Calcula el numero de páginas dividiendo el total de lineas entre el numero de lineas por página
            int numeroPaginas = (textoLineas.length - 1) / lineasPorPagina;
            paginas = new int[numeroPaginas];
            for (int b = 0; b < numeroPaginas; b++) {
                paginas[b] = (b + 1) * lineasPorPagina;
            }
        }
        //Si se recibe un indice de página mayor que el total de páginas calculadas entonces 
        //retorna NO_SUCH_PAGE para indicar que tal pagina no existe 
        if (pageIndex > paginas.length) {
            return NO_SUCH_PAGE;
        }
        /*Por lo regular cuando dibujamos algun objeto lo coloca en la coordenada (0,0), esta coordenada 
         * se encuentra fuera del área imprimible, por tal motivo se debe trasladar la posicion de las lineas de texto
         * según el área imprimible del eje X y el eje Y 
         */

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        /*Dibujamos cada línea de texto en cada página,
         * se aumenta a la posición 'y' la altura de la línea a cada línea de texto para evitar la saturación de texto 
         */

        int y = 0;
        int start = (pageIndex == 0) ? 0 : paginas[pageIndex - 1];
        int end = (pageIndex == paginas.length) ? textoLineas.length : paginas[pageIndex];
        for (int line = start; line < end; line++) {
            y += altodelinea;
            g.drawString(textoLineas[line], 0, y);
        }
        /* Retorna PAGE_EXISTS para indicar al invocador que esta página es parte del documento impreso
         */
        return PAGE_EXISTS;
    }

    /* Agrega las lineas de texto al arreglo */
    public void initTextoLineas() {
        if (textoLineas == null) {
            int numLineas = totallineas;
            textoLineas = new String[numLineas];
            //Se llena el arreglo que contiene todas las lineas de texto
            while (lineasdetexto.hasMoreTokens()) {
                for (int i = 0; i < numLineas; i++) {
                    textoLineas[i] = lineasdetexto.nextToken();
                }
            }
        }
    }

    //Este metodo crea un objeto Printerjob el cual es inicializado y asociado con la impresora por default
    public void imprimirTicket() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
         //Si el usuario presiona imprimir en el dialogo de impresión, 
        //entonces intenta imprimir todas las lineas de texto
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }

}

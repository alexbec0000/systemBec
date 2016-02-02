/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pck_utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author Alex
 */
public class ImpresionTicket {

    public ImpresionTicket() {
    }
    
    public static void impresion()
    {
        try {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\AppSistema\\plantilla.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (inputStream == null) {
                return;
            }
            
            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);
            
            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
            
            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
            
            
            if (defaultPrintService != null) {
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                try {
                    printJob.print(document, attributeSet);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("No existen impresoras instaladas");
            }
            
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}

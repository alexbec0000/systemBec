/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generaXML;

import com.google.gson.JsonObject;
import comprobantes.util.Constantes;
import java.io.BufferedReader; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException; 
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.io.OutputStream; 
import java.io.StringWriter;
import java.net.HttpURLConnection; 
import java.net.URL; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 *
 * @author abecerra
 */
public class consumoServiciosCore {
    
       public String[] consumoFull_AuthorizationsJson(String targetURL, String filePath, String mail) 
       {
           String[] mensaje=new String[2];
           mensaje[0]="Error";
           mensaje[1]="consumoFull_AuthorizationsJson";
           try {
               File file = new File(filePath);
               DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
               InputStream inputStream = new FileInputStream(file);
               Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
               StringWriter stw = new StringWriter();
               Transformer serializer = TransformerFactory.newInstance().newTransformer();
               serializer.transform(new DOMSource(doc), new StreamResult(stw));
               String xml=stw.toString();
               
               JsonObject DATA=new JsonObject();
               DATA.addProperty("secret_key",Constantes.secret_key);
               DATA.addProperty("codigo_numerico",Constantes.codigo_numerico);
               DATA.addProperty("archivo",xml);
               try{
                    if(mail.length()>0)
                        DATA.addProperty("customer_email",mail);
               }
               catch(Exception ex){}
               
               URL url = new URL(targetURL+"full_authorizations.json");
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setDoOutput(true);
               //conn.setDoInput(true);
               //conn.setUseCaches(false);  
               //conn.setConnectTimeout(10000);  
               //conn.setReadTimeout(10000);
               conn.setRequestMethod("POST");
               conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
               
               OutputStream os = conn.getOutputStream();
               os.write(DATA.toString().getBytes("UTF-8"));
               os.flush();
               
               if (conn.getResponseCode() != 200) {
                   mensaje[0]="Error";
                   System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                   mensaje[1]="Failed : HTTP error code : " + conn.getResponseCode();
               }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output = br.readLine();
                System.out.println(output); 
                conn.disconnect();
                StringBuilder sb = new StringBuilder();
                String[] respuesta = clsManejoJSON.obtenerMensaje(output,mail,targetURL,true);
                for (int index=1;index<respuesta.length;index++) {
                    sb.append(respuesta[index]).append(" - ");
                }
                mensaje[0]=respuesta[0];
                mensaje[1]=sb.toString();
                
                /*if(mensaje[0].equals("AUTORIZADO"))
                {
                    if(!file.delete())
                        System.out.println("No se pudo borrar el archivo ");
                }*/
                    
           } catch (IOException ex) {
               System.out.println("consumoFull_AuthorizationsJson: "+ex.getMessage());
           } catch (ParserConfigurationException ex) {
               System.out.println("consumoFull_AuthorizationsJson: "+ex.getMessage());
           } catch (TransformerException ex) {
               System.out.println("consumoFull_AuthorizationsJson: "+ex.getMessage());
           } catch (SAXException ex) {
               System.out.println("consumoFull_AuthorizationsJson: "+ex.getMessage());
           } 
           return mensaje;
       }
       
       public String[] consumoAuthorizationsJson(String targetURL, String clave_acceso, String mail) 
       {
           String[] mensaje=new String[2];
           mensaje[0]="Error";
           mensaje[1]="consumoAuthorizationsJson";
           try {
             
               JsonObject DATA=new JsonObject();
               DATA.addProperty("secret_key",Constantes.secret_key);
               DATA.addProperty("clave_acceso",clave_acceso);
               try{
                    if(mail.length()>0)
                        DATA.addProperty("customer_email",mail);
               }
               catch(Exception ex){}
               
               URL url = new URL(targetURL+"authorizations.json");
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setDoOutput(true);
               conn.setRequestMethod("POST");
               conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
               
               OutputStream os = conn.getOutputStream();
               os.write(DATA.toString().getBytes("UTF-8"));
               os.flush();
               
               if (conn.getResponseCode() != 200) {
                   mensaje[0]="Error";
                   System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                   mensaje[1]="Failed : HTTP error code : " + conn.getResponseCode();
               }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output = br.readLine();
                System.out.println("consumoAuthorizationsJson: "+output); 
                conn.disconnect();
                StringBuilder sb = new StringBuilder();
                String[] respuesta = clsManejoJSON.obtenerMensaje(output,mail,targetURL,false);
                for (int index=1;index<respuesta.length;index++) {
                    sb.append(respuesta[index]).append(" - ");
                }
                mensaje[0]=respuesta[0];
                mensaje[1]="consumoAuthorizationsJson: "+respuesta[0]+" :: "+sb.toString();
                    
           } catch (IOException ex) {
               System.out.println("consumoAuthorizationsJson: "+ex.getMessage());
           }
           return mensaje;
       }
       
}

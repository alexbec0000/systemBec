package servicios;


public final class ComprobantesElectronicosERP {

    /**
     * Permite procesar una factura del ERP, enviándola al Core de Facturación
     *
     * @param ambiente
     * @param secuencial
     * @return String msjRespuesta
     */
    public static String[] procesarFacturaElectronica(int ambiente, String secuencial) {
        // Mensaje de respuesta
        String[] msjRespuesta = new String[2];

        try {
            if (secuencial.length() > 0) {
                // Instanciando un nuevo objeto Factura electrónica
                FacturaElectronica objFacturaElectronica = new FacturaElectronica();
                // Respuesta del servidor|
                msjRespuesta = objFacturaElectronica.generarFactura(secuencial, String.valueOf(ambiente));
                //System.out.println("Secuencial: " + secuencial + "\n " + msjRespuesta[0] + "\n" + msjRespuesta[1]);
            } else {
                msjRespuesta[0] = "Error:";
                msjRespuesta[1] = "Por favor ingrese el secuencial de la factura.";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            msjRespuesta[0] = "Error:";
            msjRespuesta[1] = e.getMessage();
        }

        return msjRespuesta;
    }

    /**
     * Permite procesar un comprobante de retencion del ERP, enviándola al Core de Facturación
     *
     * @param ambiente
     * @param secuencial
     * @return String msjRespuesta
     */
    public static String[] procesarRetencionElectronica(int ambiente, String secuencial) {
        // Mensaje de respuesta
        String[] msjRespuesta = new String[2];

        try {
            if (secuencial.length() > 0) {
                RetencionElectronica objRetencionElectronica = new RetencionElectronica();
                msjRespuesta = objRetencionElectronica.generarRetencion(secuencial, String.valueOf(ambiente));
            } else {
                msjRespuesta[0] = "Error:";
                msjRespuesta[1] = "Por favor ingrese el secuencial de la retencion.";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            msjRespuesta[0] = "Error:";
            msjRespuesta[1] = e.getMessage();
        }

        return msjRespuesta;
    }
    
    /**
     * Permite procesar una nota de debito del ERP, enviándola al Core de Facturación
     *
     * @param ambiente
     * @param secuencial
     * @return String msjRespuesta
     */
    public static String[] procesarNotaDebitoElectronica(int ambiente, String secuencial) {
        // Mensaje de respuesta
        String[] msjRespuesta = new String[2];

        try {
            if (secuencial.length() > 0) {
                NotaDebitoElectronica objNotaDebitoElectronica = new NotaDebitoElectronica();
                msjRespuesta = objNotaDebitoElectronica.generarNotaDebito(secuencial, String.valueOf(ambiente));
            } else {
                msjRespuesta[0] = "Error:";
                msjRespuesta[1] = "Por favor ingrese el secuencial de la nota de debito.";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            msjRespuesta[0] = "Error:";
            msjRespuesta[1] = e.getMessage();
        }

        return msjRespuesta;
    }
    
    /**
     * Permite procesar una nota de cretido del ERP, enviándola al Core de Facturación
     *
     * @param ambiente
     * @param secuencial
     * @return String msjRespuesta
     */
    public static String[] procesarNotaCreditoElectronica(int ambiente, String secuencial) {
        // Mensaje de respuesta
        String[] msjRespuesta = new String[2];

        try {
            if (secuencial.length() > 0) {
                NotaCreditoElectronica objNotaCreditoElectronica = new NotaCreditoElectronica();
                msjRespuesta = objNotaCreditoElectronica.generarNotaCredito(secuencial, String.valueOf(ambiente));
            } else {
                msjRespuesta[0] = "Error:";
                msjRespuesta[1] = "Por favor ingrese el secuencial de la nota de credito.";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            msjRespuesta[0] = "Error:";
            msjRespuesta[1] = e.getMessage();
        }

        return msjRespuesta;
    }
 
}

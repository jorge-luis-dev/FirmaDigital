/*
 * Copyright (C) 2014 jorjoluiso
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package firmadigital;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import autorizacion.ws.sri.gob.ec.AutorizacionComprobantes;
import autorizacion.ws.sri.gob.ec.AutorizacionComprobantesService;
import autorizacion.ws.sri.gob.ec.Mensaje;
import autorizacion.ws.sri.gob.ec.RespuestaComprobante;
import com.thoughtworks.xstream.XStream;
import firmadigital.util.ArchivoUtils;
import firmadigital.util.XStreamUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;

/**
 *
 * @author jorjoluiso
 */
public class AutorizacionComprobantesWs {

    private AutorizacionComprobantesService service;
/*
    public AutorizacionComprobantesWs(String wsdlLocation) throws MalformedURLException {
        
        URL url = new URL(wsdlLocation);
        service = new AutorizacionComprobantesService(url);                    
    }
*/
     public AutorizacionComprobantesWs(String wsdlLocation)
  {
    try
    {
      this.service = new AutorizacionComprobantesService(new URL(wsdlLocation), new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesService"));
    }
    catch (MalformedURLException ex) {
      Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
      
    }
  }
    public RespuestaComprobante llamadaWSAutorizacionInd(String claveDeAcceso) {
        RespuestaComprobante response = null;
        try {
            AutorizacionComprobantes port = this.service.getAutorizacionComprobantesPort();
            response = port.autorizacionComprobante(claveDeAcceso);
        } catch (Exception e) {
            Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, e);
            return response;
        }

        return response;
    }

    public static String autorizarComprobanteIndividual(String claveDeAcceso, String nombreArchivo, String urlWsdl) {
        StringBuilder mensaje = new StringBuilder();
        try {
            String dirAutorizados = "C:\\fd\\Autorizado";
            String dirNoAutorizados = "C:\\fd\\NoAutorizado";

            RespuestaComprobante respuesta = null;

            for (int i = 0; i < 5; i++) {
                respuesta = new AutorizacionComprobantesWs(urlWsdl).llamadaWSAutorizacionInd(claveDeAcceso);

                if (!respuesta.getAutorizaciones().getAutorizacion().isEmpty()) {
                    break;
                }
                Thread.currentThread();
                Thread.sleep(300L);
            }
            int i;
            if (respuesta != null) {
                i = 0;
                for (Autorizacion item : respuesta.getAutorizaciones().getAutorizacion()) {
                    mensaje.append(item.getEstado());

                    item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");

                    XStream xstream = XStreamUtil.getRespuestaXStream();
                    Writer writer = null;
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    writer = new OutputStreamWriter(outputStream, "UTF-8");
                    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

                    xstream.toXML(item, writer);
                    String xmlAutorizacion = outputStream.toString("UTF-8");

                    if ((i == 0) && (item.getEstado().equals("AUTORIZADO"))) {
                        ArchivoUtils.stringToArchivo(dirAutorizados + File.separator + nombreArchivo, xmlAutorizacion);
                        break;
                    }
                    if (item.getEstado().equals("NO AUTORIZADO")) {
                        ArchivoUtils.stringToArchivo(dirNoAutorizados + File.separator + nombreArchivo, xmlAutorizacion);
                        mensaje.append("|" + obtieneMensajesAutorizacion(item));
                        break;
                    }
                    i++;
                }
            }

            if ((respuesta == null) || (respuesta.getAutorizaciones().getAutorizacion().isEmpty() == true)) {
                mensaje.append("TRANSMITIDO SIN RESPUESTA|Ha ocurrido un error en el proceso de la Autorización, por lo que se traslado el archivo a la carpeta de: transmitidosSinRespuesta");

                String dirFirmados = "C:\\fd\\Firmados";
                String dirTransmitidos = dirFirmados + File.separator + "transmitidosSinRespuesta";

                File transmitidos = new File(dirTransmitidos);
                if (!transmitidos.exists()) {
                    new File(dirTransmitidos).mkdir();
                }

                File archivoFirmado = new File(new File(dirFirmados), nombreArchivo);
                if (!ArchivoUtils.copiarArchivo(archivoFirmado, transmitidos.getPath() + File.separator + nombreArchivo)) {
                    mensaje.append("\nError al mover archivo a carpeta de Transmitidos sin Respuesta");
                } else {
                    archivoFirmado.delete();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje.toString();
    }

    public static String obtieneMensajesAutorizacion(Autorizacion autorizacion) {
        StringBuilder mensaje = new StringBuilder();
        for (Mensaje m : autorizacion.getMensajes().getMensaje()) {
            if (m.getInformacionAdicional() != null) {
                mensaje.append("\n" + m.getMensaje() + ": " + m.getInformacionAdicional());
            } else {
                mensaje.append("\n" + m.getMensaje());
            }
        }

        return mensaje.toString();
    }
}

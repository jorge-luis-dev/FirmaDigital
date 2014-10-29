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

import firmadigital.util.ArchivoUtils;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceException;
import recepcion.ws.sri.gob.ec.RecepcionComprobantes;
import recepcion.ws.sri.gob.ec.RecepcionComprobantesService;
import recepcion.ws.sri.gob.ec.RespuestaSolicitud;

/**
 *
 * @author admin
 */
public class EnvioComprobantesWs {

    private static RecepcionComprobantesService service;

    public EnvioComprobantesWs(String wsdlLocation)
            throws MalformedURLException, WebServiceException {
        URL url = new URL(wsdlLocation);        
        service = new RecepcionComprobantesService(url);
    }

    public static RespuestaSolicitud obtenerRespuestaEnvio(File archivo, String ruc, String tipoComprobante, String claveDeAcceso, String urlWsdl) {
        RespuestaSolicitud respuesta = new RespuestaSolicitud();
        EnvioComprobantesWs cliente = null;
        try {
            cliente = new EnvioComprobantesWs(urlWsdl);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
            respuesta.setEstado(ex.getMessage());
            return respuesta;
        } catch (WebServiceException ex) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
            respuesta.setEstado(ex.getMessage());
            return respuesta;
        }
        respuesta = cliente.enviarComprobante(ruc, archivo, tipoComprobante, "1.0.0");

        return respuesta;
    }

    public RespuestaSolicitud enviarComprobante(String ruc, File xmlFile, String tipoComprobante, String versionXsd) {
        RespuestaSolicitud response = null;
        try {
            RecepcionComprobantes port = service.getRecepcionComprobantesPort();
            response = port.validarComprobante(ArchivoUtils.archivoToByte(xmlFile));
        } catch (IOException e) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, null, e);
            response = new RespuestaSolicitud();
            response.setEstado(e.getMessage());
            return response;
        }

        return response;
    }
}


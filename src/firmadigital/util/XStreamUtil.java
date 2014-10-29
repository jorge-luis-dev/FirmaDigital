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
package firmadigital.util;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import autorizacion.ws.sri.gob.ec.Mensaje;
import autorizacion.ws.sri.gob.ec.RespuestaComprobante;
import autorizacion.ws.sri.gob.ec.RespuestaLote;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.Writer;

/**
 *
 * @author jorjoluiso
 */

public class XStreamUtil
{
  public static XStream getLoteXStream()
  {
    XStream xstream = new XStream(new XppDriver()
    {
      public HierarchicalStreamWriter createWriter(Writer out)
      {
        return new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
      }
    });
    xstream.alias("lote", LoteXml.class);
    xstream.alias("comprobante", ComprobanteXml.class);

    xstream.registerConverter(new ComprobanteXmlConverter());

    return xstream;
  }

  public static XStream getRespuestaXStream()
  {
    XStream xstream = new XStream(new XppDriver()
    {
      public HierarchicalStreamWriter createWriter(Writer out)
      {
        return new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
      }
    });
    xstream.alias("respuesta", RespuestaComprobante.class);
    xstream.alias("autorizacion", Autorizacion.class);
    xstream.alias("fechaAutorizacion", XMLGregorianCalendarImpl.class);
    xstream.alias("mensaje", Mensaje.class);
    xstream.registerConverter(new RespuestaDateConverter());

    return xstream;
  }

  public static XStream getRespuestaLoteXStream()
  {
    XStream xstream = new XStream(new XppDriver()
    {
      public HierarchicalStreamWriter createWriter(Writer out)
      {
        return new PrettyPrintWriter(out)
        {
          protected void writeText(QuickWriter writer, String text)
          {
            writer.write(text);
          }
        };
      }
    });
    xstream.alias("respuesta", RespuestaLote.class);
    xstream.alias("autorizacion", Autorizacion.class);
    xstream.alias("fechaAutorizacion", XMLGregorianCalendarImpl.class);
    xstream.alias("mensaje", Mensaje.class);
    xstream.registerConverter(new RespuestaDateConverter());

    return xstream;
  }
}
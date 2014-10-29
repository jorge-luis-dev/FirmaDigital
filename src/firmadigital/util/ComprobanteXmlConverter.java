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

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 *
 * @author jorjoluiso
 */
public class ComprobanteXmlConverter
  implements Converter
{
  public boolean canConvert(Class clazz)
  {
    return clazz.equals(ComprobanteXml.class);
  }

  public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
    ComprobanteXml i = (ComprobanteXml)o;

    writer.setValue(i.getFileXML());
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
    ComprobanteXml item = new ComprobanteXml();
    item.setTipo(reader.getAttribute("tipo"));
    item.setVersion(reader.getAttribute("version"));
    item.setFileXML(reader.getValue());

    return item;
  }
}
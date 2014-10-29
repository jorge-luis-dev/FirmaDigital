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

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorjoluiso
 */

public class RespuestaDateConverter
  implements Converter
{
  public boolean canConvert(Class clazz)
  {
    return clazz.equals(XMLGregorianCalendarImpl.class);
  }

  public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
    XMLGregorianCalendarImpl i = (XMLGregorianCalendarImpl)o;
    writer.setValue(Constantes.dateTimeFormat.format(i.toGregorianCalendar().getTime()));
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc)
  {
    Date date = null;
    try {
      date = Constantes.dateTimeFormat.parse(reader.getValue());
    } catch (ParseException ex) {
      Logger.getLogger(RespuestaDateConverter.class.getName()).log(Level.SEVERE, null, ex);
    }
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);
    XMLGregorianCalendarImpl item = new XMLGregorianCalendarImpl(cal);

    return item;
  }
}

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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author admin
 */

public class LectorXPath
{
  private final String xmlFile;
  private Document xmlDocument;
  private XPath xPath;

  public LectorXPath(String xmlFile)
  {
    this.xmlFile = xmlFile;
    inicializar();
  }

  private void inicializar() {
    try {
      this.xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.xmlFile);

      this.xPath = XPathFactory.newInstance().newXPath();
    }
    catch (IOException ex) {
      Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SAXException ex) {
      Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParserConfigurationException ex) {
      Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public Object leerArchivo(String expression, QName returnType) {
    try {
      XPathExpression xPathExpression = this.xPath.compile(expression);
      return xPathExpression.evaluate(this.xmlDocument, returnType);
    } catch (XPathExpressionException ex) {
      Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, null, ex);
    }return null;
  }
}

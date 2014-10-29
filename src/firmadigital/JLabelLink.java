/*
 * Copyright (C) 2014 
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

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;

/**
 *
 * @author admin
 */
public class JLabelLink extends JLabel {

    private String text = "";
    private String TextLink = null;
    private URI uri;

    public JLabelLink() {
        super();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setPreferredSize(new Dimension(34, 14));
        this.setVisible(true);
        //Eventos del raton sobre el JLabel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Abrir_URL(uri);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setText(text, false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setText(text, true);
                repaint();
            }
        });

    }

    /**
     * Coloca la dirección web
     */
    public void setLink(String link) {
        try {
            uri = new URI(link);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Coloca el texto que contiene el enlace web
     */
    public void setTextLink(String texto) {
        this.TextLink = texto;
    }

    /**
     * Se sobreescribe metodo
     */
    @Override
    public void setText(String value) {
        setText(value, false);
    }

    /**
     * Retorna el texto sin las etiquetas HTML
     */
    public String getTextSinFormato() {
        return text;
    }

    /**
     * Da formato al texto para añadir las etiquetas HTML necesarias
     */
    private void setText(String text, boolean inout) {
        //Estilo CSS
        String css = "<style type='text/css'>"
                + ".link {text-decoration: none;font-weight: bold;color:#0404B4;}"
                + ".link_hover{color:rgb(255,0,0);text-decoration:underline;}"
                + "</style>";

        //estilo css segun el mouse este dentro o fuera
        String clase = (inout) ? "link" : "link_hover";
        //forma el texto HTML
        String html_text = (TextLink != null) ? text.replace(TextLink, "<span class='" + clase + "' >" + TextLink + "</span>") : text;
        //coloca al padre
        super.setText("<html>" + css + "<span>" + html_text + "<span/></html>");

        this.text = text;
    }

    /**
     * Abre enlace web en el navegador
     */
    private void Abrir_URL(URI uri) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                System.err.println("Error: No se pudo abrir el enlace" + e.getMessage());
            }
        } else {
            System.err.println("Error: Error de compatibilidad en la plataforma actual. No se puede abrir enlaces web.");
        }
    }

}


package autorizacion.ws.sri.gob.ec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para autorizacionComprobanteLoteMasivoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="autorizacionComprobanteLoteMasivoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RespuestaAutorizacionLoteMasivo" type="{http://ec.gob.sri.ws.autorizacion}respuestaLote" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autorizacionComprobanteLoteMasivoResponse", propOrder = {
    "respuestaAutorizacionLoteMasivo"
})
public class AutorizacionComprobanteLoteMasivoResponse {

    @XmlElement(name = "RespuestaAutorizacionLoteMasivo")
    protected RespuestaLote respuestaAutorizacionLoteMasivo;

    /**
     * Obtiene el valor de la propiedad respuestaAutorizacionLoteMasivo.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaLote }
     *     
     */
    public RespuestaLote getRespuestaAutorizacionLoteMasivo() {
        return respuestaAutorizacionLoteMasivo;
    }

    /**
     * Define el valor de la propiedad respuestaAutorizacionLoteMasivo.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaLote }
     *     
     */
    public void setRespuestaAutorizacionLoteMasivo(RespuestaLote value) {
        this.respuestaAutorizacionLoteMasivo = value;
    }

}

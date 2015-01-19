//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.24 at 11:30:02 AM CEST 
//


package de.drv.dsrv.spoc.extra.v1_3.jaxb.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.components.DataType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.components.ElementWithOptionalVersionType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.plugins.TransformedDataType;
import org.w3._2001._04.xmlenc.EncryptedDataType;


/**
 * <p>Java class for PackageResponseBodyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PackageResponseBodyType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.extra-standard.de/namespace/components/1}ElementWithOptionalVersionType">
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{http://www.w3.org/2001/04/xmlenc#}EncryptedData"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/components/1}TransformedData"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/response/1}Message" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/components/1}Data"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PackageResponseBodyType", propOrder = {
    "encryptedData",
    "transformedData",
    "message",
    "data"
})
public class PackageResponseBodyType
    extends ElementWithOptionalVersionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "EncryptedData", namespace = "http://www.w3.org/2001/04/xmlenc#")
    protected EncryptedDataType encryptedData;
    @XmlElement(name = "TransformedData", namespace = "http://www.extra-standard.de/namespace/components/1")
    protected TransformedDataType transformedData;
    @XmlElement(name = "Message")
    protected List<MessageResponseType> message;
    @XmlElement(name = "Data", namespace = "http://www.extra-standard.de/namespace/components/1")
    protected DataType data;

    /**
     * Gets the value of the encryptedData property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptedDataType }
     *     
     */
    public EncryptedDataType getEncryptedData() {
        return encryptedData;
    }

    /**
     * Sets the value of the encryptedData property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptedDataType }
     *     
     */
    public void setEncryptedData(EncryptedDataType value) {
        this.encryptedData = value;
    }

    /**
     * Gets the value of the transformedData property.
     * 
     * @return
     *     possible object is
     *     {@link TransformedDataType }
     *     
     */
    public TransformedDataType getTransformedData() {
        return transformedData;
    }

    /**
     * Sets the value of the transformedData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransformedDataType }
     *     
     */
    public void setTransformedData(TransformedDataType value) {
        this.transformedData = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the message property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessageResponseType }
     * 
     * 
     */
    public List<MessageResponseType> getMessage() {
        if (message == null) {
            message = new ArrayList<MessageResponseType>();
        }
        return this.message;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link DataType }
     *     
     */
    public DataType getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType }
     *     
     */
    public void setData(DataType value) {
        this.data = value;
    }

}
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.24 at 11:30:02 AM CEST 
//


package de.drv.dsrv.spoc.extra.v1_3.jaxb.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoggingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoggingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.extra-standard.de/namespace/logging/1}LogSequence"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/logging/1}LogStream"/>
 *       &lt;/choice>
 *       &lt;attribute name="version" use="required" type="{http://www.extra-standard.de/namespace/logging/1}LoggingVersionType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoggingType", propOrder = {
    "logSequenceOrLogStream"
})
public class LoggingType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "LogSequence", type = LogSequenceType.class),
        @XmlElement(name = "LogStream", type = LogStreamType.class)
    })
    protected List<Serializable> logSequenceOrLogStream;
    @XmlAttribute(name = "version", required = true)
    protected LoggingVersionType version;

    /**
     * Gets the value of the logSequenceOrLogStream property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logSequenceOrLogStream property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogSequenceOrLogStream().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LogSequenceType }
     * {@link LogStreamType }
     * 
     * 
     */
    public List<Serializable> getLogSequenceOrLogStream() {
        if (logSequenceOrLogStream == null) {
            logSequenceOrLogStream = new ArrayList<Serializable>();
        }
        return this.logSequenceOrLogStream;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link LoggingVersionType }
     *     
     */
    public LoggingVersionType getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoggingVersionType }
     *     
     */
    public void setVersion(LoggingVersionType value) {
        this.version = value;
    }

}

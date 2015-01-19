//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.24 at 11:30:02 AM CEST 
//


package de.drv.dsrv.spoc.extra.v1_3.jaxb.request;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.components.AnyPlugInContainerType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.components.ElementWithOptionalVersionType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.components.SignaturesType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.logging.LoggingType;


/**
 * <p>Java class for PackageRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PackageRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.extra-standard.de/namespace/components/1}ElementWithOptionalVersionType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/request/1}PackageHeader"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/request/1}PackagePlugIns" minOccurs="0"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/request/1}PackageBody"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/logging/1}Logging" minOccurs="0"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/components/1}Signatures" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PackageRequestType", propOrder = {
    "packageHeader",
    "packagePlugIns",
    "packageBody",
    "logging",
    "signatures"
})
public class PackageRequestType
    extends ElementWithOptionalVersionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "PackageHeader", required = true)
    protected PackageRequestHeaderType packageHeader;
    @XmlElement(name = "PackagePlugIns")
    protected AnyPlugInContainerType packagePlugIns;
    @XmlElement(name = "PackageBody", required = true)
    protected PackageRequestBodyType packageBody;
    @XmlElement(name = "Logging", namespace = "http://www.extra-standard.de/namespace/logging/1")
    protected LoggingType logging;
    @XmlElement(name = "Signatures", namespace = "http://www.extra-standard.de/namespace/components/1")
    protected SignaturesType signatures;

    /**
     * Gets the value of the packageHeader property.
     * 
     * @return
     *     possible object is
     *     {@link PackageRequestHeaderType }
     *     
     */
    public PackageRequestHeaderType getPackageHeader() {
        return packageHeader;
    }

    /**
     * Sets the value of the packageHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageRequestHeaderType }
     *     
     */
    public void setPackageHeader(PackageRequestHeaderType value) {
        this.packageHeader = value;
    }

    /**
     * Gets the value of the packagePlugIns property.
     * 
     * @return
     *     possible object is
     *     {@link AnyPlugInContainerType }
     *     
     */
    public AnyPlugInContainerType getPackagePlugIns() {
        return packagePlugIns;
    }

    /**
     * Sets the value of the packagePlugIns property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnyPlugInContainerType }
     *     
     */
    public void setPackagePlugIns(AnyPlugInContainerType value) {
        this.packagePlugIns = value;
    }

    /**
     * Gets the value of the packageBody property.
     * 
     * @return
     *     possible object is
     *     {@link PackageRequestBodyType }
     *     
     */
    public PackageRequestBodyType getPackageBody() {
        return packageBody;
    }

    /**
     * Sets the value of the packageBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageRequestBodyType }
     *     
     */
    public void setPackageBody(PackageRequestBodyType value) {
        this.packageBody = value;
    }

    /**
     * Gets the value of the logging property.
     * 
     * @return
     *     possible object is
     *     {@link LoggingType }
     *     
     */
    public LoggingType getLogging() {
        return logging;
    }

    /**
     * Sets the value of the logging property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoggingType }
     *     
     */
    public void setLogging(LoggingType value) {
        this.logging = value;
    }

    /**
     * Gets the value of the signatures property.
     * 
     * @return
     *     possible object is
     *     {@link SignaturesType }
     *     
     */
    public SignaturesType getSignatures() {
        return signatures;
    }

    /**
     * Sets the value of the signatures property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignaturesType }
     *     
     */
    public void setSignatures(SignaturesType value) {
        this.signatures = value;
    }

}
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.24 at 11:30:02 AM CEST 
//


package de.drv.dsrv.spoc.extra.v1_3.jaxb.service;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.components.ApplicationType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.components.ClassifiableIDType;
import de.drv.dsrv.spoc.extra.v1_3.jaxb.components.ReportType;


/**
 * <p>Java class for ExtraErrorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExtraErrorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Reason" type="{http://www.extra-standard.de/namespace/service/1}ExtraErrorReasonType"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/components/1}RequestID" minOccurs="0"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/components/1}ResponseID" minOccurs="0"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/components/1}TimeStamp"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/components/1}Application" minOccurs="0"/>
 *         &lt;element ref="{http://www.extra-standard.de/namespace/components/1}Report"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.extra-standard.de/namespace/components/1}AbstractVersionType">
 *             &lt;enumeration value="1.0"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtraErrorType", propOrder = {
    "reason",
    "requestID",
    "responseID",
    "timeStamp",
    "application",
    "report"
})
@XmlRootElement(name = "ExtraError")
public class ExtraErrorType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Reason", required = true)
    protected ExtraErrorReasonType reason;
    @XmlElement(name = "RequestID", namespace = "http://www.extra-standard.de/namespace/components/1")
    protected ClassifiableIDType requestID;
    @XmlElement(name = "ResponseID", namespace = "http://www.extra-standard.de/namespace/components/1")
    protected ClassifiableIDType responseID;
    @XmlElement(name = "TimeStamp", namespace = "http://www.extra-standard.de/namespace/components/1", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp;
    @XmlElement(name = "Application", namespace = "http://www.extra-standard.de/namespace/components/1")
    protected ApplicationType application;
    @XmlElement(name = "Report", namespace = "http://www.extra-standard.de/namespace/components/1", required = true)
    protected ReportType report;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link ExtraErrorReasonType }
     *     
     */
    public ExtraErrorReasonType getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtraErrorReasonType }
     *     
     */
    public void setReason(ExtraErrorReasonType value) {
        this.reason = value;
    }

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link ClassifiableIDType }
     *     
     */
    public ClassifiableIDType getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassifiableIDType }
     *     
     */
    public void setRequestID(ClassifiableIDType value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the responseID property.
     * 
     * @return
     *     possible object is
     *     {@link ClassifiableIDType }
     *     
     */
    public ClassifiableIDType getResponseID() {
        return responseID;
    }

    /**
     * Sets the value of the responseID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassifiableIDType }
     *     
     */
    public void setResponseID(ClassifiableIDType value) {
        this.responseID = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationType }
     *     
     */
    public ApplicationType getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationType }
     *     
     */
    public void setApplication(ApplicationType value) {
        this.application = value;
    }

    /**
     * Gets the value of the report property.
     * 
     * @return
     *     possible object is
     *     {@link ReportType }
     *     
     */
    public ReportType getReport() {
        return report;
    }

    /**
     * Sets the value of the report property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportType }
     *     
     */
    public void setReport(ReportType value) {
        this.report = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}

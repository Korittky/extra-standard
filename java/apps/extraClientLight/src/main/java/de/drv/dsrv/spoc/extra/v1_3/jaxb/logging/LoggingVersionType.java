//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.24 at 11:30:02 AM CEST 
//


package de.drv.dsrv.spoc.extra.v1_3.jaxb.logging;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoggingVersionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LoggingVersionType">
 *   &lt;restriction base="{http://www.extra-standard.de/namespace/components/1}AbstractVersionType">
 *     &lt;enumeration value="1.0"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LoggingVersionType")
@XmlEnum
public enum LoggingVersionType {

    @XmlEnumValue("1.0")
    VERSION_1_0("1.0");
    private final String value;

    LoggingVersionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LoggingVersionType fromValue(String v) {
        for (LoggingVersionType c: LoggingVersionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.24 at 11:30:02 AM CEST 
//


package de.drv.dsrv.spoc.extra.v1_3.jaxb.plugins;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.drv.dsrv.spoc.extra.v1_3.jaxb.plugins package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DataSource_QNAME = new QName("http://www.extra-standard.de/namespace/plugins/1", "DataSource");
    private final static QName _DataTransforms_QNAME = new QName("http://www.extra-standard.de/namespace/plugins/1", "DataTransforms");
    private final static QName _Contacts_QNAME = new QName("http://www.extra-standard.de/namespace/plugins/1", "Contacts");
    private final static QName _Certificates_QNAME = new QName("http://www.extra-standard.de/namespace/plugins/1", "Certificates");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.drv.dsrv.spoc.extra.v1_3.jaxb.plugins
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TransformedDataType }
     * 
     */
    public TransformedDataType createTransformedDataType() {
        return new TransformedDataType();
    }

    /**
     * Create an instance of {@link DataTransformsType }
     * 
     */
    public DataTransformsType createDataTransformsType() {
        return new DataTransformsType();
    }

    /**
     * Create an instance of {@link ContactsType }
     * 
     */
    public ContactsType createContactsType() {
        return new ContactsType();
    }

    /**
     * Create an instance of {@link DataSourceType }
     * 
     */
    public DataSourceType createDataSourceType() {
        return new DataSourceType();
    }

    /**
     * Create an instance of {@link CertificatesType }
     * 
     */
    public CertificatesType createCertificatesType() {
        return new CertificatesType();
    }

    /**
     * Create an instance of {@link EndpointType }
     * 
     */
    public EndpointType createEndpointType() {
        return new EndpointType();
    }

    /**
     * Create an instance of {@link SignatureAlgorithmType }
     * 
     */
    public SignatureAlgorithmType createSignatureAlgorithmType() {
        return new SignatureAlgorithmType();
    }

    /**
     * Create an instance of {@link DataSetType }
     * 
     */
    public DataSetType createDataSetType() {
        return new DataSetType();
    }

    /**
     * Create an instance of {@link ContactType }
     * 
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link CompressionType }
     * 
     */
    public CompressionType createCompressionType() {
        return new CompressionType();
    }

    /**
     * Create an instance of {@link CompressionAlgorithmType }
     * 
     */
    public CompressionAlgorithmType createCompressionAlgorithmType() {
        return new CompressionAlgorithmType();
    }

    /**
     * Create an instance of {@link DataType }
     * 
     */
    public DataType createDataType() {
        return new DataType();
    }

    /**
     * Create an instance of {@link EncryptionAlgorithmType }
     * 
     */
    public EncryptionAlgorithmType createEncryptionAlgorithmType() {
        return new EncryptionAlgorithmType();
    }

    /**
     * Create an instance of {@link X509CertificateType }
     * 
     */
    public X509CertificateType createX509CertificateType() {
        return new X509CertificateType();
    }

    /**
     * Create an instance of {@link DataContainerType }
     * 
     */
    public DataContainerType createDataContainerType() {
        return new DataContainerType();
    }

    /**
     * Create an instance of {@link SignatureType }
     * 
     */
    public SignatureType createSignatureType() {
        return new SignatureType();
    }

    /**
     * Create an instance of {@link EncryptionType }
     * 
     */
    public EncryptionType createEncryptionType() {
        return new EncryptionType();
    }

    /**
     * Create an instance of {@link SpecificationType }
     * 
     */
    public SpecificationType createSpecificationType() {
        return new SpecificationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataSourceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.extra-standard.de/namespace/plugins/1", name = "DataSource")
    public JAXBElement<DataSourceType> createDataSource(DataSourceType value) {
        return new JAXBElement<DataSourceType>(_DataSource_QNAME, DataSourceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataTransformsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.extra-standard.de/namespace/plugins/1", name = "DataTransforms")
    public JAXBElement<DataTransformsType> createDataTransforms(DataTransformsType value) {
        return new JAXBElement<DataTransformsType>(_DataTransforms_QNAME, DataTransformsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.extra-standard.de/namespace/plugins/1", name = "Contacts")
    public JAXBElement<ContactsType> createContacts(ContactsType value) {
        return new JAXBElement<ContactsType>(_Contacts_QNAME, ContactsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificatesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.extra-standard.de/namespace/plugins/1", name = "Certificates")
    public JAXBElement<CertificatesType> createCertificates(CertificatesType value) {
        return new JAXBElement<CertificatesType>(_Certificates_QNAME, CertificatesType.class, null, value);
    }

}
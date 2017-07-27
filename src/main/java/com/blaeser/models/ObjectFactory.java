//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.07.27 um 07:26:57 PM CEST 
//


package com.blaeser.models;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

    private final static QName _SqlTemplateGroup_QNAME = new QName("", "sqlTemplateGroup");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SqlTemplateGroupType }
     * 
     */
    public SqlTemplateGroupType createSqlTemplateGroupType() {
        return new SqlTemplateGroupType();
    }

    /**
     * Create an instance of {@link SqlTemplateType }
     * 
     */
    public SqlTemplateType createSqlTemplateType() {
        return new SqlTemplateType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SqlTemplateGroupType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "sqlTemplateGroup")
    public JAXBElement<SqlTemplateGroupType> createSqlTemplateGroup(SqlTemplateGroupType value) {
        return new JAXBElement<SqlTemplateGroupType>(_SqlTemplateGroup_QNAME, SqlTemplateGroupType.class, null, value);
    }

}

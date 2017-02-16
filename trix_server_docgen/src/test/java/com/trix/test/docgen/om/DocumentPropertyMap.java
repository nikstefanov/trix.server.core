package com.trix.test.docgen.om;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DocumentPropertyMap")
public class DocumentPropertyMap {
  @XmlElement(name = "property", required = true)
  private final List<DocumentProperty> properties = new ArrayList<DocumentProperty>();

  public List<DocumentProperty> getProperties() {
    return this.properties;
  }
}
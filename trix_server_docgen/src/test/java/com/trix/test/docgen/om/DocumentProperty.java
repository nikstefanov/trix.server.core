package com.trix.test.docgen.om;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("restriction")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentProperty {
  public String name;
  public Object value;
  
  public DocumentProperty() {
  }
  
  public DocumentProperty(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Object getValue() {
    return value;
  }
  public void setValue(Object value) {
    this.value = value;
  }
}

package com.trix.test.docgen.om;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DocumentPropertyList")
public class DocumentPropertyList {
  @XmlElement(name = "entity", required = true)
  private List<Object> entities;
  
  public void setEntities(List<Object> entities) {
    this.entities = entities;
  }
  
  public List<Object> getEntities() {
    return this.entities;
  }
}
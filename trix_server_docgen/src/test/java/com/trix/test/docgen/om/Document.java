package com.trix.test.docgen.om;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Document {
  private long id;
  private Integer version;
  private boolean active;
  private String documentNo;
  private Date documentDate;
  
  //@SuppressWarnings("unchecked")
  @XmlJavaTypeAdapter(DocumentPropertiesXmlAdaptor.class)
  protected Map<String, Object> documentProperties = new HashMap<String, Object>();
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getDocumentNo() {
    return documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public Date getDocumentDate() {
    return documentDate;
  }

  public void setDocumentDate(Date documentDate) {
    this.documentDate = documentDate;
  }
}

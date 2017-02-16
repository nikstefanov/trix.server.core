package com.trix.docgen.dom.inf;

import com.trix.dads.DADataSource;
import com.trix.docgen.dom.om.DocumentGenerator;

public class DocumentGeneratorFactory {
  private DADataSource daDataSource;
  
  public void setDaDataSource(DADataSource daDataSource) {
    this.daDataSource = daDataSource;
  }
  
  public DocumentGenerator create() {
    DocumentGenerator documentGenerator = new DocumentGenerator();
    documentGenerator.setDADataSource(daDataSource);
    return documentGenerator;   
  }
  
//  public void init(DocumentGenerator documentGenerator) {
//    documentGenerator.setTemplateDataSource(templateDataSource);
//  }
}

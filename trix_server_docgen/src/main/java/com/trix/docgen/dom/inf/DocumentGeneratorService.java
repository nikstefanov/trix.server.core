package com.trix.docgen.dom.inf;

import java.io.OutputStream;

import com.trix.docgen.dom.om.DocumentGenerator;

public class DocumentGeneratorService {
  private DocumentGeneratorFactory  documentGeneratorFactory;

  public void setDocumentGeneratorFactory(
      DocumentGeneratorFactory documentGeneratorFactory) {
    this.documentGeneratorFactory = documentGeneratorFactory;
  }

  public void generate(String dataXml, String type,
       String templateUri,OutputStream os) throws Exception {
    DocumentGenerator documentGenerator = documentGeneratorFactory.create();
    documentGenerator.setDataXml(dataXml);
    documentGenerator.generate(type,templateUri,os);
  }
}

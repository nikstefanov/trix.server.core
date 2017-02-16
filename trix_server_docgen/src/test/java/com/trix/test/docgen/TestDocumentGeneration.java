package com.trix.test.docgen;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trix.docgen.dom.inf.DocumentGeneratorService;
import com.trix.test.docgen.om.RentalContract;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "/META-INF/spring/docgen-dom-context.xml",
    "/META-INF/spring/dads-app-context.xml"
})
public class TestDocumentGeneration {
  @Autowired
  private DocumentGeneratorService documentGeneratorService;
  
  @Test
  public void testObjectToPdf() throws Exception {
    RentalContract rentalContract = new RentalContract();
    rentalContract.setId(1);
    rentalContract.setDocumentNo("123");
    rentalContract.setDocumentDate(new Date());
    rentalContract.setClient("Client Name");
    rentalContract.setBookingLocation("Bourgas, Demokraciq 111");
    
    JAXBContext jaxbContext = JAXBContext.newInstance(
        new Class[] { RentalContract.class });
    Marshaller m = jaxbContext.createMarshaller();

    m.marshal(rentalContract, System.out);

    
  }
  
  @Test
  public void testDocxGenerator() throws Exception {
    Logger  logger = Logger.getLogger("org.docx4j.convert.out.pdf.viaXSLFO");
    logger.setLevel(Level.OFF);
    FileOutputStream fos = new FileOutputStream("D:/Users/User/"
        + "Development/Projects/DataBinding/OpenDoPE/SampleDocuments/"
        + "invoice.pdf");
    documentGeneratorService.generate(
        FileUtils.readFileToString(new File(
            "D:/Users/User/Development/Projects/DataBinding/OpenDoPE/"
                + "SampleDocuments/SampleData.xml"),
            "utf-8"),  
        "pdf",
        "file://D:/Users/User/Development/Projects/DataBinding"
        + "/OpenDoPE/SampleDocuments/"
        + "invoice.docx",
//        + "noCustomXML.docx",
//        + "noBindings.docx",
        fos
        );    
  }
  
  @Test
  public void testDocxGeneratorRental() throws Exception {
    Logger  logger = Logger.getLogger("org.docx4j.convert.out.pdf.viaXSLFO");
    logger.setLevel(Level.OFF);
    FileOutputStream fos = new FileOutputStream("D:/Users/User/"
        + "Development/Projects/DataBinding/OpenDoPE/Rental/"
        + "RentalContracts.pdf");
    documentGeneratorService.generate(
        FileUtils.readFileToString(new File(
            "D:/Users/User/Development/Projects/Rental/"
                + "rentalContractUTF-8.xml"),
            "utf-8"),  
        "pdf",
        "file://D:/Users/User/Development/Projects/DataBinding"
        + "/OpenDoPE/Rental/RentalContracts.opendope.docx",
        fos
        );    
  }
  
  @Test
  public void testMarshalledObjectGenerator() throws Exception {
    Logger  logger = Logger.getLogger("org.docx4j.convert.out.pdf.viaXSLFO");
    logger.setLevel(Level.OFF);
    
    RentalContract rentalContract = new RentalContract();
    rentalContract.setId(1);
    rentalContract.setDocumentNo("123");
    rentalContract.setDocumentDate(new Date());
    rentalContract.setClient("Plamen Stojanov");
    rentalContract.setBookingLocation("Bourgas, Demokraciq 111");
    
    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();    
    JAXBContext jaxbContext = JAXBContext.newInstance(
        new Class[] { RentalContract.class });
    Marshaller m = jaxbContext.createMarshaller();
    m.marshal(rentalContract, byteOutputStream);    
    
    FileOutputStream fos = new FileOutputStream("D:/Users/User/"
        + "Development/Projects/DataBinding/OpenDoPE/Rental/"
        + "RentalContractsTest.pdf");
    documentGeneratorService.generate(
        byteOutputStream.toString("UTF-8"),  
        "pdf",
        "file://D:/Users/User/Development/Projects/DataBinding"
        + "/OpenDoPE/Rental/RentalContractsTest.opendope.docx",
//        + "noCustomXML.docx",
//        + "noBindings.docx",
        fos
        );    
  }
  
  //@Test
   public void testXpath() {
     try {
       // 1. Instantiate an XPathFactory.
     javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
  
     // 2. Use the XPathFactory to create a new XPath object
     javax.xml.xpath.XPath xpath = factory.newXPath();
  
     // set the NamespaceContext to
     // org.apache.xalan.extensions.ExtensionNamespaceContext
     xpath
         .setNamespaceContext(new org.apache.xalan.extensions.ExtensionNamespaceContext());
  
     // set the XPathFunctionResolver to
     // org.apache.xalan.extensions.XPathFunctionResolverImpl
     xpath
         .setXPathFunctionResolver(new org.apache.xalan.extensions.XPathFunctionResolverImpl());
  
     // 3. Compile an XPath string into an XPathExpression
     // javax.xml.xpath.XPathExpression expression =
     // xpath.compile("/customer/name");
     javax.xml.xpath.XPathExpression expression = xpath
         .compile("java:com.trix.test.docgen.CustomXSLTFunctions.printHello(/customer/name/text())");
     // javax.xml.xpath.XPathExpression expression =
     // xpath.compile("math:max(/customer/v)");
  
     // 4. Evaluate the XPath expression on an input document
     String result = expression
         .evaluate(new org.xml.sax.InputSource(
             "D:\\Users\\User\\Development\\Projects\\DataBinding\\OpenDoPE\\SampleDocuments\\"
                 + "CustomFunction.xml"));
  
     System.out.println("result:[" + result + "]");
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
}

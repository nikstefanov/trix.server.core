package com.trix.test.docgen;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trix.docgen.dom.inf.DocumentGeneratorService;
import com.trix.docgen.dom.utils.FormatDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/docgen-dom-context.xml",
    "/META-INF/spring/dads-app-context.xml" })
public class CustomXSLTFunctions {
  @Autowired
  private DocumentGeneratorService documentGeneratorService;

  public static String printArgument() {
    return "Argument is: " + "no_arg";
  }

  public static String printHello(String name) {
    return "Helo " + name;
  }

  @Test
  public void testXpath() {
    try {
      // 1. Instantiate an XPathFactory.
      javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory
          .newInstance();

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
      javax.xml.xpath.XPathExpression expression = xpath.compile(
          "java:com.trix.docgen.dom.utils.Utils.toFullName(/rentalContract/documentProperties/property[name='client']/value/nomenclatureProperties/property[name='person']/value)"
//          "java:com.trix.docgen.dom.utils.Utils.toAddress(/rentalContract/documentProperties/property[name='client']/value/nomenclatureProperties/property[name='person']/value/nomenclatureProperties/property[name='address2']/value)"
//          "java:com.trix.docgen.dom.utils.Utils.getMoney(/rentalContract/documentProperties/property[name='pricePerDayAfterDiscount']/value)"
//          "java:com.trix.docgen.dom.utils.FormatMoney.toMoney(/rentalContract/documentProperties/property[name='pricePerDayAfterDiscount']/value)"
//          "java:com.trix.docgen.dom.utils.Utils.testMethod()"
          );
      // javax.xml.xpath.XPathExpression expression =
      // xpath.compile("math:max(/customer/v)");

      // 4. Evaluate the XPath expression on an input document
      String result = expression
          .evaluate(new org.xml.sax.InputSource(
              "D:\\Users\\User\\Development\\Projects\\Rental\\"
                  + "rentalContractUTF-8.xml"));

      System.out.println("result:[" + result + "]");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDocxGenerator() throws Exception {
    Logger logger = Logger.getLogger("org.docx4j.convert.out.pdf.viaXSLFO");
    logger.setLevel(Level.ALL);
    FileOutputStream fos = new FileOutputStream("D:/Users/User/"
        + "Development/Projects/DataBinding/OpenDoPE/SampleDocuments/"
        + "CustomFunctionEvaluated.docx");

    //
    // WHY WE DO THOSE PATCHES IN DOX4J ...
    //

//    // PATCH !!!!
//    org.docx4j.jaxb.NamespacePrefixMappings
//        .registerPrefixMappings("xmlns:java='http://xml.apache.org/xalan/java'");

    // // PATCH !!!!
    // Class c = Class.forName("org.docx4j.openpackaging.parts.XmlPart");
    // Field[] fields = c.getDeclaredFields();
    // for (int i=0; i<fields.length; i++) {
    // fields[i].setAccessible(true);
    // if ("xPath".equals(fields[i].getName())) {
    // ((javax.xml.xpath.XPath)fields[i].get(null)).setXPathFunctionResolver(
    // new org.apache.xalan.extensions.XPathFunctionResolverImpl());
    // break;
    // }
    // }

    org.docx4j.utils.XPathFactoryUtil.getXPathFactory()
        .setXPathFunctionResolver(
            new org.apache.xalan.extensions.XPathFunctionResolverImpl());

    documentGeneratorService
        .generate(FileUtils.readFileToString(new File(
            "D:/Users/User/Development/Projects/DataBinding/OpenDoPE/"
                + "SampleDocuments/CustomFunction.xml"), "utf-8"), "docx",
            "file://D:/Users/User/Development/Projects/DataBinding"
                + "/OpenDoPE/SampleDocuments/"
                + "Custom_Functions.opendope.1.docx",
            // + "noCustomXML.docx",
            // + "noBindings.docx",
            fos);
  }
  
  @Test
  public void shortDateTest() throws ParseException {
    System.out.println(FormatDateTime.toShortDate("2013-05-28T19:47:21+03:00"));
  }
  
  @Test
  public void timeTest() throws ParseException {
    System.out.println(FormatDateTime.toTime("2013-05-28T19:47:21+03:00"));
  }

}

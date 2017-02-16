package com.trix.docgen.dom.om;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.Conversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.jaxb.NamespacePrefixMappings;
import org.docx4j.model.datastorage.BindingHandler;
import org.docx4j.model.datastorage.OpenDoPEHandler;
import org.docx4j.model.datastorage.OpenDoPEIntegrity;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.LoadFromZipNG;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.CustomXmlDataStoragePart;
import org.docx4j.utils.XPathFactoryUtil;
import org.opendope.xpaths.Xpaths;
import org.opendope.xpaths.Xpaths.Xpath;

import com.trix.dads.DADataSource;

public class DocumentGenerator {
  private DADataSource dataSource;
  private String dataXml;

  public DocumentGenerator() {
  }

  public void setDADataSource(DADataSource dataSource) {
    this.dataSource = dataSource;
  }

  public String getDataXml() {
    return dataXml;
  }

  public void setDataXml(String dataXml) {
    this.dataXml = dataXml;
  }

  public void generate(String type, String uri, OutputStream os)
      throws Exception {
    if (dataXml == null) {
      throw new Docx4JException("No xml data file provided!");
    }
    ByteArrayInputStream xis = new ByteArrayInputStream(
        dataXml.getBytes("utf-8"));
    if (dataSource == null) {
      throw new Docx4JException("No template file provided!");
    }
    InputStream docxInputStream = dataSource.getData(uri);

    // Custom xslt functions are defined in some namespace. Here we give
    // reference to this namespace.
    NamespacePrefixMappings
        .registerPrefixMappings("xmlns:java='http://xml.apache.org/xalan/java'");
    XPathFactoryUtil.getXPathFactory().setXPathFunctionResolver(
        new org.apache.xalan.extensions.XPathFunctionResolverImpl());

    final WordprocessingMLPackage wordMLPackage;
    WordprocessingMLPackage tmpPkg = null;
    LoadFromZipNG loader = new LoadFromZipNG();
    try {
      tmpPkg = (WordprocessingMLPackage) loader.get(docxInputStream);
    } catch (Exception e) {
      throw new Docx4JException(
          "Error reading docx file (is this a valid docx?)");
    }
    if (tmpPkg == null) {
      throw new Docx4JException(
          "No docx file provided!The supplied file is not docx!");
    }
    // if (xis==null) {
    // throw new Docx4JException("No xml data file provided");
    // }
    wordMLPackage = tmpPkg;

    // Find custom xml item id
    String itemId = this.getCustomXmlItemId(wordMLPackage).toLowerCase();

    // Inject data_file.xml
    CustomXmlDataStoragePart customXmlDataStoragePart = (CustomXmlDataStoragePart) wordMLPackage
        .getCustomXmlDataStorageParts().get(itemId);
    if (customXmlDataStoragePart == null) {
      throw new Docx4JException("Couldn't find CustomXmlDataStoragePart");
    }
    customXmlDataStoragePart.getData().setDocument(xis);
    // java.lang.NullPointerException
    // org.docx4j.openpackaging.parts.XmlPart.setDocument(XmlPart.java:129)

    final SaveToZipFile saver = new SaveToZipFile(wordMLPackage);

    // Process conditionals and repeats
    OpenDoPEHandler odh = new OpenDoPEHandler(wordMLPackage);
    odh.preprocess();

    OpenDoPEIntegrity odi = new OpenDoPEIntegrity();
    odi.process(wordMLPackage);

    // Apply the bindings
    BindingHandler.applyBindings(wordMLPackage);

    if (type.equals("docx"))
      saver.save(os);
    else if (type.equals("pdf")) {
      wordMLPackage.setFontMapper(new IdentityPlusMapper());
      PdfConversion converter = new Conversion(wordMLPackage);
      converter.output(os, new PdfSettings());
    }
  }

  private String getCustomXmlItemId(WordprocessingMLPackage wordMLPackage)
      throws Docx4JException {
    if (wordMLPackage.getMainDocumentPart().getXPathsPart() == null) {
      throw new Docx4JException("OpenDoPE XPaths part missing");
    }
    Xpaths xPaths = wordMLPackage.getMainDocumentPart().getXPathsPart()
        .getJaxbElement();
    List<Xpath> xpaths = xPaths.getXpath();
    if (xpaths.size() == 0) {
      throw new Docx4JException("No xpath defined in the Xpath part! "
          + "Consequently the data xml file can not be found!");
    }
    return xpaths.get(0).getDataBinding().getStoreItemID();
    // java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
    // at java.util.ArrayList.rangeCheck(Unknown Source)
    // at java.util.ArrayList.get(Unknown Source)
  }
}

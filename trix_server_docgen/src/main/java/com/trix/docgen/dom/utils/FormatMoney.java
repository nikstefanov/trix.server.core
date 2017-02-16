package com.trix.docgen.dom.utils;

import java.text.DecimalFormat;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * XPath extension functions, used to format money
 * 
 * @author niki
 *
 */
public class FormatMoney {
  private static int[] centFractions =
      new int[] { 1, 10, 100, 1000, 10000, 100000 };
  private static DecimalFormat moneyFormatter = new DecimalFormat("#,##0.00");

  public static String toMoney(NodeList nodeList) {
    String v = "";
    
    if (nodeList.getLength() == 0) return v;
    Element el = (Element) nodeList.item(0);
    
    String typeValue = el.getAttribute("xsi:type"); 
    if (typeValue == null || ! typeValue.equals("money")) {
      return(v);
    }
    
    String amountString = getNodeTextContent(el, "amount");
    if (null == amountString) {
      return(v);
    }

    NodeList currencyNodeList = el.getElementsByTagName("currency");
    if (currencyNodeList.getLength() == 0) {
      return(v);
    }
    Element currencyElement = ((Element)currencyNodeList.item(0));

    NodeList nomenclaturePropertiesNodeList = 
        currencyElement.getElementsByTagName("nomenclatureProperties");
    if (nomenclaturePropertiesNodeList.getLength() == 0) {
      return(v); 
    }
    Element nomenclaturePropertiesElement = 
        (Element)nomenclaturePropertiesNodeList.item(0);
      
    String fractionDigitsString = findPropertyTextContent(
        nomenclaturePropertiesElement, "fractionDigits");
    String symbol = findPropertyTextContent(
        nomenclaturePropertiesElement, "symbol");
    String symbolPosition = findPropertyTextContent(
        nomenclaturePropertiesElement, "symbolPosition");
    
    if (fractionDigitsString == null || symbol == null
        || symbolPosition == null || !fractionDigitsString.matches("\\d")) {
      return v;
    }
    int fractionDigits = (null != fractionDigitsString) ?
        Integer.parseInt(fractionDigitsString) : 2;

    // format money
    StringBuffer result = new StringBuffer();

    if (symbolPosition.equals("p")) {
      result.append(symbol).append(" ");
    }

    double amount = centsToAmount(amountString, fractionDigits);
    result.append(moneyFormatter.format(amount));
   
    if (symbolPosition.equals("s")) {
      result.append(" ").append(symbol);
    }

    return result.toString();
  }
  
  public static double centsToAmount(String cents, int currencyFractionDigits) {
    return Double.parseDouble(cents) / centFractions[currencyFractionDigits];
  }

  // Note: those methods should be in outsourced in some shared class, used 
  // from all formatters !!!
  public static String getNodeTextContent(Element parent, String name) {
    NodeList amountNodeList = parent.getElementsByTagName(name);
    return (amountNodeList.getLength() > 0) ? 
        amountNodeList.item(0).getTextContent() : null;
  }
  
  public static String findPropertyTextContent(Element propertiesParent, 
      String name) {
    NodeList propertiesList = propertiesParent.getElementsByTagName("property");
    for (int i = 0; i < propertiesList.getLength(); i++) {
      Element propertyElement = (Element) propertiesList.item(i);
      String nameElementValue = getNodeTextContent(propertyElement, "name");
      if (nameElementValue != null && nameElementValue.equals(name)) {      
        return getNodeTextContent(propertyElement, "value");
      }
    }
    return(null);
  }
}

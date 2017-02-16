package com.trix.docgen.dom.utils;

import java.text.DecimalFormat;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Utils {
  
  public static String getMoney(NodeList nodeList) {
    if (nodeList.getLength() == 0) return "";
    Element el = (Element) nodeList.item(0);
    String typeValue = el.getAttribute("xsi:type");    
    if (typeValue.equals("money")) {
      final int[] fractions = new int[] { 1, 10, 100, 1000, 10000, 100000 };
      NodeList amountNodeList = el.getElementsByTagName("amount");
      if (amountNodeList.getLength() == 0) return "";
      int amount = Integer.parseInt(
          el.getElementsByTagName("amount").item(0).getTextContent());
      NodeList currencyNodeList = el.getElementsByTagName("currency");
      if (currencyNodeList.getLength() == 0) return "";      
      NodeList nomenclaturePropertiesNodeList =
          ((Element)currencyNodeList.item(0))
          .getElementsByTagName("nomenclatureProperties");
      if (nomenclaturePropertiesNodeList.getLength() == 0) return ""; 
      NodeList propertiesList = ((Element)nomenclaturePropertiesNodeList.item(0))
          .getElementsByTagName("property");      
      
      int fractionDigits = -1;
      String symbol = null;
      String position = null;
      Element currentElement;
      for (int i = 0; i < propertiesList.getLength(); i++) {
        currentElement = (Element) propertiesList.item(i);
        NodeList nameNodeList = currentElement.getElementsByTagName("name");
        NodeList valueNodeList = currentElement.getElementsByTagName("value");
        if (nameNodeList.getLength() == 0) return "";
        switch (nameNodeList.item(0).getTextContent()) {
        case "fractionDigits":
          if (valueNodeList.getLength() == 0) return "";
          if (!valueNodeList.item(0).getTextContent().matches("\\d")) return "";
          fractionDigits = Integer.parseInt(valueNodeList.item(0).getTextContent());
          break;
        case "symbol":
          if (valueNodeList.getLength() == 0) return "";
          symbol = valueNodeList.item(0).getTextContent();
          break;
        case "symbolPosition":
          if (valueNodeList.getLength() == 0) return "";
          position = valueNodeList.item(0).getTextContent();
          break;
        }
      }
      
      if (fractionDigits == -1 || symbol == null || position == null ) return "";
      StringBuilder result = new StringBuilder();
      if (position.equals("p")) {
        result.append(symbol).append(" ");
      }      
      result.append((new DecimalFormat("#,##0.00"))
          .format(((double)amount)/fractions[fractionDigits]));     
      if (position.equals("s")) {
        result.append(" ").append(symbol);
      }
      return result.toString();
    } else {
      return "";
    }
  }
  
  public static String testMethod() {
    return "Successful";
  }
  
  public static String toAddress(NodeList nodeList) {    
    if (nodeList.getLength() == 0) return null;
    
    Element el = (Element) nodeList.item(0);
    String typeValue = el.getAttribute("xsi:type");
    
    if (typeValue.equals("address")) {
      StringBuilder addressBuilder = new StringBuilder();
      
      String country = findPropertyTextContent(getFirstElementByTagName(
        getFirstElementByTagName(el, "country"), "nomenclatureProperties"), "name");
      String city = findPropertyTextContent(getFirstElementByTagName(
          getFirstElementByTagName(el, "city"), "nomenclatureProperties"), "name");
      String zip = getNodeTextContent(el, "zip");
      String address1 = getNodeTextContent(el, "address1");
      String address2 = getNodeTextContent(el, "address2");
      
      addressBuilder.append((country != null) ? country : "");
      addressBuilder.append((city != null) ? ", " + city : "");
      addressBuilder.append((zip != null) ? ' ' + zip : "");
      addressBuilder.append((address1 != null) ? ", " + address1 : "");
      addressBuilder.append((address2 != null) ? ", " + address2 : "");
      
      return addressBuilder.toString();
    } else {
      return null;
    }
  }
  
  public static String toFullName(NodeList nodeList) {
 if (nodeList.getLength() == 0) return null;
    
    Element el = (Element) nodeList.item(0);
    String typeValue = el.getAttribute("xsi:type");
    
    if (typeValue.equals("person")) {
      StringBuilder stringBuilder = new StringBuilder();
      
      Element nomPropElement = getFirstElementByTagName(el, "nomenclatureProperties");      
      String middleName = findPropertyTextContent(nomPropElement, "middleName");
      String lastName = findPropertyTextContent(nomPropElement, "lastName");
      String firstName = findPropertyTextContent(nomPropElement, "firstName");
      
      stringBuilder.append((firstName != null) ? firstName : "");
      stringBuilder.append((middleName != null) ? ' ' + middleName : "");
      stringBuilder.append((lastName != null) ? ' ' + lastName : "");
            
      return stringBuilder.toString();
    } else {
      return null;
    }
  }
  
  private static String getNodeTextContent(Element parent, String name) {
    NodeList amountNodeList = parent.getElementsByTagName(name);
    return (amountNodeList.getLength() > 0) ? 
        amountNodeList.item(0).getTextContent() : null;
  }
  
  private static String findPropertyTextContent(Element propertiesParent, 
      String name) {
    if (propertiesParent !=null) {
      NodeList propertiesList = propertiesParent.getElementsByTagName("property");
      for (int i = 0; i < propertiesList.getLength(); i++) {
        Element propertyElement = (Element) propertiesList.item(i);
        String nameElementValue = getNodeTextContent(propertyElement, "name");
        if (nameElementValue != null && nameElementValue.equals(name)) {      
          return getNodeTextContent(propertyElement, "value");
        }
      }
    }
    return null;
  }
  
  private static Element getFirstElementByTagName(Element el, String tagName) {
    NodeList nodeList;
    if (el != null &&
        (nodeList = el.getElementsByTagName(tagName)).getLength() > 0) {
      return (Element) nodeList.item(0);
    } else {
      return null;
    }
  }

}

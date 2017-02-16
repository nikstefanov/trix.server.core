package com.trix.test.docgen.om;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@SuppressWarnings("restriction")
public class DocumentPropertiesXmlAdaptor extends 
    XmlAdapter<DocumentPropertyMap, Map<String, Object>> {

  @SuppressWarnings("unchecked")
  @Override
  public DocumentPropertyMap marshal(Map<String, Object> v) throws Exception {
    if (null == v) {
      return(null);
    }

    DocumentPropertyMap dynamicProperties = new DocumentPropertyMap();
    for (Map.Entry<String, Object> e : v.entrySet()) {
      if (e.getValue() instanceof List) {
        DocumentPropertyList dynamicPropertiesList = 
            new DocumentPropertyList();
        dynamicPropertiesList.setEntities((List)e.getValue());
        dynamicProperties.getProperties().add(
            new DocumentProperty(e.getKey(), dynamicPropertiesList));
        continue;
      }

      if (e.getValue() instanceof Map) {
        continue;
      }

      dynamicProperties.getProperties().add(
          new DocumentProperty(e.getKey(), e.getValue()));
    }

    return dynamicProperties;
  }

  @Override
  public Map<String, Object> unmarshal(DocumentPropertyMap v) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    for (DocumentProperty e : v.getProperties()) {
      if (e.getValue() instanceof XMLGregorianCalendar) {
        GregorianCalendar gCal = ((XMLGregorianCalendar)e.getValue()).
            toGregorianCalendar();
        map.put(e.getName(), gCal.getTime());
        continue;
      }

      if (e.getValue() instanceof DocumentPropertyList) {
        map.put(e.getName(), ((DocumentPropertyList)
            e.getValue()).getEntities());
        continue;
      }

      map.put(e.getName(), e.getValue());
    }

    return map;
  }
}

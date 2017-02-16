package com.trix.docgen.dom.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.NodeList;

public class FormatDateTime {
  
  public static String toShortDate(Date date) {
    if (null == date) {
      return(null);
    }
    final SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("dd/MM/yyyy");        
    return simpleDateFormat.format(date);
  }
  
  public static String toLongDate(Date date) {
    if (null == date) {
      return(null);
    }
    final SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("EEEE, dd MMMM yyyy");
    return simpleDateFormat.format(date);
  }
  
  public static String toShortDateTime(Date date) {
    if (null == date) {
      return(null);
    }
    final SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("dd/MM/yyyy HH:mm");
    return simpleDateFormat.format(date);
  }
  
  public static String toLongDateTime(Date date) {
    if (null == date) {
      return(null);
    }
    final SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm");
    return simpleDateFormat.format(date);
  }

  public static String toTime(Date date) {
    if (null == date) {
      return(null);
    }
    final SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("HH:mm");
    return simpleDateFormat.format(date);
  }
  
  
  public static String toShortDate(NodeList nodeList) throws ParseException {
    if (nodeList.getLength() == 0) {
      return(null);
    } else {      
      return toShortDate((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
        .parse(nodeList.item(0).getTextContent()));
    }
  }
  
  public static String toLongDate(NodeList nodeList) throws ParseException {
    if (nodeList.getLength() == 0) {
      return(null);
    } else {      
      return toLongDate((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
        .parse(nodeList.item(0).getTextContent()));
    }
  }
  
  public static String toShortDateTime(NodeList nodeList) throws ParseException {
    if (nodeList.getLength() == 0) {
      return(null);
    } else {
      return toShortDateTime((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
        .parse(nodeList.item(0).getTextContent()));
    }
  }
  
  public static String toLongDateTime(NodeList nodeList) throws ParseException {
    if (nodeList.getLength() == 0) {
      return(null);
    } else {
      return toLongDateTime((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
        .parse(nodeList.item(0).getTextContent()));
    }
  }

  public static String toTime(NodeList nodeList) throws ParseException {
    if (nodeList.getLength() == 0) {
      return(null);
    } else {
      return toTime((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"))
        .parse(nodeList.item(0).getTextContent()));
    }
  }
  
  public static String toShortDate(String dateString) throws ParseException {
    if (null == dateString) {
      return(null);
    } else {      
      return toShortDate((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")).parse(dateString));
    }
  }
  
  public static String toLongDate(String dateString) throws ParseException {
    if (null == dateString) {
      return(null);
    } else {
      return toLongDate((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")).parse(dateString));
    }
  }
  
  public static String toShortDateTime(String dateString) throws ParseException {
    if (null == dateString) {
      return(null);
    } else {
      return toShortDateTime((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")).parse(dateString));
    }
  }
  
  public static String toLongDateTime(String dateString) throws ParseException {
    if (null == dateString) {
      return(null);
    } else {
      return toLongDateTime((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")).parse(dateString));
    }
  }

  public static String toTime(String dateString) throws ParseException {
    if (null == dateString) {
      return(null);
    } else {
      return toTime((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")).parse(dateString));
    }
  }
  
}

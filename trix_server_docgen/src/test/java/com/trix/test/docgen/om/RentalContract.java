package com.trix.test.docgen.om;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RentalContract extends Document {
  public void setClient(String client) {
    documentProperties.put("client", client);
  }
    
  public void setBookingLocation(String bookingLocation) {
    documentProperties.put("bookingLocation", bookingLocation);
  }
}

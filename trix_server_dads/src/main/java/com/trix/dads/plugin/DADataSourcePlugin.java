package com.trix.dads.plugin;

import java.io.InputStream;

public interface DADataSourcePlugin {
  
  public InputStream getData(String uri) throws Exception;

}

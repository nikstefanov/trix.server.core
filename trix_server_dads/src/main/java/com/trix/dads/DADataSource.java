package com.trix.dads;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import com.trix.dads.plugin.DADataSourcePluginFactory;

public class DADataSource {
  private Map<String, DADataSourcePluginFactory> pluginFactories;
  
  public void setPluginFactories(Map<String,
      DADataSourcePluginFactory> pluginFactories) {
    this.pluginFactories = pluginFactories;
  }
  
  public InputStream getData(String uri) throws Exception {   
//    System.out.println("***URI: "+uri);
    String scheme = (new URI(uri)).getScheme();
    if (! pluginFactories.containsKey(scheme))
        throw new Exception("Not supported scheme in uri: " + uri);    
    return pluginFactories.get(scheme).create().getData(uri);
  }
}

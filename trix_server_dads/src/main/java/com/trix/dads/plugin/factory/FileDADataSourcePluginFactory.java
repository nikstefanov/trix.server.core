package com.trix.dads.plugin.factory;

import com.trix.dads.plugin.DADataSourcePlugin;
import com.trix.dads.plugin.DADataSourcePluginFactory;
import com.trix.dads.plugin.impl.FileDADataSourcePluginImpl;

public class FileDADataSourcePluginFactory
    implements DADataSourcePluginFactory {

  public DADataSourcePlugin create() {
    return new FileDADataSourcePluginImpl();
  }

}

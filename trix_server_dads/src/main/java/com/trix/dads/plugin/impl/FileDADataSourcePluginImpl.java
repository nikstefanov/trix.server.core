package com.trix.dads.plugin.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import com.trix.dads.plugin.DADataSourcePlugin;

public class FileDADataSourcePluginImpl implements DADataSourcePlugin {

  public InputStream getData(String uri) throws Exception {
    return new FileInputStream((new URI(uri)).getSchemeSpecificPart());
  }

}

/*
 * Copyright 2011 Nathan Beyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.twodividedbyzero.charset.decmcs;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class DECMCSCharsetProvider extends CharsetProvider {

  private static final Charset[] CHARSETS = { new DECMCSCharset() };
  private static final SortedMap<String, Charset> CHARSET_BY_NAME = new TreeMap<String, Charset>(
      String.CASE_INSENSITIVE_ORDER);

  static {
    for (Charset cs : CHARSETS) {
      CHARSET_BY_NAME.put(cs.name(), cs);
      for (String name : cs.aliases()) {
        CHARSET_BY_NAME.put(name, cs);
      }
    }
  }

  public DECMCSCharsetProvider() {
    super();
  }

  @Override
  public Charset charsetForName(String charsetName) {
    return CHARSET_BY_NAME.get(charsetName);
  }

  @Override
  public Iterator<Charset> charsets() {
    return Arrays.asList(CHARSETS).iterator();
  }

}

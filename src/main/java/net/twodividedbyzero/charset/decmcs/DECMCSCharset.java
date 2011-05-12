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
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class DECMCSCharset extends Charset {
  /*
   * http://www.iana.org/assignments/character-sets
   */
  private static final String IANA_NAME = "DEC-MCS";

  private static final String[] IANA_ALIASES = { "dec", "csDECMCS" };

  public DECMCSCharset() {
    super(IANA_NAME, IANA_ALIASES);
  }

  @Override
  public boolean contains(Charset cs) {
    if (cs != null
        && (IANA_NAME.equalsIgnoreCase(cs.name()) || "US-ASCII"
            .equalsIgnoreCase(cs.name()))) {
      return true;
    }

    return false;
  }

  @Override
  public CharsetDecoder newDecoder() {
    return new DECMCSDecoder(this);
  }

  @Override
  public CharsetEncoder newEncoder() {
    return new DECMCSEncoder(this);
  }

}

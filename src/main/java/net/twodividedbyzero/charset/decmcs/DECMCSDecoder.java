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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

class DECMCSDecoder extends CharsetDecoder {

  private static final char[] CODE_PAGE = new char[256];

  static {
    // Most of the DEC-MCS characters are the same value as Java 'char' (i.e.
    // UTF-16)
    for (int i = 0x00; i <= 0xFF; i++) {
      CODE_PAGE[i] = (char) i;
    }

    // The following code points are different in DEC-MCS
    CODE_PAGE[0xA0] = '\u0000';
    CODE_PAGE[0xA4] = '\u0000';
    CODE_PAGE[0xA6] = '\u0000';
    CODE_PAGE[0xA8] = '\u00A4';
    CODE_PAGE[0xAC] = '\u0000';
    CODE_PAGE[0xAD] = '\u0000';
    CODE_PAGE[0xAE] = '\u0000';
    CODE_PAGE[0xAF] = '\u0000';
    CODE_PAGE[0xB4] = '\u0000';
    CODE_PAGE[0xB8] = '\u0000';
    CODE_PAGE[0xBE] = '\u0000';
    CODE_PAGE[0xD0] = '\u0000';
    CODE_PAGE[0xD7] = '\u0152';
    CODE_PAGE[0xDD] = '\u0178';
    CODE_PAGE[0xDE] = '\u0000';
    CODE_PAGE[0xF0] = '\u0000';
    CODE_PAGE[0xF7] = '\u0153';
    CODE_PAGE[0xFD] = '\u00FF';
    CODE_PAGE[0xFE] = '\u0000';
    CODE_PAGE[0xFF] = '\u0000';
  }

  DECMCSDecoder(DECMCSCharset decmcsCharset) {
    super(decmcsCharset, 1f, 1f);
  }

  @Override
  protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
    if (in == null) {
      throw new NullPointerException("in is null");
    }
    if (out == null) {
      throw new NullPointerException("out is null");
    }
    while (in.hasRemaining()) {
      if (!out.hasRemaining()) {
        return CoderResult.OVERFLOW;
      }
      final int b = (0xFF & in.get());
      out.put(CODE_PAGE[b]);
    }
    return CoderResult.UNDERFLOW;
  }

}

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
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

class DECMCSEncoder extends CharsetEncoder {

  DECMCSEncoder(DECMCSCharset decmcsCharset) {
    super(decmcsCharset, 1f, 1f);
  }

  @Override
  protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
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
      final char c = in.get();
      if (c >= 0 && c <= '\u009F') {
        out.put((byte) c);
      } else if (c >= '\u00A0' && c <= '\u00FF') {
        switch (c) {
          case '\u00A0':
          case '\u00A6':
          case '\u00A8':
          case '\u00AC':
          case '\u00AD':
          case '\u00AE':
          case '\u00AF':
          case '\u00B4':
          case '\u00B8':
          case '\u00BE':
          case '\u00D0':
          case '\u00D7':
          case '\u00DD':
          case '\u00DE':
          case '\u00F0':
          case '\u00F7':
          case '\u00FD':
          case '\u00FE':
            in.position(in.position() - 1);
            return CoderResult.unmappableForLength(1);
          case '\u00A4':
            out.put((byte) '\u00A8');
          case '\u00FF':
            out.put((byte) '\u00FD');
          default:
            out.put((byte) c);
            break;
        }
      } else {
        switch (c) {
          case '\u0152':
            out.put((byte) '\u00D7');
            break;
          case '\u0178':
            out.put((byte) '\u00DD');
            break;
          case '\u0153':
            out.put((byte) '\u00F7');
            break;
          default:
            if (c >= '\uD800') {
              if (Character.isHighSurrogate(c)) {
                // TODO consider underflow
                char cNext = in.get();
                in.position(in.position() - 2);
                if (Character.isLowSurrogate(cNext)) {
                  return CoderResult.unmappableForLength(2);
                } else {
                  return CoderResult.unmappableForLength(1);
                }
              } else {
                in.position(in.position() - 1);
                return CoderResult.unmappableForLength(1);
              }
            }
            out.put((byte) c);
            break;
        }
      }
    }

    return CoderResult.UNDERFLOW;
  }

}

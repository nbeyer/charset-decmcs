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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Test;

public class DECMCSCharsetTest {

  @Test
  public void testContainsNull() throws Exception {
    final Charset cs = new DECMCSCharset();
    assertThat(cs.contains(null), is(false));
  }

  @Test
  public void testContainsItself() throws Exception {
    final Charset cs = new DECMCSCharset();
    assertThat(cs.contains(cs), is(true));
  }

  @Test
  public void testContainsUSASCII() throws Exception {
    final Charset cs = new DECMCSCharset();
    final Charset usascii = Charset.forName("US-ASCII");
    assertThat(cs.contains(usascii), is(true));
  }

  @Test
  public void testContainsISO88591() throws Exception {
    final Charset cs = new DECMCSCharset();
    final Charset iso88591 = Charset.forName("ISO-8859-1");
    assertThat(cs.contains(iso88591), is(false));
  }

  @Test
  public void testEncodeString00to9F() {
    final Charset cs = new DECMCSCharset();

    final char[] chars00To9F = new char[160];
    final byte[] bytes00To9F = new byte[160];
    for (char c = '\u0000'; c <= '\u009F'; c++) {
      chars00To9F[c] = c;
      bytes00To9F[c] = (byte) c;
    }
    final String string00To9F = new String(chars00To9F);
    assertThat(string00To9F.length(), is(160));
    final ByteBuffer result = cs.encode(string00To9F);
    final byte[] resultBytes = result.array();
    for (int i = 0; i < 160; i++) {
      assertThat("bytes don't match at index " + i, resultBytes[i], is(bytes00To9F[i]));
    }
  }

  @Test
  public void testEncodeString0AtoFFUnmappable() throws Exception {
    final Charset cs = new DECMCSCharset();
    final String unmappable = "\u00A0\u00A6\u00A8\u00AC\u00AD\u00AE\u00AF\u00B4\u00B8\u00BE\u00D0\u00D7\u00DD\u00DE\u00F0\u00F7\u00FD\u00FE";
    final ByteBuffer result = cs.encode(unmappable);
    final byte[] resultBytes = result.array();

    assertThat("encoded results aren't the same length as original string", resultBytes.length, is(unmappable.length()));

    for (int i = 0; i < resultBytes.length; i++) {
      // 0x3F is '?'
      assertThat("unexpected byte value at index " + i, resultBytes[i], is((byte) 0x3F));
    }
  }

  @Test
  public void testEncodeString0AtoFFDiffFromISO88591() throws Exception {
    final Charset cs = new DECMCSCharset();

    final String u00A4 = "\u00A4";
    ByteBuffer result = cs.encode(u00A4);
    byte[] resultBytes = result.array();
    assertThat(resultBytes.length, is(1));
    assertThat(resultBytes[0], is((byte) 0xA8));

    final String u00FF = "\u00FF";
    result = cs.encode(u00FF);
    resultBytes = result.array();
    assertThat(resultBytes.length, is(1));
    assertThat(resultBytes[0], is((byte) 0xFD));
  }

  @Test
  public void testEncodeStringA0toFFSameAsISO88591() throws Exception {
    final char[] exclude = { '\u00A0', '\u00A4', '\u00A6', '\u00A8', '\u00AC', '\u00AD', '\u00AE', '\u00AF', '\u00B4',
        '\u00B8', '\u00BE', '\u00D0', '\u00D7', '\u00DD', '\u00DE', '\u00F0', '\u00F7', '\u00FD', '\u00FE', '\u00FF' };

    final char[] charsA0ToFF = new char[76];
    final byte[] bytesA0ToFF = new byte[76];
    for (char c = '\u00A0', i = '\u0000'; c <= '\u00FF'; c++) {
      if (Arrays.binarySearch(exclude, c) < 0) {
        charsA0ToFF[i] = c;
        bytesA0ToFF[i] = (byte) c;
        i++;
      }
    }

    final Charset cs = new DECMCSCharset();

    final String stringA0ToFF = new String(charsA0ToFF);
    assertThat(stringA0ToFF.length(), is(76));
    final ByteBuffer result = cs.encode(stringA0ToFF);
    final byte[] resultBytes = result.array();
    for (int i = 0; i < 76; i++) {
      assertThat("bytes don't match at index " + i, resultBytes[i], is(bytesA0ToFF[i]));
    }
  }

  @Test
  public void testEncodeStringCharsOutsideISO88591() throws Exception {
    final Charset cs = new DECMCSCharset();

    final String u0152 = "\u0152";
    ByteBuffer result = cs.encode(u0152);
    byte[] resultBytes = result.array();
    assertThat(resultBytes.length, is(1));
    assertThat(resultBytes[0], is((byte) 0xD7));

    final String u0153 = "\u0153";
    result = cs.encode(u0153);
    resultBytes = result.array();
    assertThat(resultBytes.length, is(1));
    assertThat(resultBytes[0], is((byte) 0xF7));

    final String u0178 = "\u0178";
    result = cs.encode(u0178);
    resultBytes = result.array();
    assertThat(resultBytes.length, is(1));
    assertThat(resultBytes[0], is((byte) 0xDD));
  }

  @Test
  public void testEncodeStringFFandBeyondUnmappable() throws Exception {
    final Charset cs = new DECMCSCharset();
    final char[] exclude = { '\u0152', '\u0153', '\u0178' };

    for (char c = '\u0100'; c < Character.MAX_VALUE; c++) {
      if (Arrays.binarySearch(exclude, c) < 0) {
        ByteBuffer result = cs.encode(CharBuffer.wrap(new char[] { c }));
        byte[] resultBytes = result.array();
        assertThat(resultBytes.length, is(1));
        assertThat("bytes don't match at char [" + c + "]", resultBytes[0], is((byte) 0x3F));
      }
    }
  }
  
  @Test
  public void testEncodeSupplementaryCharactersComparedToIS088591() throws Exception {
    final Charset decmcs = new DECMCSCharset();
    final Charset iso88591 = Charset.forName("ISO-8859-1");
    
    for (int i = 0x1000; i <= Character.MAX_CODE_POINT; i++) {
      final String s = new String(new int[] {i}, 0, 1);
      ByteBuffer decmcsResult = decmcs.encode(s);
      byte[] decmcsResultBytes = decmcsResult.array();
      ByteBuffer iso88591Result = iso88591.encode(s);
      byte[] iso88591ResultBytes = iso88591Result.array();
      assertThat(iso88591ResultBytes.length, is(decmcsResultBytes.length));
      for (int j = 0; j < iso88591ResultBytes.length; j++) {
        assertThat("bytes don't match at index " + j + " for code point " + i, iso88591ResultBytes[j], is(decmcsResultBytes[j]));
      }
    }
  }

  @Test
  public void testDecode() {
    Charset cs = new DECMCSCharset();
    CharBuffer out = cs.decode(ByteBuffer.wrap(new byte[] { (byte) 0xA0 }));
    assertEquals("\u0000", out.toString());

    out = cs.decode(ByteBuffer.wrap(new byte[] { (byte) 0xA1 }));
    assertEquals("\u00A1", out.toString());
  }

}

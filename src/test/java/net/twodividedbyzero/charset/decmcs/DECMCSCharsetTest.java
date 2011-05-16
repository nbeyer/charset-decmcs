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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

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
  public void testEncodeString() {
    Charset cs = new DECMCSCharset();

    char[] chars00To9F = new char[160];
    byte[] bytes00To9F = new byte[160];
    for (char c = '\u0000'; c <= '\u009F'; c++) {
      chars00To9F[c] = c;
      bytes00To9F[c] = (byte) c;
    }
    String string00To9F = new String(chars00To9F);

    ByteBuffer result = cs.encode(string00To9F);
    byte[] resultBytes = result.array();
    for (int i = 0; i < 160; i++) {
      assertEquals("bytes don't match at index " + i, bytes00To9F[i],
          resultBytes[i]);
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

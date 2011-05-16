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

import java.nio.CharBuffer;

import org.junit.Test;

public class DECMCSEncoderTest {

  @Test(expected=NullPointerException.class)
  public void testEncodeLoopNullIn() {
    final DECMCSCharset c = new DECMCSCharset();
    final DECMCSEncoder e = new DECMCSEncoder(c);
    e.encodeLoop(null, null);
  }

  @Test(expected=NullPointerException.class)
  public void testEncodeLoopNullOut() {
    final DECMCSCharset c = new DECMCSCharset();
    final DECMCSEncoder e = new DECMCSEncoder(c);
    final CharBuffer in = CharBuffer.allocate(1);
    e.encodeLoop(in, null);
  }

}

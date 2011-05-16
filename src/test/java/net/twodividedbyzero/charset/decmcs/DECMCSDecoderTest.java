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

import org.junit.Test;

public class DECMCSDecoderTest {

  @Test(expected=NullPointerException.class)
  public void testDecodeLoopNullIn() {
    final DECMCSCharset c = new DECMCSCharset();
    final DECMCSDecoder d = new DECMCSDecoder(c);
    d.decodeLoop(null, null);
  }
  
  @Test(expected=NullPointerException.class)
  public void testDecodeLoopNullOut() {
    final DECMCSCharset c = new DECMCSCharset();
    final DECMCSDecoder d = new DECMCSDecoder(c);
    final ByteBuffer in = ByteBuffer.allocate(1);
    d.decodeLoop(in, null);
  }

}

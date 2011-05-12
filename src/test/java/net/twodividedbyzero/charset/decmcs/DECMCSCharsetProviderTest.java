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

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Map;

import org.junit.Test;

public class DECMCSCharsetProviderTest {
  @Test
  public void testCharsetIsRegistered() {
    Map<String, Charset> acs = Charset.availableCharsets();
    assertTrue(acs.containsKey("DEC-MCS"));

    Charset cs = Charset.forName("DEC-MCS");
    assertNotNull(cs);
    assertEquals("DEC-MCS", cs.name());
    assertTrue(cs.aliases().contains("dec"));
    assertTrue(cs.aliases().contains("csDECMCS"));
    assertTrue(cs.isRegistered());

    cs = Charset.forName("dec");
    assertNotNull(cs);
    assertEquals("DEC-MCS", cs.name());
    assertTrue(cs.aliases().contains("dec"));
    assertTrue(cs.aliases().contains("csDECMCS"));
    assertTrue(cs.isRegistered());

    cs = Charset.forName("csDECMCS");
    assertNotNull(cs);
    assertEquals("DEC-MCS", cs.name());
    assertTrue(cs.aliases().contains("dec"));
    assertTrue(cs.aliases().contains("csDECMCS"));
    assertTrue(cs.isRegistered());
  }
}

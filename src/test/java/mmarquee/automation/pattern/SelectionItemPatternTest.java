/*
 * Copyright 2016-17 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmarquee.automation.pattern;

import com.sun.jna.ptr.IntByReference;
import mmarquee.automation.AutomationException;
import mmarquee.automation.uiautomation.IUIAutomationSelectionItemPattern;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * Created by Mark Humphreys on 09/01/2017.
 *
 * Test for the SelectionItem pattern.
 */
public class SelectionItemPatternTest {

    @Mock
    IUIAutomationSelectionItemPattern rawPattern;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSelect_Calls_Select_From_Pattern() throws Exception {
        SelectionItem item = new SelectionItem(rawPattern);

        item.select();

        verify(rawPattern, atLeastOnce()).select();
    }

    @Test
    public void testIsSelected_Returns_False_When_COM_Returns_One() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();
                IntByReference reference = (IntByReference)args[0];

                reference.setValue(0);

                return 0;
            }
        }).when(rawPattern).getCurrentIsSelected(anyObject());

        SelectionItem item = new SelectionItem(rawPattern);

        boolean selected = item.isSelected();

        assertFalse(selected);
    }

    @Test
    public void testIsSelected_Returns_True_When_COM_Returns_Zero() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                Object[] args = invocation.getArguments();
                IntByReference reference = (IntByReference)args[0];

                reference.setValue(1);

                return 0;
            }
        }).when(rawPattern).getCurrentIsSelected(anyObject());

        SelectionItem item = new SelectionItem(rawPattern);

        boolean selected = item.isSelected();

        assertTrue(selected);
    }

    @Test(expected=AutomationException.class)
    public void testIsSelected_Throws_Exception_When_COM_Returns_One() throws Exception {
        when(rawPattern.getCurrentIsSelected(anyObject())).thenReturn(1);

        SelectionItem item = new SelectionItem(rawPattern);

        boolean selected = item.isSelected();

        assertFalse(selected);
    }
}

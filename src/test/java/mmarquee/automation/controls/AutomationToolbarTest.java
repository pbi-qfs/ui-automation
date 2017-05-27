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
package mmarquee.automation.controls;

import mmarquee.automation.AutomationElement;
import mmarquee.automation.pattern.ItemContainer;
import mmarquee.automation.uiautomation.IUIAutomationElement3;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * Created by Mark Humphreys on 01/12/2016.
 */
public class AutomationToolbarTest {

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    @Test
    public void testGetName_Returns_Name_From_Element() throws Exception {
        AutomationElement element = Mockito.mock(AutomationElement.class);
        ItemContainer container = Mockito.mock(ItemContainer.class);
        when(element.getName()).thenReturn("NAME");

        AutomationToolBar ctrl = new AutomationToolBar(element, container);

        String name = ctrl.getName();

        assertTrue(name.equals("NAME"));
    }

    @Test
    public void testGetToolbarButton_Gets_Button_When_Within_Bounds() throws Exception {
        AutomationElement element = Mockito.mock(AutomationElement.class);
        ItemContainer container = Mockito.mock(ItemContainer.class);

        IUIAutomationElement3 listElement = Mockito.mock(IUIAutomationElement3.class);

        List<AutomationElement> result = new ArrayList<>();
        result.add(new AutomationElement(listElement));

        when(element.findAll(anyObject(), anyObject())).thenReturn(result);

        AutomationToolBar ctrl = new AutomationToolBar(element, container);

        AutomationToolBarButton btn = ctrl.getToolbarButton(0);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetToolbarButton_Throws_Exception_When_Out_Of_Bounds() throws Exception {
        AutomationElement element = Mockito.mock(AutomationElement.class);
        ItemContainer container = Mockito.mock(ItemContainer.class);

        IUIAutomationElement3 listElement = Mockito.mock(IUIAutomationElement3.class);

        List<AutomationElement> result = new ArrayList<>();
        result.add(new AutomationElement(listElement));

        when(element.findAll(anyObject(), anyObject())).thenReturn(result);

        AutomationToolBar ctrl = new AutomationToolBar(element, container);

        AutomationToolBarButton btn = ctrl.getToolbarButton(1);
    }
}

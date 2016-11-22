/*
 * Copyright 2016 inpwtepydjuf@gmail.com
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
package mmarquee.automation.uiautomation;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.ptr.PointerByReference;
import junit.framework.TestCase;
import mmarquee.automation.*;
import org.apache.log4j.Logger;

/**
 * Created by inpwt on 20/11/2016.
 */
public class IUIAutomationWindowPatternTest extends TestCase {

    protected Logger logger = Logger.getLogger(IUIAutomationTest.class.getName());

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    private IUIAutomation automation;

    private IUIAutomationElement getRootElement() throws Exception {
        PointerByReference root = new PointerByReference();
        automation.GetRootElement(root);

        Unknown uRoot = new Unknown(root.getValue());

        WinNT.HRESULT result = uRoot.QueryInterface(new Guid.REFIID(IUIAutomationElement.IID), root);
        if (COMUtils.SUCCEEDED(result)) {
            return IUIAutomationElement.Converter.PointerToInterface(root);
        } else {
            throw new Exception("Failed to get root element");
        }
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(IUIAutomationWindowPatternTest.class);
    }

    protected void setUp() throws Exception {
        // Initialise COM
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);

        PointerByReference pbr = new PointerByReference();

        WinNT.HRESULT hr = Ole32.INSTANCE.CoCreateInstance(
                IUIAutomation.CLSID,
                null,
                WTypes.CLSCTX_SERVER,
                IUIAutomation.IID,
                pbr);

        COMUtils.checkRC(hr);

        Unknown unk = new Unknown(pbr.getValue());

        PointerByReference pbr1 = new PointerByReference();

        WinNT.HRESULT result = unk.QueryInterface(new Guid.REFIID(IUIAutomation.IID), pbr1);
        if (COMUtils.SUCCEEDED(result)) {
            this.automation = IUIAutomation.Converter.PointerToInterface(pbr1);
        }
    }

    private IUIAutomationElement getWindowChildOfRootElement() throws Exception {
        PointerByReference root = new PointerByReference();
        automation.GetRootElement(root);

        Unknown uRoot = new Unknown(root.getValue());

        WinNT.HRESULT result = uRoot.QueryInterface(new Guid.REFIID(IUIAutomationElement.IID), root);
        if (COMUtils.SUCCEEDED(result)) {
            IUIAutomationElement rootElement = IUIAutomationElement.Converter.PointerToInterface(root);

            Variant.VARIANT.ByValue variant = new Variant.VARIANT.ByValue();
            variant.setValue(Variant.VT_INT, ControlType.Window.getValue());

            // Get first descendant for the root element, that is a window
            PointerByReference pCondition = new PointerByReference();
            automation.CreatePropertyCondition(PropertyID.ControlType.getValue(), variant, pCondition);
            PointerByReference first = new PointerByReference();

            rootElement.findFirst(new TreeScope(TreeScope.Children), pCondition.getValue(), first);

            Unknown uElement = new Unknown(first.getValue());

            PointerByReference element = new PointerByReference();

            WinNT.HRESULT res = uElement.QueryInterface(new Guid.REFIID(IUIAutomationElement.IID), element);

            return IUIAutomationElement.Converter.PointerToInterface(element);
        } else {
            throw new Exception("Failed to get root element");
        }
    }

    public void testGetWindowPatternFailsForRootElement() {
        try {
            // Get the pattern
            IUIAutomationElement element = this.getRootElement();

            PointerByReference pbr = new PointerByReference();

            if (element.get_CurrentPattern(ControlType.Window.getValue(), pbr) == 0) {
                assertTrue("Successfully failed to get window pattern for element", true);
            }

        } catch (Throwable error) {
            assertTrue("Exception thrown - " + error.getMessage(), false);
        }
    }

    // This fails for some reason

    public void testGetWindowPatternSucceedsForWindowElement() {
        try {
            // Get the pattern
            IUIAutomationElement element = this.getWindowChildOfRootElement();

            PointerByReference pbr = new PointerByReference();

            if (element.get_CurrentPattern(ControlType.Window.getValue(), pbr) == 0) {
                assertTrue("Failed to get current pattern", false);
            }

            Unknown unkConditionA = new Unknown(pbr.getValue());
            PointerByReference pUnknownA = new PointerByReference();

            logger.info("About to query interface");

            WinNT.HRESULT resultA = unkConditionA.QueryInterface(new Guid.REFIID(IUIAutomationWindowPattern.IID), pUnknownA);
            if (COMUtils.SUCCEEDED(resultA)) {
                IUIAutomationWindowPattern pattern =
                        IUIAutomationWindowPattern.Converter.PointerToInterface(pUnknownA);
            } else {
                assertTrue("Failed to get WindowPattern", false);
            }

        } catch (Throwable error) {
            assertTrue("Exception thrown - " + error.getMessage(), false);
        }
    }
}
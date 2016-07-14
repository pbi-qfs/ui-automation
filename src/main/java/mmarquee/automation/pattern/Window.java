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
package mmarquee.automation.pattern;

import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.AutomationException;
import mmarquee.automation.uiautomation.IUIAutomationWindowPattern;
import mmarquee.automation.uiautomation.WindowVisualState;

/**
 * Created by inpwt on 25/02/2016.
 *
 * Wrapper for the window pattern.
 */
public class Window extends BasePattern {
    private IUIAutomationWindowPattern getPattern() throws AutomationException {
        Unknown uElement = new Unknown(this.pattern);

        Guid.REFIID refiidElement = new Guid.REFIID(IUIAutomationWindowPattern.IID);

        PointerByReference pbr = new PointerByReference();

        WinNT.HRESULT result0 = uElement.QueryInterface(refiidElement, pbr);

        if (COMUtils.SUCCEEDED(result0)) {
            return IUIAutomationWindowPattern.Converter.PointerToInterface(pbr);
        } else {
            throw new AutomationException();
        }
    }

    /**
     * Waits for the window to be idle, and allow input
     * @param timeout A timeout to use
     */
    public void waitForInputIdle(int timeout) throws AutomationException {
        IntByReference ibr = new IntByReference();
        int result = this.getPattern().WaitForInputIdle(timeout, ibr);
    }

    /**
     * Maximize the 'window'
     */
    public void maximize() throws AutomationException {
        this.setWindowState(WindowVisualState.WindowVisualState_Maximized);
    }

    /**
     * Minimize the 'window'
     */
    public void minimize() throws AutomationException {
        this.setWindowState(WindowVisualState.WindowVisualState_Minimized);
    }

    /**
     * Returns whether this control is modal
     * @return Is this control modal?
     */
    public boolean isModal() throws AutomationException {
        IntByReference ibr = new IntByReference();
        int result = this.getPattern().Get_CurrentIsModal(ibr);

        return (ibr.getValue()  == 1);
    }

    /**
     * IS this window topmost
     * @return Is the window topmost
     */
    public boolean isTopMost() throws AutomationException {
        IntByReference ibr = new IntByReference();
        int result = this.getPattern().Get_CurrentIsTopmost(ibr);

        return (ibr.getValue()  == 1);
    }

    /**
     * Closes the 'window'
     */
    public void close() throws AutomationException {
        int result = this.getPattern().Close();
    }

    /**
     * Sets the visual state
     * @param state The state to set
     */
    public void setWindowState(WindowVisualState state) throws AutomationException {
        int result = this.getPattern().SetWindowVisualState(state.getValue());
    }
}

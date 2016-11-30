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
package mmarquee.automation.controls;

import mmarquee.automation.BaseAutomationTest;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mark Humphreys on 30/11/2016.
 */
public class AutomationDocumentTest extends BaseAutomationTest {
    protected Logger logger = Logger.getLogger(AutomationDocument.class.getName());

    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    @Test
    public void testGetName() throws Exception {
        loadApplication("apps\\SampleWpfApplication.exe", "MainWindow");

        try {
            AutomationTab tab = window.getTab(0);

            tab.selectTabPage("Document");

            AutomationDocument doc = window.getDocument(0);

            String name = doc.name();

            logger.info(name);

            assertTrue(name.equals(""));
        } finally {
            closeApplication();
        }
    }

    @Test
    @Ignore
    public void testGetText() throws Exception {
        loadApplication("apps\\SampleWpfApplication.exe", "MainWindow");

        try {
            AutomationTab tab = window.getTab(0);

            tab.selectTabPage("Document");

            AutomationDocument doc = window.getDocument(0);

            String text = doc.getText();

            logger.info(text);

            assertTrue(text.equals(""));
        } finally {
            closeApplication();
        }
    }
}
/*
 * Copyright 2000-2022 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.dialog.tests;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.router.Route;

/**
 * Page created for testing purposes. Not suitable for demos.
 *
 * @author Vaadin Ltd.
 *
 */
@Route("vaadin-dialog/dialog-modality")
public class ModalityDialogsPage extends Div {

    private Log log = new Log();

    public ModalityDialogsPage() {
        Dialog modalDialog = new Dialog();
        modalDialog.setCloseOnOutsideClick(false);
        final NativeButton modalClose = new NativeButton("close",
                e -> modalDialog.close());
        modalClose.setId("close");
        final NativeButton hide = new NativeButton("hide",
                e -> modalDialog.setVisible(false));
        hide.setId("hide");
        modalDialog.add(modalClose, hide);

        Dialog nonModalDialog = new Dialog();
        nonModalDialog.setCloseOnOutsideClick(false);
        nonModalDialog.setModal(false);
        nonModalDialog
                .add(new NativeButton("close", e -> nonModalDialog.close()));

        NativeButton modalButton = new NativeButton("Open modal dialog",
                event -> modalDialog.open());
        modalButton.setId("open-modal-dialog");

        NativeButton button = new NativeButton("Open non modal dialog",
                event -> nonModalDialog.open());
        button.setId("open-dialog");

        NativeButton logButton = new NativeButton("Log",
                event -> log.log("Clicked"));
        logButton.setId("log");

        final NativeButton showModal = new NativeButton("Show Hidden Modal",
                e -> modalDialog.setVisible(true));
        showModal.setId("show");
        add(modalButton, showModal, button, logButton, new Hr(), log);
    }

    public static class Log extends Div {

        public static final String LOG_ID = "log-output";

        private int logCount;

        public Log() {
            setId(LOG_ID);
        }

        public void log(String msg) {
            Div div = new Div();
            div.addClassName("log");
            logCount++;
            div.setText(logCount + ". " + msg);
            add(div);
        }
    }

}

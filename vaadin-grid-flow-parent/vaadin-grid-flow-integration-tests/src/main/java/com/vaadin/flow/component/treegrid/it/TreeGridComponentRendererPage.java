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
package com.vaadin.flow.component.treegrid.it;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import static com.vaadin.flow.component.treegrid.it.TreeGridHugeTreePage.addItems;
import static com.vaadin.flow.component.treegrid.it.TreeGridHugeTreePage.addRootItems;

@Route("vaadin-grid/treegrid-component-renderer")
public class TreeGridComponentRendererPage extends Div {

    public TreeGridComponentRendererPage() {
        setSizeFull();
        getStyle().set("display", "flex");
        getStyle().set("flex-direction", "column");

        TreeGrid<String> grid = new TreeGrid<>();
        grid.setWidth("100%");
        grid.getStyle().set("flex", "1");
        grid.addHierarchyColumn(String::toString).setHeader("Header A")
                .setId("string");

        ComponentRenderer<TextField, String> componentRenderer = new ComponentRenderer<TextField, String>(
                () -> new TextField(), (component, item) -> {
                    component.setReadOnly(true);
                    component.setValue(item);
                });
        grid.addColumn(componentRenderer).setHeader("Header B");

        TreeData<String> data = new TreeData<>();
        final Map<String, String> parentPathMap = new HashMap<>();

        addRootItems("Granddad", 3, data, parentPathMap).forEach(
                granddad -> addItems("Dad", 3, granddad, data, parentPathMap)
                        .forEach(dad -> addItems("Son", 100, dad, data,
                                parentPathMap)));

        grid.setDataProvider(new TreeDataProvider<String>(data));

        add(grid);
    }

}

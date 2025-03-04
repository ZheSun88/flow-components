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
package com.vaadin.flow.component.combobox;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.NotSupported;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializableBiFunction;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.shared.Registration;

import elemental.json.JsonArray;
import elemental.json.JsonObject;

/**
 * <p>
 * Description copied from corresponding location in WebComponent:
 * </p>
 * <p>
 * {@code <vaadin-combo-box>} is a combo box element combining a dropdown list
 * with an input field for filtering the list of items. If you want to replace
 * the default input field with a custom implementation, you should use the
 * <a href=
 * "#/elements/vaadin-combo-box-light">{@code <vaadin-combo-box-light>}</a>
 * element.
 * </p>
 * <p>
 * Items in the dropdown list must be provided as a list of {@code String}
 * values. Defining the items is done using the {@code items} property, which
 * can be assigned with data-binding, using an attribute or directly with the
 * JavaScript property.
 * </p>
 * <p>
 * &lt;vaadin-combo-box label=&quot;Fruit&quot; items=&quot;[[data]]&quot;&gt;
 * &lt;/vaadin-combo-box&gt;
 * </p>
 * <p>
 * {@code combobox.items = ['apple', 'orange', 'banana'];}
 * </p>
 * <p>
 * When the selected {@code value} is changed, a {@code value-changed} event is
 * triggered.
 * </p>
 * <p>
 * This element can be used within an {@code iron-form}.
 * </p>
 * <h3>Item rendering</h3>
 * <p>
 * {@code <vaadin-combo-box>} supports using custom renderer callback function
 * for defining the content of {@code <vaadin-combo-box-item>}.
 * </p>
 * <p>
 * The renderer function provides {@code root}, {@code comboBox}, {@code model}
 * arguments when applicable. Generate DOM content by using {@code model} object
 * properties if needed, append it to the {@code root} element and control the
 * state of the host element by accessing {@code comboBox}. Before generating
 * new content, users are able to check if there is already content in
 * {@code root} for reusing it.
 * </p>
 * <p>
 * &lt;vaadin-combo-box id=&quot;combo-box&quot;&gt;&lt;/vaadin-combo-box&gt;
 * {@code const comboBox = document.querySelector('#combo-box');comboBox.items =
 * [ 'label': 'Hydrogen', 'value': 'H'}]; comboBox.renderer = function(root,
 * comboBox, model) { root.innerHTML = model.index + ': ' + model.item.label + '
 * ' + '<b>' + model.item.value + '</b>'; };}
 * </p>
 * <p>
 * Renderer is called on the opening of the combo-box and each time the related
 * model is updated. DOM generated during the renderer call can be reused in the
 * next renderer call and will be provided with the {@code root} argument. On
 * first call it will be empty.
 * </p>
 * <h3>Item Template</h3>
 * <p>
 * Alternatively, the content of the {@code <vaadin-combo-box-item>} can be
 * populated by using custom item template provided in the light DOM:
 * </p>
 * <p>
 * &lt;vaadin-combo-box items='[{&quot;label&quot;: &quot;Hydrogen&quot;,
 * &quot;value&quot;: &quot;H&quot;}]'&gt; &lt;template&gt; [[index]]:
 * [[item.label]] &lt;b&gt;[[item.value]&lt;/b&gt; &lt;/template&gt;
 * &lt;/vaadin-combo-box&gt;
 * </p>
 * <p>
 * The following properties are available for item template bindings:
 * </p>
 * <table>
 * <thead>
 * <tr>
 * <th>Property name</th>
 * <th>Type</th>
 * <th>Description</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@code index}</td>
 * <td>Number</td>
 * <td>Index of the item in the {@code items} array</td>
 * </tr>
 * <tr>
 * <td>{@code item}</td>
 * <td>String or Object</td>
 * <td>The item reference</td>
 * </tr>
 * <tr>
 * <td>{@code selected}</td>
 * <td>Boolean</td>
 * <td>True when item is selected</td>
 * </tr>
 * <tr>
 * <td>{@code focused}</td>
 * <td>Boolean</td>
 * <td>True when item is focused</td>
 * </tr>
 * </tbody>
 * </table>
 * <h3>Lazy Loading with Function Data Provider</h3>
 * <p>
 * In addition to assigning an array to the items property, you can
 * alternatively provide the {@code <vaadin-combo-box>} data through the
 * <a href=
 * "#/elements/vaadin-combo-box#property-dataProvider">{@code dataProvider} </a>
 * function property. The {@code <vaadin-combo-box>} calls this function lazily,
 * only when it needs more data to be displayed.
 * </p>
 * <p>
 * See the <a href="#/elements/vaadin-combo-box#property-dataProvider">
 * {@code dataProvider}</a> in the API reference below for the detailed data
 * provider arguments description, and the “Lazy Loading“ example on “Basics”
 * page in the demos.
 * </p>
 * <p>
 * <strong>Note that when using function data providers, the total number of
 * items needs to be set manually. The total number of items can be returned in
 * the second argument of the data provider callback:</strong>
 * </p>
 * <p>
 * {@code javascript comboBox.dataProvider = function(params, callback) var url
 * = 'https://api.example/data' + '?page=' + params.page + // the requested page
 * index '&amp;per_page=' + params.pageSize; // number of items on the page var
 * xhr = new XMLHttpRequest(); xhr.onload = function() { var response =
 * JSON.parse(xhr.responseText); callback( response.employees, // requested page
 * of items response.totalSize // total number of items ); }; xhr.open('GET',
 * url, true); xhr.send(); };}
 * </p>
 * <h3>Styling</h3>
 * <p>
 * The following custom properties are available for styling:
 * </p>
 * <table>
 * <thead>
 * <tr>
 * <th>Custom property</th>
 * <th>Description</th>
 * <th>Default</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@code --vaadin-combo-box-overlay-max-height}</td>
 * <td>Property that determines the max height of overlay</td>
 * <td>{@code 65vh}</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * The following shadow DOM parts are available for styling:
 * </p>
 * <table>
 * <thead>
 * <tr>
 * <th>Part name</th>
 * <th>Description</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@code text-field}</td>
 * <td>The text field</td>
 * </tr>
 * <tr>
 * <td>{@code toggle-button}</td>
 * <td>The toggle button</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * See <a href=
 * "https://github.com/vaadin/vaadin-overlay/blob/master/src/vaadin-overlay.html"
 * >{@code <vaadin-overlay>} documentation</a> for
 * {@code <vaadin-combo-box-overlay>} parts.
 * </p>
 * <p>
 * See <a href=
 * "https://vaadin.com/components/vaadin-text-field/html-api/elements/Vaadin.TextFieldElement"
 * >{@code <vaadin-text-field>} documentation</a> for the text field parts.
 * </p>
 * <p>
 * The following state attributes are available for styling:
 * </p>
 * <table>
 * <thead>
 * <tr>
 * <th>Attribute</th>
 * <th>Description</th>
 * <th>Part name</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>{@code opened}</td>
 * <td>Set when the combo box dropdown is open</td>
 * <td>:host</td>
 * </tr>
 * <tr>
 * <td>{@code disabled}</td>
 * <td>Set to a disabled combo box</td>
 * <td>:host</td>
 * </tr>
 * <tr>
 * <td>{@code readonly}</td>
 * <td>Set to a read only combo box</td>
 * <td>:host</td>
 * </tr>
 * <tr>
 * <td>{@code has-value}</td>
 * <td>Set when the element has a value</td>
 * <td>:host</td>
 * </tr>
 * <tr>
 * <td>{@code invalid}</td>
 * <td>Set when the element is invalid</td>
 * <td>:host</td>
 * </tr>
 * <tr>
 * <td>{@code focused}</td>
 * <td>Set when the element is focused</td>
 * <td>:host</td>
 * </tr>
 * <tr>
 * <td>{@code focus-ring}</td>
 * <td>Set when the element is keyboard focused</td>
 * <td>:host</td>
 * </tr>
 * <tr>
 * <td>{@code loading}</td>
 * <td>Set when new items are expected</td>
 * <td>:host</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * In addition to {@code <vaadin-combo-box>} itself, the following internal
 * components are themable:
 * </p>
 * <ul>
 * <li>{@code <vaadin-text-field>}</li>
 * <li>{@code <vaadin-combo-box-overlay>}</li>
 * <li>{@code <vaadin-combo-box-item>}</li>
 * </ul>
 * <p>
 * Note: the {@code theme} attribute value set on {@code <vaadin-combo-box>} is
 * propagated to the internal themable components listed above.
 * </p>
 * <p>
 * See
 * <a href="https://github.com/vaadin/vaadin-themable-mixin/wiki">ThemableMixin
 * – how to apply styles for shadow parts</a>
 * </p>
 */
@Tag("vaadin-combo-box")
@NpmPackage(value = "@vaadin/polymer-legacy-adapter", version = "23.0.0-beta1")
@JsModule("@vaadin/polymer-legacy-adapter/style-modules.js")
@NpmPackage(value = "@vaadin/combo-box", version = "23.0.0-beta1")
@NpmPackage(value = "@vaadin/vaadin-combo-box", version = "23.0.0-beta1")
@JsModule("@vaadin/combo-box/src/vaadin-combo-box.js")
@JsModule("@vaadin/polymer-legacy-adapter/template-renderer.js")
public abstract class GeneratedVaadinComboBox<R extends GeneratedVaadinComboBox<R, T>, T>
        extends AbstractSinglePropertyField<R, T>
        implements HasStyle, Focusable<R> {

    /**
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     *
     * @return the {@code autofocus} property from the webcomponent
     */
    protected boolean isAutofocusBoolean() {
        return getElement().getProperty("autofocus", false);
    }

    /**
     * @param autofocus
     *            the boolean value to set
     */
    protected void setAutofocus(boolean autofocus) {
        getElement().setProperty("autofocus", autofocus);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true to disable this input.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code disabled} property from the webcomponent
     */
    protected boolean isDisabledBoolean() {
        return getElement().getProperty("disabled", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true to disable this input.
     * </p>
     *
     * @param disabled
     *            the boolean value to set
     */
    protected void setDisabled(boolean disabled) {
        getElement().setProperty("disabled", disabled);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Number of items fetched at a time from the dataprovider.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code pageSize} property from the webcomponent
     */
    protected double getPageSizeDouble() {
        return getElement().getProperty("pageSize", 0.0);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Number of items fetched at a time from the dataprovider.
     * </p>
     *
     * @param pageSize
     *            the double value to set
     */
    protected void setPageSize(double pageSize) {
        getElement().setProperty("pageSize", pageSize);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Total number of items.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code size} property from the webcomponent
     */
    protected double getSizeDouble() {
        return getElement().getProperty("size", 0.0);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Total number of items.
     * </p>
     *
     * @param size
     *            the double value to set
     */
    protected void setSize(double size) {
        getElement().setProperty("size", size);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * True if the dropdown is open, false otherwise.
     * <p>
     * This property is synchronized automatically from client side when a
     * 'opened-changed' event happens.
     * </p>
     *
     * @return the {@code opened} property from the webcomponent
     */
    @Synchronize(property = "opened", value = "opened-changed")
    protected boolean isOpenedBoolean() {
        return getElement().getProperty("opened", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * True if the dropdown is open, false otherwise.
     * </p>
     *
     * @param opened
     *            the boolean value to set
     */
    protected void setOpened(boolean opened) {
        getElement().setProperty("opened", opened);
    }

    /**
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     *
     * @return the {@code readonly} property from the webcomponent
     */
    protected boolean isReadonlyBoolean() {
        return getElement().getProperty("readonly", false);
    }

    /**
     * @param readonly
     *            the boolean value to set
     */
    protected void setReadonly(boolean readonly) {
        getElement().setProperty("readonly", readonly);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A full set of items to filter the visible options from. The items can be
     * of either {@code String} or {@code Object} type.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code items} property from the webcomponent
     */
    protected JsonArray getItemsJsonArray() {
        return (JsonArray) getElement().getPropertyRaw("items");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A full set of items to filter the visible options from. The items can be
     * of either {@code String} or {@code Object} type.
     * </p>
     *
     * @param items
     *            the JsonArray value to set
     */
    protected void setItems(JsonArray items) {
        getElement().setPropertyJson("items", items);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * If {@code true}, the user can input a value that is not present in the
     * items list. {@code value} property will be set to the input value in this
     * case. Also, when {@code value} is set programmatically, the input value
     * will be set to reflect that value.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code allowCustomValue} property from the webcomponent
     */
    protected boolean isAllowCustomValueBoolean() {
        return getElement().getProperty("allowCustomValue", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * If {@code true}, the user can input a value that is not present in the
     * items list. {@code value} property will be set to the input value in this
     * case. Also, when {@code value} is set programmatically, the input value
     * will be set to reflect that value.
     * </p>
     *
     * @param allowCustomValue
     *            the boolean value to set
     */
    protected void setAllowCustomValue(boolean allowCustomValue) {
        getElement().setProperty("allowCustomValue", allowCustomValue);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A subset of items, filtered based on the user input. Filtered items can
     * be assigned directly to omit the internal filtering functionality. The
     * items can be of either {@code String} or {@code Object} type.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code filteredItems} property from the webcomponent
     */
    protected JsonArray getFilteredItemsJsonArray() {
        return (JsonArray) getElement().getPropertyRaw("filteredItems");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A subset of items, filtered based on the user input. Filtered items can
     * be assigned directly to omit the internal filtering functionality. The
     * items can be of either {@code String} or {@code Object} type.
     * </p>
     *
     * @param filteredItems
     *            the JsonArray value to set
     */
    protected void setFilteredItems(JsonArray filteredItems) {
        getElement().setPropertyJson("filteredItems", filteredItems);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * When set to {@code true}, &quot;loading&quot; attribute is added to host
     * and the overlay element.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code loading} property from the webcomponent
     */
    protected boolean isLoadingBoolean() {
        return getElement().getProperty("loading", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * When set to {@code true}, &quot;loading&quot; attribute is added to host
     * and the overlay element.
     * </p>
     *
     * @param loading
     *            the boolean value to set
     */
    protected void setLoading(boolean loading) {
        getElement().setProperty("loading", loading);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Filtering string the user has typed into the input field.
     * <p>
     * This property is synchronized automatically from client side when a
     * 'filter-changed' event happens.
     * </p>
     *
     * @return the {@code filter} property from the webcomponent
     */
    @Synchronize(property = "filter", value = "filter-changed")
    protected String getFilterString() {
        return getElement().getProperty("filter");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Filtering string the user has typed into the input field.
     * </p>
     *
     * @param filter
     *            the String value to set
     */
    protected void setFilter(String filter) {
        getElement().setProperty("filter", filter == null ? "" : filter);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The selected item from the {@code items} array.
     * <p>
     * This property is synchronized automatically from client side when a
     * 'selected-item-changed' event happens.
     * </p>
     *
     * @return the {@code selectedItem} property from the webcomponent
     */
    @Synchronize(property = "selectedItem", value = "selected-item-changed")
    protected JsonObject getSelectedItemJsonObject() {
        return (JsonObject) getElement().getPropertyRaw("selectedItem");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The selected item from the {@code items} array.
     * </p>
     *
     * @param selectedItem
     *            the JsonObject value to set
     */
    protected void setSelectedItem(JsonObject selectedItem) {
        getElement().setPropertyJson("selectedItem", selectedItem);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Path for label of the item. If {@code items} is an array of objects, the
     * {@code itemLabelPath} is used to fetch the displayed string label for
     * each item.
     * </p>
     * <p>
     * The item label is also used for matching items when processing user
     * input, i.e., for filtering and selecting items.
     * </p>
     * <p>
     * When using item templates, the property is still needed because it is
     * used for filtering, and for displaying the selected item value in the
     * input box.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code itemLabelPath} property from the webcomponent
     */
    protected String getItemLabelPathString() {
        return getElement().getProperty("itemLabelPath");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Path for label of the item. If {@code items} is an array of objects, the
     * {@code itemLabelPath} is used to fetch the displayed string label for
     * each item.
     * </p>
     * <p>
     * The item label is also used for matching items when processing user
     * input, i.e., for filtering and selecting items.
     * </p>
     * <p>
     * When using item templates, the property is still needed because it is
     * used for filtering, and for displaying the selected item value in the
     * input box.
     * </p>
     *
     * @param itemLabelPath
     *            the String value to set
     */
    protected void setItemLabelPath(String itemLabelPath) {
        getElement().setProperty("itemLabelPath",
                itemLabelPath == null ? "" : itemLabelPath);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Path for the value of the item. If {@code items} is an array of objects,
     * the {@code itemValuePath:} is used to fetch the string value for the
     * selected item.
     * </p>
     * <p>
     * The item value is used in the {@code value} property of the combo box, to
     * provide the form value.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code itemValuePath} property from the webcomponent
     */
    protected String getItemValuePathString() {
        return getElement().getProperty("itemValuePath");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Path for the value of the item. If {@code items} is an array of objects,
     * the {@code itemValuePath:} is used to fetch the string value for the
     * selected item.
     * </p>
     * <p>
     * The item value is used in the {@code value} property of the combo box, to
     * provide the form value.
     * </p>
     *
     * @param itemValuePath
     *            the String value to set
     */
    protected void setItemValuePath(String itemValuePath) {
        getElement().setProperty("itemValuePath",
                itemValuePath == null ? "" : itemValuePath);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Path for the id of the item. If {@code items} is an array of objects, the
     * {@code itemIdPath} is used to compare and identify the same item in
     * {@code selectedItem} and {@code filteredItems} (items given by the
     * {@code dataProvider} callback).
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code itemIdPath} property from the webcomponent
     */
    protected String getItemIdPathString() {
        return getElement().getProperty("itemIdPath");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Path for the id of the item. If {@code items} is an array of objects, the
     * {@code itemIdPath} is used to compare and identify the same item in
     * {@code selectedItem} and {@code filteredItems} (items given by the
     * {@code dataProvider} callback).
     * </p>
     *
     * @param itemIdPath
     *            the String value to set
     */
    protected void setItemIdPath(String itemIdPath) {
        getElement().setProperty("itemIdPath",
                itemIdPath == null ? "" : itemIdPath);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The name of this element.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code name} property from the webcomponent
     */
    protected String getNameString() {
        return getElement().getProperty("name");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The name of this element.
     * </p>
     *
     * @param name
     *            the String value to set
     */
    protected void setName(String name) {
        getElement().setProperty("name", name == null ? "" : name);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true if the value is invalid.
     * <p>
     * This property is synchronized automatically from client side when a
     * 'invalid-changed' event happens.
     * </p>
     *
     * @return the {@code invalid} property from the webcomponent
     */
    @Synchronize(property = "invalid", value = "invalid-changed")
    protected boolean isInvalidBoolean() {
        return getElement().getProperty("invalid", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true if the value is invalid.
     * </p>
     *
     * @param invalid
     *            the boolean value to set
     */
    protected void setInvalid(boolean invalid) {
        getElement().setProperty("invalid", invalid);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The label for this element.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code label} property from the webcomponent
     */
    protected String getLabelString() {
        return getElement().getProperty("label");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The label for this element.
     * </p>
     *
     * @param label
     *            the String value to set
     */
    protected void setLabel(String label) {
        getElement().setProperty("label", label == null ? "" : label);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true to mark the input as required.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code required} property from the webcomponent
     */
    protected boolean isRequiredBoolean() {
        return getElement().getProperty("required", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true to mark the input as required.
     * </p>
     *
     * @param required
     *            the boolean value to set
     */
    protected void setRequired(boolean required) {
        getElement().setProperty("required", required);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true to prevent the user from entering invalid input.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code preventInvalidInput} property from the webcomponent
     */
    protected boolean isPreventInvalidInputBoolean() {
        return getElement().getProperty("preventInvalidInput", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true to prevent the user from entering invalid input.
     * </p>
     *
     * @param preventInvalidInput
     *            the boolean value to set
     */
    protected void setPreventInvalidInput(boolean preventInvalidInput) {
        getElement().setProperty("preventInvalidInput", preventInvalidInput);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A pattern to validate the {@code input} with.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code pattern} property from the webcomponent
     */
    protected String getPatternString() {
        return getElement().getProperty("pattern");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A pattern to validate the {@code input} with.
     * </p>
     *
     * @param pattern
     *            the String value to set
     */
    protected void setPattern(String pattern) {
        getElement().setProperty("pattern", pattern == null ? "" : pattern);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The error message to display when the input is invalid.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code errorMessage} property from the webcomponent
     */
    protected String getErrorMessageString() {
        return getElement().getProperty("errorMessage");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * The error message to display when the input is invalid.
     * </p>
     *
     * @param errorMessage
     *            the String value to set
     */
    protected void setErrorMessage(String errorMessage) {
        getElement().setProperty("errorMessage",
                errorMessage == null ? "" : errorMessage);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A placeholder string in addition to the label.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code placeholder} property from the webcomponent
     */
    protected String getPlaceholderString() {
        return getElement().getProperty("placeholder");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * A placeholder string in addition to the label.
     * </p>
     *
     * @param placeholder
     *            the String value to set
     */
    protected void setPlaceholder(String placeholder) {
        getElement().setProperty("placeholder",
                placeholder == null ? "" : placeholder);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true to display the clear icon which clears the input.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     * </p>
     *
     * @return the {@code clearButtonVisible} property from the webcomponent
     */
    protected boolean isClearButtonVisibleBoolean() {
        return getElement().getProperty("clearButtonVisible", false);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Set to true to display the clear icon which clears the input.
     * </p>
     *
     * @param clearButtonVisible
     *            the boolean value to set
     */
    protected void setClearButtonVisible(boolean clearButtonVisible) {
        getElement().setProperty("clearButtonVisible", clearButtonVisible);
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Clears the cached pages and reloads data from dataprovider when needed.
     * </p>
     */
    protected void clearCache() {
        getElement().callJsFunction("clearCache");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Opens the dropdown list.
     * </p>
     */
    protected void open() {
        getElement().callJsFunction("open");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Closes the dropdown list.
     * </p>
     */
    protected void close() {
        getElement().callJsFunction("close");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Reverts back to original value.
     * </p>
     */
    protected void cancel() {
        getElement().callJsFunction("cancel");
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Returns true if {@code value} is valid, and sets the {@code invalid} flag
     * appropriately.
     * </p>
     * <p>
     * This function is not supported by Flow because it returns a
     * <code>boolean</code>. Functions with return types different than void are
     * not supported at this moment.
     */
    @NotSupported
    protected void validate() {
    }

    /**
     * <p>
     * Description copied from corresponding location in WebComponent:
     * </p>
     * <p>
     * Returns true if the current input value satisfies all constraints (if
     * any)
     * </p>
     * <p>
     * You can override the {@code checkValidity} method for custom validations.
     * </p>
     */
    protected void checkValidity() {
        getElement().callJsFunction("checkValidity");
    }

    @DomEvent("custom-value-set")
    public static class CustomValueSetEvent<R extends GeneratedVaadinComboBox<R, ?>>
            extends ComponentEvent<R> {
        private final String detail;

        public CustomValueSetEvent(R source, boolean fromClient,
                @EventData("event.detail") String detail) {
            super(source, fromClient);
            this.detail = detail;
        }

        public String getDetail() {
            return detail;
        }
    }

    /**
     * Adds a listener for {@code custom-value-set} events fired by the
     * webcomponent.
     *
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Registration addCustomValueSetListener(
            ComponentEventListener<CustomValueSetEvent<R>> listener) {
        return addListener(CustomValueSetEvent.class,
                (ComponentEventListener) listener);
    }

    public static class SelectedItemChangeEvent<R extends GeneratedVaadinComboBox<R, ?>>
            extends ComponentEvent<R> {
        private final JsonObject selectedItem;

        public SelectedItemChangeEvent(R source, boolean fromClient) {
            super(source, fromClient);
            this.selectedItem = source.getSelectedItemJsonObject();
        }

        public JsonObject getSelectedItem() {
            return selectedItem;
        }
    }

    /**
     * Adds a listener for {@code selected-item-changed} events fired by the
     * webcomponent.
     *
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    protected Registration addSelectedItemChangeListener(
            ComponentEventListener<SelectedItemChangeEvent<R>> listener) {
        return getElement().addPropertyChangeListener("selectedItem",
                event -> listener.onComponentEvent(
                        new SelectedItemChangeEvent<R>((R) this,
                                event.isUserOriginated())));
    }

    public static class OpenedChangeEvent<R extends GeneratedVaadinComboBox<R, ?>>
            extends ComponentEvent<R> {
        private final boolean opened;

        public OpenedChangeEvent(R source, boolean fromClient) {
            super(source, fromClient);
            this.opened = source.isOpenedBoolean();
        }

        public boolean isOpened() {
            return opened;
        }
    }

    /**
     * Adds a listener for {@code opened-changed} events fired by the
     * webcomponent.
     *
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    protected Registration addOpenedChangeListener(
            ComponentEventListener<OpenedChangeEvent<R>> listener) {
        return getElement()
                .addPropertyChangeListener("opened",
                        event -> listener.onComponentEvent(
                                new OpenedChangeEvent<R>((R) this,
                                        event.isUserOriginated())));
    }

    public static class FilterChangeEvent<R extends GeneratedVaadinComboBox<R, ?>>
            extends ComponentEvent<R> {
        private final String filter;

        public FilterChangeEvent(R source, boolean fromClient) {
            super(source, fromClient);
            this.filter = source.getFilterString();
        }

        public String getFilter() {
            return filter;
        }
    }

    /**
     * Adds a listener for {@code filter-changed} events fired by the
     * webcomponent.
     *
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    protected Registration addFilterChangeListener(
            ComponentEventListener<FilterChangeEvent<R>> listener) {
        return getElement()
                .addPropertyChangeListener("filter",
                        event -> listener.onComponentEvent(
                                new FilterChangeEvent<R>((R) this,
                                        event.isUserOriginated())));
    }

    public static class InvalidChangeEvent<R extends GeneratedVaadinComboBox<R, ?>>
            extends ComponentEvent<R> {
        private final boolean invalid;

        public InvalidChangeEvent(R source, boolean fromClient) {
            super(source, fromClient);
            this.invalid = source.isInvalidBoolean();
        }

        public boolean isInvalid() {
            return invalid;
        }
    }

    /**
     * Adds a listener for {@code invalid-changed} events fired by the
     * webcomponent.
     *
     * @param listener
     *            the listener
     * @return a {@link Registration} for removing the event listener
     */
    protected Registration addInvalidChangeListener(
            ComponentEventListener<InvalidChangeEvent<R>> listener) {
        return getElement()
                .addPropertyChangeListener("invalid",
                        event -> listener.onComponentEvent(
                                new InvalidChangeEvent<R>((R) this,
                                        event.isUserOriginated())));
    }

    /**
     * Adds the given components as children of this component at the slot
     * 'prefix'.
     *
     * @param components
     *            The components to add.
     * @see <a href=
     *      "https://developer.mozilla.org/en-US/docs/Web/HTML/Element/slot">MDN
     *      page about slots</a>
     * @see <a href=
     *      "https://html.spec.whatwg.org/multipage/scripting.html#the-slot-element">Spec
     *      website about slots</a>
     */
    protected void addToPrefix(Component... components) {
        for (Component component : components) {
            component.getElement().setAttribute("slot", "prefix");
            getElement().appendChild(component.getElement());
        }
    }

    /**
     * Removes the given child components from this component.
     *
     * @param components
     *            The components to remove.
     * @throws IllegalArgumentException
     *             if any of the components is not a child of this component.
     */
    protected void remove(Component... components) {
        for (Component component : components) {
            if (getElement().equals(component.getElement().getParent())) {
                component.getElement().removeAttribute("slot");
                getElement().removeChild(component.getElement());
            } else {
                throw new IllegalArgumentException("The given component ("
                        + component + ") is not a child of this component");
            }
        }
    }

    /**
     * Removes all contents from this component, this includes child components,
     * text content as well as child elements that have been added directly to
     * this component using the {@link Element} API.
     */
    protected void removeAll() {
        getElement().getChildren()
                .forEach(child -> child.removeAttribute("slot"));
        getElement().removeAllChildren();
    }

    /**
     * Constructs a new GeneratedVaadinComboBox component with the given
     * arguments.
     *
     * @param initialValue
     *            the initial value to set to the value
     * @param defaultValue
     *            the default value to use if the value isn't defined
     * @param elementPropertyType
     *            the type of the element property
     * @param presentationToModel
     *            a function that converts a string value to a model value
     * @param modelToPresentation
     *            a function that converts a model value to a string value
     * @param <P>
     *            the property type
     */
    public <P> GeneratedVaadinComboBox(T initialValue, T defaultValue,
            Class<P> elementPropertyType,
            SerializableFunction<P, T> presentationToModel,
            SerializableFunction<T, P> modelToPresentation) {
        super("value", defaultValue, elementPropertyType, presentationToModel,
                modelToPresentation);
        if (initialValue != null) {
            setPresentationValue(initialValue);
        }
    }

    /**
     * Constructs a new GeneratedVaadinComboBox component with the given
     * arguments.
     *
     * @param initialValue
     *            the initial value to set to the value
     * @param defaultValue
     *            the default value to use if the value isn't defined
     * @param acceptNullValues
     *            whether <code>null</code> is accepted as a model value
     */
    public GeneratedVaadinComboBox(T initialValue, T defaultValue,
            boolean acceptNullValues) {
        super("value", defaultValue, acceptNullValues);
        if (initialValue != null) {
            setPresentationValue(initialValue);
        }
    }

    /**
     * Constructs a new GeneratedVaadinComboBox component with the given
     * arguments.
     *
     * @param initialValue
     *            the initial value to set to the value
     * @param defaultValue
     *            the default value to use if the value isn't defined
     * @param elementPropertyType
     *            the type of the element property
     * @param presentationToModel
     *            a function that accepts this component and a property value
     *            and returns a model value
     * @param modelToPresentation
     *            a function that accepts this component and a model value and
     *            returns a property value
     * @param isInitialValueOptional
     *            if {@code isInitialValueOptional} is {@code true} then the
     *            initial value is used only if element has no {@code "value"}
     *            property value, otherwise element {@code "value"} property is
     *            ignored and the initial value is set
     * @param <P>
     *            the property type
     */
    public <P> GeneratedVaadinComboBox(T initialValue, T defaultValue,
            Class<P> elementPropertyType,
            SerializableBiFunction<R, P, T> presentationToModel,
            SerializableBiFunction<R, T, P> modelToPresentation,
            boolean isInitialValueOptional) {
        super("value", defaultValue, elementPropertyType, presentationToModel,
                modelToPresentation);
        if ((getElement().getProperty("value") == null
                || !isInitialValueOptional) && initialValue != null) {
            setPresentationValue(initialValue);
        }
    }

    /**
     * Constructs a new GeneratedVaadinComboBox component with the given
     * arguments.
     *
     * @param initialValue
     *            the initial value to set to the value
     * @param defaultValue
     *            the default value to use if the value isn't defined
     * @param elementPropertyType
     *            the type of the element property
     * @param presentationToModel
     *            a function that accepts this component and a property value
     *            and returns a model value
     * @param modelToPresentation
     *            a function that accepts this component and a model value and
     *            returns a property value
     * @param <P>
     *            the property type
     */
    public <P> GeneratedVaadinComboBox(T initialValue, T defaultValue,
            Class<P> elementPropertyType,
            SerializableBiFunction<R, P, T> presentationToModel,
            SerializableBiFunction<R, T, P> modelToPresentation) {
        this(initialValue, defaultValue, elementPropertyType,
                presentationToModel, modelToPresentation, false);
    }

    /**
     * Default constructor.
     */
    public GeneratedVaadinComboBox() {
        this(null, null, null, (SerializableFunction) null,
                (SerializableFunction) null);
    }
}

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
package com.vaadin.flow.component.radiobutton.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.radiobutton.dataview.RadioButtonGroupListDataView;
import com.vaadin.flow.component.radiobutton.demo.data.DepartmentData;
import com.vaadin.flow.component.radiobutton.demo.entity.Department;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

@Route("vaadin-radio-button")
public class RadioButtonGroupView extends DemoView {

    private static final String DATA_VIEW = "Data View";

    @Override
    protected void initView() {
        basicDemo(); // Basic Usage
        helperDemo();
        entityList();
        disabledAndDisabledItem();
        valueChangeEvent();
        dataViewRefreshItem(); // Data View
        dataViewAddAndRemoveItem();
        dataViewFiltering();
        configurationForRequired(); // Validation
        customOptions(); // Presentation
        usingTemplateRenderer();
        themeVariantsHorizontal();// Theme Variants
        themeVariantsHelperText();
        styling(); // Styling
    }

    private void basicDemo() {
        // begin-source-example
        // source-example-heading: Basic usage
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Label");
        radioGroup.setItems("Option one", "Option two", "Option three");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setValue("Option one");
        // end-source-example

        addCard("Basic usage", radioGroup);
    }

    private void helperDemo() {
        // begin-source-example
        // source-example-heading: Helper text
        RadioButtonGroup<String> radioGroupHelperText = new RadioButtonGroup<>();
        radioGroupHelperText.setLabel("Label");
        radioGroupHelperText.setItems("Option one", "Option two",
                "Option three");
        radioGroupHelperText.setHelperText("Helper text");

        RadioButtonGroup<String> radioGroupHelperComponent = new RadioButtonGroup<>();
        radioGroupHelperComponent.setLabel("Label");
        radioGroupHelperComponent.setItems("Option one", "Option two",
                "Option three");
        radioGroupHelperComponent.setHelperComponent(new Span("Helper text"));
        ;

        add(radioGroupHelperText, radioGroupHelperComponent);
        // end-source-example

        addCard("Helper text", radioGroupHelperText, radioGroupHelperComponent);
    }

    private void entityList() {
        // begin-source-example
        // source-example-heading: Entity list
        RadioButtonGroup<Department> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Department");
        List<Department> departmentList = getDepartments();
        radioGroup.setItems(departmentList);
        radioGroup.setItemLabelGenerator(Department::getName);
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        // end-source-example

        addCard("Entity list", radioGroup);
    }

    private void disabledAndDisabledItem() {
        // begin-source-example
        // source-example-heading: Disabled state
        RadioButtonGroup<String> disabledRadioGroup = new RadioButtonGroup<>();
        disabledRadioGroup.setLabel("Disabled");
        disabledRadioGroup.setItems("Option one", "Option two", "Option three");
        disabledRadioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        disabledRadioGroup.setValue("Option one");
        disabledRadioGroup.setEnabled(false);

        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Disabled item");
        radioGroup.setItems("Option one", "Option two", "Option three");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setItemEnabledProvider(item -> !"Option three".equals(item));
        // end-source-example

        disabledRadioGroup.getStyle().set("margin-right", "7.5em");
        VerticalLayout verticalLayout = new VerticalLayout(disabledRadioGroup,
                radioGroup);
        addCard("Disabled state", verticalLayout);
    }

    private List<Department> getDepartments() {

        DepartmentData departmentData = new DepartmentData();
        return departmentData.getDepartments();
    }

    private void valueChangeEvent() {
        // begin-source-example
        // source-example-heading: Value change event
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Label");
        radioGroup.setItems("Option one", "Option two", "Option three");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        Div value = new Div();
        value.setText("Select a value");
        radioGroup.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                value.setText("No option selected");
            } else {
                value.setText("Selected: " + event.getValue());
            }
        });
        // end-source-example

        addCard("Value change event", radioGroup, value);
    }

    private void configurationForRequired() {
        // begin-source-example
        // source-example-heading: Required
        Employee employee = new Employee();
        Binder<Employee> binder = new Binder<>();

        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Employee title");
        radioGroup.setItems("Account Manager", "Designer", "Marketing Manager",
                "Developer");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        binder.forField(radioGroup)
                .asRequired("Please choose an employee title")
                .bind(Employee::getTitle, Employee::setTitle);

        Button button = new Button("Submit", event -> {
            if (binder.writeBeanIfValid(employee)) {
                Notification.show("Submit successful", 2000,
                        Notification.Position.MIDDLE);
            }
        });
        // end-source-example

        radioGroup.getStyle().set("margin-right", "5.5em");
        HorizontalLayout layout = new HorizontalLayout(radioGroup, button);
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);
        addCard("Validation", "Required", layout);

    }

    private void customOptions() {
        // begin-source-example
        // source-example-heading: Customizing radio button label
        RadioButtonGroup<Employee> radioButton = new RadioButtonGroup<>();
        radioButton.setLabel("Employee");
        radioButton.setItems(
                new Employee("Gabriella",
                        "https://randomuser.me/api/portraits/women/43.jpg"),
                new Employee("Rudi",
                        "https://randomuser.me/api/portraits/men/77.jpg"),
                new Employee("Hamsa",
                        "https://randomuser.me/api/portraits/men/35.jpg"),
                new Employee("Jacob",
                        "https://randomuser.me/api/portraits/men/76.jpg"));
        radioButton.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        radioButton.setRenderer(new ComponentRenderer<>(employee -> {
            Div title = new Div();
            title.setText(employee.getTitle());

            Image image = new Image();
            image.setWidth("21px");
            image.setHeight("21px");
            image.setSrc(employee.getImage());

            FlexLayout wrapper = new FlexLayout();
            title.getStyle().set("margin-left", "0.5em");
            wrapper.add(image, title);
            return wrapper;
        }));
        // end-source-example

        addCard("Presentation", "Customizing radio button label", radioButton);
    }

    private void usingTemplateRenderer() {
        // begin-source-example
        // source-example-heading: Multi-line label
        RadioButtonGroup<Department> radioGroup = new RadioButtonGroup<>();
        List<Department> listOfDepartments = getDepartments();
        radioGroup.setItems(listOfDepartments);
        radioGroup.setLabel("Department");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        radioGroup.setRenderer(new ComponentRenderer<>(department -> {
            Div name = new Div();
            name.getStyle().set("font-weight", "bold");
            name.setText(department.getName());

            Div description = new Div();
            description.setText(department.getDescription());
            Div div = new Div(name, description);
            return div;
        }));
        // end-source-example

        addCard("Presentation", "Multi-line label", radioGroup);
    }

    private void themeVariantsHorizontal() {
        // begin-source-example
        // source-example-heading: Direction
        RadioButtonGroup<String> horizontal = new RadioButtonGroup<>();
        horizontal.setLabel("Horizontal");
        horizontal.setItems("Option one", "Option two", "Option three");
        horizontal.setValue("Option one");

        RadioButtonGroup<String> vertical = new RadioButtonGroup<>();
        vertical.setLabel("Vertical");
        vertical.setItems("Option one", "Option two", "Option three");
        vertical.setValue("Option one");
        vertical.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        // end-source-example

        addCard("Theme variants", "Direction", horizontal, vertical);
    }

    private void dataViewRefreshItem() {
        // begin-source-example
        // source-example-heading: Refresh Items
        RadioButtonGroup<Employee> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setLabel("Assignable Employees: ");
        Employee employee1 = new Employee("Employee One");
        Employee employee2 = new Employee("Employee Two");
        Employee employee3 = new Employee("Employee Three");
        RadioButtonGroupListDataView<Employee> dataView = radioButtonGroup
                .setItems(employee1, employee2, employee3);
        radioButtonGroup.setValue(employee3);
        radioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        Button updateButton = new Button("Update second employee's name",
                click -> {
                    employee2.setTitle("Employee 2");
                    dataView.refreshItem(employee2);
                });
        // end-source-example

        addCard(DATA_VIEW, "Refresh Items", radioButtonGroup, updateButton);
    }

    private void dataViewAddAndRemoveItem() {
        // begin-source-example
        // source-example-heading: Add and Remove Item
        RadioButtonGroup<Employee> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setLabel("Assignable Employees: ");
        List<Employee> employeeList = getEmployeeList();
        RadioButtonGroupListDataView<Employee> dataView = radioButtonGroup
                .setItems(employeeList);
        radioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        AtomicInteger employeeCounter = new AtomicInteger(1);
        Button addButton = new Button("Add to Options",
                click -> dataView.addItem(new Employee(
                        "Employee " + (employeeCounter.incrementAndGet()))));
        Button removeButton = new Button("Remove from Options", click -> {
            int itemCount = dataView.getItemCount();
            if (itemCount > 0) {
                dataView.removeItem(dataView.getItem(itemCount - 1));
            }
        });
        // end-source-example

        HorizontalLayout layout = new HorizontalLayout(radioButtonGroup,
                addButton, removeButton);
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);

        addCard(DATA_VIEW, "Add and Remove Item", layout);
    }

    private List<Employee> getEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Employee 1"));
        return employeeList;
    }

    private void dataViewFiltering() {
        // begin-source-example
        // source-example-heading: Filtering Items
        RadioButtonGroup<Integer> numbers = new RadioButtonGroup<>();
        RadioButtonGroupListDataView<Integer> numbersDataView = numbers
                .setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        RadioButtonGroup<String> oddEven = new RadioButtonGroup<>();
        oddEven.setLabel("Select Sub-set: ");
        oddEven.setItems("Odd Numbers", "Even Numbers", "No Filter");
        oddEven.addValueChangeListener(event -> {
            switch (event.getValue()) {
            case "Odd Numbers":
                numbersDataView.setFilter(number -> number % 2 == 1);
                break;

            case "Even Numbers":
                numbersDataView.setFilter(number -> number % 2 == 0);
                break;

            default:
                numbersDataView.removeFilters();
                break;
            }
        });
        // end-source-example

        addCard(DATA_VIEW, "Filtering Items", oddEven, numbers);
    }

    private void themeVariantsHelperText() {
        // begin-source-example
        // source-example-heading: Helper text position
        RadioButtonGroup<String> below = new RadioButtonGroup<>();
        below.setLabel("Label");
        below.setItems("Option one", "Option two", "Option three");
        below.setValue("Option one");
        below.setHelperText("Helper text below");

        RadioButtonGroup<String> above = new RadioButtonGroup<>();
        above.setLabel("Label");
        above.setItems("Option one", "Option two", "Option three");
        above.setValue("Option one");
        above.setHelperText("Helper text above");
        above.addThemeVariants(RadioGroupVariant.LUMO_HELPER_ABOVE_FIELD);

        add(below, above);
        // end-source-example

        addCard("Theme variants", "Helper text position", below, above);
    }

    private void styling() {
        Paragraph p1 = new Paragraph(
                "To read about styling you can read the related tutorial ");
        p1.add(new Anchor(
                "https://vaadin.com/docs/flow/theme/using-component-themes.html",
                "Using Component Themes"));

        Paragraph p2 = new Paragraph(
                "To know about styling in HTML you can read the ");
        p2.add(new Anchor("https://vaadin.com/components/"
                + "vaadin-radio-button/html-examples/radio-button-styling-demos",
                "HTML Styling Demos"));
        // begin-source-example
        // source-example-heading: Styling references

        // end-source-example
        addCard("Styling", "Styling references", p1, p2);
    }

    private static class Employee {
        private String title;
        private String image;

        public Employee() {
        }

        public Employee(String title) {
            this.title = title;
        }

        private Employee(String title, String image) {
            this.title = title;
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}

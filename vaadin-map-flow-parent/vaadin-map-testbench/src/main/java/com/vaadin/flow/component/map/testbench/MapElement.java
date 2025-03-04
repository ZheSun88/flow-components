package com.vaadin.flow.component.map.testbench;

import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

@Element("vaadin-map")
public class MapElement extends TestBenchElement {
    /**
     * Evaluates a Javascript expression against a vaadin-map's internal
     * OpenLayers map instance, and returns the result. The OpenLayers map
     * instance will be provided as the {@code map} variable to the expression.
     *
     * @param expression
     *            the Javascript expression to execute
     * @return result of the Javascript evaluation
     */
    public Object evaluateOLExpression(String expression) {
        return executeScript(
                "const map = arguments[0].configuration; return " + expression,
                getWrappedElement());
    }

    /**
     * Helper for building a Javascript expression that returns the type name of
     * an OpenLayers class instance
     *
     * @param jsExpression
     *            a Javascript expression that returns an OpenLayers class
     *            instance
     * @return the Javascript expression that evaluates the type name
     */
    public String getOLTypeNameExpression(String jsExpression) {
        return jsExpression + ".typeName";
    }

    /**
     * Performs a native click at the specified map coordinates. The method will
     * convert the coordinates into pixel values, and perform a click on the map
     * at the calculated pixel offset.
     *
     * @param x
     * @param y
     */
    public void clickAtCoordinates(double x, double y) {
        // Selenium click event offset starts from center, so we need to shift
        // the offset to the start of the element first
        Rectangle mapRectangle = this.getRect();
        int startLeft = -mapRectangle.width / 2;
        int startTop = -mapRectangle.height / 2;

        List<Number> pixelCoordinates = (List<Number>) executeScript(
                "return arguments[0].configuration.getPixelFromCoordinate([arguments[1], arguments[2]])",
                this, x, y);

        int clickX = startLeft + pixelCoordinates.get(0).intValue();
        int clickY = startTop + pixelCoordinates.get(1).intValue();
        new Actions(getDriver()).moveToElement(this, clickX, clickY).click()
                .build().perform();
    }

    /**
     * Returns a Javascript expression that returns the layer with the specified
     * ID.
     *
     * @return a Javascript expression evaluating the layer
     */
    public String getLayerExpression(String layerId) {
        return String.format(
                "map.getLayers().getArray().find(layer => layer.id === '%s')",
                layerId);
    }

    /**
     * Returns a Javascript expression that evaluates the feature collection of
     * the layer with the specified ID. Assumes that the layer is a vector layer
     * that has a feature collection.
     *
     * @return a Javascript expression evaluating the feature collection of the
     *         feature layer's source
     */
    public String getFeatureCollectionExpression(String layerId) {
        return getLayerExpression(layerId)
                + ".getSource().getFeaturesCollection()";
    }

    /**
     * Gets the attribution container div
     *
     * @return attribution container div
     */
    public TestBenchElement getAttributionContainer() {
        return $("div").attributeContains("class", "ol-attribution").first();
    }

    /**
     * Gets the list of attributions list items in the attribution container div
     *
     * @return list of list items
     */
    public List<TestBenchElement> getAttributionItems() {
        return getAttributionContainer().$("li").all();
    }

    /**
     * Disables all interactions that could interfere with a test, such as
     * double-click to zoom.
     */
    public void disableInteractions() {
        String script = "const interactions = arguments[0].configuration.getInteractions();"
                + "interactions.forEach(interaction => interaction.setActive && interaction.setActive(false));";
        executeScript(script, this);
    }
}

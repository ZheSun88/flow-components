package com.vaadin.flow.component.map.configuration;

/*
 * #%L
 * Vaadin Map
 * %%
 * Copyright (C) 2022 - 2022 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Developer License
 * 4.0 (CVDLv4).
 *
 * See the file license.html distributed with this software for more
 * information about licensing.
 *
 * For the full License, see <https://vaadin.com/license/cvdl-4.0>.
 * #L%
 */

import com.vaadin.flow.component.map.configuration.geometry.Point;
import com.vaadin.flow.component.map.configuration.geometry.SimpleGeometry;
import com.vaadin.flow.component.map.configuration.style.Style;

import java.util.Objects;

/**
 * A geographic feature to be displayed on a map. A feature can be anything that
 * should be displayed on top of a map, such as points of interest, vehicles or
 * people.
 */
public abstract class Feature extends AbstractConfigurationObject {

    private SimpleGeometry geometry;
    private Style style;

    @Override
    public String getType() {
        return Constants.OL_FEATURE;
    }

    /**
     * The geometry representing the feature, for example a {@link Point} or a
     * polygon.
     *
     * @return the current geometry
     */
    public SimpleGeometry getGeometry() {
        return geometry;
    }

    /**
     * Sets the geometry representing the feature.
     *
     * @param geometry
     *            the new geometry, not null
     */
    public void setGeometry(SimpleGeometry geometry) {
        Objects.requireNonNull(geometry);
        updateNestedPropertyObserver(this.geometry, geometry);
        this.geometry = geometry;
        notifyChange();
    }

    /**
     * The {@link Style} defines how the feature should be visually displayed.
     *
     * @return the current style
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Sets the style that defines how the feature should be visually displayed.
     *
     * @param style
     *            the new style, not null
     */
    public void setStyle(Style style) {
        Objects.requireNonNull(style);
        updateNestedPropertyObserver(this.style, style);
        this.style = style;
        notifyChange();
    }
}

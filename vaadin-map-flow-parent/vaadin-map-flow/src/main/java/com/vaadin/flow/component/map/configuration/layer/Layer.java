package com.vaadin.flow.component.map.configuration.layer;

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

import com.vaadin.flow.component.map.configuration.AbstractConfigurationObject;

public abstract class Layer extends AbstractConfigurationObject {
    private float opacity = 1;
    private boolean visible = true;

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity, boolean markDirty) {
        this.opacity = opacity;
        notifyChange();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        notifyChange();
    }
}

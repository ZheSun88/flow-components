package com.vaadin.flow.component.map.configuration.source;

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

import com.vaadin.flow.component.map.configuration.Constants;
import com.vaadin.flow.component.map.configuration.Feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VectorSource extends Source {
    private final List<Feature> features = new ArrayList<>();

    public VectorSource() {
        this(new Options());
    }

    public VectorSource(BaseOptions options) {
        super(options);
    }

    @Override
    public String getType() {
        return Constants.OL_SOURCE_VECTOR;
    }

    public List<Feature> getFeatures() {
        return Collections.unmodifiableList(features);
    }

    public void addFeature(Feature feature) {
        Objects.requireNonNull(feature);

        feature.addPropertyChangeListener(this::notifyChange);

        features.add(feature);
        notifyChange();
    }

    public void removeFeature(Feature feature) {
        Objects.requireNonNull(feature);

        feature.removePropertyChangeListener(this::notifyChange);

        features.remove(feature);
        notifyChange();
    }

    protected static class BaseOptions<T extends BaseOptions<T>>
            extends Source.BaseOptions<T> {
    }

    public static class Options extends BaseOptions<Options> {
    }
}

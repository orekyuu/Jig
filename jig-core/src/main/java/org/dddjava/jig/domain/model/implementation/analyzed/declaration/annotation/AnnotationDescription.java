package org.dddjava.jig.domain.model.implementation.analyzed.declaration.annotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * アノテーションの記述
 */
public class AnnotationDescription {

    final Map<String, String> map = new HashMap<>();

    public String asText() {
        return map.toString();
    }

    public void addAnnotation(String name, String descriptor) {
        map.put(name, descriptor + "[...]");
    }

    public void addArray(String name, List<Object> list) {
        if (list.size() == 1) {
            this.map.put(name, list.get(0).toString());
        } else {
            this.map.put(name, list.toString());
        }
    }

    public void addEnum(String name, String value) {
        map.put(name, value);
    }

    public void addParam(String name, Object value) {
        map.put(name, String.valueOf(value));
    }

    public String textOf(String name) {
        return map.get(name);
    }
}

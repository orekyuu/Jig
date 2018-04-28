package org.dddjava.jig.domain.model.angle;

import org.dddjava.jig.domain.model.declaration.method.MethodDeclarations;
import org.dddjava.jig.domain.model.declaration.method.MethodDeclarations;

public class DesignSmellAngle {

    private final MethodDeclarations stringComparingMethods;

    public DesignSmellAngle(MethodDeclarations stringComparingMethods) {
        this.stringComparingMethods = stringComparingMethods;
    }

    public MethodDeclarations stringComparingMethods() {
        return stringComparingMethods;
    }
}
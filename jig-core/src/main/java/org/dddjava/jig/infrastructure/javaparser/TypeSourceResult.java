package org.dddjava.jig.infrastructure.javaparser;

import org.dddjava.jig.domain.model.implementation.analyzed.alias.MethodAlias;
import org.dddjava.jig.domain.model.implementation.analyzed.alias.TypeAlias;

import java.util.List;

public class TypeSourceResult {
    TypeAlias typeAlias;
    List<MethodAlias> methodAliases;

    public TypeSourceResult(TypeAlias typeAlias, List<MethodAlias> methodAliases) {
        this.typeAlias = typeAlias;
        this.methodAliases = methodAliases;
    }
}

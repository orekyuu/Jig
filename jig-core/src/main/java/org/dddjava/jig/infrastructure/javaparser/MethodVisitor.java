package org.dddjava.jig.infrastructure.javaparser;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.dddjava.jig.domain.model.implementation.analyzed.alias.Alias;
import org.dddjava.jig.domain.model.implementation.analyzed.alias.MethodAlias;
import org.dddjava.jig.domain.model.implementation.analyzed.declaration.method.Arguments;
import org.dddjava.jig.domain.model.implementation.analyzed.declaration.method.MethodIdentifier;
import org.dddjava.jig.domain.model.implementation.analyzed.declaration.method.MethodSignature;
import org.dddjava.jig.domain.model.implementation.analyzed.declaration.type.TypeIdentifier;

import java.util.Collections;
import java.util.List;

class MethodVisitor extends VoidVisitorAdapter<List<MethodAlias>> {
    private final TypeIdentifier typeIdentifier;

    public MethodVisitor(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
    }

    @Override
    public void visit(MethodDeclaration n, List<MethodAlias> methodAliases) {
        n.getJavadoc().ifPresent(javadoc -> {
            String javadocText = javadoc.getDescription().toText();

            MethodAlias methodAlias = new MethodAlias(
                    new MethodIdentifier(
                            typeIdentifier,
                            new MethodSignature(
                                    n.getNameAsString(),
                                    // TODO 引数を取得したい
                                    new Arguments(Collections.emptyList())
                            )),
                    new Alias(javadocText)
            );
            methodAliases.add(methodAlias);
        });
    }
}

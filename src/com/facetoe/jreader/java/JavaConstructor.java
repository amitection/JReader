package com.facetoe.jreader.java;

/**
 * Created with IntelliJ IDEA.
 * User: facetoe
 * Date: 1/11/13
 * Time: 12:01 PM
 */

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.stmt.BlockStmt;

/**
 * Represents a constructor.
 */
public class JavaConstructor extends AbstractJavaObject<ConstructorDeclaration> {

    public JavaConstructor(ConstructorDeclaration typeDec) {
        super(typeDec);
        beginColumn = typeDec.getBeginColumn();
        endColumn = typeDec.getEndColumn();
        beginLine = typeDec.getBeginLine();
        endLine = typeDec.getEndLine();
        type = CONSTRUCTOR;
    }

    @Override
    void extractFullDeclaration() {
        fullDeclaration = new ConstructorDeclaration(null,
                typeDeclaration.getModifiers(),
                null,
                typeDeclaration.getTypeParameters(),
                typeDeclaration.getName(),
                typeDeclaration.getParameters(),
                typeDeclaration.getThrows(),
                new BlockStmt()) // Add a new, empty block or it throws an error.
                .toString()
                .replace("{", "")
                .replace("}", "")
                .replaceAll("\\n", "");
    }

    @Override
    void extractDeclaration() {
        if ( typeDeclaration.getParameters() == null ) {
            declaration = typeDeclaration.getName() + "()";
        } else {
            declaration = (typeDeclaration.getName()
                    + typeDeclaration.getParameters())
                    .replace("[", "(")
                    .replace("]", ")");
        }
    }

    @Override
    public int getModifiers() {
        return typeDeclaration.getModifiers();
    }
}

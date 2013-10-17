package com.facetoe.jreader;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.*;
import japa.parser.ast.visitor.GenericVisitorAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class JavaSourceFileParser {

    public static JavaSourceFile parse(FileInputStream inputStream) throws ParseException, IOException {
        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(inputStream);
        } finally {
            inputStream.close();
        }
        return ( JavaSourceFile ) new SourceFileVisitor().visit(cu, null);
    }

    private static class SourceFileVisitor extends GenericVisitorAdapter {
        @Override
        public Object visit(CompilationUnit n, Object arg) {
            ArrayList<JavaClassOrInterface> classesAndInterfaces = new ArrayList<JavaClassOrInterface>();
            if ( n != null && n.getTypes() != null ) {
                for ( TypeDeclaration type : n.getTypes() ) {
                    if ( type instanceof ClassOrInterfaceDeclaration ) {
                        JavaClassOrInterface javaObj = new JavaClassOrInterface(( ClassOrInterfaceDeclaration ) type);

                        for ( BodyDeclaration declaration : type.getMembers() ) {
                            if ( declaration instanceof MethodDeclaration ) {
                                JavaMethod method = ( JavaMethod ) visit(( MethodDeclaration ) declaration, null);
                                javaObj.addMethod(method);

                            } else if ( declaration instanceof FieldDeclaration ) {
                                JavaField field = ( JavaField ) visit(( FieldDeclaration ) declaration, null);
                                javaObj.addField(field);

                            } else if ( declaration instanceof EnumDeclaration ) {
                                JavaEnum javaEnum = ( JavaEnum ) visit(( EnumDeclaration ) declaration, null);
                                javaObj.addEnum(javaEnum);

                            } else if ( declaration instanceof ConstructorDeclaration ) {
                                JavaConstructor constructor = ( JavaConstructor ) visit(( ConstructorDeclaration ) declaration, null);
                                javaObj.addConstructor(constructor);

                            } else {
                                //System.out.println(declaration.getClass());
                            }
                        }
                        classesAndInterfaces.add(javaObj);
                    }
                }
            } else {
                System.out.println("Null Fuck");
            }
            return new JavaSourceFile(classesAndInterfaces);
        }

        @Override
        public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
            return new JavaClassOrInterface(n).getDeclaration();
        }

        @Override
        public Object visit(ConstructorDeclaration n, Object arg) {
            return new JavaConstructor(n);
        }

        @Override
        public Object visit(EnumConstantDeclaration n, Object arg) {
            System.out.println("Called enumConstant");
            System.exit(0); //TODO remove this!
            return null;
        }

        @Override
        public Object visit(EnumDeclaration n, Object arg) {
            return new JavaEnum(n);
        }

        @Override
        public Object visit(FieldDeclaration n, Object arg) {
            return new JavaField(n);
        }

        @Override
        public Object visit(MethodDeclaration n, Object arg) {
            return new JavaMethod(n);
        }
    }
}


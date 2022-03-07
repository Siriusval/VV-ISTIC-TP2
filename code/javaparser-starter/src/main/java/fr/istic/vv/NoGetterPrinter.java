package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.nodeTypes.modifiers.NodeWithPrivateModifier;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

import java.lang.reflect.Field;


// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class NoGetterPrinter extends VoidVisitorWithDefaults<Void> {

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitClassDeclaration(declaration, arg);
    }

    public void visitClassDeclaration(ClassOrInterfaceDeclaration declaration, Void arg) {
        System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"));
        for(FieldDeclaration field : declaration.getFields()) {
            field.accept(this, arg);
        }

    }

    @Override
    public void visit(FieldDeclaration declaration, Void arg) {
        visitFieldDeclaration(declaration, arg);
    }

    public void visitFieldDeclaration(FieldDeclaration declaration, Void arg) {
        if(declaration.isPrivate() && !declaration.hasModifier(Modifier.Keyword.PUBLIC) && !declaration.isFinal()){
            System.out.println(" Line: " + declaration.getRange().map(r -> r.begin.line).orElse(-1) + "\n"+declaration);
        }
    }



    /*@Override
    public void visit(CompilationUnit unit, Void arg) {
        super.visit()
        unit.findAll(FieldDeclaration.class).stream()
                .filter(f -> f.isPrivate() && !f.hasModifier(Modifier.Keyword.PUBLIC) && !f.isFinal())
                .forEach(f -> System.out.println(" Line: " + f.getRange().map(r -> r.begin.line).orElse(-1) + "\n"+f));
    }

    public void visitTypeDeclaration(FieldDeclaration declaration, Void arg) {
        System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"));
    }


    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(ClassOrInterfaceDeclaration type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitClassOrInterfaceDeclaration(declaration, arg);
    }

    @Override
    public void visit(FieldDeclaration declaration, Void arg) {
        visitFieldDeclaration(declaration, arg);
    }*/
}

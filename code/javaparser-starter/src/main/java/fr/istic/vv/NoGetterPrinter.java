package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.nodeTypes.modifiers.NodeWithPrivateModifier;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import com.github.javaparser.resolution.MethodUsage;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


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
        for(MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg);
        }
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        visitMethodDeclaration(declaration, arg);
    }

    public void visitMethodDeclaration(MethodDeclaration declaration, Void arg) {
        MethodUsage resolve = declaration.resolve();
        resolve.
    }
}

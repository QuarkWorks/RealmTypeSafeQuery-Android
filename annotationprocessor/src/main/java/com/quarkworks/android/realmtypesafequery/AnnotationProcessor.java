package com.quarkworks.android.realmtypesafequery;

import com.google.auto.service.AutoService;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import io.realm.annotations.Ignore;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(GenerateRealmFieldNames.class.getCanonicalName());
        set.add(GenerateRealmFields.class.getCanonicalName());

        return set;
    }

    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateRealmFieldNames.class)) {
            if (!(element instanceof TypeElement)) continue;

            TypeElement typeElement = (TypeElement) element;
            List<VariableElement> variableElements = ElementFilter.fieldsIn(typeElement.getEnclosedElements());
            List<FieldSpec> fieldSpecs = new LinkedList<>();

            for (VariableElement variableElement : variableElements) {

                boolean isIgnored = variableElement.getAnnotation(Ignore.class) != null;
                if (isIgnored) continue;

                Type type = String.class;
                String name = variableElement.getSimpleName().toString()
                        .replaceAll("([A-Z])", "_$1").toUpperCase();
                Modifier[] modifiers = {Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL};
                CodeBlock codeBlock = CodeBlock.of("$S", variableElement.getSimpleName());

                FieldSpec fieldSpec = FieldSpec.builder(type, name, modifiers)
                        .initializer(codeBlock)
                        .build();

                fieldSpecs.add(fieldSpec);
            }

            String packageName = this.getClass().getPackage().getName() + ".generated";
            String className = typeElement.getSimpleName() + "FieldNames";

            TypeSpec typeSpec = TypeSpec.classBuilder(className)
                    .addFields(fieldSpecs)
                    .addModifiers(Modifier.PUBLIC)
                    .build();

            JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();

            try {
                javaFile.writeTo(this.processingEnv.getFiler());
            } catch (IOException e) {
                this.reportError(element, e.toString());
            }
        }

        return true;
    }

    private void reportError(Element element, CharSequence message) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void reportWarning(Element element, CharSequence message) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message, element);
    }

    private void log(CharSequence message) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }
}

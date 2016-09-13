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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

import static java.lang.String.format;



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


    @Override //synchronized not needed
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateRealmFieldNames(roundEnv);
        generateRealmFields(roundEnv);
        return true;
    }


    private void generateRealmFieldNames(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateRealmFieldNames.class)) {
            if (!(element instanceof TypeElement)) continue;

            TypeElement typeElement = (TypeElement) element;
            List<VariableElement> variableElements = ElementFilter.fieldsIn(typeElement.getEnclosedElements());
            List<FieldSpec> fieldSpecs = new LinkedList<>();

            for (VariableElement variableElement : variableElements) {
                if (variableElement.getModifiers().contains(Modifier.STATIC)) continue;
                boolean isIgnored = variableElement.getAnnotation(Ignore.class) != null;
                if (isIgnored) continue;

                Type type = String.class;
                String name = variableElement.getSimpleName().toString()
                        .replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
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
    }

    private FieldSpec makeFieldSpec(String klass, String field_name, Element extra) {
        Modifier[] modifiers = {Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL};
        String field_name_constant = field_name
                .replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();

        log("<");
        log(format("klass: \"%s\"", klass));
        log(format("field_name: \"%s\"", field_name));
        log(format("isIndex: %b", null != extra.getAnnotation(Ignore.class)));
        log(format("isPrimaryKey: %b", null != extra.getAnnotation(PrimaryKey.class)));
        log(">");
        return null;
    }

    private void generateRealmFields(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateRealmFields.class)) {
            if (!(element instanceof TypeElement)) continue;

            TypeElement realmClassElement = (TypeElement) element;
            List<VariableElement> variableElements = ElementFilter.fieldsIn(realmClassElement.getEnclosedElements());
            List<FieldSpec> realmFieldClassFSpecs = new LinkedList<>();

            for (VariableElement realmFieldElement : variableElements) {
                // ignore static and @Ignore fields
                if (realmFieldElement.getModifiers().contains(Modifier.STATIC)) continue;
                boolean isIgnored = realmFieldElement.getAnnotation(Ignore.class) != null;
                if (isIgnored) continue;

                String field_name = realmFieldElement.getSimpleName().toString();

                realmFieldClassFSpecs.add(makeFieldSpec(realmFieldElement.asType().toString(), field_name, realmFieldElement));
            }

//            String packageName = this.getClass().getPackage().getName() + ".generated";
//            String className = realmClassElement.getSimpleName() + "Fields";
//
//            TypeSpec typeSpec = TypeSpec.classBuilder(className)
//                    .addFields(realmFieldClassFSpecs)
//                    .addModifiers(Modifier.PUBLIC)
//                    .build();
//
//            JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
//
//            try {
//                javaFile.writeTo(this.processingEnv.getFiler());
//            } catch (IOException e) {
//                this.reportError(element, e.toString());
//            }
        }
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

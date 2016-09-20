package com.quarkworks.android.realmtypesafequery;

import com.google.auto.service.AutoService;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AnnotationProcessor extends AbstractProcessor {

    private static final Modifier[] fieldSpecs_modifiers = {Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL};
    private TypeElement realmModel;
    @SuppressWarnings("FieldCanBeLocal")
    private TypeElement realmList;
    private TypeMirror realmList_erasure;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(GenerateRealmFieldNames.class.getCanonicalName());
        set.add(GenerateRealmFields.class.getCanonicalName());

        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        realmModel = processingEnvironment.getElementUtils().getTypeElement("io.realm.RealmModel");
        realmList = processingEnvironment.getElementUtils().getTypeElement("io.realm.RealmList");
        realmList_erasure = processingEnvironment.getTypeUtils().erasure(realmList.asType());
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

                FieldSpec fieldSpec = FieldSpec.builder(type, name, fieldSpecs_modifiers)
                        .initializer("$S", variableElement.getSimpleName())
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


    private FieldSpec makeFieldSpec(Element realmClassElement, Element realmFieldElement) {

        if (processingEnv.getTypeUtils().isSubtype(realmFieldElement.asType(), realmModel.asType())) {
            return makeToOne(realmClassElement, realmFieldElement);
        }
        if (processingEnv.getTypeUtils().isSameType(processingEnv.getTypeUtils().erasure(realmFieldElement.asType()), realmList_erasure)) {
            return makeToMany(realmClassElement, realmFieldElement);
        }
        String rfe_klass = realmFieldElement.asType().toString();
        String field_name = realmFieldElement.getSimpleName().toString();
        String field_name_constant = field_name
                .replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();

        boolean isPk = null != realmFieldElement.getAnnotation(PrimaryKey.class);
        boolean isIndex = null != realmFieldElement.getAnnotation(Index.class);
        TypeMirror rce_tm = realmClassElement.asType();
        FieldSpec fs;

        if (!isPk && !isIndex) {
            ParameterizedTypeName pt_n = ParameterizedTypeName.get(Maps.BaseMap.get(rfe_klass), TypeName.get(rce_tm));
            fs = FieldSpec.builder(pt_n, field_name_constant, fieldSpecs_modifiers)
                    .initializer("new $T($T.class, $S)", pt_n, TypeName.get(rce_tm), field_name).build();
        } else {
            ParameterizedTypeName pt_n = ParameterizedTypeName
                    .get(Maps.IndexMap.get(Maps.BaseMap.get(rfe_klass)), TypeName.get(rce_tm));
            fs = FieldSpec.builder(pt_n, field_name_constant, fieldSpecs_modifiers)
                    .initializer("new $T($T.class, $S)", pt_n, TypeName.get(rce_tm), field_name).build();
        }
        return fs;
    }
    private FieldSpec makeToMany(Element realmClassElement, Element realmFieldElement) {
        TypeMirror rce_tm = realmClassElement.asType();
        String field_name = realmFieldElement.getSimpleName().toString();
        String field_name_constant = field_name
                .replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();

        ParameterizedTypeName pt_n = ParameterizedTypeName.get(Maps.realmtomanyrelationship,
                TypeName.get(rce_tm),
                TypeName.get((((DeclaredType)realmFieldElement.asType()).getTypeArguments()).get(0)));

        return FieldSpec.builder(pt_n, field_name_constant, fieldSpecs_modifiers)
                .initializer("new $T($T.class, $S)", pt_n, TypeName.get(rce_tm), field_name).build();

    }

    private FieldSpec makeToOne(Element realmClassElement, Element realmFieldElement) {
        TypeMirror rce_tm = realmClassElement.asType();
        String field_name = realmFieldElement.getSimpleName().toString();
        String field_name_constant = field_name
                .replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
        ParameterizedTypeName pt_n = ParameterizedTypeName.get(Maps.realmtoonerelationship,
                TypeName.get(rce_tm),
                TypeName.get((realmFieldElement).asType()));

        return FieldSpec.builder(pt_n, field_name_constant, fieldSpecs_modifiers)
                .initializer("new $T($T.class, $S)", pt_n, TypeName.get(rce_tm), field_name).build();

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

                realmFieldClassFSpecs.add(makeFieldSpec(realmClassElement, realmFieldElement));
            }

            String packageName = this.getClass().getPackage().getName() + ".generated";
            String className = realmClassElement.getSimpleName() + "Fields";

            TypeSpec typeSpec = TypeSpec.classBuilder(className)
                    .addFields(realmFieldClassFSpecs)
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



    private void reportError(Element element, CharSequence message) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void reportWarning(Element element, CharSequence message) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message, element);
    }

    private void log(CharSequence message) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }

    private CharSequence cat(List in)
    {
        StringBuilder b = new StringBuilder();
        for (Object i: in)
        {
            b.append("\"");
            b.append(i.toString());
            b.append("\", ");
        }
        return b;
    }

    private void logall(CharSequence... rest) {
        for (CharSequence m : rest) {
            log(m);
        }
    }

}

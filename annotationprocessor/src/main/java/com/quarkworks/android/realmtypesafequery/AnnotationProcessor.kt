package com.quarkworks.android.realmtypesafequery

import com.google.auto.service.AutoService
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec

import java.io.IOException
import java.util.HashSet
import java.util.LinkedList

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.ElementFilter
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

import io.realm.annotations.Ignore
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
class AnnotationProcessor : AbstractProcessor() {
    private val packageName: String
    private var realmModel: DeclaredType? = null
    private var realmList_erasure: TypeMirror? = null
    private var typeUtils: Types? = null
    private var elementUtils: Elements? = null

    init {
        packageName = this.javaClass.`package`.name + ".generated"
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val set = HashSet<String>()
        set.add(GenerateRealmFieldNames::class.java.canonicalName)
        set.add(GenerateRealmFields::class.java.canonicalName)

        return set
    }

    @Synchronized override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        log("GIT_COMMIT:${GIT_COMMIT.`val`}")
        typeUtils = processingEnv.typeUtils
        elementUtils = processingEnv.elementUtils
        realmModel = elementUtils!!.getTypeElement("io.realm.RealmModel").asType() as DeclaredType
        realmList_erasure = typeUtils!!.erasure(elementUtils!!.getTypeElement("io.realm.RealmList")
                .asType())
    }

    @Synchronized override //synchronized not needed
    fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        generateRealmFieldNames(roundEnv)
        generateRealmFields(roundEnv)
        return true
    }

    private fun generateRealmFieldNames(roundEnv: RoundEnvironment) {
        for (element in roundEnv.getElementsAnnotatedWith(GenerateRealmFieldNames::class.java)) {
            if (element !is TypeElement) continue

            val variableElements = ElementFilter.fieldsIn(element.enclosedElements)
            val fieldSpecs = LinkedList<FieldSpec>()

            for (realmField in variableElements) {
                // ignore static and @Ignore fields
                if (realmField.modifiers.contains(Modifier.STATIC)) continue
                if (isAnnotatedWith(realmField, Ignore::class.java)) continue

                val name = toConstId(realmField.simpleName.toString())

                val fieldSpec = FieldSpec.builder(String::class.java, name, *fieldSpecs_modifiers)
                        .initializer("\$S", realmField.simpleName)
                        .build()

                fieldSpecs.add(fieldSpec)
            }

            val className = element.simpleName.toString() + "FieldNames"

            val typeSpec = TypeSpec.classBuilder(className).addFields(fieldSpecs)
                    .addModifiers(Modifier.PUBLIC).build()

            val javaFile = JavaFile.builder(packageName, typeSpec).build()

            try {
                javaFile.writeTo(this.processingEnv.filer)
            } catch (e: IOException) {
                this.reportError(element, e.toString())
            }

        }
    }

    private fun makeFieldSpec(realmClassElement: Element, realmFieldElement: Element): FieldSpec {
        
        if (typeUtils!!.isSubtype(realmFieldElement.asType(), realmModel)) {
            return makeToOne(realmClassElement, realmFieldElement)
        }
        if (typeUtils!!.isSubtype(typeUtils!!.erasure(realmFieldElement.asType()), realmList_erasure)) {
            return makeToMany(realmClassElement, realmFieldElement)
        }
        val rfe_klass = realmFieldElement.asType().toString()
        val field_name = realmFieldElement.simpleName.toString()
        val field_name_constant = toConstId(field_name)

        val isPk = isAnnotatedWith(realmFieldElement, PrimaryKey::class.java)
        val isIndex = isAnnotatedWith(realmFieldElement, Index::class.java)
        val rce_tm = realmClassElement.asType()
        val fs: FieldSpec

        if (!isPk && !isIndex) {
            val pt_n = ParameterizedTypeName.get(Maps.BaseMap[rfe_klass], TypeName.get(rce_tm))
            fs = FieldSpec.builder(pt_n, field_name_constant, *fieldSpecs_modifiers)
                    .initializer("new \$T(\$T.class, \$S)", pt_n, TypeName.get(rce_tm), field_name)
                    .build()
        } else {
            val pt_n = ParameterizedTypeName.get(Maps.IndexMap[Maps.BaseMap[rfe_klass]], TypeName.get(rce_tm))
            fs = FieldSpec.builder(pt_n, field_name_constant, *fieldSpecs_modifiers)
                    .initializer("new \$T(\$T.class, \$S)", pt_n, TypeName.get(rce_tm), field_name)
                    .build()
        }
        return fs
    }

    private fun makeToMany(realmClassElement: Element, realmFieldElement: Element): FieldSpec {
        val rce_tm = realmClassElement.asType()
        val field_name = realmFieldElement.simpleName.toString()
        val field_name_constant = toConstId(field_name)

        val pt_n = ParameterizedTypeName.get(Maps.realmtomanyrelationship,
                TypeName.get(rce_tm),
                TypeName.get((realmFieldElement.asType() as DeclaredType).typeArguments[0]))

        return FieldSpec.builder(pt_n, field_name_constant, *fieldSpecs_modifiers)
                .initializer("new \$T(\$T.class, \$S)", pt_n, TypeName.get(rce_tm), field_name)
                .build()

    }

    private fun makeToOne(realmClassElement: Element, realmFieldElement: Element): FieldSpec {
        val rce_tm = realmClassElement.asType()
        val field_name = realmFieldElement.simpleName.toString()
        val field_name_constant = toConstId(field_name)
        val pt_n = ParameterizedTypeName.get(Maps.realmtoonerelationship,
                TypeName.get(rce_tm),
                TypeName.get(realmFieldElement.asType()))

        return FieldSpec.builder(pt_n, field_name_constant, *fieldSpecs_modifiers)
                .initializer("new \$T(\$T.class, \$S)", pt_n, TypeName.get(rce_tm), field_name)
                .build()

    }

    private fun generateRealmFields(roundEnv: RoundEnvironment) {
        for (element in roundEnv.getElementsAnnotatedWith(GenerateRealmFields::class.java)) {
            if (element !is TypeElement) continue

            val variableElements = ElementFilter.fieldsIn(element.enclosedElements)
            val realmFieldClassFSpecs = LinkedList<FieldSpec>()

            for (realmFieldElement in variableElements) {
                // ignore static and @Ignore fields
                if (realmFieldElement.modifiers.contains(Modifier.STATIC)) continue
                if (isAnnotatedWith(realmFieldElement, Ignore::class.java)) continue

                realmFieldClassFSpecs.add(makeFieldSpec(element, realmFieldElement))
            }

            val className = element.simpleName.toString() + "Fields"

            val typeSpec = TypeSpec.classBuilder(className)
                    .addFields(realmFieldClassFSpecs)
                    .addModifiers(Modifier.PUBLIC)
                    .build()

            val javaFile = JavaFile.builder(packageName, typeSpec).build()

            try {
                javaFile.writeTo(this.processingEnv.filer)
            } catch (e: IOException) {
                this.reportError(element, e.toString())
            }

        }
    }


    private fun reportError(element: Element, message: CharSequence) {
        this.processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, message, element)
    }

    private fun reportWarning(element: Element, message: CharSequence) {
        this.processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, message, element)
    }

    private fun log(message: CharSequence) {
        this.processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, message)
    }

    private fun cat(`in`: List<*>): CharSequence {
        val b = StringBuilder()
        for (i in `in`) {
            b.append("\"")
            b.append(i.toString())
            b.append("\", ")
        }
        return b
    }

    private fun logall(vararg rest: CharSequence) {
        for (m in rest) {
            log(m)
        }
    }

    companion object {

        private val fieldSpecs_modifiers = arrayOf(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)

        private fun isAnnotatedWith(element: Element, annotation: Class<out Annotation>): Boolean {

            return element.getAnnotation(annotation) != null
        }

        private fun toConstId(`in`: String): String {
            return `in`.replace("([a-z])([A-Z])".toRegex(), "$1_$2").toUpperCase()
        }
    }

}

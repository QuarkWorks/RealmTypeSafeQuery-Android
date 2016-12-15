package com.quarkworks.android.realmtypesafequery;
// from intro examples realm-java

import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFieldNames;
import com.quarkworks.android.realmtypesafequery.annotations.GenerateRealmFields;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

@RealmClass
@GenerateRealmFieldNames
@GenerateRealmFields
public class Person implements RealmModel {

    // All fields are by default persisted.
    private String name;
    private int age;

    // Other objects in a one-to-one relation must also subclass RealmObject
    private Dog dog;

    // One-to-many relations is simply a RealmList of the objects which also subclass RealmObject
    private RealmList<Cat> cats;

    // You can instruct Realm to ignore a field and not persist it.
    @Ignore
    private int tempReference;

    private long id;

    // Let your IDE generate getters and setters for you!
    // Or if you like you can even have public fields and no accessors! See Dog.java and Cat.java
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public RealmList<Cat> getCats() {
        return cats;
    }


    public int getTempReference() {
        return tempReference;
    }

    public void setTempReference(int tempReference) {
        this.tempReference = tempReference;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

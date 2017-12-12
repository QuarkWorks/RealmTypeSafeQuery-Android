# RealmTypeSafeQuery-Android
[![](https://jitpack.io/v/QuarkWorks/RealmTypeSafeQuery-Android.svg)](https://jitpack.io/#QuarkWorks/RealmTypeSafeQuery-Android)


### A type safe way to handle realm queries in Android.
Supports Realm query API 110% (there are some bonus features too 😉)
```java
// Bad, field name and type are checked at runtime.
realm.where(Person.class).equalTo("firstName", "Sally").findFirst();

// Better, field name is checked at compile time, but type is still at runtime.
realm.where(Person.class).equalTo(PersonFieldNames.FIRST_NAME, "Sally").findFirst();

// Best, field name and type are both check at compile type.
RealmTypeSafeQuery.with(realm).where(Person.class).equalTo(PersonFields.FIRST_NAME, "Sally").findFirst();
```

## How to include

#### In your top level build file, add the jitpack repository
```groovy
buildscript {
    dependencies {
            classpath "io.realm:realm-gradle-plugin:4.3.1" // supported version of realm
    }
}

allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" } // needed to import RTSQ
    }
}
```

#### App module build file dependencies:
```groovy
apply plugin: 'realm-android' // realm setup

compileOnly 'com.github.quarkworks.RealmTypeSafeQuery-Android:annotations:{{version_number}}' // annotations
annotationProcessor 'com.github.quarkworks.RealmTypeSafeQuery-Android:annotationprocessor:{{version_number}}' // annotation processor
implementation 'com.github.quarkworks.RealmTypeSafeQuery-Android:query:{{version_number}}'  // query dsl
```

#### Example Model
```java
@GenerateRealmFields // Generates a file called PersonFields.java
@GenerateRealmFieldNames // Generates a file called PersonFieldNames.java
class Person extends RealmObject {
    String firstName;
    String lastName;
    Date birthday;
    
    RealmList<Pet> pets;
    
    // If what pops out of the code generator doesn't compile add these annotations
    // Realm constantly updates their api and RTSQ might be a little behind
    @SkipGenerationOfRealmFieldNames
    @SkipGenerationOfRealmField  
    RealmList<String> website;
}

@GenerateRealmFields // Generates a file called PetFields.java
@GenerateRealmFieldNames // Generates a file called PetFieldNames.java
class Pet extends RealmObject {
    String name;
    Integer weight;
}
```

#### Example Queries

```java

Realm realm = Realm.getInstance();

RealmResults<Person> sallyNotSmiths = RealmTypeSafeQuery.with(realm).where(Person.class)
    .equalTo(PersonFields.FIRST_NAME, "Sally")
    .notEqualTo(PersonFields.LAST_NAME, "Smith", Case.INSENSITIVE)
    .lessThan(PersonFields.BIRTHDAY, new Date())
    .findAllSorted(PersonFields.BIRTHDAY, Sort.ASCENDING);
    
//Link queries also work too
 
RealmResults<Person> peopleWithHeavyPets = RealmTypeSafeQuery.with(realm).where(Person.class)
    .greaterThan(PersonFields.PETS.link(PetFields.WEIGHT), 9000).findAll();
```

#### Bonus
 
```java
// For chainable sorting 
RealmTypeSafeQuery.with(realm).where(model).sort(field1).sort(field3).sort(field2).findAll();

// For creating query groups with lambdas
RealmTypeSafeQuery.with(realm).where(model).with(realm).where(model).group((query) -> {}).findAll();
RealmTypeSafeQuery.with(realm).where(model).or((query) -> {}).findAll();
  
// For those pesky CSV fields that have a delimiter
RealmTypeSafeQuery.with(realm).where(model).contains(field, value, delemiter).findAll();  
```

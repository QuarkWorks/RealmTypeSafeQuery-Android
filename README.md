# RealmTypeSafeQuery-Android
[![](https://jitpack.io/v/QuarkWorks/RealmTypeSafeQuery-Android.svg)](https://jitpack.io/#QuarkWorks/RealmTypeSafeQuery-Android)


### A type safe way to handle realm queries in Android.
Supports Realm query API 110% (there are some bonus features too 😉)

There are two big issues when working with Realm Queries.
1) If a field name is spelled incorrectly it fails at runtime instead of compile time.
2) If an argument is an incorrect type (Date instead of String) then the query fails at runtime instead of compile time.

If you have typesafe query parameters that allows you to refactor your models without worry of breaking your queries.
All the field descriptors are auto generated using annotation process so you can't make a mistake. 
If you do, then it is caught at compile time and your app won't compile until you fix it.

### Here is some java code highlighting what the API looks like
```java
// Bad, field name and type are checked at runtime. This is using Realm the default way.
realm.where(Person.class).equalTo("firstName", "Sally").findFirst();

// Better, field name is checked at compile time, but type is still at runtime.
realm.where(Person.class).equalTo(PersonFieldNames.FIRST_NAME, "Sally").findFirst();

// Best, field name and type are both check at compile type.
RealmTypeSafeQuery.with(realm).where(Person.class).equalTo(PersonFields.FIRST_NAME, "Sally").findFirst();
```

## How to include

#### In your top level build file, add the jitpack repository along with realm
```groovy
buildscript {
    dependencies {
            classpath "io.realm:realm-gradle-plugin:4.3.4" // supported version of realm
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        maven { url "https://jitpack.io" } // RTSQ is hosted on jitpack
    }
}
```

#### App module build file dependencies:
```groovy
apply plugin: 'realm-android' // realm setup at top of file
android { 
    ...[elided]...
    // requires java 8
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    ...[elided]...
}

dependencies {
    ...[elided]...
    compileOnly "com.github.quarkworks.RealmTypeSafeQuery-Android:annotations:$RTSQ_version" // annotations
    annotationProcessor "com.github.quarkworks.RealmTypeSafeQuery-Android:annotationprocessor:$RTSQ_version" // annotation processor
    implementation "com.github.quarkworks.RealmTypeSafeQuery-Android:query:$RTSQ_version"  // query dsl
    ...[elided]...
}
```

#### Example Model
```java
@GenerateRealmFields // Generates a file called PersonFields.java. This is a RTSQ annotation.
@GenerateRealmFieldNames // Generates a file called PersonFieldNames.java This is a RTSQ annotation.
class Person extends RealmObject {
    String firstName;
    String lastName;
    Date birthday;
    
    RealmList<Pet> pets;
    
    // If what pops out of the code generator doesn't compile add these annotations.
    // Realm constantly updates their api and RTSQ might be a little behind.
    @SkipGenerationOfRealmFieldNames
    @SkipGenerationOfRealmField  
    RealmList<String> website;
}

@GenerateRealmFields // Generates a file called PetFields.java.
@GenerateRealmFieldName // Generates a file called PetFieldNames.java.
class Pet extends RealmObject {
    String name;
    Integer weight;
}
```

#### Example Queries

```java

final Realm realm = ...

RealmResults<Person> sallyNotSmiths = RealmTypeSafeQuery.with(realm).where(Person.class)
    .equalTo(PersonFields.FIRST_NAME, "Sally")
    .notEqualTo(PersonFields.LAST_NAME, "Smith", Case.INSENSITIVE)
    .lessThan(PersonFields.BIRTHDAY, new Date())
    .findAllSorted(PersonFields.BIRTHDAY, Sort.ASCENDING);
    
//Link queries also work too
 
RealmResults<Person> peopleWithHeavyPets = RealmTypeSafeQuery.with(realm).where(Person.class)
    .greaterThan(PersonFields.PETS.link(PetFields.WEIGHT), 9000).findAll();
```

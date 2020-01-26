# No exclamations`!!`
This is an Android lint rule that forbids the use of the `!!` operator in Kotlin.

[ ![Download](https://api.bintray.com/packages/dananas/android/no-exclamations/images/download.svg) ](https://bintray.com/dananas/android/no-exclamations/_latestVersion)
## How to use it
Import with gradle:
```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/dananas/android" 
    }
}

dependencies {
    lintChecks "com.github.dananas:no-exclamations:0.1.0"
}
```
The code won't compile if `abortOnError` is set to `true`.
```groovy
android {
    lintOptions {
        abortOnError true
    }
}
```

## Why `!!` is bad
Unlike Java, Kotlin natively supports nullable types.
By using `!!` not only you throw away this functionality, your code becomes error prone and sometimes non-obvious, because one needs to know the whole lifecycle of the instances `!!` was used for.

Consider using `TheGood` way:
```kotlin
class TheGood {
    private var name: String? = null
    fun setup() {
        name = "Clint Eastwood"
    }
    override fun toString(): String {
        return requireNotNull(name) { "Name is not set!" }
    }
}
```
```kotlin
class TheBad {
    private var name: String? = null
    fun setup() {
        name = "Lee Van Cleef"
    }
    override fun toString(): String {
        return name!!
    }
}
```
```kotlin
class TheUgly {
    private lateinit var name: String
    fun setup() {
        name = "Eli Wallach"
    }
    override fun toString(): String {
        return name
    }
}
```
## License
Apache-2.0
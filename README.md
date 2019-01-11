# Cost Extractor

A simple utility that extracts numbers from text

`public List<BigDecimal> extract(String text)`

Please see `NumberExtractorTest` for examples.

## To build

`./gradlew build`

## To use in your project

### Gradle

Add the following to your build file:

```
repositories {

    maven {
        url  "https://dl.bintray.com/adambirse/cost-extractor"
    }
}
dependencies {
    compile 'com.birse:cost-extractor:1.0'
}

```
### To Upload to bintray

` ./gradlew build bintrayUpload -Dbintray.user=<YOUR_USER_NAME> -Dbintray.key=<YOUR_API_KEY>`

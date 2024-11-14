# UploadThing Kotlin SDK
An implementation of the uploadthing.com REST API in Kotlin

### How it works
Learn the features and usage on our [Wiki](../../wiki)

### Repository
Maven
```xml
<repositories>
  <repository>
    <id>playranked-public</id>
    <url>https://nexus.playranked.net/repository/public/</url>
  </repository>
</repositories>

<dependency>
  <groupId>net.playranked.library</groupId>
  <artifactId>ut-kotlin-sdk</artifactId>
  <version>1.0</version>
</dependency>  
```
Gradle
```groovy
maven {
    url = "https://nexus.playranked.net/repository/public/"
}

implementation 'net.playranked.library:ut-kotlin-sdk:1.0'
```

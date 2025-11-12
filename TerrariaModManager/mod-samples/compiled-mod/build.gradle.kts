plugins {
    kotlin("jvm") version "1.9.10"
}

repositories {
    mavenCentral()
}

dependencies {
    // no android deps here; keep to pure JVM
}

tasks.register<Jar>("jar") {
    archiveBaseName.set("samplemod")
    from(sourceSets["main"].output)
}

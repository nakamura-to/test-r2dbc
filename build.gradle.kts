plugins {
    kotlin("jvm")
    id("com.diffplug.spotless") version "5.12.5"
}

allprojects {
    apply(plugin = "com.diffplug.spotless")

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlinGradle {
            ktlint("0.41.0")
        }
    }

    tasks {
        build {
            dependsOn(spotlessApply)
        }
    }

    repositories {
        mavenCentral()
    }
}

val javaProjects = subprojects.filter {
    it.name.startsWith("java")
}

val kotlinProjects = subprojects.filter {
    it.name.startsWith("kotlin")
}

configure(javaProjects) {
    apply(plugin = "java")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
        }
    }
}

configure(kotlinProjects) {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            ktlint("0.41.0")
        }
    }
}

configure(javaProjects + kotlinProjects) {

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.7.2")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    }
}

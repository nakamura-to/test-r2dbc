pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

rootProject.name = "test-r2dbc"
include("java-h2")
include("java-mysql")
include("java-postgresql")

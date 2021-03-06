plugins {
    java
}

version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:Dysprosium-SR20"))
    implementation("io.projectreactor:reactor-core")
    implementation("io.r2dbc:r2dbc-spi:0.8.5.RELEASE")
    runtimeOnly("io.r2dbc:r2dbc-postgresql:0.8.8.RELEASE")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
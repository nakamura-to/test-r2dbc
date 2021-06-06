plugins {
    java
}

version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:Dysprosium-SR7"))
    implementation("io.projectreactor:reactor-core")
    implementation("io.r2dbc:r2dbc-spi:0.8.2.RELEASE")
    runtimeOnly("io.r2dbc:r2dbc-h2:0.8.4.RELEASE")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
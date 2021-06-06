plugins {
    java
}

version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:Dysprosium-SR8"))
    implementation("io.projectreactor:reactor-core")
    implementation("io.r2dbc:r2dbc-spi:0.8.2.RELEASE")
    runtimeOnly("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
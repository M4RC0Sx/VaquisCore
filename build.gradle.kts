plugins {
    java
    kotlin("jvm") version "1.7.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "vaquiscore"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven {
        name = "paper"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "aikar"
        url = uri("https://repo.aikar.co/content/groups/aikar/")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core-jvm", version = "1.6.4")
    implementation(group = "io.papermc.paper", name = "paper-api", version = "1.19.3-R0.1-SNAPSHOT")
    implementation(group = "net.dv8tion", name = "JDA", version = "5.0.0-beta.2")
    implementation(group = "co.aikar", name = "acf-paper", version = "0.5.1-SNAPSHOT")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
        options.isFork = true
        options.forkOptions.executable = "javac"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.javaParameters = true
    }

    val fatJar by named("shadowJar", com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
        dependencies {
            // Kotlin
            include(dependency("org.jetbrains.kotlin:.*"))
            include(dependency("org.jetbrains.kotlinx:.*"))

            // JDA
            include(dependency("net.dv8tion:.*"))
            include(dependency("com.neovisionaries:nv-websocket-client"))
            include(dependency("com.squareup.okhttp3:okhttp"))
            include(dependency("com.squareup.okio:okio"))
            include(dependency("org.apache.commons:commons-collections4"))
            include(dependency("net.sf.trove4j:trove4j"))
            include(dependency("com.fasterxml.jackson.core:jackson-databind"))
            include(dependency("com.fasterxml.jackson.core:jackson-core"))
            include(dependency("com.fasterxml.jackson.core:jackson-annotations"))
            include(dependency("org.slf4j:slf4j-api"))

            // Aikar Commands
            include(dependency("co.aikar:.*"))
        }

        // Kotlin
        relocate("kotlin", "vaquiscore.shaded.kotlin")
        relocate("org.jetbrains", "vaquiscore.shaded.org.jetbrains")

        // JDA
        relocate("net.dv8tion", "vaquiscore.shaded.net.dv8tion")
        relocate("com.neovisionaries", "vaquiscore.shaded.com.neovisionaries")
        relocate("gnu.trove", "vaquiscore.shaded.gnu.trove")
        relocate("org.apache.commons.collections4", "vaquiscore.shaded.org.apache.commons.collections4")
        relocate("com.squareup.okhttp3", "vaquiscore.shaded.com.squareup.okhttp3")
        relocate("com.squareup.okio", "vaquiscore.shaded.com.squareup.okio")
        relocate("com.fasterxml.jackson", "vaquiscore.shaded.com.fasterxml.jackson")
        relocate("org.slf4j", "vaquiscore.shaded.org.slf4j")

        // Aikar Commands
        relocate("co.aikar", "vaquiscore.shaded.co.aikar")

        minimize()
    }

    artifacts {
        add("archives", fatJar)
    }
}

(tasks.getByName("processResources") as ProcessResources).apply {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from("src/main/resources") {
        include("**/*.yml")
        filter<org.apache.tools.ant.filters.ReplaceTokens>("tokens" to mapOf("VERSION" to project.version))
    }
    filesMatching("application.properties") {
        expand(project.properties)
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
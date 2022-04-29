plugins {
    java
    id("maven-publish")
    id("fabric-loom") version "0.11-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
}

group = ModProperties.mavenGroup
version = ModProperties.modVersion

base {
    archivesName.set(ModProperties.archivesBaseName)
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:${FabricProperties.minecraftVersion}")
    mappings("net.fabricmc:yarn:${FabricProperties.yarnMappings}:v2")

    modImplementation("net.fabricmc:fabric-loader:${FabricProperties.loaderVersion}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Dependencies.fabricVersion}")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }

    withType<JavaCompile> {
        configureEach {
            options.release.set(17)
        }
    }

    jar {
        from("LICENSE") {
            rename {
                "${it}_${ModProperties.archivesBaseName}"
            }
        }
    }

}

publishing {
    publications {
        val mavenJava by creating(MavenPublication::class) {
            from(components["java"])
        }
    }

    repositories {

    }
}

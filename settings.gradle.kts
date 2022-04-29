rootProject.name = "fabric-mod-example"

pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.fabricmc.net/")
            name = "Fabric"
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
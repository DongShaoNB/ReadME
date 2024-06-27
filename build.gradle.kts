plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "cc.dsnb"
version = "1.0.0"

repositories {
    mavenCentral()
    // SpigotAPI
    maven {
        name = "spigot-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    // SpigotAPI
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    // UniversalScheduler
    maven {
        name = "jitpack.io"
        url = uri("https://jitpack.io")
    }
    // AuthMe
    maven {
        name = "codemc-repo"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    // PlaceholderAPI
    maven {
        name = "placeholderapi"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    testImplementation(kotlin("test"))
    // SpigotAPI
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
    // XSeries - XMaterial
    implementation("com.github.cryptomorin:XSeries:11.2.0")
    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.2")
    // DazzleConf
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:1.3.0-M2")
    // UniversalScheduler
    implementation("com.github.Anon8281:UniversalScheduler:0.1.6")
    // Adventure MiniMessage
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    // Adventure Platform - Bukkit
    implementation("net.kyori:adventure-platform-bukkit:4.3.3")
    // AuthMe
    compileOnly("fr.xephi:authme:5.6.0-SNAPSHOT")
    // PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.11.6")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
tasks.processResources {
    doLast {
        val pluginFile = project.projectDir.resolve("build/resources/main/plugin.yml")
        val pluginContent = pluginFile.readText()
        val outputContent = pluginContent.replace("\${pluginVersion}", version.toString())
        pluginFile.writeText(outputContent)
    }
}
tasks.shadowJar {
    // XSeries - XMaterial
    relocate("com.cryptomorin.xseries", "cc.dsnb.readme.xseries")
    // bStats
    relocate("org.bstats", "cc.dsnb.readme.bstats")
    // DazzleConf
    relocate("space.arim.dazzleconf", "cc.dsnb.readme.dazzleconf")
    // UniversalScheduler
    relocate("com.github.Anon8281.universalScheduler", "cc.dsnb.readme.universalScheduler")
}
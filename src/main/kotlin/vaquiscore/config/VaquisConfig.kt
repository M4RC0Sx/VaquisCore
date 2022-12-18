package vaquiscore.config

import org.bukkit.configuration.ConfigurationSection


interface VaquisConfig {
    val section: ConfigurationSection

    fun <T> value(path: String) = section[path] as T
    fun <F, T> value(path: String, transform: (F) -> T) = transform(section[path] as F)

    fun stringList(path: String): List<String> {
        return section.getStringList(path)
    }

    fun multilineString(path: String): String {
        val value = section[path]

        if (value is String) {
            return value
        } else if (value is List<*>) {
            return stringList(path).joinToString(separator = "\n") { it.ifEmpty { " " } }
        }

        return ""
    }
}

data class VaquisConfigMain(override val section: ConfigurationSection) : VaquisConfig {
    val configVersion: Int = value("config-version")
    val debug: Boolean = value("debug")

    val discord by lazy { VaquisConfigDiscord(section.getConfigurationSection("discord")!!) }
}

data class VaquisConfigDiscord(override val section: ConfigurationSection) : VaquisConfig {
    val token: String = value("token")
    val guildId: String = value("guild-id")
    val msgPrefix: String = value("msg-prefix")
    val activityType: String = value("activity-type")
    val activityText: String = value("activity-text")
    val channelInfoId: String = value("channel-info-id")
}
package vaquiscore.extensions

import org.bukkit.ChatColor

internal fun String.color(): String = ChatColor.translateAlternateColorCodes('&', this)
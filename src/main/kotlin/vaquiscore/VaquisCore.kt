package vaquiscore

import co.aikar.commands.PaperCommandManager
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import vaquiscore.command.VaquisCommand
import vaquiscore.config.VaquisConfigMain
import vaquiscore.config.PrettyConfig
import vaquiscore.discord.VaquisDiscord
import vaquiscore.listener.PlayerJoinListener
import vaquiscore.listener.PlayerQuitListener
import java.io.File

class VaquisCore : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: VaquisCore
    }
    private var conf: PrettyConfig? = null

    lateinit var vaquisConfig: VaquisConfigMain
    lateinit var vaquisDiscord: VaquisDiscord

    private lateinit var commandManager: PaperCommandManager

    val playerJoinTimesMap = mutableMapOf<String, Long>()


    override fun onEnable() {
        INSTANCE = this
        logger.info("Enabling VaquisCore plugin v${description.version} by ${description.authors}")

        // Load config
        saveDefaultConfig()
        reloadConfig()

        // Load Discord bot
        vaquisDiscord = VaquisDiscord.INSTANCE
        try {
            vaquisDiscord.start()
        } catch (e: Exception) {
            logger.severe("Failed to start Discord bot: ${e.message}")
        }

        // Load listeners
        registerEvents()

        // Load commands
        commandManager = PaperCommandManager(this)
        registerCommands()
    }

    override fun onDisable() {
        logger.info("Disabling VaquisCore plugin...")
        vaquisDiscord.stop()
    }

    override fun getConfig(): FileConfiguration {
        if (conf == null)
            reloadConfig()

        return conf!!
    }

    override fun reloadConfig() {
        conf = PrettyConfig(File(dataFolder, "config.yml"))
        vaquisConfig = VaquisConfigMain(conf!!)
    }

    fun reloadPlugin() {
        onDisable()

        saveDefaultConfig()
        reloadConfig()
        vaquisDiscord.start()
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(PlayerJoinListener(), this)
        server.pluginManager.registerEvents(PlayerQuitListener(), this)
    }

    private fun registerCommands() {
        commandManager.registerCommand(VaquisCommand())
    }
}
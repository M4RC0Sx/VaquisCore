package vaquiscore.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Dependency
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import vaquiscore.VaquisCore
import vaquiscore.extensions.color

@CommandAlias("vaquis|vaquiscore|vc")
@Description("VaquisCore plugin base command")
class VaquisCommand : BaseCommand() {

    @Dependency
    private val vaquisCore = VaquisCore.INSTANCE

    @Default
    @Subcommand("version|ver|v")
    @Description("Shows the plugin version")
    fun onVersion() {
        currentCommandIssuer.sendMessage("&3&lVaquis&b&lCore &8&l> &7Using plugin version &e${vaquisCore.description.version}&7.".color())
    }

    @Subcommand("reload|rl|r")
    @Description("Reloads the plugin")
    @CommandPermission("vaquiscore.reload")
    fun onReload() {
        vaquisCore.reloadPlugin()
        currentCommandIssuer.sendMessage("&3&lVaquis&b&lCore &8&l> &aVaquisCore plugin reloaded!".color())
    }

}
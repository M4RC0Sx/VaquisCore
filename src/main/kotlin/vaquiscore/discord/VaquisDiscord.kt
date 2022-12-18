package vaquiscore.discord

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import vaquiscore.VaquisCore

class VaquisDiscord {
    companion object {
        val INSTANCE = VaquisDiscord()
    }

    private var vaquisCore: VaquisCore = VaquisCore.INSTANCE

    private lateinit var token: String
    private lateinit var guildId: String
    private lateinit var msgPrefix: String
    private lateinit var channelInfoId: String
    private var jda: JDA? = null

    fun start() {
        token = vaquisCore.vaquisConfig.discord.token
        guildId = vaquisCore.vaquisConfig.discord.guildId
        msgPrefix = vaquisCore.vaquisConfig.discord.msgPrefix
        channelInfoId = vaquisCore.vaquisConfig.discord.channelInfoId
        val builder: JDABuilder = JDABuilder.create(token,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_PRESENCES)

        builder.setActivity(Activity.of(Activity.ActivityType.valueOf(vaquisCore.vaquisConfig.discord.activityType), vaquisCore.vaquisConfig.discord.activityText))
        jda = builder.build()
    }

    fun stop() {
        jda?.shutdown()
        jda = null
    }

    fun sendInfoMessage(msg: String) {
        if (jda == null) return

        jda!!.getGuildById(guildId)?.getTextChannelById(channelInfoId)?.sendMessage("$msgPrefix $msg")?.queue()
    }

}
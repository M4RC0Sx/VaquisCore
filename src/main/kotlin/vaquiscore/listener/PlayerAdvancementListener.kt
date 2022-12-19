package vaquiscore.listener

import net.dv8tion.jda.api.EmbedBuilder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import vaquiscore.VaquisCore
import java.awt.Color
import java.time.Instant

class PlayerAdvancementListener : Listener {

    @EventHandler
    fun onPlayerAdvancement(event: PlayerAdvancementDoneEvent) {

        val advancement = event.advancement;
        // Check if story advancement
        if ("story" !in advancement.key.key) return

        val player = event.player;

        val vaquisDiscord = VaquisCore.INSTANCE.vaquisDiscord;

        val eb = EmbedBuilder()
        eb.setAuthor("VaquisCore - Logros", null, vaquisDiscord.jda?.selfUser?.avatarUrl)
        eb.setTitle("¡Un jugador ha conseguido un logro!")
        eb.addField("Jugador", player.name, false)
        eb.addField("Logro", advancement.key.key, false)
        eb.setFooter("VaquisCore - Bot", vaquisDiscord.jda?.selfUser?.avatarUrl)
        eb.setThumbnail("https://minotar.net/avatar/${player.name}/100.png")
        eb.setColor(Color.YELLOW)
        eb.setTimestamp(Instant.now())

        vaquisDiscord.sendInfoMessage(eb.build())
    }

}
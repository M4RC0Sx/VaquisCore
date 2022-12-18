package vaquiscore.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import vaquiscore.VaquisCore

class PlayerQuitListener : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val timeJoin = VaquisCore.INSTANCE.playerJoinTimesMap[event.player.name]
        // Get hour, minutes and seconds played
        val timePlayed = (System.currentTimeMillis() - timeJoin!!) / 1000
        val hours = timePlayed / 3600
        val minutes = (timePlayed % 3600) / 60
        val seconds = timePlayed % 60
        // Send message to Discord with 0 padded values
        VaquisCore.INSTANCE.vaquisDiscord.sendInfoMessage("La pedazo de vaca **${event.player.name}** se ha desconectado después de viciar durante **${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}**.")

        // Remove player from map
        VaquisCore.INSTANCE.playerJoinTimesMap.remove(event.player.name)
    }

}
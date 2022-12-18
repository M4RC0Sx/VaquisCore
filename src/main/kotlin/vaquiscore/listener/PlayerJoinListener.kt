package vaquiscore.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import vaquiscore.VaquisCore

class PlayerJoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        VaquisCore.INSTANCE.playerJoinTimesMap[event.player.name] = System.currentTimeMillis()
    }

}
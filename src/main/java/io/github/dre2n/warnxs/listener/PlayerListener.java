/*
 * Copyright (C) 2016 Daniel Saukel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.dre2n.warnxs.listener;

import io.github.dre2n.warnxs.WarnXS;
import io.github.dre2n.warnxs.player.WPlayer;
import io.github.dre2n.warnxs.player.WPlayers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Daniel Saukel
 */
public class PlayerListener implements Listener {

    WarnXS plugin;
    WPlayers wPlayers;

    public PlayerListener(WarnXS plugin) {
        this.plugin = plugin;
        wPlayers = plugin.getWPlayers();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WPlayer wPlayer = new WPlayer(player);
        wPlayers.addPlayer(wPlayer);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        WPlayer wPlayer = wPlayers.getByPlayer(event.getPlayer());
        wPlayers.removePlayer(wPlayer);
    }

}

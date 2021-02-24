/*
 * Copyright (C) 2016-2021 Daniel Saukel
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
package de.erethon.warnxs.listener;

import de.erethon.warnxs.WarnXS;
import de.erethon.warnxs.config.WMessage;
import de.erethon.warnxs.player.WPlayer;
import de.erethon.warnxs.player.WPlayers;
import de.erethon.warnxs.player.WReason;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
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
        wPlayer.sendNewWarnings();
        wPlayers.addPlayer(wPlayer);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        WPlayer wPlayer = wPlayers.getByPlayer(event.getPlayer());
        wPlayers.removePlayer(wPlayer);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if(event.getResult() != PlayerLoginEvent.Result.KICK_BANNED) {
            return;
        }
        String infoMessage = plugin.getWConfig().getInfoMessage().replace("&nl", "\n");
        String expirationDate = "";
        for (BanEntry entry : Bukkit.getBanList(BanList.Type.NAME).getBanEntries()) {
            if (entry.getTarget().equals(player.getName())) {
                if (entry.getExpiration() == null) {
                    expirationDate = WMessage.BAN_INFO_UNLIMITED.getMessage();
                    break;
                }
                expirationDate = entry.getExpiration().toString();
                break;
            }
        }
        String expiration = WMessage.BAN_INFO_DURATION.getMessage(expirationDate);
        StringBuilder lastWarnMessages = new StringBuilder();
        for (WReason reason : wPlayers.getByName(player.getName()).getData().getReasons()) {
            lastWarnMessages.append(WMessage.BAN_INFO_ENTRY.getMessage(reason.getCase(), String.valueOf(reason.getPenaltyPoints()))).append("\n");
        }
        String kickMessage = infoMessage + "\n" + expiration + "\n" + WMessage.BAN_INFO_WARNINGS.getMessage() + "\n" + lastWarnMessages;
        event.setKickMessage(kickMessage);

    }

}

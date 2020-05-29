/*
 * Copyright (C) 2016-2020 Daniel Saukel
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
package de.erethon.warnxs.player;

import de.erethon.warnxs.WarnXS;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * WPlayer instance manager.
 *
 * @author Daniel Saukel
 */
public class WPlayers {

    private CopyOnWriteArrayList<WPlayer> wPlayers = new CopyOnWriteArrayList<>();

    public WPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            wPlayers.add(new WPlayer(player));
        }
    }

    /* Getters and setters */
    /**
     * @return
     * the WPlayer which represents the player
     */
    public WPlayer getByPlayer(Player player) {
        for (WPlayer wPlayer : wPlayers) {
            if (wPlayer.getName().equals(player.getName())) {
                return wPlayer;
            }
        }
        return null;
    }

    /**
     * @return
     * the WPlayer which represents the player
     * Creates a new WPlayer object if data exists!
     */
    public WPlayer getByName(String name) {
        for (WPlayer wPlayer : wPlayers) {
            if (wPlayer.getName().equalsIgnoreCase(name)) {
                return wPlayer;
            }
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        File file = new File(WarnXS.PLAYERS, player.getUniqueId().toString() + ".yml");
        if (file.exists()) {
            return new WPlayer(file);
        } else {
            return null;
        }
    }

    /**
     * @param player
     * an instance of WPlayer to add
     */
    public void addPlayer(WPlayer player) {
        for (WPlayer wPlayer : wPlayers) {
            if (wPlayer.getName().equals(player.getName())) {
                wPlayers.remove(wPlayer);
            }
        }

        wPlayers.add(player);
    }

    /**
     * @param player
     * an instance of WPlayer to remove
     */
    public void removePlayer(WPlayer player) {
        wPlayers.remove(player);
        WPlayerData data = player.getData();
        data.setLastName(player.getName());
        data.setTimeLastPlayed(System.currentTimeMillis());
        data.serialize();
    }

    /* Persistence */
    /**
     * Saves all players
     */
    public void saveAll() {
        for (WPlayer wPlayer : wPlayers) {
            wPlayer.getData().serialize();
        }
    }

    /**
     * Loads all players
     */
    public void loadAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            new WPlayer(player);
        }
    }

}

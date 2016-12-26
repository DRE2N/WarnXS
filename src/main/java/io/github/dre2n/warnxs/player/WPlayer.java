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
package io.github.dre2n.warnxs.player;

import io.github.dre2n.warnxs.WarnXS;
import java.io.File;
import java.util.Date;
import org.bukkit.entity.Player;

/**
 * Represents a player.
 *
 * @author Daniel Saukel
 */
public class WPlayer {

    WarnXS plugin = WarnXS.getInstance();

    private Player player;
    private WPlayerData data;

    public WPlayer(Player player) {
        this.player = player;
        data = new WPlayerData(new File(WarnXS.PLAYERS, player.getUniqueId().toString() + ".yml"));
        data.setLastName(player.getName());
    }

    public WPlayer(File file) {
        data = new WPlayerData(file);
    }

    /* Getters and setters */
    /**
     * @return
     * the player's name
     */
    public String getName() {
        return data.getLastName();
    }

    /**
     * @return
     * the Bukkit player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return
     * the saved data
     */
    public WPlayerData getData() {
        return data;
    }

    /**
     * @return
     * the amount of penalty points
     */
    public int getPenaltyPoints() {
        int pps = 0;
        for (WReason reason : data.getReasons()) {
            int rpps = reason.getPenaltyPoints();
            long time = reason.getDate().getTime();
            int i = 1;
            while (time + plugin.getWConfig().getRemoveTime() * i <= System.currentTimeMillis()) {
                if (rpps > 0) {
                    rpps--;
                }
                i++;
            }
            pps += rpps;
        }
        return pps;
    }

    /**
     * @param reason
     * the reason for the warning
     * @param amount
     * the amount of penalty points
     * @param mod
     * the player that warns this player
     */
    public void warn(String reason, int amount, Player mod) {
        data.getReasons().add(new WReason(reason, new Date(), amount, mod.getUniqueId()));
        data.save();
    }

}

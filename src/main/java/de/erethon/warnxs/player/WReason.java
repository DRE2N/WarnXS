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

import java.util.Date;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Daniel Saukel
 */
public class WReason {

    private String rCase;
    private Date date;
    private int pps;
    private UUID mod;
    private boolean seen;

    WReason(String rCase, Date date, int pps, UUID mod, boolean seen) {
        this.rCase = rCase;
        this.date = date;
        this.pps = pps;
        this.mod = mod;
        this.seen = seen;
    }

    WReason(ConfigurationSection config) {
        rCase = config.getString("case");
        date = new Date(config.getLong("date"));
        pps = config.getInt("pps");
        mod = UUID.fromString(config.getString("mod"));
        seen = config.getBoolean("seen", true);
    }

    /**
     * @return
     * the reason case
     */
    public String getCase() {
        return rCase;
    }

    /**
     * @return
     * the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return
     * the amount of penalty points
     */
    public int getPenaltyPoints() {
        return pps;
    }

    /**
     * @return
     * the player who warned this one
     */
    public OfflinePlayer getMod() {
        return Bukkit.getOfflinePlayer(mod);
    }

    /**
     * @return
     * if the warned player has already received the PP notification
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * @param seen
     * set if the player has already received the PP notification
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    ConfigurationSection serialize() {
        ConfigurationSection config = new YamlConfiguration();
        config.set("case", rCase);
        config.set("date", date.getTime());
        config.set("pps", pps);
        config.set("mod", mod.toString());
        config.set("seen", seen);
        return config;
    }

}

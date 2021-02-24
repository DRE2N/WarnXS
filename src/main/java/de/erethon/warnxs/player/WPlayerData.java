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
package de.erethon.warnxs.player;

import de.erethon.commons.config.DREConfig;
import de.erethon.commons.player.PlayerUtil;
import de.erethon.warnxs.WarnXS;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Daniel Saukel
 */
public class WPlayerData extends DREConfig {

    WarnXS plugin = WarnXS.getInstance();

    public static final int CONFIG_VERSION = 2;

    private String lastName;
    private long timeLastPlayed;
    private long timeLastWarned;
    private int pointsLastWarned;
    private List<WReason> reasons = new ArrayList<>();

    public WPlayerData(File file) {
        super(file, CONFIG_VERSION);
        load();
    }

    /* Getters and setters */
    /**
     * @return
     * the name the player had when he was online for the last time
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param name
     * the name to set
     */
    public void setLastName(String name) {
        lastName = name;
    }

    /**
     * @return
     * the last time when the player played
     */
    public long getTimeLastPlayed() {
        return timeLastPlayed;
    }

    /**
     * @param time
     * the last time when the player played to set
     */
    public void setTimeLastPlayed(long time) {
        timeLastPlayed = time;
    }

    /**
     * @return
     * the last time when the player was warned
     */
    public long getTimeLastWarned() {
        return timeLastWarned;
    }

    /**
     * @return
     * the amount of penalty points when the player was warned the latest time
     */
    public int getPointsLastWarned() {
        return pointsLastWarned;
    }

    /**
     * @param pp
     * the new amount of penalty points to set
     */
    public void updatePoints(int pp) {
        pointsLastWarned = pp;
        timeLastWarned = System.currentTimeMillis();
    }

    /**
     * @return
     * the warn reasons
     */
    public List<WReason> getReasons() {
        return reasons;
    }

    /* Serialization */
    @Override
    public void load() {
        lastName = config.getString("lastName", PlayerUtil.getNameFromUniqueId(getFile().getName().replace(".yml", "")));
        timeLastPlayed = config.getLong("timeLastPlayed", timeLastPlayed);
        timeLastWarned = config.getLong("timeLastWarned", timeLastWarned);
        pointsLastWarned = config.getInt("pointsLastWarned", pointsLastWarned);
        if (config.contains("reasons")) {
            for (String key : config.getConfigurationSection("reasons").getKeys(false)) {
                reasons.add(new WReason(config.getConfigurationSection("reasons." + key)));
            }
        }
    }

    public void serialize() {
        config.set("lastName", lastName);
        config.set("timeLastPlayed", timeLastPlayed);
        config.set("timeLastWarned", timeLastWarned);
        config.set("pointsLastWarned", pointsLastWarned);
        config.set("reasons", serializeReasons());
        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    ConfigurationSection serializeReasons() {
        ConfigurationSection config = new YamlConfiguration();
        if (reasons == null) {
            reasons = new ArrayList<>();
        }
        int i = 0;
        for (WReason reason : reasons) {
            config.set(String.valueOf(i), reason.serialize());
            i++;
        }
        return config;
    }

}

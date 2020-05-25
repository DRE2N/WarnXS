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

import io.github.dre2n.commons.chat.MessageUtil;
import io.github.dre2n.commons.config.DREConfig;
import io.github.dre2n.warnxs.WarnXS;
import io.github.dre2n.warnxs.config.WMessages;
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

    public static final int CONFIG_VERSION = 1;

    private String lastName;
    private long timeLastPlayed;
    private List<WReason> reasons = new ArrayList<>();

    public WPlayerData(File file) {
        super(file, CONFIG_VERSION);

        if (initialize) {
            initialize();
        }
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
     * the warn reasons
     */
    public List<WReason> getReasons() {
        return reasons;
    }

    /* Serialization */
    @Override
    public void initialize() {
        if (!file.exists()) {
            try {
                file.createNewFile();
                MessageUtil.log(plugin, WMessages.LOG_NEW_PLAYER_DATA.getMessage(file.getName()));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        save();
    }

    @Override
    public void load() {
        if (config.contains("lastName")) {
            lastName = config.getString("lastName");
        }
        if (config.contains("timeLastPlayed")) {
            timeLastPlayed = config.getLong("timeLastPlayed");
        }
        if (config.contains("reasons")) {
            for (String key : config.getConfigurationSection("reasons").getKeys(false)) {
                reasons.add(new WReason(config.getConfigurationSection("reasons." + key)));
            }
        }
    }

    @Override
    public void save() {
        config.set("lastName", lastName);
        config.set("timeLastPlayed", timeLastPlayed);
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

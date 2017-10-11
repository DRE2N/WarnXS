/*
 * Copyright (C) 2016-2017 Daniel Saukel
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
package io.github.dre2n.warnxs.config;

import io.github.dre2n.commons.config.DREConfig;
import io.github.dre2n.commons.misc.NumberUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the main config.yml.
 *
 * @author Daniel Saukel
 */
public class WConfig extends DREConfig {

    public static final long HOUR = 1000 * 60 * 60;

    public static final int CONFIG_VERSION = 1;

    private String language = "english";
    private Map<Integer, String> commands = new HashMap<>();
    private String deathPenalty = "";
    private long removeTime = HOUR * 24 * 7;

    public WConfig(File file) {
        super(file, CONFIG_VERSION);

        if (initialize) {
            initialize();
        }
        load();
    }

    /* Getters and setters */
    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return
     * a map of penalty points and command
     */
    public Map<Integer, String> getCommands() {
        return commands;
    }

    /**
     * @return
     * the penalty command if someone gets more PPs than the index covers
     */
    public String getDeathPenalty() {
        return deathPenalty;
    }

    /**
     * @return
     * the time until one penalty point gets removed
     */
    public long getRemoveTime() {
        return removeTime;
    }

    /* Actions */
    @Override
    public void initialize() {
        if (!config.contains("language")) {
            config.set("language", language);
        }

        if (!config.contains("commands")) {
            config.createSection("commands");
        }

        if (!config.contains("deathPenalty")) {
            config.set("deathPenalty", deathPenalty);
        }

        if (!config.contains("removeTime")) {
            config.set("removeTime", removeTime / HOUR);
        }

        save();
    }

    @Override
    public void load() {
        if (config.contains("language")) {
            language = config.getString("language");
        }

        if (config.contains("commands")) {
            for (String key : config.getConfigurationSection("commands").getKeys(false)) {
                try {
                    commands.put(NumberUtil.parseInt(key), config.getString("commands." + key));
                } catch (NullPointerException exception) {
                }
            }
        }

        if (config.contains("deathPenalty")) {
            deathPenalty = config.getString("deathPenalty");
        }

        if (config.contains("removeTime")) {
            removeTime = config.getInt("removeTime") * HOUR;
        }
    }

}

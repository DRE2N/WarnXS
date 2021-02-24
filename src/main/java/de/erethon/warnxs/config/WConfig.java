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
package de.erethon.warnxs.config;

import de.erethon.commons.chat.MessageUtil;
import de.erethon.commons.config.DREConfig;
import de.erethon.commons.javaplugin.DREPlugin;
import de.erethon.commons.misc.NumberUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the main config.yml.
 *
 * @author Daniel Saukel
 */
public class WConfig extends DREConfig {

    public static final long HOUR = 1000 * 60 * 60;

    public static final int CONFIG_VERSION = 2;

    private String language = "english";
    private Map<Integer, String[]> commands = new HashMap<>();
    private String deathPenalty = "";
    private String infoMessage = "Add your info message here!";
    private long removeTime = HOUR * 24 * 7;
    private Map<Integer, Long> removeTimeDelays = new HashMap<>();

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
    public Map<Integer, String[]> getCommands() {
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

    /**
     * @return
     * a map of penalty points and command
     */
    public Map<Integer, Long> getRemoveTimeDelays() {
        return removeTimeDelays;
    }

    /**
     * @return
     * the message displayed at the top of the info page a banned player gets
     */
    public String getInfoMessage() {
        return infoMessage;
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

        if (!config.contains("removeTimeDelays")) {
            config.createSection("removeTimeDelays");
        }
        if (!config.contains("infoMessage")) {
            config.set("infoMessage", infoMessage);
        }



        save();
    }

    @Override
    public void load() {
        language = config.getString("language", language);
        DREPlugin.getInstance().getMessageHandler().setDefaultLanguage(language);

        if (config.contains("commands")) {
            for (String key : config.getConfigurationSection("commands").getKeys(false)) {
                try {
                    Object cmdObject = config.get("commands." + key);
                    if (cmdObject instanceof String) {
                        commands.put(NumberUtil.parseInt(key), new String[]{(String) cmdObject});
                    } else if (cmdObject instanceof List) {
                        List<String> list = (List<String>) cmdObject;
                        commands.put(NumberUtil.parseInt(key), list.toArray(new String[list.size()]));
                    }
                } catch (Exception exception) {
                    MessageUtil.log(DREPlugin.getInstance(), "Invalid commands value: " + key);
                }
            }
        }

        deathPenalty = config.getString("deathPenalty", deathPenalty);

        if (config.contains("removeTime")) {
            removeTime = config.getInt("removeTime") * HOUR;
        }

        if (config.contains("removeTimeDelays")) {
            for (String key : config.getConfigurationSection("removeTimeDelays").getKeys(false)) {
                try {
                    removeTimeDelays.put(NumberUtil.parseInt(key), config.getInt("removeTimeDelays." + key) * HOUR);
                } catch (Exception exception) {
                    MessageUtil.log(DREPlugin.getInstance(), "Invalid removeTimeDelays value: " + key);
                }
            }
        }

        if (config.contains("infoMessage")) {
            infoMessage = config.getString("infoMessage");
        }
    }

}

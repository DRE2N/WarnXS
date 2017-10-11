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
package io.github.dre2n.warnxs;

import io.github.dre2n.commons.compatibility.Internals;
import io.github.dre2n.commons.config.MessageConfig;
import io.github.dre2n.commons.javaplugin.DREPlugin;
import io.github.dre2n.commons.javaplugin.DREPluginSettings;
import io.github.dre2n.commons.misc.FileUtil;
import io.github.dre2n.warnxs.command.WCommands;
import io.github.dre2n.warnxs.config.WConfig;
import io.github.dre2n.warnxs.config.WMessages;
import io.github.dre2n.warnxs.listener.PlayerListener;
import io.github.dre2n.warnxs.player.WPermissions;
import io.github.dre2n.warnxs.player.WPlayers;
import java.io.File;
import org.bukkit.event.HandlerList;

/**
 * The main class of WarnXS.
 * It contains several important instances and the actions when the plugin is enabled / disabled.
 *
 * @author Daniel Saukel
 */
public class WarnXS extends DREPlugin {

    private static WarnXS instance;

    public static File BACKUPS;
    public static File LANGUAGES;
    public static File PLAYERS;

    private WConfig wConfig;
    private MessageConfig messageConfig;
    private WCommands wCommands;
    private WPlayers wPlayers;

    public WarnXS() {
        /*
         * ##########################
         * ####~BRPluginSettings~####
         * ##########################
         * #~Internals~##INDEPENDENT#
         * #~SpigotAPI~##~~~false~~~#
         * #~~~~UUID~~~##~~~~true~~~#
         * #~~Economy~~##~~~false~~~#
         * #Permissions##~~~false~~~#
         * #~~Metrics~~##~~~~true~~~#
         * #Resource ID##~~~48233~~~#
         * ##########################
         */

        settings = new DREPluginSettings(false, true, false, false, true, 48233, Internals.INDEPENDENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        WPermissions.register();
        initFolders();
        loadCore();

        manager.registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        saveData();

        HandlerList.unregisterAll(this);
        getServer().getScheduler().cancelTasks(this);
    }

    // Initialize
    public void initFolders() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        BACKUPS = new File(getDataFolder(), "backups");
        if (!BACKUPS.exists()) {
            BACKUPS.mkdir();
        }

        LANGUAGES = new File(getDataFolder(), "languages");
        if (!LANGUAGES.exists()) {
            LANGUAGES.mkdir();
        }

        PLAYERS = new File(getDataFolder(), "players");
        if (!PLAYERS.exists()) {
            PLAYERS.mkdir();
        }
    }

    public void loadCore() {
        // Load Language
        loadMessageConfig(new File(LANGUAGES, "english.yml"));
        // Load Config
        loadWConfig(new File(getDataFolder(), "config.yml"));
        // Load Language 2
        loadMessageConfig(new File(LANGUAGES, wConfig.getLanguage() + ".yml"));
        loadWPlayers();
        wPlayers.loadAll();
        loadWCommands();
    }

    public void saveData() {
        wPlayers.saveAll();
        messageConfig.save();
        File backupDir = new File(BACKUPS, String.valueOf(System.currentTimeMillis()));
        backupDir.mkdir();
        FileUtil.copyDirectory(PLAYERS, backupDir, new String[]{});
    }

    /* Getters and loaders */
    /**
     * @return the plugin instance
     */
    public static WarnXS getInstance() {
        return instance;
    }

    /**
     * @return the loaded instance of WConfig
     */
    public WConfig getWConfig() {
        return wConfig;
    }

    /**
     * load / reload a new instance of WConfig
     */
    public void loadWConfig(File file) {
        wConfig = new WConfig(file);
    }

    /**
     * @return the loaded instance of MessageConfig
     */
    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    /**
     * load / reload a new instance of MessageConfig
     */
    public void loadMessageConfig(File file) {
        messageConfig = new MessageConfig(WMessages.class, file);
    }

    /**
     * @return the loaded instance of WCommands
     */
    @Override
    public WCommands getCommandCache() {
        return wCommands;
    }

    /**
     * load / reload a new instance of WCommands
     */
    public void loadWCommands() {
        wCommands = new WCommands(this);
        wCommands.register(this);
    }

    /**
     * @return the loaded instance of WPlayers
     */
    public WPlayers getWPlayers() {
        return wPlayers;
    }

    /**
     * load / reload a new instance of WPlayers
     */
    public void loadWPlayers() {
        wPlayers = new WPlayers();
    }

}

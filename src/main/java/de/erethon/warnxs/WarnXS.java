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
package de.erethon.warnxs;

import de.erethon.commons.compatibility.Internals;
import de.erethon.commons.javaplugin.DREPlugin;
import de.erethon.commons.javaplugin.DREPluginSettings;
import de.erethon.commons.misc.FileUtil;
import de.erethon.commons.spiget.comparator.VersionComparator;
import de.erethon.warnxs.command.WCommands;
import de.erethon.warnxs.config.WConfig;
import de.erethon.warnxs.listener.PlayerListener;
import de.erethon.warnxs.player.WPermission;
import de.erethon.warnxs.player.WPlayers;
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
    private WCommands wCommands;
    private WPlayers wPlayers;

    public WarnXS() {
        settings = DREPluginSettings.builder()
                .internals(Internals.INDEPENDENT)
                .economy(false)
                .permissions(false)
                .metrics(true)
                .spigotMCResourceId(48233)
                .versionComparator(VersionComparator.SEM_VER)
                .build();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        WPermission.register();
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
        loadWConfig(new File(getDataFolder(), "config.yml"));
        loadWPlayers();
        wPlayers.loadAll();
        loadWCommands();
    }

    public void saveData() {
        wPlayers.saveAll();
        File backupDir = new File(BACKUPS, String.valueOf(System.currentTimeMillis()));
        backupDir.mkdir();
        FileUtil.copy(PLAYERS, backupDir);
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

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
package io.github.dre2n.warnxs.config;

import io.github.dre2n.commons.config.Messages;
import io.github.dre2n.commons.util.messageutil.MessageUtil;
import io.github.dre2n.warnxs.WarnXS;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * An enumeration of all messages.
 * The values are fetched from the language file.
 *
 * @author Daniel Saukel
 */
public enum WMessages implements Messages {

    CMD_ADD_SUCCESS("cmd.add.success", "&3You successfully warned &4&v1&3. The player has &4&v2&3 pp."),
    CMD_LIST_PENALTY_POINTS("cmd.list.penaltyPoints", "&3The player &4&v1 &3has &4&v2 &3penalty points."),
    CMD_MAIN_WELCOME("cmd.main.welcome", "&3Welcome to &4Warn&fXS"),
    CMD_MAIN_HELP("cmd.main.help", "&3Type in &o/w help&r&3 for further information."),
    CMD_RELOAD_DONE("cmd.reload.done", "&3Successfully reloaded WarnXS."),
    CMD_REMOVE_CLICK("cmd.remove.click", "&3&oClick at one of the entries to remove it!"),
    CMD_REMOVE_FAIL("cmd.remove.fail", "&4The player &3&v1 &4does not have a warning at index number &3&v2&4."),
    CMD_REMOVE_SUCCESS("cmd.remove.success", "&3Successfully removed the warning."),
    ERROR_CMD_NOT_EXIST_1("error.cmdNotExist.1", "&4Command &3&v1&4 does not exist!"),
    ERROR_CMD_NOT_EXIST_2("error.cmdNotExist.2", "&4Please enter &3/f help&4 for help!"),
    ERROR_NO_PERMISSION("error.noPermission", "&4You do not have permission to do this!"),
    ERROR_NO_SUCH_PLAYER("error.noSuch.player", "&4The player &3&v1 &4does not exist!"),
    HELP_CMD_ADD("help.cmd.add", "/w add [player] [amount] [reason] - Adds a warning."),
    HELP_CMD_HELP("help.cmd.help", "/w help [page] - Shows the help page."),
    HELP_CMD_LIST("help.cmd.list", "/w list ([player=you]) - Shows a list of all warnings."),
    HELP_CMD_MAIN("help.cmd.main", "/w - General status information."),
    HELP_CMD_RELOAD("help.cmd.reload", "/w reload - Reloads the plugin."),
    HELP_CMD_REMOVE("help.cmd.remove", "/w remove [player] ([index]) - Removes a player's warn entry. Leave the index number empty to select."),
    LOG_NEW_PLAYER_DATA("log.newPlayerData", "&3A new player data file has been created and saved as &v1.");

    private String identifier;
    private String message;

    WMessages(String identifier, String message) {
        this.identifier = identifier;
        this.message = message;
    }

    /* Getters and setters */
    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getMessage() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public String getMessage(String... args) {
        return WarnXS.getInstance().getMessageConfig().getMessage(this, args);
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /* Actions */
    /**
     * Sends the message to the console.
     */
    public void debug() {
        MessageUtil.log(WarnXS.getInstance(), getMessage());
    }

    /* Statics */
    /**
     * @param identifer
     * the identifer to set
     */
    public static Messages getByIdentifier(String identifier) {
        for (Messages message : values()) {
            if (message.getIdentifier().equals(identifier)) {
                return message;
            }
        }

        return null;
    }

    /**
     * @return a FileConfiguration containing all messages
     */
    public static FileConfiguration toConfig() {
        FileConfiguration config = new YamlConfiguration();
        for (WMessages message : values()) {
            config.set(message.getIdentifier(), message.message);
        }

        return config;
    }

}

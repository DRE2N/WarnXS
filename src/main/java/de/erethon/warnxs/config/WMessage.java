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
package de.erethon.warnxs.config;

import de.erethon.commons.config.Message;

/**
 * An enumeration of all messages.
 * The values are fetched from the language file.
 *
 * @author Daniel Saukel
 */
public enum WMessage implements Message {

    CMD_ADD_BROADCAST("cmd.add.broadcast"),
    CMD_ADD_SUCCESS("cmd.add.success"),
    CMD_LIST_PENALTY_POINTS("cmd.list.penaltyPoints"),
    CMD_MAIN_WELCOME("cmd.main.welcome"),
    CMD_MAIN_HELP("cmd.main.help"),
    CMD_RELOAD_DONE("cmd.reload.done"),
    CMD_REMOVE_CLICK("cmd.remove.click"),
    CMD_REMOVE_FAIL("cmd.remove.fail"),
    CMD_REMOVE_SUCCESS("cmd.remove.success"),
    ERROR_CMD_NOT_EXIST_1("error.cmdNotExist.1"),
    ERROR_CMD_NOT_EXIST_2("error.cmdNotExist.2"),
    ERROR_NO_PERMISSION("error.noPermission"),
    ERROR_NO_SUCH_PLAYER("error.noSuch.player"),
    HELP_CMD_ADD("help.cmd.add"),
    HELP_CMD_HELP("help.cmd.help"),
    HELP_CMD_LIST("help.cmd.list"),
    HELP_CMD_MAIN("help.cmd.main"),
    HELP_CMD_RELOAD("help.cmd.reload"),
    HELP_CMD_REMOVE("help.cmd.remove"),
    LOG_NEW_PLAYER_DATA("log.newPlayerData"),
    NEW_WARNINGS_ONE("newWarnings.one"),
    NEW_WARNINGS_MULTIPLE("newWarnings.multiple"),
    NEW_WARNINGS_CLICK("newWarnings.click"),
    NEW_WARNINGS_CMD("newWarnings.cmd");

    private String path;

    WMessage(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}

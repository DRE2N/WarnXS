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
package de.erethon.warnxs.command;

import de.erethon.commons.chat.MessageUtil;
import de.erethon.commons.command.DRECommand;
import de.erethon.warnxs.WarnXS;
import de.erethon.warnxs.config.WMessage;
import de.erethon.warnxs.player.WPermission;
import org.bukkit.command.CommandSender;

/**
 * @author Daniel Saukel
 */
public class ReloadCommand extends DRECommand {

    WarnXS plugin = WarnXS.getInstance();

    public ReloadCommand() {
        setCommand("reload");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp(WMessage.HELP_CMD_RELOAD.getMessage());
        setPermission(WPermission.RELOAD.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        plugin.saveData();
        plugin.loadCore();

        MessageUtil.sendPluginTag(sender, plugin);
        MessageUtil.sendCenteredMessage(sender, WMessage.CMD_RELOAD_DONE.getMessage());
    }

}

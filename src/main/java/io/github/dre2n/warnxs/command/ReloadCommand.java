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
package io.github.dre2n.warnxs.command;

import io.github.dre2n.commons.command.BRCommand;
import io.github.dre2n.commons.util.messageutil.MessageUtil;
import io.github.dre2n.warnxs.WarnXS;
import io.github.dre2n.warnxs.config.WMessages;
import io.github.dre2n.warnxs.player.WPermissions;
import org.bukkit.command.CommandSender;

/**
 * @author Daniel Saukel
 */
public class ReloadCommand extends BRCommand {

    WarnXS plugin = WarnXS.getInstance();

    public ReloadCommand() {
        setCommand("reload");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp(WMessages.HELP_CMD_RELOAD.getMessage());
        setPermission(WPermissions.RELOAD.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        // Save
        plugin.saveData();
        plugin.getMessageConfig().save();

        plugin.loadCore();

        MessageUtil.sendPluginTag(sender, plugin);
        MessageUtil.sendCenteredMessage(sender, WMessages.CMD_RELOAD_DONE.getMessage());
    }

}

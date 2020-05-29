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
import de.erethon.commons.misc.NumberUtil;
import de.erethon.warnxs.WarnXS;
import de.erethon.warnxs.config.WMessage;
import de.erethon.warnxs.player.WPermission;
import de.erethon.warnxs.player.WPlayer;
import org.bukkit.command.CommandSender;

/**
 * @author Daniel Saukel
 */
public class SetCommand extends DRECommand {

    WarnXS plugin = WarnXS.getInstance();

    public SetCommand() {
        setCommand("set");
        setMinArgs(2);
        setMaxArgs(2);
        setHelp(WMessage.HELP_CMD_SET.getMessage());
        setPermission(WPermission.SET.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        if (args.length < 3) {
            displayHelp(sender);
            return;
        }
        WPlayer player = plugin.getWPlayers().getByName(args[1]);
        if (player == null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_PLAYER.getMessage(args[1]));
            return;
        }

        int amount = NumberUtil.parseInt(args[2], -1);
        if (amount == -1) {
            displayHelp(sender);
            return;
        }

        player.getData().updatePoints(amount);

        MessageUtil.sendMessage(sender, WMessage.CMD_SET_SUCCESS.getMessage(player.getName(), String.valueOf(amount)));
    }

}

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
package de.erethon.warnxs.command;

import static de.erethon.commons.chat.FatLetter.*;
import de.erethon.commons.chat.MessageUtil;
import de.erethon.commons.command.DRECommand;
import de.erethon.warnxs.WarnXS;
import de.erethon.warnxs.config.WMessage;
import de.erethon.warnxs.player.WPermission;
import org.bukkit.command.CommandSender;

/**
 * @author Daniel Saukel
 */
public class MainCommand extends DRECommand {

    WarnXS plugin = WarnXS.getInstance();

    public MainCommand() {
        setCommand("main");
        setHelp(WMessage.HELP_CMD_MAIN.getMessage());
        setPermission(WPermission.MAIN.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        MessageUtil.sendCenteredMessage(sender, "&4" + W[0] + A[0] + R[0] + N[0]);
        MessageUtil.sendCenteredMessage(sender, "&4" + W[1] + A[1] + R[1] + N[1]);
        MessageUtil.sendCenteredMessage(sender, "&4" + W[2] + A[2] + R[2] + N[2]);
        MessageUtil.sendCenteredMessage(sender, "&4" + W[3] + A[3] + R[3] + N[3]);
        MessageUtil.sendCenteredMessage(sender, "&4" + W[4] + A[4] + R[4] + N[4]);
        MessageUtil.sendCenteredMessage(sender, "&b&l######## " + WMessage.CMD_MAIN_WELCOME.getMessage() + " &3v" + plugin.getDescription().getVersion() + " &b&l########");
        MessageUtil.sendCenteredMessage(sender, WMessage.CMD_MAIN_HELP.getMessage());
        MessageUtil.sendCenteredMessage(sender, "&7\u00a92016-2021 Daniel Saukel; licensed under GPLv3.");
    }

}

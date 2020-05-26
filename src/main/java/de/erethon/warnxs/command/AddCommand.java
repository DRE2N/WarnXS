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
import java.util.Collections;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class AddCommand extends DRECommand {

    WarnXS plugin = WarnXS.getInstance();

    public AddCommand() {
        setCommand("add");
        setMinArgs(-1);
        setMaxArgs(-1);
        setHelp(WMessage.HELP_CMD_ADD.getMessage());
        setPermission(WPermission.ADD.getNode());
        setPlayerCommand(true);
        setConsoleCommand(false);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        if (args.length < 3) {
            displayHelp(sender);
            return;
        }
        Player mod = (Player) sender;
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

        String reason = new String();
        for (String arg : args) {
            if (args[0] != arg && args[1] != arg && args[2] != arg) {
                reason += arg + " ";
            }
        }
        player.warn(reason, amount, mod);

        Map<Integer, String> commands = plugin.getWConfig().getCommands();
        if (!commands.isEmpty()) {
            String command = player.getPenaltyPoints() > Collections.max(commands.keySet()) ? plugin.getWConfig().getDeathPenalty() : commands.get(player.getPenaltyPoints());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("<player>", player.getName()));
        }

        MessageUtil.sendMessage(sender, WMessage.CMD_ADD_SUCCESS.getMessage(player.getName(), String.valueOf(player.getPenaltyPoints())));
        if (!WMessage.CMD_ADD_BROADCAST.getMessage().isEmpty()) {
            MessageUtil.broadcastMessage(WMessage.CMD_ADD_BROADCAST.getMessage(mod.getName(), player.getName(), reason));
        }
    }

}

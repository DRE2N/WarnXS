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
import io.github.dre2n.commons.util.NumberUtil;
import io.github.dre2n.commons.util.messageutil.MessageUtil;
import io.github.dre2n.warnxs.WarnXS;
import io.github.dre2n.warnxs.config.WMessages;
import io.github.dre2n.warnxs.player.WPermissions;
import io.github.dre2n.warnxs.player.WPlayer;
import java.util.Collections;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class AddCommand extends BRCommand {

    WarnXS plugin = WarnXS.getInstance();

    public AddCommand() {
        setCommand("add");
        setMinArgs(-1);
        setMaxArgs(-1);
        setHelp(WMessages.HELP_CMD_ADD.getMessage());
        setPermission(WPermissions.ADD.getNode());
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
            MessageUtil.sendMessage(sender, WMessages.ERROR_NO_SUCH_PLAYER.getMessage(args[1]));
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
        String command = player.getPenaltyPoints() > Collections.max(commands.keySet()) ? plugin.getWConfig().getDeathPenalty() : commands.get(player.getPenaltyPoints());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("<player>", player.getName()));

        MessageUtil.sendMessage(sender, WMessages.CMD_ADD_SUCCESS.getMessage(player.getName(), String.valueOf(player.getPenaltyPoints())));
    }

}

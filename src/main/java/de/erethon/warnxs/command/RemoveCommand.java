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
import de.erethon.commons.compatibility.CompatibilityHandler;
import de.erethon.commons.misc.NumberUtil;
import de.erethon.warnxs.WarnXS;
import de.erethon.warnxs.config.WMessage;
import de.erethon.warnxs.player.WPermission;
import de.erethon.warnxs.player.WPlayer;
import de.erethon.warnxs.player.WReason;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class RemoveCommand extends DRECommand {

    WarnXS plugin = WarnXS.getInstance();

    public RemoveCommand() {
        setCommand("remove");
        setMinArgs(1);
        setMaxArgs(2);
        setHelp(WMessage.HELP_CMD_REMOVE.getMessage());
        setPermission(WPermission.REMOVE.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        WPlayer player = plugin.getWPlayers().getByName(args[1]);
        if (args.length == 2) {
            if (CompatibilityHandler.getInstance().isSpigot() && sender instanceof Player) {
                MessageUtil.sendCenteredMessage(sender, WMessage.CMD_REMOVE_CLICK.getMessage());
                MessageUtil.sendCenteredMessage(sender, "&e&l= = = = = = = = = =");
                MessageUtil.sendMessage(sender, "&bDATE &e| &bMOD &e| &bPENALTY POINTS");
                int index = 0;
                for (WReason reason : player.getData().getReasons()) {
                    String date = reason.getDate().toString();
                    String pps = String.valueOf(reason.getPenaltyPoints());
                    String mod = reason.getMod().getName();
                    String wCase = reason.getCase();
                    String message = ChatColor.GOLD + date + ChatColor.YELLOW + " | " + ChatColor.GOLD + mod + ChatColor.YELLOW + " | " + ChatColor.GOLD + pps;
                    HoverEvent onHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.DARK_RED + wCase));
                    ClickEvent onClick = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warnxs remove " + player.getName() + " " + index);
                    BaseComponent[] text = TextComponent.fromLegacyText(message);
                    for (BaseComponent component : text) {
                        component.setHoverEvent(onHover);
                        component.setClickEvent(onClick);
                    }
                    ((Player) sender).spigot().sendMessage(text);
                    index++;
                }
            } else {
                displayHelp(sender);
            }
            return;
        }

        int index = NumberUtil.parseInt(args[2], -1);
        if (player == null) {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_PLAYER.getMessage(args[1]));
            return;
        }
        if (index == -1) {
            displayHelp(sender);
            return;
        }

        if (player.getData().getReasons().size() > index) {
            player.getData().getReasons().remove(index);
            player.getData().save();
            MessageUtil.sendMessage(sender, WMessage.CMD_REMOVE_SUCCESS.getMessage());
        } else {
            MessageUtil.sendMessage(sender, WMessage.CMD_REMOVE_FAIL.getMessage(player.getName(), String.valueOf(index)));
        }
    }

}

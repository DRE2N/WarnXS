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

import de.erethon.commons.chat.MessageUtil;
import de.erethon.commons.command.DRECommand;
import de.erethon.commons.compatibility.CompatibilityHandler;
import de.erethon.warnxs.WarnXS;
import de.erethon.warnxs.config.WMessage;
import de.erethon.warnxs.player.WPermission;
import de.erethon.warnxs.player.WPlayer;
import de.erethon.warnxs.player.WReason;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class ListCommand extends DRECommand {

    WarnXS plugin = WarnXS.getInstance();

    public ListCommand() {
        setCommand("list");
        setMinArgs(0);
        setMaxArgs(1);
        setHelp(WMessage.HELP_CMD_LIST.getMessage());
        setPermission(WPermission.LIST.getNode());
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        WPlayer wPlayer = null;
        if (args.length == 2 && WPermission.hasPermission(sender, WPermission.LIST_OTHERS)) {
            wPlayer = plugin.getWPlayers().getByName(args[1]);
            if (wPlayer == null) {
                MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_PLAYER.getMessage(args[1]));
                return;
            }
        } else if (sender instanceof Player) {
            wPlayer = plugin.getWPlayers().getByPlayer((Player) sender);
        } else {
            MessageUtil.sendMessage(sender, WMessage.ERROR_NO_SUCH_PLAYER.getMessage("none"));
            return;
        }

        MessageUtil.sendCenteredMessage(sender, WMessage.CMD_LIST_PENALTY_POINTS.getMessage(wPlayer.getName(), String.valueOf(wPlayer.getPenaltyPoints())));
        MessageUtil.sendCenteredMessage(sender, "&e&l= = = = = = = = = =");
        MessageUtil.sendMessage(sender, "&bDATE &e| &bMOD &e| &bPENALTY POINTS");
        for (WReason reason : wPlayer.getData().getReasons()) {
            String date = reason.getDate().toString();
            String pps = String.valueOf(reason.getPenaltyPoints());
            String mod = reason.getMod().getName();
            String wCase = reason.getCase();
            String message = ChatColor.GOLD + date + ChatColor.YELLOW + " | " + ChatColor.GOLD + mod + ChatColor.YELLOW + " | " + ChatColor.GOLD + pps;
            if (CompatibilityHandler.getInstance().isSpigot()) {
                HoverEvent onHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(" " + ChatColor.DARK_RED + wCase));
                BaseComponent[] text = TextComponent.fromLegacyText(message);
                for (BaseComponent component : text) {
                    component.setHoverEvent(onHover);
                }
                ((Player) sender).spigot().sendMessage(text);
            } else {
                MessageUtil.sendMessage(sender, message);
            }
        }
    }

}

/*
 * Copyright (C) 2016-2017 Daniel Saukel
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

import io.github.dre2n.commons.command.BRCommands;
import io.github.dre2n.commons.javaplugin.BRPlugin;

/**
 * An enumeration of all command instances.
 *
 * @author Daniel Saukel
 */
public class WCommands extends BRCommands {

    public static AddCommand ADD = new AddCommand();
    public static HelpCommand HELP = new HelpCommand();
    public static ListCommand LIST = new ListCommand();
    public static MainCommand MAIN = new MainCommand();
    public static ReloadCommand RELOAD = new ReloadCommand();
    public static RemoveCommand REMOVE = new RemoveCommand();

    public WCommands(BRPlugin plugin) {
        super("warnxs", plugin,
                ADD,
                HELP,
                LIST,
                MAIN,
                RELOAD,
                REMOVE
        );
    }

}

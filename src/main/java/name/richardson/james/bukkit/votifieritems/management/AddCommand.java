/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AddCommand.java is part of VotifierItems.
 * 
 * VotifierItems is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * VotifierItems is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * VotifierItems. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.votifieritems.management;

import java.text.DecimalFormat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.votifieritems.Configuration;
import name.richardson.james.bukkit.votifieritems.Item;
import name.richardson.james.bukkit.votifieritems.VotifierItemsPlugin;

public final class AddCommand extends AbstractCommand {

  private double chance;

  private final Configuration configuration;

  private final DecimalFormat format = new DecimalFormat("0.0##%");

  public AddCommand(final VotifierItemsPlugin plugin) {
    super(plugin, false);
    this.configuration = plugin.getConfiguration();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    final Player player = (Player) sender;
    final ItemStack item = player.getItemInHand();
    if (this.configuration.getItemList().isSpace()) {
      this.configuration.getItemList().add(new Item(this.chance, item));
      sender.sendMessage(this.getLocalisation().getMessage(this, "added", item.getAmount(), item.getType().toString(), this.format.format(this.chance)));
      this.configuration.setItemList();
      this.configuration.save();
    }
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    if (arguments.length == 1) {
      this.chance = Float.parseFloat(arguments[0]);
    } else {
      this.chance = Item.DEFAULT_CHANCE;
    }
  }

}

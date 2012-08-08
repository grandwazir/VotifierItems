/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ListCommand.java is part of VotifierItems.
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
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;
import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;
import name.richardson.james.bukkit.votifieritems.Configuration;
import name.richardson.james.bukkit.votifieritems.Item;
import name.richardson.james.bukkit.votifieritems.VotifierItemsPlugin;

@ConsoleCommand
public class ListCommand extends AbstractCommand {

  private final Configuration configuration;

  private final DecimalFormat format = new DecimalFormat("(0.0#%)");

  private final ChoiceFormatter formatter;

  public ListCommand(final VotifierItemsPlugin plugin) {
    super(plugin, false);
    this.configuration = plugin.getConfiguration();
    this.formatter = new ChoiceFormatter(this.getLocalisation());
    this.formatter.setLimits(0, 1, 2);
    this.formatter.setMessage(this, "header");
    this.formatter.setArguments(this.configuration.getItemList().getItems().size());
    this.formatter.setFormats(this.getLocalisation().getMessage(this, "no-items"), this.getLocalisation().getMessage(this, "one-item"), this.getLocalisation().getMessage(this, "many-items"));
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    final List<Item> items = this.configuration.getItemList().getItems();
    sender.sendMessage(this.formatter.getMessage());
    if (items.size() != 0) {
      sender.sendMessage(this.getStringItemList(items));
    }
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    return;
  }

  private String getStringItemList(final List<Item> items) {
    final StringBuilder message = new StringBuilder();
    message.append(ChatColor.YELLOW);
    for (final Item item : items) {
      message.append(item.getItemStack().getAmount());
      message.append("x");
      message.append(item.getItemStack().getType().toString());
      message.append(ChatColor.RED);
      message.append(" ");
      message.append(this.format.format(item.getChance()));
      message.append(ChatColor.YELLOW);
      message.append(", ");
    }
    message.delete(message.length() - 2, message.length());
    message.append(".");
    return message.toString();
  }

}

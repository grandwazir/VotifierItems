/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * TestCommand.java is part of VotifierItems.
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

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;
import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;
import name.richardson.james.bukkit.votifieritems.Configuration;
import name.richardson.james.bukkit.votifieritems.VotifierItemsPlugin;

@ConsoleCommand
public class TestCommand extends AbstractCommand {

  private final Configuration configuration;
  private final ChoiceFormatter formatter;

  public TestCommand(final VotifierItemsPlugin plugin) {
    super(plugin, false);
    this.configuration = plugin.getConfiguration();
    this.formatter = new ChoiceFormatter(this.getLocalisation());
    this.formatter.setLimits(0, 1, 2);
    this.formatter.setMessage(this, "header");
    this.formatter.setArguments(this.configuration.getItemList().getItems().size());
    this.formatter.setFormats(this.getLocalisation().getMessage(VotifierItemsPlugin.class, "no-items"), this.getLocalisation().getMessage(VotifierItemsPlugin.class, "one-item"), this.getLocalisation().getMessage(VotifierItemsPlugin.class, "many-items"));
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    final ItemStack[] items = this.configuration.getItemList().getRandomItems();
    this.formatter.setArguments(items.length);
    sender.sendMessage(this.formatter.getMessage());
    sender.sendMessage(this.getStringItemList(items));
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    return;
  }

  private String getStringItemList(final ItemStack[] items) {
    final StringBuilder message = new StringBuilder();
    message.append(ChatColor.YELLOW);
    for (final ItemStack item : items) {
      message.append(item.getAmount());
      message.append("x");
      message.append(item.getType().toString());
      message.append(", ");
    }
    message.delete(message.length() - 2, message.length());
    message.append(".");
    return message.toString();
  }

}

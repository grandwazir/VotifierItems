/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * VotifierItemsPlugin.java is part of VotifierItems.
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
package name.richardson.james.bukkit.votifieritems;

import java.io.IOException;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;
import name.richardson.james.bukkit.votifieritems.management.AddCommand;
import name.richardson.james.bukkit.votifieritems.management.ListCommand;
import name.richardson.james.bukkit.votifieritems.management.RemoveCommand;
import name.richardson.james.bukkit.votifieritems.management.TestCommand;

public final class VotifierItemsPlugin extends AbstractPlugin {

  private Configuration configuration;

  private ItemList items;

  public VotifierItemsPlugin() {
    ConfigurationSerialization.registerClass(Item.class);
  }

  public String getArtifactID() {
    return "votifier-items";
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  @Override
  protected void loadConfiguration() throws IOException {
    super.loadConfiguration();
    this.configuration = new Configuration(this);
    this.items = this.configuration.getItemList();
  }

  @Override
  protected void registerCommands() {
    final CommandManager cm = new CommandManager(this);
    this.getCommand("vi").setExecutor(cm);
    cm.addCommand(new AddCommand(this));
    cm.addCommand(new ListCommand(this));
    cm.addCommand(new RemoveCommand(this));
    cm.addCommand(new TestCommand(this));
  }

  @Override
  protected void registerListeners() {
    new PlayerListener(this, this.items);
  }

}

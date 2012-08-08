/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Configuration.java is part of VotifierItems.
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
import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public class Configuration extends PluginConfiguration {

  private ItemList itemList;

  public Configuration(final Plugin plugin) throws IOException {
    super(plugin);
    this.setDefaultItemList();
  }

  public ItemList getItemList() {
    if (this.itemList == null) {
      final ConfigurationSection section = this.getConfiguration().getConfigurationSection("items");
      final ArrayList<Item> items = new ArrayList<Item>();
      for (final String key : section.getKeys(false)) {
        items.add((Item) section.get(key));
      }
      Collections.sort(items);
      this.itemList = new ItemList(items, this.isCalculatingDropsSeparately());
    }
    return this.itemList;
  }

  public boolean isCalculatingDropsSeparately() {
    return this.getConfiguration().getBoolean("calculate-drops-independantly", false);
  }

  @Override
  public void load() {
    super.load();
  }

  public void setItemList() {
    final ConfigurationSection section = this.getConfiguration().getConfigurationSection("items");
    int i = 0;
    for (final Item item : this.itemList.getItems()) {
      section.set(Integer.toString(i), item);
      i++;
    }
  }

  private void setDefaultItemList() {
    if (!this.getConfiguration().isConfigurationSection("items")) {
      this.getConfiguration().createSection("items");
      this.save();
    }
  }

}

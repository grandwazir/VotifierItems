package name.richardson.james.bukkit.votifieritems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.configuration.ConfigurationSection;

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;
import name.richardson.james.bukkit.utilities.plugin.Plugin;

public class Configuration extends PluginConfiguration {

  private ItemList itemList;

  public Configuration(Plugin plugin) throws IOException {
    super(plugin);
    this.setDefaultItemList();
  }
  
  private void setDefaultItemList() {
    if (!this.getConfiguration().isConfigurationSection("items")) {
      this.getConfiguration().createSection("items");
      this.save();
    }
  }

  public boolean isCalculatingDropsSeparately() {
    return this.getConfiguration().getBoolean("calculate-drops-independantly", false);
  }
  
  public ItemList getItemList() {
    if (itemList == null) {
      ConfigurationSection section = getConfiguration().getConfigurationSection("items");
      ArrayList<Item> items = new ArrayList<Item>(); 
      for (String key : section.getKeys(false)) {
        items.add((Item) section.get(key));
      }
      Collections.sort(items);
      this.itemList = new ItemList(items, this.isCalculatingDropsSeparately());
    }
    return itemList;
  }
  
  public void load() {
    super.load();
  }
  
  public void setItemList() {
    ConfigurationSection section = getConfiguration().getConfigurationSection("items");
    int i = 0;
    for (Item item : itemList.getItems()) {
      section.set(Integer.toString(i), item);
      i++;
    }
  }
  

  
}

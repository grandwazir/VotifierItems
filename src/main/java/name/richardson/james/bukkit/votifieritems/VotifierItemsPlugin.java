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

  protected void loadConfiguration() throws IOException {
    super.loadConfiguration();
    this.configuration = new Configuration(this);
    this.items = this.configuration.getItemList();
  }
  
  protected void registerListeners() {
    new PlayerListener(this, items);
  }
  
  protected void registerCommands() {
    CommandManager cm = new CommandManager(this);
    this.getCommand("vi").setExecutor(cm);
    cm.addCommand(new AddCommand(this));
    cm.addCommand(new ListCommand(this));
    cm.addCommand(new RemoveCommand(this));
    cm.addCommand(new TestCommand(this));
  }
  
  public Configuration getConfiguration() {
    return this.configuration;
  }
  
}

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

  private Configuration configuration;
  private ChoiceFormatter formatter;

  public TestCommand(VotifierItemsPlugin plugin) {
    super(plugin, false);
    this.configuration = plugin.getConfiguration();
    this.formatter = new ChoiceFormatter(this.getLocalisation());
    this.formatter.setLimits(0, 1, 2);
    this.formatter.setMessage(this, "header");
    this.formatter.setArguments(this.configuration.getItemList().getItems().size());
    this.formatter.setFormats(
        this.getLocalisation().getMessage(VotifierItemsPlugin.class, "no-items"), 
        this.getLocalisation().getMessage(VotifierItemsPlugin.class, "one-item"), 
        this.getLocalisation().getMessage(VotifierItemsPlugin.class, "many-items")
    );
  }

  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    ItemStack[] items = configuration.getItemList().getRandomItems();
    this.formatter.setArguments(items.length);
    sender.sendMessage(this.formatter.getMessage());
    sender.sendMessage(this.getStringItemList(items));
  }

  private String getStringItemList(ItemStack[] items) {
    final StringBuilder message = new StringBuilder();
    message.append(ChatColor.YELLOW);
    for (ItemStack item : items) {
      message.append(item.getAmount());
      message.append("x");
      message.append(item.getType().toString());
      message.append(", ");
    }
    message.delete(message.length() - 2, message.length());
    message.append(".");
    return message.toString();
  }
  
  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException {
    return;
  }

}

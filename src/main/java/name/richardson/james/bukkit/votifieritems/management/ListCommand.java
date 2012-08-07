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

  private final DecimalFormat format = new DecimalFormat("(0.0#%)");
  
  private final Configuration configuration;

  private final ChoiceFormatter formatter;

  public ListCommand(VotifierItemsPlugin plugin) {
    super(plugin, false);
    this.configuration = plugin.getConfiguration();
    this.formatter = new ChoiceFormatter(this.getLocalisation());
    this.formatter.setLimits(0, 1, 2);
    this.formatter.setMessage(this, "header");
    this.formatter.setArguments(this.configuration.getItemList().getItems().size());
    this.formatter.setFormats(
        this.getLocalisation().getMessage(this, "no-items"), 
        this.getLocalisation().getMessage(this, "one-item"), 
        this.getLocalisation().getMessage(this, "many-items")
    );
  }

  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    final List<Item> items = this.configuration.getItemList().getItems();
    sender.sendMessage(this.formatter.getMessage());
    if (items.size() != 0) {
      sender.sendMessage(this.getStringItemList(items));
    }
  }
  
  private String getStringItemList(List<Item> items) {
    final StringBuilder message = new StringBuilder();
    message.append(ChatColor.YELLOW);
    for (Item item : items) {
      message.append(item.getItemStack().getAmount());
      message.append("x");
      message.append(item.getItemStack().getType().toString());
      message.append(ChatColor.RED);
      message.append(" ");
      message.append(format.format(item.getChance()));
      message.append(ChatColor.YELLOW);
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

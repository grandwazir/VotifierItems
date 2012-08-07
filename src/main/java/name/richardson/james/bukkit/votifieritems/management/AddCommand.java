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
  
  private final DecimalFormat format = new DecimalFormat("0.0##%");
  
  private double chance;
  
  private final Configuration configuration;
  
  public AddCommand(VotifierItemsPlugin plugin) {
    super(plugin, false);
    this.configuration = plugin.getConfiguration();
  }

  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    final Player player = (Player) sender;
    final ItemStack item = player.getItemInHand();
    if (this.configuration.getItemList().isSpace()) {
      this.configuration.getItemList().add(new Item(chance, item));
      sender.sendMessage(this.getLocalisation().getMessage(this, "added",item.getAmount(), item.getType().toString(), format.format(chance)));
      this.configuration.setItemList();
      this.configuration.save();
    }
  }

  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException {
    if (arguments.length == 1) {
      this.chance = Float.parseFloat(arguments[0]);
    } else {
      this.chance = Item.DEFAULT_CHANCE;
    }
  }

}

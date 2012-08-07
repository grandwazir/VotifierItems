package name.richardson.james.bukkit.votifieritems.management;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.votifieritems.Configuration;
import name.richardson.james.bukkit.votifieritems.VotifierItemsPlugin;

public class RemoveCommand extends AbstractCommand {

  private Configuration configuration;

  public RemoveCommand(VotifierItemsPlugin plugin) {
    super(plugin, false);
    this.configuration = plugin.getConfiguration();
  }

  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException {
    return;
  }

  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    final Player player = (Player) sender;
    final ItemStack item = player.getItemInHand();
    this.configuration.getItemList().remove(item);
    sender.sendMessage(this.getLocalisation().getMessage(this, "removed", item.getAmount(), item.getType().toString()));
    this.configuration.setItemList();
    this.configuration.save();
  }
  
}

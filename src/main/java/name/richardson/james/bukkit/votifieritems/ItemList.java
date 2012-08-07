package name.richardson.james.bukkit.votifieritems;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.inventory.ItemStack;

public final class ItemList {
  
  /** The random number generator for this class */
  private final Random random = new Random();
  
  /** The items associated with this list */
  private final List<Item> items;

  /** If we should calcuate the chance to drop each item individually */
  private final boolean independantDrops;
  
  public ItemList(List<Item> items, boolean independantDrops) {
    this.items = items;
    Collections.sort(items);
    this.independantDrops = independantDrops;
  }
  
  public List<Item> getItems() {
    return items;
  }
  
  public ItemStack[] getRandomItems() {
    // logger.debug("Getting a random choice of items");
    List<ItemStack> pickedItems = new ArrayList<ItemStack>();
    double roll = round(random.nextDouble());
    for (Item item : items) {
      // logger.debug("- Rolled a " + Double.toString(roll));
      double itemChance = round(item.getChance());
      // logger.debug("- Current item chance " + Double.toString(round(item.getChance())));
      // if the roll is less than or equal to the item chance, award the item
      if (roll <= itemChance) {
        // logger.debug("-- Adding " + item.getItemStack().getType().toString());
        pickedItems.add(item.getItemStack());
      }
      // if we are not calucating drops individually and we have picked an item already break
      if (this.independantDrops == false && !pickedItems.isEmpty()) {
        break;
      // if we are rolling for every drop, roll the dice again
      } else if (this.independantDrops == true) {
        roll = round(random.nextDouble());
      }
    }
    return (ItemStack[]) pickedItems.toArray(new ItemStack[pickedItems.size()]);
  }
  
  private double round(double value) {
    DecimalFormat format = new DecimalFormat("#.###");
    return Double.valueOf(format.format(value));
  }
  
  public boolean isSpace() {
    if (this.independantDrops) {
      double totalChance = 0;
      for (Item item : this.items) {
        totalChance=+ item.getChance();
      }
      if (totalChance == 1.0) {
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  public void add(Item item) {
    items.add(item);
    Collections.sort(items);
  }

  public void remove(ItemStack item) {
    items.remove(item);
  }
  
}

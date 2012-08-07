package name.richardson.james.bukkit.votifieritems;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Item implements ConfigurationSerializable, Comparable<Item> {
  
  public static final double DEFAULT_CHANCE = 0.1;

  public static Item deserialize(Map<String, Object> map) {
    double chance = (Double) map.get("chance");
    ItemStack itemStack = (ItemStack) map.get("item-stack");
    return new Item(chance, itemStack);
  }
  
  /* The percentage chance represented as a float that this item will be selected */
  private final double chance;
  
  /* The stack associated with this item */
  private final ItemStack itemStack;

  public Item(ItemStack itemStack) {
    this.chance = Item.DEFAULT_CHANCE;
    this.itemStack = itemStack;
  }
  
  public Item(double chance, ItemStack itemStack) {
    this.chance = chance;
    this.itemStack = itemStack;
  }
  
  public ItemStack getItemStack() {
    return itemStack;
  }
  
  public double getChance() {
    return chance;
  }

  /** Items with the bigger chance appear at the top of the list */
  public int compareTo(Item item) {
    if (item.getChance() < item.getChance()) {
      return 1;
    } else if (item.getChance() > item.getChance()) {
      return -1;
    } else {
      return 0;
    }
  }

 
  public Map<String, Object> serialize() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("chance", this.chance);
    map.put("item-stack", this.itemStack);
    return map;
  } 
  
}

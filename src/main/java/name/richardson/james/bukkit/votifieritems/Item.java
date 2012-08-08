/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Item.java is part of VotifierItems.
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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Item implements ConfigurationSerializable, Comparable<Item> {

  public static final double DEFAULT_CHANCE = 0.1;

  public static Item deserialize(final Map<String, Object> map) {
    final double chance = (Double) map.get("chance");
    final ItemStack itemStack = (ItemStack) map.get("item-stack");
    return new Item(chance, itemStack);
  }

  /*
   * The percentage chance represented as a float that this item will be
   * selected
   */
  private final double chance;

  /* The stack associated with this item */
  private final ItemStack itemStack;

  public Item(final double chance, final ItemStack itemStack) {
    this.chance = chance;
    this.itemStack = itemStack;
  }

  public Item(final ItemStack itemStack) {
    this.chance = Item.DEFAULT_CHANCE;
    this.itemStack = itemStack;
  }

  /** Items with the bigger chance appear at the top of the list */
  public int compareTo(final Item item) {
    if (item.getChance() < item.getChance()) {
      return 1;
    } else if (item.getChance() > item.getChance()) {
      return -1;
    } else {
      return 0;
    }
  }

  public double getChance() {
    return this.chance;
  }

  public ItemStack getItemStack() {
    return this.itemStack;
  }

  public Map<String, Object> serialize() {
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put("chance", this.chance);
    map.put("item-stack", this.itemStack);
    return map;
  }

}

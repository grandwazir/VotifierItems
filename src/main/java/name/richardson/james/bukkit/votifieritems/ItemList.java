/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ItemList.java is part of VotifierItems.
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.inventory.ItemStack;

public final class ItemList {

  /** If we should calcuate the chance to drop each item individually */
  private final boolean independantDrops;

  /** The items associated with this list */
  private final List<Item> items;

  /** The random number generator for this class */
  private final Random random = new Random();

  public ItemList(final List<Item> items, final boolean independantDrops) {
    this.items = items;
    Collections.sort(items);
    this.independantDrops = independantDrops;
  }

  public void add(final Item item) {
    this.items.add(item);
    Collections.sort(this.items);
  }

  public List<Item> getItems() {
    return this.items;
  }

  public ItemStack[] getRandomItems() {
    // logger.debug("Getting a random choice of items");
    final List<ItemStack> pickedItems = new ArrayList<ItemStack>();
    double roll = this.round(this.random.nextDouble());
    for (final Item item : this.items) {
      // logger.debug("- Rolled a " + Double.toString(roll));
      final double itemChance = this.round(item.getChance());
      // logger.debug("- Current item chance " +
      // Double.toString(round(item.getChance())));
      // if the roll is less than or equal to the item chance, award the item
      if (roll <= itemChance) {
        // logger.debug("-- Adding " +
        // item.getItemStack().getType().toString());
        pickedItems.add(item.getItemStack());
      }
      // if we are not calucating drops individually and we have picked an item
      // already break
      if ((this.independantDrops == false) && !pickedItems.isEmpty()) {
        break;
        // if we are rolling for every drop, roll the dice again
      } else if (this.independantDrops == true) {
        roll = this.round(this.random.nextDouble());
      }
    }
    return pickedItems.toArray(new ItemStack[pickedItems.size()]);
  }

  public boolean isSpace() {
    if (this.independantDrops) {
      double totalChance = 0;
      for (final Item item : this.items) {
        totalChance = +item.getChance();
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

  public void remove(final ItemStack item) {
    this.items.remove(item);
  }

  private double round(final double value) {
    final DecimalFormat format = new DecimalFormat("#.###");
    return Double.valueOf(format.format(value));
  }

}

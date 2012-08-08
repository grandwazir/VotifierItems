/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PlayerListener.java is part of VotifierItems.
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

import com.vexsoftware.votifier.model.VotifierEvent;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.utilities.listener.Listener;
import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.logging.Logger;

public final class PlayerListener implements Listener {

  private final ItemList list;

  private final Localisation localisation;

  private final Logger logger;

  /** Items held for offline players */
  private final Map<String, ItemStack[]> offlinePlayerItems = new HashMap<String, ItemStack[]>();

  private final Server server;

  public PlayerListener(final VotifierItemsPlugin plugin, final ItemList list) {
    this.list = list;
    this.server = plugin.getServer();
    this.localisation = plugin.getLocalisation();
    this.logger = plugin.getCustomLogger();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onPlayerJoin(final PlayerJoinEvent event) {
    final String playerName = event.getPlayer().getName();
    if (!this.offlinePlayerItems.containsKey(playerName)) {
      return;
    }
    this.awardItems(event.getPlayer(), this.offlinePlayerItems.get(playerName));
    this.offlinePlayerItems.remove(playerName);
  }

  @EventHandler(priority = EventPriority.NORMAL)
  public void onPlayerVote(final VotifierEvent event) {
    final String playerName = event.getVote().getUsername();
    final Player player = this.server.getPlayerExact(playerName);
    final ItemStack[] items = this.list.getRandomItems();
    this.server.broadcastMessage(this.localisation.getMessage(this, "vote-broadcast", this.localisation.getMessage(this, "prefix"), event.getVote().getUsername()));
    // if the player is online award them the items now
    if (player != null) {
      this.awardItems(player, items);
    } else {
      this.offlinePlayerItems.put(playerName, items);
    }
  }

  private void awardItems(final Player player, final ItemStack[] items) {
    if (player == null) {
      throw new IllegalArgumentException("player can not be null");
    }
    if (items == null) {
      throw new IllegalArgumentException("items can not be null");
    }
    this.logger.debug(this, "awarded-items", player.getName(), items.toString());
    final PlayerInventory inventory = player.getInventory();
    player.sendMessage(this.localisation.getMessage(this, "items-awarded"));
    Map<Integer, ItemStack> overflow;
    overflow = inventory.addItem(items);
    // drop any overflow items in front of the player
    if (!overflow.isEmpty()) {
      player.sendMessage(this.localisation.getMessage(this, "items-overflow"));
      for (final ItemStack item : overflow.values()) {
        player.getWorld().dropItem(player.getEyeLocation().add(player.getLocation().getDirection()), item);
      }
    }
  }

}

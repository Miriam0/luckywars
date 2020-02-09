package custom.luckywars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import custom.luckywars.lucky.LuckyBlock;
import custom.mgapi.classes.SuperItem;
import custom.mgapi.enums.GameStatus;
import custom.mgapi.events.MinigamePlayerDeathEvent;
import custom.mgapi.events.MinigameStartedEvent;
import custom.mgapi.secondary.ItemStore;

public class EventListener implements Listener {
	@EventHandler
	public void onLuckyBlockBreak(BlockBreakEvent e) {
		if(e.getBlock().getType().equals(Material.SKULL)) {
			e.setDropItems(false);
			e.getPlayer().sendMessage("лакиблок сломан");
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(ItemStore.store.containsKey(e.getItem())) {
				((SuperItem)ItemStore.store.get(e.getItem())).onUse(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onGameStarted(MinigameStartedEvent e) {
		for(Player p : e.getPlayers()) {
			LuckyBlock.giveLuckyBlocks(p);
			p.setHealth(20);
			p.setFoodLevel(20);
		}
	}
	
	/*@EventHandler
	public void onGameStopped(MinigameStoppedEvent e) {
		for(Player p : e.getPlayers()) {
			p.removeMetadata("disguise", this);
			p.removeMetadata("kit", this);
		}
	}*/
	
	@EventHandler
	public void onGameDeath(MinigamePlayerDeathEvent e) {
		e.getGame().respawnPlayer(e.getTarget());
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {
		if(!LW.game.getGameStatus().equals(GameStatus.Waiting) || LW.game.getPlayersCount() >= LW.game.getMaxPlayersCount()) {
			e.getPlayer().kickPlayer("Сервер не готов");
		} else LW.game.addPlayer(e.getPlayer());
		e.setJoinMessage(ChatColor.GREEN + "Игрок " + ChatColor.WHITE + e.getPlayer().getName() + " " + ChatColor.GREEN + "присоединился к игре");
	}
}

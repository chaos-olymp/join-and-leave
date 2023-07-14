package de.chaosolymp.joinandleave;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public final class JoinAndLeave extends Plugin implements Listener {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
    }

    private TextComponent getMessageComponent(boolean isJoin, String name) {
        TextComponent sign = new TextComponent();
        if (isJoin) {
            sign.setText("+ ");
            sign.setColor(ChatColor.GREEN);
        }
        else {
            sign.setText("- ");
            sign.setColor(ChatColor.RED);
        }

        TextComponent nameComponent = new TextComponent(name);
        nameComponent.setBold(false);
        nameComponent.setColor(ChatColor.GRAY);

        sign.addExtra(nameComponent);
        return sign;

    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        TextComponent joinMessage = getMessageComponent(true, player.getName());
        // Broadcast the join message
        for (ProxiedPlayer onlinePlayer : ProxyServer.getInstance().getPlayers()) {
            onlinePlayer.sendMessage(joinMessage);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        TextComponent leaveMessage = getMessageComponent(false, player.getName());
        // Broadcast the leave message
        for (ProxiedPlayer onlinePlayer : ProxyServer.getInstance().getPlayers()) {
            onlinePlayer.sendMessage(leaveMessage);
        }

    }
}



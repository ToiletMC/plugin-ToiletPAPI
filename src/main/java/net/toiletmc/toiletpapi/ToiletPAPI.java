package net.toiletmc.toiletpapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToiletPAPI extends PlaceholderExpansion {
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {

    }

    @Override
    public @NotNull String getIdentifier() {
        return "toiletpapi";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TheLittle_Yang";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
}
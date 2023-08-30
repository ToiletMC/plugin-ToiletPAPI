package net.toiletmc.toiletpapi;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ToiletPAPI extends PlaceholderExpansion implements Configurable {
    private String tabFooter;
    private String fixedWorldEmoji;
    private Spark spark;

    @Override
    public Map<String, Object> getDefaults() {
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("tab-footer", "请修改默认页脚信息");
        defaults.put("fixed-world-emoji", "none");
        return defaults;
    }

    @Override
    public boolean register() {
        RegisteredServiceProvider<Spark> provider = Bukkit.getServicesManager().getRegistration(Spark.class);
        if (provider != null) {
            spark = provider.getProvider();
            this.info("已挂钩到 Spark");
        } else {
            this.severe("spark 服务异常，已停用插件");
            this.unregister();
        }

        tabFooter = this.getString("tab-footer", "请修改默认页脚信息");
        fixedWorldEmoji = this.getString("fixed-world-emoji", "none");

        return super.register();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        switch (params) {
            case "emoji_world" -> {
                if (!fixedWorldEmoji.equals("none")) {
                    return fixedWorldEmoji;
                }

                if (player.getWorld().getName().equals("resource_world")) {
                    return "§7⛏";
                }
            }
            case "emoji_world_time" -> {
                long gameTime = player.getWorld().getTime() % 24000;
                if (gameTime >= 0 && gameTime <= 12000) {
                    return "§6☀";
                } else {
                    return "§e\uD83C\uDF1A";
                }
            }
            case "emoji_tps" -> {
                DoubleStatistic<StatisticWindow.TicksPerSecond> tps = spark.tps();
                double tpsLast10Secs = tps.poll(StatisticWindow.TicksPerSecond.SECONDS_10);
                if (tpsLast10Secs >= 18) {
                    return "§a\uD83C\uDF11";
                } else if (tpsLast10Secs >= 15) {
                    return "§6\uD83C\uDF11";
                } else if (tpsLast10Secs >= 10) {
                    return "§c\uD83C\uDF11";
                } else {
                    return "§4\uD83C\uDF11";
                }
            }
            case "tab_footer" -> {
                return PlaceholderAPI.setPlaceholders(player, tabFooter);
            }
        }
        return "";
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
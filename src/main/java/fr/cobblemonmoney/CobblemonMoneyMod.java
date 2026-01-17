package fr.cobblemonmoney;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CobblemonMoneyMod implements ModInitializer {

    @Override
    public void onInitialize() {

        CobblemonEvents.POKEMON_CAPTURED.subscribe(event -> {
            ServerPlayerEntity player = event.getPlayer();
            Pokemon pokemon = event.getPokemon();

            if (player == null || pokemon == null) return;

            reward(player, pokemon);
        });
    }

    private void reward(ServerPlayerEntity player, Pokemon pokemon) {

        String rarity = pokemon.getSpecies().getRarity().name();

        switch (rarity) {
            case "COMMON" -> give(player, "numismatic-overhaul:copper_coin", 3);
            case "UNCOMMON" -> give(player, "numismatic-overhaul:copper_coin", 5);
            case "RARE" -> give(player, "numismatic-overhaul:silver_coin", 1);
            case "ULTRA_RARE" -> give(player, "numismatic-overhaul:silver_coin", 2);
            case "LEGENDARY" -> give(player, "numismatic-overhaul:gold_coin", 1);
        }
    }

    private void give(ServerPlayerEntity player, String itemId, int amount) {
        ItemStack stack = new ItemStack(
                Registries.ITEM.get(new Identifier(itemId)),
                amount
        );
        player.giveItemStack(stack);
    }
}

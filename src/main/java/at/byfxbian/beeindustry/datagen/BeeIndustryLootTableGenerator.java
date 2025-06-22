package at.byfxbian.beeindustry.datagen;

import at.byfxbian.beeindustry.BeeIndustry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class BeeIndustryLootTableGenerator extends FabricBlockLootTableProvider {

    public BeeIndustryLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> biConsumer) {
        super.accept(biConsumer);

        Identifier goldBeeLootTableId = Identifier.of(BeeIndustry.MOD_ID, "entities/custom_bee/gold_bee");
        RegistryKey<LootTable> goldBeeLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, goldBeeLootTableId);
        LootTable.Builder goldBeeLootTable = LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(Items.GOLD_NUGGET)
                                .weight(1)
                        )
                );
        biConsumer.accept(goldBeeLootTableKey, goldBeeLootTable);

        Identifier ironBeeLootTableId = Identifier.of(BeeIndustry.MOD_ID, "entities/custom_bee/iron_bee");
        RegistryKey<LootTable> ironBeeLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, ironBeeLootTableId);
        LootTable.Builder ironBeeLootTable = LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(Items.IRON_NUGGET)
                                .weight(1)
                        )
                );
        biConsumer.accept(ironBeeLootTableKey, ironBeeLootTable);
    }

    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>) ItemEntry.builder(item)
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops)))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE))))));
    }
}

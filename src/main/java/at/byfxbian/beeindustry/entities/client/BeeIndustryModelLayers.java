package at.byfxbian.beeindustry.entities.client;

import at.byfxbian.beeindustry.BeeIndustry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class BeeIndustryModelLayers {
    public static final EntityModelLayer FARMING_BEE_LAYER =
            new EntityModelLayer(Identifier.of(BeeIndustry.MOD_ID, "farming_bee"), "main");

    public static final EntityModelLayer MINING_BEE_LAYER =
            new EntityModelLayer(Identifier.of(BeeIndustry.MOD_ID, "mining_bee"), "main");

    public static final EntityModelLayer EMERALD_BEE_LAYER =
            new EntityModelLayer(Identifier.of(BeeIndustry.MOD_ID, "emerald_bee"), "main");
}

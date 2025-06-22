package at.byfxbian.beeindustry.entities.client;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.util.BeeRegistries;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class CustomBeeRenderer extends MobEntityRenderer<CustomBeeEntity, BeeEntityModel<CustomBeeEntity>> {
    public CustomBeeRenderer(EntityRendererFactory.Context context) {
        super(context, new BeeEntityModel<>(context.getPart(EntityModelLayers.BEE)), 0.5f);
        this.addFeature(new CustomBeeFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(CustomBeeEntity entity) {
        RegistryEntry<CustomBee> customBeeEntry = entity.getCustomBee();

        if(customBeeEntry != null) {
            String texturePath = customBeeEntry.value().beeTexture();
            //System.out.println("getTexture: CustomBee loaded with texture: " + texturePath);
            return Identifier.of(texturePath);
        }
        String beeType = entity.getBeeType();
        //System.out.println("getTexture: CustomBee is null, trying BeeType: " + beeType);

        Registry<CustomBee> registry = entity.getWorld().getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
        if(registry != null) {
            return registry.getEntry(RegistryKey.of(BeeRegistries.BEE_REGISTRY_KEY, Identifier.of(BeeIndustry.MOD_ID, beeType)))
                    .map(entry -> Identifier.of(entry.value().beeTexture()))
                    .orElse(Identifier.of(BeeIndustry.MOD_ID, "textures/entity/bee/example_bee.png"));
        }
        return Identifier.of(BeeIndustry.MOD_ID, "textures/entity/bee/example_bee.png");
    }
}

package at.byfxbian.beeindustry.entities.client;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.util.BeeRegistries;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Map;

public class CustomBeeRenderer extends MobEntityRenderer<CustomBeeEntity, EntityModel<CustomBeeEntity>> {

    private final Map<String, EntityModel<CustomBeeEntity>> models;

    public CustomBeeRenderer(EntityRendererFactory.Context context) {
        // Wir übergeben dem super-Konstruktor erstmal nur das Standard-Modell als Basis
        super(context, new BeeEntityModel<>(context.getPart(EntityModelLayers.BEE)), 0.5f);

        // Wir laden beim Erstellen des Renderers alle benötigten Modelle in unsere Map
        this.models = Map.of(
                "default", new BeeEntityModel<>(context.getPart(EntityModelLayers.BEE)),
                "farming_bee", new FarmingBeeModel(context.getPart(BeeIndustryModelLayers.FARMING_BEE_LAYER)),
                "mining_bee", new MiningBeeModel(context.getPart(BeeIndustryModelLayers.MINING_BEE_LAYER)),
                "emerald_bee", new EmeraldBeeModel(context.getPart(BeeIndustryModelLayers.EMERALD_BEE_LAYER))
        );

        // Wir entfernen den FeatureRenderer, da er nicht mehr gebraucht wird
        // this.addFeature(new CustomBeeFeatureRenderer(this)); // DIESE ZEILE LÖSCHEN
    }

    @Override
    public void render(CustomBeeEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        String beeType = mobEntity.getBeeType();
        this.model = this.models.getOrDefault(beeType, this.models.get("default"));

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(CustomBeeEntity entity) {
        // Deine getTexture-Methode bleibt unverändert und funktioniert weiterhin perfekt
        RegistryEntry<CustomBee> customBeeEntry = entity.getCustomBee();
        if(customBeeEntry != null) {
            return Identifier.of(customBeeEntry.value().beeTexture());
        }
        return Identifier.of(BeeIndustry.MOD_ID, "textures/entity/bee/example_bee.png"); // Sicherer Fallback
    }
}

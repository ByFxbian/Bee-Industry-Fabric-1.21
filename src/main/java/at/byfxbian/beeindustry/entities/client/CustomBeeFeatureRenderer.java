package at.byfxbian.beeindustry.entities.client;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CustomBeeFeatureRenderer extends FeatureRenderer<CustomBeeEntity, BeeEntityModel<CustomBeeEntity>> {
    public CustomBeeFeatureRenderer(FeatureRendererContext<CustomBeeEntity, BeeEntityModel<CustomBeeEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CustomBeeEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(entity.getCustomBee() != null) {
            BeeIndustry.LOGGER.debug("render custom bee not null");
            Identifier texture = Identifier.of(entity.getCustomBee().value().beeTexture());

            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}

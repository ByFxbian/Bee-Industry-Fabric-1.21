package at.byfxbian.beeindustry.entities.client;

import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class MiningBeeModel extends EntityModel<CustomBeeEntity> {
    private final ModelPart body;
    private final ModelPart stinger;
    private final ModelPart rightwing_bone;
    private final ModelPart leftwing_bone;
    private final ModelPart leg_front;
    private final ModelPart leg_mid;
    private final ModelPart leg_back;
    private final ModelPart helmet_top;
    private float bodyPitch;

    public MiningBeeModel(ModelPart root) {
        this.body = root.getChild("body");
        this.stinger = body.getChild("stinger");
        this.rightwing_bone = body.getChild("rightwing_bone");
        this.leftwing_bone = body.getChild("leftwing_bone");
        this.leg_front = body.getChild("leg_front");
        this.leg_mid = body.getChild("leg_mid");
        this.leg_back = body.getChild("leg_back");
        this.helmet_top = body.getChild("helmet_top");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -4.0F, -5.0F, 7.0F, 7.0F, 10.0F, new Dilation(0.0F))
                .uv(2, 0).cuboid(1.5F, -4.0F, -8.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(2, 3).cuboid(-2.5F, -4.0F, -8.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 19.0F, 0.0F));

        body.addChild("stinger", ModelPartBuilder.create().uv(26, 7).cuboid(0.0F, 0.0F, 4.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 1.0F));

        body.addChild("rightwing_bone", ModelPartBuilder.create().uv(0, 18).cuboid(-9.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -4.0F, -3.0F, 0.2618F, -0.2618F, 0.0F));
        body.addChild("leftwing_bone", ModelPartBuilder.create().uv(9, 24).cuboid(0.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -4.0F, -3.0F, 0.2618F, 0.2618F, 0.0F));
        body.addChild("leg_front", ModelPartBuilder.create().uv(26, 1).cuboid(-5.0F, 0.0F, 0.0F, 7.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 3.0F, -2.0F));
        body.addChild("leg_mid", ModelPartBuilder.create().uv(26, 3).cuboid(-5.0F, 0.0F, 0.0F, 7.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 3.0F, 0.0F));
        body.addChild("leg_back", ModelPartBuilder.create().uv(26, 5).cuboid(-5.0F, 0.0F, 0.0F, 7.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 3.0F, 2.0F));

        body.addChild("helmet_top", ModelPartBuilder.create().uv(36, 58).cuboid(-4.0F, 1.0F, -1.0F, 9.0F, 1.0F, 5.0F, new Dilation(0.0F))
                .uv(36, 10).cuboid(-3.0F, -1.0F, 0.0F, 7.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(39, 20).cuboid(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -5.0F, -5.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(CustomBeeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.rightwing_bone.pitch = 0.0F;
        this.body.pitch = 0.0F;
        this.helmet_top.pitch = 0.0F;
        boolean bl = entity.isOnGround() && entity.getVelocity().lengthSquared() < 1.0E-7;
        if (bl) {
            this.rightwing_bone.yaw = -0.2618F;
            this.rightwing_bone.roll = 0.0F;
            this.leftwing_bone.yaw = 0.2618F;
            this.leftwing_bone.roll = 0.0F;
            this.leg_front.pitch = 0.0F;
            this.leg_mid.pitch = 0.0F;
            this.leg_back.pitch = 0.0F;
        } else {
            float k = animationProgress * 120.32113F * (float) (Math.PI / 180.0);
            this.rightwing_bone.yaw = 0.0F;
            this.rightwing_bone.roll = MathHelper.cos(k) * (float)Math.PI * 0.15F;
            this.leftwing_bone.yaw = 0.0F;
            this.leftwing_bone.roll = -this.rightwing_bone.roll;
            this.leg_front.pitch = (float) (Math.PI / 4);
            this.leg_mid.pitch = (float) (Math.PI / 4);
            this.leg_back.pitch = (float) (Math.PI / 4);
            this.body.pitch = 0.0F;
            this.helmet_top.pitch = 0.0F;
            this.body.yaw = 0.0F;
            this.helmet_top.yaw = 0.0F;
            this.body.roll = 0.0F;
            this.helmet_top.roll = 0.0F;
        }

        if (!entity.hasAngerTime()) {
            this.body.pitch = 0.0F;
            this.helmet_top.pitch = 0.0F;
            this.body.yaw = 0.0F;
            this.helmet_top.yaw = 0.0F;
            this.body.roll = 0.0F;
            this.helmet_top.roll = 0.0F;
            if (!bl) {
                float k = MathHelper.cos(animationProgress * 0.18F);
                this.body.pitch = 0.1F + k * (float) Math.PI * 0.025F;
                this.helmet_top.pitch = 0.1F + k * (float) Math.PI * 0.025F;
                this.leg_front.pitch = -k * (float) Math.PI * 0.1F + (float) (Math.PI / 8);
                this.leg_back.pitch = -k * (float) Math.PI * 0.05F + (float) (Math.PI / 4);
                this.body.pivotY = 19.0F - MathHelper.cos(animationProgress * 0.18F) * 0.9F;
                this.helmet_top.pivotY = -5.2F - MathHelper.cos(animationProgress * 0.18F) * 0.1F;
            }
        }

        if (this.bodyPitch > 0.0F) {
            this.body.pitch = ModelUtil.interpolateAngle(this.body.pitch, 3.0915928F, this.bodyPitch);
        }
    }

    @Override
    public void animateModel(CustomBeeEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
        this.bodyPitch = entity.getBodyPitch(tickDelta);
        this.stinger.visible = !entity.hasStung();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        body.render(matrices, vertices, light, overlay, color);
    }
}

package at.byfxbian.beeindustry.components;

import at.byfxbian.beeindustry.BeeIndustry;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;

public class BeeIndustryDataComponentTypes {

    public static final ComponentType<String> BEE_TYPE = register("bee_type", builder -> builder
            .codec(Codec.STRING)
            .packetCodec(PacketCodecs.STRING)
    );

    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(BeeIndustry.MOD_ID, name),
                (builderOperator.apply(ComponentType.builder())).build());
    }

    public static void registerDataComponentTypes() {
        BeeIndustry.LOGGER.info("Registering Data Component Types for " + BeeIndustry.MOD_ID);
    }
}

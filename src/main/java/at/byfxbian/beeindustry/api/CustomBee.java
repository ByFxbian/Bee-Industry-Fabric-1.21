package at.byfxbian.beeindustry.api;

import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.util.BeeRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;

public record CustomBee(
        String name,
        String primaryColor,
        String secondaryColor,
        String pollenColor,
        String description,
        String beeTexture,
        boolean createNectar,
        String flowerBlockTag,
        float size,
        boolean translucent,
        boolean fireproof,
        Attributes attributes
) {
    public static final Codec<CustomBee> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(CustomBee::name),
            Codec.STRING.fieldOf("primaryColor").forGetter(CustomBee::primaryColor),
            Codec.STRING.fieldOf("secondaryColor").forGetter(CustomBee::secondaryColor),
            Codec.STRING.fieldOf("pollenColor").forGetter(CustomBee::pollenColor),
            Codec.STRING.fieldOf("description").forGetter(CustomBee::description),
            Codec.STRING.fieldOf("beeTexture").forGetter(CustomBee::beeTexture),
            Codec.BOOL.fieldOf("createNectar").forGetter(CustomBee::createNectar),
            Codec.STRING.fieldOf("flowerBlockTag").forGetter(CustomBee::flowerBlockTag),
            Codec.FLOAT.fieldOf("size").forGetter(CustomBee::size),
            Codec.BOOL.fieldOf("translucent").forGetter(CustomBee::translucent),
            Codec.BOOL.fieldOf("fireproof").forGetter(CustomBee::fireproof),
            Attributes.CODEC.fieldOf("attributes").forGetter(CustomBee::attributes)
    ).apply(instance, CustomBee::new));

    /*public static final PacketCodec<RegistryByteBuf, CustomBee> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            CustomBee::name,
            PacketCodecs.STRING,
            CustomBee::primaryColor,
            PacketCodecs.STRING,
            CustomBee::secondaryColor,
            PacketCodecs.STRING,
            CustomBee::pollenColor,
            PacketCodecs.STRING,
            CustomBee::description,
            PacketCodecs.STRING,
            CustomBee::beeTexture,
            PacketCodecs.BOOL,
            CustomBee::createNectar,
            PacketCodecs.STRING,
            CustomBee::flowerBlockTag,
            PacketCodecs.FLOAT,
            CustomBee::size,
            PacketCodecs.BOOL,
            CustomBee::translucent,
            PacketCodecs.BOOL,
            CustomBee::fireproof,
            Attributes.PACKET_CODEC,
            CustomBee::attributes,
            CustomBee::new
    );*/


    public static record Attributes(
            int productivity,
            int temper,
            int speed
    ) {
        public static final Codec<Attributes> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("productivity").forGetter(Attributes::productivity),
            Codec.INT.fieldOf("temper").forGetter(Attributes::temper),
            Codec.INT.fieldOf("speed").forGetter(Attributes::speed)
        ).apply(instance, Attributes::new));

        public static final PacketCodec<RegistryByteBuf, Attributes> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.INTEGER,
                Attributes::productivity,
                PacketCodecs.INTEGER,
                Attributes::temper,
                PacketCodecs.INTEGER,
                Attributes::speed,
                Attributes::new
        );
    }

    public static final Codec<RegistryEntry<CustomBee>> ENTRY_CODEC = RegistryElementCodec.of(BeeRegistries.BEE_REGISTRY_KEY, CODEC);
    /*public static final PacketCodec<RegistryByteBuf, RegistryEntry<CustomBee>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(
          BeeRegistries.BEE_REGISTRY_KEY, PACKET_CODEC
    );*/

    public static CustomBee of(
            String name,
            String primaryColor,
            String secondaryColor,
            String pollenColor,
            String description,
            String beeTexture,
            boolean createNectar,
            String flowerBlockTag,
            float size,
            boolean translucent,
            boolean fireproof,
            Attributes attributes
    ) {
        return new CustomBee(
                name,
                primaryColor,
                secondaryColor,
                pollenColor,
                description,
                beeTexture,
                createNectar,
                flowerBlockTag,
                size,
                translucent,
                fireproof,
                attributes
        );
    }
}

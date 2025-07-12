package at.byfxbian.beeindustry.networking.payload;

import at.byfxbian.beeindustry.BeeIndustry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record ToggleBeeSlotPayload(BlockPos pos, int slotIndex) implements CustomPayload {

    public static final CustomPayload.Id<ToggleBeeSlotPayload> ID = new CustomPayload.Id<>(Identifier.of(BeeIndustry.MOD_ID, "toggle_bee_slot"));

    public static final PacketCodec<RegistryByteBuf, ToggleBeeSlotPayload> CODEC = PacketCodec.of(
            (value, buf) -> {
                buf.writeBlockPos(value.pos());
                buf.writeInt(value.slotIndex());
            },
            (buf) -> new ToggleBeeSlotPayload(buf.readBlockPos(), buf.readInt())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

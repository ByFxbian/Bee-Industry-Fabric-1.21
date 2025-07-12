package at.byfxbian.beeindustry.networking;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.networking.payload.ToggleBeeSlotPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.util.Identifier;

public class BeeIndustryNetworking {
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(ToggleBeeSlotPayload.ID, ToggleBeeSlotPayload.CODEC);
    }

    public static void registerS2CPackets() {

    }
}

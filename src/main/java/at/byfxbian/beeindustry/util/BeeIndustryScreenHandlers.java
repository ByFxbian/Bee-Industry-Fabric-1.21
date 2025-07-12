package at.byfxbian.beeindustry.util;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.screen.AdvancedBeehiveScreenHandler;
import at.byfxbian.beeindustry.screen.BeepostScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class BeeIndustryScreenHandlers {
    public static final ScreenHandlerType<AdvancedBeehiveScreenHandler> ADVANCED_BEEHIVE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(BeeIndustry.MOD_ID, "advanced_beehive"),
                    new ScreenHandlerType<>(AdvancedBeehiveScreenHandler::new, FeatureSet.empty()));

    public static final ScreenHandlerType<BeepostScreenHandler> BEEPOST_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(BeeIndustry.MOD_ID, "beepost"),
                    new ScreenHandlerType<>(BeepostScreenHandler::new, FeatureSet.empty()));

    public static void registerScreenHandlers() {
        BeeIndustry.LOGGER.info("Registering Screen Handlers for " + BeeIndustry.MOD_ID);
    }
}


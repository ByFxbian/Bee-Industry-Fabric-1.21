package at.byfxbian.beeindustry.screen;

import at.byfxbian.beeindustry.util.BeeIndustryScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class AdvancedBeehiveScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public AdvancedBeehiveScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5));
    }

    public AdvancedBeehiveScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(BeeIndustryScreenHandlers.ADVANCED_BEEHIVE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 5);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new BeeContainerSlot(inventory, 0, 26, 35));

        this.addSlot(new UpgradeSlot(inventory, 1, 62, 17));
        this.addSlot(new UpgradeSlot(inventory, 2, 62, 35));
        this.addSlot(new UpgradeSlot(inventory, 3, 62, 53));

        this.addSlot(new OutputSlot(inventory, 4, 116, 35));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if(slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(slotIndex < 5) {
                if(!this.insertItem(originalStack, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if(!this.insertItem(originalStack, 0, 5, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}

package at.byfxbian.beeindustry.screen;

import at.byfxbian.beeindustry.util.BeeIndustryScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class BeepostScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public BeepostScreenHandler(int syncId, PlayerInventory playerInventory){
        this(syncId, playerInventory, new SimpleInventory(24), new ArrayPropertyDelegate(6));
    }

    public BeepostScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(BeeIndustryScreenHandlers.BEEPOST_SCREEN_HANDLER, syncId);
        checkSize(inventory, 24);
        this.inventory = inventory;
        this.propertyDelegate = delegate;
        inventory.onOpen(playerInventory.player);

        this.addProperties(delegate);

        int beeRowY1 = 18;
        int beeRowY2 = 36;
        int beeRowY3 = 54;

        this.addSlot(new BeeContainerSlot(inventory, 0, 26, beeRowY1));
        this.addSlot(new BeeContainerSlot(inventory, 4, 26, beeRowY2));
        this.addSlot(new BeeContainerSlot(inventory, 8, 26, beeRowY3));

        this.addSlot(new UpgradeSlot(inventory, 1, 44, beeRowY1));
        this.addSlot(new UpgradeSlot(inventory, 2, 62, beeRowY1));
        this.addSlot(new UpgradeSlot(inventory, 3, 80, beeRowY1));

        this.addSlot(new UpgradeSlot(inventory, 5, 44, beeRowY2));
        this.addSlot(new UpgradeSlot(inventory, 6, 62, beeRowY2));
        this.addSlot(new UpgradeSlot(inventory, 7, 80, beeRowY2));

        this.addSlot(new UpgradeSlot(inventory, 9, 44, beeRowY3));
        this.addSlot(new UpgradeSlot(inventory, 10, 62, beeRowY3));
        this.addSlot(new UpgradeSlot(inventory, 11, 80, beeRowY3));

        this.addSlot(new UpgradeSlot(inventory, 12, 134, beeRowY1));
        this.addSlot(new UpgradeSlot(inventory, 13, 134, beeRowY2));
        this.addSlot(new UpgradeSlot(inventory, 14, 134, beeRowY3));

        this.addSlot(new FuelSlot(inventory, 15, 134, 90));

        /*this.addSlot(new OutputSlot(inventory, 16, 26, 90));
        this.addSlot(new OutputSlot(inventory, 17, 44, 90));
        this.addSlot(new OutputSlot(inventory, 18, 62, 90));
        this.addSlot(new OutputSlot(inventory, 19, 80, 90));
        this.addSlot(new OutputSlot(inventory, 20, 26, 108));
        this.addSlot(new OutputSlot(inventory, 21, 44, 108));
        this.addSlot(new OutputSlot(inventory, 22, 62, 108));
        this.addSlot(new OutputSlot(inventory, 23, 80, 108));*/
        this.addSlot(new OutputSlot(inventory, 16, 19, 99));
        this.addSlot(new OutputSlot(inventory, 17, 36, 89));
        this.addSlot(new OutputSlot(inventory, 18, 36, 110));
        this.addSlot(new OutputSlot(inventory, 19, 80, 90));
        this.addSlot(new OutputSlot(inventory, 20, 53, 99));
        this.addSlot(new OutputSlot(inventory, 21, 70, 89));
        this.addSlot(new OutputSlot(inventory, 22, 70, 110));
        this.addSlot(new OutputSlot(inventory, 23, 87, 99));


        int playerInvY = 140;
        addPlayerInventory(playerInventory, playerInvY);
        addPlayerHotbar(playerInventory, playerInvY + 58);
    }

    public BlockPos getPos() {
        return new BlockPos(this.propertyDelegate.get(0), this.propertyDelegate.get(1), this.propertyDelegate.get(2));
    }

    public PropertyDelegate getPropertyDelegate() {
        return this.propertyDelegate;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if(slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(slotIndex < this.inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if(!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
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

    private void addPlayerInventory(PlayerInventory playerInventory, int y) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, y + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory, int y) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, y));
        }
    }
}

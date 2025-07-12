package at.byfxbian.beeindustry.block.entity.custom;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.block.entity.BeeIndustryBlockEntities;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import at.byfxbian.beeindustry.screen.BeepostScreenHandler;
import at.byfxbian.beeindustry.util.BeeRegistries;
import at.byfxbian.beeindustry.util.ImplementedInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BeepostBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(24, ItemStack.EMPTY);

    private static final int[] BEE_SLOTS = {0, 4, 8};
    private static final int[] UPGRADE_SLOTS_BEE_1 = {1, 2, 3};
    private static final int[] UPGRADE_SLOTS_BEE_2 = {5, 6, 7};
    private static final int[] UPGRADE_SLOTS_BEE_3 = {9, 10, 11};
    private static final int[] BLOCK_UPGRADE_SLOTS = {12, 13, 14};
    private static final int FUEL_SLOT = 15;
    private static final int[] OUTPUT_SLOTS = {16, 17, 18, 19, 20, 21, 22, 23};

    private final boolean[] beeSlotsActive = {true, true, true};

    public enum WorkState {
        IDLE,
        WORKING
    }
    private WorkState currentState = WorkState.IDLE;
    private final List<UUID> assignedBeeUuids = new ArrayList<>();
    private int beeMissingTicks = 0;

    protected final PropertyDelegate propertyDelegate;

    public BeepostBlockEntity(BlockPos pos, BlockState state) {
        super(BeeIndustryBlockEntities.BEEPOST_BLOCK_ENTITY, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch(index) {
                    case 0: return BeepostBlockEntity.this.pos.getX();
                    case 1: return BeepostBlockEntity.this.pos.getY();
                    case 2: return BeepostBlockEntity.this.pos.getZ();

                    case 3: return BeepostBlockEntity.this.isBeeSlotActive(0) ? 1 : 0;
                    case 4: return BeepostBlockEntity.this.isBeeSlotActive(1) ? 1 : 0;
                    case 5: return BeepostBlockEntity.this.isBeeSlotActive(2) ? 1 : 0;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int size() {
                return 6;
            }
        };
    }

    public void setBeeSlotActive(int logicalSlotIndex, boolean active) {
        if (logicalSlotIndex >= 0 && logicalSlotIndex < 3) {
            this.beeSlotsActive[logicalSlotIndex] = active;
            markDirty();
        }
    }

    public boolean isBeeSlotActive(int logicalSlotIndex) {
        if (logicalSlotIndex >= 0 && logicalSlotIndex < 3) {
            return this.beeSlotsActive[logicalSlotIndex];
        }
        return false;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.beeindustry.beepost");
    }



    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BeepostScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        byte[] activeStates = new byte[3];
        for (int i = 0; i < 3; i++) {
            activeStates[i] = this.beeSlotsActive[i] ? (byte)1 : (byte)0;
        }
        nbt.putByteArray("beeSlotsActive", activeStates);
        if(!this.assignedBeeUuids.isEmpty()) {
            NbtList uuidList = new NbtList();
            for(UUID uuid : this.assignedBeeUuids) {
                uuidList.add(NbtHelper.fromUuid(uuid));
            }
            nbt.put("assignedBeeUuids", uuidList);
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        if (nbt.contains("beeSlotsActive")) {
            byte[] activeStates = nbt.getByteArray("beeSlotsActive");
            for (int i = 0; i < Math.min(activeStates.length, 3); i++) {
                this.beeSlotsActive[i] = activeStates[i] == 1;
            }
        }
        this.assignedBeeUuids.clear();
        if(nbt.contains("assignedBeeUuids", NbtElement.LIST_TYPE)) {
            NbtList uuidList = nbt.getList("assignedBeeUuids", NbtElement.INT_ARRAY_TYPE);

            for(NbtElement element : uuidList) {
                this.assignedBeeUuids.add(NbtHelper.toUuid(element));
            }
        }
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient || world.getTime() % 40 != 0) return;

        if (hasFreeWorkerSlot() && canWork()) {
            spawnWorkerBee();
        }
    }

    private void spawnWorkerBee() {
        if (world == null || world.isClient) return;
        for (int i = 0; i < 3; i++) {
            if (isBeeSlotActive(i)) {
                ItemStack container = getStack(BEE_SLOTS[i]);
                if (!container.isEmpty() && !container.contains(BeeIndustryDataComponentTypes.IS_BEE_WORKING)) {

                    String beeTypeIdString = container.get(BeeIndustryDataComponentTypes.STORED_BEE_TYPE);
                    if (beeTypeIdString == null) continue;

                    boolean hasTarget = checkForWork(beeTypeIdString);
                    if (!hasTarget) {
                        System.out.println("No work found for bee type " + beeTypeIdString + ". Bee will remain in post.");
                        continue;
                    }

                    Registry<CustomBee> registry = world.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
                    int finalI = i;
                    Optional.ofNullable(registry.get(Identifier.of(beeTypeIdString))).ifPresent(beeData -> {

                        CustomBeeEntity beeToSpawn = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(world);
                        if (beeToSpawn != null) {
                            beeToSpawn.setCustomBee(registry.getEntry(beeData));
                            beeToSpawn.setHivePos(this.pos);

                            beeToSpawn.workRange = 10 + getRangeBonus();

                            int efficiencyUpgrades = getBeeUpgradeCount(finalI, BeeIndustryItems.EFFICIENCY_UPGRADE);
                            int quantityUpgrades = getBeeUpgradeCount(finalI, BeeIndustryItems.QUANTITY_UPGRADE);

                            beeToSpawn.workSpeedModifier = 1.0f + (efficiencyUpgrades * 0.2f);
                            beeToSpawn.bonusQuantity = quantityUpgrades;

                            beeToSpawn.initializeWorkerGoals();

                            beeToSpawn.updatePosition(this.pos.getX() + 0.5, this.pos.getY() + 1.5, this.pos.getZ() + 0.5);
                            world.spawnEntity(beeToSpawn);

                            //this.assignedBeeUuid = beeToSpawn.getUuid();
                            this.assignedBeeUuids.add(beeToSpawn.getUuid());
                            container.set(BeeIndustryDataComponentTypes.IS_BEE_WORKING, true);

                            this.currentState = WorkState.WORKING;
                            markDirty();

                            System.out.println("Beepost spawned worker bee " + beeToSpawn.getUuid());
                        }
                    });

                    return;
                }
            }
        }
    }

    private boolean checkForWork(String beeTypeId) {
        if (beeTypeId.equals("beeindustry:farming_bee")) {
            return BlockPos.findClosest(this.pos, 10, 5,
                    p -> world.getBlockState(p).getBlock() instanceof CropBlock crop && crop.isMature(world.getBlockState(p))).isPresent();
        } else if (beeTypeId.equals("beeindustry:mining_bee")) {

            TagKey<Block> mineableTag = TagKey.of(RegistryKeys.BLOCK, Identifier.of("beeindustry:mineable_by_bee"));
            return BlockPos.findClosest(this.pos, 10, 10,
                    p -> world.getBlockState(p).isIn(mineableTag)).isPresent();
        } else if(beeTypeId.equals("beeindustry:fighting_bee")) {
            Box searchBox = new Box(this.pos).expand(10);
            return !world.getEntitiesByClass(HostileEntity.class, searchBox, (entity) -> true).isEmpty();
        }
        return false;
    }

    public void onWorkerBeeReturned(CustomBeeEntity bee) {
        this.assignedBeeUuids.remove(bee.getUuid());
        for (ItemStack drop : bee.getInventory()) {
            if (!drop.isEmpty()) {
                this.insertDrop(drop);
            }
        }
        bee.clearInventory();

        for (int slotIndex : BEE_SLOTS) {
            ItemStack stack = getStack(slotIndex);
            if (stack.contains(BeeIndustryDataComponentTypes.IS_BEE_WORKING)) {
                stack.remove(BeeIndustryDataComponentTypes.IS_BEE_WORKING);
                break;
            }
        }

        getStack(FUEL_SLOT).decrement(1);
        markDirty();
        System.out.println("Beepost received items from worker bee.");
    }

    private boolean hasFuel() {
        return !getStack(FUEL_SLOT).isEmpty();
    }

    public List<UUID> getAssignedBeeUuids() {
        return this.assignedBeeUuids;
    }

    public int getBeeUpgradeCount(int logicalBeeSlotIndex, Item upgradeItem) {
        int count = 0;
        int[] upgradeSlots = getUpgradeSlotsForBee(logicalBeeSlotIndex);
        for(int slotIndex : upgradeSlots) {
            ItemStack stack = getStack(slotIndex);
            if(stack.isOf(upgradeItem)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public int[] getUpgradeSlotsForBee(int logicalBeeSlotIndex) {
        switch (logicalBeeSlotIndex) {
            case 0: return UPGRADE_SLOTS_BEE_1;
            case 1: return UPGRADE_SLOTS_BEE_2;
            case 2: return UPGRADE_SLOTS_BEE_3;
            default: return new int[0];
        }
    }

    public int getRangeBonus() {
        int bonus = 0;
        for(int i = 0; i <= 2; i++) {
            ItemStack stack = getStack(BLOCK_UPGRADE_SLOTS[i]);
            if(stack.isOf(BeeIndustryItems.RANGE_UPGRADE)) {
                bonus += (stack.getCount() * 2);
            }
        }
        return bonus;
    }

    private boolean hasFreeWorkerSlot() {
        for (int i = 0; i < 3; i++) {
            if (isBeeSlotActive(i)) {
                ItemStack stack = getStack(BEE_SLOTS[i]);
                if (!stack.isEmpty() && !stack.contains(BeeIndustryDataComponentTypes.IS_BEE_WORKING)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canWork() {
        boolean hasWorker = false;
        for (int i = 0; i < 3; i++) {
            ItemStack stack = getStack(BEE_SLOTS[i]);
            if (!stack.isEmpty() && stack.isOf(BeeIndustryItems.BEE_CONTAINER)) {
                String beeId = stack.get(BeeIndustryDataComponentTypes.STORED_BEE_TYPE);
                if (beeId != null && (beeId.equals("beeindustry:farming_bee") || beeId.equals("beeindustry:mining_bee") || beeId.equals("beeindustry:fighting_bee"))) {
                    hasWorker = true;
                    break;
                }
            }
        }
        boolean hasFuel = !getStack(FUEL_SLOT).isEmpty();
        return hasWorker && hasFuel;
    }

    public void insertDrop(ItemStack drop) {
        for (int slotIndex : OUTPUT_SLOTS) {
            ItemStack outputStack = this.getStack(slotIndex);
            if (outputStack.isEmpty()) {
                this.setStack(slotIndex, drop.copy());
                return;
            }
            if (ItemStack.areItemsAndComponentsEqual(outputStack, drop) && outputStack.getCount() < outputStack.getMaxCount()) {
                int amountToInsert = Math.min(drop.getCount(), outputStack.getMaxCount() - outputStack.getCount());
                outputStack.increment(amountToInsert);
                drop.decrement(amountToInsert);
                if (drop.isEmpty()) {
                    return;
                }
            }
        }
    }
}

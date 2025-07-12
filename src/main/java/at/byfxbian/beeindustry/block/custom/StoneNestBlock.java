package at.byfxbian.beeindustry.block.custom;

import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.block.entity.custom.DirtNestBlockEntity;
import at.byfxbian.beeindustry.block.entity.custom.StoneNestBlockEntity;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.CustomBees;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import at.byfxbian.beeindustry.util.BeeRegistries;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoneNestBlock extends BlockWithEntity {
    public static final MapCodec<StoneNestBlock> CODEC = createCodec(StoneNestBlock::new);

    public StoneNestBlock(Settings settings) {
        super(settings);
    }

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneNestBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient){
            if(player.getStackInHand(Hand.MAIN_HAND).isOf(BeeIndustryItems.BEE_SMOKER)) {
                CustomBeeEntity beeToSpawn = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(world);
                if(beeToSpawn != null) {
                    Registry<CustomBee> registry = world.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY);
                    registry.getEntry(CustomBees.STONE).ifPresent(beeToSpawn::setCustomBee);

                    beeToSpawn.updatePosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                    world.spawnEntity(beeToSpawn);
                    world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ((ServerWorld) world).spawnParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.0);

                    world.setBlockState(pos, Blocks.STONE.getDefaultState());

                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}

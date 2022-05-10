package io.github.changwook987.example.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.datafixer.fix.EntitySkeletonSplitFix;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class ExampleBlock extends Block {
    private WorldAccess world;
    private BlockPos pos;
    private BlockState state;
    public ExampleBlock(Settings settings) {
        super(settings);
    }
    private static class Broken {
        private WorldAccess world;
        private BlockPos pos;
        private BlockState state;
        public Broken(WorldAccess world, BlockPos pos, BlockState state) {
            this.world = world;
            this.pos = pos;
            this.state = state;
        }
        public WorldAccess getWorld() {
            return world;
        }
        public BlockPos getPos() {
            return pos;
        }
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        Random random = new Random();
        int number = random.nextInt(10) + 2;
        for(int i = 0; i < number; i++) {
            ItemStack itemStack = new ItemStack(Items.DIAMOND);
            ItemEntity itemEntity = new ItemEntity((World) world, pos.getX(), pos.getY() + 1, pos.getZ(), itemStack);
            world.spawnEntity(itemEntity);
        }
    }
}

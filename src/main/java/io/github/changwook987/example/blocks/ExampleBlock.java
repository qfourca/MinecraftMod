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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class ExampleBlock extends Block {
    public ExampleBlock(Settings settings) {
        super(settings);

        actions.add(this::summonLightningBolt);
        actions.add(this::summonTNT);
        actions.add(this::summonZombie);
        actions.add(this::summonCreeper);
    }

    private final Random random = new Random();
    private final List<Consumer<Broken>> actions = new ArrayList<>();

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

        public void setWorld(WorldAccess world) {
            this.world = world;
        }

        public BlockPos getPos() {
            return pos;
        }

        public void setPos(BlockPos pos) {
            this.pos = pos;
        }

        public BlockState getState() {
            return state;
        }

        public void setState(BlockState state) {
            this.state = state;
        }
    }

    private void summonLightningBolt(Broken broken) {
        LightningEntity bolt = new LightningEntity(EntityType.LIGHTNING_BOLT, (World) broken.getWorld());
        bolt.setPos(broken.getPos().getX(), broken.getPos().getY(), broken.getPos().getZ());

        broken.getWorld().spawnEntity(bolt);
    }

    private void summonTNT(Broken broken) {
        for (int i = 0; i < 10; i++) {
            TntEntity tnt = new TntEntity(EntityType.TNT, (World) broken.getWorld());
            tnt.setPos(broken.getPos().getX(), broken.getPos().getY(), broken.getPos().getZ());
            tnt.setVelocity(new Vec3d(random.nextDouble(-.5, .5), 1, random.nextDouble(-.5, .5)));

            broken.getWorld().spawnEntity(tnt);
        }
    }
    private void summonZombie(Broken broken) {
        for (int i = 0; i < 10; i++) {
            ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, (World) broken.getWorld());
            zombie.setPos(broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ());
            zombie.setVelocity(new Vec3d(random.nextDouble(-.5, .5), 1, random.nextDouble(-.5, .5)));

            broken.getWorld().spawnEntity(zombie);
        }
    }
    private void summonCreeper(Broken broken) {
        for (int i = 0; i < 10; i++) {
            CreeperEntity creeper = new CreeperEntity(EntityType.CREEPER, (World) broken.getWorld());
            creeper.setPos(broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ());
            creeper.setVelocity(new Vec3d(random.nextDouble(-.5, .5), 1, random.nextDouble(-.5, .5)));

            broken.getWorld().spawnEntity(creeper);
        }
    }
    private void summonItem(Broken broken) {
        for (int i = 0; i < 10; i++) {
            dropStack(); item = new ItemEntity(EntityType.ITEM, (World) broken.getWorld());
            item.setPos(broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ());
            item.setVelocity(new Vec3d(random.nextDouble(-.5, .5), 1, random.nextDouble(-.5, .5)));

            broken.getWorld().spawnEntity(item);
        }
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        actions.get(random.nextInt(0, actions.size())).accept(new Broken(world, pos, state));
        super.onBroken(world, pos, state);
    }
}

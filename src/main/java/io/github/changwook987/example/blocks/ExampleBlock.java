package io.github.changwook987.example.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.CatEntity;
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


public class ExampleBlock extends Block { //Block 클래스를 상속받아 Example Block 생성
    private WorldAccess world;
    private BlockPos pos;
    private BlockState state;
    public ExampleBlock(Settings settings) {
        super(settings);
        actions.add(this::dropRandomDiamond); //실행할 함수들 등록
        actions.add(this::dropDiamondItems);
        actions.add(this::summonCat);
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
        public BlockPos getPos() {
            return pos;
        }
    }
    public void dropRandomDiamond (Broken broken) { // 랜덤 다이아 드롭 함수
        Random random = new Random();
        int number = random.nextInt(8) + 2; // 2 에서 8개의 다이아 생성
        for(int i = 0; i < number; i++) {
            ItemStack itemStack = new ItemStack(Items.DIAMOND);
            ItemEntity itemEntity = new ItemEntity((World) broken.getWorld(), broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ(), itemStack);
            broken.getWorld().spawnEntity(itemEntity);
        }
    }

    public void dropDiamondItems (Broken broken) {
        Item[] arr = {
                Items.DIAMOND_HELMET,
                Items.DIAMOND_SWORD,
                Items.DIAMOND_BOOTS,
                Items.DIAMOND_LEGGINGS,
                Items.DIAMOND_CHESTPLATE
        }; // 다이아 아이템들
        for (Item item : arr) { //다이아 아이템 하나씩 드롭
            ItemStack itemStack = new ItemStack(item);
            ItemEntity itemEntity = new ItemEntity((World) broken.getWorld(), broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ(), itemStack);
            broken.getWorld().spawnEntity(itemEntity);
        }
    }
    public void summonCat (Broken broken) { //고양이 소환 함수
        CatEntity cat = new CatEntity(EntityType.CAT, (World) broken.getWorld());
        cat.setPos(broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ());
        broken.getWorld().spawnEntity(cat);

//        WolfEntity wolf = new WolfEntity(EntityType.WOLF, (World) broken.getWorld());
//        wolf.setPos(broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ());
//        broken.getWorld().spawnEntity(wolf);
    }
    private void summonZombie(Broken broken) { //좀비 소환 함수
        for (int i = 0; i < 10; i++) {
            ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, (World) broken.getWorld());
            zombie.setPos(broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ());
            zombie.setVelocity(new Vec3d(random.nextDouble(-.5, .5), 1, random.nextDouble(-.5, .5)));
            broken.getWorld().spawnEntity(zombie);
        }
    }
    private void summonCreeper(Broken broken) { //크리퍼 소환 함수
        for (int i = 0; i < 10; i++) {
            CreeperEntity creeper = new CreeperEntity(EntityType.CREEPER, (World) broken.getWorld());
            creeper.setPos(broken.getPos().getX(), broken.getPos().getY() + 1, broken.getPos().getZ());
            creeper.setVelocity(new Vec3d(random.nextDouble(-.5, .5), 1, random.nextDouble(-.5, .5)));
            broken.getWorld().spawnEntity(creeper);
        }
    }
    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) { // 블록을 부쉈을 때 발생하는 함수
        actions.get(random.nextInt(0, actions.size())).accept(new Broken(world, pos, state)); // 일어날 이벤트 중 하나를 정한다
        super.onBroken(world, pos, state); // 이벤트 실행
    }
}

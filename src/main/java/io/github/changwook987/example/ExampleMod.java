package io.github.changwook987.example;

import io.github.changwook987.example.blocks.ExampleBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("tutorial", "example_block"), EXAMPLE_BLOCK);
        Registry.register(
                Registry.ITEM,
                new Identifier("tutorial", "example_block"),
                new BlockItem(EXAMPLE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC))
        );
    }

    public static Block EXAMPLE_BLOCK = new ExampleBlock(FabricBlockSettings.of(Material.METAL).strength(4f));
}

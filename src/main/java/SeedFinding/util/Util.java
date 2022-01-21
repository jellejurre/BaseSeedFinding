package SeedFinding.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import nl.jellejurre.seedchecker.SeedChecker;

public class Util {

    public static int getTopBlockCount(SeedChecker checker, Block block, Box box) {
        return getTopBlockCount(checker, block, box,
            new ArrayList<>(Arrays.asList(Blocks.AIR, Blocks.WATER, Blocks.CAVE_AIR)));
    }

    public static int getTopBlockCount(SeedChecker checker, Block block, Box box,
                                       List<Block> above) {
        int count = 0;
        List<Block> checkBlocks =
            new ArrayList<>(Arrays.asList(Blocks.AIR, Blocks.WATER, Blocks.CAVE_AIR));
        for (int x = (int) box.minX; x <= (int) box.maxX; x++) {
            for (int z = (int) box.minZ; z <= (int) box.maxZ; z++) {
                int y = 90;
                Block checkblock;
                BlockPos pos;
                do {
                    pos = new BlockPos(x, y, z);
                    checkblock = checker.getBlock(pos);
                    y--;
                } while (checkBlocks.contains(checkblock));
                if (checkblock == block && above.contains(checker.getBlock(pos.add(0, 1, 0)))) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int goodEyeCount(SeedChecker checker, Box box) {
        int count = 0;
        for (int x = (int) box.minX; x <= (int) box.maxX; x++) {
            for (int z = (int) box.minZ; z <= (int) box.maxZ; z++) {
                for (int y = 80; y > 0; y--) {
                    BlockState state = checker.getBlockState(x, y, z);
                    if (state.getBlock() == Blocks.END_PORTAL_FRAME &&
                        ((Boolean) state.getEntries().get(BooleanProperty.of("eye")))) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static BlockPos locateBlockFromTop(SeedChecker checker, Block block, Box box) {
        return locateBlockFromTop(checker, block, box,
            new ArrayList<>(Arrays.asList(Blocks.AIR, Blocks.WATER, Blocks.CAVE_AIR)));
    }

    public static BlockPos locateBlockFromTop(SeedChecker checker, Block block, Box box,
                                              List<Block> above) {
        List<Block> checkBlocks =
            new ArrayList<>(Arrays.asList(Blocks.AIR, Blocks.WATER, Blocks.CAVE_AIR));
        for (int x = (int) box.minX; x <= (int) box.maxX; x++) {
            for (int z = (int) box.minZ; z <= (int) box.maxZ; z++) {
                int y = 90;
                Block checkblock;
                BlockPos pos;
                do {
                    pos = new BlockPos(x, y, z);
                    checkblock = checker.getBlock(pos);
                    y--;
                } while (checkBlocks.contains(checkblock));
                if (checkblock == block && above.contains(checker.getBlock(pos.add(0, 1, 0)))) {
                    return pos;
                }
            }
        }
        return new BlockPos(0, 0, 0);
    }

    public static int goodStrongholdEyeCount(SeedChecker checker, Box box) {
        int count = 0;
        for (int x = (int) box.minX; x <= (int) box.maxX; x++) {
            for (int z = (int) box.minZ; z <= (int) box.maxZ; z++) {
                for (int y = 80; y > 0; y--) {
                    BlockState state = checker.getBlockState(x, y, z);
                    if (state.getBlock() == Blocks.END_PORTAL_FRAME &&
                        ((Boolean) state.getEntries().get(
                            BooleanProperty.of("eye")))) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static ArrayList<Long> getSeedsFromFile(String s) {
        ArrayList<Long> seeds = new ArrayList<>();
        try {
            File f = new File(s);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                long seed = Long.parseLong(sc.nextLine().split(" ")[0]);
                seeds.add(seed);
            }
            sc.close();
            System.out.println("Finished reading loot seeds.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seeds;
    }
}

package SeedFinding;


import SeedFinding.Tasks.ExampleTask;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import kaptainwutax.mcutils.rand.seed.WorldSeed;
import kaptainwutax.mcutils.util.data.ThreadPool;

public class Main {
    static long StructureSeed = 0;
    static int index = 0;
    static ArrayList<Long> StructureSeeds;
    static ThreadPool pool =
        new ThreadPool((int) Math.ceil(Runtime.getRuntime().availableProcessors() * 3 / 4d));
    static Object lock = new Object();
    static Class<? extends Runnable> task;

    public static void main(String[] args) throws Exception {
        //Set task to run here
        task = ExampleTask.class;

        //Different ways to run project with preset list of seeds
//        StructureSeeds = getStructureSeedsFromLootSeeds(getSeedsFromFile("treasureSeeds.txt"););
//        StructureSeeds = getSeedsFromFile("16tnt_structureSeeds.txt");
//        StructureSeeds = new ArrayList<>(Arrays.asList(
//
//        ));
//        runFromList();

        //Run project with random structure seeds
        runRandom();
    }

    //Also change this if you change the run type
    public static void startNext() {
        startNextFromRandom();
    }

    public static void runRandom() {
        StructureSeed =
            WorldSeed.toStructureSeed(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE));
        for (int i = 0; i < 20; i++) {
            startNextFromRandom();
        }
    }

    public static void runFromList() {
        for (int i = 0; i < 200; i++) {
            startNextFromList();
        }
    }

    public static void startNextFromRandom() {
        synchronized (lock) {
            try {
                pool.run(task.getDeclaredConstructor(long.class).newInstance(StructureSeed));
            } catch (Exception e) {

            }
            StructureSeed = StructureSeed + 1;
        }
    }

    public static void startNextFromList() {
        synchronized (lock) {
            if (index != StructureSeeds.size()) {
                try {
                    pool.run(task.getDeclaredConstructor(long.class)
                        .newInstance(StructureSeeds.get(index)));
                } catch (Exception e) {
                    System.out.println(
                        "Class " + task.getName() + " does not have constructor taking long.");
                    e.printStackTrace();
                }
                index++;
            } else {
                if (pool.getThreadCount() == 1) {
                    pool.shutdown();
                    System.out.println("done");
                }
            }
        }
    }

    public static void print(String s) {
        synchronized (lock) {
            try {
                FileWriter fw = new FileWriter(new File("output.txt"), true);
                fw.append(s).append(String.valueOf('\n'));
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

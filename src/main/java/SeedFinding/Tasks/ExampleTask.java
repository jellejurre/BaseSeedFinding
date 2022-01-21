package SeedFinding.Tasks;

import static SeedFinding.Main.startNext;


import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.featureutils.structure.RuinedPortal;
import kaptainwutax.featureutils.structure.generator.structure.RuinedPortalGenerator;
import kaptainwutax.mcutils.rand.ChunkRand;
import kaptainwutax.mcutils.rand.seed.StructureSeed;
import kaptainwutax.mcutils.version.MCVersion;
import kaptainwutax.terrainutils.terrain.OverworldTerrainGenerator;
import nl.jellejurre.biomesampler.BiomeSampler;
import nl.jellejurre.seedchecker.SeedChecker;
import nl.kallestruik.noisesampler.NoiseSampler;
import nl.kallestruik.noisesampler.NoiseType;
import nl.kallestruik.noisesampler.minecraft.Dimension;

public class ExampleTask implements Runnable {
    private long structureSeed;

    public ExampleTask(long structureSeed) {
        this.structureSeed = structureSeed;
    }

    @Override
    public void run() {
        //How to use Jelle/DragonTamerFred's libraries
        NoiseSampler sampler = new NoiseSampler(structureSeed, Dimension.OVERWORLD);
        sampler.queryNoiseFromBlockPos(1, 100, 1, NoiseType.CAVE_CHEESE);
        BiomeSampler biomeSampler = new BiomeSampler(structureSeed, Dimension.OVERWORLD);
        biomeSampler.getBiomeFromBlockPos(1, 100, 1);
        SeedChecker checker = new SeedChecker(structureSeed);
        checker.getBlock(1, 100, 1);

        //How to use Wutax/Neil's libraries
        OverworldBiomeSource obs = new OverworldBiomeSource(MCVersion.v1_17, structureSeed);
        OverworldTerrainGenerator otg = new OverworldTerrainGenerator(obs);
        RuinedPortalGenerator generator = new RuinedPortalGenerator(MCVersion.v1_17);
        RuinedPortal portal = new RuinedPortal(kaptainwutax.mcutils.state.Dimension.OVERWORLD,
            MCVersion.v1_17);
        if (portal.canGenerate(1, 1, otg)) {
            generator.generate(otg, 1, 1, new ChunkRand());
            portal.getLoot(structureSeed, generator, new ChunkRand(), false);
        }

        //Not doing all biome seeds because if it cant find in 2000, it probably wont exist.
        for (int biomeSeed = 0; biomeSeed < 2000; biomeSeed++) {
            long worldSeed = StructureSeed.toWorldSeed(structureSeed, biomeSeed);
            //checks here
            if (worldSeed%1000000==0) {
                System.out.println("Seed: " + worldSeed);
                startNext();
                return;
            }
        }
        startNext();
    }
}

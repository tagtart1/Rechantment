package net.tagtart.rechantment.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tagtart.rechantment.Rechantment;

@Mod.EventBusSubscriber(modid = Rechantment.MOD_ID)
public class ParticleEmitter {
    private static int ticksRemaining = 0;
    private static int particlesPerTick = 0;
    private static Player player;
    private static ServerLevel serverLevel;

    public static void emitParticlesOverTime(Player p, ServerLevel level, int totalParticles, int totalTicks) {
        ticksRemaining = totalTicks;
        particlesPerTick = (int) Math.ceil((double) totalParticles / totalTicks);
        player = p;
        serverLevel = level;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (ticksRemaining > 0 && player != null && serverLevel != null) {
            ticksRemaining--;

            for (int i = 0; i < particlesPerTick; i++) {
                spawnParticles(player, serverLevel);
            }

            if (ticksRemaining == 0) {
                player = null;
                serverLevel = null;
            }
        }
    }

    private static void spawnParticles(Player player, ServerLevel level) {
        double speedX = (Math.random() - 0.5) * 2.0; // Random between -1.0 and 1.0
        double speedY = Math.random() * 1.0;         // Random Y direction between 0 and 1 (you can adjust)
        double speedZ = (Math.random() - 0.5) * 2.0; // Random between -1.0 and 1.0

        // Spawn the particle at the player's position with the random velocity
        level.sendParticles(
                ParticleTypes.SOUL_FIRE_FLAME,  // Particle type (this one has gravity by default)
                player.getX(),                   // X position
                player.getY() + 1,               // Y position (just above the player)
                player.getZ(),                   // Z position
                1,                               // Number of particles per spawn (1 per iteration)
                speedX,                          // Random speed in X
                speedY,                          // Random speed in Y
                speedZ,                          // Random speed in Z
                0.1                              // Speed multiplier
        );
        level.sendParticles(
                ParticleTypes.ENCHANT,  // Particle type (this one has gravity by default)
                player.getX(),                   // X position
                player.getY() + 1,               // Y position (just above the player)
                player.getZ(),                   // Z position
                1,                               // Number of particles per spawn (1 per iteration)
                speedX,                          // Random speed in X
                speedY,                          // Random speed in Y
                speedZ,                          // Random speed in Z
                0.1                              // Speed multiplier
        );
        level.sendParticles(
                ParticleTypes.ENCHANTED_HIT,  // Particle type (this one has gravity by default)
                player.getX(),                   // X position
                player.getY() + 1,               // Y position (just above the player)
                player.getZ(),                   // Z position
                1,                               // Number of particles per spawn (1 per iteration)
                speedX,                          // Random speed in X
                speedY,                          // Random speed in Y
                speedZ,                          // Random speed in Z
                0.1                              // Speed multiplier
        );
        level.sendParticles(
                ParticleTypes.FIREWORK,  // Particle type (this one has gravity by default)
                player.getX(),                   // X position
                player.getY() + 1,               // Y position (just above the player)
                player.getZ(),                   // Z position
                1,                               // Number of particles per spawn (1 per iteration)
                speedX,                          // Random speed in X
                speedY,                          // Random speed in Y
                speedZ,                          // Random speed in Z
                0.1                              // Speed multiplier
        );
    }
}

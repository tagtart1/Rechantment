package net.tagtart.rechantment.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class LoopingAmbientSound extends AbstractTickableSoundInstance {
    private boolean shouldStop = false;

    public LoopingAmbientSound(SoundEvent soundEvent, SoundSource category, double x, double y, double z) {
        super(soundEvent, category, RandomSource.create());
        this.looping = true; // Ensure the sound loops
        this.x = x;          // Sound's position in the world
        this.y = y;
        this.z = z;
        this.volume = 1.0F;  // Set the volume
        this.pitch = 1.0F;   // Set the pitch
    }

    @Override
    public void tick() {
        // Stop the sound if necessary
        if (shouldStop) {
            this.stop();
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void stopPlaying() {
        this.shouldStop = true;
    }
}
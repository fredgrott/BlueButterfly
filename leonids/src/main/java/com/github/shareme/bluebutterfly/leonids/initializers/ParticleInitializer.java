package com.github.shareme.bluebutterfly.leonids.initializers;

import com.github.shareme.bluebutterfly.leonids.Particle;

import java.util.Random;



public interface ParticleInitializer {

	void initParticle(Particle p, Random r);

}

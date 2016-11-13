package com.github.shareme.bluebutterfly.leonids.modifiers;


import com.github.shareme.bluebutterfly.leonids.Particle;

public interface ParticleModifier {

	/**
	 * modifies the specific value of a particle given the current miliseconds
	 * @param particle
	 * @param miliseconds
	 */
	void apply(Particle particle, long miliseconds);

}

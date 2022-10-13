package com.thermostate.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RandomStringGeneratorTest {
    @Test
    public void should_generate_a_random_string() {
        String res1 = new RandomStringGenerator().generate();
        String res2 = new RandomStringGenerator().generate();
        assertNotEquals(res1, res2);
        assertThat(res1.length()).isEqualTo(12);
    }
}
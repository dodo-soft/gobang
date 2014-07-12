package com.dodosoft.gobang.ai;

import org.junit.Test;


public class Example_StraightAiTest {

    @Test
    public void testOnYourTurn() {
        TestingFixture fixture = new TestingFixture(new Example_StraightAi());
        fixture.enemy.mark(10, 10);
        fixture.ai.mark(0, 0);
        fixture.enemy.mark(1, 0);
        fixture.ai.mark(2, 0);
        fixture.enemy.mark(3, 0);
        fixture.ai.mark(4, 0);
    }

}
package com.maven.archetype.designPattern.creation.factoryMethod;

import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.Sender;

public interface Provider {
    public Sender produce();
}

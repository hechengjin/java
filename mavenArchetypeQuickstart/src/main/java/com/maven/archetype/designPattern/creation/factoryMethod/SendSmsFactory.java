package com.maven.archetype.designPattern.creation.factoryMethod;

import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.Sender;
import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.SmsSender;

public class SendSmsFactory implements Provider {
    @Override
    public Sender produce() {
        return new SmsSender();
    }
}

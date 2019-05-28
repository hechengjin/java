package com.maven.archetype.designPattern.creation.simpleFactory.multiMethod;

import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.Sender;

public class FactoryTest {
    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        Sender sender = factory.produceMail();
        sender.Send();

        Sender sender2 = factory.produceSms();
        sender2.Send();
    }
}

package com.maven.archetype.designPattern.creation.simpleFactory.staticMethod;

import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.Sender;

public class FactoryTest {
    public static void main(String[] args) {
        Sender sender = SendFactory.produceMail();
        sender.Send();

        Sender sender2 = SendFactory.produceSms();
        sender2.Send();
    }
}

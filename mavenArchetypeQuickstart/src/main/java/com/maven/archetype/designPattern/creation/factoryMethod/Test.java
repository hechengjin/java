package com.maven.archetype.designPattern.creation.factoryMethod;

import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.Sender;

public class Test {
    public static void main(String[] args) {
        Provider provider = new SendMailFactory();
        Sender sender = provider.produce();
        sender.Send();

        Provider provider2 = new SendSmsFactory();
        Sender sender2 = provider2.produce();
        sender2.Send();
    }
}

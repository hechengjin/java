package com.maven.archetype.designPattern.creation.simpleFactory.ordinary;

public class FactoryTest {
    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        Sender sender = factory.produce("sms");
        sender.Send();

        Sender sender2 = factory.produce("mail");
        sender2.Send();
    }
}

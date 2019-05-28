package com.maven.archetype.designPattern.creation.simpleFactory.ordinary;

public class SmsSender implements Sender {
    @Override
    public void Send() {
        System.out.println("this is sms sender!");
    }
}

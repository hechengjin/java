package com.maven.archetype.designPattern.creation.simpleFactory.ordinary;

public class MailSender implements Sender {
    @Override
    public void Send() {
        System.out.println("this is mail sender!");
    }
}

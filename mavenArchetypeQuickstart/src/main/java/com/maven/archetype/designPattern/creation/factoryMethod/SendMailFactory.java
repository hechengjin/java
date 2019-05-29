package com.maven.archetype.designPattern.creation.factoryMethod;

import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.MailSender;
import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.Sender;

public class SendMailFactory implements Provider {
    @Override
    public Sender produce() {
        return new MailSender();
    }
}

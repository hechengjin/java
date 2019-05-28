package com.maven.archetype.designPattern.creation.simpleFactory.multiMethod;

import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.MailSender;
import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.Sender;
import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.SmsSender;

public class SendFactory {
    public Sender produceMail(){
        return new MailSender();
    }

    public Sender produceSms(){
        return new SmsSender();
    }
}

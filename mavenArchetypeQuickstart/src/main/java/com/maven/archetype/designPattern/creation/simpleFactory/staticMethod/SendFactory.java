package com.maven.archetype.designPattern.creation.simpleFactory.staticMethod;

import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.MailSender;
import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.Sender;
import com.maven.archetype.designPattern.creation.simpleFactory.ordinary.SmsSender;

public class SendFactory {
    public static Sender produceMail(){
        return new MailSender();
    }

    public static Sender produceSms(){
        return new SmsSender();
    }
}

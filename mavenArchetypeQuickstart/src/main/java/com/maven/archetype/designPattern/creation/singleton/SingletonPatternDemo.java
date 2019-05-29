package com.maven.archetype.designPattern.creation.singleton;

public class SingletonPatternDemo {
    public static void main(String[] args) {
        //获取唯一可用的对象
        Singleton singOobject = Singleton.getInstance();

        //显示消息
        singOobject.showMessage();
    }

}

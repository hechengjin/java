package com.maven.archetype.designPattern.creation.abstractFactory;

public class Green implements Color{
    @Override
    public void fill() {
        System.out.println("Inside Green::fill() method.");
    }
}

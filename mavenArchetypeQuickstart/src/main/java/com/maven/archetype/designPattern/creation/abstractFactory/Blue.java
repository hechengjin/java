package com.maven.archetype.designPattern.creation.abstractFactory;

public class Blue implements Color{
    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}

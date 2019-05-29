package com.maven.archetype.designPattern.creation.abstractFactory;

public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

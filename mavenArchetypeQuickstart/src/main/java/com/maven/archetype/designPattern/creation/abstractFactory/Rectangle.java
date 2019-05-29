package com.maven.archetype.designPattern.creation.abstractFactory;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

package com.kwicktask;

import com.kwicktask.utils.generator.EnumGenerator;

public class ClassGenerator {
    public static void main(String[] args) {
        EnumGenerator enumGenerator = new EnumGenerator(
                "src/main/resources/data/enum-types.xml",
                "src/main/java/com/kwicktask/enumtype",
                "com.kwicktask.utils.enumtype");
        enumGenerator.generate(false);
    }
}

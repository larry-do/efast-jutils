package com.efasttask;

import com.efasttask.utils.generator.EnumGenerator;

public class ClassGenerator {
    public static void main(String[] args) {
        EnumGenerator enumGenerator = new EnumGenerator(
                "src/main/resources/data/enum-types.xml",
                "src/main/java/com/efasttask/enumtype",
                "com.efasttask.utils.enumtype");
        enumGenerator.generate(false);
    }
}

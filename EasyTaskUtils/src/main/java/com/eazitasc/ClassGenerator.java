package com.eazitasc;

import com.eazitasc.generator.EnumGenerator;

public class ClassGenerator {
    public static void main(String[] args) {
        EnumGenerator enumGenerator = new EnumGenerator(
                "src/main/resources/data/enum-types.xml",
                "src/main/java/com/eazitasc/enumtype",
                "com.eazitasc.enumtype");
        enumGenerator.generate(false);
    }
}

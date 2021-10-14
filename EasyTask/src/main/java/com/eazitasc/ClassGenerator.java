package com.eazitasc;

import com.eazitasc.generator.EntityGenerator;
import com.eazitasc.generator.EnumGenerator;

import java.io.IOException;

public class ClassGenerator {
    public static void main(String[] args) throws IOException {
        EntityGenerator entityGenerator = new EntityGenerator(
                "src/main/resources/data/entities",
                "src/main/java/com/eazitasc/entity",
                "src/main/java/com/eazitasc/repository",
                "com.eazitasc.entity",
                "com.eazitasc.enumtype",
                "com.eazitasc.repository");
        entityGenerator.generate(false);

        EnumGenerator enumGenerator = new EnumGenerator(
                "src/main/resources/data/enum-types.xml",
                "src/main/java/com/eazitasc/enumtype",
                "com.eazitasc.enumtype");
        enumGenerator.generate(false);
    }
}

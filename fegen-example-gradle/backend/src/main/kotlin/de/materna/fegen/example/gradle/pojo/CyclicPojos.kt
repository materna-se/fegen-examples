package de.materna.fegen.example.gradle.pojo

data class CyclicTestPojoA(
        val b: CyclicTestPojoB
)

data class CyclicTestPojoB(
        val a: CyclicTestPojoA?
)

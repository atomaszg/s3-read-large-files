package com.galuszkat.s3largefiles.generator

import java.io.File

class CsvFileGenerator {


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Generating data started")

            val output = File("offers.csv")

            val totalCount: Long = 100_000_000
            val idStart: Long = 10000000000
            val idEnd: Long = idStart + totalCount

            val ids = mutableListOf<String>()
            for (id in idStart .. idEnd) {
                ids.add(id.toString())
            }
            println("Generating data finished")

            println("Generating CSV file")

            output.printWriter().use { out ->
                ids.forEach { out.println(it) }
            }

            println("Generating CSV file finished")
        }
    }
}
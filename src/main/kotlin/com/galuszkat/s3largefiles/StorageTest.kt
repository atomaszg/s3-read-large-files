package com.galuszkat.s3largefiles

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicLong


class StorageTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) =
            runBlocking {
                val bucketName = "test-offers"
                val fileName = "offers.csv"

                val objectStorageCrud = ObjectStorageReader()

                val counter = AtomicLong(0)

                launch(Dispatchers.Default) {
                    val startTime = System.currentTimeMillis() / 1000
                    while(true) {
                        delay(2000)
                        val currentTime = System.currentTimeMillis() / 1000
                        val elapsedTime = currentTime - startTime
                        val ops = counter.get() / elapsedTime
                        println("rq/s = $ops")
                    }
                }


                launch(Dispatchers.IO) {
                    objectStorageCrud.readFileContent(bucketName, fileName, delayMs = 0)
                        .onEach {
//                        println(it)
                            counter.incrementAndGet()
                        }
                        .collect()

                    println("end")
                }.join()
            }
    }


}

// https://kotlinlang.org/docs/channels.html#buffered-channels

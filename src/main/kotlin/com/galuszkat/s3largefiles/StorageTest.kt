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
                val counter = AtomicLong(0)

                val metrics = launch(Dispatchers.Default) {
                    val startTime = System.currentTimeMillis() / 1000
                    while (true) {
                        delay(2000)
                        val currentTime = System.currentTimeMillis() / 1000
                        val elapsedTime = currentTime - startTime
                        val ops = counter.get() / elapsedTime
                        println("rq/s = $ops")
                    }
                }

                val objectStorageCrud = ObjectStorageReader()
                launch(Dispatchers.IO) {
                    objectStorageCrud.readFileContent(bucketName, fileName, delayMs = 0)
                        .onEach { counter.incrementAndGet() }
                        .collect()
                }.join()
                metrics.cancel()
                println("Processed: ${counter.get()}")
            }
    }

}

// https://kotlinlang.org/docs/channels.html#buffered-channels

package com.galuszkat.s3largefiles

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GetObjectRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Scanner


class ObjectStorageReader(
    private val amazons3: AmazonS3 = ObjectStorageClientFactory.amazonS3()
) {

    fun readFileContent(bucketName: String, fileName: String, delayMs: Long = 0): Flow<String> =
        flow {
            println("Reading file: $fileName from bucket: $bucketName STARTED")

            val request = GetObjectRequest(bucketName, fileName)
            val response = amazons3.getObject(request)
            val scanner = Scanner(response.objectContent)

            scanner.use {
                while (it.hasNextLine()) {
                    emit(it.nextLine())
                    delay(delayMs)
                }
            }

            println("Reading file: $fileName from bucket: $bucketName FINISHED")
        }

}


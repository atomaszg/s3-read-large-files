package com.galuszkat.s3largefiles

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.retry.PredefinedRetryPolicies
import com.amazonaws.retry.RetryPolicy
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder


class ObjectStorageClientFactory {

    companion object {
        fun amazonS3(): AmazonS3 {
            val configuration = ClientConfiguration()
            configuration.signerOverride = ""
            configuration.retryPolicy = RetryPolicy(
                PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
                PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY, // increases exponentially from 100ms up to 20000ms amount of delay
                5,
                false
            )

            val endpointConfig = AwsClientBuilder.EndpointConfiguration(
                "",
                "us-east-1"
            )

            return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfig)
                .withClientConfiguration(configuration)
                .withCredentials(credentials("", ""))
                .withPathStyleAccessEnabled(true)
                .build()
        }

        private fun credentials(accessKey: String, secretKey: String): AWSCredentialsProvider {
            return object: AWSCredentialsProvider {
                override fun getCredentials(): AWSCredentials {
                    return BasicAWSCredentials(accessKey, secretKey)
                }

                override fun refresh() {}
            }
        }
    }


}


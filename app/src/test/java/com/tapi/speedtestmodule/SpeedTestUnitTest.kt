package com.tapi.speedtestmodule

import com.tapi.jspeedtest.SpeedTestReport
import com.tapi.jspeedtest.SpeedTestSocket
import com.tapi.jspeedtest.inter.ISpeedTestListener
import com.tapi.jspeedtest.model.SpeedTestError
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SpeedTestUnitTest {
    @Test
    fun test_upload() = runBlocking {
        val speedTestSocket = SpeedTestSocket()

        speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {
            override fun onCompletion(report: SpeedTestReport) {
                // called when download/upload is complete
                println("[COMPLETED] rate in octet/s : " + report.transferRateOctet)
                println("[COMPLETED] rate in bit/s   : " + report.transferRateBit)
            }

            override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
                println("[ERROR] onError : $errorMessage ${speedTestError.name}")
                // called when a download/upload error occur
            }

            override fun onProgress(percent: Float, report: SpeedTestReport) {
                // called to notify download/upload progress
                println("[PROGRESS] progress : $percent%")
                println("[PROGRESS] rate in octet/s : " + report.transferRateOctet)
                println("[PROGRESS] rate in bit/s   : " + report.transferRateBit)
            }
        })

        speedTestSocket.startUpload("http://speedtestkv1a.viettel.vn:8080/speedtest/upload.php", 10000000)
        delay(100000)
    }
}
import gnu.io.CommPortIdentifier
import gnu.io.SerialPort
import gnu.io.SerialPortEvent
import gnu.io.SerialPortEventListener
import org.apache.commons.lang.math.RandomUtils
import org.apache.commons.logging.LogFactory
import java.io.IOException

import org.apache.commons.lang.ArrayUtils
import java.io.InputStream


class WeighingScaleEventListener(private val portName: String): SerialPortEventListener {

    private val log = LogFactory.getLog(this.javaClass)

    var port: SerialPort? = null
    var portIdentifier: CommPortIdentifier


    init {
        try {
            portIdentifier = CommPortIdentifier.getPortIdentifier(portName)
            port = portIdentifier.open("Seanam-Scale", RandomUtils.nextInt(2000)) as SerialPort
            port!!.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE)
            port!!.flowControlMode = SerialPort.FLOWCONTROL_NONE
            port!!.addEventListener(this)
            port!!.notifyOnDataAvailable(true)
            log.info("Connected to $port, portName: $portName portID $portIdentifier")

        } catch (e: Exception) {
            close()
            throw e
        }
    }

    /**
     * Close connection to the port
     */

    private fun close() {
        if (port != null) {
            log.info("Closing serial connection $portName... ")
            port!!.close()
            port = null
        }
    }


    override fun serialEvent(p0: SerialPortEvent?) {
        val inStream: InputStream
        try {
            inStream = port!!.inputStream
            var value: Int
            var result = ByteArray(0)
            log.info("Some data to read")
            while (inStream.read().also { value = it } != -1) {
                result = ArrayUtils.add(result, value.toByte())
            }
            val stringValue = String(result, Charsets.UTF_8)
            log.info("Received value (" + stringValue.length + "): \"" + stringValue + "\"")
        } catch (e: IOException) {
            log.error(e, e)
            throw RuntimeException(e)
        }
    }
}
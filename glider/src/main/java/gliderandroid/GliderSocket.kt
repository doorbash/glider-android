package gliderandroid

import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class GliderSocket(val args: String) : Socket() {

        var conn: MyConnI? = null
        var connected = false
        var closed = false

        override fun isConnected(): Boolean {
//            println("isConnected(): $connected")
            return connected
        }

        private val inputStream = object : InputStream() {
            override fun read(): Int {
//                println("read()")
                val b = ByteArray(1)
                return if (read(b) == 1) b[0].toInt() else -1
            }

            override fun read(b: ByteArray): Int {
//                println("read(b.size=${b.size})")
                return conn?.read(b)?.toInt() ?: -1
            }

            override fun read(b: ByteArray, off: Int, len: Int): Int {
//                println("read(b.size=${b.size},off=$off,len=$len)")
                return conn?.readOffLen(b, off.toLong(), len.toLong())?.toInt() ?: -1
            }
        }

        private val outputStream = object : OutputStream() {
            override fun write(p0: Int) {
//                println("write($p0)")
                write(byteArrayOf(p0.toByte()))
            }

            override fun write(b: ByteArray) {
//                println("write(b.size=${b.size})")
                conn?.write(b)
            }

            override fun write(b: ByteArray, off: Int, len: Int) {
//                println("write(b.size=${b.size},off=$off,len=$len)")
                conn?.writeOffLen(b, off.toLong(), len.toLong())
            }
        }

        override fun getInputStream(): InputStream = inputStream

        override fun getOutputStream(): OutputStream = outputStream

        override fun isClosed(): Boolean {
//            println("isClosed(): $closed")
            return closed
        }

        override fun close() {
//            println("close()")
            try {
                conn?.close()
            } catch (_: Exception) {

            }
            closed = true
        }

        override fun setSoTimeout(timeout: Int) {
//            println("setSoTimeout($timeout)")
            conn?.setTimeout(timeout.toLong() * 1000)
        }

        override fun connect(endpoint: SocketAddress?) {
//            println("connect(${endpoint.toString()})")
            connect(endpoint, 10)
        }

        override fun connect(endpoint: SocketAddress?, timeout: Int) {
//            println("connect(${endpoint.toString()}, $timeout)")
//            println((endpoint as InetSocketAddress).hostName + ":" + endpoint.port)

            conn = try {
                gliderandroid.Gliderandroid.dial(args, (endpoint as InetSocketAddress).hostName + ":" + endpoint.port)
            } catch (e: Exception) {
//                println("error while connecting...")
//                println(e)
                null
            }

            connected = conn != null
        }
    }
package gliderandroid

import java.net.InetAddress
import java.net.Socket
import javax.net.SocketFactory

class GliderSocketFactory(val args: String) : SocketFactory() {
    override fun createSocket(): Socket = GliderSocket(args)

    override fun createSocket(host: String?, port: Int): Socket = GliderSocket(args)

    override fun createSocket(
        host: String?,
        port: Int,
        localHost: InetAddress?,
        localPort: Int
    ): Socket = GliderSocket(args)

    override fun createSocket(host: InetAddress?, port: Int): Socket = GliderSocket(args)

    override fun createSocket(
        address: InetAddress?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int
    ): Socket = GliderSocket(args)
}
import java.io.IOException
import java.lang.NullPointerException
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket


class ClientHandler {
    companion object {
        private const val port: Int = 8100
        private const val IP: String = "127.0.0.1"
        private var logger = Logger()

        @JvmStatic
        fun main(arg: Array<String>) {
            try{
                val serverSocket = ServerSocket()
                serverSocket.bind(InetSocketAddress(IP, port))
                while (serverSocket.isBound) {
                    val clientRequest = serverSocket.accept()
                    logger.info("${clientRequest.remoteSocketAddress} is connected")
                }
            }catch (io : IOException){
                logger.warning("${io.message}")
            }catch (n : NullPointerException){
                logger.warning("${n.message}")
            }

        }
    }
}
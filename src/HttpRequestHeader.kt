import java.io.*
import java.util.*


class HttpRequestHeader() {
    companion object Request{
        private var method: String = ""
        private var targetFilePath: String = ""
        private var mediaType: String = ""
        private var isDirectory: Boolean? = null
        private var contentLength: Int? = null
        private var contentType: String? = null
        private var ifModifiedSince: String? = null
        private var requestData: String? = null

        private val logger = Logger

        private fun phaseRequestPath(firstLine: String) {
            var tokenizer =  StringTokenizer(firstLine)
            this.method = tokenizer.nextToken()
            this.targetFilePath = tokenizer.nextToken()
            this.mediaType = targetFilePath.substring(targetFilePath.lastIndexOf(".") + 1)
            this.isDirectory = this.mediaType == this.targetFilePath
        }

       fun init(inputStream: InputStream){

           val stringBuffer = StringBuffer()
           val requestDataStream = BufferedReader(InputStreamReader(inputStream))
           var contentLengthString: String? = null
           try {
               phaseRequestPath(requestDataStream.readLine())
               while (requestDataStream.ready()) {
                   val line = requestDataStream.readLine()
                   if (line.startsWith("content-length"))
                        contentLengthString = line.substring(line.indexOf(" ") + 1)
                        contentLengthString = contentLengthString?: "0"
                        contentLength = Integer.valueOf(contentLengthString)
                   if (line.startsWith("Content-Type")) {
                        contentType = line.substring(line.indexOf(" ") + 1)
                   }
                   if (line.startsWith("If-Modified-Since")) {
                        ifModifiedSince = line.substring(line.indexOf(" ") + 1)
                   }
                   logger.debug("contentLength: $contentLength")
                   logger.debug("contentType: $contentType")
                   logger.debug("ifModifiedSince: $ifModifiedSince")
                   break
               }
               while (requestDataStream.ready()) {
                   stringBuffer.append(requestDataStream.read().toChar())
               }
               if (contentLength!! > 0)
                   requestData = stringBuffer.substring(stringBuffer.length - contentLength!!, stringBuffer.length)
               logger.debug("requestData: $requestData")
           } catch (io: IOException) {
               logger.warning("$io")
           }
       }
    }
}
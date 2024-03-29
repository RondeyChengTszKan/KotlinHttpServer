import java.time.LocalDateTime

class Logger{
    companion object {
        fun info(statement: String) {
            println("${LocalDateTime.now()}- [INFO] - $statement")
        }

        fun debug(statement: String) {
            println("${LocalDateTime.now()}- [DEBUG] - $statement")
        }

        fun warning(statement: String) {
            println("${LocalDateTime.now()}- [WARN] - $statement")
        }
    }
}


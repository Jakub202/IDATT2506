package ntnu.idatt2506.client

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(
    private val activity: ClientActivity,
    private val textView: TextView,
    private val SERVER_IP: String = "10.0.2.2",
    private val SERVER_PORT: Int = 12345
) {
    private lateinit var connection: Socket
    private val messagesReceived: ArrayList<String> = arrayListOf()
    private val editText = activity.findViewById<EditText>(R.id.editText_message)
    private val sendButton = activity.findViewById<View>(R.id.button_send)


    private var ui: String? = null
        set(value) {
            if (value != null) {
                messagesReceived.add(value)
                MainScope().launch { textView.text = messagesReceived.joinToString("\n") }
            }
            field = value
        }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                connection = Socket(SERVER_IP, SERVER_PORT)
                ui = "Koblet til server"
                listenForMessage()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Connection", e.message ?: "An error occurred")
                ui = "Error: ${e.message}"
            }
        }
    }

    private fun listenForMessage() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
                while (connection.isConnected) {
                    val message = reader.readLine() ?: break
                    Log.i("Message", "Server said: $message")
                    ui = "Til meg: $message"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Connection", e.message ?: "An error occurred")
                ui = e.message
            } finally {
                ui = "Socket is closed"
                connection.close()
            }
        }
    }

    init {
        sendButton.setOnClickListener {
            sendMessage(editText.text.toString())
            editText.text.clear()
        }
    }

    fun sendMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val writer = PrintWriter(connection.getOutputStream(), true)
                writer.println(message)
                Log.i("Message", "Client said: $message")
                ui = "Fra meg: $message"
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Connection", e.message ?: "An error occurred")
                ui = e.message
            }
        }
    }


}

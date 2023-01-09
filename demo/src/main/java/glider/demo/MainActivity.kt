package glider.demo

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import gliderandroid.GliderSocketFactory
import gliderandroid.toStringList
import okhttp3.CacheControl
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.InetAddress
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            val client: OkHttpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .socketFactory(GliderSocketFactory("-verbose -forward direct:// -dialtimeout 10"))
                .callTimeout(20, TimeUnit.SECONDS)
                .dns {
                    val addresses = gliderandroid.Gliderandroid.resolve(
                        "-verbose -forward doh://1.1.1.1",
                        it,
                        "8.8.8.8",
                        53
                    ).toStringList()
                    println("addresses: ${addresses.joinToString(", ")}")
                    addresses.map { address -> InetAddress.getByName(address) }
                }
                .build()

            try {
                val response = client.newCall(
                    Request.Builder()
                        .addHeader("Cache-Control", "no-cache")
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .url("http://ip-api.com/json")
                        .build()
                ).execute().body.string()

                println(response)

                val jsonObject = Gson().fromJson(response, JsonObject::class.java)

                runOnUiThread {
                    (findViewById<TextView>(R.id.text)).text =
                        GsonBuilder().setPrettyPrinting().create().toJson(jsonObject)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "error: ${e.message}")
                runOnUiThread {
                    (findViewById<TextView>(R.id.text)).text = e.message
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
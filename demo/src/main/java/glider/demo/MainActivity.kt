package glider.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import gliderandroid.GliderSocketFactory
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            val client: OkHttpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .socketFactory(GliderSocketFactory("--verbose -forward tls://api.ipify.org/"))
                .callTimeout(10, TimeUnit.SECONDS)
                .build()

            try {
                val response = client.newCall(
                    Request.Builder()
                        .addHeader("Cache-Control", "no-cache")
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .url("http://api.ipify.org/")
                        .build()
                ).execute().body.string()

                println(response)
            } catch (e : Exception) {
                Log.e(TAG, "error: ${e.message}")
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
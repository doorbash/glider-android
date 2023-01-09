# Glider-Android

## Download

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.doorbash:glider-android:1.0.0'
}
```

## Usage
```kotlin
val client: OkHttpClient = OkHttpClient.Builder()
  .retryOnConnectionFailure(false)
  .socketFactory(GliderSocketFactory("-forward tls://api.ipify.org/ -dialtimeout 10"))
  .callTimeout(10, TimeUnit.SECONDS)
  .dns {
    val address = gliderandroid.Gliderandroid.resolve(
        "-verbose -forward doh://1.1.1.1 -dialtimeout 10",
        it,
        "8.8.8.8",
        53
    )
    arrayListOf(InetAddress.getByName(address))
  }
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
```

## License
MIT
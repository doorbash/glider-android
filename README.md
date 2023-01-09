# Glider-Android

## Download

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.doorbash:glider-android:1.0.3'
}
```

## Usage
```kotlin
val client: OkHttpClient = OkHttpClient.Builder()
    .retryOnConnectionFailure(false)
    .socketFactory(GliderSocketFactory("-verbose -forward tls://api.ipify.org/ -dialtimeout 10"))
    .callTimeout(20, TimeUnit.SECONDS)
    .dns {
        val addresses = Gliderandroid.resolve(
            "-verbose -forward doh://1.1.1.1", // any proxy that supports DialUDP works
            it,
            "8.8.8.8",
            53 // must be 53 when using doh://
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
            .url("http://api.ipify.org/")
            .build()
    ).execute().body.string()

    println(response)
} catch (e: Exception) {
    e.printStackTrace()
}
```

## License
MIT
# Backend-Services-Android

Android client for https://github.com/doorbash/backend-services

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
```

## License
MIT
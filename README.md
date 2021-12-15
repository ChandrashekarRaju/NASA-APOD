# APOD

Project using NASA APOD (Astronomy Picture of the Day) API (see https://api.nasa.gov/)

*Features*
- Allow users to search for the picture for a date of their choice. Minimum and maximum date based are set in the date picker which prevents the user from selected the dates which are not supported by the api
- Allow users to create/manage a list of "favourite" listings by clicking on favourite icon
- Display date, explanation, Title and the image / video of the day. For Youtube video, we can directly retrieve the thumbnail of it. So for example, in the flux, we can find something like 
this: https://www.youtube.com/embed/ictZttw3c98?rel=0. The ID here, is ictZttw3c98, so we can find the thumbnail using this url: https://img.youtube.com/vi/ictZttw3c98/0.jpg. Like proposed on this useful [stackoverflow post](https://stackoverflow.com/questions/8841159/how-to-make-youtube-video-thumbnails-in-android/8842839#8842839)
- App will cache information and should display last updated information in case of
network unavailability.

## External dependencies

- [Retrofit](https://github.com/square/retrofit): A type-safe HTTP client for Android.
- For my database, I chose [Room](https://developer.android.com/topic/libraries/architecture/room) for the efficiency to handle entities and database access.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html): Light-weight thread implementation. I like the readability and the simplicity of coroutine.
- [Glide](https://github.com/bumptech/glide): Fast and efficient open source image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling. Easy to use and easily configurable, it was the perfect library for this test.
- [Hilt](https://dagger.dev/hilt/): I used Hilt for dependency injection.
- [Jetpack](https://developer.android.com/jetpack): I used the Jetpack libraries like Navigation, LiveData to follow best practices, reduce boilerplate code, and write code that works consistently across Android versions and devices
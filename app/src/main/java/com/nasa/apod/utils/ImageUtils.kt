package com.nasa.apod.utils

object ImageUtils {

    private const val YOUTUBE_NAME = "youtube"
    const val IMAGE_MEDIA_TYPE = "image"
    const val VIDEO_MEDIA_TYPE = "video"

    private fun isYoutubeVideo(url: String): Boolean {
        return url.contains(YOUTUBE_NAME)
    }

    /**
     * Format example: https://www.youtube.com/embed/ictZttw3c98?rel=0
     */
    fun getYoutubeThumbnail(url: String): String {
        return if(isYoutubeVideo(url)) {
            val youtubeId = url.split("?")[0].split("/").last()
            "https://img.youtube.com/vi/${youtubeId}/0.jpg"
        } else {
            url
        }
    }
}
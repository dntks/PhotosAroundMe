package com.dtks.photosaroundme.data.api

import com.dtks.photosaroundme.data.apimodel.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("rest/")
    suspend fun searchImagesAtCoordinates(
        @Query("nojsoncallback")
        noCallback: Int = 1,
        @Query("method")
        method: String = "flickr.photos.search",
        @Query("per_page")
        itemsPerPage: Int = 50,
        @Query("format")
        format: String = "json",
        @Query("radius_units")
        radius: Double = 1.0,
        @Query("lat")
        latitude: Double,
        @Query("lon")
        longitude: Double,
        @Query("api_key")
        apiKey: String = API_KEY
    ): SearchResponse

    companion object {
        private const val API_KEY = "be678ebb152208010df688c2df43b4a8"
        val secret = "3ff2792144b53d16"
    }
}
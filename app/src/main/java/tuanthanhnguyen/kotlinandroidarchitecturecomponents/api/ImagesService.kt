/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Tuan Thanh Nguyen modified this file.

package tuanthanhnguyen.kotlinandroidarchitecturecomponents.api

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.response.ImageResponse
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.response.SearchImageResultsResponse

interface ImagesService {

    @GET("photos")
    fun getImages(
        @Header(ApiConfig.AUTHORIZATION) publicAuthorization: String,
        @Query(ApiConfig.PAGE) pageNumber: Int,
        @Query(ApiConfig.PER_PAGE) picturePerPage: Int,
        @Query(ApiConfig.ORDER_BY) orderBy: String
    ): Flowable<List<ImageResponse>>

    @GET("search/photos")
    fun searchImages(
        @Header(ApiConfig.AUTHORIZATION) publicAuthorization: String,
        @Query(ApiConfig.QUERY) keyword: String,
        @Query(ApiConfig.PAGE) pageNumber: Int,
        @Query(ApiConfig.PER_PAGE) picturePerPage: Int,
        @Query(ApiConfig.ORDER_BY) orderBy: String
    ): Single<SearchImageResultsResponse>
}
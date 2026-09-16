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

object ApiConfig {

    /**
     * How to this application access the photos from the Unsplash API
     *
     * First, you must create your application in your Unsplash developer account to get the
     * access key value of this application of yours. Second, you must set the value of this
     * ACCESS_KEY constant to the access key value of this application of yours.
     */
    const val ACCESS_KEY = ""

    const val PUBLIC_AUTHORIZATION = "Client-ID $ACCESS_KEY"

    const val AUTHORIZATION = "Authorization"

    const val PHOTO_BASE_URL_UNSPLASH = "https://api.unsplash.com/"

    const val QUERY = "query"
    const val PAGE = "page"
    const val PER_PAGE = "per_page"
    const val ORDER_BY = "order_by"

    const val DEFAULT_PAGE = 1
    const val DEFAULT_PER_PAGE = 20
    const val DEFAULT_ORDER_BY = "latest"
}
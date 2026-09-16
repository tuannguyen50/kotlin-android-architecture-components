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

package tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.response.mapper

import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.response.PhotoUrlsResponse
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.model.PhotoUrls

object PhotoUrlsResponseMapper {

    fun photoUrlsResponseToPhotoUrls(photoUrlsResponse: PhotoUrlsResponse): PhotoUrls {
        return PhotoUrls(
            photoUrlsResponse.raw,
            photoUrlsResponse.full,
            photoUrlsResponse.regular,
            photoUrlsResponse.small,
            photoUrlsResponse.thumb
        )
    }
}
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

package tuanthanhnguyen.kotlinandroidarchitecturecomponents.repository

import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.ApiConfig
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.ApiConfig.PUBLIC_AUTHORIZATION
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.ImagesService
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.ImageDao
import io.reactivex.Flowable
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.vo.Image
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao,
    private val imagesService: ImagesService
) : ImagesRepository {

    override fun getImages(): Flowable<List<Image>> {
        return imagesService.getImages(
            PUBLIC_AUTHORIZATION,
            ApiConfig.DEFAULT_PAGE,
            ApiConfig.DEFAULT_PER_PAGE,
            ApiConfig.DEFAULT_ORDER_BY
        ).doOnNext { imageDao.upsertImages(it) }
    }
}
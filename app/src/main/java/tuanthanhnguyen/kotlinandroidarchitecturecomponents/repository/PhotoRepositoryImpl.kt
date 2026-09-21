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
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.PhotoService
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.PhotoDao
import io.reactivex.Flowable
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.response.mapper.PhotoResponseMapper
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.PhotoDb
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.UserDao
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.entity.mapper.PhotoEntityMapper
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.entity.mapper.UserEntityMapper
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.model.Photo
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDb: PhotoDb,
    private val photoDao: PhotoDao,
    private val userDao: UserDao,
    private val photoService: PhotoService
) : PhotoRepository {

    override fun getPhotos(): Flowable<List<Photo>> {
        return photoService.getPhotos(
            PUBLIC_AUTHORIZATION,
            ApiConfig.DEFAULT_PAGE,
            ApiConfig.DEFAULT_PER_PAGE,
            ApiConfig.DEFAULT_ORDER_BY
        ).map {
            return@map PhotoResponseMapper.photoResponsesToPhotos(it)
        }.doOnNext { photos ->
            photoDb.runInTransaction {
                photos.forEach { photo ->
                    userDao.upsertUserEntity(UserEntityMapper.userToUserEntity(photo.user))
                    photoDao.upsertPhotoEntity(PhotoEntityMapper.photoToPhotoEntity(photo))
                }
            }
        }
    }
}
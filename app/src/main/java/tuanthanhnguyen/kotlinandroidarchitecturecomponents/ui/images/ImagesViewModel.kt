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

package tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.toLiveData
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.repository.ImagesRepository
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.util.scheduler.SchedulerProvider
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.model.Image
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.common.result.Resource
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val _getImages: MutableLiveData<Boolean> = MutableLiveData()

    var images: LiveData<Resource<List<Image>>> = _getImages.switchMap {
        imagesRepository.getImages()
            .map { return@map Resource.success(it) }
            .onErrorReturn { e ->
                return@onErrorReturn Resource.failure(e.message)
            }
            .startWith(Resource.loading())
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .toLiveData()
    }

    fun getImages() {
        _getImages.value = true
    }

    fun retry() {
        _getImages.value = true
    }
}
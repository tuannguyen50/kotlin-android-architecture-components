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

package tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.photos

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.repository.PhotoRepository
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.util.scheduler.SchedulerProvider
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.model.Photo
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.common.result.Resource
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _photoListState = MutableLiveData<Resource<List<Photo>>>()

    val photoListState: LiveData<Resource<List<Photo>>> = _photoListState

    fun getPhotos() {
        _photoListState.value = Resource.loading()

        photoRepository.getPhotos()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(object : SingleObserver<List<Photo>> {
                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(photoList: List<Photo>) {
                    _photoListState.value = Resource.success(photoList)
                }

                override fun onError(e: Throwable) {
                    _photoListState.value = Resource.failure(e.message)
                }
            })
    }

    fun retry() {
        getPhotos()
    }

    @SuppressLint("EmptySuperCall")
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
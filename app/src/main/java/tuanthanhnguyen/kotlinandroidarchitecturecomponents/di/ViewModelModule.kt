/*
 * Copyright (C) 2018 The Android Open Source Project
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

package tuanthanhnguyen.kotlinandroidarchitecturecomponents.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.photos.PhotosViewModel
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.viewmodel.PhotosViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    abstract fun bindPhotosViewModel(photosViewModel: PhotosViewModel): ViewModel

    @Binds
    abstract fun bindPhotosViewModelFactory(
        photosViewModelFactory: PhotosViewModelFactory
    ): ViewModelProvider.Factory
}
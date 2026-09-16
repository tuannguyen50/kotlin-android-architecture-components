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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.R
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.databinding.ItemPhotoBinding
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.common.DataBoundListAdapter
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.model.Photo

class PhotosAdapter(
    private val dataBindingComponent: DataBindingComponent
) : DataBoundListAdapter<Photo, ItemPhotoBinding>() {

    override fun createBinding(parent: ViewGroup): ItemPhotoBinding {
        val binding = DataBindingUtil.inflate<ItemPhotoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_photo,
            parent,
            false,
            dataBindingComponent
        )
        return binding
    }

    override fun bind(binding: ItemPhotoBinding, item: Photo) {
        binding.photo = item
    }

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
                && oldItem.photoUrls.small == newItem.photoUrls.small
                && oldItem.user.name == newItem.user.name
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
                && oldItem.photoUrls.small == newItem.photoUrls.small
                && oldItem.user.name == newItem.user.name
    }
}
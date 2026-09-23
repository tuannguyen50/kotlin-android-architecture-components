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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.R
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.binding.FragmentDataBindingComponent
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.databinding.FragmentPhotoBinding
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.di.Injectable
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.common.GridSpacingItemDecoration
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.common.RetryCallback
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.util.ui.AutoClearedValue
import javax.inject.Inject

class PhotosFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val dataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by AutoClearedValue<FragmentPhotoBinding>(this)

    private var adapter by AutoClearedValue<PhotosAdapter>(this)

    private lateinit var photosViewModel: PhotosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_photo,
                container,
                false,
                dataBindingComponent
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        photosViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[PhotosViewModel::class.java]

        photosViewModel.photos.observe(viewLifecycleOwner, Observer {
            binding.resource = it
            adapter.replace(it.data)
            binding.executePendingBindings()
        })
        binding.callback = object : RetryCallback {
            override fun retry() {
                photosViewModel.retry()
            }
        }
        photosViewModel.getPhotos()
    }

    private fun initRecyclerView() {
        val gridSpacingItemInPixels =
            resources.getDimensionPixelSize(R.dimen.grid_photo_item_spacing)
        val includeEdge = true
        val spanCount = 2
        adapter = PhotosAdapter(dataBindingComponent)
        binding.photosRecyclerView.apply {
            this.layoutManager = GridLayoutManager(context, spanCount)
            this.addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount,
                    gridSpacingItemInPixels,
                    includeEdge
                )
            )
            this.adapter = this@PhotosFragment.adapter
        }
    }
}
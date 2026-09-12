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
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.databinding.FragmentImageBinding
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.di.Injectable
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.common.GridSpacingItemDecoration
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.common.NavigationController
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.ui.common.RetryCallback
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.util.AutoClearedValue
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.vo.Image
import javax.inject.Inject

class ImagesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigationController: NavigationController

    private val dataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var binding: AutoClearedValue<FragmentImageBinding>

    private lateinit var adapter: AutoClearedValue<ImagesAdapter>

    private lateinit var imagesViewModel: ImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding = DataBindingUtil
            .inflate<FragmentImageBinding>(
                inflater,
                R.layout.fragment_image,
                container,
                false,
                dataBindingComponent
            )
        binding = AutoClearedValue(this, dataBinding)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        imagesViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[ImagesViewModel::class.java]

        imagesViewModel.images.observe(viewLifecycleOwner, Observer {
            binding.get()?.resource = it
            adapter.get()?.replace(it?.data)
            binding.get()?.executePendingBindings()
        })
        binding.get()?.callback = object : RetryCallback {
            override fun retry() {
                imagesViewModel.retry()
            }
        }
        imagesViewModel.getImages()
    }

    private fun initRecyclerView() {
        val gridSpacingItemInPixels =
            resources.getDimensionPixelSize(R.dimen.grid_item_spacing)
        val includeEdge = true
        val spanCount = 2
        val imagesAdapter = ImagesAdapter(dataBindingComponent)
        binding.get()?.imageRecyclerView?.apply {
            this.layoutManager = GridLayoutManager(context, spanCount)
            this.addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount,
                    gridSpacingItemInPixels,
                    includeEdge
                )
            )
            this.adapter = imagesAdapter
        }
        adapter = AutoClearedValue(this, imagesAdapter)
    }
}
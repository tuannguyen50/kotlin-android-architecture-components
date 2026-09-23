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

package tuanthanhnguyen.kotlinandroidarchitecturecomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var mainActivityNavigation: MainActivityNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityNavigation = MainActivityNavigationImpl(
            this@MainActivity
        )

        // Add the photos fragment if the creation of this activity not from the recreation of
        // this activity the recreation of this activity then savedInstanceStatus at this onCreate
        // function will not be null because if add the photos fragment from the recreation of
        // this activity then the fragment manager will add the photos fragment and when the
        // recreation of this activity then the fragment manager will restore the photos fragment
        // added before then the fragment manager manages the fragment back stack this fragment
        // back stack has many the photos fragment
        if (savedInstanceState == null) {
            mainActivityNavigation.navigateToPhotosFragment()
        }
    }

    override fun androidInjector(): AndroidInjector<in Any> =
        dispatchingAndroidInjector as AndroidInjector<in Any>
}
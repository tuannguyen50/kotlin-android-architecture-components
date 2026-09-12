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

package tuanthanhnguyen.kotlinandroidarchitecturecomponents.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.vo.ImageUrlsList
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.vo.User

class ImageDataConverter {

    @TypeConverter
    fun imageUrlsListToImageUrlsListJsonText(imageUrlsList: ImageUrlsList): String =
        Gson().toJson(imageUrlsList)

    @TypeConverter
    fun imageUrlsListJsonTextToImageUrlsList(imageUrlsListJsonText: String): ImageUrlsList =
        Gson().fromJson(imageUrlsListJsonText, ImageUrlsList::class.java)

    @TypeConverter
    fun userToUserJsonText(user: User): String =
        Gson().toJson(user)

    @TypeConverter
    fun userJsonTextToUser(userJsonText: String): User =
        Gson().fromJson(userJsonText, User::class.java)
}
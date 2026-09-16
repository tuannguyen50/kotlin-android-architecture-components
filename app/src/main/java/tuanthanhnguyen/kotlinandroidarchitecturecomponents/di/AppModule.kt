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

package tuanthanhnguyen.kotlinandroidarchitecturecomponents.di

import android.app.Application
import androidx.room.Room
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.ApiConfig
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.PhotosService
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.DatabaseConfig
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.PhotoDao
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.PhotoDb
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.repository.PhotosRepository
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.repository.PhotosRepositoryImpl
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.util.scheduler.AppSchedulerProvider
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.util.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.UserDao
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providePhotosService(okHttpClient: OkHttpClient): PhotosService =
        Retrofit.Builder()
            .baseUrl(ApiConfig.PHOTO_BASE_URL_UNSPLASH)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PhotosService::class.java)

    @Singleton
    @Provides
    fun providePhotoDb(application: Application): PhotoDb =
        Room.databaseBuilder(
            application,
            PhotoDb::class.java,
            DatabaseConfig.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun providePhotoDao(photoDb: PhotoDb): PhotoDao =
        photoDb.photoDao()

    @Singleton
    @Provides
    fun provideUserDao(photoDb: PhotoDb): UserDao =
        photoDb.userDao()

    @Singleton
    @Provides
    fun providePhotosRepository(photosRepositoryImpl: PhotosRepositoryImpl): PhotosRepository =
        photosRepositoryImpl

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}
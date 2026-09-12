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
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.api.ImagesService
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.DatabaseConfig
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.ImageDao
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.db.ImageDb
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.repository.ImagesRepository
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.repository.ImagesRepositoryImpl
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.util.scheduler.AppSchedulerProvider
import tuanthanhnguyen.kotlinandroidarchitecturecomponents.util.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideGithubService(okHttpClient: OkHttpClient): ImagesService =
        Retrofit.Builder()
            .baseUrl(ApiConfig.IMAGE_BASE_URL_UNSPLASH)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ImagesService::class.java)

    @Singleton
    @Provides
    fun provideDb(app: Application): ImageDb =
        Room.databaseBuilder(
            app,
            ImageDb::class.java,
            DatabaseConfig.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideImageDao(db: ImageDb): ImageDao =
        db.imageDao()

    @Singleton
    @Provides
    fun provideImagesRepository(imagesRepositoryImpl: ImagesRepositoryImpl): ImagesRepository =
        imagesRepositoryImpl

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}
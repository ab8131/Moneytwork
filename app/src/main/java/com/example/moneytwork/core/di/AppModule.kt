package com.example.moneytwork.core.di

import android.app.Application
import androidx.room.Room
import com.example.moneytwork.core.constants.Constants
import com.example.moneytwork.data.local.database.MoneytworkDatabase
import com.example.moneytwork.data.remote.api.CoinGeckoApi
import com.example.moneytwork.data.remote.api.FinnhubApi
import com.example.moneytwork.data.repository.CryptoRepositoryImpl
import com.example.moneytwork.data.repository.PortfolioRepositoryImpl
import com.example.moneytwork.data.repository.StockRepositoryImpl
import com.example.moneytwork.domain.repository.CryptoRepository
import com.example.moneytwork.domain.repository.PortfolioRepository
import com.example.moneytwork.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinGeckoApi(okHttpClient: OkHttpClient): CoinGeckoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.COINGECKO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoApi::class.java)
    }
    @Provides
    @Singleton
    fun provideFinnhubApi(okHttpClient: OkHttpClient): FinnhubApi {
        return Retrofit.Builder()
            .baseUrl(Constants.FINNHUB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FinnhubApi::class.java)
    }


    @Provides
    @Singleton
    fun provideMoneytworkDatabase(app: Application): MoneytworkDatabase {
        return Room.databaseBuilder(
            app,
            MoneytworkDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoRepository(
        api: CoinGeckoApi,
        db: MoneytworkDatabase
    ): CryptoRepository {
        return CryptoRepositoryImpl(api, db.coinDao, db.watchlistDao)
    }

    @Provides
    @Singleton
    fun provideStockRepository(
        api: FinnhubApi,
        db: MoneytworkDatabase
    ): StockRepository {
        return StockRepositoryImpl(api, db.stockDao)
    }

    @Provides
    @Singleton
    fun providePortfolioRepository(
        db: MoneytworkDatabase,
        cryptoRepository: CryptoRepository,
        stockRepository: StockRepository
    ): PortfolioRepository {
        return PortfolioRepositoryImpl(db.transactionDao, cryptoRepository, stockRepository)
    }
}


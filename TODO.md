# MONEYTWORK - BUILD CHECKLIST

## ✅ Setup Complete
- [x] Firebase project created
- [x] Git repository initialized
- [x] Project created

---

## 📋 BUILD TASKS (Do in Order)

### TASK 1: Dependencies & Setup (30 min) ✅ DONE
- [x] Update `gradle/libs.versions.toml` with all dependencies
- [x] Update `build.gradle.kts` files
- [ ] Sync project (DO THIS NOW!)
- [x] Create package structure
- [ ] Test build works

### TASK 2: Setup Hilt DI (20 min)
- [ ] Add Hilt application class
- [ ] Add Hilt to MainActivity
- [ ] Create network module
- [ ] Create database module

### TASK 3: Create Data Layer - DTOs & API (45 min)
- [ ] Create `data/remote/dto/CoinDto.kt`
- [ ] Create `data/remote/dto/CoinDetailDto.kt`
- [ ] Create `data/remote/dto/MarketChartDto.kt`
- [ ] Create `data/remote/api/CoinGeckoApi.kt` interface
- [ ] Create Retrofit instance in NetworkModule

### TASK 4: Create Data Layer - Room Database (45 min)
- [ ] Create `data/local/entity/CoinEntity.kt`
- [ ] Create `data/local/entity/WatchlistEntity.kt`
- [ ] Create `data/local/dao/CoinDao.kt`
- [ ] Create `data/local/dao/WatchlistDao.kt`
- [ ] Create `data/local/database/MoneytworkDatabase.kt`

### TASK 5: Create Domain Layer (30 min)
- [ ] Create `domain/model/Coin.kt`
- [ ] Create `domain/model/CoinDetail.kt`
- [ ] Create `domain/repository/CryptoRepository.kt` interface
- [ ] Create mapper functions (DTO → Domain)

### TASK 6: Create Repository Implementation (30 min)
- [ ] Create `data/repository/CryptoRepositoryImpl.kt`
- [ ] Implement caching logic
- [ ] Add to DI module

### TASK 7: Create Use Cases (20 min)
- [ ] Create `domain/usecase/GetTrendingCoinsUseCase.kt`
- [ ] Create `domain/usecase/GetCoinDetailUseCase.kt`
- [ ] Create `domain/usecase/SearchCoinsUseCase.kt`

### TASK 8: Navigation Setup (15 min)
- [ ] Create `presentation/navigation/Screen.kt`
- [ ] Create `presentation/navigation/NavGraph.kt`
- [ ] Update MainActivity with NavHost

### TASK 9: Market List Screen - ViewModel (30 min)
- [ ] Create `presentation/market/list/MarketListState.kt`
- [ ] Create `presentation/market/list/MarketListEvent.kt`
- [ ] Create `presentation/market/list/MarketListViewModel.kt`

### TASK 10: Market List Screen - UI (1 hour)
- [ ] Create `presentation/market/list/MarketListScreen.kt`
- [ ] Create `presentation/market/list/components/CoinListItem.kt`
- [ ] Add pull-to-refresh
- [ ] Add loading/error/empty states
- [ ] Test with real API data

### TASK 11: Detail Screen - ViewModel (30 min)
- [ ] Create `presentation/market/detail/DetailState.kt`
- [ ] Create `presentation/market/detail/DetailViewModel.kt`

### TASK 12: Detail Screen - UI (1.5 hours)
- [ ] Create `presentation/market/detail/DetailScreen.kt`
- [ ] Create price header component
- [ ] Create stats section
- [ ] Add Vico chart
- [ ] Add timeframe selector
- [ ] Add navigation from list

### TASK 13: Search Screen (45 min)
- [ ] Create SearchViewModel
- [ ] Create SearchScreen
- [ ] Add search bar with debounce
- [ ] Show results

### TASK 14: Watchlist Feature (1 hour)
- [ ] Create watchlist use cases (add/remove/get)
- [ ] Create WatchlistViewModel
- [ ] Create WatchlistScreen
- [ ] Add swipe-to-delete
- [ ] Add watchlist button to detail screen

### TASK 15: Offline Mode (30 min)
- [ ] Implement cache expiration logic
- [ ] Show cached data when offline
- [ ] Add offline indicator
- [ ] Test offline scenarios

### TASK 16: Stock API Integration (1 hour)
- [ ] Add Finnhub API interface
- [ ] Create stock DTOs and entities
- [ ] Add stock repository
- [ ] Update UI to show stocks
- [ ] Add filter (crypto/stocks/all)

### TASK 17: Testing - Unit Tests (1.5 hours)
- [ ] Test GetTrendingCoinsUseCase
- [ ] Test CryptoRepositoryImpl (with MockK)
- [ ] Test MarketListViewModel
- [ ] Test DetailViewModel

### TASK 18: Testing - UI Tests (1 hour)
- [ ] Test navigation flow
- [ ] Test market list screen
- [ ] Test detail screen
- [ ] Test search functionality

### TASK 19: Multi-Language Support (1 hour)
- [ ] Extract all strings to strings.xml
- [ ] Create strings.xml for French
- [ ] Create strings.xml for 3rd language
- [ ] Add language switcher in settings
- [ ] Test all languages

### TASK 20: Polish & Integration (2 hours)
- [ ] Add animations
- [ ] Add gestures (swipe actions)
- [ ] Fix bugs
- [ ] Test integration with partner's features
- [ ] Navigate from coin → chat group
- [ ] Performance optimization

### TASK 21: Final Testing & Documentation (1 hour)
- [ ] Full app testing
- [ ] Fix critical bugs
- [ ] Update README with screenshots
- [ ] Prepare demo

---

## 📦 API Keys Needed
- CoinGecko: https://www.coingecko.com/api (free, no key needed for basic)
- Finnhub: https://finnhub.io/register (free tier)

Store in `local.properties`:
```
FINNHUB_API_KEY=your_key_here
```

---

## 🎯 Estimated Time: ~20 hours total
- Week 1-2: Tasks 1-10 (Core features)
- Week 3: Tasks 11-16 (Advanced features)
- Week 4: Tasks 17-21 (Testing & polish)

---

**Current Task: TASK 1 - Let's set up dependencies!**


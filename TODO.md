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
- [x] Sync project
- [x] Create package structure
- [x] Test build works

### TASK 2: Setup Hilt DI (20 min) ✅ DONE
- [x] Add Hilt application class
- [x] Add Hilt to MainActivity
- [x] Create network module
- [x] Create database module

### TASK 3: Create Data Layer - DTOs & API (45 min) ✅ DONE
- [x] Create `data/remote/dto/CoinDto.kt`
- [x] Create `data/remote/dto/CoinDetailDto.kt`
- [x] Create `data/remote/dto/MarketChartDto.kt`
- [x] Create `data/remote/api/CoinGeckoApi.kt` interface
- [x] Create Retrofit instance in NetworkModule

### TASK 4: Create Data Layer - Room Database (45 min) ✅ DONE
- [x] Create `data/local/entity/CoinEntity.kt`
- [x] Create `data/local/entity/WatchlistEntity.kt`
- [x] Create `data/local/dao/CoinDao.kt`
- [x] Create `data/local/dao/WatchlistDao.kt`
- [x] Create `data/local/database/MoneytworkDatabase.kt`

### TASK 5: Create Domain Layer (30 min) ✅ DONE
- [x] Create `domain/model/Coin.kt`
- [x] Create `domain/model/CoinDetail.kt`
- [x] Create `domain/repository/CryptoRepository.kt` interface
- [x] Create mapper functions (DTO → Domain)

### TASK 6: Create Repository Implementation (30 min) ✅ DONE
- [x] Create `data/repository/CryptoRepositoryImpl.kt`
- [x] Implement caching logic
- [x] Add to DI module

### TASK 7: Create Use Cases (20 min) ✅ DONE
- [x] Create `domain/usecase/GetTrendingCoinsUseCase.kt`
- [x] Create `domain/usecase/GetCoinDetailUseCase.kt`
- [x] Create `domain/usecase/SearchCoinsUseCase.kt`

### TASK 8: Navigation Setup (15 min) ✅ DONE
- [x] Create `presentation/navigation/Screen.kt`
- [x] Create `presentation/navigation/NavGraph.kt`
- [x] Update MainActivity with NavHost

### TASK 9: Market List Screen - ViewModel (30 min) ✅ DONE
- [x] Create `presentation/market/list/MarketListState.kt`
- [x] Create `presentation/market/list/MarketListEvent.kt`
- [x] Create `presentation/market/list/MarketListViewModel.kt`

### TASK 10: Market List Screen - UI (1 hour) ✅ DONE
- [x] Create `presentation/market/list/MarketListScreen.kt`
- [x] Create `presentation/market/list/components/CoinListItem.kt`
- [x] Add loading/error/empty states
- [x] Test with real API data
- [x] Add Crypto/Stocks tabs for organization

---

## 🎉 CURRENT STATUS: MVP WORKING!
- App successfully loads and displays cryptocurrency data
- Crypto/Stocks tabs implemented
- Clean UI with Material3 design
- Error handling and loading states working
- Navigation ready for detail screens

---

### TASK 11: Detail Screen - ViewModel (30 min) ✅ DONE
- [x] Create `presentation/market/detail/DetailState.kt`
- [x] Create `presentation/market/detail/DetailEvent.kt`
- [x] Create `presentation/market/detail/DetailViewModel.kt`

### TASK 12: Detail Screen - UI (1.5 hours) ✅ DONE
- [x] Create `presentation/market/detail/DetailScreen.kt`
- [x] Create price header component with star/watchlist
- [x] Create stats section with market data
- [x] Add Vico chart with beautiful gradient
- [x] Add timeframe selector (1D, 1W, 1M, 3M, 1Y, ALL)
- [x] Add navigation from list
- [x] Add "Join Community Chat" button for partner integration
- [x] Add coin description section

---

## 🚀 MAJOR MILESTONE ACHIEVED!
- **Complete crypto tracking flow working!**
- Users can browse coins → View details → See beautiful charts
- Ready for partner's chat integration
- Professional UI with Material3 design
- **Dark mode by default**
- **Glassmorphism/Frosted glass effect on all cards**
- **Bottom navigation with 4 tabs**
- **Portfolio home page showing investment tracking**

---

## ✅ UI IMPROVEMENTS COMPLETE (Just Added!)
- [x] Force dark mode theme
- [x] Glassmorphism card component (frosted glass effect)
- [x] Bottom navigation bar (Portfolio, Crypto, Stocks, Community)
- [x] Portfolio home screen with 3 glass cards:
  - Portfolio value with profit/loss tracking
  - Top Cryptocurrencies (clickable to Crypto tab)
  - Top Stocks placeholder (clickable to Stocks tab)
- [x] Community placeholder screen
- [x] Updated navigation flow

---

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


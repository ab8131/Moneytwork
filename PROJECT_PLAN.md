# Moneytwork - Project Implementation Plan

## 📋 Project Overview
**App Name**: Moneytwork  
**Description**: A hybrid platform combining real-time stock/crypto tracking with social media features, allowing users to discuss investments in coin/stock-specific groups.

**Team Division**:
- **You**: Stocks/Crypto tracking, market data, API integration
- **Partner**: Social media features, authentication, chat system

---

## 🎯 Core Requirements Checklist

### ✅ Professor's Requirements
- [ ] Local Database (Room) - offline capability
- [ ] Remote Database (Firebase Firestore/Realtime Database)
- [ ] API Consumption (CoinGecko, Alpha Vantage, Custom Backend)
- [ ] Unit Tests
- [ ] UI Tests
- [ ] Multi-language Support (English, French, + 1 more)
- [ ] Google Sheets/Translate API integration

---

## 🏗️ Architecture Overview

### Tech Stack
```
Frontend: Jetpack Compose (Kotlin)
Architecture: MVVM + Clean Architecture
Local DB: Room
Remote DB: Firebase Firestore
Authentication: Firebase Auth (Partner's responsibility)
API: Retrofit + OkHttp
DI: Hilt/Koin
Navigation: Jetpack Navigation Compose
Async: Kotlin Coroutines + Flow
Image Loading: Coil
Charts: Vico/MPAndroidChart
Testing: JUnit, Mockk, Espresso, Compose UI Test
```

---

## 📱 Feature Breakdown

### YOUR RESPONSIBILITIES (Stocks/Crypto)

#### 1. Market Data & Tracking
- [ ] Display list of trending cryptocurrencies
- [ ] Display list of popular stocks
- [ ] Real-time price updates
- [ ] Price charts (1D, 1W, 1M, 1Y, ALL)
- [ ] Search functionality for coins/stocks
- [ ] Favorites/Watchlist
- [ ] Price alerts
- [ ] Market statistics (24h change, volume, market cap)
- [ ] Historical data visualization

#### 2. API Integration
- [ ] CoinGecko API for crypto data
- [ ] Alpha Vantage/Finnhub API for stock data
- [ ] Implement caching strategy
- [ ] Handle rate limiting
- [ ] Offline mode with cached data

#### 3. Database (Your Part)
- [ ] Room entities for crypto/stock data
- [ ] Watchlist storage
- [ ] Price alerts storage
- [ ] Cache API responses
- [ ] Sync mechanism

### PARTNER'S RESPONSIBILITIES (Social & Auth)

#### 1. Authentication
- [ ] Email/Password signup/login
- [ ] Google Sign-In
- [ ] Password reset
- [ ] Profile management
- [ ] User sessions

#### 2. Social Features
- [ ] Coin/Stock-specific group chats
- [ ] User profiles
- [ ] Post creation (text, images)
- [ ] Comments system
- [ ] Like/reaction system
- [ ] Follow users
- [ ] User search
- [ ] Notifications

#### 3. Database (Partner's Part)
- [ ] Firestore for user profiles
- [ ] Firestore for chat messages
- [ ] Firestore for posts/comments
- [ ] Real-time message sync

### SHARED RESPONSIBILITIES

#### 1. UI/UX Design
- [ ] Navigation structure
- [ ] Theme system (Dark/Light mode)
- [ ] Common UI components
- [ ] Responsive layouts
- [ ] Animations & gestures

#### 2. Localization
- [ ] String resources setup
- [ ] Google Sheets integration for translations
- [ ] Language switcher
- [ ] Support for EN, FR, + 1 more

#### 3. Testing
- [ ] Unit tests for ViewModels
- [ ] Unit tests for Repositories
- [ ] Unit tests for Use Cases
- [ ] UI tests for navigation
- [ ] Integration tests

---

## 📂 Project Structure

```
app/
├── src/main/java/com/example/moneytwork/
│   ├── MainActivity.kt
│   │
│   ├── core/
│   │   ├── di/                     # Dependency Injection
│   │   ├── network/                # Retrofit, API clients
│   │   ├── util/                   # Extensions, helpers
│   │   └── constants/              # App constants
│   │
│   ├── data/
│   │   ├── local/
│   │   │   ├── dao/               # Room DAOs
│   │   │   ├── entity/            # Room Entities
│   │   │   └── database/          # Database instance
│   │   ├── remote/
│   │   │   ├── api/               # API interfaces
│   │   │   └── dto/               # Data Transfer Objects
│   │   └── repository/            # Repository implementations
│   │
│   ├── domain/
│   │   ├── model/                 # Domain models
│   │   ├── repository/            # Repository interfaces
│   │   └── usecase/               # Business logic
│   │
│   ├── presentation/
│   │   ├── navigation/            # Navigation setup
│   │   ├── theme/                 # Theme, colors, typography
│   │   ├── components/            # Reusable UI components
│   │   │
│   │   ├── market/                # YOUR SCREENS
│   │   │   ├── list/             # Crypto/Stock list
│   │   │   ├── detail/           # Coin/Stock detail
│   │   │   ├── search/           # Search screen
│   │   │   └── watchlist/        # Favorites
│   │   │
│   │   ├── social/                # PARTNER'S SCREENS
│   │   │   ├── feed/             # Social feed
│   │   │   ├── chat/             # Group chat
│   │   │   ├── profile/          # User profile
│   │   │   └── post/             # Create post
│   │   │
│   │   └── auth/                  # PARTNER'S SCREENS
│   │       ├── login/
│   │       ├── register/
│   │       └── profile/
│   │
│   └── ui/
│       └── theme/                 # Theme files
│
└── src/test/                      # Unit tests
└── src/androidTest/               # UI tests
```

---

## 🚀 Implementation Roadmap

### Phase 1: Setup & Foundation (Week 1)
**Both Team Members**
- [ ] Set up project dependencies
- [ ] Configure Hilt/Koin for DI
- [ ] Set up Room database
- [ ] Set up Firebase project
- [ ] Create base architecture (MVVM)
- [ ] Set up navigation
- [ ] Design theme system
- [ ] Create common UI components

### Phase 2: Core Features - Part 1 (Week 2-3)
**YOU - Market Data**
- [ ] Implement CoinGecko API integration
- [ ] Implement stock API integration
- [ ] Create market list screen
- [ ] Create detail screen with charts
- [ ] Implement search functionality
- [ ] Set up Room caching

**PARTNER - Authentication**
- [ ] Implement Firebase Auth
- [ ] Create login/register screens
- [ ] Create profile management
- [ ] Set up Firestore user structure

### Phase 3: Core Features - Part 2 (Week 4-5)
**YOU - Advanced Market Features**
- [ ] Implement watchlist
- [ ] Add price alerts
- [ ] Real-time price updates
- [ ] Offline mode
- [ ] Pull-to-refresh
- [ ] Add gestures (swipe actions)

**PARTNER - Social Features**
- [ ] Create group chat system
- [ ] Implement real-time messaging
- [ ] Create feed/post system
- [ ] Add comments feature
- [ ] Implement likes/reactions

### Phase 4: Integration (Week 6)
**Both Team Members**
- [ ] Connect market screens with social features
- [ ] Navigate from coin detail to group chat
- [ ] Share coin info in chat
- [ ] User mentions in posts
- [ ] Testing integration points

### Phase 5: Localization (Week 7)
**Both Team Members**
- [ ] Extract all strings to resources
- [ ] Set up Google Sheets for translations
- [ ] Implement Google Translate API
- [ ] Add language switcher
- [ ] Test all languages

### Phase 6: Testing (Week 8)
**YOU - Market Tests**
- [ ] Unit tests for market ViewModels
- [ ] Unit tests for repositories
- [ ] UI tests for market screens
- [ ] Integration tests

**PARTNER - Social Tests**
- [ ] Unit tests for social ViewModels
- [ ] Unit tests for chat logic
- [ ] UI tests for social screens
- [ ] Integration tests

### Phase 7: Polish & Deployment (Week 9-10)
**Both Team Members**
- [ ] Bug fixes
- [ ] Performance optimization
- [ ] UI/UX improvements
- [ ] Documentation
- [ ] Prepare presentation
- [ ] Final testing

---

## 📋 YOUR DETAILED TODO LIST (Stocks/Crypto Features)

### Setup Tasks
```
[ ] 1. Add dependencies to build.gradle.kts:
    - Retrofit & OkHttp
    - Room
    - Hilt/Koin
    - Coroutines & Flow
    - Coil (images)
    - Vico/MPAndroidChart (charts)
    - Gson/Kotlinx Serialization

[ ] 2. Create package structure

[ ] 3. Set up Hilt modules for DI

[ ] 4. Create base classes:
    - BaseViewModel
    - Resource/Result wrapper
    - NetworkBoundResource
```

### API Integration Tasks
```
[ ] 5. Register for API keys:
    - CoinGecko (free tier)
    - Alpha Vantage/Finnhub (stocks)

[ ] 6. Create API interfaces:
    - CryptoApiService
    - StockApiService

[ ] 7. Create DTOs (Data Transfer Objects):
    - CoinDto
    - CoinDetailDto
    - MarketChartDto
    - StockDto
    - StockQuoteDto

[ ] 8. Implement Retrofit client with:
    - Base URL configuration
    - Logging interceptor
    - Error handling
    - Rate limiting handling
```

### Database Tasks
```
[ ] 9. Create Room entities:
    - CoinEntity
    - StockEntity
    - WatchlistEntity
    - PriceAlertEntity
    - CachedPriceEntity

[ ] 10. Create DAOs:
    - CoinDao
    - StockDao
    - WatchlistDao
    - PriceAlertDao

[ ] 11. Create database class:
    - MoneytworkDatabase

[ ] 12. Create type converters if needed
```

### Domain Layer Tasks
```
[ ] 13. Create domain models:
    - Coin
    - Stock
    - MarketData
    - ChartData
    - Watchlist
    - PriceAlert

[ ] 14. Create repository interfaces:
    - CryptoRepository
    - StockRepository
    - WatchlistRepository

[ ] 15. Implement repositories with:
    - Network + cache strategy
    - Offline support
    - Error handling
```

### Use Cases Tasks
```
[ ] 16. Create use cases:
    - GetTrendingCoinsUseCase
    - GetCoinDetailUseCase
    - GetCoinChartUseCase
    - SearchCoinsUseCase
    - GetStocksUseCase
    - GetStockDetailUseCase
    - AddToWatchlistUseCase
    - RemoveFromWatchlistUseCase
    - GetWatchlistUseCase
    - SetPriceAlertUseCase
```

### UI - Market List Screen
```
[ ] 17. Create MarketListViewModel:
    - Load trending coins/stocks
    - Handle refresh
    - Handle search
    - Filter by type (crypto/stock)

[ ] 18. Create MarketListScreen composable:
    - LazyColumn for list
    - Pull-to-refresh
    - Loading states
    - Error states
    - Empty states

[ ] 19. Create CoinListItem composable:
    - Coin icon/logo
    - Name & symbol
    - Current price
    - 24h change (with color)
    - Mini chart preview
    - Swipe actions (add to watchlist)
```

### UI - Detail Screen
```
[ ] 20. Create DetailViewModel:
    - Load coin/stock details
    - Load chart data
    - Handle timeframe changes
    - Add/remove watchlist
    - Set price alerts

[ ] 21. Create DetailScreen composable:
    - Header with basic info
    - Price chart (interactive)
    - Timeframe selector (1D, 1W, 1M, etc.)
    - Statistics section
    - About/Description
    - Navigate to group chat button
    - Add to watchlist FAB
    - Set alert dialog

[ ] 22. Create chart component:
    - Line chart with Vico
    - Touch interactions
    - Zoom/pan gestures
    - Custom styling
```

### UI - Search Screen
```
[ ] 23. Create SearchViewModel:
    - Search query handling
    - Debounce search input
    - Filter results

[ ] 24. Create SearchScreen composable:
    - Search bar
    - Recent searches
    - Search results list
    - Filter chips (crypto/stocks)
```

### UI - Watchlist Screen
```
[ ] 25. Create WatchlistViewModel:
    - Load watchlist
    - Remove items
    - Reorder items
    - Real-time price updates

[ ] 26. Create WatchlistScreen composable:
    - Grid or list view
    - Swipe to delete
    - Drag to reorder
    - Quick stats
    - Empty state
```

### Real-time Updates
```
[ ] 27. Implement price update mechanism:
    - WebSocket or polling
    - Update UI in real-time
    - Battery optimization

[ ] 28. Implement price alerts:
    - Background worker
    - Notification system
    - Alert settings
```

### Offline Support
```
[ ] 29. Implement caching strategy:
    - Cache API responses
    - Set expiration times
    - Offline indicator

[ ] 30. Handle offline scenarios:
    - Show cached data
    - Disable refresh when offline
    - Queue actions for sync
```

### Testing Tasks
```
[ ] 31. Unit tests:
    - Test ViewModels
    - Test UseCases
    - Test Repositories
    - Mock API responses

[ ] 32. UI tests:
    - Test navigation
    - Test list scrolling
    - Test search
    - Test detail screen

[ ] 33. Integration tests:
    - Test API integration
    - Test database operations
    - Test cache mechanism
```

---

## 🎨 UI/UX Suggestions

### Design Principles
- **Clean & Modern**: Material 3 design
- **Data-First**: Prioritize readability of market data
- **Quick Actions**: Swipe gestures for common actions
- **Visual Feedback**: Color-coded gains/losses (green/red)
- **Smooth Animations**: Transitions between screens

### Gestures to Implement
1. **Swipe Right** on coin → Add to watchlist
2. **Swipe Left** on coin → Remove from watchlist
3. **Pull Down** → Refresh data
4. **Long Press** on coin → Quick actions menu
5. **Swipe Up** on detail → Show more info
6. **Pinch/Zoom** on chart → Zoom in/out

### Color Scheme Suggestions
```kotlin
// Price Changes
val positiveGreen = Color(0xFF00C853)
val negativeRed = Color(0xFFD50000)

// Charts
val chartLineColor = Color(0xFF2962FF)
val chartGradientStart = Color(0x802962FF)
val chartGradientEnd = Color(0x00000000)
```

---

## 🔌 API Resources

### Cryptocurrency APIs
1. **CoinGecko** (Recommended - Free)
   - URL: https://www.coingecko.com/api/documentation
   - No API key needed for basic tier
   - Endpoints:
     - `/coins/markets` - List coins
     - `/coins/{id}` - Coin details
     - `/coins/{id}/market_chart` - Price chart
     - `/search` - Search coins

2. **CoinCap** (Alternative)
   - URL: https://docs.coincap.io/

### Stock APIs
1. **Alpha Vantage** (Free tier: 25 requests/day)
   - URL: https://www.alphavantage.co/documentation/
   - API key required (free)

2. **Finnhub** (Free tier: 60 calls/minute)
   - URL: https://finnhub.io/docs/api
   - Better free tier limits

3. **Yahoo Finance** (Unofficial - via RapidAPI)

### Translation API
- **Google Sheets API** - For translation management
- **Google Cloud Translation API** - For auto-translation

---

## 📚 Key Dependencies

### Add to `libs.versions.toml`:
```toml
[versions]
# Existing versions...
retrofit = "2.9.0"
okhttp = "4.12.0"
room = "2.6.1"
hilt = "2.50"
navigation = "2.8.0"
coil = "2.5.0"
vico = "1.13.1"
firebase = "32.7.0"
coroutines = "1.8.0"
lifecycle = "2.7.0"
mockk = "1.13.9"

[libraries]
# Existing libraries...

# Networking
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }
okhttp = { group = "com.squareup.okhttp3", name = "okhttp", version.ref = "okhttp" }
okhttp-logging = { group = "com.squareup.okhttp3", name = "logging-interceptor", version.ref = "okhttp" }

# Room
room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }

# Hilt
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }
hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version = "1.2.0" }

# Navigation
navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigation" }

# Image Loading
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }

# Charts
vico-compose = { group = "com.patrykandpatrick.vico", name = "compose", version.ref = "vico" }
vico-compose-m3 = { group = "com.patrykandpatrick.vico", name = "compose-m3", version.ref = "vico" }

# Firebase
firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebase" }
firebase-auth = { group = "com.google.firebase", name = "firebase-auth-ktx" }
firebase-firestore = { group = "com.google.firebase", name = "firebase-firestore-ktx" }

# Coroutines
coroutines-core = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version.ref = "coroutines" }
coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }

# Lifecycle
lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycle" }
lifecycle-runtime-compose = { group = "androidx.lifecycle", name = "lifecycle-runtime-compose", version.ref = "lifecycle" }

# Testing
mockk = { group = "io.mockk", name = "mockk", version.ref = "mockk" }
coroutines-test = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-test", version.ref = "coroutines" }
turbine = { group = "app.cash.turbine", name = "turbine", version = "1.0.0" }

[plugins]
# Existing plugins...
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
google-services = { id = "com.google.gms.google-services", version = "4.4.0" }
ksp = { id = "com.google.devtools.ksp", version = "2.0.21-1.0.25" }
```

---

## 🔒 Security Considerations

### API Keys
```kotlin
// Store in local.properties (not in version control)
COINGECKO_API_KEY=your_key_here
ALPHA_VANTAGE_API_KEY=your_key_here

// Access in build.gradle.kts
android {
    defaultConfig {
        buildConfigField("String", "COINGECKO_API_KEY", 
            "\"${project.findProperty("COINGECKO_API_KEY") ?: ""}\"")
    }
}
```

### Firebase Security Rules
- Partner will handle this for Firestore
- Ensure read/write permissions are properly set
- Validate user authentication

---

## 📊 Success Metrics

### Functionality
- [ ] App works offline with cached data
- [ ] Real-time price updates work smoothly
- [ ] All tests pass (>80% coverage)
- [ ] Multi-language support works
- [ ] Chat integration works seamlessly

### Performance
- [ ] App launches in < 2 seconds
- [ ] List scrolling is smooth (60fps)
- [ ] API calls complete in < 1 second
- [ ] Database operations are instant

### UX
- [ ] Intuitive navigation
- [ ] Clear visual hierarchy
- [ ] Responsive to user input
- [ ] Helpful error messages

---

## 🎓 Presentation Tips

### Demo Flow
1. **Offline Mode**: Show app working without internet
2. **Market Data**: Browse coins/stocks with real prices
3. **Detail View**: Interactive charts and data
4. **Social Integration**: Navigate to coin group chat
5. **Multi-language**: Switch between languages
6. **Real-time**: Show live price updates
7. **Tests**: Show test coverage report

### Technical Highlights
- Clean Architecture implementation
- Offline-first approach
- Real-time data synchronization
- Comprehensive testing
- Modern UI with Jetpack Compose
- Multi-language support with Google Sheets

---

## 📞 Questions to Discuss with Partner

1. **Navigation Structure**: Bottom nav vs drawer vs tabs?
2. **Shared Components**: Who creates common UI components?
3. **Data Models**: How to link coins with chat groups?
4. **Notifications**: Who handles notification system?
5. **User Profiles**: What data to include about trading interests?
6. **Backend**: Are we building custom backend or Firebase only?
7. **Testing Strategy**: Split or shared test suites?

---

## 🚨 Potential Challenges & Solutions

### Challenge 1: API Rate Limits
**Solution**: 
- Implement aggressive caching
- Use WebSocket for real-time data when possible
- Combine multiple requests

### Challenge 2: Real-time Price Updates
**Solution**:
- Use WorkManager for background updates
- Implement efficient polling mechanism
- Consider WebSocket connections

### Challenge 3: Chart Performance
**Solution**:
- Use Canvas for custom drawing
- Limit data points shown
- Implement data sampling

### Challenge 4: Offline Sync
**Solution**:
- Implement SyncManager
- Queue operations when offline
- Conflict resolution strategy

### Challenge 5: Testing Firebase
**Solution**:
- Use Firebase Emulator Suite
- Mock Firebase in unit tests
- Separate integration tests

---

## 📝 Notes

- Start with MVP (Minimum Viable Product) features first
- Regular sync meetings with partner (daily/every 2 days)
- Use Git branches for features
- Code review each other's work
- Document as you go
- Keep professor updated on progress

---

## 🎯 Sprint Goals (Agile Approach)

### Sprint 1 (Days 1-7): Foundation
- Project setup complete
- Architecture in place
- Basic navigation working
- API integration started

### Sprint 2 (Days 8-14): Core Features
- Market list screen working
- Detail screen with charts
- Authentication working
- Basic chat implemented

### Sprint 3 (Days 15-21): Integration
- Watchlist feature complete
- Social features integrated
- Real-time updates working
- Offline mode working

### Sprint 4 (Days 22-28): Polish
- All features complete
- Tests written
- Localization done
- Bug fixes

---

## ✅ Quick Start Checklist (Do This First!)

1. [ ] Review this plan with your partner
2. [ ] Divide tasks clearly
3. [ ] Set up Git repository with proper .gitignore
4. [ ] Register for all API keys
5. [ ] Set up Firebase project
6. [ ] Create project board (Trello/GitHub Projects)
7. [ ] Set up communication channel (Discord/Slack)
8. [ ] Schedule regular sync meetings
9. [ ] Start with Phase 1 tasks

---

**Good luck with your project! 🚀**


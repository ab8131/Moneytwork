# Moneytwork

A modern Android app for tracking cryptocurrency and stock portfolios with integrated social features.

## Overview

Moneytwork combines financial portfolio tracking with social networking, allowing users to:
- Track crypto and stock investments with real-time prices
- Record BUY/SELL transactions and view profit/loss
- Chat with other users about specific assets
- Switch between multiple languages
- Use the app offline with local caching

---

## Features

### Portfolio Management
- Track cryptocurrency and stock holdings
- Record transactions (BUY/SELL)
- Real-time profit/loss calculations
- Average buy price tracking
- Portfolio overview with total value

### Live Market Data
- Real-time cryptocurrency prices (CoinGecko API)
- Live stock quotes (Finnhub API)
- Price charts with multiple timeframes (1D, 1W, 1M, 1Y, ALL)
- Search functionality

### Social Features
- Group chat per asset (e.g., Bitcoin chat, AAPL chat)
- Real-time messaging with Firebase
- User authentication

### Multi-Language Support
- English, French, Hausa
- Language switcher in settings
- Persistent language preference

### Offline Support
- Local caching with Room database
- Works without internet connection
- 10-minute cache duration

---

## Tech Stack

**Language**: Kotlin  
**UI**: Jetpack Compose + Material 3  
**Architecture**: MVVM + Repository Pattern  
**Dependency Injection**: Hilt  
**Local Database**: Room  
**Remote Database**: Firebase Firestore  
**APIs**: Retrofit + OkHttp  
**Authentication**: Firebase Auth  
**Charts**: Custom Compose Canvas  

---

## Project Structure

```
app/
├── data/
│   ├── local/          # Room database (DAOs, entities)
│   ├── remote/         # API services
│   ├── preferences/    # DataStore for settings
│   └── repository/     # Data repositories
├── domain/
│   ├── model/          # Domain models
│   └── repository/     # Repository interfaces
├── presentation/
│   ├── auth/           # Sign In/Sign Up screens
│   ├── chat/           # Chat functionality
│   ├── crypto/         # Cryptocurrency list
│   ├── detail/         # Asset detail screens
│   ├── language/       # Language selection
│   ├── navigation/     # Navigation setup
│   ├── portfolio/      # Portfolio & holdings
│   ├── profile/        # User profile
│   ├── settings/       # App settings
│   └── stocks/         # Stock list
└── ui/
    └── theme/          # Material 3 theme, colors, fonts
```

---

## Setup

### Prerequisites
- Android Studio (latest version)
- JDK 17+
- Android SDK 34+

### Firebase Setup
1. Create a Firebase project at [console.firebase.google.com](https://console.firebase.google.com)
2. Enable Firebase Authentication (Email/Password)
3. Create a Firestore database
4. Download `google-services.json` and place it in `app/` folder

### API Keys
The app uses free tiers of:
- **CoinGecko API** - No key required for basic usage
- **Finnhub API** - Free tier available at [finnhub.io](https://finnhub.io)

### Build & Run
```bash
# Clone the repository
git clone [your-repo-url]

# Open in Android Studio
# Build and run on emulator or device

# Or via command line:
./gradlew assembleDebug
./gradlew installDebug
```

---

## Testing

### Run Unit Tests
```bash
./gradlew testDebugUnitTest
```

### Run UI Tests (requires emulator/device)
```bash
./gradlew connectedDebugAndroidTest
```

**Test Coverage**:
- 12 unit tests (data models, state management)
- 5 UI tests (authentication, navigation)

---

## Key Dependencies

```gradle
// UI & Compose
implementation "androidx.compose.material3:material3"
implementation "androidx.navigation:navigation-compose"

// Dependency Injection
implementation "com.google.dagger:hilt-android"
kapt "com.google.dagger:hilt-compiler"

// Database
implementation "androidx.room:room-runtime"
implementation "androidx.room:room-ktx"

// Firebase
implementation "com.google.firebase:firebase-auth"
implementation "com.google.firebase:firebase-firestore"

// Networking
implementation "com.squareup.retrofit2:retrofit"
implementation "com.squareup.retrofit2:converter-gson"

// Charts
implementation "com.github.PhilJay:MPAndroidChart"
```

---

## Features by Screen

### Portfolio (Home)
- Total portfolio value
- Profit/loss summary
- Top cryptocurrencies preview
- Top stocks preview
- Click to view detailed holdings

### Crypto / Stocks
- Live price list
- 24h price change
- Search functionality
- Click to view details

### Asset Details
- Price chart with timeframes
- Financial data (market cap, volume, etc.)
- Record transaction dialog
- Group chat per asset

### Portfolio Holdings
- All your investments in one place
- Per-asset profit/loss
- Average buy price
- Current value vs invested

### Settings
- User profile
- Language selection
- Logout

---

## Multi-Language Implementation

Supports 3 languages:
- English (en)
- French (fr)
- Hausa (ha)

All UI text uses string resources. Language changes recreate the activity to apply new locale.

---

## Offline Support

The app works offline through:
- Room database caching for crypto/stock prices
- 10-minute cache validity
- Local storage for all transactions
- Graceful handling of network errors

---

## Database Schema

### Local (Room)
- **transactions** - BUY/SELL records
- **coins** - Cached cryptocurrency data
- **stocks** - Cached stock data

### Remote (Firestore)
- **users** - User profiles
- **chats/{assetId}/messages** - Chat messages per asset

---

## Team

This is a 2-person team project:
- **Developer 1**: Portfolio tracking, API integration, charts
- **Developer 2**: Authentication, social features, chat

---

## Academic Requirements Met

✅ Local database (Room)  
✅ Remote database (Firebase Firestore)  
✅ External API consumption (CoinGecko + Finnhub)  
✅ Own backend (Firebase)  
✅ Offline functionality  
✅ Multi-language support (3 languages)  
✅ Unit tests  
✅ UI tests  

---

## License

This is an academic project for educational purposes.

---

## Acknowledgments

- CoinGecko API for cryptocurrency data
- Finnhub API for stock market data
- Firebase for authentication and real-time database
- Material Design 3 for UI components


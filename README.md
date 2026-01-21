# Moneytwork

**Financial tracking meets social networking** - A modern Android app that combines cryptocurrency and stock portfolio management with real-time community discussions.

## Why Moneytwork?

Traditional portfolio trackers are isolated experiences. Moneytwork brings investors together by combining robust portfolio tracking with asset-specific chat rooms, enabling you to track your investments while engaging with a community of like-minded investors.

## Key Features

**Smart Portfolio Tracking**
- Track multiple cryptocurrencies and stocks in one unified portfolio
- Record buy/sell transactions with automatic profit/loss calculations
- View average cost basis across multiple purchases
- Real-time portfolio valuation based on live market data

**Live Market Data**
- Real-time cryptocurrency prices powered by CoinGecko
- Live stock quotes via Finnhub
- Interactive price charts with multiple timeframes (1D, 1W, 1M, 1Y, ALL)
- Advanced search across thousands of assets

**Community Discussions**
- Asset-specific chat rooms for every cryptocurrency and stock
- Real-time messaging with other investors
- Share insights and discuss market trends

**Built for Everyone**
- Multi-language support (English, French, Hausa)
- Full offline functionality with smart caching
- Clean, modern interface with dark mode
- Secure authentication

## Technology

Built with modern Android development tools:

- **Kotlin** - Modern, expressive language
- **Jetpack Compose** - Declarative UI framework
- **Material Design 3** - Clean, intuitive interface
- **MVVM Architecture** - Maintainable, testable code
- **Room Database** - Offline-first data persistence
- **Firebase** - Authentication and real-time chat
- **Retrofit** - Efficient API communication

## Getting Started

### Requirements
- Android 8.0 (API 26) or higher
- Internet connection for live data (works offline with cached data)

### Installation

```bash
git clone https://github.com/ab8131/Moneytwork.git
cd moneytwork
./gradlew assembleRelease
```

### Firebase Setup (for developers)
1. Create a Firebase project
2. Enable Authentication (Email/Password)
3. Create a Firestore database
4. Add your `google-services.json` to the `app/` directory

## How It Works

**Track Your Investments**
1. Sign up and authenticate securely
2. Search for cryptocurrencies or stocks
3. Record your buy/sell transactions
4. View real-time profit/loss calculations

**Connect with the Community**
1. Navigate to any asset (Bitcoin, Tesla, etc.)
2. Join the asset-specific chat room
3. Discuss market trends and strategies
4. Learn from other investors

**Works Everywhere**
- Use the app in English, French, or Hausa
- Full functionality offline with smart caching
- Seamless synchronization when back online

## Data Sources

- **Cryptocurrency Data**: Powered by [CoinGecko API](https://www.coingecko.com/)
- **Stock Market Data**: Powered by [Finnhub API](https://finnhub.io/)
- **Real-time Chat**: Firebase Firestore

## Privacy & Security

- Secure authentication with Firebase
- Local data encrypted and stored on-device
- No transaction data shared with third parties
- Open source - review the code yourself

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- CoinGecko for cryptocurrency market data
- Finnhub for stock market data
- Firebase for backend infrastructure
- The Android and Kotlin communities


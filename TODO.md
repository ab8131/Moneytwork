# Moneytwork - Master TODO & Plan

## 🎯 Project Overview
A platform to track stocks/crypto prices with social media integration. Users can view asset prices, record transactions (not actual trading), track portfolio performance, and chat with other users about specific assets.

---

## ✅ COMPLETED

### Core Features
- [x] Bottom navigation (Portfolio, Crypto, Stocks, Community, Settings)
- [x] Dark mode with glassmorphism UI
- [x] Crypto list with live prices (CoinGecko API)
- [x] Stock list with live prices (Finnhub API)
- [x] Crypto detail page with price chart
- [x] Stock detail page
- [x] Transaction recording dialog (Buy/Sell)
- [x] Portfolio value calculation (real-time profit/loss)
- [x] Dual input for transactions (quantity OR total amount)
- [x] Search functionality for crypto
- [x] Poppins font family

### Technical Infrastructure
- [x] Room database (v4) with caching
- [x] Offline support (cached data)
- [x] Error handling (no crashes on API failures)
- [x] Local transaction storage
- [x] Real-time price fetching
- [x] Navigation setup
- [x] Hilt dependency injection

### Database Schema
- [x] CoinEntity - Crypto cache
- [x] StockEntity - Stock cache  
- [x] TransactionEntity - User transactions
- [x] WatchlistEntity - User favorites

---

## 🚧 IN PROGRESS / NEEDS WORK

### UI/UX Improvements
- [ ] **Glassmorphism refinement** - Make it more transparent/glassy
- [ ] **Primary color definition** - Centralize the green color for easy changes
- [ ] **Chart improvements** - Remove vertical grid lines, keep horizontal
- [ ] **Time period selector** - Use pipes (|) instead of squares, highlight active
- [ ] **Portfolio cards** - Make them more "glass-like" with better transparency
- [ ] **Crypto list on home** - Remove individual cards, use faint dividers only

### Functionality Gaps
- [ ] **Stock holdings in portfolio** - Currently only shows crypto, add stock support
- [ ] **Transaction history tab** - Show list of all user transactions
- [ ] **Ownership tab content** - Display user's positions for each asset
- [ ] **Edit/Delete transactions** - Allow modifying recorded transactions
- [ ] **Watchlist/Favorites** - Let users favorite assets for quick access
- [ ] **Pull-to-refresh** - Manual refresh for price data
- [ ] **Top stocks on home** - Currently added but may need polish

### Data & Performance
- [ ] **Background price updates** - Update portfolio values periodically
- [ ] **Stock price in portfolio calculation** - Fetch real-time stock prices
- [ ] **Transaction validation** - Prevent selling more than owned
- [ ] **Export transactions** - CSV or PDF reports

---

## 📋 PARTNER'S TASKS (Authentication & Social)

These are primarily your partner's responsibility:

- [ ] Firebase Authentication setup
- [ ] User login/signup screens
- [ ] User profiles
- [ ] Group chats per asset (Bitcoin chat, AAPL chat, etc.)
- [ ] Real-time messaging
- [ ] User mentions/notifications
- [ ] Chat moderation
- [ ] Firebase Firestore for chat storage
- [ ] Sync transactions to cloud (optional)

---

## 🌍 INTERNATIONALIZATION

- [ ] Add French translation
- [ ] Add your mother tongue translation
- [ ] Use Google Translate API or Google Sheets for translations
- [ ] Implement language switcher in settings

---

## 🧪 TESTING

- [ ] Unit tests for repositories
- [ ] Unit tests for ViewModels
- [ ] UI tests for transaction recording
- [ ] UI tests for navigation
- [ ] Test offline mode
- [ ] Test API failure scenarios
- [ ] Test transaction calculations

---

## 🎨 UI POLISH NEEDED

### Home/Portfolio Screen
```
Current: Cards look solid, not glassy
Target: More transparent, actual glass effect
Action: Adjust alpha values, add backdrop blur if possible
```

### Asset Lists
```
Current: Crypto looks good, stocks might not match
Target: Both should have consistent styling
Action: Faint dividers, no card backgrounds
```

### Detail Pages
```
Current: Tabs work, but chart needs polish
Target: Clean chart, better time period selector
Action: 
- Remove vertical grid lines
- Use "1D | 1W | 1M | 1Y" format
- Highlight active period in green
```

### Color System
```
Current: Green used in multiple places, hard to change
Target: Centralized theme color
Action: Create Theme.kt with primary color definition
```

---

## 🔧 TECHNICAL DEBT

- [ ] Improve error messages (more user-friendly)
- [ ] Add loading states to all screens
- [ ] Implement proper retry logic for API failures
- [ ] Add analytics/crash reporting
- [ ] Optimize database queries
- [ ] Add ProGuard rules for release build
- [ ] Set up CI/CD pipeline (GitHub Actions?)

---

## 📱 RELEASE CHECKLIST

When ready to submit:

- [ ] All features working (crypto, stocks, portfolio, transactions)
- [ ] No crashes on any screen
- [ ] APIs working with proper error handling
- [ ] 3+ languages supported
- [ ] Unit tests passing
- [ ] UI tests passing
- [ ] Local database working (Room)
- [ ] Remote API integration working (CoinGecko, Finnhub)
- [ ] Partner's features integrated (auth, chat)
- [ ] App icon designed
- [ ] Screenshots for presentation
- [ ] Demo video recorded
- [ ] Documentation complete

---

## 🚀 NEXT SESSION - Priority Tasks

1. **Fix glassmorphism** - Make cards actually look like glass
2. **Centralize primary color** - Create theme system
3. **Polish chart UI** - Remove vertical lines, improve time selector
4. **Add transaction history** - Show all user transactions
5. **Show ownership in detail** - Display user's position per asset

---

## 📝 NOTES

### Current Status (Jan 18, 2026)
- App builds successfully
- Crypto loading works ✓
- Stocks loading works ✓
- Stock detail no longer crashes ✓
- Transaction recording works ✓
- Portfolio shows real calculations ✓

### Known Issues
- Glassmorphism needs improvement (not glassy enough)
- Stock prices not included in portfolio calculations yet
- Some UI inconsistencies between pages

### API Keys
- CoinGecko: Free tier, no key needed
- Finnhub: `d5k1t3hr01qjaedsi5fgd5k1t3hr01qjaedsi5g0`

### Database Version
- Current: v4
- Migration: fallbackToDestructiveMigration (dev only)

---

## 🎓 PROFESSOR REQUIREMENTS

- ✅ Local database (Room) - Transactions, Cache
- ✅ Remote database ready (Firebase - partner handles)
- ✅ API consumption (CoinGecko, Finnhub)
- ⏳ Unit tests (need to add)
- ⏳ UI tests (need to add)
- ⏳ Multiple languages (need to add 3+)
- ⏳ Partner integration (auth, chat)

---

**Last Updated:** Jan 18, 2026  
**Team:** 2 members (You: Stocks/Crypto | Partner: Social/Auth)  
**Target:** Complete functional app with all requirements


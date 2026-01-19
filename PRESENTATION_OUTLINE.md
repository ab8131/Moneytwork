# Moneytwork - PowerPoint Presentation Outline

## Slide 1: Title Slide
**Title:** Moneytwork  
**Tagline:** Financial Tracking Meets Social Networking  
**Authors:** Ahmad Habib & Alexandre Bourges  
**Date:** January 2026

---

## Slide 2: Table of Contents
1. Project Overview
2. Technical Architecture
3. Core Features
4. Database Implementation
5. API Integration
6. Real-time Chat System
7. Security & Authentication
8. Technical Challenges
9. Project Results
10. Conclusion

---

## Slide 3: Project Overview
**What is Moneytwork?**
- Mobile application for cryptocurrency and stock tracking
- Real-time price monitoring with portfolio management
- Social platform with asset-specific chat rooms
- Built for Android using Kotlin and Jetpack Compose

**Key Objectives:**
- Track investments across crypto and stocks
- Calculate real-time profit/loss
- Connect investors through community discussions
- Function seamlessly offline

---

## Slide 4: Technical Architecture
**Technology Stack:**
- Language: Kotlin
- UI: Jetpack Compose
- Architecture: MVVM + Clean Architecture
- DI: Hilt
- Local DB: Room (SQLite)
- Remote DB: Firebase Firestore
- Auth: Firebase Authentication
- Networking: Retrofit + OkHttp

**Architecture Layers:**
- Presentation → ViewModels + Compose UI
- Domain → Use Cases + Business Logic
- Data → Repositories + APIs + Database

---

## Slide 5: Core Features
**Market Tracking:**
- 50+ cryptocurrencies (CoinGecko API)
- Major US stocks (Finnhub API)
- Real-time price updates
- Historical charts (1D, 1W, 1M, 3M, 1Y)

**Portfolio Management:**
- Transaction recording (buy/sell)
- Dual input mode (quantity or amount)
- Automatic profit/loss calculation
- Average cost basis tracking

**Social Features:**
- Real-time chat per asset
- User authentication
- Message history
- Cross-device synchronization

---

## Slide 6: Database Implementation
**Local Database (Room):**
- CoinEntity → Cryptocurrency cache
- StockEntity → Stock data cache
- TransactionEntity → User transactions
- WatchlistEntity → Favorite assets

**Remote Database (Firestore):**
- users → User profiles
- chats/{assetId}/messages → Chat messages

**Strategy:**
- Offline-first: Cache before display
- Automatic fallback to local data
- Real-time sync for chat

---

## Slide 7: API Integration
**External APIs:**

| API | Purpose | Data |
|-----|---------|------|
| CoinGecko | Crypto data | Prices, charts, market info |
| Finnhub | Stock data | Quotes, company profiles |
| Firebase Auth | Authentication | Email/password, sessions |
| Firebase Firestore | Real-time DB | User data, chat messages |

**Benefits:**
- Multiple data sources
- Real-time updates
- Offline capability
- Rate limit handling

---

## Slide 8: Real-time Chat System
**Implementation:**
- Firestore snapshot listeners
- Asset-specific chat rooms (Bitcoin, AAPL, etc.)
- Message synchronization < 2 seconds
- Last 100 messages per room

**Features:**
- Username attribution
- Timestamp display
- Read-only for guests
- Write access for authenticated users

**Security:**
- Public read access
- Authenticated write access
- Users can delete own messages only

---

## Slide 9: Security & Authentication
**Authentication:**
- Firebase Authentication (email/password)
- Minimum 6-character passwords
- Email validation
- Session persistence

**Data Security:**
- Firestore security rules enforce access control
- Transactions stored locally (privacy)
- User profiles protected (owner-only write)
- Chat messages moderated by rules

**Privacy:**
- Portfolio data never leaves device
- Only username/email stored in cloud
- Chat messages public by design

---

## Slide 10: Technical Challenges & Solutions
**Challenge 1: Real-time Calculations**
- Problem: Portfolio value with changing prices
- Solution: Kotlin Flow reactive architecture

**Challenge 2: API Rate Limits**
- Problem: Free tier limitations
- Solution: Aggressive caching (60s timeout)

**Challenge 3: Complex Navigation**
- Problem: State management across screens
- Solution: Jetpack Navigation with state preservation

**Challenge 4: Dual Input Mode**
- Problem: Quantity ↔ Amount conversion
- Solution: Bidirectional reactive calculation

---

## Slide 11: Project Results
**Deliverables:**
- ✓ Fully functional Android application
- ✓ 10 screens (8 main + 2 auth)
- ✓ 4 local entities + 2 remote collections
- ✓ 4 API integrations
- ✓ ~5,000 lines of Kotlin code

**Requirements Met:**
- ✓ Local database (Room)
- ✓ Remote database (Firestore)
- ✓ API consumption (CoinGecko, Finnhub)
- ✓ Offline functionality
- ✓ Real-time features
- ✓ User authentication

---

## Slide 12: Conclusion
**Achievements:**
- Production-ready mobile application
- Modern Android development practices
- Clean Architecture implementation
- Multi-source data management
- Real-time communication
- Offline-first strategy

**Key Learnings:**
- Jetpack Compose UI development
- Firebase integration
- API consumption and caching
- Real-time data synchronization
- State management in reactive apps

**Future Potential:**
- Multi-language support
- Advanced analytics
- Push notifications
- Enhanced social features

---

## Presentation Notes:

**Slide 1 (30 seconds):**
Introduce the project name and team members.

**Slide 2 (15 seconds):**
Quick overview of presentation structure.

**Slide 3 (1 minute):**
Explain what Moneytwork does and why it's useful. Emphasize the combination of financial tracking and social networking.

**Slide 4 (1 minute):**
Discuss the technology choices and architecture pattern. Mention Clean Architecture benefits.

**Slide 5 (1.5 minutes):**
Walk through the three main feature categories. Show how they work together.

**Slide 6 (1 minute):**
Explain database structure and offline-first strategy. Highlight dual database approach.

**Slide 7 (1 minute):**
Show API integrations and how they provide comprehensive data coverage.

**Slide 8 (1 minute):**
Demonstrate real-time chat capabilities and security measures.

**Slide 9 (1 minute):**
Explain security implementation and data privacy approach.

**Slide 10 (1.5 minutes):**
Discuss major technical challenges faced and solutions implemented.

**Slide 11 (1 minute):**
Present concrete results and requirement compliance.

**Slide 12 (1 minute):**
Conclude with achievements, learnings, and future potential.

**Total Time: ~12 minutes**


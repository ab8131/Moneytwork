# 🔥 Firebase Setup Guide for Moneytwork

## ✅ What You Need to Do

Follow these steps carefully to enable authentication and chat features.

---

## Step 1: Create Firebase Project (5 minutes)

1. Go to https://console.firebase.google.com/
2. Click **"Add project"**
3. Project name: **Moneytwork**
4. Click **Continue**
5. Disable Google Analytics (optional, or enable if you want)
6. Click **Create project**
7. Wait for it to finish, then click **Continue**

---

## Step 2: Add Android App to Firebase

1. In Firebase Console, click the **Android icon** (or "Add app")
2. Fill in the form:
   - **Android package name:** `com.example.moneytwork`
   - **App nickname:** Moneytwork (or any name you like)
   - **Debug signing certificate SHA-1:** (Skip for now, not needed for basic features)
3. Click **"Register app"**

---

## Step 3: Download google-services.json ⚠️ IMPORTANT

1. Click **"Download google-services.json"**
2. **Save the file**
3. **Move it to:** `C:\Users\ahmad\AndroidStudioProjects\Moneytwork\app\`
   - It should be in the `app` folder, NOT in the root project folder
   - The path should be: `Moneytwork/app/google-services.json`

4. Click **"Next"** → **"Next"** → **"Continue to console"**

---

## Step 4: Enable Authentication

1. In Firebase Console sidebar, click **"Authentication"**
2. Click **"Get started"**
3. Click **"Email/Password"** tab
4. Toggle **"Enable"** switch to ON
5. Click **"Save"**

✅ Email/Password authentication is now enabled!

---

## Step 5: Enable Firestore Database

1. In Firebase Console sidebar, click **"Firestore Database"**
2. Click **"Create database"**
3. Choose **"Start in production mode"** (we'll set rules later)
4. Click **"Next"**
5. Select a location (choose closest to you, e.g., `us-central1`)
6. Click **"Enable"**
7. Wait for it to finish

✅ Firestore Database is now enabled!

---

## Step 6: Set Firestore Security Rules

1. In Firestore Database page, click **"Rules"** tab
2. Replace the rules with this:

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users collection - only authenticated users can read/write their own data
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Chats - anyone can read, only authenticated users can write
    match /chats/{assetId}/messages/{messageId} {
      allow read: if true;  // Anyone can read chat messages
      allow create: if request.auth != null;  // Only signed-in users can send messages
      allow delete: if request.auth != null && request.auth.uid == resource.data.userId;  // Can delete own messages
    }
  }
}
```

3. Click **"Publish"**

✅ Security rules are set!

---

## Step 7: Verify Setup

Check that you have:
- ✅ `google-services.json` in `app/` folder
- ✅ Email/Password authentication enabled
- ✅ Firestore Database created
- ✅ Firestore security rules published

---

## Step 8: Build and Run

Now build the app:

```powershell
cd C:\Users\ahmad\AndroidStudioProjects\Moneytwork
.\gradlew clean assembleDebug installDebug
```

---

## 🎉 Testing the Features

### Test Authentication:
1. Open the app
2. You'll see a **Sign In** screen
3. Click **"Don't have an account? Sign Up"**
4. Enter:
   - Username: testuser
   - Email: test@example.com
   - Password: test123
5. Click **"Sign Up"**
6. You should be signed in and see the Portfolio screen!

### Test Chat:
1. Navigate to any crypto (e.g., Bitcoin)
2. Go to the **"Chat"** tab
3. Type a message
4. Click send
5. Your message should appear!
6. Open another device/emulator and sign in with different account
7. Both users can see and send messages in real-time!

---

## 🔍 Troubleshooting

### Issue: "google-services.json not found"
**Solution:** Make sure the file is in `app/` folder, not root folder

### Issue: "Build fails with Firebase error"
**Solution:** 
1. Check `google-services.json` is in correct location
2. Run `.\gradlew clean` then build again

### Issue: "Authentication fails"
**Solution:** 
1. Check Firebase Console → Authentication → Email/Password is enabled
2. Check internet connection on emulator

### Issue: "Can't send messages"
**Solution:**
1. Make sure you're signed in
2. Check Firestore rules are published
3. Check internet connection

### Issue: "Messages don't appear"
**Solution:**
1. Check Firestore Console → you should see a "chats" collection
2. Click on "chats" → see your asset → see messages
3. If no data, check console logs for errors

---

## 📱 What's Implemented

### Authentication:
- ✅ Sign Up with username, email, password
- ✅ Sign In with email, password  
- ✅ Sign Out
- ✅ User profile stored in Firestore
- ✅ Auth state persistence (stays signed in)
- ✅ Automatic navigation (signed in → portfolio, signed out → sign in)

### Chat:
- ✅ Real-time group chat per asset (coin/stock)
- ✅ Send messages
- ✅ See all messages instantly
- ✅ Messages show username and timestamp
- ✅ Chat embedded in detail pages (crypto & stocks)
- ✅ Auto-scroll to latest message
- ✅ "Sign in required" prompt for guests

### Database Structure:
```
Firestore
├── users/
│   └── {userId}/
│       ├── uid: string
│       ├── username: string
│       ├── email: string
│       └── createdAt: timestamp
│
└── chats/
    └── {assetId}/  (e.g., "bitcoin", "AAPL")
        └── messages/
            └── {messageId}/
                ├── userId: string
                ├── username: string
                ├── message: string
                ├── assetId: string
                ├── assetName: string
                └── timestamp: number
```

---

## 🎯 Next Steps After Setup

Once Firebase is working:

1. **Test on multiple devices** - See real-time chat in action
2. **Customize chat UI** - Adjust colors, add reactions, etc.
3. **Add features**:
   - User avatars
   - Delete messages
   - Report messages
   - Message reactions
   - Push notifications

---

## 📞 If You're Stuck

1. **Check Firebase Console** - Look for errors in Authentication/Firestore
2. **Check Logcat** - Filter by "AuthRepository" or "ChatRepository"
3. **Verify internet** - Emulator must have working internet
4. **Check file location** - `google-services.json` must be in `app/` folder

---

**The code is ready! Just add `google-services.json` and Firebase will work! 🚀**


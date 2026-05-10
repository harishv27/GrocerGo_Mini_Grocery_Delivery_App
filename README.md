<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 05 PM (1)" src="https://github.com/user-attachments/assets/5eb09917-bba7-4942-9bdf-06fe99713f2a" /># 🛒 GrocerGo — Mini Grocery Delivery App

> A Blinkit-style grocery delivery Android app built with **Kotlin + MVVM + Room DB**  
> Submitted for **OceanX Agency — Kotlin Android Assignment**

---

## 📱 Screenshots

| Splash | Login | OTP Verify |
|--------|-------|------------|
| ![Splash](screenshots/<img width="1080" height="2392" alt="splash" src="https://github.com/user-attachments/assets/4a0a5a64-667e-436c-967a-1950462481a1" />.png) | ![Login](screenshot<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 04 PM (1)" src="https://github.com/user-attachments/assets/53dc139d-0ccb-43d1-b9e2-5baf42b6420e" />
s/login.png) | ![OTP](screens<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 05 PM" src="https://github.com/user-attachments/assets/1c34f0ac-3993-4e9b-b03c-4cf37c536692" />
hots/otp.png) |

| Home Screen | Category Filter | Search |
|-------------|-----------------|--------|
| ![Home](scree<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 05 PM (1)" src="https://github.com/user-attachments/assets/5b324da0-394d-46d6-ad73-810df313c7ab" />
nshots/home.png) | ![Category](screenshots/ca<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 05 PM (2)" src="https://github.com/user-attachments/assets/5cbf8074-6055-42b9-9539-b3c05c4fd855" />
tegory.png) | ![Search](screensho<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 06 PM" src="https://github.com/user-attachments/assets/436e1b33-2720-410e-80bc-8cf76356e113" />
ts/search.png) |

| Cart Screen | Checkout | Order Success |
|-------------|----------|---------------|
| ![Cart](screenshots<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 06 PM (1)" src="https://github.com/user-attachments/assets/e123f779-2efe-4a5f-8eef-b16dd77dceaf" />
/cart.png) | ![Checkout](screen<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 07 PM (1)" src="https://github.com/user-attachments/assets/ab6c87ce-3b82-44a8-841b-06e9cf4d44d0" />
shots/checkout.png) | ![Success](screenshots/order_success.png) |
<img width="540" height="1196" alt="WhatsApp Image 2026-05-10 at 12 14 07 PM (2)" src="https://github.com/user-attachments/assets/c4676ab6-122d-47af-bf51-77695dfdd48d" />

---

## 🎥 Demo

▶️ [Watch Demo Video](demo/demo_recording.mp4)

---

## 📋 Features Implemented

### 1. 🔐 Login / OTP
- Mobile number input with validation
- 4-digit OTP verification (auto-focus between boxes)
- Fake OTP: **1234**
- Navigates to Home on success
- Login state persisted via SharedPreferences

### 2. 🏠 Home Screen
- Search products by name or category
- Category filter list (All, Vegetables, Fruits, Dairy, Bakery, Snacks, Beverages, Meat)
- Product grid with emoji image, name, unit, price, discount badge
- Add to cart with +/− counter per product
- Real-time cart badge count on bottom nav

### 3. 🛒 Cart Screen
- Shows all added items with emoji, name, unit, price
- Increase / decrease quantity per item
- Remove item from cart
- Bill summary: Subtotal, Delivery Fee, Total
- Free delivery unlocked above ₹499
- Empty cart state with "Start Shopping" button

### 4. 📦 Checkout Screen
- Delivery address form: Name, Mobile, Address, City, Pincode
- Full form validation with error messages
- Payment options: Cash on Delivery / Online Payment
- Order summary panel
- Loading dialog while placing order

### 5. ✅ Order Success Screen
- Unique Order ID generated
- Estimated delivery time: 30–45 minutes
- Order summary (amount, payment mode, address)
- "Continue Shopping" navigates back to Home

---

## ⭐ Bonus Features Implemented

| Bonus Feature | Status |
|---------------|--------|
| MVVM Architecture | ✅ |
| ViewModel + LiveData | ✅ |
| StateFlow for Cart UI state | ✅ |
| Local cart using Room DB | ✅ |
| Smooth animations (slide, fade, scale, bounce) | ✅ |
| Dark mode support (theme defined) | ✅ |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Kotlin |
| Architecture | MVVM (Model-View-ViewModel) |
| Database | Room DB (local cart persistence) |
| State Management | LiveData + StateFlow |
| Async Operations | Kotlin Coroutines |
| UI | XML Layouts + Material Components 3 |
| Navigation | Fragment transactions (Bottom Nav) |
| Fonts | Poppins (Regular, Medium, Bold) |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

---

## 📁 Project Structure



---

## 🗃️ Database Schema

**Table: `cart_items`**

| Column | Type | Description |
|--------|------|-------------|
| productId | INTEGER (PK) | Unique product identifier |
| name | TEXT | Product name |
| emoji | TEXT | Product emoji icon |
| price | REAL | Unit price in ₹ |
| unit | TEXT | e.g. "1 kg", "500 g" |
| quantity | INTEGER | Quantity added to cart |

---

## 🚀 How to Run

**Prerequisites**
- Android Studio Hedgehog or later
- Android device or emulator (API 24+)
- Internet for Gradle sync

**Steps**

```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/GrocerGo.git

# 2. Open in Android Studio
# File → Open → select the GrocerGo folder

# 3. Sync Gradle
# Click "Sync Now" when prompted

# 4. Add Poppins fonts to res/font/
# Download from https://fonts.google.com/specimen/Poppins
# Add: poppins_regular.ttf, poppins_medium.ttf, poppins_bold.ttf

# 5. Run the app
# Click ▶ Run or press Shift+F10
```

**Login Credentials for Testing**
```
Mobile Number : any 10-digit number (e.g. 9876543210)
OTP           : 1234
```

---

## 📦 APK

[⬇️ Download APK](apk/grocergo-debug.apk)

---

## 🧠 Architecture Overview

```
┌─────────────────────────────────┐
│          UI Layer               │
│  Activities / Fragments         │
│  Adapters (ListAdapter+DiffUtil)│
└──────────────┬──────────────────┘
               │ observes LiveData / StateFlow
┌──────────────▼──────────────────┐
│        ViewModel Layer          │
│  HomeViewModel                  │
│  CartViewModel (StateFlow)      │
│  CheckoutViewModel              │
└──────────────┬──────────────────┘
               │ calls suspend functions
┌──────────────▼──────────────────┐
│       Repository Layer          │
│  ProductRepository (in-memory)  │
│  CartRepository (Room DB)       │
└──────────────┬──────────────────┘
               │ reads / writes
┌──────────────▼──────────────────┐
│         Data Layer              │
│  Room Database                  │
│  CartDao (Flow-based queries)   │
└─────────────────────────────────┘
```

---

## ✅ Assignment Checklist

### Requirements
- [x] Kotlin only
- [x] XML layouts only
- [x] RecyclerView (Products, Categories, Cart)
- [x] Add to cart functionality
- [x] Search / Filter
- [x] Proper validation (mobile, OTP, checkout form)
- [x] Clean UI (Material Design 3, Poppins font)
- [x] Proper code structure (MVVM, Repository pattern)

### Bonus
- [x] MVVM Architecture
- [x] ViewModel + LiveData + StateFlow
- [x] Local cart using Room DB
- [x] Smooth animations
- [x] Dark mode support

### Submission
- [x] GitHub repository link
- [x] APK file
- [x] Screen recording
- [x] README file

---

## 👨‍💻 Developed By

**[Your Name]**  
Android Developer  
📧 your.email@gmail.com  
🔗 [LinkedIn](https://linkedin.com/in/yourprofile) | [GitHub](https://github.com/yourusername)

---

*Built with ❤️ for OceanX Agency Kotlin Android Internship Assignment*

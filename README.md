# 🛒 GrocerGo — Mini Grocery Delivery App

> A Blinkit-style grocery delivery Android app built with **Kotlin + MVVM + Room DB**  
> Submitted for **OceanX Agency — Kotlin Android Assignment**

---

## 📋 Features Implemented

### 1. 🔐 Login / OTP
- Mobile number input with validation
- 4-digit OTP verification with auto-focus between boxes
- Fake OTP: **1234**
- Navigates to Home on success
- Login state persisted via SharedPreferences

### 2. 🏠 Home Screen
- Search products by name or category
- Category filter (All, Vegetables, Fruits, Dairy, Bakery, Snacks, Beverages, Meat)
- Product grid with emoji, name, unit, price, discount badge
- Add to cart with +/− counter per product
- Real-time cart badge count on bottom nav

### 3. 🛒 Cart Screen
- All added items with emoji, name, unit, price
- Increase / decrease quantity per item
- Remove item from cart
- Bill summary: Subtotal, Delivery Fee, Total
- Free delivery unlocked above ₹499
- Empty cart state with "Start Shopping" button

### 4. 📦 Checkout Screen
- Delivery address form: Name, Mobile, Address, City, Pincode
- Full form validation with error messages
- Payment options: Cash on Delivery / Online Payment
- Order summary panel + loading dialog

### 5. ✅ Order Success Screen
- Unique Order ID generated
- Estimated delivery: 30–45 minutes
- Order summary (amount, payment mode, address)
- "Continue Shopping" navigates back to Home

---

## ⭐ Bonus Features

| Feature | Status |
|---------|--------|
| MVVM Architecture | ✅ |
| ViewModel + LiveData | ✅ |
| StateFlow for Cart UI | ✅ |
| Local cart using Room DB | ✅ |
| Smooth animations | ✅ |
| Dark mode support | ✅ |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Kotlin |
| Architecture | MVVM |
| Database | Room DB |
| State Management | LiveData + StateFlow |
| Async | Kotlin Coroutines |
| UI | XML + Material Components 3 |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

---

## 📱 Screenshots

<table>
  <tr>
    <td align="center"><b>Splash</b></td>
    <td align="center"><b>Login</b></td>
    <td align="center"><b>OTP Verify</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/4a0a5a64-667e-436c-967a-1950462481a1" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/53dc139d-0ccb-43d1-b9e2-5baf42b6420e" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/1c34f0ac-3993-4e9b-b03c-4cf37c536692" width="200"/></td>
  </tr>
  <tr>
    <td align="center"><b>Home Screen</b></td>
    <td align="center"><b>Category Filter</b></td>
    <td align="center"><b>Search</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/5b324da0-394d-46d6-ad73-810df313c7ab" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/5cbf8074-6055-42b9-9539-b3c05c4fd855" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/436e1b33-2720-410e-80bc-8cf76356e113" width="200"/></td>
  </tr>
  <tr>
    <td align="center"><b>Cart Screen</b></td>
    <td align="center"><b>Checkout</b></td>
    <td align="center"><b>Order Success</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/e123f779-2efe-4a5f-8eef-b16dd77dceaf" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/ab6c87ce-3b82-44a8-841b-06e9cf4d44d0" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/c4676ab6-122d-47af-bf51-77695dfdd48d" width="200"/></td>
  </tr>
</table>

---

## 🎥 Demo

▶️ [Watch Demo Video](demo/demo_recording.mp4)

## 📁 Project Structure

<img width="874" height="1322" alt="Screenshot 2026-05-10 112819" src="https://github.com/user-attachments/assets/b832177a-7253-4ee4-862c-d2179c1080f9" />


---

## 🗃️ Database Schema

**Table: `cart_items`**

| Column | Type | Description |
|--------|------|-------------|
| productId | INTEGER (PK) | Unique product ID |
| name | TEXT | Product name |
| emoji | TEXT | Product emoji |
| price | REAL | Unit price in ₹ |
| unit | TEXT | e.g. "1 kg", "500 g" |
| quantity | INTEGER | Quantity in cart |

---

## 🚀 How to Run

```bash
# 1. Clone the repo
git clone https://github.com/harishv27/GrocerGo_Mini_Grocery_Delivery_App.git

# 2. Open in Android Studio → Sync Gradle

# 3. Run on device or emulator (API 24+)
```

**Test Login**
```
Mobile : any 10-digit number
OTP    : 1234
```


## 🧠 Architecture

```
UI Layer  →  ViewModel Layer  →  Repository Layer  →  Room DB
(Activities / Fragments / Adapters)
     observes LiveData / StateFlow
     calls suspend functions via Coroutines
```


---

## 👨‍💻 Developed By

**Harish V**  
🔗 [GitHub](https://github.com/harishv27)

---

*Built with ❤️ for OceanX Agency Kotlin Android Internship Assignment*

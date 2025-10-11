# ♻️ Souq El Khorda – Recycle & Earn Smartly

**Souq El Khorda** is a modern recycling marketplace that connects individuals and companies for selling and buying recyclable materials such as plastic, aluminum, paper, glass, and more.  
It aims to promote sustainability, reduce waste, and simplify the recycling process through an easy-to-use mobile application.

---

## 🌿 Features

Souq El Khorda offers an integrated and seamless experience that brings together all recycling services in one platform:

### 1. 🏢 **Sell to the Company**
- Choose from ready categories: **Plastic, Aluminum, Paper, Glass, Bread, Oil**, or add a **Custom Item**.  
- Specify whether selling is **by weight** or **by quantity**.  
- Attach **photos or videos** for each item.  
- Add multiple items in one order.  
- Submit your full **order directly to the company** with one click.

### 2. 🛒 **The Market (Souq)**
- Browse all posted orders from other sellers in a clean, organized list.  
- Each order shows:
  - Title, total quantities/weights, seller name, description, and posting time.  
- View full order details including:
  - Attached media (images/videos), full description, and all items included.  
- **Offer your own price** to buy an existing order.  
- Easily **create your own order** with the same interface used in “Sell to Company.”

### 3. 📍 **Nearest Buyers**
- View a **map or list** of nearby recycling shops that buy scrap materials.  
- Each shop card shows:
  - Name, contact info, and **distance from your location**.  
- Get **directions instantly** through map navigation.

---

## 🎨 Design & User Experience

- **Simple, modern, and intuitive UI**, designed for all users — even those with minimal technical background.  
- **Arabic interface**, fully localized for Egypt.  
- **Green & white color theme** to reflect sustainability and eco-friendliness.  
- **Bottom Navigation Bar** with 3 main tabs:
  - 🏢 Sell to Company  
  - 🛒 Market  
  - 📍 Nearest Buyers  

---

## 🧠 Architecture & Technologies

**Language:** Kotlin  
**UI Framework:** Jetpack Compose  
**Architecture Pattern:** MVI (Clean Architecture)  
**Async Programming:** Kotlin Coroutines + LiveData  
**Dependency Injection:** Hilt  
**Serialization:** Kotlinx Serialization  
**Navigation:** Navigation 3 (Compose Navigation)  
**Networking:** Retrofit + Gson  
**Database:** Room  
**Image Loading:** Coil  
**Animations:** Lottie Compose  
**Cloud Storage:** Cloudinary (for images & videos)  
**Location Services:** Google Play Services Location  
**Placeholder Animations:** Accompanist Placeholder (Shimmer)  
**System UI Control:** Accompanist System UI Controller  
**Firebase Integration:**
- Firestore (Data Storage)
- Firebase Authentication (User Accounts)
- Firebase Messaging (Notifications)

---

## 🌐 APIs & Services

Souq El Khorda integrates multiple cloud and system services to ensure seamless operation:

- **Firebase Firestore** – Manages users, orders, and listings.  
- **Firebase Auth** – Handles secure user authentication.  
- **Firebase Cloud Messaging** – Delivers notifications and order updates.  
- **Cloudinary** – Uploads and stores media (images/videos).  
- **Google Play Services Location** – Provides GPS and location-based shop listings.

---

## 👥 Team Members

**Souq El Khorda** is developed by a dedicated team of Android developers:

- **abdelazizmaher17499@gmail.com**  
- **alikotb38@gmail.com**  
- **youssefadelfayad@gmail.com**
- **medosaad2071@gmail.com**

---

## 📸 Screenshots
![Screenshot 1](img/1.png)  
![Screenshot 2](img/2.png)  
![Screenshot 3](img/3.png)

---

## 🎥 Video

[Watch the demo video](https://youtu.be/your-demo-link)

---

## 🛠 Setup & Installation

To get started with **Souq El Khorda**, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone <repository-url>

# Android Security Crypto

An Android project focused on **cryptographic security**, **device registration**, **message signing**, and **secure local data storage**.

The project demonstrates security practices for applications that need to protect credentials, cryptographic keys, and sensitive information stored locally, using native Android security mechanisms.

## 🔐 Features

* 🔑 Cryptographic key pair generation and management using **Android Keystore**
* 📱 Device registration and identification
* ✍️ Cryptographic message signing
* 🔒 Encryption and decryption using asymmetric cryptography
* 🛡️ Private key protection through Android Keystore
* 💾 Secure storage using `SharedPreferences`
* 🗄️ Encrypted SQLite database
* 🔐 Protection of sensitive locally stored data
* 👤 Login Activity
* 🔄 Device-based authentication flow
* 🧩 Separation of cryptographic responsibilities

---

## 🏗️ Architecture

The main application flow can be represented as follows:

```text
                         ┌─────────────────────┐
                         │      LoginActivity  │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │   Device           │
                         │   Registration     │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │   Android Keystore  │
                         │                     │
                         │   Private Key       │
                         │   Public Key        │
                         └──────────┬──────────┘
                                    │
                    ┌───────────────┴───────────────┐
                    │                               │
                    ▼                               ▼
           ┌─────────────────┐             ┌─────────────────┐
           │ Message         │             │ Encryption /    │
           │ Signing         │             │ Decryption      │
           └────────┬────────┘             └────────┬────────┘
                    │                               │
                    └───────────────┬───────────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │ Protected Local     │
                         │ Data                │
                         ├─────────────────────┤
                         │ SharedPreferences   │
                         │ SQLite              │
                         └─────────────────────┘
```

---

## 🔑 Android Keystore

The project uses **Android Keystore** for cryptographic key management.

The main goal is to prevent private keys from being directly stored as plain text or as regular application files.

Conceptually:

```text
Android Keystore
       │
       ├── Private Key
       │
       └── Public Key
```

The private key remains protected by the Android Keystore, while the public key can be exported and registered with a backend when required.

---

## ✍️ Message Signing

The project demonstrates cryptographic message signing using a private key stored in the Android Keystore.

The general flow is:

```text
Message
   │
   ▼
Private Key
   │
   ▼
Digital Signature
   │
   ▼
Backend
   │
   ▼
Public Key
   │
   ▼
Signature Verification
```

This allows the backend to verify that the sender possesses the private key without requiring the private key to leave the Android device.

For RSA-based signatures, the project can use **RSA-PSS with SHA-256**.

---

## 🔒 Asymmetric Encryption

The project also demonstrates encryption and decryption using an asymmetric key pair.

Conceptually:

```text
              Public Key
                  │
                  ▼
               Encrypt
                  │
                  ▼
              Ciphertext
                  │
                  ▼
               Decrypt
                  │
                  ▼
              Private Key
```

For RSA encryption, **RSA-OAEP** can be used.

The project distinguishes between the two RSA mechanisms:

* **RSA-PSS** → digital signatures
* **RSA-OAEP** → encryption

These mechanisms serve different cryptographic purposes and should not be treated as interchangeable.

---

## 📱 Device Registration

During authentication, the application can register its cryptographic identity with the backend.

A simplified flow:

```text
1. User logs in
        │
        ▼
2. Application generates key pair
        │
        ▼
3. Private Key → Android Keystore
        │
        ▼
4. Public Key → Backend
        │
        ▼
5. Backend associates public key with device
        │
        ▼
6. Device registered
```

After registration, subsequent requests can use the private key to prove possession of the registered cryptographic identity.

---

## 💾 Secure SharedPreferences

The project demonstrates secure handling of locally stored information.

Sensitive data should **not be stored directly as plaintext**.

Conceptually:

```text
Sensitive Data
      │
      ▼
Encryption
      │
      ▼
Ciphertext
      │
      ▼
Local Storage
```

Encryption keys should be protected separately from the encrypted data, preferably using Android Keystore when appropriate.

The project uses this concept to demonstrate safer handling of sensitive local application data.

---

## 🗄️ Encrypted SQLite

The project also demonstrates the use of an encrypted SQLite database for information that requires local persistence.

Conceptually:

```text
Application
     │
     ▼
Database Layer
     │
     ▼
Encrypted SQLite
     │
     ▼
Protected Data
```

Encrypting the database adds an additional layer of protection for locally persisted information.

---

## 🛡️ Security Principles

The project follows several security principles.

### Private Keys Should Not Be Stored Directly

```text
❌ SharedPreferences
❌ Plain files
❌ SQLite

✅ Android Keystore
```

### Separation of Responsibilities

Cryptographic operations should be isolated from the UI layer.

```text
Activity
   │
   ▼
Authentication / Device Service
   │
   ▼
Crypto Manager
   │
   ▼
Android Keystore
```

### Minimize Sensitive Data

Sensitive information should not remain in memory longer than necessary.

### Encryption Is Not Authentication

Encryption alone does not prove the identity of the user or device.

A secure architecture should consider:

```text
Confidentiality
       +
Integrity
       +
Authenticity
       +
Key Protection
```

---

## 🧩 Project Structure

A possible project organization:

```text
app/
├── src/
│   └── main/
│       ├── java/
│       │   └── ...
│       │       ├── activity/
│       │       │   └── LoginActivity.kt
│       │       │
│       │       ├── crypto/
│       │       │   ├── CryptoManager.kt
│       │       │   └── KeyManager.kt
│       │       │
│       │       ├── security/
│       │       │   └── DeviceManager.kt
│       │       │
│       │       ├── storage/
│       │       │   ├── SecurePreferences.kt
│       │       │   └── SecureDatabase.kt
│       │       │
│       │       └── network/
│       │           └── ApiService.kt
│       │
│       └── res/
│
└── README.md
```

The structure above is only a reference and can be adapted according to the application's architecture.

---

## 🔐 Technologies

* **Kotlin**
* **Android SDK**
* **Android Keystore**
* **Java Cryptography Architecture (JCA)**
* **RSA**
* **RSA-PSS**
* **RSA-OAEP**
* **SHA-256**
* **SharedPreferences**
* **SQLite**
* **Encrypted database storage**
* **REST API**

---

## 🔄 Authentication Flow

A simplified authentication flow can be represented as:

```text
┌──────────────┐
│   Android    │
│ Application  │
└──────┬───────┘
       │
       │ Login
       ▼
┌──────────────┐
│    Backend   │
└──────┬───────┘
       │
       │ Register device
       ▼
┌──────────────────┐
│ Generate RSA Key │
│      Pair        │
└────────┬─────────┘
         │
         ├──────────────► Private Key
         │                Android Keystore
         │
         ▼
    Public Key
         │
         ▼
┌──────────────────┐
│ Backend stores   │
│ public key       │
└────────┬─────────┘
         │
         ▼
    Device Registered
         │
         ▼
    Signed Requests
         │
         ▼
┌──────────────────┐
│ Backend verifies │
│ the signature    │
└──────────────────┘
```

---

## ⚠️ Security Considerations

This project is intended for **educational and demonstration purposes**.

A production security implementation should also consider:

* Replay attack protection
* Session management
* Key rotation and revocation
* Cryptographic downgrade protection
* TLS certificate validation
* Server authentication
* Secure token storage
* Credential expiration
* Device revocation
* Application integrity
* Secure logging
* Memory protection
* Secure error handling

Cryptography should be treated as one component of a broader security architecture rather than as a standalone security mechanism.

---

## 📚 Project Goals

This project is intended as a practical reference for Android developers interested in:

* Applied Android cryptography
* Android Keystore
* Cryptographic key management
* Digital signatures
* Asymmetric encryption
* Secure local storage
* Encrypted SQLite databases
* Device registration
* Key-based authentication
* Secure Android application architecture

---

## 📄 License

This project is provided for educational and research purposes.

Add or modify the license according to the requirements of your project.

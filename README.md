# 🚀 Rate Limiter as a Service (RLaaS)

A production-grade distributed rate limiter built using **Spring Boot, Redis, and PostgreSQL**, implementing the **Token Bucket algorithm with Lua scripting** to handle high-concurrency API requests safely.

---

## 🌍 Live Demo

* 🔗 Live API: https://ratelimiter-kkpy.onrender.com
* 💻 GitHub: https://github.com/harshwb12/rateLimiter

---

## 🧠 Problem Statement

Modern APIs need protection against:

* Abuse and excessive traffic
* DDoS or bot attacks
* Resource exhaustion

This project provides a **scalable backend service** that enforces request limits per user using efficient in-memory data structures.

---

## ⚙️ Tech Stack

* **Backend:** Spring Boot
* **Database:** PostgreSQL (Neon)
* **Cache:** Redis (Upstash)
* **Algorithm:** Token Bucket
* **Concurrency Handling:** Redis Lua Scripts
* **Deployment:** Docker + Render

---

## 🏗️ Architecture

Client → Interceptor → RateLimiterService → Redis → DB (for API key validation)

* **Interceptor**: Captures incoming requests
* **RateLimiterService**: Applies rate limiting logic
* **Redis**: Stores tokens + timestamps
* **PostgreSQL**: Stores user + API key data

---

## 🔥 Features

* 🔑 API Key based access control
* ⚡ High-performance rate limiting using Redis
* 🪣 Token Bucket algorithm (handles burst traffic)
* 🔒 Atomic operations using Lua scripts (no race conditions)
* 📊 Usage tracking endpoint
* 🌍 Fully deployed on cloud

---

## 🧩 API Endpoints

### 1. Register User

POST /auth/register?email=[test@gmail.com](mailto:test@gmail.com)

Response:

```json
{
  "id": 1,
  "email": "test@gmail.com",
  "apiKey": "your-api-key"
}
```

---

### 2. Access Protected API

GET /api/hello

Header:

```
x-api-key: your-api-key
```

---

### 3. Usage Info

GET /api/usage

Header:

```
x-api-key: your-api-key
```

---

## 🧠 How Rate Limiting Works

### Token Bucket Algorithm

* Each user has a bucket with limited tokens
* Every request consumes 1 token
* Tokens refill over time
* If no tokens → request is rejected (429)

---

## ⚡ Why Redis?

* In-memory → ultra-fast
* Atomic operations
* Perfect for counters & rate limiting

---

## 🔒 Why Lua Script?

* Executes inside Redis
* Ensures atomic updates
* Prevents race conditions in concurrent systems

---

## 🚀 How to Run Locally

```bash
git clone https://github.com/harshwb12/rateLimiter
cd rateLimiter
./mvnw clean install
java -jar target/*.jar
```

---

## 🔮 Future Improvements

* API usage dashboard
* Rate limit tiers (free vs premium)
* Metrics & monitoring (Prometheus)
* Distributed scaling

---

## 📌 Key Learnings

* Distributed system design
* Redis + caching strategies
* Handling race conditions
* Production deployment
* Backend architecture best practices

---

## 👨‍💻 Author

Harsh
Backend Developer (Spring Boot | System Design)

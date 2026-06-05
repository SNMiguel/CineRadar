# 🎬 CineRadar

> A movie discovery app for Android that lets you search and explore films in real time, powered by [The Movie Database (TMDB)](https://www.themoviedb.org/) API.

CineRadar is a lightweight, native Android application built in Kotlin. Type a movie title and CineRadar instantly fetches matching results from TMDB, displaying each film's poster, title, and overview in a smooth, scrollable list. It was created as part of **AND101 – Mobile App Development (Milestone 2)**.

---

## 📑 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Screenshots & Demo](#-screenshots--demo)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [TMDB API Configuration](#-tmdb-api-configuration)
- [How It Works](#-how-it-works)
- [Roadmap](#-roadmap)
- [Known Limitations](#-known-limitations)
- [Author](#-author)
- [Acknowledgements](#-acknowledgements)
- [License](#-license)

---

## 🔭 Overview

CineRadar solves a simple problem: **finding information about a movie quickly without leaving the app**. Rather than browsing a website, users get an at-a-glance, poster-first view of search results pulled live from one of the largest community-maintained film databases in the world.

The app is intentionally focused. It does one thing well — **search** — and presents the results with a clean, cinematic feel using custom fonts and a dark, theatre-inspired theme. Under the hood it demonstrates the core building blocks of a modern Android app: REST networking, JSON parsing, asynchronous callbacks, image loading and caching, and efficient list rendering with a `RecyclerView`.

**Why I built it:** CineRadar was a solo project that gave me hands-on experience integrating a third-party API, structuring an app into clean, single-responsibility classes, and handling the realities of network programming — loading states, failures, and offline conditions.

---

## ✨ Features

| Feature | Description |
| --- | --- |
| 🔎 **Live movie search** | Enter any title and submit to query TMDB's `search/movie` endpoint in real time. |
| 🖼️ **Poster-first results** | Each result renders the film's official poster, loaded and cached via Glide with a graceful placeholder while images download. |
| 📜 **Smooth scrolling list** | Results are displayed in a `RecyclerView` with a `LinearLayoutManager` for efficient, recycled view rendering. |
| 🌐 **Network error handling** | API failures and connectivity problems surface friendly `Toast` messages ("Failed to load movies" / "Network Error") instead of crashing. |
| 🎨 **Custom cinematic styling** | Imported Google Fonts and a themed color palette give the app a distinct, movie-theatre vibe. |
| ⚡ **Asynchronous networking** | All requests run off the main thread using Retrofit's `enqueue` callbacks, keeping the UI responsive. |

---

## 📸 Screenshots & Demo

🎥 **Demo video / GIF:** https://imgur.com/a/4GwzM0R.gif

> _Recorded with [ScreenToGif](https://www.screentogif.com/)._

> _Tip: drop static screenshots into a `/screenshots` folder and link them here, e.g._ `![Search screen](screenshots/search.png)`.

---

## 🧰 Tech Stack

| Layer | Technology |
| --- | --- |
| **Language** | [Kotlin](https://kotlinlang.org/) |
| **Platform** | Android (native) |
| **Networking** | [Retrofit 2](https://square.github.io/retrofit/) (`2.9.0`) |
| **JSON parsing** | [Gson](https://github.com/google/gson) via `converter-gson` |
| **Image loading** | [Glide](https://github.com/bumptech/glide) (`4.12.0`) |
| **UI toolkit** | Android Views — `RecyclerView`, `CardView`, `SearchView`, `AppCompat` |
| **Build system** | [Gradle](https://gradle.org/) (Kotlin DSL) with version catalogs (`libs.versions.toml`) |
| **Data source** | [TMDB API v3](https://developer.themoviedb.org/) |

**Build configuration**

| Setting | Value |
| --- | --- |
| `compileSdk` | 36 |
| `targetSdk` | 36 |
| `minSdk` | 24 (Android 7.0 Nougat) |
| `applicationId` | `com.example.cineradar` |
| `versionName` | `1.0` |
| Java / JVM target | 11 |

> ℹ️ The build file also wires up Jetpack Compose and Material 3 dependencies, but the current UI is implemented with the classic Android View system (XML layouts + `RecyclerView`). The Compose dependencies are present for future migration.

---

## 🏗️ Architecture

CineRadar follows a straightforward, single-screen architecture with clear separation of concerns. Each class has one job:

```
┌─────────────────┐      user query       ┌──────────────────┐
│  MainActivity   │ ─────────────────────▶ │  RetrofitInstance│
│  (SearchView +  │                        │  (HTTP client)   │
│   RecyclerView) │                        └────────┬─────────┘
└────────┬────────┘                                 │
         │                                          ▼
         │                                 ┌──────────────────┐
         │                                 │   ApiService     │
         │                                 │ GET search/movie │
         │                                 └────────┬─────────┘
         │                                          │ JSON
         │   updateList(movies)            ┌────────▼─────────┐
         │ ◀────────────────────────────── │  MovieResponse   │
         ▼                                 │  → List<Movie>   │
┌─────────────────┐                        └──────────────────┘
│  MovieAdapter   │  binds each Movie → item_movie.xml (poster + title)
│  + Glide        │
└─────────────────┘
```

**Flow in plain English:**
1. The user types a query into the `SearchView` and submits.
2. `MainActivity` calls `RetrofitInstance.api.searchMovies(query)`.
3. Retrofit performs the HTTP `GET` to TMDB and Gson deserializes the JSON into a `MovieResponse`.
4. The list of `Movie` objects is handed to `MovieAdapter` via `updateList()`.
5. The adapter binds each movie to a row, and Glide loads the poster image.

---

## 📂 Project Structure

```
CineRadar/
├── app/
│   ├── build.gradle.kts            # App-level Gradle config & dependencies
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml # INTERNET permission, launcher activity
│           ├── java/com/example/cineradar/
│           │   ├── MainActivity.kt      # Entry point: search UI + result wiring
│           │   ├── ApiService.kt        # Retrofit interface (search/movie)
│           │   ├── RetrofitInstance.kt  # Singleton Retrofit client (base URL)
│           │   ├── Movie.kt             # Data model for a single film
│           │   ├── MovieResponse.kt     # Wrapper for the TMDB results array
│           │   ├── MovieAdapter.kt      # RecyclerView adapter + Glide loading
│           │   └── ui/theme/            # Color, Type & Theme (Compose theme files)
│           └── res/
│               ├── layout/
│               │   ├── activity_main.xml   # SearchView + RecyclerView
│               │   └── item_movie.xml      # Single result row (poster + title)
│               ├── drawable/movie_placeholder.xml
│               ├── values/                 # colors, strings, styles, themes
│               └── ...
├── build.gradle.kts                # Root Gradle config
├── settings.gradle.kts
├── gradle/                         # Wrapper + version catalog (libs.versions.toml)
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (Ladybug / latest stable recommended)
- **JDK 11** or newer
- An **Android device or emulator** running API 24 (Android 7.0) or higher
- Internet connection (the app fetches all data live from TMDB)

### Run it

1. **Clone the repository**
   ```bash
   git clone https://github.com/SNMiguel/CineRadar.git
   cd CineRadar
   ```
2. **Open in Android Studio** — `File → Open` and select the project root. Let Gradle sync finish.
3. **Build & run** — select a device/emulator and press **Run ▶** (or `Shift+F10`).

   Or build from the command line:
   ```bash
   # macOS / Linux
   ./gradlew assembleDebug

   # Windows
   gradlew.bat assembleDebug
   ```
   The APK will be generated under `app/build/outputs/apk/debug/`.

---

## 🔑 TMDB API Configuration

CineRadar talks to **TMDB API v3**:

- **Base URL:** `https://api.themoviedb.org/3/`
- **Endpoint used:** `GET search/movie?query={query}&api_key={key}`
- **Image CDN:** posters are loaded from `https://image.tmdb.org/t/p/w500{poster_path}`

The project currently ships with a hard-coded API key in `ApiService.kt` for demo purposes.

> ⚠️ **Security note:** committing an API key to source control is not recommended for production apps. To use your own key:
> 1. Create a free account at [themoviedb.org](https://www.themoviedb.org/signup) and request an API key.
> 2. Move the key out of source — e.g. into `local.properties` or `gradle.properties` (which are git-ignored) and expose it via `BuildConfig`, then reference `BuildConfig.TMDB_API_KEY` in `ApiService.kt`.
> 3. Consider rotating the demo key included in this repo before any public release.

---

## 🔍 How It Works

**`ApiService.kt`** — declares the REST contract with Retrofit annotations:
```kotlin
interface ApiService {
    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "..."
    ): Call<MovieResponse>
}
```

**`RetrofitInstance.kt`** — lazily builds a single Retrofit client with the TMDB base URL and the Gson converter, so the whole app shares one HTTP stack.

**`MainActivity.kt`** — sets up the `SearchView` listener; on submit it enqueues the request and, in the callback, either updates the adapter with results or shows an error `Toast`.

**`MovieAdapter.kt`** — a `RecyclerView.Adapter` that inflates `item_movie.xml`, sets the title, and uses Glide to load the poster (with `movie_placeholder` shown while loading).

---

## 🗺️ Roadmap

Planned / potential enhancements:

- [ ] **Movie detail screen** — tap a result to open full details (rating, release date, runtime, cast).
- [ ] **Trending & Top Rated tabs** — browse `trending/movie` and `movie/top_rated` without searching.
- [ ] **Splash screen** with the CineRadar logo.
- [ ] **Debounced live search** (results as you type, not just on submit).
- [ ] **Empty & loading states** — progress indicator and "no results" messaging.
- [ ] **Favorites** — save movies locally with Room.
- [ ] **Move the API key** out of source into `BuildConfig`.
- [ ] **Migrate UI to Jetpack Compose** (dependencies are already in place).

---

## ⚠️ Known Limitations

- Search results are returned **only on query submit**, not as you type.
- There is currently **no dedicated detail view** — each row shows the title and poster only (the `overview` field is fetched but not yet displayed on a detail screen).
- Trending / top-rated browsing is on the roadmap but **not yet implemented**; the app is search-driven.
- The TMDB API key is **embedded in source** (see the security note above).

---

## 👤 Author

Designed and built entirely by **Miguel Shema Ngabonziza** for AND101 – Milestone 2 (20+ hours total). Every part of CineRadar — the TMDB API integration, networking layer, UI, and styling — was implemented solo.

---

## 🙏 Acknowledgements

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for the film data and imagery. _This product uses the TMDB API but is not endorsed or certified by TMDB._
- [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson) by Square / Google.
- [Glide](https://github.com/bumptech/glide) for image loading and caching.
- **AND101 – Mobile App Development** course staff and curriculum.

---

## 📄 License

This project was created for educational purposes as part of a course milestone. If you intend to reuse or distribute it, please add an explicit license (e.g. [MIT](https://choosealicense.com/licenses/mit/)) and ensure compliance with the [TMDB API Terms of Use](https://www.themoviedb.org/api-terms-of-use).

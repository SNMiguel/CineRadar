# **CineRadar**

## Table of Contents

1. [App Overview](#App-Overview)  
2. [Product Spec](#Product-Spec)  
3. [Wireframes](#Wireframes)  
4. [Build Notes](#Build-Notes)  

## App Overview

### Description 

**CineRadar** is an Android app that allows users to explore and search for movies using data fetched from the TMDB API. Users can browse trending movies, perform keyword-based searches, and view detailed information about each movie including poster, title, overview, rating, and release date.

### App Evaluation

- **Category:** Entertainment / Movie Discovery  
- **Mobile:** Built natively for Android using Kotlin and Android Studio  
- **Story:** Helps users find information about popular and trending movies using a clean and simple interface  
- **Market:** Useful for any Android user interested in discovering or researching movies  
- **Habit:** Can be used occasionally when looking for movie ideas or planning a watchlist  
- **Scope:** A simple but functional MVP focused on search and detail display, with room for future expansion (e.g. favorites, user login, genres, etc.)

## Product Spec

### 1. User Features (Required and Optional)

**Required Features:**

- Search for movies by keyword  
- View a list of trending/popular movies on launch  
- Tap on a movie to view detailed info  
- Use RecyclerView to show movie results  
- Apply consistent app theme and custom font styling  
- Load movie posters using Glide  
- Handle loading states and basic error cases

**Stretch Features (not implemented but possible):**

- Add to Favorites (local storage)  
- Filter by genre or rating  
- Offline caching  
- User authentication for personalizing favorites/watchlist  
- Dark mode toggle  

### 2. Chosen API(s)

**TMDB API**  
- `GET /search/movie`  
  - Used for keyword-based movie search  
- `GET /movie/popular` or `GET /trending/movie/day`  
  - Used to show trending or popular movies  
- `GET /movie/{movie_id}`  
  - Used to display detailed info about selected movie  

### 3. User Interaction

**Required Features**

- User opens the app  
  - => Trending movies are displayed in a scrollable list  
- User types a movie name in the search bar  
  - => Search results are fetched from TMDB and shown in a list  
- User taps on a movie item in the list  
  - => App navigates to a detail screen showing full movie info  
- App fetches data from API  
  - => Loading spinner is shown during network request  
- API call fails (e.g., no internet)  
  - => Error UI or toast message shown

## Wireframes

<!-- Replace the URL below with your actual uploaded image URL -->
![wireframe](https://imgur.com/a/EOrM3qE.gif)

### [BONUS] Digital Wireframes & Mockups

> *Not yet implemented*

### [BONUS] Interactive Prototype

> *Not yet implemented*

## Build Notes

- Built using Kotlin and Android Studio  
- Followed MVVM architecture:  
  - **Model** = Movie data classes  
  - **ViewModel** = MovieViewModel for managing API logic  
  - **View** = MainActivity and DetailsActivity (UI logic only)  
- Used Retrofit for networking  
- Used Glide for image loading  
- Implemented custom font using `res/font`  
- Designed using XML layout and consistent theme across the app  

**Milestone 2 Build Demo:**  
- ![🎥 Add Video/GIF Here](https://imgur.com/a/ijFtxXL.gif)

## License

Copyright 2025  
Miguel Shema Ngabonziza, Zablon Geletu, John Nti Anokye, Vensen Sibanda

Licensed under the Apache License, Version 2.0 (the "License");  
you may not use this file except in compliance with the License.  
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software  
distributed under the License is distributed on an "AS IS" BASIS,  
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
See the License for the specific language governing permissions and  
limitations under the License.

# AltMusic

**A Java & React-based music information discovery app powered by the Spotify API**   
University: **University of Cyprus**  
Course: **ECE318 – Programming Principles for Engineers**  
Date: **December 2022**

---

## Overview

AltMusic is a full-stack web application that allows users to search for and explore songs, albums, and artists using the Spotify API.

This application was developed as a final assignment for the university course "Programming Principles for Engineers" and showcases an end-to-end integration of a Java backend with a dynamic React.js frontend. It is structured following object-oriented programming principles, RESTful design patterns, and emphasizes clean code architecture.

---

## Project Highlights

- Generalized search interface for tracks, albums, and artists
- Track metadata including duration, type, popularity, and album details
- Album metadata including release date, track listings, and album art
- Artist information including genres, popularity, and linked albums
- Direct navigation to the Spotify page for any track, album, or artist
- Fully modular backend and frontend design for extensibility
- Implements clean MVC structure in the backend

---

## Features

- Unified search interface for all Spotify content types
- Seamless fetching and rendering of track, album, and artist details
- External linking to Spotify pages
- Ability to perform multiple searches without refreshing the interface
- Frontend and backend components clearly separated

---

## Repository Contents

| File / Directory                     | Description                                                                 |
|-------------------------------------|-----------------------------------------------------------------------------|
| AltMusic - Report.docx              | Full report (in Greek) detailing project goals, structure, and comments     |
| AltMusic Class Diagram.png          | UML class diagram outlining backend structure                               |
| Demo Video.mov                      | Screen recording showcasing full functionality of the app                   |
| src/                                | Source code for backend and frontend implementations                        |
| README.md                           | This document                                                               |

---

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/AltMusic.git
cd AltMusic
```

### 2. Backend Setup (Java)

- Open the backend project (found inside `src/backend`) using an IDE such as IntelliJ IDEA or Eclipse.
- Insert your Spotify Developer API credentials (Client ID and Client Secret).
- Build and run the application (it will expose endpoints for search, track info, album info, and artist info).

You will need to create a Spotify Developer account at: https://developer.spotify.com/dashboard/

### 3. Frontend Setup (React)

```bash
cd src/frontend
npm install
npm start
```

Ensure the React frontend is correctly configured to send requests to the backend server (e.g., `http://localhost:8080/`).

---

## Usage

1. Launch the React application.
2. Click "Start Exploring" on the welcome screen.
3. Enter the name of a song, album, or artist in the search bar.
4. Select an item from the results to view its details.
5. Click on the title to open the corresponding Spotify page.

Refer to the `Demo Video.mov` for a full walkthrough.

---

## System Architecture

### Backend (Java)

- Built using an object-oriented structure with classes for `SpotifyService`, `SpotifyServiceImplementation`, and data models (`TrackItem`, `AlbumItem`, `ArtistItem`, `SearchItem`)
- Provides four main endpoints:
  - `/search`: Accepts a string query and returns relevant tracks, albums, and artists
  - `/trackInfo`: Returns detailed track information by ID
  - `/albumInfo`: Returns album information by ID
  - `/artistInfo`: Returns artist information by ID

### Frontend (React.js)

- Main component handles user input and manages which results to fetch and display
- Three separate display components:
  - Track Details
  - Album Details
  - Artist Details
- Minimal styling with potential for enhancement through unified CSS theming

---

## Known Issues and Limitations

- Lyrics functionality was initially planned but could not be implemented due to licensing restrictions. Spotify uses Musixmatch, which requires a paid API key, and the Genius API could not be used due to ID incompatibility.
- Styling is embedded per component instead of being abstracted to a shared stylesheet. With more time, a centralized CSS file could reduce code repetition and improve maintainability.

---

## Lessons Learned

- Integration of third-party APIs (authentication, rate limits, response parsing)
- Clean separation of concerns in backend design
- Use of React functional components for frontend logic and rendering
- Strategies for error handling and API response validation

---

## License

This repository is part of a university assignment.  
All rights reserved. This project is proprietary and is not licensed for reuse or distribution without explicit permission from the author.

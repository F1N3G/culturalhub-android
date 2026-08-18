# CulturalHub Android

> **Work in progress** — the core architecture (networking, repository,
> ViewModels, navigation) is in place. Booking, authentication and search
> are not implemented yet.

Native Android client for [CulturalHub](https://github.com/F1N3G/cultural-hub),
a Laravel platform for cultural events. The app consumes the platform's REST
API and presents events in a Jetpack Compose interface.

## Current State

**Implemented**
- Event list fetched from the CulturalHub REST API
- Event detail screen, navigable by event ID
- Loading / success / error states handled explicitly across both screens
- Image loading with a shared OkHttp client

**Planned**
- Ticket booking and seat selection
- Authentication against the platform's user accounts
- Search and category filtering
- Offline caching

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM |
| Navigation | Navigation Compose 2.8.5 |
| Networking | Retrofit 2.11 + Gson, OkHttp logging interceptor |
| Async | Kotlin Coroutines, StateFlow |
| Images | Coil 2.7 |
| Min SDK | 24 (target 36) |

## Architecture

```
UI (Compose)  →  ViewModel  →  Repository  →  Retrofit API  →  Laravel backend
                     ↓
                 StateFlow
```

**Model** (`model/Event.kt`) — a plain data class describing an event, with no
behaviour attached.

**Repository** (`data/EventRepository.kt`) — the single access point for event
data. It calls the API and maps the DTOs into domain models, so the rest of the
app never sees the network layer's shape.

**ViewModels** — each screen exposes its state as a `StateFlow` of a sealed
interface, so the UI can only ever be in one of three defined states:

```kotlin
sealed interface EventsUiState {
    data object Loading : EventsUiState
    data class Success(val events: List<Event>) : EventsUiState
    data class Error(val message: String) : EventsUiState
}
```

This makes the error and loading paths impossible to forget — the Compose layer
has to handle every branch.

**Navigation** — a single `NavHost` with two destinations, the detail screen
receiving the event ID as a typed integer argument.

## Project Structure

```
app/src/main/java/com/g/culturalhub/
├── CulturalHubApp.kt          # Application class, shared Coil ImageLoader
├── MainActivity.kt            # NavHost and destinations
├── model/
│   └── Event.kt               # Domain model
├── data/
│   ├── EventRepository.kt     # Data access
│   └── remote/                # Retrofit service, DTOs, mappers
└── ui/
    ├── EventListScreen.kt
    ├── EventListViewModel.kt
    ├── EventDetailScreen.kt
    ├── EventDetailViewModel.kt
    └── theme/
```

## Getting Started

The app expects a running CulturalHub backend.

1. Start the [CulturalHub](https://github.com/F1N3G/cultural-hub) Laravel
   application locally.
2. Set the API base URL in `data/remote/ApiClient.kt`. When using the Android
   emulator, `10.0.2.2` maps to the host machine's `localhost`.
3. Open the project in Android Studio and run on a device or emulator
   (API 24+).

## Related

- [CulturalHub](https://github.com/F1N3G/cultural-hub) — Laravel backend and web platform
- [Cultural Event Recommender](https://github.com/F1N3G/cultural_event_recommender) — Python recommendation engine for the same domain

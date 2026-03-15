# Android Betting App - Betsson Interview Test

A modern Android application demonstrating Clean Architecture, MVVM pattern, and reactive programming with Kotlin Coroutines.

## Project Overview

This is a refactored betting odds management application that evolved from a basic single-activity UI to a fully architected, testable application following Android best practices.

## Architecture

### Clean Architecture Layers

```
presentation/
├── ui/
│   ├── activity/        # MainActivity (Fragment container)
│   ├── fragment/        # BetsFragment (UI composition)
│   └── adapter/         # RecyclerView adapters
├── viewmodel/          # BetsViewModel (state management)
└── state/              # BetsUiState (sealed class)

domain/
├── model/              # Domain entities (Bet)
├── repository/         # Repository interfaces
└── usecase/            # Business logic (FetchBetsUseCase, UpdateBetsOddsUseCase)

data/
├── datasource/         # BetLocalDataSource (data fetching)
└── repository/         # BetRepositoryImpl (implementation)

di/                     # Hilt dependency injection modules
utils/                  # OddsCalculator (odds calculation logic)
```

### Design Patterns

- **MVVM**: ViewModel manages UI state with StateFlow
- **Clean Architecture**: Separation of concerns across layers
- **Repository Pattern**: Abstraction of data sources
- **Use Cases**: Encapsulated business logic
- **Dependency Injection**: Hilt for automated DI
- **Fragment-based UI**: Modern Fragment composition over direct Activity UI

## Key Components

### ViewModel (BetsViewModel)
- Reactive state management using `StateFlow<BetsUiState>`
- Handles loading, success, and error states
- Delegates business logic to use cases
- Coroutine-based async operations

### Fragment (BetsFragment)
- UI layer with @AndroidEntryPoint for Hilt injection
- Observes ViewModel state using `repeatOnLifecycle`
- RecyclerView-based bet list rendering
- Update odds button integration

### Domain Layer
- **FetchBetsUseCase**: Retrieves initial bet data
- **UpdateBetsOddsUseCase**: Updates odds based on bet strategies
- **OddsCalculator**: Strategy-based odds calculation
  - Default strategy (most bets)
  - First goal scorer (special handling)
  - Total score (increases over time)
  - Number of fouls (accelerated increases)

## State Management

```kotlin
sealed class BetsUiState {
    object Loading : BetsUiState()
    data class Success(val bets: List<Bet>) : BetsUiState()
    data class Error(val message: String) : BetsUiState()
}
```

## Testing

Unit test suite covering:
- **BetsViewModelTest**: ViewModel state management and use case integration
- **OddsCalculatorTest**: Odds calculation logic with parametrized tests
- **Strategy Tests**: Individual bet strategy implementations

Tests use:
- JUnit 4 framework
- Mockito for mocking dependencies
- Coroutine test utilities (MainDispatcherRule)

## Evolution

### Initial State (Commit 0b076273)
Basic Activity with inline RecyclerView + hardcoded odds calculation

### Current State (Commit a0f3a6f)
- Clean separation of domain logic from UI
- Fragment-based UI composition with FragmentContainerView
- Hilt dependency injection throughout
- Reactive StateFlow state management
- Unit test coverage
- Strategy pattern for odds calculation

## Build & Run

### Prerequisites
- Android SDK 34+
- Kotlin 1.8.0+
- Java 17

### Build Commands

```bash
# Build the app
./gradlew build

# Run tests
./gradlew test

# Build debug APK
./gradlew assembleDebug

# Run on emulator/device
./gradlew installDebug
```

### Build Status
- **Framework**: Android Gradle Plugin
- **Kotlin Compiler**: 1.8.0
- **Target SDK**: 34
- **Min SDK**: 24

## Dependencies

### Core Android
- androidx.appcompat:appcompat:1.4.1
- androidx.constraintlayout:constraintlayout:2.1.3
- androidx.fragment:fragment-ktx:1.6.1

### Lifecycle & ViewModel
- androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1
- androidx.lifecycle:lifecycle-livedata-ktx:2.6.1
- androidx.activity:activity-ktx:1.7.2

### Dependency Injection
- com.google.dagger:hilt-android:2.44

### Navigation & UI
- androidx.navigation:navigation-fragment-ktx:2.7.4
- androidx.navigation:navigation-ui-ktx:2.7.4

### Image Loading
- io.coil-kt:coil:2.5.0

### Testing
- junit:junit:4.13.2
- org.mockito:mockito-core:5.5.0
- org.mockito.kotlin:mockito-kotlin:5.1.0
- kotlinx.coroutines:kotlinx-coroutines-test:1.7.3

# Android Betting App - Betsson Interview Test

A modern Android application demonstrating Clean Architecture, MVVM pattern, and reactive programming with Kotlin Coroutines.

<img width="346" height="751" alt="Screenshot 2026-03-27 at 12 08 27" src="https://github.com/user-attachments/assets/a5546f7b-55f9-44fc-a2e1-cbc93d0a04c4" />


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
├── repository/         # Repository interfaces (data contracts)
├── usecase/            # Business logic use cases
│   ├── FetchBetsUseCase
│   └── UpdateBetsOddsUseCase
└── service/            # Business logic services
    ├── OddsCalculator (odds calculation orchestrator)
    ├── OddsUpdateStrategy (interface)
    ├── DefaultBetStrategy
    ├── FirstGoalScorerStrategy
    ├── TotalScoreBetStrategy
    └── NumberOfFoulsStrategy

data/
├── datasource/         # BetLocalDataSource (data fetching)
└── repository/         # BetRepositoryImpl (data source abstraction)

di/                     # Hilt dependency injection modules
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
- **FetchBetsUseCase**: Retrieves initial bet data from repository
- **UpdateBetsOddsUseCase**: Owns and executes odds calculation business logic via OddsCalculator
- **OddsCalculator**: Strategy-based odds calculation service
  - Strategy Pattern implementation with multiple bet type strategies
  - Default strategy (most bets)
  - First goal scorer (special handling - no change)
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
- **OddsCalculatorTest**: Odds calculation logic with strategy routing
- **Strategy Tests**: Individual bet strategy implementations
  - DefaultBetStrategyParameterizedTest
  - FirstGoalScorerStrategyTest
  - TotalScoreBetStrategyParameterizedTest
  - NumberOfFoulsStrategyParameterizedTest

Tests use:
- JUnit 4 framework
- Mockito for mocking dependencies
- Coroutine test utilities (MainDispatcherRule)

**Build & Test Status**: ✅ All passing

## Evolution

### Initial State (Commit 0b076273)
Basic Activity with inline RecyclerView + hardcoded odds calculation

### Step 1: MVVM & Clean Architecture (Commits up to 0803c81)
- Separated concerns with ViewModel, Repository, Use Cases
- Introduced domain/data/presentation layers

### Step 2: Fragment-based UI (Commit a0f3a6f)
- Migrated from UIController pattern to Fragment composition
- Fragment-based UI with FragmentContainerView

### Current State: Business Logic in Domain Layer
- ✅ **OddsCalculator moved to domain/service/** (from utils/)
- ✅ **UpdateBetsOddsUseCase now owns business logic** (not proxy to repository)
- ✅ **BetRepository interface simplified** (only data operations: fetchBets)
- ✅ **BetRepositoryImpl has single responsibility** (data source abstraction only)
- ✅ **Proper Clean Architecture** - each layer owns its logic:
  - Presentation: UI state management (ViewModel)
  - Domain: Business logic (Use Cases + Services)
  - Data: Data source abstraction (Repository)

## Build & Run

### Prerequisites
- Android SDK 34+
- Kotlin 2.0.21+
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
- **Framework**: Android Gradle Plugin 8.7.3
- **Kotlin Compiler**: 2.0.21
- **Annotation Processing**: KSP 2.0.21-1.0.27
- **Target SDK**: 34
- **Min SDK**: 24

## Dependencies

### Core Android
- androidx.appcompat:appcompat:1.4.1
- androidx.constraintlayout:constraintlayout:2.1.3
- androidx.fragment:fragment-ktx:1.6.1

### Lifecycle & ViewModel
- androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7
- androidx.lifecycle:lifecycle-livedata-ktx:2.8.7
- androidx.activity:activity-ktx:1.7.2

### Dependency Injection
- com.google.dagger:hilt-android:2.51.1
- com.google.devtools.ksp:2.0.21-1.0.27

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

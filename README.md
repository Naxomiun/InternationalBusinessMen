# InternationalBusinessMen
 
## Features

- Clean architecture & SOLID principles
- MVVM
- Repository/Datasource pattern
- Dependency Injection with Hilt
- Multi Module
- Coroutines
- Error handling
- ViewBinding
- State Flows & Channels
- Unit testing of domain layer
- Jetpack Navigatión
- Retrofit

#### The app is divided in three main layers:
 - App (presentation/UI/DI)
 - Data
 - Domain

### Domain
Layer that contains app entities, repository interfaces, managers and Use Cases (and tests). It has no dependencies with other layers.

### Data
Contains all business logic. Layer in charge of fetching data by implementing our domain interfaces and mapping the DTOs. It depends on domain.

### App (Presentation/UI)
Contains UI. This includes ViewModels, DI, fragments, activities and delegates. It depends on domain.

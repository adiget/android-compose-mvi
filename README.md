# GithubApp (Jetpack Compose + MVI)

![Language](https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&logo=kotlin)

GithubApp is a sample Android project developed with MVI architecture with Compose.

## - MVI Architecture

**MVI** stands for **Model-View-Intent** . It works based on the principle of unidirectional and cylindrical flow inspired by the Cycle.js framework.

**Model**: Unlike other patterns, In MVI Model represents the state of the UI. i.e for example UI might have different states like Data Loading, Loaded, Change in UI with user Actions, Errors, User current screen position states. Each state is stored as similar to the object in the model.
**View**: The View in the MVI is our Interfaces which can be implemented in Activities and fragments. It means to have a container which can accept the different model states and display it as a UI. They use observable intents(Note: This doesn't represent the Android traditional Intents) to respond to user actions.
**Intent:** Even though this is not an Intent as termed by Android from before. The result of the user actions is passed as an input value to Intents. In turn, we can say we will be sending models as inputs to the Intents which can load it through Views.

<center><img src="https://miro.medium.com/v2/resize:fit:1400/1*boEPmavtPezPzZaH-Icl0Q.png"></center>

## - Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [coroutines](https://kotlinlang.org/docs/coroutines-guide.html) - A coroutine is a concurrency design pattern that executes asynchronously.
- [Flow](https://kotlinlang.org/docs/flow.html) - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
- [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow and SharedFlow are Flow APIs that enable flows to optimally emit state updates and emit values to multiple consumers.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [Compose](https://developer.android.com/jetpack/compose) - A modern toolkit for building native UI.
    - [Navigation](https://developer.android.com/jetpack/compose/navigation) - A framework to navigate between composables while taking advantage of the Navigation component’s infrastructure and features.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [OkHttp](http://square.github.io/okhttp/) - HTTP client that's efficient by default: HTTP/2 support allows all requests to the same host to share a socket
- [Coil](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines
- [Gson](https://github.com/google/gson) - used to convert Java Objects into their JSON representation and vice versa.
- [Turbine](https://github.com/cashapp/turbine) - A testing library for kotlinx.coroutines Flow.


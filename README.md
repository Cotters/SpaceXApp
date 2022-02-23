# SpaceXApp
An Android app written in Jetpack Compose that displays SpaceX company information and launches.

All data is taken from [this API](https://github.com/r-spacex/SpaceX-API/) and stored locally (to be offline-friendly) before being presented.


## Features
* Show mission patch image, name, date, status, and rocket details.
* Sort launches by date in ascending or descending order.
* Filter launches to show only launches that have failed or succeeeded.
* Filter launches by year.
* Show web links (Wikipedia/press/YouTube) and open when tapped.
* All fetched data is stored locally.


## Testing
* Unit tested (developed using TDD).
* UI tested.
* API integration tested.

## Technologies
* Android Jetpack Compose for UI (with Coil for images) and UI Tests.
* Hilt for dependency injection.
* Retrofit for HTTP requests.
* Kotlin Coroutines and Flow for data movement.
* Room for local persistence.
* Paging for pagination.
* MockK for mocking.
* Hamcrest for assertions.
* Material themes & icons for the UI.

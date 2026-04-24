# Nightlife App 

A nightlife event listing and ticketing app built with **MVVM · Koin · Room · Jetpack Compose**.

---

## Stack

| Layer       | Library                          |
|-------------|----------------------------------|
| UI          | Jetpack Compose + Material 3     |
| Navigation  | Compose Navigation               |
| ViewModel   | AndroidX ViewModel + StateFlow   |
| DI          | Koin 3.5                         |
| Database    | Room 2.6                         |
| Network     | Retrofit 2                       |
| Images      | Coil 2                           |
| Background  | WorkManager                      |

---
## Project Structure

```
app/src/main/java/com/nightlife/
├── data/
│   ├── local/
│   │   ├── dao/          Daos.kt          — EventDao, 
│   │   ├── entity/       Entities.kt      — EventEntity
│   │   └──               NightlifeDatabase.kt
│   ├── network/
│   │   ├── api/          NightlifeApiService.kt
│   │   └── dto/          EventDtos.kt
│   └── repository/
│       ├── EventRepository.kt             —  offline-first sync
├── di/
│   └── AppModules.kt                      — Koin modules
│   ├──           Models.kt        — Event, Booking
├── screen/
│  
├── worker/
│   └── Workers.kt                         — NotificationWorker 
├── NightlifeApp.kt                        — Koin init
└── MainActivity.kt
```

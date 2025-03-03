# Fetch Interview Project

This is an Android application developed for Fetch Rewards Coding Exercise - Software Engineering - Mobile. The application gets data from a given URL and displays it in a readable list.

## Features

- Retrieves data from `https://fetch-hiring.s3.amazonaws.com/hiring.json`.
- Displays the data in an expandable list view, grouped by "listId".
- Sorts the results first by "listId" then by "name".
- Filters out any items where "name" is blank or null.

## Prerequisites

- Android Studio
- Android SDK
  
## How to Run via APK on android device

1. **Copy the APK**: Copy the file `apk\fetch_project.apk` from repository to Android device
2. **Install**: Select the file and install it on android device.
   
## How to Run Via Code

1. **Clone the repository**: git clone https://github.com/clarkx98/fetch_project.git

2. **Open the project**: Open Android Studio and select `File > Open...`. Navigate to the directory where you cloned the repository and click `OK`.

3. **Sync the project with Gradle files**: Android Studio should automatically sync the project with the Gradle files. If it doesn't, you can do this manually by selecting `File > Sync Project with Gradle Files`.

4. **Run the application**: Click on the green play button in the toolbar at the top of Android Studio. Choose either a connected device or a configured emulator to run the application.

# Coinventor
## Usage: 
* **Video Demo:** https://imgur.com/a/bc0S40Y.gif
* **Installation:**
1. **Clone the Coinventor Repository:**
    - Open Android Studio and go to ```File -> New -> Project from Version Control```.
    - In the dialog, select ```Git``` as the version control system and enter [https://github.com/hiepnguyenduc2005/Coinventor](https://github.com/hiepnguyenduc2005/Coinventor) into the URL field.
    - Choose a directory for the project and click ```Clone```.
2. **Set Up an Android Emulator:**
    - After opening the project, navigate to ```Tools -> AVD Manager```.
    - Click on ```Create Virtual Device...,``` pick a device definition (e.g., Pixel 4), and select a system image (e.g., Android 11.0).
    - Complete the emulator setup.
3. **Run the Coinventor App:**
    - Select the **Coinventor** app configuration in the run configurations dropdown at the top.
    - Choose the newly created emulator as your target device.
    - Click the ```Run``` button to build and run the app. The emulator will start, and the **Coinventor** app will launch on it once it's ready.

_Note:_ Make sure you have the latest Android Studio and Android SDK. Running an emulator requires substantial system resources.

## Description
### Introduction
**Coinventor** is the final project of *CodePath* **AND 101 - Intro to Android Development** and an app that allows users to convert from one currency to another with the current rate.

![image](https://github.com/hiepnguyenduc2005/Coinventor/assets/130782979/7b20a0b0-b8a1-4221-90fd-b8cd02a06cde)

### Features
- Users can select two different countries or areas from a menu and convert currencies between them.
- Users can save the results of currency conversions and view them on a RecyclerView page.

### Future development
- Users can create an account to check their past conversions.
- Users can view graphs of how rates change over the period.

# Sample Android AsynchronousSocketChannel Client App
A single activity sample app includes examples of
- Using AsynchronousSocketChannel for socket communication
- Using Jetpack Compose to construct UI
- Using Kotlin Corountines to handle  concurrency

## Prerequisites
- Android Studio Canary builds (https://developer.android.com/studio/preview)
- Android 8.0 or above (AsynchronousSocketChannel only avaiable since API level 26)
- A socket connection tester (e.g. https://sourceforge.net/projects/sockettest/)
- Kotlin plugin must be updated to 203-1.4.30-RC-AS6682.9 or newer
  1. Open the Preference dialog
  1. Go into "Languages & Frameworks" > Kotlin
  1. Select "Early Access Preview 1.4.x" in "Update channel"
  1. Update the plugin and restart Android Studio

## Package usage
- **viewModel**
    - Hold the view state such as button status (i.e. name, enable)
    - Update on UI view will be done through viewModel in Jetpack Compose
- **ui**
    - Reusable composable function for buttons, text fields and text
- **style**
    - Used by composable function for Theme and UI style
- **service**
    - Using AsynchronousSocketChannel for socket connection client
    - Using Kotlin Corountines to communicate with socket server asynchronously
  
## Compose version
- **kotlinCompilerVersion**
    - 1.4.30
- **androidx.compose**
    - 1.0.0-beta01
  
## Testing
- Download **SocketTest** from http://sockettest.sourceforge.net/ to create a socket server for testing
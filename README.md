# GODLESS Launcher — UI Prototype

Native Android starter project using Kotlin and Jetpack Compose.

## Current status

This is a UI prototype, not a functioning Minecraft Java launcher yet.

Implemented:
- Black + Neon Green theme
- Home dashboard and bottom navigation
- Version screen (demo data)
- Mod / shader library screen (demo data)
- Performance settings UI (demo state)
- Settings UI
- Profile selector dialog

Not implemented:
- Minecraft Java runtime and game launching
- Microsoft authentication
- Real version downloads
- Forge / Fabric installation
- Actual mod and shader installation
- Live FPS telemetry
- Persistent profiles and settings
- Custom touch control editor

These require integration with the Amethyst Android source and testing.

## Build

Requirements:
- JDK 17
- Android SDK 35
- Android Gradle Plugin 8.7.3 compatible Gradle installation

Run:

```bash
gradle :app:assembleDebug
```

APK output:

`app/build/outputs/apk/debug/app-debug.apk`

## Important

Keep this UI project separate from the Amethyst fork until the first baseline Amethyst build succeeds. Integrate UI modules incrementally; do not replace Amethyst's launcher, Java runtime, JNI or rendering code with this prototype.

SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_151
SET ANDROID_HOME=C:\Program Files\Android\Android Studio
SET GLA=C:\Program Files\Android\Android Studio\plugins\android\lib\templates\gradle\wrapper
SET PATH=%PATH%;%JAVA_HOME%;%ANDROID_HOME%;%GLA%
SET ZIPALIGN="%ANDROID_HOME%\build-tools\23.0.3\zipalign"

echo %cd%


gradlew assembleDebug 
rem & "%JAVA_HOME%\bin\jarsigner" -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ..\..\MobileKey\boco.jks -storepass 111111 app\app-debug-unsigned.apk 111111& %ZIPALIGN% -v 4 app\app-debug-unsigned.apk app\debug-release.apk
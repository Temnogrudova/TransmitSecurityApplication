# TransmitSecurityApplication
Implemented techniques and principles for “TransmitSecurityApplication”:

1. Applictaion architectural pattern: MVP Language: Java.

2. I used android databinding to get an access to xml elements from java file.

3. Transmit Security SDK is already implemented with AsyncTasks and I shouldn’t change SDK, that’s why I rejected an idea to use Rxjava for asynchronous requests. Instead of that I support a treatment of configuration change, according to these recommendations:
https://android.jlelse.eu/handling-orientation-changes-in-android-7072958c442a

4. Authenticators are not secure information, that’s why I decided to keep it in shared preferences.
5. For implementation I used only  AppCompat and other Jetpack libs, according to requirements: auto added its and extra
- com.android.support:recyclerview-v7:28.0.0
- com.google.code.gson:gson:2.8.5

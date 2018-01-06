# SecureTimer
SecureTimer gives current time which doesn't depend on system time, thus changing system time will not affect SecureTimer's time. It is offline and needs sync to NTP Server once per device reboot and not always you request for time.

## Download
### Gradle:
```sh
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
    
dependencies {
    compile 'com.github.kk121:SecureTimer:v1.0'
}
```
### Maven:
```sh
<dependency>
  <groupId>com.github.kk121</groupId>
  <artifactId>SecureTimer</artifactId>
  <version>v1.0</version>
</dependency>
```
## How to use SecureTimer ?
#### Initialize the SecureTimer in Application class or MainActivity
```sh
SecureTimer.with(getApplicationContext()).initialize();

// Optionally you can pass preferred NTP time servers
SecureTimer.with(getApplicationContext()).initialize("2.in.pool.ntp.org", "0.asia.pool.ntp.org");
```
#### Getting current time
```sh
//time in millis
long currentTimeInMillis = SecureTimer.with(MainActivity.this).getCurrentTimeInMillis();

//date
Date date = SecureTimer.with(MainActivity.this).getCurrentDate();
```

#### Comments/bugs/questions/pull requests are always welcome!
### **Cheers :)**

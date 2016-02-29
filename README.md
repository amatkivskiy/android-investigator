Android Investigator
====================

A simple tool that can be used to quickly add **informative debug logs** to the code during investigation without typing much:  


```java
@Override
public void onResume() {
    Investigator.log(this);
    // code
}
```
log:
```
D/Investigator: [main] MainActivity@27a4868.onResume()
```

Features
----------
Easy or automatic logging of the followings at the place of the call:

* the **thread name**
* the **!! object instance !!** (its `toString()` value)
* the **method name**
* the **stacktrace** (the method depth is configurable)
* **variable values** conveniently
* the **time elapsed** since a start call
* an extra **comment**  

Motivation
----------
Android Investigator is not intended as a production logging solution but as a handy little productivity tool for helping bugfixing and investigation, kept available on the debug classpath (or even commented out in gradle).  
**Logging the object instance** (not just the class) **is the extra** that it does compared to other logging libraries. I found it useful in many situations (e.g.: configuration changes, fragment transactions, checking DI scopes, checking activity launchmodes).  
It is also **simple and convenient to use**: 


More sample usage
--------------------
```
D/Investigator: [main] SampleLogActivity@a21b74.onCreate()												<- Investigator.log(this)

D/Investigator: [main] SampleLogActivity@a21b74.onCreate() | comment								    <- Investigator.log(this, "comment")

D/Investigator: [main] SampleLogActivity@a21b74.onStart() | name = John									<- Investigator.log(this, "name", name)

D/Investigator: [main] SampleLogActivity@a21b74.onStart() | pi = 3.14 | days = [Mon, Tue, Wed]			<- Investigator.log(this, "pi", pi, "days", days);

D/Investigator: [AsyncTask 2] MyAsyncTask@ad03c5e.doInBackground()										<- Investigator.log(this, 3);
                    at gk.android.investigator.sample.MyAsyncTask.doInBackground(MyAsyncTask.java:10)
                    at android.os.AsyncTask$2.call(AsyncTask.java:295)
                    at java.util.concurrent.FutureTask.run(FutureTask.java:237)                    

D/Investigator: [main] SampleLogActivity@a21b74.onPause() | 0 ms (STOPWATCH STARTED)					<- Investigator.startStopWatch(this);
D/Investigator: [main] SampleLogActivity@a21b74.onDestroy() | 344 ms									<- Investigator.log(this);
```
Tag, default stacktrace method depth, thread name, and log level are customizable through the fields of the class. (Check out [the class][TheClass] itself or the [javadoc][JavaDoc].)

When is it useful?
----------------------
It can be most useful when debugging is not effective any more because there are too many checkpoints to step through or there is asynchronicity.
Adding a few simple Investigator log calls to checkpoints can provide a **overview about the order of the events, the object instances in play, variable values, where our code is called from, and on which thread**. (I also found myself using it instead of simple debugging, too.)  

Download
----------
Android Investigator is available in [Maven Central][MavenSearch].  

Get with gradle:
```
dependencies {
    debugCompile 'com.github.lemonboston:android-investigator:0.1.1'
}
```
Or, since it is a single java class, you can grab it from [here][TheClass] and add to your project.  


License
----------

The MIT License (MIT)

Copyright (c) 2016 Gabor Keszthelyi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[TheClass]: /AndroidInvestigatorSample/android-investigator/src/main/java/gk/android/investigator/Investigator.java
[JavaDoc]: http://www.javadoc.io/doc/com.github.lemonboston/android-investigator/
[MavenSearch]: http://search.maven.org/#search%7Cga%7C1%7Clemonboston%20android-investigator
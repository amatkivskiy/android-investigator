Android Investigator
====================

A simple tool that can be used to quickly add **informative debug logs** to the code without typing much:  


```java
@Override
public void onResume() {
    super.onResume();
    Investigator.log(this);
}
```
log:
```console
D/Investigator: [main] MainActivity@27a4868.onResume()
```

It can log
----------

* the **thread name**
* the **object instance** (its `toString()` value)
* the **method name**
* the **stacktrace** at the place of the call (the method depth is configurable)
* **variable values** conveniently
* the **time elapsed** since a start call
* an extra **comment**

What it is useful for?
----------------------
Android Investigator can be most useful when debugging is not effective any more because there are too many checkpoints to step through or there is asynchronicity. (But once it's available on the classpath, I found myself using it for quickly checking object instances or threads in simple situations too.)  
Adding a few simple Investigator log calls to checkpoints **can provide information about the order of the events, the object instances in play, variable values, where our code is called from, and on which thread**.  

*Note:* Android Investigator is not intended as a logging solution for production code. It is for temporary ad hoc logging during investigating something. I usually just keep it on the debug classpath to not include it in the release build.

More sample usage
--------------------
```console
D/Investigator: [main] SampleLogActivity@a21b74.onCreate()												<- Investigator.log(this)
D/Investigator: [main] SampleLogActivity@a21b74.onCreate() | some comment								<- Investigator.log(this, "some comment")
D/Investigator: [main] SampleLogActivity@a21b74.onStart() | name = John									<- Investigator.log(this, "name", name)
D/Investigator: [main] SampleLogActivity@a21b74.onStart() | pi = 3.14 | days = [Mon, Tue, Wed]			<- Investigator.log(this, "pi", pi, "days", days);
D/Investigator: [main] MyAsyncTask@ad03c5e.onPreExecute()
D/Investigator: [AsyncTask 2] MyAsyncTask@ad03c5e.doInBackground()										<- Investigator.methodDepth = 3; Investigator.log(this); Investigator.methodDepth = 0;
                    at gk.android.investigator.sample.MyAsyncTask.doInBackground(MyAsyncTask.java:10)
                    at android.os.AsyncTask$2.call(AsyncTask.java:295)
                    at java.util.concurrent.FutureTask.run(FutureTask.java:237)                    
D/Investigator: [main] MyAsyncTask@ad03c5e.onPostExecute()
D/Investigator: [main] SampleLogActivity@a21b74.onPause() | 0 ms (STOPWATCH STARTED)					<- Investigator.startStopWatch(this);
D/Investigator: [main] MainActivity@b0ae1b2.onResume() | 17 ms											<- Investigator.log(this);
D/Investigator: [main] SampleLogActivity@a21b74.onDestroy() | 344 ms									<- Investigator.log(this); Investigator.stopLoggingTimes();
```

Download
----------
```
dependencies {
    debugCompile 'com.github.lemonboston:android-investigator:0.1.0'
}
```

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
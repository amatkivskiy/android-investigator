__Android Investigator__ can be used to quickly add informative debug logs to the code without typing much:  
```
@Override
public void onResume() {
    super.onResume();
    Investigator.log(this);
}
```
-->
```
D/Investigator: [main] MainActivity@27a4868.onResume()
```

It can log:

* **thread name**
* **object instance** (its `toString()` value)
* **method name**
* **stacktrace** (the method depth is configurable)
* **variable value** conveniently
* **time elapsed** since a start call
* an extra **comment**

### What is it for? ###

Android Investigator is not a logging solution for production code but a productivity tool to help investigate something.  
It can be most useful when debugging is not effective any more because there are too many checkpoints to step through or there is asynchronicity. (But once it's available on the classpath, I also found myself using it for quickly checking object instance or thread in simple situations too.)  
Adding a few simple Investigator log calls to checkpoints **can provide information about the order of the events, the object instances in play, variable values, where our code is called from, and on which thread**.

### More sample usage ###

```
D/Investigator: [main] SampleLogActivity@a21b74.onCreate()														<- Investigator.log(this)
D/Investigator: [main] SampleLogActivity@a21b74.onCreate() | some comment										<- Investigator.log(this, "some comment")
D/Investigator: [main] SampleLogActivity@a21b74.onStart() | name = John											<- Investigator.log(this, "name", name)
D/Investigator: [main] SampleLogActivity@a21b74.onStart() | name = John | pi = 3.14 | days = [Mon, Tue, Wed]	<- Investigator.log(this, "name", name, "pi", pi, "days", days);
D/Investigator: [main] MyAsyncTask@ad03c5e.onPreExecute()
D/Investigator: [AsyncTask 2] MyAsyncTask@ad03c5e.doInBackground()												<- Investigator.methodDepth = 3; Investigator.log(this); Investigator.methodDepth = 0;
                    at gk.android.investigator.sample.MyAsyncTask.doInBackground(MyAsyncTask.java:10)
                    at android.os.AsyncTask$2.call(AsyncTask.java:295)
                    at java.util.concurrent.FutureTask.run(FutureTask.java:237)                    
D/Investigator: [main] MyAsyncTask@ad03c5e.onPostExecute()
D/Investigator: [main] SampleLogActivity@a21b74.onPause() | 0 ms (STOPWATCH STARTED)							<- Investigator.startStopWatch(this);
D/Investigator: [main] MainActivity@b0ae1b2.onResume() | 17 ms													<- Investigator.log(this);
D/Investigator: [main] SampleLogActivity@a21b74.onDestroy() | 344 ms											<- Investigator.log(this); Investigator.stopLoggingTimes();
```
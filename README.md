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
With stacktrace enabled:
```
D/Investigator: [main] MainActivity@27a4868.onResume()
                    at android.app.Instrumentation.callActivityOnResume(Instrumentation.java:1258)
                    at android.app.Activity.performResume(Activity.java:6327)
                    at android.app.ActivityThread.performResumeActivity(ActivityThread.java:3092)
```


Features:

* log **thread name**
* log **object instance** (its `toString()` value)
* log **method name**
* log **stacktrace** (method depth configurable)
* log **variable value** conveniently
* simple **time measurement** - time elapsed message added to logs

### What is it for? ###

Android Investigator is not a logging solution for production code but a productivity tool to help investigate something.  
It can be most useful when debugging is not effective any more because there are too many checkpoints to step through or there is asynchronicity. (But once it's available on the classpath, I also found myself using it for quickly checking object instance or thread in simple situations too.)  
Adding a few simple Investigator log calls to checkpoints **can provide information about the order of the events, the object instances in play, variable values, where our code is called from, and on which thread**.

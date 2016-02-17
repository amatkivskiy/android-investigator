# Android Investigator

This simple tool (1 java [file][Investigator.java]) is to **help add investigation logs into code easier**.

So if during some investigation you get to a point (after some code digging and debugging) where you start adding logs like this
```
@Override
public void onResume() {
    super.onResume();
    Log.d(TAG, "MyFragment onResume");
}
```
at multiple locations to figure out what the heck is really happening and in what order  
(maybe when dealing with things like fragment lifecycles, screen rotation, background networking, event bus events, ...)

then *Investigator* can make it less painful, **save you some typing**, and **provide some extra information**:

### Log instance and method
Just pasting **`Investigator.log(this)`** to each checkpoint will usually do:
```
@Override
protected void onResume() {
     super.onResume();
     Investigator.log(this);
}
```
It will log the method name and the calling instance:
```
D/investigation﹕ MyFragment{83884e5 #0 id=0x7f080000}.onResume()
```

#### Log the caller of the method (stacktrace)
To also **print where the watched method is called from**, set `Investigator.NO_OF_EXTRA_METHOD_DEPTH_LOGGED` to 1  
(or to any number to show bigger portion of the stacktrace):
```
D/investigation﹕ UserStore@3f1bdd2d.storeUser()
    MainActivity.storeUsername()
```
#### Log variable values
..conveniently with the varargs parameter: ` Investigator.log(this, "name", name, "age", age)`
```
D/investigation﹕ UserStore@3f1bdd2d.storeUser() | name = Jack | age = 32
```
#### Log times elapsed since checkpoint
Calling `Investigator.startStopWatch(this)` will start a stopwatch and any subsequent `Investigator.log(this)` call will show the time elapsed since:
```
D/investigation﹕ App@2ade266f.onCreate() | 0 ms (STOPWATCH STARTED)
D/investigation﹕ MainActivity@3c56a005.onResume() | 55 ms
```
(This is not for profiling of course, just a nice extra to be able to see the time elapsed between events if it is useful.)

You can also configure some behaviours with the [class][Investigator.java]'s settings constants (log thread or not, tag name, log level, toString formatting options).

* * *
#### How is it implemented?
By simply getting a stacktrace during execution and using the instance's toString(), something like this:
```
void log(Object instance) {
    StackTraceElement[] stackTrace = new Exception().getStackTrace();
    String methodName = stackTrace[3].getMethodName();
    Log.d(TAG, instance.toString() + methodName);
}
```

* * *
#### How to use in project
- One way is to simply copy the [Investigator.java] file into the project temporarily when it's needed.

- Or it can be committed to the project, possibly under the *debug* classpath (`src\debug\java\` instead of `src\main\java\`) to be safe from leaving these logs behind mistakenly in *release* build.

- Or it could be put into a library module, but I think that may be a bit overkill. (But let me know if you would prefer that.)

* * *
### Using with Android Studio breakpoints (to avoid modifying the code)
IntelliJ have this feature for breakpoints to *Log evaluated expression*, so it is possible to use them to **add these investigation logs without touching our code**:

[Breakpoint setup screenshot][1]  
(Tip: add new logging breakpoints with keyboard: Ctrl + F8, Ctrl + Shift + F8 twice, Alt + S, Alt + E, down arrow, select)

Note: *Log message to console* can of course be used without *Investigator*. Could be worth checking if that's enough for you.  
(It logs class and method name at the breakpoint to separate console window.)

* * *
#### Alternatives?
If there was any good tool available out there already for this kind of tracking, or if you have better or other methods, please do let me know! :)

There are some default *live templates in Android Studio* for logging code generation: *logd*, *loge*, *logi*, *logm* (Log method name and argument), *logr* (Log result of method), *logt* (Create TAG field).

Haven't checked but seems good:
https://github.com/orhanobut/logger

From Jake Wharton:
https://github.com/JakeWharton/timber
https://github.com/JakeWharton/hugo

* * *
#### Q&A
*Any performace hit for creating stacktraces?*  
I haven't made any measurements, but my assumption is that, although this is an invasive tracking, it would very rarely distort the normal program flow to an extent that it would mislead the investigation. And that's enough for me :)
But there is some written [here][2] and [here][3] about getting the stack trace and performance.

*1 java file with multiple classes, '..Imp' for static util class, ...?*  
For now, the intention is to keep the 'tool' in 1 file so it can be easily copied into any project ad hoc, and use static methods to avoid allocations. But if later it seems reasonable, it can of course be changed (split it, use instances and proper OO structure, put into lib, etc.).

* * *
#### Tip
I usually copy the collected logs into Notepad++ for undisturbed inspection and its feature to highligh the occurences of the selected word is useful to see the entries coming from the same instance by clicking its id (by default the hashcode in the toString). [Screenshot][4]

Note: There is also a feature idea from Balazs Csernai to replace these hashcodes in the toString values with more human friendly names (like 'Charlie', 'Vilma', etc).

* * *
###### Any feedback or contribution is welcome!

[Investigator.java]: AndroidInvestigatorSample/app/src/debug/java/gk/android/investigator/Investigator.java
[1]: DocumentationAssets/AndroidStudio_breakpoint_screenshot.png
[2]: http://stackoverflow.com/q/421280/4247460
[3]: http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6375302
[4]: DocumentationAssets/notepad_screenshot.png
package gk.android.investigator.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import gk.android.investigator.Investigator;

/**
 * @author Gabor_Keszthelyi
 */
public class MainActivity extends AppCompatActivity {

    static {
        // Changing defaults can be done from anywhere.
        Investigator.threadNameEnabled = true;
        Investigator.defaultMethodDepth = 0;
        Investigator.tag = "Investigator";
        Investigator.logLevel = Log.DEBUG;
        Investigator.removePackageName = true;
        Investigator.highlightAnonymousClasses = true;
        // ...
    }

    private ViewGroup holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        holder = (ViewGroup) findViewById(R.id.holder);

        addButton("Simple usage", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleUsage();
            }
        });

        addButton("With variable", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logVariable();
            }
        });

        addButton("With comment", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logWithComment();
            }
        });

        addButton("Different instances", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                differentInstances();
            }
        });

        addButton("Different threads", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                differentThreads();
            }
        });

        addButton("Stack trace - 1 method depth", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackTrace(1);
            }
        });

        addButton("Stack trace - 20 method depth", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackTrace(20);
            }
        });

        addButton("Anonymous class", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anonymousClass();
            }
        });

        addButton("Stopwatch", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopWatch();
            }
        });

        addButton("Overridden toString()", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overriddenToString();
            }
        });

        addButton("__Go to sample log screen__", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSampleLogScreen();
            }
        });
    }

    private void addButton(String text, View.OnClickListener onClickListener) {
        Button button = new Button(this);
        button.setText(text);
        button.setOnClickListener(onClickListener);
        holder.addView(button);
    }

    private void simpleUsage() {
        Investigator.log(this);
    }

    private void logVariable() {
        String name = "John";
        BigDecimal pi = new BigDecimal("3.14");
        List<String> days = Arrays.asList("Mon", "Tue", "Wed");

        Investigator.log(this, "name", name);
        Investigator.log(this, "name", name, "pi", pi, "days", days);
    }

    private void logWithComment() {
        Investigator.log(this, "this is a comment");
    }

    private void differentInstances() {
        new MyRunnable().run();
        new MyRunnable().run();
    }

    private void differentThreads() {
        MyRunnable runnable = new MyRunnable();
        runnable.run();
        new Thread(runnable, "ThreadName-1").start();
        new Thread(runnable, "ThreadName-2").start();
        new MyAsyncTask().execute();
    }

    private void stackTrace(int methodDepth) {
        Investigator.log(this, methodDepth);
    }

    private void anonymousClass() {
        new Runnable() {
            @Override
            public void run() {
                Investigator.log(this);
            }
        }.run();
    }

    private void stopWatch() {
        Investigator.startStopWatch(this);
        new Thread(new LongerRunningTask()).start();
    }

    private void overriddenToString() {
        ClassWithToString john = new ClassWithToString("John", 55);
        john.doSomething();
    }

    private void goToSampleLogScreen() {
        startActivity(new Intent(this, SampleLogActivity.class));
    }

}

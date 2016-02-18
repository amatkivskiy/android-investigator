package gk.android.investigator.sample;

import gk.android.investigator.Investigator;

/**
 * @author Gabor_Keszthelyi
 */
public class ClassWithToString {

    private final String name;
    private final Integer age;

    public ClassWithToString(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void doSomething() {
        Investigator.log(this);
    }

    @Override
    public String toString() {
        return "ClassWithToString{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

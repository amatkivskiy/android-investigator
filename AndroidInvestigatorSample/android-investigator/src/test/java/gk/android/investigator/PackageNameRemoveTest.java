package gk.android.investigator;

import org.junit.Test;

import static gk.android.investigator.Investigator.removePackageName;
import static org.junit.Assert.assertEquals;

/**
 * @author Gabor_Keszthelyi
 */
public class PackageNameRemoveTest {

    @Test
    public void testRemovePackageName() {
        // Normal case - should return class simple name
        assertEquals("Date", removePackageName("java.util.Date"));

        // Usual overridden toString() - should not change
        assertNotChange("ClassWithToString{name='" + "john" + "', age=" + "55}");

        // toString() with no dot - should not change
        assertNotChange("Date");

        // toString() with dot at the end - should not change (let's say)
        assertNotChange("Date.");

        // toString() with many subsequent dots - should use last word (let's say)
        assertEquals("Date", removePackageName("java.util...Date"));

        // toString() with starting dot - should remove dot (let's say)
        assertEquals("Date", removePackageName(".Date"));

        // toString() with special characters - should not affect, just the last dot
        assertEquals("$~!@!L:?>", removePackageName("}|()._^&*^&@.$~!@!L:?>"));
    }

    private void assertNotChange(String name) {
        assertEquals(name, removePackageName(name));
    }
}

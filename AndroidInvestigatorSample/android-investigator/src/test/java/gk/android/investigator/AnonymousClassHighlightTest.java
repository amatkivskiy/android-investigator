package gk.android.investigator;

import org.junit.Before;
import org.junit.Test;

import static gk.android.investigator.Investigator.anonymousClassHighlightWord;
import static gk.android.investigator.Investigator.checkAndHighlightAnonymousClass;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for the anonymous class highlight.
 *
 * @author Gabor_Keszthelyi
 */
public class AnonymousClassHighlightTest {

    @Before
    public void setup() {
        Investigator.highlightAnonymousClasses = true;
    }

    @Test
    public void testCheckAndHighlightAnonymousClass_highlightDisabled_shouldNotChange() {
        Investigator.highlightAnonymousClasses = false;
        assertNotChange("MainActivity@d0d41fa");
        assertNotChange("MainActivity$2@a898d8f");
        assertNotChange("MainActivity$MyRunnable@14d081c");
        assertNotChange("SomeStrangeToStringValue$");
    }

    @Test
    public void testCheckAndHighlightAnonymousClass_noSymbol_shouldNotChange() {
        assertNotChange("MainActivity@d0d41fa");
    }

    @Test
    public void testCheckAndHighlightAnonymousClass_anonymousClass_shouldInsertHighlightWord() {
        assertEquals("MainActivity" + anonymousClassHighlightWord + "2", checkAndHighlightAnonymousClass("MainActivity$2"));
        assertEquals("MainActivity" + anonymousClassHighlightWord + "2@a898d8f", checkAndHighlightAnonymousClass("MainActivity$2@a898d8f"));
        assertEquals("MainActivity" + anonymousClassHighlightWord + "2234@a898d8f", checkAndHighlightAnonymousClass("MainActivity$2234@a898d8f"));
        assertEquals("gk.android.investigator.sample.MainActivity" + anonymousClassHighlightWord + "8", checkAndHighlightAnonymousClass("gk.android.investigator.sample.MainActivity$8"));
    }

    @Test
    public void testCheckAndHighlightAnonymousClass_innerClass_shouldNotChange() {
        assertNotChange("MainActivity$MyRunnable@14d081c");
    }

    @Test
    public void testCheckAndHighlightAnonymousClass_edgeCaseWithSymbolAsLastChar_shouldNotChange() {
        assertNotChange("SomeStrangeToStringValue$");
    }

    private void assertNotChange(String instanceName) {
        assertEquals(instanceName, checkAndHighlightAnonymousClass(instanceName));
    }

    // Note: Lambda Investigator log looks like this:
    // DataAccessControllerImpl@a844969.lambda$getForecastForWarmerCity$29()
}
package ua.nure.kn.teteruk.usermanagment.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import org.junit.After;
import org.junit.Before;

import static junit.extensions.jfcunit.TestHelper.cleanUp;
import static org.junit.Assert.*;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;

    @Before
    public void setUp() {
        setHelper(new JFCTestHelper());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    @After
    public void tearDown() {
        mainFrame.setVisible(false);
        cleanUp(this);
    }
}
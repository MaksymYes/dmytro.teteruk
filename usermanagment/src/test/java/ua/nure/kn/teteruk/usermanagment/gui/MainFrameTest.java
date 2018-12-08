package ua.nure.kn.teteruk.usermanagment.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

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

    @Test
    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        find(JPanel.class, "userTable");
        find(JPanel.class, "addButton");
        find(JPanel.class, "editButton");
        find(JPanel.class, "deleteButton");
        find(JPanel.class, "detailsButton");
    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component \'" + name +"\'", component);
        return component;
    }
}
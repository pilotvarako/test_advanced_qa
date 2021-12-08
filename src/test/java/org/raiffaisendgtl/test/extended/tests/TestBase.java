package org.raiffaisendgtl.test.extended.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.raiffaisendgtl.test.extended.app.Application;

public class TestBase {

    public Application app;

    @BeforeEach
    public void setUp() {
        app = new Application();
    }

    @AfterEach
    public void tearDown() {
        app.quit();
    }

}

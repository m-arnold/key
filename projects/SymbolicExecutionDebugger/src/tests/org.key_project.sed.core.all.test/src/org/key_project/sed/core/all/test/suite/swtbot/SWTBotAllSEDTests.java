package org.key_project.sed.core.all.test.suite.swtbot;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.key_project.key4eclipse.starter.core.test.suite.swtbot.SWTBotAllStarterCoreTests;
import org.key_project.key4eclipse.test.suite.swtbot.SWTBotAllKeY4EclipseTests;
import org.key_project.sed.core.test.suite.swtbot.SWTBotAllSEDCoreTests;
import org.key_project.sed.key.core.test.suite.swtbot.SWTBotAllSEDKeYTests;
import org.key_project.util.test.suite.swtbot.SWTBotAllUtilTests;

/**
 * <p>
 * Run all contained JUnit 4 test cases that requires SWT Bot.
 * </p>
 * <p>
 * Requires the following JVM settings: -Xms128m -Xmx1024m -XX:MaxPermSize=256m
 * </p>
 * <p>
 * If you got timeout exceptions increase the waiting time with the following
 * additional JVM parameter: -Dorg.eclipse.swtbot.search.timeout=10000
 * </p>
 * @author Martin Hentschel
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
   SWTBotAllKeY4EclipseTests.class,
   SWTBotAllUtilTests.class,
   SWTBotAllStarterCoreTests.class,
   SWTBotAllSEDCoreTests.class,
   SWTBotAllSEDKeYTests.class
})
public class SWTBotAllSEDTests {
}
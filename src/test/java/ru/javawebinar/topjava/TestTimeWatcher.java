package ru.javawebinar.topjava;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestTimeWatcher extends TestWatcher {
    private static final Logger log = LoggerFactory.getLogger(TestTimeWatcher.class);

    private static Map<String, Long> startTimes = new ConcurrentHashMap<>();
    private static Map<String, Long> execTimes = new ConcurrentHashMap<>();

    @Override
    protected void starting(Description description) {
        startTimes.put(description.getMethodName(), System.currentTimeMillis());
    }

    protected void finished(Description description) {
        long delta = System.currentTimeMillis() - startTimes.get(description.getMethodName());
        execTimes.put(description.getMethodName(), delta);
        log.info("Test {} executed {} ms", description.getMethodName(), delta);
    }

    public static void summary() {
        log.info("Test execution summary:");
        execTimes.forEach((testName, delta) -> {
            log.info("Test {} executed {} ms", testName, delta);
        });
    }
}

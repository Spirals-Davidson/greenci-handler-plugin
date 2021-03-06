package com.powerapi.listener;

import org.junit.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class GreenciTestRunner extends BlockJUnit4ClassRunner {

    private GreenciTestListener instanceMyTestListener;

    public GreenciTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Object createTest() throws Exception {
        Object test = super.createTest();
        // Note that JUnit4 will call this createTest() multiple times for each
        // test method, so we need to ensure to call "beforeClassSetup" only once.
        if (test instanceof GreenciTestListener && instanceMyTestListener == null) {
            instanceMyTestListener = (GreenciTestListener) test;
            instanceMyTestListener.beforeClassSetup();
        }
        return test;
    }

    @Override
    public void run (RunNotifier notifier){
        super.run(notifier);
        notifier.addListener(new GreenciTestListener());

        EachTestNotifier testNotifier = new EachTestNotifier(notifier,
                getDescription());
        try {
            notifier.fireTestRunStarted(getDescription());
            Statement statement = classBlock(notifier);
            statement.evaluate();
        } catch (AssumptionViolatedException e) {
            testNotifier.fireTestIgnored();
        } catch (StoppedByUserException e) {
            throw e;
        } catch (Throwable e) {
            testNotifier.addFailure(e);
        }
    }
}
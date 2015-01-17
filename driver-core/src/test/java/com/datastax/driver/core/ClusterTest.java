package com.datastax.driver.core;

import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

import static com.datastax.driver.core.Assertions.fail;

public class ClusterTest {
    @Test(groups = "unit")
    public void public_methods_should_fail_once_closeInternal_was_called() {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        cluster.closeInternal();

        try {
            cluster.connect();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        try {
            cluster.connect("foo");
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        try {
            cluster.getMetadata();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        try {
            cluster.getMetrics();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        try {
            cluster.init();
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        try {
            cluster.register(mock(Host.StateListener.class));
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        try {
            cluster.unregister(mock(Host.StateListener.class));
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        try {
            cluster.register(mock(LatencyTracker.class));
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
        try {
            cluster.unregister(mock(LatencyTracker.class));
            fail("Expected an IllegalStateException");
        } catch (IllegalStateException e) {
        }
    }
}

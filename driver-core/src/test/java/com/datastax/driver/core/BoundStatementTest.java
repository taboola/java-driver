package com.datastax.driver.core;

import java.util.Collection;

import com.google.common.collect.Lists;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.datastax.driver.core.exceptions.InvalidTypeException;

public class BoundStatementTest extends CCMBridge.PerClassSingleNodeCluster {

    PreparedStatement prepared;

    @Override
    protected Collection<String> getTableDefinitions() {
        return Lists.newArrayList("CREATE TABLE foo (k int primary key, v text)");
    }

    @BeforeClass(groups = "short")
    public void setup() {
        prepared = session.prepare("INSERT INTO foo (k, v) VALUES (?, ?)");
    }

    @Test(groups = "short")
    public void should_get_single_value() {
        // This test is not exhaustive, note that the method is also covered in DataTypeIntegrationTest.
        BoundStatement statement = prepared.bind(1, "test");

        assertThat(statement.getInt(0))
            .isEqualTo(statement.getInt("k"))
            .isEqualTo(1);

        assertThat(statement.getString(1))
            .isEqualTo(statement.getString("v"))
            .isEqualTo("test");

        try {
            statement.getString(0);
            fail("Expected type error");
        } catch (InvalidTypeException e) { /* expected */ }

        try {
            statement.getString(2);
            fail("Expected index error");
        } catch (IndexOutOfBoundsException e) { /* expected */ }
    }

    @Test(groups = "short")
    public void should_get_all_values() {
        BoundStatement statement = prepared.bind(1, "test");
        assertThat(statement.getValues()).containsExactly(1, "test");
    }

    @Test(groups = "short")
    public void should_get_all_values_when_not_all_set() {
        BoundStatement statement = prepared.bind();
        assertThat(statement.getValues()).containsExactly(null, null);

        statement = prepared.bind();
        statement.setInt(0, 1);
        assertThat(statement.getValues()).containsExactly(1, null);

        statement = prepared.bind();
        statement.setString(1, "test");
        assertThat(statement.getValues()).containsExactly(null, "test");
    }
}
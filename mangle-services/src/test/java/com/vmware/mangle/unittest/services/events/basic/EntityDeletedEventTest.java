/*
 * Copyright (c) 2016-2019 VMware, Inc. All Rights Reserved.
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with separate copyright notices
 * and license terms. Your use of these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 */

package com.vmware.mangle.unittest.services.events.basic;

import static org.springframework.test.util.ReflectionTestUtils.getField;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vmware.mangle.cassandra.model.endpoint.EndpointSpec;
import com.vmware.mangle.services.cassandra.model.events.basic.EntityDeletedEvent;
import com.vmware.mangle.services.cassandra.model.events.basic.Event;
import com.vmware.mangle.services.mockdata.EndpointMockData;

/**
 * Unit tests for the {@link Event} entity, basically just verifying that Lombok is working.
 *
 * @author hkilari
 * @since 1.0
 */
public class EntityDeletedEventTest {
    EndpointMockData endpointMockData;
    EndpointSpec endpointspec;
    private String eventMesage;

    @BeforeClass
    public void init() {
        endpointMockData = new EndpointMockData();
        endpointspec = endpointMockData.k8sEndpointMockData();
        endpointspec.setName("12345");
        eventMesage =
                "Deleted entity: " + endpointspec.getClass().getName() + " With Id: " + endpointspec.getPrimaryKey();
    }

    @Test
    public void lombokShouldSetCorrectly() {
        EntityDeletedEvent event =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());

        Assert.assertEquals(getField(event, "name"), "EntityDeletedEvent");
        Assert.assertEquals(getField(event, "message"), eventMesage);
        Assert.assertEquals(getField(event, "entityId"), endpointspec.getPrimaryKey());
    }

    @Test
    public void lombokShouldGetCorrectly() {
        EntityDeletedEvent event =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());

        Assert.assertEquals(event.getName(), "EntityDeletedEvent");
        Assert.assertEquals(event.getMessage(), eventMesage);
        Assert.assertEquals(event.getEntityId(), endpointspec.getPrimaryKey());
    }

    @Test
    public void eventsShouldBeEqualWithDifferentIdsButTheSameName() {
        EntityDeletedEvent event1 =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());
        EntityDeletedEvent event2 =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());

        event1.setId("1");
        event2.setId("2");

        Assert.assertEquals(event1, event1);
        Assert.assertEquals(event2, event2);
        Assert.assertEquals(event1, event2);
    }

    @Test
    public void eventsShouldNotBeEqualWithTheSameIdButDifferentNames() {
        EntityDeletedEvent event3 =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());
        EntityDeletedEvent event4 =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());
        event4.setName("EntityDeletedEventModified");

        Assert.assertEquals(event3, event3);
        Assert.assertEquals(event4, event4);
        Assert.assertNotEquals(event3, event4);
    }

    @Test
    public void eventsShouldHaveSameHashCodeWithDifferentIdsButTheSameName() {
        EntityDeletedEvent event1 =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());
        EntityDeletedEvent event2 =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());
        event1.setId("1");
        event2.setId("2");
        Assert.assertEquals(event1.hashCode(), event1.hashCode());
        Assert.assertEquals(event2.hashCode(), event2.hashCode());
    }

    @Test
    public void eventsShouldNotHaveSameHashCodeWithTheSameIdButDifferentNames() {
        EntityDeletedEvent event3 =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());
        EntityDeletedEvent event4 =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());
        event4.setName("EntityDeletedEventModified");

        Assert.assertEquals(event3.hashCode(), event3.hashCode());
        Assert.assertEquals(event4.hashCode(), event4.hashCode());
        Assert.assertNotEquals(event3.hashCode(), event4.hashCode());
    }

    @Test
    public void testEntityDeletedEventResolvableType() {
        EntityDeletedEvent event =
                new EntityDeletedEvent(endpointspec.getPrimaryKey(), endpointspec.getClass().getName());
        event.getResolvableType();
        Assert.assertEquals(event.getResolvableType().toString(), EntityDeletedEvent.class.getName());
    }

}

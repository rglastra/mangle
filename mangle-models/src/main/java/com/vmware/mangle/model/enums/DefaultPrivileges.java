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

package com.vmware.mangle.model.enums;

/**
 *
 *
 * @author chetanc
 */
public enum DefaultPrivileges {
    ADMIN_READ_WRITE("ADMIN_READ_WRITE"),
    ADMIN_READ("ADMIN_READ"),
    USER_READ_WRITE("USER_READ_WRITE"),
    READONLY("READONLY");

    private String value;

    private DefaultPrivileges(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

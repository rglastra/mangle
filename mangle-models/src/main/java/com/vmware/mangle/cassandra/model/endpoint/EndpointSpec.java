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

package com.vmware.mangle.cassandra.model.endpoint;

import java.io.Serializable;
import java.util.Map;
import javax.validation.constraints.NotEmpty;

import com.datastax.driver.core.DataType.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.vmware.mangle.cassandra.model.MangleDto;
import com.vmware.mangle.model.enums.EndpointType;

/**
 * Model class for Endpoint
 *
 * @author kumargautam
 */
@Table(value = "EndPointSpec")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = { "primaryKey" })
public class EndpointSpec extends MangleDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Indexed
    private String id;
    @ApiModelProperty(position = 0, value = "Name of Endpoint which will be used in the fault apis")
    @PrimaryKeyColumn(value = "name", ordering = Ordering.ASCENDING, type = PrimaryKeyType.PARTITIONED)
    @NotEmpty
    private String name;
    @ApiModelProperty(position = 1, value = "EndpointType is an enum. please use appropriate value")
    @Column
    @CassandraType(type = Name.VARCHAR)
    @Indexed
    private EndpointType endPointType;

    @ApiModelProperty(position = 2, value = "Name of credentials which is created using api /endpoints/credentials/<endpointType>. Can be ignored if credentials are not required")
    @JsonProperty(required = false)
    @Indexed
    private String credentialsName;

    @JsonProperty(required = false)
    @ApiModelProperty(position = 3)
    @Column
    @CassandraType(type = Name.UDT, userTypeName = "awsConnectionProperties")
    private AWSConnectionProperties awsConnectionProperties;

    @JsonProperty(required = false)
    @ApiModelProperty(position = 4)
    @CassandraType(type = Name.UDT, userTypeName = "dockerConnectionProperties")
    private DockerConnectionProperties dockerConnectionProperties;

    @JsonProperty(required = false)
    @ApiModelProperty(position = 5)
    @CassandraType(type = Name.UDT, userTypeName = "remoteMachineConnectionProperties")
    private RemoteMachineConnectionProperties remoteMachineConnectionProperties;

    @JsonProperty(required = false)
    @ApiModelProperty(position = 6)
    @CassandraType(type = Name.UDT, userTypeName = "k8sConnectionProperties")
    private K8SConnectionProperties k8sConnectionProperties;


    @ApiModelProperty(position = 7)
    @CassandraType(type = Name.UDT, userTypeName = "vCenterConnectionProperties")
    private VCenterConnectionProperties vCenterConnectionProperties;

    @JsonProperty(required = false)
    @ApiModelProperty(position = 9, value = "Tags to be associated while sending the metrics, events to Monitoring system. Format: key : value. Example Value is: \"environment\" : \"production\"")
    private Map<String, String> tags;

    public EndpointSpec() {
        this.id = super.generateId();
    }

    @Override
    @JsonIgnore
    public String getPrimaryKey() {
        return name;
    }
}

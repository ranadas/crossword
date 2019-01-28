package com.rdas.justice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class Metadata {
    private String executionTime;
    private Resultset resultset;
    private ResponseInfo responseInfo;
}

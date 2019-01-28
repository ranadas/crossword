package com.rdas.justice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class ResponseInfo {
    private String developerMessage;
    private String status;
}

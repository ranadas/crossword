package com.rdas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter @Setter
@ToString
public class GHUser {
    private String login;
    private String id;
    private String html_url;
}

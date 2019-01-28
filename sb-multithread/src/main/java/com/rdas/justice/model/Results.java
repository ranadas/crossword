package com.rdas.justice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class Results {
    private String date;

    private String[] image;

    private String[] attachments;

    private String vuuid;

    private String created;

    private String body;

    private String title;

    private String uuid;

    private String url;

    private Component[] component;

    private String[] topic;

    private String changed;

    private String[] teaser;
}

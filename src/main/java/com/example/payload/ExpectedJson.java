package com.example.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpectedJson {
	private int id;
	@JsonProperty("interface")
	private InterfaceClass Interface;
	private int priority;
	private boolean active;
	private String from;
	private String to;
	private int expected;
	private String relation;
	
}

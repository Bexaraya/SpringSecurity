package com.example.demo.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecutionStatus {
	private String code;
	private String description;
	private User user;
}

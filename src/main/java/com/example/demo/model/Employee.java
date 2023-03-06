package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3195606126545823749L;
	private String id;
    private String name;
    private String email;
    private String address;
}

package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//為了放在list中,所以要包裝起來
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BMI {
	private Double height;
	private Double weight;
	private Double bmi;

}
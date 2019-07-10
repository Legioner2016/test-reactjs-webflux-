package com.intech.test.react.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * % выполнения
 * 
 * @author a.palkin
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressStatus {

	private boolean done = false;
	private int percentage = 0;
	
}

package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void positveTestCase() {
		// I have hard coded the input value below
		// Since it is a unit test case
		Integer inputValue = 10;
		Integer[] in = result(inputValue);
		System.out.println(
				"Is the result value between the range of 0 and input value :" + checkTheRange(in, 0, inputValue));
	}

	@Test
	public void negativeTestCase() {
		try {
			Integer inputValue = -1;
			if (inputValue < 0) {
				throw new IllegalArgumentException("Only Positive Numbers & no Letters Please!");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public Boolean checkTheRange(Integer[] arrA, int x, int y) {
		for (Integer ls : arrA) {
			if (ls > x && ls < y) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	private Integer[] result(Integer inputValue) {
		int i = 0;
		int num = 0;
		List<Integer> numberList = new ArrayList<Integer>();
		for (i = 1; i <= inputValue; i++) {
			int counter = 0;
			for (num = i; num >= 1; num--) {
				if (i % num == 0) {
					counter = counter + 1;
				}
			}
			if (counter == 2) {

				numberList.add(i);
			}
		}
		return numberList.toArray(new Integer[numberList.size()]);
	}

}

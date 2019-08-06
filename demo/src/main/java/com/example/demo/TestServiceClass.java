package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Path("/rest")
public class TestServiceClass {

	@Autowired
	private TestImpl testImpl;

	//http://localhost:8080/rest/getDetails/trp
	@GET
	@Path("/getDetails/{test}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDetails(@PathParam("test") String test) {
		try {
			testImpl.testImplementation(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return test;
	}

	//http://localhost:8080/rest/getDetail/10
	@GET
	@Path("/getDetail/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Integer[] getDetail(@PathParam("value") int inputValue) {

		try {
			if (inputValue < 0) {
				throw new IllegalArgumentException("Only Positive Numbers & no Letters Please!");

			} else {
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
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		return null;

	}
}

package com.terok.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.terok.demo.controllers.HomeController;

public class HomeControllerTest {

	@Test
	public void testHomeController() {
		HomeController homeController = new HomeController();
		String result = homeController.home();
		assertEquals(result, "HOME");
	}
}

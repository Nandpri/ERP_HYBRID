package driverFactory;

import java.io.IOException;

import org.testng.annotations.Test;

public class AppTest {

	@Test
	public void kickstart() throws IOException, Throwable
	{
		DriverScript ds = new DriverScript();
		ds.startTest();
	}
}

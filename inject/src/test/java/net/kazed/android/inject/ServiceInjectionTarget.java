package net.kazed.android.inject;

public class ServiceInjectionTarget {

	private TestService service;
	
	@Resource("testService")
	public void setTestService(TestService service) {
		this.service = service;
	}

	public TestService getTestService() {
		return service;
	}
}

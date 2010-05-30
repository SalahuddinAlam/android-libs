package net.kazed.android.inject;

@Component("testService")
public class ExampleServiceImpl implements TestService {
	
	private ExampleDao dao;
	
	@Resource("testDao")
	public void setTestDao(ExampleDao dao) {
		this.dao = dao;
	}

	public ExampleDao getTestDao() {
		return dao;
	}

}

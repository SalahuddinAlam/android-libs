package net.kazed.android.inject;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit test for Injector.
 * @author Koert Zeilstra
 */
@RunWith(JMock.class)
public class InjectorTest {

    private Mockery mockery;
	private ApplicationContext applicationContext;
	
    /**
     * Test setup.
     */
    @Before
    public void setUp() {
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
        
        applicationContext = mockery.mock(ApplicationContext.class, "applicationContext");
    }
    
    /**
     * Test @Autowire annotation
     */
	@Test
	public void testInjectAutowire() {
		Injector injector = new Injector(applicationContext);
		AutowireInjectionTarget target = new AutowireInjectionTarget();
		final ExampleDao testDao = new ExampleDaoImpl();
        mockery.checking(new Expectations() { {
            oneOf(applicationContext).containsType(ExampleDao.class); will(returnValue(true));
            oneOf(applicationContext).getBean(ExampleDao.class); will(returnValue(testDao));
        } });
		
		injector.inject(target);
	}

    /**
     * Test @Resource annotation
     */
	@Test
	public void testInjectResource() {
		Injector injector = new Injector(applicationContext);
		ResourceInjectionTarget target = new ResourceInjectionTarget();
		final ExampleDao testDao = new ExampleDaoImpl();
        mockery.checking(new Expectations() { {
            oneOf(applicationContext).getBean("testDao"); will(returnValue(testDao));
        } });
		
		injector.inject(target);
	}

    /**
     * Test @Component annotation
     */
	@Test
	public void testInjectComponent() {
		Injector injector = new Injector(applicationContext);
		ServiceInjectionTarget target = new ServiceInjectionTarget();
		final ExampleServiceImpl testService = new ExampleServiceImpl();
		final ExampleDao testDao = new ExampleDaoImpl();
        mockery.checking(new Expectations() { {
            oneOf(applicationContext).getBean("testService"); will(returnValue(testService));
            oneOf(applicationContext).getBean("testDao"); will(returnValue(testDao));
        } });
		
		injector.inject(target);
		Assert.assertSame(testService, target.getTestService());
		Assert.assertSame(testDao, testService.getTestDao());
	}

}

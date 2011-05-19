package net.kazed.android.inject;

public class ApplicationSingleton {

   private static final ApplicationSingleton INSTANCE = new ApplicationSingleton();
   
   private ApplicationContext applicationContext;
   private Injector injector;

   private ApplicationSingleton() {
   }
   
   public synchronized ApplicationContext getApplicationContext() {
      if (applicationContext == null) {
         applicationContext = new DirectApplicationContext();
      }
      return applicationContext;
    }
    
   public synchronized Injector getInjector() {
      if (injector == null) {
         ApplicationContext applicationContext = getApplicationContext();
         injector = new Injector(applicationContext);
      }
      return injector;
    }
   
   public static ApplicationSingleton getInstance() {
     return INSTANCE;
   }
   
}

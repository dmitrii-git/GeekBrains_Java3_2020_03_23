import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    int priority() default 0;

    @interface AfterSuite{

    }

    @interface BeforeSuite{

    }
}

class MainTest {
    public void start(Class testClass) {
        @Test.BeforeSuite
        public void before(){

        }
        @Test(priority = 1){

        }

        @Test.AfterSuite
        public void after(){

        }

    }

}


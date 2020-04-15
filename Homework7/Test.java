import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    int priority() default 0;
    Class<? extends Throwable> expected() default org.junit.Test.None.class;

    long timeout() default 0L;

    public static class None extends Throwable {
        private static final long serialVersionUID = 1L;

        private None() {
        }
    }


    @interface AfterSuite{

    }

    @interface BeforeSuite{

    }
}







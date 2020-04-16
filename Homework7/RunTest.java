import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTest {
    public static void Start(Object TestClass) throws InvocationTargetException, IllegalAccessException {
        boolean before = false;
        boolean after = false;

        Method[] methods = TestMethods.class.getMethods();

        for(Method method : methods){
            if(method.isAnnotationPresent(Test.BeforeSuite.class) && before == false){
                before = true;
                try {
                method.invoke(TestClass);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            else if(method.isAnnotationPresent(Test.BeforeSuite.class) && before == true) {
                throw new RuntimeException("There can be only one method with an annotation BeforeSuite");
            }

            }
        for(Method method : methods) {
            for (int i = 1; i <= 10; i++) {
                if (method.getAnnotation(Test.class).priority() < 1 || method.getAnnotation(Test.class).priority() > 10) {
                    throw new RuntimeException(method.getName() + " ERROR The priority must be between 1 and 10.");
                }

                if (method.isAnnotationPresent(Test.class) && (method.getAnnotation(Test.class).priority() == i)){
                    try {
                        method.invoke(TestClass);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    }

                }

            }
        for(Method method : methods){
            if(method.isAnnotationPresent(Test.AfterSuite.class) && after == false){
                after = true;
                try {
                    method.invoke(TestClass);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            else if(method.isAnnotationPresent(Test.AfterSuite.class) && after == true) {
                throw new RuntimeException("There can be only one method with an annotation AfterSuite");
            }

        }

        }




}


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTest {
    public static void Start(Class TestClass) throws InvocationTargetException, IllegalAccessException {
        boolean before = false;
        boolean after = false;
        Method[] methods = TestMethods.class.getMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(Test.BeforeSuite.class) && before == false){
                before = true;
                try {
                method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            if(method.isAnnotationPresent(Test.BeforeSuite.class) && before == true) {
                throw new RuntimeException("There can be only one method with an annotation BeforeSuite");
            }




            }
        }



}


import java.lang.reflect.InvocationTargetException;

public class Demo {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TestClass test = new TestClass();
        RunTest.Start(test);
    }
}

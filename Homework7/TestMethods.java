public class TestMethods {


    @Test
    public boolean isNull(Class TestClass){
        int priority = 1;
        if (TestClass == null) {
            return true;
        }
        else return false;
    }

    @Test
    public boolean isNotNull(Class TestClass){
        int priority = 2;
        if (TestClass == null) {
            return false;
        }
        else return true;
    }

    @Test.BeforeSuite
    public void Before(){
        System.out.println("Run BeforeSuite");

    }

    @Test.AfterSuite

    public void After(){
        System.out.println("Run AfterSuite");

    }


}

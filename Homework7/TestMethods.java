public class TestMethods {


    @Test(priority = 3)
    public boolean isNull(Object TestClass) {

        if (TestClass == null) {
            System.out.println("Test isNull passed");
            return true;

        } else {
            System.out.println("Test isNull not passed");
            return false;
        }
    }

    @Test(priority = 5)
    public boolean isNotNull(Object TestClass) {

        if (TestClass == null) {
            System.out.println("Test isNotNull not passed");
            return false;
        } else {
            System.out.println("Test isNotNull passed");
            return true;
        }
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

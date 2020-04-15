
class Test1 {
    @Test(priority = 1)
    public static boolean equal(Object x, Object y) {
        if (x.equals(y)) {
            return true;
        } else return false;

    }

    @Test(priority = 2)
    public static boolean notNull(Object x) {
        if (x == null) {
            return false;
        }
        else return true;

    }
}

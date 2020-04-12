import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.Arrays;

public class ArrayTest {
    private Array testArray;

    @Before
    public void newArray(){
        testArray = new Array();
    }

    @Test

    public void testArrayStringEquals() {

        Assert.assertEquals(Arrays.toString(new int[]{43, 44, 80}), Arrays.toString(testArray.arrayEnter(new int[]{167, 4, 3, 26, 4, 43, 44, 80})));

        //System.out.println(Arrays.toString(Array.arrayEnter(new int[]{167, 4, 3, 26, 4, 43, 44, 80})));
    }

    @Test

    public void testArrayEquals(){
        Assert.assertArrayEquals(new int[]{43,44,80},testArray.arrayEnter(new int[]{167, 3, 4, 43, 44, 80}));
    }

    @Test

    public void test(){
        Assert.assertArrayEquals(new int[]{80},testArray.arrayEnter(new int[]{167, 3, 4, 43, 4, 80}));
    }

    @Test

    public void test1(){
        Assert.assertFalse(testArray.arrayEnter14(new int[]{167, 3, 5, 43, 8, 80}));
    }

    @Test

    public void test2(){
        Assert.assertTrue(testArray.arrayEnter14(new int[]{167, 4, 5, 43, 1, 80}));
    }

    @Test

    public void test3(){
        Assert.assertTrue(testArray.arrayEnter14(new int[]{167, 7, 5, 43, 1, 80,8,7}));
    }

}

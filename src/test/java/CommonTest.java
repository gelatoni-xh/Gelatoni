import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

public class CommonTest {

    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        CommonTest test = new CommonTest();
        System.out.println(test.add(a, b));
    }
}



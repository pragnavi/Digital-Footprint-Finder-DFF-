package test;

import util.SecurityUtil;

public class HashTest {

    public static void main(String[] args) {
        // Test acquiring and releasing a connection multiple times
    	String hashedpass = SecurityUtil.hashPassword("test");
    	System.out.println(hashedpass);
    }
}

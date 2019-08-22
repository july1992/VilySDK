// VilyAidl.aidl
package com.vily.idil;
import com.vily.idil.book.bookBean;

// Declare any non-default types here with import statements

interface VilyAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int addNumbers(int num1, int num2);//2 argument method to add
    List<bookBean> getBookList();
}

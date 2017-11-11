package com.yidu.sevensecondmall.utils;

import com.google.zxing.BarcodeFormat;

import java.util.Hashtable;

/**
 * Created by Administrator on 2017/3/13.
 */
public class MyBarcodeFormat {
        private static final Hashtable VALUES = new Hashtable();
        public static final MyBarcodeFormat QR_CODE = new MyBarcodeFormat("QR_CODE");
        public static final MyBarcodeFormat DATA_MATRIX = new MyBarcodeFormat("DATA_MATRIX");
        public static final MyBarcodeFormat UPC_E = new MyBarcodeFormat("UPC_E");
        public static final MyBarcodeFormat UPC_A = new MyBarcodeFormat("UPC_A");
        public static final MyBarcodeFormat EAN_8 = new MyBarcodeFormat("EAN_8");
        public static final MyBarcodeFormat EAN_13 = new MyBarcodeFormat("EAN_13");
        public static final MyBarcodeFormat UPC_EAN_EXTENSION = new MyBarcodeFormat("UPC_EAN_EXTENSION");
        public static final MyBarcodeFormat CODE_128 = new MyBarcodeFormat("CODE_128");
        public static final MyBarcodeFormat CODE_39 = new MyBarcodeFormat("CODE_39");
        public static final MyBarcodeFormat CODE_93 = new MyBarcodeFormat("CODE_93");
        public static final MyBarcodeFormat CODABAR = new MyBarcodeFormat("CODABAR");
        public static final MyBarcodeFormat ITF = new MyBarcodeFormat("ITF");
        public static final MyBarcodeFormat RSS14 = new MyBarcodeFormat("RSS14");
        public static final MyBarcodeFormat PDF417 = new MyBarcodeFormat("PDF417");
        public static final MyBarcodeFormat RSS_EXPANDED = new MyBarcodeFormat("RSS_EXPANDED");
        private final String name;

        private MyBarcodeFormat(String name) {
            this.name = name;
            VALUES.put(name, this);
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public static MyBarcodeFormat valueOf(String name) {
            if(name != null && name.length() != 0) {
                MyBarcodeFormat format = (MyBarcodeFormat)VALUES.get(name);
                if(format == null) {
                    throw new IllegalArgumentException();
                } else {
                    return format;
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
}

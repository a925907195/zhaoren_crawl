package com.fjsh.expression.constant;

public class ManageConstant {
    public enum ReturnStatus {
        FAIL_ZERO(0), SUCCESS_ONE(1);
        private final int value;
        ReturnStatus(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static ReturnStatus getByValue(int value) {
            switch (value) {
                case 0 :
                    return FAIL_ZERO;
                case 1 :
                    return SUCCESS_ONE;
                default :
                    throw new IllegalArgumentException("value " + value + "illegal ReturnStatus");
            }
        }
    }
    public static final String basicPath="/Volumes/CN_WIN7_SP1/images";
}

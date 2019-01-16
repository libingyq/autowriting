package com.dinfo.fi.enums;

public enum GrabStatusType {
    TRUE("true","正常"),OK("200","正常"),FALSE("false","抓取失败"),FAILED("failed","抓取失败");

    private String status;
    private String discription;
    GrabStatusType(String s ,String discription) {
        this.status = s;
        this.discription = discription;
    }

    public String getDiscription() {
        return discription;
    }

    public static GrabStatusType getEnumType(String status) {
        for (GrabStatusType grabStatusType : GrabStatusType.values()) {
            if (status.equals(grabStatusType.status)) {
                return grabStatusType;
            }
        }
        throw new TypeNotPresentException(status,null);
    }
}

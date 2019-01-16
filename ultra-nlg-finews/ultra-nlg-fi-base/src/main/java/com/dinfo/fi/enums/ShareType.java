package com.dinfo.fi.enums;

/**
 * 映射股票类型
 */
public enum ShareType {
    INDEX("index"),CONCEPT("concept"),INDUSTRY("industry"), STOCK("stock");

    String type;
    ShareType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ShareType getEnumType(String value) {
        for (ShareType shareType : ShareType.values()) {
            if (value.equals(shareType.type)) {
                return shareType;
            }
        }
        throw new TypeNotPresentException(value,null);
    }
}

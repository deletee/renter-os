package com.delete.renter.constant;

public enum RenterType {
    RENTER("借"),
    RETURN("还");

    String desc;
    public static String getDesc(String renterType) {
        if (RenterType.RETURN.name().equals(renterType)){
            return RenterType.RETURN.getDesc();
        }

        if (RenterType.RENTER.name().equals(renterType)){
            return RenterType.RENTER.getDesc();
        }

        return RenterType.RENTER.getDesc();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    RenterType(String desc){
        this.desc = desc;
    }


}

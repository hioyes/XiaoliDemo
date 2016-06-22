package com.xiaolidemo.model;

/**
 * 分公司实体
 * xiaokx
 * hioyes@qq.com
 * 2016-6-22
 */
public class Branch {

    /**
     * 分公司ID
     */
    private int BranchID;

    /**
     * 分公司名称
     */
    private String BranchName;

    /**
     * 分公司简称
     */
    private String ShortName;

    /**
     * 分公司编码
     */
    private String BranchCode;

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public int getBranchID() {
        return BranchID;
    }

    public void setBranchID(int branchID) {
        BranchID = branchID;
    }
}

package com.cmit.clouddetection.recognition;

import java.util.List;

public class ugc_result {
    private String sid;
    private String prism_version;
    private String prism_wnum;
    private List<prism_wordsInfo> prism_wordsInfo;

    public List<prism_wordsInfo> getPrism_wordsInfo() {
        return prism_wordsInfo;
    }

    public void setPrism_wordsInfo(List<prism_wordsInfo> prism_wordsInfo) {
        this.prism_wordsInfo = prism_wordsInfo;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPrism_version() {
        return prism_version;
    }

    public void setPrism_version(String prism_version) {
        this.prism_version = prism_version;
    }

    public String getPrism_wnum() {
        return prism_wnum;
    }

    public void setPrism_wnum(String prism_wnum) {
        this.prism_wnum = prism_wnum;
    }
}

package com.wcf.hellohome.read.model;

import lombok.Data;

/**
 * @author WCF
 * @time 2018/7/2
 * @why 功能：记录统计值和统计名称
 **/
@Data
public class SimpleStatisticInfo {
    /**
     * 统计项目名称
     */
    private String name;
    /**
     * 统计项目值
     */
    private int count;
    @Override
    public String toString(){
        return name+":"+count;
    }
}

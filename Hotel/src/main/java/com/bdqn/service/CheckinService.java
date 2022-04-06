package com.bdqn.service;

import com.bdqn.entity.Checkin;
import com.bdqn.vo.CheckinVo;

import java.util.List;

public interface CheckinService {

    /**
     * 查询入住信息列表接口
     * @param checkinVo
     * @return
     */
    List<Checkin> findCheckinList(CheckinVo checkinVo);

    /**
     * 登记入住接口
     * @param checkin
     * @return
     */
    int addCheckin(Checkin checkin);
}

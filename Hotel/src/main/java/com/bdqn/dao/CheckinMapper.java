package com.bdqn.dao;

import com.bdqn.entity.Checkin;
import com.bdqn.vo.CheckinVo;

import java.util.List;

public interface CheckinMapper {

    /**
     * 查询入住信息列表
     * @param checkinVo
     * @return
     */
    List<Checkin> findCheckinList(CheckinVo checkinVo);

    /**
     * 登记入住
     * @param checkin
     * @return
     */
    int addCheckin(Checkin checkin);

    /**
     * 根据主键查询入住信息
     * @param checkInId
     * @return
     */
    Checkin findById(Long checkInId);

    /**
     * 修改入住信息
     * @param checkin
     * @return
     */
    int updateCheckin(Checkin checkin);
}

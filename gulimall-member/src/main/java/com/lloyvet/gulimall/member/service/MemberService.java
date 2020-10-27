package com.lloyvet.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 10:55:07
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


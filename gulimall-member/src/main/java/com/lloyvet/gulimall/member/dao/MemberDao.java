package com.lloyvet.gulimall.member.dao;

import com.lloyvet.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 10:55:07
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}

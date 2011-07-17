package com.wfms.common.system.dao.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.IDepartDao;
import com.wfms.common.system.entity.DepartGenInfo;

@Repository
@Lazy(true)
public class DepartDao extends BaseDao<DepartGenInfo> implements IDepartDao {

}

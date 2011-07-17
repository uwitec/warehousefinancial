package com.wfms.common.system.dao.impl;


import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.IPartDao;
import com.wfms.common.system.entity.PartGenInfo;

@Repository
public class PartDao extends BaseDao<PartGenInfo> implements IPartDao {

}

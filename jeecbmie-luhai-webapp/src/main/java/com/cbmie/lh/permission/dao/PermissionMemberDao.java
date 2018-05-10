package com.cbmie.lh.permission.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.permission.entity.PermissionMember;
@Repository
public class PermissionMemberDao extends HibernateDao<PermissionMember, Long> {

}

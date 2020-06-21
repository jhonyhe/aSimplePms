package com.jyh.main.Realm;

import java.util.HashSet;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jyh.main.dao.*;
import com.jyh.main.modle.User;

/**
 * 自定义realm
 * @author Jyh
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    PrivilegeDao privilegeDao;
    
    @Resource
    RoleDao roleDao;
    
    @Resource
    UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userDao.findUserByName(username);
        System.out.println("调用doGetAuthorizationInfo user="+username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 给该用户设置角色，角色信息存在 role 表中取
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add(roleDao.findRoleById(user.getRole_id()).getRole_name());
        authorizationInfo.setRoles(hashSet);
        // 给该用户设置权限，权限信息存在 privilege表中取
        hashSet = new HashSet<String>();
        hashSet.add(privilegeDao.findPrivilegeById(user.getP_id()).getP_name());
        authorizationInfo.setStringPermissions(hashSet);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 根据 Token 获取用户名，如果您不知道该 Token 怎么来的，先可以不管，下文会解释
        String username = (String) authenticationToken.getPrincipal();
        // 根据用户名从数据库中查询该用户
        User user = userDao.findUserByName(username);
        System.out.println("调用doGetAuthenticationInfo user="+username);
        if(user != null) {
            // 把当前用户存到 Session 中
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            // 传入用户名和密码进行身份认证，并返回认证信息
            
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassWord(), "myRealm");
            return authcInfo;
        } else {
            return null;
        }
    }


}

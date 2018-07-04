package com.crazy.code.security;

import com.crazy.dashboard.model.UserInfo;
import com.crazy.dashboard.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by developer2 on 2017/9/13.
 */
public class DashboardUserDetailService implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoService.getByUsername(username);
        if(userInfo != null){
            return new DashboardUserDetail(userInfo);
        }
        throw new UsernameNotFoundException("The user not exist.");
    }
    public static void main(String[] args){
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode("465315099");
        System.out.print(hashPass);
    }

}

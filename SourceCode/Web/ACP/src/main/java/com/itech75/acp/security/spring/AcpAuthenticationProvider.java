//package com.itech75.acp.security.spring;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import com.itech75.acp.dal.LoginDAL;
//
//public class AcpAuthenticationProvider implements AuthenticationProvider {
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		String userName = authentication.getName();
//		String password = (String) authentication.getCredentials();
//		Authentication authNew = new UsernamePasswordAuthenticationToken(userName, password);
//		int userid = LoginDAL.checkLogin(userName, password);
//		
//		if(userid > 0){
//			Collection<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//			authNew = new UsernamePasswordAuthenticationToken(userName, password, authorities);
//		}
//		return authNew;
//	}
//
//	@Override
//	public boolean supports(Class<?> arg0) {
//		return true;
//	}
//
//}

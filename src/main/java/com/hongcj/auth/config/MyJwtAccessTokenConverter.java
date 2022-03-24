package com.hongcj.auth.config;

import com.hongcj.auth.util.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import sun.security.util.SecurityConstants;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Hongcj
 * @description
 * @date 3/24/22 5:53 PM
 */
public class MyJwtAccessTokenConverter extends JwtAccessTokenConverter {

	public MyJwtAccessTokenConverter() {
		setAccessTokenConverter(new AccessTokenConverter());
	}

	public static class AccessTokenConverter extends DefaultAccessTokenConverter{
		@Override
		public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
			Map<String, Object> map = (Map<String, Object>) super.convertAccessToken(token, authentication);
			if (authentication.getPrincipal() instanceof UserDetailsImpl) {
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				map.put("username", userDetails.getUsername());
				map.put("password", userDetails.getPassword());
			}
			List<String> roleList = authentication.getAuthorities().parallelStream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			map.put("authorities", roleList);
			map.put("license", "made by Hongcj");
			return map;
//		return super.convertAccessToken(token, authentication);
		}
	}

}

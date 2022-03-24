package com.hongcj.auth.config;

import com.hongcj.auth.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Hongcj
 * @description
 * @date 3/21/22 3:45 PM
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
				.tokenKeyAccess("permitAll()")  // 开启端口/oauth/token_key的访问权限（允许）
				.checkTokenAccess("permitAll()")   // 开启端口/oauth/check_token的访问权限（允许）/oauth/check_token allow check token
				.allowFormAuthenticationForClients();
	}

	/** 配置token管理 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				.tokenStore(tokenStore)
				.tokenServices(authorizationServerTokenServices())
				.userDetailsService(userDetailService)
				.authenticationManager(authenticationManager)
				.allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
	}
	/**
	 * 配置一个客户端
	 *
	 * 既可以通过授权码方式获取令牌，也可以通过密码方式获取令牌
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				//客户端ID
				.withClient("client1")
				//设置验证方式
				.authorizedGrantTypes("client_credentials", "password", "refresh_token")
				.scopes("all")
				.resourceIds("resourcesId")
//				.secret("{noop}client")
				.secret(passwordEncoder.encode("client"))
				//token过期时间
				.accessTokenValiditySeconds(10000)
				//refresh过期时间
				.refreshTokenValiditySeconds(10000)
				;
	}

	@Bean
	public TokenStore tokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
//		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		MyJwtAccessTokenConverter converter = new MyJwtAccessTokenConverter();
		converter.setSigningKey("hongcj");
		converter.setVerifier(new MacSigner("hongcj"));
		return converter;
	}

	/**
	 * 该方法用户获取一个token服务对象（该对象描述了token有效期等信息）
	 */
	private AuthorizationServerTokenServices authorizationServerTokenServices() {
		// 使用默认实现
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setSupportRefreshToken(true); // 是否开启令牌刷新
		defaultTokenServices.setTokenStore(tokenStore());
//		defaultTokenServices.setReuseRefreshToken(true);
		// 针对jwt令牌的添加
		defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());

		// 设置令牌有效时间（一般设置为2个小时）
		defaultTokenServices.setAccessTokenValiditySeconds(2 * 60 * 60); // access_token就是我们请求资源需要携带的令牌
		// 设置刷新令牌的有效时间
		defaultTokenServices.setRefreshTokenValiditySeconds(259200); // 3天

		return defaultTokenServices;
	}

}

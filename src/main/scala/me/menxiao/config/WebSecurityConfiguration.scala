package me.menxiao.config

import javax.sql.DataSource
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl

@Configuration
class WebSecurityConfiguration {
  @Bean def webSecurityConfig(dataSource: DataSource): WebSecurityConfigurerAdapter =
    new WebSecurityConfigurerAdapter() {
      override def configure(http: HttpSecurity): Unit = {
        //        http.authorizeRequests.antMatchers(
        //          "/console", "/console/**", "/console/",
        //          "/swagger-ui.html", "/swagger-resources", "/v2/**",
        //          "/**/*.css", "/**/*.js", "/**/*.png",
        //          "/configuration/**").permitAll
        //        http.authorizeRequests().anyRequest().authenticated()
        http.authorizeRequests().anyRequest().permitAll()
        http.csrf().disable()
        http.headers().frameOptions().disable()
        http.httpBasic()
      }

      @Bean override def userDetailsService(): UserDetailsService = {
        val manager = new JdbcDaoImpl
        manager.setDataSource(dataSource)
        manager
      }
    }
}

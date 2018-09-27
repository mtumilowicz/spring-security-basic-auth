[![Build Status](https://travis-ci.com/mtumilowicz/spring-security-basic-auth.svg?branch=master)](https://travis-ci.com/mtumilowicz/spring-security-basic-auth)

# spring-security-basic-auth
The main goal of this project is to show configuration of Basic Authentication in
Spring Security + Spring Boot environment.

_Reference_: https://www.baeldung.com/spring-security-basic-authentication  
_Reference_: http://websystique.com/spring-security/secure-spring-rest-api-using-basic-authentication/

# preface
With Basic Authentication, clients send it’s Base64 encoded 
credentials with each request, using HTTP [Authorization] header. 
That means each request is independent of other request and server 
may/does not maintain any state information for the client, 
which is good for scalability point of view.

_Remark_: For any sort of Security implementation, ranging from Basic 
authentication to a full fledged OAuth2 implementation, HTTPS is a must 
have. Without HTTPS, no matter what your implementation is, security is 
vulnerable to be compromised.

Exemplary header:
```
Header name: Authorization
Header value: Basic OnVzZXI=
```

# realm
The realm attribute (case-insensitive) is required for all authentication 
schemes which issue a challenge. The realm value (case-sensitive), in combination 
with the canonical root URL of the server being accessed, defines the protection 
space. These realms allow the protected resources on a server to be partitioned 
into a set of protection spaces, each with its own authentication scheme and/or 
authorization database. The realm value is a string, generally assigned by the origin 
server, which may have additional semantics specific to the authentication scheme.

In short, pages in the same realm should share credentials. If your credentials work 
for a page with the realm "My Realm", it should be assumed that the same username 
and password combination should work for another page with the same realm.

# project description
1. We have endpoints in `HelloController` that we want to protect (for different user roles)
    ```
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    ```
1. We provide authorization exception handler with `BasicAuthenticationEntryPoint`:
    ```
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        super.commence(request, response, authEx);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authEx.getMessage());
    }    
1. We provide configuration for basic authentication in `BasicAuthenticationSecurityConfigurerAdapter`:
    * protected urls:
        ```
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/health").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint);
        }    
        ```
    * users with roles (educational purposes only):
        ```
        @Bean
        public UserDetailsService userDetailsService() {
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(User
                    .withUsername("user")
                    .password(encoder().encode(String.valueOf(new char[]{'u','s','e','r'})))
                    .roles("USER").build());
            manager.createUser(User
                    .withUsername("admin")
                    .password(encoder().encode(String.valueOf(new char[]{'a','d','m','i','n'})))
                    .roles("ADMIN").build());
            return manager;
        }    
        ```

# tests
We provide full test for authorized / not authorized requests.

Note that to disable basic auth for test, you could use:
1. `@ConditionalOnProperty(value = "app.security.basic.enabled", havingValue = "true", matchIfMissing = true)`
1. and add `app.security.basic.enabled = false` to `test/resources/application.properties`.
package digitalbooking.backend.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import digitalbooking.backend.demo.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        Auth auth = new Auth();
        try{
            auth = new ObjectMapper().readValue(request.getReader(), Auth.class);
        }catch (IOException e){
        }
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                auth.getEmail(),
                auth.getPassword(),
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(userToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = Token.createToken(userDetails.getName(),userDetails.getUsername());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        ObjectMapper mapper = new ObjectMapper();
        String jsonToken = mapper.writeValueAsString(tokenMap);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonToken);
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
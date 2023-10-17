package tech.gabrielmelo.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.gabrielmelo.todolist.repository.IUserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var servletPath = request.getServletPath();
    if (servletPath.equals("/tasks")) {
      // Get auth information
      var authorization = request.getHeader("Authorization");
      
      // Convert information
      var authDecoded = authorization.substring("Basic".length()).trim();

      byte[] authDecode = Base64.getDecoder().decode(authDecoded);

      var authString = new String(authDecode);

      String[] credentials = authString.split(":");

      String username = credentials[0];
      String password = credentials[1];

      // Validate user
      var user = this.userRepository.findByUsername(username);
      if (user == null) {
        response.sendError(401, "User does not have access");
      } else {
        // Validate password
        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        
        if (passwordVerify.verified) {
          request.setAttribute("idUser", user.getId());
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401, "User does not have access");
        }
      }
    } else {
      filterChain.doFilter(request, response);
    }

  }
  
}

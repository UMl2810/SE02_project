package fit.se2.se02_project.service;

import fit.se2.se02_project.model.User;
import fit.se2.se02_project.repositories.RoleRepository;
import fit.se2.se02_project.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.*;

@Service
public class CommonService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Value("${spring.mail.password}")
    private String fromPassword;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Autowired
    private HttpServletRequest request;


    public String createRandomString(int length) {
        Random rng = new Random();
        StringBuilder rString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (rng.nextInt(26) + 'a');
            rString.append(randomChar);
        }

        return rString.toString();
    }

    public String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            if (value == null) {
                return null;
            }
            byte[] bytes = digest.digest(value.getBytes());

            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle exception or log error
            e.printStackTrace();
            return null;
        }
    }

    public void sendEmail(String subject, String body, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        emailSender.send(message);
    }

    public String createToken(User user) {
        String userRole = roleRepository.findAll()
                .stream()
                .filter(r -> r.getId() == user.getRole().getId())
                .map(r -> r.getName())
                .findAny()
                .orElse(null);

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getUsername());
        claims.put("role", userRole);

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutes
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();

        return token;
    }

    public User getCurrentUser() {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null || token.isEmpty()) {
            return null;
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = (String) claims.get("name");

            return userRepository.findAll()
                    .stream()
                    .filter(u -> Objects.equals(u.getUsername(), username))
                    .findAny()
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }


}

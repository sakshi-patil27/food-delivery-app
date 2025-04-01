package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.roles;
import com.example.demo.entity.user_info;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Optional<user_info> findByEmail(String email) {
        String sql = "SELECT * FROM user_info WHERE email = '" + email + "'";
        
        List<user_info> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            user_info user = new user_info();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        });
        
        return users.stream().findFirst();
    }

    public void save(user_info user) {
    	String sql = "INSERT INTO user_info (name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
        //for fiding the last inserted user_id
        String userIdQuery = "SELECT LAST_INSERT_ID()";
        Long userId = jdbcTemplate.queryForObject(userIdQuery, Long.class);
        
        for (String role : user.getRoles()) {
        	String roleIdQuery = "SELECT id FROM roles WHERE role_name = ?";
        	Long role_id = jdbcTemplate.queryForObject(roleIdQuery, Long.class, role);


            String roleSql = "INSERT INTO user_roles (user_id, role_id) VALUES (" + userId + ", " + role_id + ")";
            
            jdbcTemplate.update(roleSql); 
        }
    }
    public void update(user_info user) {
        String sql = "UPDATE user_info SET name = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getId());

        // Optional: Update user roles (clear and reinsert)
        String deleteRolesSql = "DELETE FROM user_roles WHERE user_id = ?";
        jdbcTemplate.update(deleteRolesSql, user.getId());

        for (String role : user.getRoles()) {
            String roleIdQuery = "SELECT id FROM roles WHERE role_name = ?";
            Long roleId = jdbcTemplate.queryForObject(roleIdQuery, Long.class, role);

            String roleSql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
            jdbcTemplate.update(roleSql, user.getId(), roleId);
        }
    }
}

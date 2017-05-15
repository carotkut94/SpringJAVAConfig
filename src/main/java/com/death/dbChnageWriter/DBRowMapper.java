package com.death.dbChnageWriter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
public class DBRowMapper implements RowMapper<Users> {
	public Users mapRow(ResultSet rs, int i) throws SQLException {
		// TODO Auto-generated method stub
		Users user = new Users();
		user.setId(rs.getInt("id"));
		user.setPassword(rs.getString("password"));
		user.setAlias(rs.getString("alias"));
		user.setUsername(rs.getString("username"));
		user.setTimestamp(rs.getTimestamp("updated_at"));
		UserDetails userDetails = new UserDetails();
		userDetails.setDetail_id(rs.getInt("detail_id"));
		userDetails.setAddress(rs.getString("address"));
		userDetails.setPhone(rs.getInt("phone"));
		user.setDetails(userDetails);
		return user;
	}

}

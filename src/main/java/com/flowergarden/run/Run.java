package com.flowergarden.run;


import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Run {

	public static void main(String[] args) throws IOException {

		File file = new File("com.flowergarden.db");
		String url = "jdbc:sqlite:"+file.getCanonicalFile().toURI();
		System.out.println(url);
		try(Connection conn = DriverManager.getConnection(url)) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from flower");
			while (rs.next()) {
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

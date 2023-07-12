package in.ineuron.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.ineuron.dto.Student;
import in.ineuron.util.jdbcUtil;

//Persistence logic using JDBC API

public class StudentDaoImpl implements IStudentDao {

	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;
	@Override
	public String addStudent(Student student) {
		// TODO Auto-generated method stub
		String sqlInsertQuery= "insert into student (`name`,`age`,`address`) values (?,?,?) ";
		try {
			connection = jdbcUtil.getJdbcConnection();
			if (connection != null) {
				
				pstmt = connection.prepareStatement(sqlInsertQuery);
			}
			if(pstmt !=null) {
				pstmt.setString(1, student.getSname());
				pstmt.setInt(2, student.getSage());
				pstmt.setString(3, student.getSaddress());
				
				int rowAffected = pstmt.executeUpdate();
				if(rowAffected == 1) {
					return "success";
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failure";
	}

	@Override
	public Student searchStudent(Integer sid) {
		// TODO Auto-generated method stub
		String sqlSelectQuery = "select id, name, age, address from student where id = ?";
		try {
			connection = jdbcUtil.getJdbcConnection();
			if(connection != null)
				pstmt = connection.prepareStatement(sqlSelectQuery);
			if(pstmt != null) {
				pstmt.setInt(1, sid);
			}
			if(pstmt!= null) {
				resultSet = pstmt.executeQuery();
			}
			if(resultSet!= null) {
				Student student = null;
				if(resultSet.next()) {
					student = new Student();
					//copy resultSet data to studetnt object
					student.setSid(resultSet.getInt(1));
					student.setSname(resultSet.getString(2));
					student.setSage(resultSet.getInt(3));
					student.setSaddress(resultSet.getString(4));
					
					
					return student;
				}
		
			}
		} catch (SQLException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateStudent(Student student) {
		// TODO Auto-generated method stub
		String SqlUpdateQuery = "update student set name=?,age=?,address=? where id=?";
		try {
			connection = jdbcUtil.getJdbcConnection();
			if(connection!= null)
				pstmt = connection.prepareStatement(SqlUpdateQuery);
			
			if(pstmt!= null) {
				pstmt.setString(1, student.getSname());
				pstmt.setInt(2, student.getSage());
				pstmt.setString(3, student.getSaddress());
				pstmt.setInt(4, student.getSid());
				
				int rowAffected = pstmt.executeUpdate();
				if(rowAffected ==1) {
					return "success";
				}
			}
		} catch (SQLException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "failure";
	}

	@Override
	public String deleteStudent(Integer sid) {
		// TODO Auto-generated method stub
		String sqlDeleteQuery = "delete from student where id = ?";
		try {
			connection = jdbcUtil.getJdbcConnection();
			if(connection != null)
				pstmt = connection.prepareStatement(sqlDeleteQuery);
			
			if(pstmt != null) {
				pstmt.setInt(1, sid);
				
				int rowAffected = pstmt.executeUpdate();
				if (rowAffected ==1) {
					return "success";
				}else {
					return "not found";
				}
			}
		}catch(SQLException | IOException e) {
			e.printStackTrace();
			return "failure";
		}
		return "failure";

	}

}

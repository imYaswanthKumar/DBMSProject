package com.dbms.project.dao;

import com.dbms.project.model.Branch;
import com.dbms.project.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BranchDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BranchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertBranch(Branch branch) {
        final String sql = "INSERT INTO BRANCH(BRANCHNAME) VALUES(?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toUpperCase(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, branch.getBranchName());
            return ps;
        }, keyholder);
    }


    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS BRANCH(" +
                "BRANCHNAME VARCHAR(100)," +
                "BRANCHID INT AUTO_INCREMENT PRIMARY KEY" +
                ")";
        String sql2="ALTER TABLE BRANCH ADD UNIQUE INDEX (`BRANCHNAME`)";
        jdbcTemplate.execute(sql.toUpperCase());
        jdbcTemplate.execute(sql2.toUpperCase());
    }

    public Integer getBranchID(String BranchName){
        String sql = "SELECT BRANCHID FROM BRANCH WHERE BRANCHNAME = ?";
        return jdbcTemplate.queryForObject(sql.toUpperCase(), new Object[]{BranchName}, Integer.class);
    }

    public String getBranchName(Integer branchID) {
        String sql = "SELECT BRANCHNAME FROM BRANCH WHERE BRANCHID = ?";
        return jdbcTemplate.queryForObject(sql.toUpperCase(), new Object[] {branchID}, String.class);
    }

    public List<Branch> getAllBranches(){
        String sql = "SELECT * FROM BRANCH";
        return jdbcTemplate.query(sql.toUpperCase(), new BeanPropertyRowMapper<>(Branch.class));
    }

    public void insertAll(){
        String sql = "SELECT COUNT(*) FROM BRANCH";
        int cnt = jdbcTemplate.queryForObject(sql.toUpperCase(), Integer.class);
        if(cnt == 0){
            Branch b1 = new Branch();
            b1.setBranchName("Computer Science and Engineering"); insertBranch(b1);
            b1.setBranchName("Mathematics and Computing"); insertBranch(b1);
            b1.setBranchName("Electronics Engineering"); insertBranch(b1);
            b1.setBranchName("Electrical Engineering"); insertBranch(b1);
            b1.setBranchName("Mechanical Engineering"); insertBranch(b1);
            b1.setBranchName("Chemical Engineering"); insertBranch(b1);
        }
    }

}

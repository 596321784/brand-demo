package com.lianxi.service;

import com.lianxi.mapper.BrandMapper;
import com.lianxi.mapper.UserMapper;
import com.lianxi.pojo.Brand;
import com.lianxi.pojo.User;
import com.lianxi.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author fq
 * @create 2022-11-16 15:41
 */
public class UserService {
    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

   public User login(String username,String password){
       SqlSession sqlSession = sqlSessionFactory.openSession();
       UserMapper mapper = sqlSession.getMapper(UserMapper.class);
       User user = mapper.select(username,password);
       sqlSession.close();
       return user;
   }
   public boolean register(User user){
       SqlSession sqlSession = sqlSessionFactory.openSession();
       UserMapper mapper = sqlSession.getMapper(UserMapper.class);
       User u = mapper.selectByUsername(user.getUsername());
       if (u == null){
           mapper.add(user);
           sqlSession.commit();
       }
       sqlSession.close();
       return u == null ;
   }
}

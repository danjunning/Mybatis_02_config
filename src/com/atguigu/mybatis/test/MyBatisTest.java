package com.atguigu.mybatis.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperAnnotation;

public class MyBatisTest {
	
	public SqlSessionFactory getsessionFactory() throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void test() throws IOException {
		SqlSessionFactory sqlSessionFactory=getsessionFactory();
		SqlSession sqlsession=sqlSessionFactory.openSession();
		
		try{
			
			Employee e=sqlsession.selectOne("com.atguigu.mybatis.dao.EmployeeMapper.getEmpByid", 1);
			System.out.println("没有使用接口"+e.getLastName());
		}finally{
			sqlsession.close();
		}
		
	}
	
	@Test
	public void test01() throws IOException{
		SqlSessionFactory sqlSessionFactory=getsessionFactory();
		SqlSession sqlsession=sqlSessionFactory.openSession();
		
		try{
			//获取接口的实现类对象：mybatis自动为接口创建代理对象
			EmployeeMapper Mapper=sqlsession.getMapper(EmployeeMapper.class);
			//System.out.println(Mapper.getClass());
			Employee e= Mapper.getEmpByid(1);
			System.out.println("使用接口的方式："+e.getLastName());
		}finally{
			sqlsession.close();
		}
		
	}
	
	@Test
	public void test02() throws IOException{
		SqlSessionFactory sessionFactory=getsessionFactory();
		SqlSession sqlSession=sessionFactory.openSession();
		
		try{
			EmployeeMapperAnnotation mapper=sqlSession.getMapper(EmployeeMapperAnnotation.class);
			Employee e=mapper.getEmpByid(2);
			System.out.println(e.getLastName());
		}finally{
			sqlSession.close();
		}
	}

}

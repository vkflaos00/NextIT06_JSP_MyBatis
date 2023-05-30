package kr.or.nextit.code.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.nextit.code.dao.ICommCodeDao;
import kr.or.nextit.code.vo.CodeVO;
import kr.or.nextit.common.util.NextITSqlSessionFactory;

public class CommCodeServiceImpl implements ICommCodeService{

	//ICommCodeDao commCodeDao = new CommCodeDaoImpl();
	SqlSessionFactory sqlSessionFactory 
		= NextITSqlSessionFactory.getSqlSessionFactory(); 
	
	
	@Override
	public List<CodeVO> getCodeListByParent(String commParent) {
		// TODO Auto-generated method stub
		
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		ICommCodeDao codeDao = sqlSession.getMapper(ICommCodeDao.class);
		
		try {
			//List<CodeVO> codeList= commCodeDao.getCodeListByParent(commParent);
			List<CodeVO> codeList= codeDao.getCodeListByParent(commParent);
			return codeList;
		}finally {
			sqlSession.close();
		}
		
		
	}

	
	
}

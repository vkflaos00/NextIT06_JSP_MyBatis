package kr.or.nextit.member.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.nextit.common.util.NextITSqlSessionFactory;
import kr.or.nextit.common.vo.RoleInfoVO;
import kr.or.nextit.common.vo.UserRoleVO;
import kr.or.nextit.exception.BizDuplicateKeyException;
import kr.or.nextit.exception.BizNotEffectedException;
import kr.or.nextit.exception.BizNotFoundException;
import kr.or.nextit.exception.BizPasswordNotMatchedException;
import kr.or.nextit.member.dao.IMemberDao;
import kr.or.nextit.member.vo.MemberSearchVO;
import kr.or.nextit.member.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {

	SqlSessionFactory sqlSessionFactory 
		= NextITSqlSessionFactory.getSqlSessionFactory();
	
	

	@Override
	public void registerMember(MemberVO member) throws BizDuplicateKeyException, BizNotEffectedException {
		// TODO Auto-generated method stub
		System.out.println("void registerMember");
		
		 SqlSession sqlSession 
		 	= sqlSessionFactory.openSession(true); //true 는 auto commit;
		 IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
		
		 try {
			 if(member.getMemId() != null && !member.getMemId().equals("")) {
					
					//MemberVO  vo = memberDao.getMember(member.getMemId());
					MemberVO  vo = memDao.getMember(member.getMemId());
					if( vo != null ) {
						throw new BizDuplicateKeyException();
					}
					
					//int resultCnt = memberDao.insertMember(member);
					int resultCnt = memDao.insertMember(member);
					if (resultCnt != 1) {
						throw new BizNotEffectedException();
					} 
					//int resultCnt2 = memberDao.insertUserRole(member);
					int resultCnt2 = memDao.insertUserRole(member);
					if (resultCnt2 != 1) {
						throw new BizNotEffectedException();
					}
				}
		 }finally {
			sqlSession.close();
		}
		 
		
	}

	
	@Override
	public boolean loginCheck(MemberVO member, HttpServletRequest request, HttpServletResponse response) throws BizNotEffectedException {
		// TODO Auto-generated method stub

		SqlSession sqlSession 
		 	= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
 
		MemberVO vo = null;
		if(member.getMemId()==null || member.getMemId().equals("")) {
			return false;
		}else {
			//vo = memberDao.loginCheck(member);
			vo = memDao.loginCheck(member);
		}
		try {
			if(vo == null){
				System.out.println("do not get member info ");
				return false;
			}else{
				System.out.println("success login");
				
				//List<UserRoleVO> userRoleList = memberDao.getUserRole(member);
				List<UserRoleVO> userRoleList = memDao.getUserRole(member);
				if(userRoleList != null) {
					vo.setUserRoleList(userRoleList);
				}
				
				HttpSession session = request.getSession();
				session.setAttribute("memberVO", vo);
					
				String rememberMe = member.getRememberMe();
				if (rememberMe != null && rememberMe.equals("Y")) {
					System.out.println("rememberMe is Y");
					Cookie cookie= new Cookie("rememberMe", member.getMemId());
					cookie.setMaxAge(60*60*24); 
					cookie.setHttpOnly(true);
					//cookie.setSecure(true);
					response.addCookie(cookie);
				}else{
					Cookie cookie= new Cookie("rememberMe", "");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
				return true;
			}
		} catch (Exception e) {
			throw new BizNotEffectedException();
		}finally {
			sqlSession.close();
		}
	}


	@Override
	public MemberVO getMember(String memId) throws BizNotEffectedException {
		// TODO Auto-generated method stub
		
		SqlSession sqlSession 
			= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
		try {
			MemberVO member = null;
			if(memId != null && ! memId.equals("")) {
				//member = memberDao.getMember(memId);
				member = memDao.getMember(memId);
			}
			if(member == null) {
				throw new BizNotEffectedException();
			}
			return member;
		} finally {
			sqlSession.close();
		}
	
	}

	@Override	
	public void modifyMember(MemberVO member) throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		// TODO Auto-generated method stub
		
		SqlSession sqlSession 
			= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
		
		try {
			MemberVO vo = null;
			if(member.getMemId() != null && ! member.getMemId().equals("")) {
				//vo = memberDao.getMember(member.getMemId());
				vo = memDao.getMember(member.getMemId());
			}
			
			if(vo == null) {
				throw new BizNotFoundException();
			}
			if( !vo.getMemPass().equals(member.getMemPass())) {
				throw new BizPasswordNotMatchedException();
			}
			
			//int resultCnt = memberDao.updateMember(member);
			int resultCnt = memDao.updateMember(member);
			
			if(resultCnt != 1){
				throw new BizNotEffectedException();
			}
		} finally {
			 sqlSession.close();
		}
		
		
	
	}

	@Override
	public void removeMember(MemberVO member) throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		// TODO Auto-generated method stub
		
		SqlSession sqlSession 
			= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
		
		try {
			MemberVO vo = null;
			if(member.getMemId() != null && ! member.getMemId().equals("")) {
				//vo = memberDao.getMember(member.getMemId());
				vo = memDao.getMember(member.getMemId());
			}
			
			if( vo == null) {
				throw new BizNotFoundException();
			}
			if( !vo.getMemPass().equals(member.getMemPass()) ){
				throw new BizPasswordNotMatchedException();
			}
			
			//int resultCnt = memberDao.deleteMember(member);
			int resultCnt = memDao.deleteMember(member);
			if(resultCnt != 1){
				throw new BizNotEffectedException();
			}
		}finally {
			sqlSession.close();
		}
	
		
	}

	@Override
	public List<MemberVO> getMemberList(MemberSearchVO searchVO) throws BizNotFoundException {
		// TODO Auto-generated method stub

		SqlSession sqlSession 
			= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
	
		try {
			//int totalRowCount  = memberDao.getTotalRowCount(searchVO);
			int totalRowCount  = memDao.getTotalRowCount(searchVO);
			searchVO.setTotalRowCount(totalRowCount);
			searchVO.pageSetting();
			System.out.println("searchVO.toString() : "+searchVO.toString());

			//List<MemberVO> memberVO = memberDao.getMemberList(searchVO);
			List<MemberVO> memberVO = memDao.getMemberList(searchVO);
			
			if(memberVO == null) {
				throw new BizNotFoundException();
			}
			return memberVO;
		}finally {
			sqlSession.close();
			
		}
		
	}


	@Override
	public void removeMultiMember(String memMultiId) throws BizNotEffectedException {
		// TODO Auto-generated method stub
		
		System.out.println("memMultiId: "+ memMultiId);
		
		SqlSession sqlSession 
			= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try { 
				List<Object> list  = objectMapper.readValue(memMultiId, new TypeReference<List<Object>>(){});
				System.out.println("list: "+ list);
				
				if(list.size() == 0) {
					throw new BizNotEffectedException();
				}
				
				for(int i=0; i<list.size(); i++) {
					String memId = (String) list.get(i);
					MemberVO member = new MemberVO();
					member.setMemId(memId);
					//int resultCnt = memberDao.deleteMember(member);
					int resultCnt = memDao.deleteMember(member);
					if(resultCnt ==0) {
						throw new BizNotEffectedException();	
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizNotEffectedException();
		} finally {
			sqlSession.close();
		}
	}


	@Override
	public MemberVO getMemberRole(String memId) throws BizNotEffectedException {
		// TODO Auto-generated method stub
		System.out.println("MemberServiceImpl memId: "+ memId);
		
		SqlSession sqlSession 
			= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
		
		try {
			MemberVO member = null;
			if(memId != null && ! memId.equals("")) {
				//member =  memberDao.getMember(memId);
				member =  memDao.getMember(memId);
			}
			if(member == null) {
				throw new BizNotEffectedException();
			}
			
			//List<UserRoleVO>  userRoleList =memberDao.getUserRole(member);
			List<UserRoleVO>  userRoleList =memDao.getUserRole(member);
			
			if(userRoleList == null) {
				throw new BizNotEffectedException();
			}
			
			member.setUserRoleList(userRoleList);
			
			return member;
		}finally {
			
		}
	
	}


	@Override
	public List<RoleInfoVO> getRoleInfo() throws BizNotEffectedException {
		// TODO Auto-generated method stub
		
		SqlSession sqlSession 
			= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
		
		try {
			//List<RoleInfoVO> roleInfoList = memberDao.getRoleInfo();
			List<RoleInfoVO> roleInfoList = memDao.getRoleInfo();
			
			if(roleInfoList ==null) {
				throw new BizNotEffectedException();
			}
			return roleInfoList;
		}finally {
			sqlSession.close();
		}
	}

	@Override
	public void updateUserRole(String memId, String[] roles) throws BizNotEffectedException {
		// TODO Auto-generated method stub

		System.out.println("roles.length :"+ roles.length);
		
		SqlSession sqlSession 
			= sqlSessionFactory.openSession(true); //true 는 auto commit;
		IMemberDao memDao = sqlSession.getMapper(IMemberDao.class);
		
		try {
			if( memId != null && ! memId.equals("")) {
				//memberDao.deleteUserRole(memId);
				memDao.deleteUserRole(memId);
				
				if(roles.length >0 ) {
					for(int i=0; i<roles.length; i++) {
						//memberDao.insertMultiRole(memId, roles[i]);
						memDao.insertMultiRole(memId, roles[i]);
					}
				}
			}else {
				throw new BizNotEffectedException();
			}
		}finally {
			
		}
		
		
	}
 

}
package com.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.board.domain.BoardCommand;

//SqlSessionDaoSupport 상속받는 이유->SqlSession객체를 자동으로 반환 getSqlSession()
public class BoardDaoImpl extends SqlSessionDaoSupport implements BoardDao {

	//검색분야에 따른 검색어까지 조회(페이징 처리)
	public List<BoardCommand> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<BoardCommand> list=getSqlSession().selectList("selectList",map);
		System.out.println("ListDaoImpl 테스트중입니다.~");
		return list;
	}

	public int getRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		System.out.println("getRowCount()호출됨!");
		return getSqlSession().selectOne("selectCount",map);
	}
    //최대값
	public int getNewSeq() {
		// TODO Auto-generated method stub
		//Object->Integer->int
		int newseq=(Integer)getSqlSession().selectOne("getNewSeq");
		System.out.println("getNewSeq()의 newseq=>"+newseq);
		return newseq;
	}
	//글쓰기
	public void insert(BoardCommand board) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertBoard",board);
	}
}





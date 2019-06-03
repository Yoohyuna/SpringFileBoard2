package com.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.board.domain.BoardCommand;

//SqlSessionDaoSupport ��ӹ޴� ����->SqlSession��ü�� �ڵ����� ��ȯ getSqlSession()
public class BoardDaoImpl extends SqlSessionDaoSupport implements BoardDao {

	//�˻��о߿� ���� �˻������ ��ȸ(����¡ ó��)
	public List<BoardCommand> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<BoardCommand> list=getSqlSession().selectList("selectList",map);
		System.out.println("ListDaoImpl �׽�Ʈ���Դϴ�.~");
		return list;
	}

	public int getRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		System.out.println("getRowCount()ȣ���!");
		return getSqlSession().selectOne("selectCount",map);
	}
    //�ִ밪
	public int getNewSeq() {
		// TODO Auto-generated method stub
		//Object->Integer->int
		int newseq=(Integer)getSqlSession().selectOne("getNewSeq");
		System.out.println("getNewSeq()�� newseq=>"+newseq);
		return newseq;
	}
	//�۾���
	public void insert(BoardCommand board) {
		// TODO Auto-generated method stub
		getSqlSession().insert("insertBoard",board);
	}
}





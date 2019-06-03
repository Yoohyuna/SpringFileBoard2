package com.board.dao;

//List(���ڵ� ������ ���� ��ü),Map(�˻��о�,�˻���)
import java.util.List;
import java.util.Map;

import com.board.domain.BoardCommand;

public interface BoardDao {
	
    //1.�۸�Ϻ��� 
	public List<BoardCommand> list(Map<String,Object>map);
	
	//2.�ѷ��ڵ��(�˻�� �´� ���ڵ������ ����)
	public int getRowCount(Map<String,Object>map);
	
	//3.�ִ밪 ��ȣ ���ϱ�
	public int getNewSeq();
	//4.�Խ����� �۾���
	public void insert(BoardCommand board);
}



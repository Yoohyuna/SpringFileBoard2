package com.board.dao;

//List(레코드 여러개 담을 객체),Map(검색분야,검색어)
import java.util.List;
import java.util.Map;

import com.board.domain.BoardCommand;

public interface BoardDao {
	
    //1.글목록보기 
	public List<BoardCommand> list(Map<String,Object>map);
	
	//2.총레코드수(검색어에 맞는 레코드수까지 포함)
	public int getRowCount(Map<String,Object>map);
	
	//3.최대값 번호 구하기
	public int getNewSeq();
	//4.게시판의 글쓰기
	public void insert(BoardCommand board);
}



package com.board.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//추가 메서드 호출 및 페이징 처리부분
import com.board.dao.BoardDao;//DB
import com.board.domain.BoardCommand;
import com.board.util.PagingUtil;

@Controller 
public class ListController {

	//private Logger log=Logger.getLogger(ListController.class); //로그를 처리할 클래스명
	private Logger log=Logger.getLogger(this.getClass());//this.getClass()->현재클래스명
	
	@Autowired
	private BoardDao boardDao;//자동으로 Setter Method 호출X=>의존성객체 넣어줌(byType)
	
	@RequestMapping("/board/list.do")
	public ModelAndView process
	   (@RequestParam(value="pageNum",defaultValue="1") int currentPage,
	    @RequestParam(value="keyField",defaultValue="") String keyField,
	    @RequestParam(value="keyWord",defaultValue="") String keyWord) {
		if(log.isDebugEnabled()) { //로그객체가 디버깅모드상태인지 아닌지를 체크
			System.out.println("여기는 보드 list.do");
			log.debug("currentPage:"+currentPage); //log.debug메서드 사용
			log.debug("keyField:"+keyField);
			log.debug("keyWord:"+keyWord);
		}
		//검색분야,검색어를 전달->parameteType="map"
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keyField", keyField);
		map.put("keyWord", keyWord);
		
		//총레코드수 또는 검색된 글의 총레코드수
		int count=boardDao.getRowCount(map);
		//페이징 처리  1.현재페이지 2.총레코드수 3.페이지당 게시물수 4.블럭당 페이지수 5.요청명령어
		PagingUtil page=new PagingUtil(currentPage,count,3,3,"list.do");
		//start=>페이지당 맨 첫번째 나오는 게시물 번호
		map.put("start",page.getStartCount());//<->map.get("start")=>#{start}
		map.put("end", page.getEndCount());
		
		List<BoardCommand> list=null;
		if(count > 0) {
			System.out.println("여기는 DAO호출");
			list=boardDao.list(map);//keyField,keyWord,start,end
		}else {
			list=Collections.emptyList();//0 적용
		}
		System.out.println("ListController클래스의 count=>"+count);
		
		ModelAndView mav=new ModelAndView("boardList");//boardList.jsp에서 
		mav.addObject("count",count);//총레코드수
		mav.addObject("list",list);//레코드 전체값
		mav.addObject("pagingHtml",page.getPagingHtml());//링크문자열을 전달
		
		return mav;
	}
}









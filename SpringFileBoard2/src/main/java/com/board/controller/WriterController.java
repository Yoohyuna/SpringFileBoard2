package com.board.controller;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.dao.BoardDao;
import com.board.domain.BoardCommand;
import com.board.util.FileUtil;
import com.board.validator.BoardValidator;

@Controller
public class WriterController {
        //Logger객체=>제대로 입력했는지 체크해서 그 결과를 콘솔에 출력
	   //                      시켜주는 객체
	private Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private BoardDao boardDao;
	
	/*
	 * 하나의 요청명령어=>하나의 컨트롤러만 사용X
	 * 하나의 컨트롤러->여러개의 요청명령어를 등록해서 사용이 가능.
	 * 같은 요청명령어를 GET or POST으로 전송할지를 결정하는 속성
	 * method=RequestMethod.GET | method=RequestMethod.POST
	 */
	 //1.글쓰기 폼으로 이동(Get방식)
	@RequestMapping(value="/board/write.do",method=RequestMethod.GET)
	public String form() {  //메서드명은 임의로 작성
		System.out.println("다시 처음부터 값을 입력받기위해서 form()호출됨!");
		return "boardWrite";// return "이동할 페이지명'//definition name과 동일
	}
	
	//2.에러메세지 출력=>다시 초기화가 가능하게 설정->@ModelAttribute("커맨드객체 별칭명")
	@ModelAttribute("command")
	public BoardCommand forBacking() { //반환형 (DTO형) 임의의 메서드명
		System.out.println("forBacking()호출됨!");
		return new BoardCommand();//BoardCommand bc=new BoardCommand();
	}
	//3.입력해서 유효성검사->에러발생
	//BindingResult->유효성검사때문에 필요=>에러정보객체를 저장
	@RequestMapping(value="/board/write.do",method=RequestMethod.POST)
	public String submit(@ModelAttribute("command") BoardCommand command,
			                       BindingResult result) {
		if(log.isDebugEnabled()) {
			log.debug("BoardCommand=>"+command);//입력받은 값을 출력
			//로그객체명.debug(출력대상자를 입력)
		}
		//유효성검사
		new BoardValidator().validate(command, result);
		//에러정보가 들어있다면
		if(result.hasErrors()) {
			return form(); //"boardWrite"->boardWrite.jsp로 이동하라
		}
		//글쓰기 및 업로드=>입출력=>예외처리
		try {
			String newName="";//업로드한 파일의 변경된 파일명을 저장
			//업로드되어있다면
			if(!command.getUpload().isEmpty()) {
				//탐색기에서 선택한 파일이름->getOriginalFileName() aaaa.txt->1234222.txt
				newName=FileUtil.rename(command.getUpload().getOriginalFilename());
				System.out.println("newName=>"+newName);
				//DTO에도 바뀐이름저장->테이블에도 바뀐이름 저장
				command.setFilename(newName);//->springbord2->filename필드
			}
			//최대값+1
			int newSeq=boardDao.getNewSeq()+1;
			System.out.println("newSeq=>"+newSeq);//1->2
			//게시물번호->계산->저장
			command.setSeq(newSeq);//2
			//글쓰기 호출
			boardDao.insert(command);//DB상에 반영
			//실제로 upload폴더로 업로드한 파일을 전송(복사)
			if(!command.getUpload().isEmpty()) {
				File file=new File(FileUtil.UPLOAD_PATH+"/"+newName);
				//물리적으로 데이터전송(파일 전송)
				command.getUpload().transferTo(file);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e2) {
			e2.printStackTrace();
		}
		
		//return "redirect:요청명령어"; =>return "이동할 페이지명"
		return "redirect:/board/list.do";
	}
}






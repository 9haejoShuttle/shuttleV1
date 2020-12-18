/*
 * Author:Recflow
 *
 * ----고객 노선 신청을 위한 Module----
 * 만들어야 할 페이지 /apply/list
 *
 * 내가 만들어야 할 때 고려할 부분
 * 1. 어플라이로 접속시 기존의 사용자가 신청한 노선을 보여준다. (페이징 해줘야 함)
 *    그리고 여기서 정렬을 어떻게 해아할지 신청마감이 거의 임박한 순, 최근에 등록된 노선인 순 2개 정도..
 *    이 부분은 프론트에서 셀렉트 박스로 보여지게 하자.
 * 2. 노선의 검색 기능,
 *    근데 검색어의 정확도를 위해 제한은 있어야 할 것 같은데
 *    (ㅇㅇ구, ㅇㅇ 역으로 제한한다던가 그런 제안을 해 주는 메세지 한줄을 보여주는 게 좋겠다)
 *    검색 시에 해당 검색 지역에 가까운 지역의 노선을 보여주는 것은 어떨지...?
 * 3. 내가 신청한 노선일 경우 삭제가 가능하도록 해야한다.
 * 4. 다른 사람이 작성한 노선일 경우 신청 가능(중복신청 x)
 * 5. 카카오 지도 API 연동
 *
 */
package com.shuttle.apply.controller;

import com.shuttle.apply.service.ApplyService;
import com.shuttle.domain.Apply;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/apply")
@Log4j2
public class ApplyController {

    private final ApplyService applyService;

    @GetMapping(value = {"/{page}"})
    public void list(Model model, @PathVariable("page") int page) {

        int size = 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by("regdate").descending());
        Page<Apply> result = applyService.getApplyPageList(pageable);

    }

    //신청
    @GetMapping("/register")
    public void register() {
        log.info("register GET.................");
        //새로 신청할 때 들어올 화면
        //그 값을 받아서 화면에서 포스트메서드로 보내줌
    }


    @GetMapping("/read/{id}")
    public void read(@PathVariable("id") long id) {

    }

}

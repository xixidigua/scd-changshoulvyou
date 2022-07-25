package com.yida.scdchangshoulvyoudemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yida.scdchangshoulvyoudemo.entity.ComplaintAndSuggest;
import com.yida.scdchangshoulvyoudemo.entity.ReadState;
import com.yida.scdchangshoulvyoudemo.service.ComplaintAndSuggestService;
import com.yida.scdchangshoulvyoudemo.service.ReadStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

;

/**
 * Controller 控制层景点增加上传
 */
@Controller
public class ComplaintAndSuggestController {
    @Autowired
    private ComplaintAndSuggestService complaintAndSuggestService;
    @Autowired
    private ReadStateService readStateService;

    //投诉建议删除功能
    //删除功能(一个)
    @GetMapping("/deleteComplaintAndSuggest")
    public String deleteComplaintAndSuggest(@RequestParam("id") Integer id,HttpServletRequest request) {
        //去掉相同的元素
        List<ComplaintAndSuggest> complaintAndSuggests = complaintAndSuggestService.readAll();
        for (ComplaintAndSuggest c : complaintAndSuggests) {
            //比较数据库里面有没有对应的id数据 没有不让删除
            if (c.getId() == id) {
                complaintAndSuggestService.delete(id);
            }
        }
        return "redirect:/tousuAndIdea";//重定向;
    }

   //获取前端选勾选的id值
    List<Integer> list = new ArrayList<>();

    @RequestMapping(value = "/deleteAllComplaintAndSuggest")
    public void deleteAllComplaintAndSuggests(HttpServletRequest req) {
        String[] ids = req.getParameterValues("array[]");
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Integer id = Integer.parseInt(ids[i]);
                list.add(id);
            }
        }

    }

    //删除功能(多删除)
    @GetMapping("/deleteAllComplaintAndSuggest")
    public String deleteAllComplaintAndSuggest() {
        if (list.size() > 0) {
            for (Integer id : list) {
                //去掉相同的元素
                List<ComplaintAndSuggest> complaintAndSuggests = complaintAndSuggestService.readAll();
                for (ComplaintAndSuggest c : complaintAndSuggests) {
                    //比较数据库里面有没有对应的id数据 没有不让删除
                    if (c.getId() == id) {
                        complaintAndSuggestService.delete(id);
                    }
                }
            }
        }
        list.clear();
        return "redirect:/tousuAndIdea";//重定向;
    }

  //查询投诉和建议
  //查询功能searchVideoColumn
  @RequestMapping( value = "/searchtousuAndIdea", method = {RequestMethod.POST, RequestMethod.GET})
  public String  searchVideo (HttpServletRequest req,
                                Model model,

                              @RequestParam("state") Integer state_id,
                                @RequestParam("begin") String startTime,
                                @RequestParam("end") String endTime,
                                @RequestParam(value = "strCur", required = false, defaultValue = "1") Integer pageNum
  ) {
      List<ReadState> readStates = readStateService.readAll();
      model.addAttribute("readStates", readStates);

      PageHelper.startPage(pageNum, 3);
      List<ComplaintAndSuggest> complaintAndSuggests = complaintAndSuggestService.read(state_id, startTime, endTime);
      model.addAttribute("state_id",state_id);
      model.addAttribute("startTime",startTime);
      model.addAttribute("endTime",endTime);
      model.addAttribute("complaintAndSuggests", complaintAndSuggests);
      PageInfo<ComplaintAndSuggest> page = new PageInfo<>(complaintAndSuggests);
      model.addAttribute("page", page);
      return  "tousuAndIdea";
  }
    //转向功反馈内容界面功能
    @GetMapping("/fanKuiAndSuggest")
    public String fanKuiAndSuggest(@RequestParam("id") Integer id,Model model) {
        int ids = id - 1;
        String str="反馈内容，某个功能有问题"+ids;
   System.out.println(str);
   model.addAttribute("str",str);
        return "fankuineirong";
    }

}
package com.yida.scdchangshoulvyoudemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yida.scdchangshoulvyoudemo.entity.*;
import com.yida.scdchangshoulvyoudemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserLoginController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ScenicSpotsColumnService scenicSpotsColumnService;
    @Autowired
    private SenicService senicService;
    @Autowired
    private FocusMessageColumnService focusMessageColumnService;
    @Autowired
    private VideoColumnService videoColumnService;
    @Autowired
    private ClassifyService classifyService;
    @Autowired
    private RimColumnService rimColumnService;
    @Autowired
    private ConsumptionClassifyService consumptionClassifyService;
    @Autowired
    private ScenicAreaConsultService scenicAreaConsultService;
    @Autowired
    private ReadStateService readStateService;
    @Autowired
    private ComplaintAndSuggestService complaintAndSuggestService;
    @Autowired
    private TrafficRouteService trafficRouteService;
    @Autowired
    private VersionManageService versionManageService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private LongevityProfileService longevityProfileService;//长寿概况
//创建变量id 供所有类使用
    public static String id="";
    //创建变量 error 供所有类使用
    public static String error="";
    @GetMapping("/userLogin")
    public String loginPage(HttpSession session) {
        if (session != null) {
            String name = (String) session.getAttribute("currentName");
            String pwd = (String) session.getAttribute("currentPwd");
            if (name != null && pwd != null) {
                return "redirect:/index";//重定向;
            }
        }

        return "userLogin";
    }

    @PostMapping("/login")
    public String login(String name, String password, Model model, HttpSession session) {
        String tmpName = name.trim();
        String tmpPwd = password.trim();
        if (tmpName.equals("") || tmpPwd.equals("") || !employeeService.isValid(tmpName, tmpPwd)) {
            model.addAttribute("loginError", "用户名或密码错误");
            return "userLogin";
        }
        //保存用户登录信息到session域
        session.setAttribute("currentName", tmpName);
        session.setAttribute("currentPwd", tmpPwd);
        return "redirect:/index";//重定向;
    }

    @GetMapping("/index")
    public String mainPage(@RequestParam ("id") String Id) {
        //将Id存起来供整个微服务使用
        if(Id!=null){
            id=Id;
        }else {
            error="请重新按要求的网址登陆";
           return  "redirect:/unauthorized";
        }
    System.out.println(id);
        return "index";
    }
    @ResponseBody
    @GetMapping("/unauthorized")
    public String  unauthorizedPage() {
        return  error;
    }

    @GetMapping("/logout")
    public String mainPage(HttpSession session) {
        if (session != null) {
            session.invalidate();//销毁session
        }
        return "redirect:/userLogin";//重定向;
    }



    //焦点信息
    @RequestMapping("/jiaodianxinxiliebiao")
    public String jingDianXinXiLieBiao(Model model,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 3);
        List<FocusMessageColumn> focusMessageColumns = focusMessageColumnService.readAll();
        model.addAttribute("focusMessageColumns", focusMessageColumns);
        PageInfo<FocusMessageColumn> page = new PageInfo<>(focusMessageColumns);
        model.addAttribute("page", page);
        return "jiaodianxinxiliebiao";
    }


    //消息列表
    @RequestMapping("/xiaoxiliebiao")
    public String xiaoXiLieBiao(Model model,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 3);
        List<FocusMessageColumn> focusMessageColumns = focusMessageColumnService.readAllMessage();
        model.addAttribute("focusMessageColumns", focusMessageColumns);
        PageInfo<FocusMessageColumn> page = new PageInfo<>(focusMessageColumns);
        model.addAttribute("page", page);
        return "xiaoxiliebiao";
    }

    //视频列表
    @RequestMapping("/shipinliebiao")
    public String shiPinLieBiao(Model model,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<Classify> classifies = classifyService.readAll();
        model.addAttribute("classifies", classifies);
        PageHelper.startPage(pageNum, 3);
        List<VideoColumn> videoColumns = videoColumnService.readAll();
        model.addAttribute("videoColumns", videoColumns);
        PageInfo<VideoColumn> page = new PageInfo<>(videoColumns);
        model.addAttribute("page", page);
        return "shipinliebiao";
    }

    //周边列表
    @RequestMapping("/zhoubianliebiao")
    public String zhouBianLieBiao(Model model,
                                  @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<ConsumptionClassify> consumptionClassifies = consumptionClassifyService.readAll();
        model.addAttribute("consumptionClassifies", consumptionClassifies);
        PageHelper.startPage(pageNum, 3);
        List<RimColumn> rimColumns = rimColumnService.readAll();
        model.addAttribute("rimColumns", rimColumns);
        PageInfo<RimColumn> page = new PageInfo<>(rimColumns);
        model.addAttribute("page", page);
        return "zhoubianliebiao";
    }

    //景区咨询
    @RequestMapping("/jingquzixun")
    public String jingQuZiXun(Model model,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        PageHelper.startPage(pageNum, 3);
        List<ScenicAreaConsult> scenicAreaConsults = scenicAreaConsultService.readAll();
        model.addAttribute("scenicAreaConsults", scenicAreaConsults);
        PageInfo<ScenicAreaConsult> page = new PageInfo<>(scenicAreaConsults);
        model.addAttribute("page", page);
        return "jingquzixun";
    }

    //投诉和建议
    @RequestMapping("/tousuAndIdea")
    public String touSuAndIdea(Model model,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<ReadState> readStates = readStateService.readAll();
        model.addAttribute("readStates", readStates);
        PageHelper.startPage(pageNum, 3);
        List<ComplaintAndSuggest> complaintAndSuggests = complaintAndSuggestService.readAll();
        model.addAttribute("complaintAndSuggests", complaintAndSuggests);
        PageInfo<ComplaintAndSuggest> page = new PageInfo<>(complaintAndSuggests);
        model.addAttribute("page", page);
        return "tousuAndIdea";
    }

    //交通路线
    @GetMapping("/trafficRoute")
    public String trafficRoute(Model model) {
        List<TrafficRoute> trafficRoutes = trafficRouteService.readAll();
        model.addAttribute("trafficRoutes", trafficRoutes);
        return "trafficRoute";
    }

    //长寿概况
    @GetMapping("/changshougaikuang")
    public String changShouGaiKuang(Model model) {
        List<LongevityProfile> longevityProfiles = longevityProfileService.readAll();
        model.addAttribute("longevityProfiles", longevityProfiles);
        return "changshougaikuang";
    }

    //版本管理
    @GetMapping("/banbenManage")
    public String banBenManage(Model model) {
        List<Client> clients = clientService.readAll();
        model.addAttribute("clients",clients);
        List<VersionManage> versionManages = versionManageService.readAll();
        model.addAttribute("versionManages",versionManages);
        return "banbenManage";
    }

    //密码管理
    @GetMapping("/password")
    public String password() {
        return "password";
    }


    //发布焦点消息
    @GetMapping("/fabujiaodianxiaoxi")
    public String faBuJiaoDianXiaoXi() {
        return "fabujiaodianxiaoxi";
    }

    //发布消息
    @GetMapping("/fabuxiaoxi")
    public String faBuXiaoXi() {
        return "fabuxiaoxi";
    }

    //发布视频
    @GetMapping("/fabushipin")
    public String faBuShiPin(Model model) {
        List<Classify> classifies = classifyService.readAll();
        model.addAttribute("classifies", classifies);
        return "fabushipin";
    }

    //发布吃喝玩乐
    @GetMapping("/fabuEatAndPlay")
    public String faBuEatAndPlay(Model model) {
        List<ConsumptionClassify> consumptionClassifies = consumptionClassifyService.readAll();
        model.addAttribute("consumptionClassifies", consumptionClassifies);
        return "fabuEatAndPlay";
    }

    //添加景区咨询
    @GetMapping("/addjingquzixun")
    public String addJingQuZiXun() {
        return "addjingquzixun";
    }

    //反馈内容
    @GetMapping("/fankuineirong")
    public String fanKui() {
        return "fankuineirong";
    }
    //用户管理
    @GetMapping("/main")
    public String userPage() {
        //要跳转到shiro权限的微服务
        return "redirect:http://localhost:9000/main";
    }
}


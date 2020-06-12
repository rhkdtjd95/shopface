package kr.ac.sunmoon.shopface.employ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EmployController {
    private final EmployService employService;
    
    @GetMapping("/employ/form")
    public ModelAndView addEmploy() {
        return new ModelAndView("/employ/list.html");
    }
    
    @PostMapping("/employ/{branchNo}")
    public Map<String, Object> addEmploy(@PathVariable int branchNo, Employ employ) {
        boolean isSucess = employService.addEmploy(employ);
        
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("isSucess", isSucess);
        
        return responseMap;
    }
    
    @GetMapping("/employ/{branchNo}")
    public ModelAndView getEmployList(@PathVariable int branchNo) {
        Employ employ = new Employ();
        employ.setBranchNo(branchNo);
        
        List<Employ> employList = employService.getEmployList(employ);
        
        ModelAndView modelAndView = new ModelAndView("employ/list.html");
        modelAndView.addObject("employList", employList);
        
        return modelAndView;
    }
    
    @GetMapping(value = "/employ/{branchNo}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Employ> getEmployList(@PathVariable  Employ employ) {
        
        return null;
        //return  employService.getEmployList(employ);
    }
    
    @GetMapping("/employ/{branchNo}/{no}")
    public ModelAndView getEmploy(@PathVariable int branchNo, @PathVariable int no) {
        Employ employ = new Employ();
        employ.setNo(no);
        
        ModelAndView modelAndView = new ModelAndView("/employ/detail.html");
        modelAndView.addObject("employ", employService.getEmploy(employ));
        
        return modelAndView;
    }
    
    @PutMapping("/employ/{branchNo}/{no}")
    public ModelAndView editEmploy(@PathVariable int branchNo, @PathVariable int no, Employ employ) {
        employService.editEmploy(employ);
        ModelAndView modelAndView = new ModelAndView("redirect:/employ/" + employ.getBranchNo());
        
        return modelAndView;
    }
    
    @DeleteMapping("/employ/{no}")
    public ModelAndView removeEmploy(@PathVariable("no") int no, Employ employ) {
        employService.deleteEmploy(employ);
        return new ModelAndView("/employ/list.html");
    }
    
    @PutMapping("/employ/invite/{no}")
    @ResponseBody
    public Map<String, Object> resendInviteMessage( @PathVariable int no, Employ employ) {
        boolean isSuccess = employService.resendInviteMessage(employ);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("isSuccess", isSuccess);
        
        return responseMap;
    }
}
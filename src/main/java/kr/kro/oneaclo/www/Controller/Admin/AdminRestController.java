package kr.kro.oneaclo.www.Controller.Admin;

import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminRestController {
    private final MembersService membersService;

    @PostMapping("/authChange")
    public void AuthChange(@RequestParam String UserId, @RequestParam String UserAuth) {
        membersService.AuthChange(UserId, UserAuth);
    }
    @PostMapping("/activeChange")
    public void ActiveChange(@RequestParam String UserId, @RequestParam String UserActive) {
        membersService.ActiveChange(UserId, UserActive);
    }
    @PostMapping("/deleteUser")
    public void DeleteUser(@RequestParam String UserId) {
        membersService.UserDel(UserId);
    }
}

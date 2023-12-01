package spring.mvc.bank.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDetailsController {
    private static final String REQUEST_MAPPING = "/admin-details";

    @Secured({" ROLE_ADMIN "})
    @GetMapping(value = REQUEST_MAPPING)
    public String showAdminDetailsPage() {
        return "admin-details";
    }
}

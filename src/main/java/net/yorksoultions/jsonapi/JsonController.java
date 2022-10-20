package net.yorksoultions.jsonapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class JsonController {

    private JsonService jsonService;

    public JsonController(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    // http://localhost:8080/code
    @GetMapping("/code")
    public String code(HttpServletRequest request) {
        return this.jsonService.code(request);
    }

    // http://localhost:8080/ip
    @GetMapping("/ip")
    public HashMap ip(HttpServletRequest request) {
        return this.jsonService.ip(request);
    }

    @GetMapping("/echo/**")
    public HashMap echo(HttpServletRequest request) {
        return this.jsonService.echo(request);
    }

    @GetMapping({"/time", "/date"})
    public HashMap time() {
        return this.jsonService.time();
    }
}

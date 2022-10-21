package net.yorksoultions.jsonapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/headers")
    public HashMap headers(HttpServletRequest request) {
        return this.jsonService.headers(request);
    }

    @GetMapping("/cookie")
    public HashMap cookie(HttpServletResponse response) {
        return this.jsonService.cookie(response);
    }

    @GetMapping("/md5")
    public HashMap md5(@RequestParam String text){
        return this.jsonService.md5(text);
    }

    @GetMapping("/validate")
    public HashMap validate(@RequestParam String json) {
        return this.jsonService.validate(json);
    }
}

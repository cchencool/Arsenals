package hello;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class WelcomeController {

	// inject via application.properties
    @Value("${welcome.message:test}")
	private String message = "Hello World";

    @Value("${welcome.name:mm}")
    private String name = "me2.";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
        model.put("name", this.name);
		return "welcome";
	}


	@PostMapping("/init")
    @ResponseBody
    public String init(@RequestBody Map<String, Object> params) {
	    String name = "me";
	    if (null != params) {
	        name = Optional.ofNullable(params.get("name")).orElse("").toString();
        }

        String resStr = "hello, " + name + ".";
        Map<String, String> res = new HashMap<String, String>();
	    res.put("result", resStr);

        String res_json = (new Gson()).toJson(res);

	    return res_json;
    }
}

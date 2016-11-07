package hello;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class WebController extends WebMvcConfigurerAdapter {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showForm(PersonForm personForm) {
        return "form";
    }

    @PostMapping("/")
    public ModelAndView checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("form");
        }
        List<Object[]> lista = new ArrayList<>();
        lista.add(new Object[] {personForm.getName(),personForm.getAge(),personForm.getCountry()});
        jdbcTemplate.batchUpdate("INSERT INTO person (name, age, country) VALUES (?,?,?)",
                lista);

        return new ModelAndView("redirect:/file");
    }
}

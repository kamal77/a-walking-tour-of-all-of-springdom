package org.springsource.examples.sawt.web.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springsource.examples.sawt.CustomerService;
import org.springsource.examples.sawt.services.model.Customer;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomerController {


    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private CustomerService customerService;

    // so that Spring MVC has something to bind form values to
    @ModelAttribute
    public Customer customer() {
        return new Customer();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String customer(@RequestParam(value = "id", required = false) Long id, Model model ) {
        if (null != id) {
        	model.addAttribute("customer", this.customerService.getCustomerById( id ));
            model.addAttribute("customerId", id);
        }
        return "customers/display";
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void setupAddition() {

        // demonstrates that this is a front controller
        // the interesting 'setup' is being done by the model ('customer')
        log.info("About to show the add page, which will be 'add.jsp'");

    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public String processAddition(@ModelAttribute Customer c, Model modelAndView) {
        Customer customer = this.customerService.createCustomer(c.getFirstName(), c.getLastName());
        modelAndView.addAttribute("id", customer.getId());
        return "redirect:/display";
    }


}



package com.blacklist.microservice.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class BlacklistController {

    private List<String> ips;
    private Logger logger = Logger.getLogger(BlacklistController.class.getName());

    public BlacklistController() {
        this.ips = new ArrayList<>();

        this.ips.add("127.0.0.1");
        this.ips.add("128.0.0.1");
    }

    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public ResponseEntity<String> checkIp(HttpServletRequest request) {
        String ipItem = request.getRemoteAddr();
        return this.ips.contains(ipItem) ? ResponseEntity.ok().body("IP FOUND!" + ipItem) : ResponseEntity.ok().body("IP NOT FOUND!" + ipItem);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<String> getAll() {
        this.logger.info("ips.getAll()");
        return this.ips;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity addItem(String item) {
        this.logger.info("ips.add()");
        this.ips.add(item);
        return ResponseEntity.ok().body(item);
    }

    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    public ResponseEntity deleteItem(String item) {
        this.logger.info("ips.delete()");
        this.ips.remove(item);
        return ResponseEntity.ok().body(this.ips);
    }
}

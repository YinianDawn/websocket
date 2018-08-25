package learn.websocket.controller;

import learn.websocket.model.Greeting;
import learn.websocket.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * Created by Candy on 2018/8/26
 */
@RestController
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(path = "/send", method = RequestMethod.GET)
    public Greeting send() {
        template.convertAndSendToUser("1", "/message",
                new Greeting("I am a msg from SubscribeMapping('/macro')."));
        return new Greeting("I am a msg from SubscribeMapping('/macro').");
    }

    @MessageMapping("/message")
    @SendToUser("/message")
    public Greeting handleSubscribe() {
        System.out.println("this is the @SubscribeMapping('/marco')");
        return new Greeting("I am a msg from SubscribeMapping('/macro').");
    }

    @Scheduled(fixedRate = 5000)
    private void sendMessage() {
        template.convertAndSend("/topic/greetings", new Greeting("Hello, there!"));
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/test")
    @SendTo("/topic/test1")
    public Greeting test(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}

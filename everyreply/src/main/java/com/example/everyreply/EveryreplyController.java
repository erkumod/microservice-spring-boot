package com.example.everyreply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
@Controller
public class EveryreplyController {
	private final static String QUEUE_NAME = "hello";
	 @GetMapping("/greeting")
	    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
	        model.addAttribute("name", name);
	        try {
	        //send();
	        }catch(Exception ex) {
	        	System.out.println("Exception coming to connect Rabbit Mq");
	        }
	        
	        try {
	        	recieve();
		        }catch(Exception ex) {
		        	ex.printStackTrace();
		        }
	        
	        return "greeting";
	    }
	 
	 private void send() throws Exception{
		 ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        try (Connection connection = factory.newConnection();
	             Channel channel = connection.createChannel()) {
	            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	            String message = "Hello World! kumud ranjan apndey";
	            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
	            System.out.println(" [x] Sent '" + message + "'");
	        }
		
	}

	 private void recieve() throws Exception{
		 ConnectionFactory factory = newConnectionFactory();
	        
		// factory.setHost("localhost");
	        Connection connection = factory.newConnection();
	        Channel channel = connection.createChannel();

	       // chup ho jao
	        
	        channel.exchangeDeclare("eciexchange", "fanout", true);
	        channel.queueDeclare("eciqueue", true, false, false, null);
	        channel.queueBind("eciqueue", "eciexchange","");
	        
	       // channel.queueDeclare("eciqueue", true, false, false, null);
	        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	            String message = new String(delivery.getBody(), "UTF-8");
	            System.out.println(" [x] Received '" + message + "'");
	        };
	        channel.basicConsume("eciqueue", true, deliverCallback, consumerTag -> { });
	    }
	 
	 
	 private ConnectionFactory newConnectionFactory() {
		  ConnectionFactory factory = new ConnectionFactory();
		  factory.setHost("localhost");
		  factory.setUsername("guest");
		  factory.setPassword("guest");

	     //   eciLogger.info("Listening to {0} with user {1}", addresses, userName);

	        return factory;
	    }
	}



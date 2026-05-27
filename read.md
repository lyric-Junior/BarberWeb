# BARBERWEB

# This software is a Scheduling Web System developed for local enterprises.

The BarberWeb system is a highly scalable and secure scheduling system, it provides the customer and the users to find the better options for both.

# Architecture

This System was developed by lyric-junior (Mauro Maleski), using JDK 21 and the spring Boot framework, there is more about it, that I will explain how everything fits to make this application secure and for real world using.

### Actually Using:
- JWT (JSON Web Tokens)
- Acess tokens
- Refresh tokens
- Lombok annotations
- MySQL Database
- A simple dev BootStrap
- OncePerRequestFilter
- Encrypted Sensitive info
- Validation Constraints in Spring.

# Security

JWT (JSON Web Token) is one of the most used security methods for simple and complex application, but the BarberWeb comes with Role Based Security, STATELESS sessions, Access tokens & Refresh tokens, each one with a different expiration, granting users won't be worried about stoled session as the software also includes replay detections.

# How to use it?

//Now there is a very different question.

When we are talking about a Java Application, we need to understand that the source code will not be on the server, and also there will not be an .env file that set all the system variables needed. The most important is to grant that all the ports open in the server (Debian || Fedora || Windows) need to be secured, you can also change the ports listened by the application, make a user have a limit of requests that he can send into the application (something to update in the future).

After the server firewall, variables and application configs, we start the web server connecting our front-end using React Native and TailwindCSS for styles(TailwindCSS is an open source CSS(Cascade Style Sheeting) framework), the web server in question there is no need to a preferred one, you can use Nginx, Apache2 or anything that you are used to.

# Endpoints

Try your best.
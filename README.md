Fest-Bong
=========
####Still under development!!!!!

Description
--
As a project in the IS-206 course at UiA, Kristiansand Norway. We are making both a client, and a scanner application where you can buy "bonger" or tickets to any major festival in Norway (atleast thats the thought).

It will be based on a client app, where you can choose festival and buy drink-tickets, and a scanner app where you can scan these tickets and check if they are valid.

As a backend we're using a REST-Api based on PHP and MySql - Using Slimframework, a framework for easy managing REST-api's

>* The scanner-application is based on scanning QR-Codes using the https://github.com/zxing/zxing - library.
>* The REST-api is based on [SLIM-framework].

###IS-206 prosjekt høst 2014
* [Chris Aardal]
* [Frode Tjomsland]



###Current API-URIs


```sh
http://chris-felixaa.no/api/v1/login - POST
http://chris-felixaa.no/api/v1/register - POST
http://chris-felixaa.no/api/v1/bong - GET
http://chris-felixaa.no/api/v1/bong/id- GET
http://chris-felixaa.no/api/v1/bonger - GET
```



[Frode Tjomsland]:https://github.com/tommel87
[Chris Aardal]:https://github.com/Felixaa
[SLIM-Framework]:http://www.slimframework.com/

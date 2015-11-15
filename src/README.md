# M2JMS
# En équipe avec Ham et Kevin
---
#voici comment j’ai diviser le code en package :

**projetjmsmodandr.serveur** : contient le sender.java et le receiver.java, users.java 
car une personnes (un user) peut etre à la fois envoyeur et receveur des tweets.
**projetjmsmodandr.messages**: qui contient le corps du message
**projetjmsmodandr.middlewareJms**: (rappel: en vrai cette partie est incluse dans le client, mais pour le projet le prof demande de le mettre dans le serveur.) qui contient le topic sous le nom de DurableSubscriber.java 
**projetjmsmodandr.serveur **: qui fera office d’orienter les requetes et les reponses au differents users.
**projetjmsmodandr.data (par Hamza)** contient le serveur de BD H2

# attention celui qui envoi ne reçoit pas son propre message



```js
console.log('WriteMe.md');
```
# msgconsumer - Message Consumer with IBM MQ & Spring Boot

Ce projet Java permet de consommer des messages IBM MQ, les mapper en entités persistées via Spring Boot, et d'exposer les résultats via des endpoints REST documentés avec Swagger.

---

## Prérequis

### Outils à installer :
- [Java 17+](https://adoptopenjdk.net/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop)

---

## Étapes de lancement

### 1. Démarrer IBM MQ en local avec Docker

```bash
docker run -d \
  --name ibm-mq \
  -e LICENSE=accept \
  -e MQ_QMGR=QM1 \
  -e MQ_APP_PASSWORD=passw0rd \
  -p 1414:1414 \
  -p 9443:9443 \
  icr.io/ibm-messaging/mq:9.3.5.0-r1
```

Le nom de file d'attente à utiliser : `DEV.QUEUE.1`

---

### 2. Lancer l'application Spring Boot (backend)

```bash
./mvnw clean spring-boot:run
```

L’application démarre sur : `http://localhost:8080`

Les messages sont automatiquement consommés depuis IBM MQ via un listener JMS.

---

### 3. Lancer le Producer (testeur)

Créer un producteur simple qui envoie un message JSON :

```java
String json = "{\"content\":\"Hello MQ!\", \"partnerAlias\":\"CACIB\"}";
TextMessage message = session.createTextMessage(json);
producer.send(message);
```

> Le champ `partnerAlias` doit exister dans la base (via un POST sur `/api/partners`). `partnerAlias` doit identifié un partenaire unique

---

### 4. Tester les endpoints REST

#### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

#### Endpoints disponibles
- `GET /api/messages` : liste tous les messages
- `POST /api/messages` : ajoute un message manuellement
- `GET /api/partners` : liste des partenaires
- `POST /api/partners` : crée un nouveau partenaire
- `DELETE /api/partners/{id}` : supprime un partenaire

Exemple de requête POST `/api/partners` :
```json
{
  "alias": "CACIB",
  "type": "EXTERNE",
  "direction": "INBOUND",
  "application": "TradeApp",
  "processedFlowType": "MESSAGE",
  "description": "Banque de test"
}
```

---

## 🧪 Lancer les tests

```
./mvnw test
```

Les tests couvrent :
- le mapping DTO ↔ entités
- les services (logique métier)
- les contrôleurs REST

---

## Structure du projet

```
src
├── main
│   ├── java/com/cacib/msgconsumer
│   │   ├── controller
│   │   ├── dto
│   │   ├── entity
│   │   ├── mapper
│   │   ├── repository
│   │   ├── service
│   │   └── listener
│   └── resources
│       └── application.properties
└── test
    └── java/com/cacib/msgconsumer
```

---

## 🛠️ Configuration MQ (application.properties)

```properties
mq.queue.name=DEV.QUEUE.1
mq.channel=DEV.APP.SVRCONN
mq.connName=localhost(1414)
mq.user=app
mq.password=passw0rd
mq.queueManager=QM1
```

---

##  Exemple de message JSON consommé

```json
{
  "content": "Hello from JMS!",
  "partnerAlias": "CACIB"
}
```

---

## Support
En cas de bug ou de question, ouvre une issue GitHub ou contacte l’auteur du projet.

---

🚀 Bon développement !


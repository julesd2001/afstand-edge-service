# Afstand Edge Service
Dit is de repository voor de afstand-edge-service microservice. Deze Edge service verbindt naar 2 back-end microservice: de ene is rit-service en draait onder een MySQL database, en de andere is vrachtwagen-service; deze draait dan onder MongoDB.
Schematisch voorgesteld ziet onze architectuur er als volgt uit:
![Diagram Service Architecture](https://user-images.githubusercontent.com/57659236/148530199-e5fdd2ac-2704-47f9-a410-d0bcfb7a96c7.jpeg)



Het doel van beide back-end services is simpel:
- vrachtwagen-service houdt gegevens bij over een vrachtwagen, zoals het merk, model, bouwjaar, nummerplaat en tot welk bedrijf deze vrachtwagen behoort.
- Bij rit-service is het de bedoeling dat we de ritten die een vrachtwagen maakt, ook bijgehouden kunnen worden. In deze service werken we dus vooral met zaken zoals de ritlengte, het vertrekpunt en de bestemming, en het begingewicht, want in deze service houden we ook bij hoeveel gewicht een vrachtwagen met zich meeneemt in cargo.

U kan deze back-end services hier bekijken:
- vrachtwagen-service: https://github.com/julesd2001/vrachtwagen-service
- rit-service: https://github.com/julesd2001/rit-service


Met onze edge-service willen we deze zaken beide combineren: bijvoorbeeld de totale afstand opvragen dat 1 vrachtwagen heeft afgelegd op basis van alle ritafstanden. 

# Swagger UI implementatie:
![image](https://user-images.githubusercontent.com/57659236/148543983-749f6b4c-a7ea-49ed-9920-a6b6bf1a3449.png)


# GET /totaleafstand/nummerplaat/{nummerplaat}
Met deze request halen we de totale afstand op voor alle vrachtwagens met het nummerplaat dat meegegeven wordt.

![image](https://user-images.githubusercontent.com/57659236/148530640-314b2a96-db1d-476e-8441-ae7cf228fe73.png)


# GET /totaleafstand/bedrijf/{bedrijf}
Met deze request halen we de totale afstand op voor alle vrachtwagens die toebehoren tot een bedrijf.

![image](https://user-images.githubusercontent.com/57659236/148530939-26e65a83-85c8-4398-b913-f2a32ef79356.png)


# GET /totaleafstand/nummerplaat/{nummerplaat}/vertrekpunt/{vertrekpunt}
Met deze request halen we de totale afstand op van de vrachtwagen met een gegeven nummerplaat en het gegeven vertrekpunt.

![image](https://user-images.githubusercontent.com/57659236/148531585-e56df1ff-3fbf-4364-853f-32b8239e60ed.png)


# GET /totaleafstand/nummerplaat/{nummerplaat}/bestemming/{bestemming}
Met deze request halen we de totale afstand op van de vrachtwagen met een gegeven nummerplaat en de gegeven bestemming.

![image](https://user-images.githubusercontent.com/57659236/148531687-5e22bd3e-9980-4bf8-8478-2be74dea7c47.png)


# POST /totaleafstand
Door een nummerplaat en een ritId mee te geven aan deze POST request kunnen we een nieuwe ritafstand object aanmaken op basis van een rit en een vrachtwagen.

# PUT /totaleafstand
Deze request gebruiken we indien we een ritafstand willen aanpassen.

# DELETE /totaleafstand/{ritId}
Voor deze request zullen we gewoon één enkele rit kunnen verwijderen. Dit door de ritId mee te geven aan deze request.

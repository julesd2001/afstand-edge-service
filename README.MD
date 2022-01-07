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


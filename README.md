# Delfinen

### Gruppe 3

| Fornavn | GitHub |
|---------|--------|
| Frederik | FredeVNN | 
| Ajla | mocina1225 |
| Lærke | lrkewendel |
| Mikkel | Hunniche |  

[Link til Team Board](https://github.com/users/lrkewendel/projects/2/views/12)


# Start program
Sådan starter du programmet...

## Funktionelle krav

**Formanden**

| Nr. | Krav | Opfyldt? |
|---|---|---|
| F1 | Formanden kan oprette et medlem med navn, fødselsdato og aktivitetsform: aktivt eller passivt medlemskab, og for aktive medlemmer motionist eller konkurrencesvømmer. |  |
| F2 | Når formanden opretter en konkurrencesvømmer, vælges én eller flere af de fire discipliner. Man kan ikke oprette en konkurrencesvømmer uden disciplin. | |
| F3 | Systemet giver hvert nyt medlem et unikt medlemsnummer. | |
| F4 | Formanden kan se en liste over alle medlemmer med medlemsnummer, navn, alder, junior/senior og aktivitetsform (passiv, motionist eller konkurrencesvømmer). | |

**Kassereren**

| Nr. | Krav | Opfyldt? |
|---|---|---|
| F5 | Kassereren kan se hvert medlems årlige kontingent, beregnet efter reglerne ovenfor. |
| F6 | Kassereren kan se den forventede samlede kontingentindtægt. |
| F7 | Kassereren kan registrere, at et medlem har betalt årets kontingent. |
| F8 | Kassereren kan se en liste over medlemmer i restance med medlemsnummer, navn og det beløb, de skylder. |

**Træneren**

| Nr. | Krav | Opfyldt? |
|---|---|---|
| F9 | Træneren kan se juniorholdet og seniorholdet hver for sig: hvilke konkurrencesvømmere der er på holdet, og hvilke discipliner de er aktive i. |
| F10 | Træneren kan registrere en træningstid for en konkurrencesvømmer i en disciplin med dato. Er tiden bedre end svømmerens hidtil bedste i disciplinen, bliver den ny bedste tid. Ellers beholdes den gamle, og træneren får det at vide. |
| F11 | Træneren kan registrere et stævneresultat for en konkurrencesvømmer: stævnets navn, dato, disciplin, placering og tid. |
| F12 | Træneren kan se én konkurrencesvømmers resultater: bedste træningstid (med dato) i hver disciplin og alle stævneresultater. |
| F13 | Træneren kan vælge en disciplin og se top 5 for juniorer og top 5 for seniorer, med navn, tid og dato. |

**Alle**

| Nr. | Krav | Opfyldt? |
|---|---|---|
| F14 | Alle data (medlemmer, betalinger og resultater) gemmes i tekstfiler og indlæses, når programmet starter. Lukker man programmet og starter det igen, er intet gået tabt. |

### Udvidelser 


| Nr. | Udvidelse | Tilføjet? |
|---|---|---|
| U1 | Formanden kan rette et medlems oplysninger, fx skifte mellem aktiv og passiv, motionist og konkurrencesvømmer, eller ændre discipliner. |
| U2 | Formanden kan melde et medlem ud. |
| U3 | Kassereren kan starte et nyt kontingentår, så alle står som ikke-betalt igen. |
| U4 | Hvert hold har en træner, hvis navn vises på holdoversigten. |
| U5 | Top 5 regnes ud fra den bedste af både trænings- og stævnetider. |
| U6 | Formanden kan søge efter et medlem på navn. |
| U7 | Medlemslisten kan sorteres efter navn, alder eller medlemsnummer. |
| U8 | Brugeren vælger rolle ved opstart og ser kun sin egen menu. |
| U9 | Kassereren kan se, hvor meget der er indbetalt indtil nu, og hvor meget der mangler. |

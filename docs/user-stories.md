## US-01 Opret medlem (dækker: medlemsinfo) Størrelse: L Status: To do

Som formand vil jeg gerne kunne oprette et medlem med navn, fødselsdato og aktivitetsform, så jeg kan registrere nye medlemmer korrekt i systemet.

- Givet: At formanden er inde i programmet.
- Når: Formanden indtaster navn, fødselsdato og aktivitetsform for et nyt medlem.
- Så: Bliver medlemmet oprettet og gemt med de indtastede oplysninger.

## US-02 Vælg discipliner (dækker: tildeling af disciplin) Størrelse: M Status: To do

Som formand vil jeg gerne kunne vælge én eller flere discipliner, når jeg opretter en konkurrencesvømmer, så jeg sikrer, at alle konkurrencesvømmere er tilknyttet mindst én disciplin.

- Givet: At formanden er ved at oprette et nyt medlem som konkurrencesvømmer.
- Når: Formanden vælger én eller flere af de fire discipliner.
- Så: Bliver konkurrencesvømmeren oprettet med de valgte discipliner. Det er ikke muligt at oprette medlemmet uden mindst én disciplin.

## US-03 Tildel medlemsnumre (dækker: skelne medlemmer) Størrelse: S Status: To do

Som formand vil jeg gerne have, at systemet automatisk tildeler hvert nyt medlem et unikt medlemsnummer, så jeg nemt kan identificere og skelne mellem medlemmerne.

- Givet: At formanden opretter et nyt medlem.
- Når: Medlemmet bliver oprettet.
- Så: Tildeler systemet automatisk et unikt medlemsnummer til medlemmet.

## US-04 Se medlemsliste (dækker: overblik af medlemmer) Størrelse: M Status: To do

Som formand vil jeg gerne kunne se en samlet medlemsliste med medlemsnummer, navn, alder, junior/senior og aktivitetsform, så jeg får et overblik over klubbens medlemmer.

- Givet: At der er oprettet medlemmer i systemet.
- Når: Formanden vælger at se medlemslisten.
- Så: Vises alle medlemmer med medlemsnummer, navn, alder, junior/senior og aktivitetsform.

## US-05 Se kontingent (dækker: overblik af betalinger) Størrelse: M Status: To do

Som kasserer vil jeg gerne kunne se hvert medlems årlige kontingent, så jeg kan holde styr på, hvad hvert medlem skal betale.

- Givet: At der er oprettet medlemmer i systemet.
- Når: Kassereren vælger at se medlemmernes kontingent.
- Så: Vises hvert medlems årlige kontingent beregnet efter klubbens kontingentregler.

## US-06 Se samlet indtægt (dækker: Overblik af klubbens est. indtægt) Størrelse: S Status: To do

Som kasserer vil jeg gerne kunne se den forventede samlede kontingentindtægt, så jeg kan få et overblik over klubbens forventede indtægter.

- Givet: At der er oprettet medlemmer med tilhørende kontingenter.
- Når: Kassereren vælger at se den samlede forventede kontingentindtægt.
- Så: Vises summen af alle medlemmers årlige kontingenter.

## US-07 Registrer betaling (dækker: Overblik over betalingsstatus) Størrelse: M Status: To do

Som kasserer vil jeg gerne kunne registrere, når et medlem har betalt sit årlige kontingent, så jeg kan holde styr på, hvilke medlemmer der har betalt.

- Givet: At et medlem er registreret i systemet og endnu ikke har betalt årets kontingent.
- Når: Kassereren registrerer, at medlemmet har betalt.
- Så: Bliver medlemmets betalingsstatus ændret til betalt.

## US-08 Se restanceliste (dækker: Overblik af restance) Størrelse: M Status: To do

Som kasserer vil jeg gerne kunne se en liste over medlemmer i restance med medlemsnummer, navn og skyldige beløb, så jeg kan følge op på manglende betalinger.

- Givet: At der er medlemmer, som ikke har betalt årets kontingent.
- Når: Kassereren vælger at se restancelisten.
- Så: Vises alle medlemmer i restance med medlemsnummer, navn og det beløb, de skylder.

## US-09 Se svømmehold (dækker: Overblik over svømmere på hvert hold) Størrelse: M Status: To do

Som træner vil jeg gerne kunne se junior- og seniorholdet hver for sig med tilhørende konkurrencesvømmere og deres discipliner, så jeg nemt kan få overblik over svømmerne på hvert hold.

- Givet: At der er oprettet konkurrencesvømmere i systemet.
- Når: Træneren vælger at se junior- eller seniorholdet.
- Så: Vises de relevante konkurrencesvømmere på holdet samt deres tilknyttede discipliner.

## US-10 Registrer træningstid (dækker: Svømmers udvikling) Størrelse: L Status: To do

Som træner vil jeg gerne kunne registrere en svømmers træningstid i en disciplin med dato, så jeg kan følge svømmerens udvikling og automatisk få opdateret vedkommendes bedste tid, når en ny tid er hurtigere.

- Givet: At en konkurrencesvømmer har en registreret bedste tid i en disciplin.
- Når: Træneren registrerer en ny træningstid med dato.
- Så: Opdateres svømmerens bedste tid, hvis den nye tid er hurtigere. Hvis tiden er langsommere, beholdes den eksisterende bedste tid, og træneren får besked om dette.

## US-11 Registrer stævneresultat (dækker: Stævnepræstation) Størrelse: M Status: To do

Som træner vil jeg gerne kunne registrere et stævneresultat med stævnets navn, dato, disciplin, placering og tid, så jeg kan dokumentere svømmernes præstationer ved stævner.

- Givet: At der er oprettet en konkurrencesvømmer i systemet.
- Når: Træneren indtaster stævnets navn, dato, disciplin, placering og tid.
- Så: Bliver stævneresultatet registreret og knyttet til den valgte svømmer.

## US-12 Se svømmeresultater (dækker: Overblik af hver enkelt præstation og udvikling) Størrelse: M Status: To do

Som træner vil jeg gerne kunne se en konkurrencesvømmers bedste træningstider med dato for hver disciplin samt alle stævneresultater, så jeg kan følge den enkelte svømmers præstationer og udvikling.

- Givet: At en konkurrencesvømmer har registrerede træningstider og/eller stævneresultater.
- Når: Træneren vælger en konkurrencesvømmer og ser vedkommendes resultater.
- Så: Vises svømmerens bedste træningstid med dato for hver disciplin samt alle registrerede stævneresultater.

## US-13 Se top 5 (dækker: Rangliste) Størrelse: M Status: To do

Som træner vil jeg gerne kunne vælge en disciplin og se de fem bedste juniorer og seniorer med navn, tid og dato, så jeg kan sammenligne svømmernes præstationer og se deres placering på ranglisten.

- Givet: At der er registreret træningstider for konkurrencesvømmere.
- Når: Træneren vælger en disciplin.
- Så: Vises de fem hurtigste juniorer og de fem hurtigste seniorer med navn, tid og dato, sorteret efter bedste tid.

## US-14 Gem og indlæs data (dækker: data ikke går tabt) Størrelse: L Status: To do

Som bruger vil jeg gerne have, at alle data om medlemmer, betalinger og resultater automatisk gemmes i tekstfiler og indlæses, når programmet starter, så jeg kan være sikker på, at mine registreringer ikke går tabt, når programmet lukkes.

- Givet: At der er registreret medlemmer, betalinger og resultater i systemet.
- Når: Brugeren lukker programmet og starter det igen.
- Så: Indlæses alle tidligere gemte data fra tekstfilerne, så ingen registreringer er gået tabt.

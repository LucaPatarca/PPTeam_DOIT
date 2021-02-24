<p align="center" >
  <img src="/Frontend/resources/icon.png" width="500" height="500">
</p>

---

<p align="center">
    <b>DO_IT</b>, progetto realizzato in <b>Ionic</b> e <b>Spring</b> per il corso di laurea <b>L-31</b> presso <b>Unicam</b>, <i>nell'anno accademico 2020/2021</i>, realizzato dagli studenti Luca Patarca, Giorgio Paoletti e Nico Trionfetti per l'esame di <b>Ingegneria del Software</b> e <b>Progettazione di Applicazioni Web e Mobili</b>.
    <br><br><b>
<a href="https://www.unicam.it/">• Unicam</a>
<a href="https://github.com/trionfettinicoUNICAM/PPTeam_DOIT">• PPTEAM_DOIT</img></a>
</b></p>

# Tabella dei contenuti

- [Panoramica](#panoramica)
- [Processo di sviluppo](#processo)
- [Tecnologie di base](#tecno)
- [Autori](#autori)

# Panoramica e funzionalità di base <a name = "panoramica"></a>

# Processo di Sviluppo<a name = "processo"></a>

Per sviluppare l'applicativo è stato scelto di seguire il processo standardizzato **Unified Process (UP)** utilizzando come strumento di lavoro **Visual Paradigm** basato sul linguaggio di modellazione **UML**.

Attualmente sono state svolte 3 iterazioni dove è stato possibile effettuare l'analisi dei requisi, la progettazione del sistema, l'implementazione e la fase di testing.

Come strumento di versioning è stato utilizzato **Git** attraverso il quale sono stati distinti due brach per sviluppo
- master: utilizzato per pubblicare la baseline (artefatti) sviluppati a fine iterazione
- develop: utilizzato per sviluppo dell'iterazione corrente

Le varie iterazioni hanno dato origine ai seguenti artefatti:
- Diagramma dei casi d'uso: raccolta e specifica dei requisiti e funzionalità del sistema
- Diagramma classi di analisi: identificano i concetti che è necessario il sistema rappresenti e sia capace di manipolare.
- Diagrammi di seguenza: descrivono come le classi di analisi interagiscono tra di loro per ealizzare il comportamento definito nei casi d'uso.
- Diagramma classi di progetto: derivato sfruttando il principio LRG (Low Representational Gap) per derivare le classi di progetto partendo dalle classi di analisi, il diagramam verrà utilizzato per le attività di implementazione.
- Code Base

# Tecnologie di base<a name = "tecno"></a>

Il lato backend si basa sul linguaggio **Java** e rende disponibile per l'interazione delle **Api Rest**, la cui scrittura e gestione, anche sotto l'ottica della sicurezza, sono state rese possibili grazie al framework **Spring Boot**. Per il testing del codice scritto ci si è affidati al framework **JUnit** mentre per il building automatizzato del sistema si è impiegato il tool **Gradle**.

Per quanto concerne la persistenza delle informazioni processate a livello di backend si è deciso di sfruttare i servizi offerti da **MongoDB** e dal relativo framework per linguaggio Java.

Il frontend è interamante scritto utilizzando il framework **Ionic** e scegliendo come user interface il framework **Angular** basato su **TypeScript**. L'applicativo si sostanzia in un app mobile Ibrida che interagisce con il backend tramite chiamate HTTP alle Api Rest rese disponibili. 
L'applicativo sarà installabile su Android o IOS tramite l'utilizzo di **Capacitor** che permette di effettuare chiamate rest più specifiche per la piattaforma scelta.

Per quanto concerne le informazioni di autenticazione tra client e server si è deciso di sfruttare lo standard **JSON Web Token (JWT)**. 
Si è deciso di implementare un autorization server sfruttando le liberie messe a disposizione dal cloud provider **Auth0**, le librie sono state utilizzate soltanto per la generazione e validazione del Token in locale.

# Autori <a name = "autori"></a>

- [Luca Patarca](https://github.com/LucaPatarca)
- [Nico Trionfetti](https://github.com/trionfettinicoUNICAM)
- [Giorgio Paoletti](https://github.com/GiorgioPaoletti-Unicam)
